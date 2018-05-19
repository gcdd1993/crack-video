package controller;

import application.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
import model.BaseVideo;
import service.SearchServiceHandler;
import userInterface.Hamburger;
import utils.GUIUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
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
	private TableColumn<BaseVideo, String> fromColumn;

	@FXML
	private TableColumn<BaseVideo, String> nameColumn;

	@FXML
	private TableColumn<BaseVideo, String> descriptionColumn;

	@FXML
	private TableColumn<BaseVideo, String> showDetailColumn;

	@FXML
	private JFXButton searchButton;

	@FXML
	private JFXTextField searchKey;

	@FXML
	private JFXSpinner searchSpinner;

	@FXML
	private JFXHamburger hbm;

	private static ConcurrentHashMap<String,List<BaseVideo>> videoCache = new ConcurrentHashMap<>();

	Hamburger hamburger = new Hamburger();

	@FXML
	void searchAction(ActionEvent event) {
		CompletableFuture<Void> future = CompletableFuture
				.runAsync(this::getVideos)
				.exceptionally((e) -> {
					System.out.println(e);
					return null;
				});
	}

	private void getVideos() {
		String key = searchKey.getText();
		reverseState(true);
		List<BaseVideo> maybeExist = videoCache.get(key);
		if(maybeExist != null && !maybeExist.isEmpty()) {
			videoTableView.getItems().setAll(maybeExist);
		}else {
			List<BaseVideo> videos = SearchServiceHandler.getInstance().search(key);
			videoTableView.getItems().setAll(videos);
			videoCache.put(key,videos);
		}
		reverseState(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// cell factory to display the index:
		GUIUtil.setRandomColor(rootBorderPane);
		fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFrom()));
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

		showDetailColumn.setCellFactory(col -> new TableCell<BaseVideo, String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				this.setText(null);
				this.setGraphic(null);

				if (!empty) {
					JFXButton detailBtn = new JFXButton("GO->");
					GUIUtil.setBtnStyle(detailBtn);
					detailBtn.setId("col-button");
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
		descriptionColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.7));
		fromColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.1));
		nameColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.1));
		showDetailColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.1));

		hamburger.getHamburger(hbm,rootBorderPane);


	}

	@FXML
	void changeHbmState(MouseEvent event) {

	}

	private Stage showVideoDetailDialog(BaseVideo video) throws IOException {
        return GUIUtil.showDialog(Main.FXMLS+"VideoDetail.fxml",Main.CSS + "jfoenix-components.css","(" + video.getFrom() + ") " + video.getName(),Optional.of(video));
	}

	/**
	 * 反转按钮和spinner状态
	 * @param isVisible
	 */
	private void reverseState(boolean isVisible) {
		System.out.println("reverse " + isVisible);
		searchSpinner.setVisible(isVisible);
		searchButton.setVisible(!isVisible);
	}
}