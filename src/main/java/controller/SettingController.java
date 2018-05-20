package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import constant.VipResolverTypeEnum;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import model.VipResolver;
import service.ConfigService;
import utils.GUIUtil;
import utils.VaildateUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gaochen on 2018/5/16.
 */
public class SettingController implements Initializable {

    @FXML
    private BorderPane settingPane;

    @FXML
    private TableView<VipResolver> vipResolverTableView;

    @FXML
    private TableColumn<VipResolver, String> nameCol;

    @FXML
    private TableColumn<VipResolver, String> urlCol;

    @FXML
    private TableColumn<VipResolver, Boolean> checkCol;

    @FXML
    private TableColumn<VipResolver, String> delCol;

    @FXML
    private TableColumn<VipResolver, String> typeCol;

    @FXML
    private TextField vipName;

    @FXML
    private TextField vipUrl;

    @FXML
    private JFXComboBox<String> vipType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GUIUtil.setRandomColor(settingPane);
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setCellFactory(TextFieldTableCell.<VipResolver>forTableColumn());

        urlCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUrl()));
        urlCol.setCellFactory(TextFieldTableCell.<VipResolver>forTableColumn());

        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(VipResolverTypeEnum.valueOf(cellData.getValue().getType()).getDescription()));

        nameCol.setCellFactory(col -> new TipTableCell<VipResolver>());
        urlCol.setCellFactory(col -> new TipTableCell<VipResolver>());
        typeCol.setCellFactory(col -> new TipTableCell<VipResolver>());

        checkCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getChecked()));
        checkCol.setCellFactory(col -> new TableCell<VipResolver, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);

                if (!empty) {
                    JFXToggleButton toggleButton = new JFXToggleButton();
                    toggleButton.setSize(5.0);
                    toggleButton.setSelected(item);
                    this.setGraphic(toggleButton);
                    toggleButton.setOnMouseClicked((me) -> {
                        VipResolver selected = this.getTableView().getItems().get(this.getIndex());
                        selected.setChecked(toggleButton.isSelected());
                        ConfigService.getInstance().update(selected);
                    });
                }
            }

        });
        delCol.setCellFactory((col) -> new TableCell<VipResolver, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);

                if (!empty) {
                    JFXButton delBtn = new JFXButton("删除");
                    GUIUtil.setBtnStyle(delBtn);
                    delBtn.setId("col-button");
                    this.setGraphic(delBtn);
                    delBtn.setOnMouseClicked((me) -> {
                        VipResolver clickVip = this.getTableView().getItems().get(this.getIndex());
                        vipResolverTableView.getItems().remove(clickVip);
                        ConfigService.getInstance().delete(clickVip.getId());
                    });
                }
            }
        });
        vipResolverTableView.getItems().addAll(ConfigService.getInstance().list());
        vipType.setItems(FXCollections.observableArrayList(VipResolverTypeEnum.list()));
        vipType.getSelectionModel().select(0);
    }

    @FXML
    void addRow(ActionEvent event) {
        String vipName = this.vipName.getText();
        String vipUrl = this.vipUrl.getText();

        if(vipName == null || vipUrl == null || vipName.isEmpty() || vipUrl.isEmpty()) {
            return;
        }
        VipResolver vipResolver = new VipResolver(null,vipName,vipUrl,false,VipResolverTypeEnum.typeOf(vipType.getSelectionModel().getSelectedItem()).toInt());
        if(vipResolverTableView.getItems().contains(vipResolver)) {
            return;
        }
        if(!VaildateUtil.validateUrl(vipUrl)) {
            GUIUtil.alert("请填写正确的VIP解析地址!");
        }
        vipResolverTableView.getItems().add(vipResolver);
    }
}
