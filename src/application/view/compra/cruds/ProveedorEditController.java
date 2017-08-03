package application.view.compra.cruds;

import application.comunes.Alerta;
import application.model.compra.Proveedor;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.repository.info.DomicilioRepository;
import application.repository.info.LocalidadRepository;
import application.repository.info.ProveedorRepository;
import application.repository.info.ProvinciaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    private ComboBox<String> provinciaComboBox;
    @FXML
    private ComboBox<String> localidadComboBox;
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


    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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
            localidad = getLocalidad();
            domicilio = getDomicilio();
            proveedor = getProveedor();
            if (isNew){
                domicilioRepository.save(domicilio,localidad.getIdLocalidad());
                proveedorRepository.save(proveedor);
            }else {
//                proveedorRepository.update(proveedor);
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
            String provinciaSeleccionada = provinciaComboBox.getSelectionModel().getSelectedItem();
            provincia = provinciaRepository.search(provinciaSeleccionada);
            localidadComboBox.setItems(localidadRepository.view(provincia.getIdProvincia()));
    }

    public Localidad getLocalidad(){
        Localidad localidad;
        String localidadSeleccionada = localidadComboBox.getSelectionModel().getSelectedItem();
        localidad= localidadRepository.search(localidadSeleccionada);
        return localidad;
    }

    public Proveedor getProveedor(){
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombreTextField.getText());
        proveedor.setCuit(cuitTextField.getText());
        proveedor.setIdLocalidad(getLocalidad().getIdLocalidad());
        return proveedor;
    }
    public Domicilio getDomicilio(){
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(calleTextField.getText());
        domicilio.setNumero(numeroTextField.getText());
        return domicilio;
    }



}
