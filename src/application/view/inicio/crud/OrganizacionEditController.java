package application.view.inicio.crud;

import application.comunes.Alerta;
import application.model.compra.Proveedor;
import application.model.info.Domicilio;
import application.model.info.Empleado;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.model.venta.Organizacion;
import application.repository.compra.ProveedorRepository;
import application.repository.info.DomicilioRepository;
import application.repository.info.EmpleadoRepository;
import application.repository.info.LocalidadRepository;
import application.repository.info.ProvinciaRepository;
import application.repository.venta.OrganizacionRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OrganizacionEditController implements Initializable{

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField cuitTextField;
    @FXML
    private TextField razonSocialField;
    @FXML
    private ComboBox<Empleado> apoderadoComboBox;
    @FXML
    private TextField domicilioCalleField;
    @FXML
    private TextField domicilioNumeroField;
    @FXML
    private ComboBox<Localidad> localidadComboBox;
    @FXML
    private TextField telefonoField;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private boolean isNew;
    private Stage dialogStage;
    private boolean okClicked;
    private Organizacion organizacion;
    private OrganizacionRepository organizacionRepository = new OrganizacionRepository();
    private LocalidadRepository localidadRepository = new LocalidadRepository();
    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }


    public void setDatos(Organizacion organizacion) {
        this.organizacion = organizacion;

        nombreTextField.setText(organizacion.getNombreOrg());
        cuitTextField.setText(organizacion.getCuitOrg());
//        razonSocialField.setText(organizacion.getRazonSocial());
        apoderadoComboBox.getSelectionModel().select(organizacion.getApoderadoOrg());
        domicilioCalleField.setText(organizacion.getDomicilioOrg().getCalle());
        domicilioNumeroField.setText(organizacion.getDomicilioOrg().getNumero());
        buscarLocalidades();
        localidadComboBox.getSelectionModel().select(organizacion.getDomicilioOrg().getLocalidad());
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    private boolean isInputValid() {
        /*String errorMessage = "";

        if (nombreTextField.getText() == null || nombreTextField.getText().length() == 0) {
            errorMessage += "No valid supplier name!\n";
        }
        if(cuitTextField.getText()==null || cuitTextField.getText().length() == 0){
            errorMessage += "No valid CUIT \n";
        }
        if(domicilioCalleField.getText()==null || domicilioCalleField.getText().length() == 0){
            errorMessage += "No valid street name\n";
        }
        if(domicilioNumeroField.getText()==null || domicilioNumeroField.getText().length() == 0){
            errorMessage += "No valid street number \n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }*/
        return true;
    }

    @FXML
    public void handleOk(){
        if (isInputValid()){
            organizacion.setNombreOrg(nombreTextField.getText());
            organizacion.setCuitOrg(cuitTextField.getText());
//            organizacion.setRazonSocial(razonSocialField.getText());
            organizacion.setApoderadoOrg(apoderadoComboBox.getValue());
            organizacion.getDomicilioOrg().setCalle(domicilioCalleField.getText());
            organizacion.getDomicilioOrg().setNumero(domicilioNumeroField.getText());
            organizacion.getDomicilioOrg().setLocalidad(localidadComboBox.getValue());
//            organizacion.setTelefono(telefonoField.getText());
            organizacionRepository.update(organizacion);
            }
            okClicked=true;
            dialogStage.close();
        }
    @FXML
    public void handleCancel(){
        dialogStage.close();

    }

    private void buscarLocalidades(){
       localidadComboBox.setItems(localidadRepository.view(organizacion.getDomicilioOrg().getLocalidad().getProvincia().getIdProvincia()));
    }
    private void cargarEmpleados(){
        apoderadoComboBox.setItems(empleadoRepository.buscarEmpleados());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarEmpleados();
    }
}
