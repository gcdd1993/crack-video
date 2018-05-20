package utils;

import controller.IWithValueInit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by gaochen on 2018/5/16.
 */
public class GUIUtil {
    /**
     * 提示弹框
     * @param msg
     */
    public static void alert(String msg) {
        Alert information = new Alert(Alert.AlertType.INFORMATION,msg);
        information.setTitle("提示"); //设置标题，不设置默认标题为本地语言的information
        information.setHeaderText("消息"); //设置头标题，默认标题为本地语言的information
        Button infor = new Button("show Information");
        infor.setOnAction((e)->{
            information.showAndWait(); //显示弹窗，同时后续代码等挂起
        });
        information.show();
    }

    public static<T> Stage showDialog(String fxmlUri,String cssUri,String title,Optional<T> data) throws IOException {
        FXMLLoader loader = new FXMLLoader(GUIUtil.class.getResource(fxmlUri));
        Stage stage = new Stage(StageStyle.DECORATED);
        Scene scene = new Scene(
                (Pane) loader.load()
        );
        scene.getStylesheets().add(GUIUtil.class.getResource(cssUri).toExternalForm());
        stage.setTitle(title);
        stage.setScene(scene);
        data.ifPresent(d -> {
            IWithValueInit controller =
                    loader.getController();
            controller.initData(d);
        });
//        stage.setResizable(false);
        stage.show();
        return stage;
    }

    /**
     * 随机获取颜色值
     * @param i
     * @return
     */
    public static String getDefaultColor(int i) {
        String color = "#FFFFFF";
        switch (i) {
            case 0:
                color = "#8F3F7E";
                break;
            case 1:
                color = "#B5305F";
                break;
            case 2:
                color = "#CE584A";
                break;
            case 3:
                color = "#DB8D5C";
                break;
            case 4:
                color = "#DA854E";
                break;
            case 5:
                color = "#E9AB44";
                break;
            case 6:
                color = "#FEE435";
                break;
            case 7:
                color = "#99C286";
                break;
            case 8:
                color = "#01A05E";
                break;
            case 9:
                color = "#4A8895";
                break;
            case 10:
                color = "#16669B";
                break;
            case 11:
                color = "#2F65A5";
                break;
            case 12:
                color = "#4E6A9C";
                break;
            default:
                break;
        }
        return color;
    }

    public static void setRandomColor(Node node) {
        node.setStyle("-fx-background-color: " + getDefaultColor((int) ((Math.random() * 12) % 12)));
    }

    public static void setBtnStyle(Node  node) {
        node.setStyle("-fx-background-radius: 40;-fx-background-color: " + GUIUtil.getDefaultColor((int) ((Math.random() * 12) % 12)));
    }

    public static Label createLabelWithTip(String msg) {
        Label label = new Label(msg);
        Tooltip tooltip = new Tooltip();
        tooltip.setText(StringUtil.autoNewLine(msg,20));
        label.setTooltip(tooltip);
        return label;
    }

}
