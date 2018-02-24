package application.view.venta.cruds;

import application.comunes.Alerta;
import application.model.calculo.Ingenio;
import application.model.venta.Cliente;
import application.model.venta.Organizacion;
import application.model.venta.Viaje;
import application.repository.calculo.IngenioRepository;
import application.repository.venta.ClienteRepository;
import application.repository.venta.FacturaVentaRepository;
import application.repository.venta.OrganizacionRepository;
import application.repository.venta.ViajeRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EmitirFacturaController {

    @FXML
    private ComboBox<String> clienteComboBox;
    @FXML
    private ComboBox<String> organizacionComboBox;
    @FXML
    private ComboBox<String> ingenioComboBox;
    @FXML
    private TableView<Viaje> viajeTableView;
    @FXML
    private TableColumn<Viaje, String> idRemitoColumn;
    @FXML
    private TableColumn<Viaje, String> checkColumn;
    @FXML
    private TableColumn<Viaje, String> fechaColumn;
    @FXML
    private TableColumn<Viaje, String> nombreConductorColumn;
    @FXML
    private TableColumn<Viaje, String> apellidoConductorColumn;
    @FXML
    private TableColumn<Viaje, String> fincaColumn;
    @FXML
    private TableColumn<Viaje, String> ingenioColumn;
    @FXML
    private TableColumn<Viaje, String > montoColumn;

    private ClienteRepository clienteRepository = new ClienteRepository();
    private OrganizacionRepository organizacionRepository =  new OrganizacionRepository();
    private IngenioRepository ingenioRepository = new IngenioRepository();
    private ViajeRepository viajeRepository = new ViajeRepository();
    private FacturaVentaRepository facturaVentaRepository = new FacturaVentaRepository();

    private ObservableList<Viaje> viajeData = FXCollections.observableArrayList();
    private ObservableList<Organizacion> organizacionData = FXCollections.observableArrayList();
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();
    private ObservableList<Ingenio> ingenioData = FXCollections.observableArrayList();

    private Double pesoNeto;

    private boolean isNew;
    private Stage dialogStage;
    private boolean okClicked;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }

    public boolean isOkClicked(){
        return okClicked;
    }


    @FXML
    public void initialize(){
        setClienteComboBox();
        setOrganizacionComboBox();
        setIngenioComboBox();
        buscarViajes();
        idRemitoColumn.setCellValueFactory(cellData -> cellData.getValue().idRemitoProperty().asString());
        fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        nombreConductorColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().nombreProperty());
        apellidoConductorColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().apellidoProperty());
        fincaColumn.setCellValueFactory(cellData -> cellData.getValue().getFinca().nombreProperty());
        ingenioColumn.setCellValueFactory(cellData -> cellData.getValue().getIngenio().nombreProperty());
        //montoColumn.setCellValueFactory(cellData -> cellData.getValue().montoProperty().asString());

        checkColumn.setCellValueFactory(new PropertyValueFactory<Viaje, String>("select"));
        checkColumn.setStyle( "-fx-alignment: CENTER;");
    }

    private void buscarViajes(){
        viajeData = viajeRepository.getViajesSinLiquidar();
        viajeTableView.setItems(viajeData);
    }


    private void buscarViajesByIngenioId(){//TODO: Hacer que recupere viajes por Ingenio
        viajeData = viajeRepository.view();
        viajeTableView.setItems(viajeData);
    }

    public void setClienteComboBox() {
        ObservableList<String> clienteList = FXCollections.observableArrayList();
        clienteData = clienteRepository.view();
        for (Cliente cliente : clienteData) clienteList.add(cliente.getNombre());
        clienteComboBox.setItems(clienteList);
    }

    public void setOrganizacionComboBox(){
        ObservableList<String> organizacionList = FXCollections.observableArrayList();
        organizacionData = organizacionRepository.view();
        for (Organizacion organizacion : organizacionData) organizacionList.add(organizacion.getNombreOrg());
        organizacionComboBox.setItems(organizacionList);
    }

    public void setIngenioComboBox(){
        ObservableList<String> ingenioList = FXCollections.observableArrayList();
        ingenioData = ingenioRepository.view();
        for (Ingenio ingenio : ingenioData) ingenioList.add(ingenio.getNombre());
        ingenioList.add("Todos");
        ingenioComboBox.setItems(ingenioList);
    }

    public void setViajeTableViewByIngenio(){
        if (ingenioComboBox.getSelectionModel().getSelectedItem() == "Todos"){
            buscarViajes();
        }else{
        viajeData = viajeRepository.getViajesSinLiqByIngenioId
                (ingenioData.get(ingenioComboBox.getSelectionModel().getSelectedIndex()).getIdIngenio());
        viajeTableView.setItems(viajeData);
    }

    }

    @FXML
    public void handleOk(){
        if (isInputValid()){

            emitirFactura();
            dialogStage.close();
            okClicked = true;
        }
        }

    @FXML
    private void handleCancel(){
        dialogStage.close();
        okClicked = false;
        dialogStage.close();
    }

    private void emitirFactura(){
        int idCliente = clienteData.get(clienteComboBox.getSelectionModel().getSelectedIndex()).getIdCliente();
        int idOrganizacion = organizacionData.get(organizacionComboBox.getSelectionModel().getSelectedIndex()).getIdOrganizacion();

        List<Viaje> list = new ArrayList<>();

        for (Viaje viaje : viajeTableView.getItems())
            if (viaje.getSelect().isSelected())
                list.add(viaje);

        facturaVentaRepository.emitirFactura(list, idCliente, idOrganizacion);
    }



    private boolean isInputValid() {
        String errorMessage = "";

        if (clienteComboBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Seleccione el cliente\n";
        }
        if (organizacionComboBox.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Seleccione la organización.\n";
        }

        for (Viaje viaje : viajeTableView.getItems())
            if (viaje.getSelect().isSelected())
                break;
            else
                errorMessage += "Al menos un viaje debe ser seleccionado";

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Complete los datos", errorMessage);
            return false;
        }
    }
}