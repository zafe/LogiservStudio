package application.view.venta.cruds;

import application.comunes.Alerta;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.model.venta.Cliente;
import application.repository.info.DomicilioRepository;
import application.repository.info.LocalidadRepository;
import application.repository.info.ProvinciaRepository;
import application.repository.venta.ClienteRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClienteEditController {
    private boolean isNew;
    private Stage dialogStage;
    private boolean okClicked;
    private Cliente cliente;
    private ClienteRepository clienteRepository = new ClienteRepository();
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


    public void setDatos(Cliente cliente) {
        this.cliente = cliente;
        if (!isNew){
            nombreTextField.setText(cliente.getNombre());
            cuitTextField.setText(cliente.getCuit());
            calleTextField.setText(cliente.getDomicilio().getCalle());
            numeroTextField.setText(cliente.getDomicilio().getNumero());
            provinciaComboBox.getSelectionModel().select(cliente.getDomicilio().getLocalidad().getProvincia());
            buscarLocalidades();
            localidadComboBox.getSelectionModel().select(cliente.getDomicilio().getLocalidad());
        }
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nombreTextField.getText() == null || nombreTextField.getText().length() == 0) {
            errorMessage += "Nombre de cliente invalido\n";
        }
        if(cuitTextField.getText()==null || cuitTextField.getText().length() == 0){
            errorMessage += "CUIT no ingresado correctamente.\n";
        }
        if(calleTextField.getText()==null || calleTextField.getText().length() == 0){
            errorMessage += "Calle no ingresada correctamente.\n";
        }
        if(numeroTextField.getText()==null || numeroTextField.getText().length() == 0){
            errorMessage += "Numero no ingresado correctamente. \n";
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
                cliente = new Cliente(0,nombreTextField.getText(),cuitTextField.getText(), domicilio);
                domicilioRepository.save(domicilio);
                clienteRepository.save(cliente);
            }else {
                cliente.setNombre(nombreTextField.getText());
                cliente.setCuit(cuitTextField.getText());
                cliente.getDomicilio().setCalle(calleTextField.getText());
                cliente.getDomicilio().setNumero(numeroTextField.getText());
                cliente.getDomicilio().setLocalidad(localidadComboBox.getSelectionModel().getSelectedItem());
                cliente.getDomicilio().getLocalidad().setProvincia(provinciaComboBox.getSelectionModel().getSelectedItem());
                clienteRepository.update(cliente);
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
