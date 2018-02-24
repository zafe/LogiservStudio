package application.view.info;

import application.model.info.Empleado;
import application.repository.info.EmpleadoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class DesempleadosController {

	@FXML
	private TableView<Empleado>  empleadoTable;
	@FXML
	private TableColumn<Empleado, String> nombreColumn;
	@FXML
	private TableColumn<Empleado, String> apellidoColumn;
	@FXML
	private TableColumn<Empleado, String> calleColumn;
	@FXML
	private TableColumn<Empleado, String> numeroColumn;
	@FXML
	private TableColumn<Empleado, String> localidadColumn;
	@FXML
	private TableColumn<Empleado, String> provinciaColumn;
	@FXML
	private TableColumn<Empleado, String> nacimientoColumn;
	@FXML
	private TableColumn<Empleado, String> cuitColumn;
	@FXML
	private TableColumn<Empleado, String> categoriaColumn;
	@FXML
	private TableColumn<Empleado, String> fechaBajaColumn;
	private Stage owner;
	private ObservableList<Empleado> empleadoData = FXCollections.observableArrayList();
	EmpleadoRepository empleadoRepository = new EmpleadoRepository();

	public void buscarEmpleados(){
		this.empleadoData = empleadoRepository.buscarEmpleadosDadosDeBaja();
		empleadoTable.setItems(empleadoData);
	}


	@FXML
	private void initialize() {
		buscarEmpleados();
		// Initialize the person table with the two columns.
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
		nacimientoColumn.setCellValueFactory(cellData -> cellData.getValue().nacimientoProperty());
		calleColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().calleProperty());
		numeroColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().numeroProperty());
		localidadColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().getLocalidad().nombreProperty());
		provinciaColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().getLocalidad().getProvincia().nombreProperty());
		cuitColumn.setCellValueFactory(cellData -> cellData.getValue().cuitProperty());
		categoriaColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoriaEmpleado().nombreProperty());
		fechaBajaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaBajaProperty());
	}
	public void setOwner(Stage owner){
		this.owner = owner;
	}
}
