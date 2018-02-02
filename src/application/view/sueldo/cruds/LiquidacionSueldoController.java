package application.view.sueldo.cruds;

import application.model.info.CategoriaEmpleado;
import application.model.info.Empleado;
import application.model.sueldo.ConceptoSueldo;
import application.repository.info.CategoriaEmpleadoRepository;
import application.repository.info.EmpleadoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
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
    private Button liquidarButton;

    @FXML
    private Button cancelButton;

    private Stage owner;
    private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();

    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();

    private ObservableList<CategoriaEmpleado> categorias = FXCollections.observableArrayList();

    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    private ObservableList<Empleado> liquidarEmpleados = FXCollections.observableArrayList();







    public void setOwner(Stage owner){
        this.owner = owner;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTotalColumn.setCellValueFactory(cellData -> cellData.getValue().idEmpleadoProperty().asString());
        apellidoTotalColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        nombreTotalColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        liquidarIdColumn.setCellValueFactory(cellData -> cellData.getValue().idEmpleadoProperty().asString());
        liquidarApellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        liquidarNombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        codigoColumn.setCellValueFactory(cellData -> cellData.getValue().idConceptoSueldoProperty().asString());
        conceptoColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        tipoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoConceptoProperty());
        cantidadColumn.setCellValueFactory(celData -> celData.getValue().tipoCantidadProperty());

        cargarCategoriaEmpleado();
    }

    private void cargarCategoriaEmpleado(){
        categorias = categoriaEmpleadoRepository.view();
        categoriaEmpleadoComboBox.setItems(categorias);
    }

    @FXML
    public void cargarTablaEmpleados(){
//        int id= categoriaEmpleadoComboBox.getSelectionModel().getSelectedItem().getIdCategoriaEmpleado();
//        empleados = empleadoRepository.getEmpleadosByCategoriaEmpleado(id);
        empleados = validarListaEmpleados();
        totalEmpleadoTableView.setItems(empleados);

    }

    private ObservableList<Empleado> validarListaEmpleados() {
//        empleados.removeAll(liquidarEmpleados);
        ObservableList<Empleado> emp = FXCollections.observableArrayList();
        emp=empleadoRepository.getEmpleadosByCategoriaEmpleado(categoriaEmpleadoComboBox.getSelectionModel().getSelectedItem().getIdCategoriaEmpleado());

        for(Empleado duplicado : liquidarEmpleados){
            System.out.println("duplicado: " + duplicado.getNombre());
            if(emp.remove(duplicado))
                System.out.println("eliminado");

        }
        return emp;
    }

    @FXML
    private void agregarEmpleadoALiquidar(){
        if (totalEmpleadoTableView.getSelectionModel().getSelectedItem() != null){
            int empleadoIndex = totalEmpleadoTableView.getSelectionModel().getFocusedIndex();
            Empleado empleadoLista = empleados.get(empleadoIndex);
            liquidarEmpleados.add(empleadoLista);
            liquidarEmpleadoTableView.setItems(liquidarEmpleados);
            totalEmpleadoTableView.getItems().remove(empleadoLista);
//            empleados.remove(empleado);

        }
    }
}
