package application.view.calculo.cruds;

import application.comunes.Alerta;
import application.model.calculo.Acoplado;
import application.repository.calculo.AcopladoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AcopladoEditController {

    @FXML
    private TextField marcaField;
    @FXML
    private TextField patenteField;
    @FXML
    private TextField chasisField;

    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private Stage dialogStage;
    private boolean isNew;
    private Acoplado acoplado;
    private boolean okClicked = false;
    private AcopladoRepository repository = new AcopladoRepository();

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }
    @FXML
    private	void initialize(){}

    @FXML
    public void handleOk(){
        if (isInputValid()){
            acoplado.setMarca(marcaField.getText());
            acoplado.setPatente(patenteField.getText());
            acoplado.setChasisNumero(chasisField.getText());
            if (isNew){
                repository.save(acoplado);
            }else {
                repository.update(acoplado);
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

    public void setAcoplado(Acoplado acoplado){
        this.acoplado = acoplado;
        if (!isNew){
            marcaField.setText(acoplado.getMarca());
            patenteField.setText(acoplado.getPatente());
            chasisField.setText(acoplado.getChasisNumero());
        }
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (marcaField.getText() == null || marcaField.getText().length() == 0) {
            errorMessage += "Marca no Ingresada\n";
        }
        if (patenteField.getText() == null || patenteField.getText().length() == 0 || patenteField.getText().length() > 6 ) {
            errorMessage += "Patente no Ingresado correctamente (6 digitos).\n";
        }
        if (chasisField.getText() == null || chasisField.getText().length() == 0) {
            errorMessage += "Chasis no ingresado correctamente (17 digitos).\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
}