package helperClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NumberValidator implements BaseValidator {

    private TextField txtfld;
    private Label lb;

    public NumberValidator(final TextField fld, Boolean onTypeAlso) {

        txtfld = fld;

        if (onTypeAlso == true) {
            fld.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                Boolean status = true;

                @Override
                public void handle(KeyEvent arg0) {
                    Pattern pat = Pattern.compile("[a-zA-Z]");
                    Matcher match = pat.matcher(txtfld.getText());

                    if (match.find()) {
                        fld.setStyle("-fx-border-color: #E64B3B;\n"
                                + "    -fx-background-color: #2C3E50;\n"
                                + "    -fx-text-fill: #E64B3B;\n"
                                + "    -fx-border-radius: 90;\n"
                                + "    -fx-background-radius: 90;");
                    } else {
                        fld.setStyle(" -fx-border-color: #ecf0f1;\n"
                                + "    -fx-background-color: #2C3E50;\n"
                                + "    -fx-text-fill: #ecf0f1;\n"
                                + "    -fx-border-radius: 90;\n"
                                + "    -fx-background-radius: 90;");
                        status = true;
                    }

                }

            });
        }

    }

    public boolean validate() {
        String txt = txtfld.getText();
        try {

            Pattern pat = Pattern.compile("[a-zA-Z]");
            Matcher match = pat.matcher(txt);

            if (match.find()) {

                txtfld.getStyleClass().remove("grouperror");
                txtfld.getStyleClass().add("error");
                lb.setText("**Only Number");
                lb.setVisible(true);
                return false;

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

}
