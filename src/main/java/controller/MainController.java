package controller;

import application.Main;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lombok.Data;
import model.BaseVideo;
import service.SearchServiceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Segp-Group 3
 */
@Data
public class MainController implements Initializable {

	@FXML
	private BorderPane rootBorderPane;

	@FXML
	private TableView<BaseVideo> videoTableView;

	@FXML
	private TableColumn<BaseVideo, Void> idColumn;

	@FXML
	private TableColumn<BaseVideo, String> nameColumn;

	@FXML
	private TableColumn<BaseVideo, String> descriptionColumn;

	@FXML
	private TableColumn<BaseVideo, String> showDetailColumn;

	@FXML
	private Button searchButton;

	@FXML
	private TextField searchKey;

	private static ConcurrentHashMap<String,List<BaseVideo>> videoCache = new ConcurrentHashMap<>();

	@FXML
	void searchAction(ActionEvent event) {
		String key = searchKey.getText();
		List<BaseVideo> maybeExist = videoCache.get(key);
		if(maybeExist != null && !maybeExist.isEmpty()) {
			videoTableView.getItems().addAll(maybeExist);
		}else {
			List<BaseVideo> videos = SearchServiceHandler.getInstance().search(key);
			videoTableView.getItems().addAll(videos);
			videoCache.put(key,videos);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// cell factory to display the index:
		idColumn.setCellFactory(col -> {
			TableCell<BaseVideo, Void> cell = new TableCell<>();
			cell.textProperty().bind(Bindings.createStringBinding(() -> {
				if (cell.isEmpty()) {
					return null ;
				} else {
					return Integer.toString(cell.getIndex() + 1);
				}
			}, cell.emptyProperty(), cell.indexProperty()));
			return cell;
		});
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

		showDetailColumn.setCellFactory(col -> new TableCell<BaseVideo, String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				this.setText(null);
				this.setGraphic(null);

				if (!empty) {
					Button detailBtn = new Button("查看详情");
					this.setGraphic(detailBtn);
					detailBtn.setOnMouseClicked((me) -> {
						BaseVideo video = this.getTableView().getItems().get(this.getIndex());
						try {
							showVideoDetailDialog(video);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
			}

		});
	}

	private Stage showVideoDetailDialog(BaseVideo video) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						Main.FXMLS+"VideoDetail.fxml"
				)
		);
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(
				new Scene(
						(Pane) loader.load()
				)
		);
		VideoDetailController controller =
				loader.getController();
		controller.initData(video);
		stage.show();
		return stage;
	}
}