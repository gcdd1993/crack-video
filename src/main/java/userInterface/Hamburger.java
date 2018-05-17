package userInterface;

import application.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Hamburger {
	
	private JFXDrawersStack drawersStack = new JFXDrawersStack();
	private JFXDrawer rightDrawer = new JFXDrawer();
	private JFXButton setting = new JFXButton();
	
	public JFXHamburger getHamburger(JFXHamburger hamburger , BorderPane borderpane) {

		setting.setMinSize(48, 48);
		setting.setGraphic(new ImageView(new Image(Main.IMAGES + "setting.png")));
		setting.setTooltip(new Tooltip("Setting"));
		setting.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			try {
				showSettingDialog();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		VBox vbox = new VBox();
		vbox.getChildren().addAll(setting);
		vbox.setSpacing(25);
		vbox.setId("righDrawerVbox");
		
		rightDrawer.setDirection(DrawerDirection.RIGHT);
		rightDrawer.setDefaultDrawerSize(80);
		rightDrawer.setSidePane(vbox);
		rightDrawer.setOpacity(0.5);

		borderpane.setRight(drawersStack);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			showHamburgerPane(borderpane);
		});
		
		return hamburger;
	}
	
	public void showHamburgerPane(BorderPane borderpane) {
		drawersStack.toggle(rightDrawer);
		if(rightDrawer.isClosed() || rightDrawer.isClosing()) {
			System.out.println("closed");
			borderpane.setRight(null);
		}else {
			System.out.println("open");
			borderpane.setRight(drawersStack);
		}
	}

	private Stage showSettingDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						Main.FXMLS+"Setting.fxml"
				)
		);
		Stage stage = new Stage(StageStyle.DECORATED);
		Scene scene = new Scene(
				(Pane) loader.load()
		);
		scene.getStylesheets().add(getClass().getResource(Main.CSS + "jfoenix-components.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		return stage;
	}
	
}
