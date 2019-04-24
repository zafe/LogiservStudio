package application.view.calculo.cruds;

import application.comunes.Alerta;
import application.model.calculo.Ingenio;
import application.repository.calculo.IngenioRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class IngenioEditController implements Initializable {
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField latField;
    @FXML
    private TextField longField;
    @FXML
    private TextField arranqueField;
    @FXML
    private TextField tarifaField;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private WebView webView;


    private Stage dialogStage;
    private boolean isNew;
    private Ingenio ingenio;
    private boolean okClicked = false;
    private IngenioRepository repository = new IngenioRepository();

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }

    @FXML
    public void handleOk(){
        if (isInputValid()){
            ingenio.setNombre(nombreTextField.getText());
            ingenio.setLatitud(Double.parseDouble(latField.getText()));
            ingenio.setLongitud(Double.parseDouble(longField.getText()));
            ingenio.setArranque(Double.parseDouble(arranqueField.getText()));
            ingenio.setTarifa(Double.parseDouble(tarifaField.getText()));
            if (isNew){
                repository.save(ingenio);
            }else {
                repository.update(ingenio);
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

    public void setIngenio(Ingenio ingenio){
        this.ingenio = ingenio;
        nombreTextField.setText(ingenio.getNombre());
        latField.setText(String.valueOf(ingenio.getLatitud()));
        longField.setText(String.valueOf(ingenio.getLongitud()));
        tarifaField.setText(String.valueOf(ingenio.getTarifa()));
        arranqueField.setText(String.valueOf(ingenio.getArranque()));

    }
    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {

        String errorMessage = "";
        if (nombreTextField.getText() == null || nombreTextField.getText().length() == 0 || nombreTextField.getText().length()>45) {
            errorMessage += "Nombre de ingenio no Ingresado o ingresado incorrectamente (maximo 45 caracteres).\n";
        }
        if (latField.getText() == null || latField.getText().length() == 0) {
            errorMessage += "Latitud no Ingresada\n";
        }
        if (longField.getText() == null || longField.getText().length() == 0) {
            errorMessage += "Longitud no Ingresada correctamente.\n";
        }
        if (tarifaField.getText() == null || tarifaField.getText().length() == 0 || !NumberUtils.isParsable(tarifaField.getText())) {
            errorMessage += "tarifa no Ingresada correctamente.\n";
        }
        if (arranqueField.getText() == null || arranqueField.getText().length() == 0 || !NumberUtils.isParsable(arranqueField.getText())) {
            errorMessage += "Arranque no Ingresado correctamente.\n";
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
        String url = "https://www.google.com.ar/maps";
        engine.load(url);
    }

}
