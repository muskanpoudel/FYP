package helperClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class NumericStringValidator implements BaseValidator {

    private TextField txtfld;

    public NumericStringValidator(final TextField fld, Boolean onTypeAlso) {
        txtfld = fld;

        if (onTypeAlso == true) {
            fld.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                Boolean status = true;

                @Override
                public void handle(KeyEvent arg0) {
                    Pattern pat = Pattern.compile("[a-zA-Z_0-9]");
                    Matcher match = pat.matcher(txtfld.getText());

                    if (fld.getText().equals("") || fld.getText().equals(null)) {
                        /*lbl.setText("**Required");
                         lbl.setVisible(true);*/
                    } else {

                        if (match.find()) { // if not matched show error

                            fld.setStyle(" -fx-border-color: #ecf0f1;\n"
                                    + "    -fx-background-color: #2C3E50;\n"
                                    + "    -fx-text-fill: #ecf0f1;\n"
                                    + "    -fx-border-radius: 90;\n"
                                    + "    -fx-background-radius: 90;");
                            status = false;
                        } else {
                            fld.setStyle("-fx-border-color: #E64B3B;\n"
                                    + "    -fx-background-color: #2C3E50;\n"
                                    + "    -fx-text-fill: #E64B3B;\n"
                                    + "    -fx-border-radius: 90;\n"
                                    + "    -fx-background-radius: 90;");
                            status = true;
                        }

                    }

                }

            });
        }

    }

    public boolean validate() {
        String txt = txtfld.getText();
        try {

            Pattern pat = Pattern.compile("[a-zA-Z_0-9]");
            Matcher match = pat.matcher(txt);
            if (txtfld.getText().equals("") || txtfld.getText().equals(null)) {

            } else {
                if (!match.find()) {
                    txtfld.setStyle(" -fx-border-color: #ecf0f1;\n"
                            + "    -fx-background-color: #2C3E50;\n"
                            + "    -fx-text-fill: #ecf0f1;\n"
                            + "    -fx-border-radius: 90;\n"
                            + "    -fx-background-radius: 90;");
                    return false;
                } else {

                    txtfld.setStyle("-fx-border-color: #E64B3B;\n"
                            + "    -fx-background-color: #2C3E50;\n"
                            + "    -fx-text-fill: #E64B3B;\n"
                            + "    -fx-border-radius: 90;\n"
                            + "    -fx-background-radius: 90;");

                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

}
