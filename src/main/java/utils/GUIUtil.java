package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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
}
