package application.view.venta.cruds;

import application.comunes.Alerta;
import application.model.calculo.Camion;
import application.repository.info.CamionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CamionEditController {

    @FXML
    private TextField marcaField;
    @FXML
    private TextField modeloField;
    @FXML
    private TextField patenteField;

    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private Stage dialogStage;
    private boolean isNew;
    private Camion camion;
    private boolean okClicked = false;
    private CamionRepository repository = new CamionRepository();

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }
    @FXML
    private	void initialize(){}

    @FXML
    public void handleOk(){
        if (isInputValid()){
            camion.setMarca(marcaField.getText());
            camion.setModelo(modeloField.getText());
            camion.setPatente(patenteField.getText());
            if (isNew){
                repository.save(camion);
            }else {
                repository.update(camion);
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

    public void setCamion(Camion camion){
        this.camion = camion;
        marcaField.setText(camion.getMarca());
        modeloField.setText(camion.getModelo());
        patenteField.setText(camion.getPatente());
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (marcaField.getText() == null || marcaField.getText().length() == 0) {
            errorMessage += "Marca no Ingresada\n";
        }
        if (modeloField.getText() == null || modeloField.getText().length() == 0) {
            errorMessage += "Modelo no Ingresado\n";
        }
        if (patenteField.getText() == null || patenteField.getText().length() == 0 || patenteField.getText().length() > 6 ) {
            errorMessage += "Patente no Ingresado correctamente (6 digitos).\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
}
