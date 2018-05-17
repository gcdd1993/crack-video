package userInterface;

import application.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.GUIUtil;

import java.io.IOException;
import java.util.Optional;

public class Hamburger {
	
	private JFXDrawersStack drawersStack = new JFXDrawersStack();
	private JFXDrawer rightDrawer = new JFXDrawer();
	private JFXButton setting = new JFXButton();
	private JFXButton information = new JFXButton();

	public JFXHamburger getHamburger(JFXHamburger hamburger , BorderPane borderpane) {

		setting.setMinSize(48, 48);
		setting.setGraphic(new ImageView(new Image(Main.IMAGES + "setting.png")));
		setting.setTooltip(new Tooltip("设置"));
		setting.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			try {
				showSettingDialog();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        information.setMinSize(48, 48);
        information.setGraphic(new ImageView(new Image(Main.IMAGES + "info.png")));
        information.setTooltip(new Tooltip("关于"));
        information.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			try {
				showInfoDialog();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		VBox vbox = new VBox();
		vbox.getChildren().addAll(setting);
		vbox.getChildren().addAll(information);
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
        return GUIUtil.showDialog(Main.FXMLS+"Setting.fxml",Main.CSS + "jfoenix-components.css","设置",Optional.empty());
	}

	private Stage showInfoDialog() throws IOException {
        return GUIUtil.showDialog(Main.FXMLS+"Information.fxml",Main.CSS + "jfoenix-components.css","关于",Optional.empty());
	}


	
}
