package controller;

import application.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import constant.VipResolverTypeEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Data;
import model.BaseVideo;
import service.SearchServiceHandler;
import utils.GUIUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
	private TableColumn<BaseVideo, String> typeCol;

	@FXML
	private JFXButton searchButton;

	@FXML
	private JFXTextField searchKey;

	@FXML
	private JFXSpinner searchSpinner;

	@FXML
	private JFXButton setting;

	@FXML
	private JFXButton information;

	@FXML
	private GridPane videoWeb;

	private static List<JFXCheckBox> checkBoxes = new ArrayList<>();

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
		List<BaseVideo> videos = SearchServiceHandler.getInstance().search(checkBoxes
				.stream()
				.filter(JFXCheckBox::isSelected)
				.map(JFXCheckBox::getText)
				.collect(Collectors.toList()),key);
		videoTableView.getItems().setAll(videos);
		reverseState(false);
	}


	@FXML
	void showSettingDialog(ActionEvent event) throws IOException {
		GUIUtil.showDialog(Main.FXMLS+"Setting.fxml",Main.CSS + "jfoenix-components.css","设置",Optional.empty());
	}

	@FXML
	void showInfoDialog(ActionEvent event) throws IOException {
		GUIUtil.showDialog(Main.FXMLS+"Information.fxml",Main.CSS + "jfoenix-components.css","关于",Optional.empty());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//grid pane
		//btn
		int i = 0;
		int j = 0;
		int maxI = 3;
		int maxJ = 3;
		List<String> videoWebNames = VipResolverTypeEnum.list()
				.stream()
				.filter(name -> !VipResolverTypeEnum.ALL.getDescription().equals(name)).collect(Collectors.toList());
		for(String name : videoWebNames) {
			JFXCheckBox checkBox = new JFXCheckBox(name);
			videoWeb.add(checkBox,i,j);
			checkBoxes.add(checkBox);
			if((i + 1) % maxI == 0 && (i + 1) > 0) {
				j ++;
				i = 0;
			}else {
				i ++;
			}
		}
		setting.setMinSize(40, 40);
		setting.setGraphic(new ImageView(new Image(Main.IMAGES + "setting.png")));
		setting.setTooltip(new Tooltip("设置"));

		information.setMinSize(40, 40);
		information.setGraphic(new ImageView(new Image(Main.IMAGES + "info.png")));
		information.setTooltip(new Tooltip("关于"));

		// cell factory to display the index:
		GUIUtil.setRandomColor(rootBorderPane);
		fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFrom()));
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().getDescription()));
		descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

		nameColumn.setCellFactory(col -> new TipTableCell<BaseVideo>());
		fromColumn.setCellFactory(col -> new TipTableCell<BaseVideo>());
		typeCol.setCellFactory(col -> new TipTableCell<BaseVideo>());
		descriptionColumn.setCellFactory(col -> new TipTableCell<BaseVideo>());

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
		descriptionColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.6));
		fromColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.1));
		nameColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.1));
		typeCol.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.1));
		showDetailColumn.prefWidthProperty().bind(videoTableView.widthProperty().multiply(0.1));

	}

	private Stage showVideoDetailDialog(BaseVideo video) throws IOException {
        return GUIUtil.showDialog(Main.FXMLS+"VideoDetail.fxml",Main.CSS + "jfoenix-components.css","(" + video.getFrom() + ") " + video.getName(),Optional.of(video));
	}

	/**
	 * 反转按钮和spinner状态
	 * @param isVisible
	 */
	private void reverseState(boolean isVisible) {
		searchSpinner.setVisible(isVisible);
		searchButton.setVisible(!isVisible);
	}

}