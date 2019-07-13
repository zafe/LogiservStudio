package application.view.calculo.cruds;

import application.comunes.Alerta;
import application.model.calculo.CargaCombustible;
import application.model.compra.FacturaCompra;
import application.model.info.Empleado;
import application.model.calculo.Camion;
import application.repository.calculo.CamionRepository;
import application.repository.calculo.CargaCombustibleRepository;
import application.repository.info.EmpleadoRepository;
import application.view.info.cruds.EmpleadoEditDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.repository.calculo.CargaCombustibleRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CargaCombustibleEditController {

    @FXML
    private DatePicker fechaDatePicker;
    @FXML
    private TextField horaTextField;
    @FXML
    private TextField litrosTextField;
    @FXML
    private ComboBox<String> horaCombo;
    @FXML
    private ComboBox<String> minutosCombo;
    @FXML
    private ComboBox<String> camionComboBox;
    @FXML
    private ComboBox<Empleado> conductorComboBox;
    @FXML
    private ComboBox<FacturaCompra> facturaCompraComboBox;



    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private Stage dialogStage;
    private boolean isNew;
    private CargaCombustible cargaCombustible;
    private boolean okClicked = false;
    private CargaCombustibleRepository repository = new CargaCombustibleRepository();
    private CamionRepository camionRepository = new CamionRepository();

    private ObservableList<Empleado> conductorData = FXCollections.observableArrayList();
    private EmpleadoRepository conductorRepository = new EmpleadoRepository();
    private ObservableList<Camion> camionData = FXCollections.observableArrayList();

    List<Empleado> conductoresList = conductorRepository.getConductores();
    List<Camion> camionList = camionRepository.view();

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }
    @FXML
    private	void initialize(){
        setConductorComboBox();
        setCamionComboBox();
    }

    @FXML
    public void handleOk(){
        if (isInputValid()){
            cargaCombustible.setFechaCarga(fechaDatePicker.toString());
            cargaCombustible.setHoraCarga(horaTextField.getText());
            cargaCombustible.setCantidadLitros(Double.parseDouble(litrosTextField.getText()));
            if (isNew){
                //todo repository.save(cargaCombustible);
            }else {
                //todo repository.update(cargaCombustible);
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

    public void setCargaCombustible(CargaCombustible cargaCombustible){
        this.cargaCombustible = cargaCombustible;
        if (!isNew){
            //todo fechaDatePicker.setd;
            horaTextField.setText(cargaCombustible.getHoraCarga());
            litrosTextField.setText(String.valueOf(cargaCombustible.getCantidadLitros()));
            //todo camionComboBox.setText
        }

        if (cargaCombustible.getFechaCarga() != null){

            //Adapto el formato a la forma en que se ven las fechas en la base de datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            String fechaCarga = cargaCombustible.getFechaCarga();

            //convert String to LocalDate
            LocalDate fechaCargaLocalDate = LocalDate.parse(fechaCarga, formatter);

            fechaDatePicker.setValue(fechaCargaLocalDate);
        }

        //Seleccionar Conductor
        if (cargaCombustible.getConductor() != null)
            for (int conductorIndex = 0; conductorIndex < conductorData.size() ; conductorIndex++)
                if (conductorData.get(conductorIndex).getIdEmpleado() == cargaCombustible.getConductor().getIdEmpleado()){
                    conductorComboBox.getSelectionModel().select(conductorIndex);
                    break;
                }

        //Seleccionar Camion
        if (cargaCombustible.getCamion() != null)
            for (int camionIndex = 0; camionIndex < camionData.size() ; camionIndex++)
                if(camionData.get(camionIndex).getId()  == cargaCombustible.getCamion().getId()){
                    camionComboBox.getSelectionModel().select(camionIndex);
                    break;
                }


    }
    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (horaTextField.getText() == null || horaTextField.getText().length() == 0) {
            errorMessage += "Marca no Ingresada\n";
        }
        if (litrosTextField.getText() == null || litrosTextField.getText().length() == 0) {
            errorMessage += "Patente no Ingresado correctamente (6 digitos).\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }

    public void setConductorComboBox(){
        conductorData = conductorRepository.getConductores();
        conductorComboBox.setItems(conductorData);
    }

    private void setCamionComboBox(){
        ObservableList<String> camionList = FXCollections.observableArrayList();
        camionData = camionRepository.view();
        for (Camion camion : camionData)
            camionList.add(camion.getMarca() + " " + camion.getModelo() + " - Patente: " + camion.getPatente());
        camionComboBox.setItems(camionList);
    }


}
