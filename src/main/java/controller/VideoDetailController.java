package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.BaseVideo;
import model.Episode;
import model.VipResolver;
import service.ConfigCache;
import utils.GUIUtil;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by gaochen on 2018/5/15.
 */
public class VideoDetailController implements Initializable {

    @FXML
    private AnchorPane videoDetailPane;

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
//        nameColumn.prefWidthProperty().bind(detailTable.widthProperty().multiply(0.2));
//        urlFromColumn.prefWidthProperty().bind(detailTable.widthProperty().multiply(0.8));
//        playColumn.prefWidthProperty().bind(detailTable.widthProperty().multiply(0.2));
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
        detailTable.getItems().setAll(video.getEpisodes());

        playColumn.setCellFactory(col -> new TableCell<Episode, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);

                if (!empty) {
                    Button detailBtn = new Button("GO->");
                    this.setGraphic(detailBtn);
                    detailBtn.setOnMouseClicked((me) -> {
                        Episode episode = this.getTableView().getItems().get(this.getIndex());
                        System.out.println("解析播放");
                        try {
                            playVideo(episode);
                        } catch (IOException | URISyntaxException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

        });
    }

    private void playVideo(Episode episode) throws IOException, URISyntaxException {
        Optional<VipResolver> vipResolver = ConfigCache.getInstance().get();
        if(vipResolver.isPresent()) {
            Desktop.getDesktop().browse(new URI(vipResolver.get().getUrl() + episode.getUrl()));
        }else {
            GUIUtil.alert("请至少选择一个VIP解析器!");
        }
    }

}
