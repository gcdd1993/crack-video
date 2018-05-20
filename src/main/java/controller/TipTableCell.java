package controller;

import javafx.scene.control.TableCell;
import utils.GUIUtil;

/**
 * Created by gaochen on 2018/5/19.
 */
public class TipTableCell<S> extends TableCell<S,String> {
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        this.setText(null);
        this.setGraphic(null);
        if (!empty) {
            this.setGraphic(GUIUtil.createLabelWithTip(item));
        }
    }
}
