package application.view.compra.cruds;

import application.comunes.Alerta;
import application.model.compra.Proveedor;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.repository.info.DomicilioRepository;
import application.repository.info.LocalidadRepository;
import application.repository.compra.ProveedorRepository;
import application.repository.info.ProvinciaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProveedorEditController {
    private boolean isNew;
    private Stage dialogStage;
    private boolean okClicked;
    private Proveedor proveedor;
    private ProveedorRepository proveedorRepository = new ProveedorRepository();
    private Provincia provincia;
    private ProvinciaRepository provinciaRepository = new ProvinciaRepository();
    private Localidad localidad;
    private LocalidadRepository localidadRepository = new LocalidadRepository();
    private Domicilio domicilio;
    private DomicilioRepository domicilioRepository = new DomicilioRepository();

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField cuitTextField;
    @FXML
    private TextField calleTextField;
    @FXML
    private TextField numeroTextField;
    @FXML
    private ComboBox<Provincia> provinciaComboBox;
    @FXML
    private ComboBox<Localidad> localidadComboBox;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }


    public void setDatos(Proveedor proveedor) {
        this.proveedor = proveedor;
        if (!isNew){
            nombreTextField.setText(proveedor.getNombre());
            cuitTextField.setText(proveedor.getCuit());
            calleTextField.setText(proveedor.getDomicilio().getCalle());
            numeroTextField.setText(proveedor.getDomicilio().getNumero());
            provinciaComboBox.getSelectionModel().select(proveedor.getDomicilio().getLocalidad().getProvincia());
            buscarLocalidades();
            localidadComboBox.getSelectionModel().select(proveedor.getDomicilio().getLocalidad());
        }
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nombreTextField.getText() == null || nombreTextField.getText().length() == 0) {
            errorMessage += "No valid supplier name!\n";
        }
        if(cuitTextField.getText()==null || cuitTextField.getText().length() == 0){
            errorMessage += "No valid CUIT \n";
        }
        if(calleTextField.getText()==null || calleTextField.getText().length() == 0){
            errorMessage += "No valid street name\n";
        }
        if(numeroTextField.getText()==null || numeroTextField.getText().length() == 0){
            errorMessage += "No valid street number \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
    @FXML
    public void initialize() {
        provinciaComboBox.setItems(provinciaRepository.view());
    }
    @FXML
    public void handleOk(){
        if (isInputValid()){
            if (isNew){
                localidad = localidadComboBox.getSelectionModel().getSelectedItem();
                provincia = provinciaComboBox.getSelectionModel().getSelectedItem();
                domicilio = new Domicilio(0, localidad, calleTextField.getText(), numeroTextField.getText());
                proveedor = new Proveedor(0,nombreTextField.getText(),cuitTextField.getText(), domicilio);
                domicilioRepository.save(domicilio);
                proveedorRepository.save(proveedor);
            }else {
                proveedor.setNombre(nombreTextField.getText());
                proveedor.setCuit(cuitTextField.getText());
                proveedor.getDomicilio().setCalle(calleTextField.getText());
                proveedor.getDomicilio().setNumero(numeroTextField.getText());
                proveedor.getDomicilio().setLocalidad(localidadComboBox.getSelectionModel().getSelectedItem());
                proveedor.getDomicilio().getLocalidad().setProvincia(provinciaComboBox.getSelectionModel().getSelectedItem());
                proveedorRepository.update(proveedor);
            }
            okClicked=true;
            dialogStage.close();
        }


    }
    @FXML
    public void handleCancel(){
        dialogStage.close();

    }
    @FXML
    public void buscarLocalidades(){
            Provincia provinciaSeleccionada = provinciaComboBox.getSelectionModel().getSelectedItem();
            localidadComboBox.setItems(localidadRepository.view(provinciaSeleccionada.getIdProvincia()));
    }

}
