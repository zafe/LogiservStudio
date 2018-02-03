package application.view.sueldo.cruds;

import application.model.info.CategoriaEmpleado;
import application.model.info.Empleado;
import application.model.sueldo.ConceptoSueldo;
import application.repository.info.CategoriaEmpleadoRepository;
import application.repository.info.ConceptoSueldoRepository;
import application.repository.info.EmpleadoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class LiquidacionSueldoController implements Initializable {
    @FXML
    private DatePicker desdeDatePicker;
    @FXML
    private DatePicker hastaDatePicker;
    @FXML
    private ComboBox<CategoriaEmpleado> categoriaEmpleadoComboBox;
    @FXML
    private TableView<Empleado> totalEmpleadoTableView;
    @FXML
    private TableColumn<Empleado, String> idTotalColumn;
    @FXML
    private TableColumn<Empleado, String> nombreTotalColumn;
    @FXML
    private TableColumn<Empleado, String> apellidoTotalColumn;
    @FXML
    private TableView<Empleado> liquidarEmpleadoTableView;
    @FXML
    private TableColumn<Empleado, String> liquidarIdColumn;
    @FXML
    private TableColumn<Empleado, String> liquidarNombreColumn;
    @FXML
    private TableColumn<Empleado, String> liquidarApellidoColumn;

    @FXML
    private Button addEmpleadoButton;
    @FXML
    private Button subsEmpleadoButton;

    @FXML
    private TableView<ConceptoSueldo> novedadesTableView;
//    @FXML
//    private TableColumn<ConceptoSueldo, String> checkLiquidacionColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> codigoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> conceptoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> cantidadColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoCantidadColumn;
    @FXML
    private DatePicker fechaLiquidacion;
    @FXML
    private Button liquidarButton;

    @FXML
    private Button cancelButton;

    private Stage owner;
    private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private ConceptoSueldoRepository conceptoSueldoRepository=new ConceptoSueldoRepository();
    private ObservableList<CategoriaEmpleado> categorias = FXCollections.observableArrayList();
    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();
    private ObservableList<Empleado> liquidarEmpleados = FXCollections.observableArrayList();
    private ObservableList<ConceptoSueldo> conceptoSueldos = FXCollections.observableArrayList();









    public void setOwner(Stage owner){
        this.owner = owner;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        novedadesTableView.setPlaceholder(new Label("No hay novedades para mostrar"));
        totalEmpleadoTableView.setPlaceholder(new Label("No hay empleados para mostrar"));
        liquidarEmpleadoTableView.setPlaceholder(new Label("No hay empleados a liquidar"));
        idTotalColumn.setCellValueFactory(cellData -> cellData.getValue().idEmpleadoProperty().asString());
        apellidoTotalColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        nombreTotalColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        liquidarIdColumn.setCellValueFactory(cellData -> cellData.getValue().idEmpleadoProperty().asString());
        liquidarApellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        liquidarNombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        codigoColumn.setCellValueFactory(cellData -> cellData.getValue().idConceptoSueldoProperty().asString());
        conceptoColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        tipoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoConceptoProperty());
        cantidadColumn.setCellValueFactory(celData -> celData.getValue().cantidadProperty().asString());
        tipoCantidadColumn.setCellValueFactory(cellData -> cellData.getValue().tipoCantidadProperty());
        ponerFechaActual();
        cargarCategoriaEmpleado();
    }

    private void cargarCategoriaEmpleado(){
        categorias = categoriaEmpleadoRepository.view();
        categoriaEmpleadoComboBox.setItems(categorias);
    }

    @FXML
    public void cargarTablaEmpleados(){
        int id= categoriaEmpleadoComboBox.getSelectionModel().getSelectedItem().getIdCategoriaEmpleado();
        empleados = empleadoRepository.getEmpleadosByCategoriaEmpleado(id);
        validarListaEmpleados();
    }

    private void validarListaEmpleados() {
        for(Empleado empleadoLiquidado : liquidarEmpleados){
            for (Empleado empleadoDuplicado : empleados){
                if (empleadoDuplicado.getIdEmpleado() == empleadoLiquidado.getIdEmpleado()){
                    empleados.remove(empleadoDuplicado);
                    break;
                }
            }
        }
        totalEmpleadoTableView.setItems(empleados);
    }

    @FXML
    private void agregarEmpleadoALiquidar(){
        if (totalEmpleadoTableView.getSelectionModel().getSelectedItem() != null){
            int empleadoIndex = totalEmpleadoTableView.getSelectionModel().getFocusedIndex();
            Empleado empleadoLista = empleados.get(empleadoIndex);
            liquidarEmpleados.add(empleadoLista);
            liquidarEmpleadoTableView.setItems(liquidarEmpleados);
            totalEmpleadoTableView.getItems().remove(empleadoLista);
        }
    }
    @FXML
    private void quitarEmpleadoALiquidar(){
        if (liquidarEmpleadoTableView.getSelectionModel().getSelectedItem() != null){
            liquidarEmpleadoTableView.getItems().remove(liquidarEmpleadoTableView.getSelectionModel().getSelectedIndex());
            cargarTablaEmpleados();
            novedadesTableView.getItems().removeAll(conceptoSueldos);
        }
    }
    @FXML
    private void cargarNovedades(){
        if (liquidarEmpleadoTableView.getSelectionModel().getSelectedItem() != null){
            conceptoSueldos = conceptoSueldoRepository.getConceptosByEmpleadoId(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem().getIdEmpleado());
            novedadesTableView.setItems(conceptoSueldos);
        }
    }

    @FXML
    private void agregarTodos(){
       //todo: hacer
    }
    @FXML
    private void quitarTodos(){
        //todo: hacer
    }
    private void ponerFechaActual() {
        java.util.Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fechaLiquidacion.setValue(date);
    }




}
