package application.view.sueldo.cruds;

import application.model.info.CategoriaEmpleado;
import application.model.info.Empleado;
import application.model.sueldo.ConceptoSueldo;
import application.repository.info.CategoriaEmpleadoRepository;
import application.repository.sueldo.ConceptoSueldoRepository;
import application.repository.info.EmpleadoRepository;
import application.view.sueldo.EmpleadoALiquidar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    @FXML
    private TableColumn<ConceptoSueldo, String> codigoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> checkLiquidacionColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> conceptoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> cantidadColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoCantidadColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> factorColumn;
    @FXML
    private DatePicker fechaLiquidacion;
    @FXML
    private Button liquidarButton;
    @FXML
    private TextField factorTextField;
    @FXML
    private Button cancelButton;

    private Stage owner;
    private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private ConceptoSueldoRepository conceptoSueldoRepository=new ConceptoSueldoRepository();
    private ObservableList<CategoriaEmpleado> categorias = FXCollections.observableArrayList();
    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();
    private ObservableList<EmpleadoALiquidar> liquidarEmpleados = FXCollections.observableArrayList();
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

        checkLiquidacionColumn.setCellValueFactory(new PropertyValueFactory<ConceptoSueldo, String>("select"));
        checkLiquidacionColumn.setStyle( "-fx-alignment: CENTER;");
        codigoColumn.setCellValueFactory(cellData -> cellData.getValue().idConceptoSueldoProperty().asString());
        conceptoColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        tipoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoConceptoProperty());
        cantidadColumn.setCellValueFactory(celData -> celData.getValue().cantidadProperty().asString());
        tipoCantidadColumn.setCellValueFactory(cellData -> cellData.getValue().tipoCantidadProperty());
        factorColumn.setCellValueFactory(cellData -> cellData.getValue().factorProperty().asString());

        ponerFechaActual();
        cargarCategoriaEmpleado();
    }

    private void ponerFechaActual() {
        java.util.Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fechaLiquidacion.setValue(date);
    }

    private void cargarCategoriaEmpleado(){
        categorias = categoriaEmpleadoRepository.view();
        categoriaEmpleadoComboBox.setItems(categorias);
    }

    @FXML
    public void cargarTablaEmpleados(){

        if (categoriaEmpleadoComboBox.getSelectionModel().getSelectedItem() != null) {
            int id = categoriaEmpleadoComboBox.getSelectionModel().getSelectedItem().getIdCategoriaEmpleado();
            totalEmpleadoTableView.setItems(empleadoRepository.getEmpleadosByCategoriaEmpleado(id));
            validarListaEmpleados();
        }

    }

    private void validarListaEmpleados() {

        for (Empleado eLiquidar : liquidarEmpleadoTableView.getItems())
            for(Empleado eDuplicado : totalEmpleadoTableView.getItems())
                if (eLiquidar.getIdEmpleado() == eDuplicado.getIdEmpleado()) {
                    totalEmpleadoTableView.getItems().remove(eDuplicado);
                    break;
                }
    }

    @FXML
    private void agregarEmpleadoALiquidar(){

        if(totalEmpleadoTableView.getSelectionModel().getSelectedItem() != null) {
            liquidarEmpleadoTableView.getItems().add(totalEmpleadoTableView.getSelectionModel().getSelectedItem());
            liquidarEmpleados.add(new EmpleadoALiquidar(totalEmpleadoTableView.getSelectionModel().getSelectedItem()));
        }
        cargarTablaEmpleados();
        System.out.println("Lista Empleados a Liquidar: [agregarEmpleadoALiquidar] ");
        for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados )
            System.out.printf("Empleado a Liquidar : %s %s%n", empleadoALiquidar.getEmpleado().getNombre(),
                    empleadoALiquidar.getEmpleado().getApellido() );
    }

    @FXML
    private void agregarTodos(){

        if(!totalEmpleadoTableView.getItems().isEmpty())
        for(Empleado empleado : totalEmpleadoTableView.getItems()) {
            liquidarEmpleadoTableView.getItems().add(empleado);
            liquidarEmpleados.add(new EmpleadoALiquidar(empleado));
        }
        cargarTablaEmpleados();
    }

    @FXML
    private void quitarEmpleadoALiquidar(){

        if(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem() != null) {
            System.out.println("ID EMPLEADO A LIQUIDAR: " + liquidarEmpleadoTableView.getSelectionModel().getSelectedItem().getIdEmpleado());
            removeEmpleadoALiquidarById(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem().getIdEmpleado());
           liquidarEmpleadoTableView.getItems().remove(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem());
        }
        cargarTablaEmpleados();
        novedadesTableView.getItems().clear();
        System.out.println("Lista Empleados a Liquidar: [quitarEmpleadoALiquidar] ");
        for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados )
            System.out.printf("Empleado a Liquidar : %s %s%n", empleadoALiquidar.getEmpleado().getNombre(),
                    empleadoALiquidar.getEmpleado().getApellido() );
    }

    @FXML
    private void quitarTodos(){

        if(!liquidarEmpleadoTableView.getItems().isEmpty()) {
            liquidarEmpleadoTableView.getItems().removeAll(liquidarEmpleadoTableView.getItems());
            liquidarEmpleados.removeAll(liquidarEmpleados);
        }
        novedadesTableView.getItems().clear();
        cargarTablaEmpleados();

    }


    @FXML
    private void cargarNovedades(){
      if (liquidarEmpleadoTableView.getSelectionModel().getSelectedItem() != null) {
            novedadesTableView.setItems(liquidarEmpleados.get(getEmpleadoALiquidarById(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem().getIdEmpleado())).getConceptos());
        }
    }


    @FXML
    private void agregarFactor(){

    }

    private int getEmpleadoALiquidarById(int idEmpleadoALiquidar){
        for(EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados)
            if (empleadoALiquidar.getEmpleado().getIdEmpleado() == idEmpleadoALiquidar)
                return liquidarEmpleados.indexOf(empleadoALiquidar);

        return 0;
    }

    private void removeEmpleadoALiquidarById(int idEmpleadoALiquidar){

        for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados)
            if (empleadoALiquidar.getEmpleado().getIdEmpleado() == idEmpleadoALiquidar) {
                liquidarEmpleados.remove(empleadoALiquidar);
                break;
            }

    }


    private void actualizarEmpleadosALiquidar() {


    }



}
