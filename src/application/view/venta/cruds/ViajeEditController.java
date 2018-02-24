package application.view.venta.cruds;

import application.model.calculo.Camion;
import application.model.calculo.Finca;
import application.model.calculo.Ingenio;
import application.model.info.Empleado;
import application.model.venta.Viaje;
import application.repository.calculo.CamionRepository;
import application.repository.calculo.FincaRepository;
import application.repository.calculo.IngenioRepository;
import application.repository.calculo.OrigenDestinoRepository;
import application.repository.info.EmpleadoRepository;
import application.repository.venta.ViajeRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ViajeEditController {

    @FXML
    private TableView<Finca> origenTable;
    @FXML
    private TableView<Ingenio> destinoTable;
    @FXML
    private TableColumn<Finca, String > fincaColumn;
    @FXML
    private TableColumn<Ingenio, String> ingenioNombreColumn;
    @FXML
    private TableColumn<Ingenio, String> arranqueColumn;
    @FXML
    private TableColumn<Ingenio, String> tarifaColumn;
    @FXML
    private DatePicker diaPicker;
    @FXML
    private ComboBox<String> horaCombo;
    @FXML
    private ComboBox<String> minutosCombo;
    @FXML
    private TextField taraTextField;
    @FXML
    private TextField brutoTextField;
    @FXML
    private ComboBox<String> conductorCombo;
    @FXML
    private ComboBox<String> camionCombo;
    @FXML
    private Label pesoNetoLabel;
    @FXML
    private Label distanciaLabel;
    @FXML
    private Label montoLabel;

    private boolean isNew;
    private FincaRepository fincaRepository = new FincaRepository();
    private IngenioRepository ingenioRepository = new IngenioRepository();
    private EmpleadoRepository conductorRepository = new EmpleadoRepository();
    private OrigenDestinoRepository origenDestinoRepository = new OrigenDestinoRepository();
    private CamionRepository camionRepository = new CamionRepository();
    private ViajeRepository viajeRepository = new ViajeRepository();
    private Viaje viaje;

    private Stage dialogStage;
    private boolean okClicked;

    private ObservableList<Finca> fincaData = FXCollections.observableArrayList();
    private ObservableList<Ingenio> ingenioData = FXCollections.observableArrayList();
    private ObservableList<Empleado> conductorData = FXCollections.observableArrayList();
    private ObservableList<Camion> camionData = FXCollections.observableArrayList();

    List<Empleado> conductoresList = conductorRepository.getEmpleadosByCategoriaEmpleado(2);// todo Hardcodeado
    List<Finca> fincasList = fincaRepository.view();
    List<Ingenio> ingeniosList = ingenioRepository.view();
    List<Camion> camionList = camionRepository.view();
    public void setIsNew(boolean aNew){this.isNew = aNew;}



    @FXML
    private void initialize(){
        setHoraCombo(); setMinutosCombo();
        fincaColumn.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
        ingenioNombreColumn.setCellValueFactory(cellData->cellData.getValue().nombreProperty());
        arranqueColumn.setCellValueFactory(cellData->cellData.getValue().arranqueProperty().asString());
        tarifaColumn.setCellValueFactory(cellData->cellData.getValue().tarifaProperty().asString());
        brutoTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setPesoNetoLabel();
                setMontoLabel();
            }
        });
        taraTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setPesoNetoLabel();
                setMontoLabel();
            }
        });

        origenTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarIngenioFinca();
                setDistanciaLabel();
                setMontoLabel();
            }
        });

        destinoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualizarIngenioFinca();
                setDistanciaLabel();
                setMontoLabel();
            }
        });
        setConductorComboBox();
        setCamionComboBox();
        setFincaTabla();
        setIngenioTabla();
    }

    public void setFincaTabla(){
        this.fincaData = fincaRepository.view();
        origenTable.setItems(fincaData);
    }

    public void setIngenioTabla(){
        this.ingenioData = ingenioRepository.view();
        destinoTable.setItems(ingenioData);
    }


    public void setConductorComboBox(){
        ObservableList<String> conList = FXCollections.observableArrayList();
        conductorData = conductorRepository.getEmpleadosByCategoriaEmpleado(2);
        for (Empleado conductor : conductoresList) conList.add(conductor.getNombre() + " " + conductor.getApellido());
        conductorCombo.setItems(conList);
    }

    private void setCamionComboBox(){
        ObservableList<String> camionList = FXCollections.observableArrayList();
        camionData = camionRepository.view();
        for (Camion camion : camionData)
            camionList.add(camion.getMarca() + " " + camion.getModelo() + " - Patente: " + camion.getPatente());
        camionCombo.setItems(camionList);
    }

   public void setHoraCombo(){
        ObservableList<String> horaList = FXCollections.observableArrayList();
        for(int i = 0; i < 24 ; i++)
            horaList.add( i < 10 ? "0"+i : ""+i);
        horaCombo.setItems(horaList);
   }

    public void setMinutosCombo(){
        ObservableList<String> minutosList = FXCollections.observableArrayList();
        for(int i = 0; i < 60 ; i++)
            minutosList.add( i < 10 ? "0"+i : ""+i);
        minutosCombo.setItems(minutosList);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setViaje(Viaje viaje){
        this.viaje = viaje;
        String s = (viaje.getHoraEntrada() != null ? viaje.getHoraEntrada() : "00:00");
        String[] tokens = s.split(":");
        horaCombo.getSelectionModel().select(tokens[0]);//todo hardcodeado
        minutosCombo.getSelectionModel().select(tokens[1]);//todo harcodeado
        brutoTextField.setText(String.valueOf(viaje.getBruto()));
        taraTextField.setText(String.valueOf(viaje.getTara()));

        if (viaje.getFecha() != null){

            //Adapto el formato a la forma en que se ven las fechas en la base de datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            String nacimiento = viaje.getFecha();

            //convert String to LocalDate
            LocalDate diaNacimiento = LocalDate.parse(nacimiento, formatter);

            diaPicker.setValue(diaNacimiento);
        }

        //Seleccionar Finca
        if (viaje.getFinca() != null)
        for (int fincaIndex = 0; fincaIndex < fincaData.size() ; fincaIndex++)
           if (fincaData.get(fincaIndex).getIdFinca() == viaje.getFinca().getIdFinca()) {
                origenTable.getSelectionModel().select(fincaIndex);
                break;
        }

        //Seleccionar Ingenio
        if (viaje.getIngenio() != null)
        for (int ingenioIndex = 0; ingenioIndex < ingenioData.size() ; ingenioIndex++)
            if (ingenioData.get(ingenioIndex).getIdIngenio() == viaje.getIngenio().getIdIngenio()) {
                destinoTable.getSelectionModel().select(ingenioIndex);
                break;
            }

        //Seleccionar Conductor
        if (viaje.getConductor() != null)
        for (int conductorIndex = 0; conductorIndex < conductorData.size() ; conductorIndex++)
            if (conductorData.get(conductorIndex).getIdEmpleado() == viaje.getConductor().getIdEmpleado()){
                conductorCombo.getSelectionModel().select(conductorIndex);
                break;
            }

        //Seleccionar Camion
        if (viaje.getCamion() != null)
            for (int camionIndex = 0; camionIndex < camionData.size() ; camionIndex++)
                if(camionData.get(camionIndex).getId()  == viaje.getCamion().getId()){
                camionCombo.getSelectionModel().select(camionIndex);
                break;
                }

        distanciaLabel.setText(viaje.getDistanciaRecorrida().isEmpty() ? "0" : viaje.getDistanciaRecorrida() + " km");

    }

    public void setPesoNetoLabel() {
        if(!brutoTextField.getText().isEmpty() && Double.parseDouble(brutoTextField.getText()) > 0 &&
           !taraTextField.getText().isEmpty()  && Double.parseDouble(taraTextField.getText()) > 0 &&
           Double.parseDouble(brutoTextField.getText()) > Double.parseDouble(taraTextField.getText()) ) {
            BigDecimal bruto = BigDecimal.valueOf(Double.parseDouble(brutoTextField.getText()));
            BigDecimal tara = BigDecimal.valueOf(Double.parseDouble(taraTextField.getText()));
            BigDecimal neto = bruto.subtract(tara);
            this.pesoNetoLabel.setText(String.valueOf(neto));
        }else {
            this.pesoNetoLabel.setText("");
        }
    }

    public void setMontoLabel(){

            if ( !pesoNetoLabel.getText().isEmpty() &&  Double.parseDouble(pesoNetoLabel.getText()) > 0){

            BigDecimal distancia = BigDecimal.valueOf(Double.valueOf(viaje.getDistanciaRecorrida()));
            BigDecimal pesoNeto = BigDecimal.valueOf(Double.parseDouble(pesoNetoLabel.getText()));
            BigDecimal precioKm = BigDecimal.valueOf(viaje.getIngenio().getTarifa());//todo cambiar metodo p/ cambiar ingenio automat...
            BigDecimal arranque = BigDecimal.valueOf(viaje.getIngenio().getArranque());
            BigDecimal precioUnitario = distancia.multiply(precioKm).add(arranque);
            BigDecimal montoViaje = precioUnitario.multiply(pesoNeto).multiply(BigDecimal.valueOf(0.001));
            montoLabel.setText("$ " + String.valueOf(montoViaje));
        }

    }

    public void setDistanciaLabel(){//todo crear metodo para actualizar los objetos ingenio y finca cada vez que se seleccione uno de la tabla

        if (origenTable.getSelectionModel().getSelectedItem() != null &&
            destinoTable.getSelectionModel().getSelectedItem() != null){
            Double distancia = origenDestinoRepository.getDistanciaByIds(viaje.getFinca().getIdFinca(),
                    viaje.getIngenio().getIdIngenio());
            this.distanciaLabel.setText(String.valueOf(distancia));
            viaje.setDistanciaRecorrida(String.valueOf(distancia));
        }

    }

    /**
     * Metodo que actualiza las instancias de ingenio y finca cada vez que el usuario selecciona
     * un elemento en la tabla origen (Finca) o destino (Ingenio)
     */
    private void actualizarIngenioFinca(){

        //Actualización de la finca
        if (origenTable.getSelectionModel().getSelectedItem() != null)
            viaje.setFinca(fincaData.get(origenTable.getSelectionModel().getSelectedIndex()));

        //Actualización del Ingenio
        if (destinoTable.getSelectionModel().getSelectedItem() != null)
            viaje.setIngenio(ingenioData.get(destinoTable.getSelectionModel().getSelectedIndex()));



    }

    public boolean isOkClicked(){return okClicked;}

    @FXML
    private void handleOk(){
        if (isInputValid()){
            viaje.setBruto(Double.parseDouble(brutoTextField.getText()));
            viaje.setTara(Double.parseDouble(taraTextField.getText()));
            viaje.setHoraEntrada(horaCombo.getSelectionModel().getSelectedItem().concat(":").
                    concat(minutosCombo.getSelectionModel().getSelectedItem()));
            //Set Fecha de Nacimiento del Empleado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            viaje.setFecha(diaPicker.getValue().format(formatter));
            //Set Conductor
            viaje.setConductor(conductoresList.get(conductorCombo.getSelectionModel().getSelectedIndex()));
            //Set Camion
            viaje.setCamion(camionList.get(camionCombo.getSelectionModel().getSelectedIndex()));
            //Set idOrigen_Destino
            int origenDestino = origenDestinoRepository.getIdByFincaIngenio(viaje.getFinca().getIdFinca(),
                    viaje.getIngenio().getIdIngenio());
            if (isNew)
                viajeRepository.save2(viaje,origenDestino);
            else
                viajeRepository.update2(viaje, origenDestino);

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    private boolean isInputValid(){
        return true;
    }

}
