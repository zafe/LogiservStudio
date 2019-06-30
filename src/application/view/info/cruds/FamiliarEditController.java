package application.view.info.cruds;

import application.comunes.Alerta;
import application.model.info.Familiar;
import application.repository.info.FamiliarRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FamiliarEditController implements Initializable{
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidoTextField;
    @FXML
    private DatePicker nacimientoPicker;
    @FXML
    private ComboBox<String> parentescoComboBox;
    @FXML
    private Label nombreEmpleadoLabel;
    @FXML
    private Label apellidoEmpleadoLabel;
    @FXML
    private Label legajoEmpleadoLabel;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;


    private Stage dialogStage;
    private boolean isNew;
    private Familiar familiar;
    private boolean okClicked = false;
    private FamiliarRepository repository = new FamiliarRepository();



    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }


    public void setDatos(Familiar datos) {
        familiar = datos;
        legajoEmpleadoLabel.setText(String.valueOf(familiar.getEmpleado().getIdEmpleado()));
        apellidoEmpleadoLabel.setText(familiar.getEmpleado().getApellido());
        nombreEmpleadoLabel.setText(familiar.getEmpleado().getNombre());
        if (!isNew){
            nombreTextField.setText(familiar.getNombre());
            apellidoTextField.setText(familiar.getApellido());
            nacimientoPicker.setValue(LocalDate.parse(familiar.getFechaNacimiento()));
            parentescoComboBox.getSelectionModel().select(familiar.getParentesco());
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    private void cargarComboBox(){
        ObservableList<String> parentescos = FXCollections.observableArrayList("Esposo/a", "Hijo/a", "Otro");
        parentescoComboBox.setItems(parentescos);
}


    @FXML
    public void handleOk() {
        if (isInputValid()) {
            familiar.setNombre(nombreTextField.getText());
            familiar.setApellido(apellidoTextField.getText());
            familiar.setFechaNacimiento(nacimientoPicker.getValue().toString());
            familiar.setParentesco(parentescoComboBox.getSelectionModel().getSelectedItem());
            if (isNew) {
                repository.save(familiar);
            } else {
                repository.update(familiar);
            }
            okClicked = true;
            dialogStage.close();
        }

    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    public boolean isInputValid() {
        String errorMessage = "";
        if (nombreTextField.getText() == null || nombreTextField.getText().length() == 0) {
            errorMessage += "Nombre no ingresado.\n";
        }
        if (apellidoTextField.getText() == null || apellidoTextField.getText().length() == 0) {
            errorMessage += "Apellido no ingresado.\n";
        }
        if (nacimientoPicker.getValue().toString() == null || nacimientoPicker.getValue().toString().length() == 0) {
            errorMessage += "Fecha no ingresada.\n";
        }
        if (parentescoComboBox.getSelectionModel().isEmpty())
            errorMessage += "Parentesco no ingresado.\n";
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarComboBox();
    }
}
