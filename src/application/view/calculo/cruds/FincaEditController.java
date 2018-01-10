package application.view.calculo.cruds;

import application.comunes.Alerta;
import application.model.calculo.Finca;
import application.repository.info.FincaRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FincaEditController implements Initializable {
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField latField;
    @FXML
    private TextField longField;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private WebView webView;


    private Stage dialogStage;
    private boolean isNew;
    private Finca finca;
    private boolean okClicked = false;
    private FincaRepository repository = new FincaRepository();

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }

    @FXML
    public void handleOk(){
        if (isInputValid()){
            finca.setNombre(nombreTextField.getText());
            finca.setLatitud(Double.parseDouble(latField.getText()));
            finca.setLongitud(Double.parseDouble(longField.getText()));
            if (isNew){
                repository.save(finca);
            }else {
                repository.update(finca);
            }
            okClicked=true;
            dialogStage.close();
        }

    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setFinca(Finca finca){
        this.finca = finca;
        nombreTextField.setText(finca.getNombre());
        latField.setText(String.valueOf(finca.getLatitud()));
        longField.setText(String.valueOf(finca.getLongitud()));

    }
    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (nombreTextField.getText() == null || nombreTextField.getText().length() == 0 || nombreTextField.getText().length()>45) {
            errorMessage += "Nombre de la finca no Ingresado o ingresado incorrectamente (maximo 45 caracteres).\n";

        }
        if (latField.getText() == null || latField.getText().length() == 0) {
            errorMessage += "Latitud no Ingresada\n";
        }
        if (longField.getText() == null || longField.getText().length() == 0) {
            errorMessage += "Longitud no Ingresada correctamente.\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine engine = webView.getEngine();
        String url = "https://www.google.com";
        engine.load(url);
    }

}
