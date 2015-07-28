package helperClasses;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GroupValidator {

    //private boolean status = true;
    private ArrayList<DataHolder> list = new ArrayList<DataHolder>();

    public void add(TextField field) {
        DataHolder dh = new DataHolder(field);
        list.add(dh);
    }

    public boolean validate() {
        boolean status = true;

        int n = list.size();

        for (int i = 0; i < n; i++) {
            if (list.get(i).field.getText() == null || list.get(i).field.getText().length() == 0) {
                list.get(i).field.setStyle("-fx-border-color: #E64B3B;\n"
                        + "    -fx-background-color: #2C3E50;\n"
                        + "    -fx-text-fill: #E64B3B;\n"
                        + "    -fx-border-radius: 90;\n"
                        + "    -fx-background-radius: 90;");
                status = false;
            }
        }

        return status;
    }

    class DataHolder {

        private TextField field;

        public DataHolder(TextField field) {

            this.field = field;
        }
    }

}
