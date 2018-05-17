package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gaochen on 2018/5/17.
 */
public class InformationController implements Initializable {

    private static String HTML = "/html/";

    @FXML
    private WebView infoWebView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoWebView.getEngine().load(getClass().getResource(HTML + "info.html").toExternalForm());
    }
}
