package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.BaseVideo;
import model.Episode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gaochen on 2018/5/15.
 */
public class VideoDetailController implements Initializable {

    @FXML
    private ImageView imageUrl;

    @FXML
    private TextField nameText;

    @FXML
    private TextField fromText;

    @FXML
    private TextArea descriptionText;

    @FXML
    private TableView<Episode> detailTable;

    @FXML
    private TableColumn<Episode, String> nameColumn;

    @FXML
    private TableColumn<Episode, String> urlFromColumn;

    @FXML
    private TableColumn<Episode, String> playColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        urlFromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUrl()));
    }

    public void initData(BaseVideo video) {
        System.out.println("video : " + video);
        //image
        try {
            Image image = new Image(video.getImageUrl());
            imageUrl.setImage(image);
        }catch (Exception e) {
            System.out.println("error");
        }
        //text
        nameText.setText(video.getName());
        fromText.setText(video.getFrom());
        descriptionText.setText(video.getDescription());
        detailTable.getItems().addAll(video.getEpisodes());

        playColumn.setCellFactory(col -> new TableCell<Episode, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);

                if (!empty) {
                    Button detailBtn = new Button("解析播放");
                    this.setGraphic(detailBtn);
                    detailBtn.setOnMouseClicked((me) -> {
                        Episode episode = this.getTableView().getItems().get(this.getIndex());
                        System.out.println("解析播放");
                    });
                }
            }

        });
    }

}
