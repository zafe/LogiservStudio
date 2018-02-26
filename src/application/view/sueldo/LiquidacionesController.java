package application.view.sueldo;

import application.Main;
import application.model.info.Empleado;
import application.model.sueldo.LiquidacionEmpleado;
import application.model.sueldo.Liquidacion;
import application.repository.sueldo.LiquidacionEmpleadoRepository;
import application.repository.sueldo.LiquidacionRepository;
import application.view.sueldo.cruds.LiquidacionSueldoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LiquidacionesController implements Initializable{

	//Declaracion de la tabla y columnas de la clase Liquidaciones
	@FXML
	private TableView<Liquidacion> liquidacionTable;
	@FXML
	private TableColumn<Liquidacion, String> idLiquidacion;
	@FXML
	private TableColumn<Liquidacion, String> fechaColumn;
	@FXML
	private TableColumn<Liquidacion, String> hrColumn;
	@FXML
	private  TableColumn<Liquidacion, String> hnrColumn;
	@FXML
	private TableColumn<Liquidacion, String> retencionesColumn;

	//Declaracion de la tabla y columnas de la clase LiquidacionEmpleado
	@FXML
	private  TableView<LiquidacionEmpleado> liqEmpleadosTable;
	@FXML
	private TableColumn<LiquidacionEmpleado, String> legajoColumn;
	@FXML
	private TableColumn<LiquidacionEmpleado, String> apellidoColumn;
	@FXML
	private TableColumn<LiquidacionEmpleado, String> nombreColumn;
	@FXML
	private TableColumn<LiquidacionEmpleado, String> categoriaColumn;
	@FXML
	private TableColumn<LiquidacionEmpleado, String> hrEmpleadoColumn;
	@FXML
	private TableColumn<LiquidacionEmpleado, String> hnrEmpleadoColumn;
	@FXML
	private TableColumn<LiquidacionEmpleado, String> retencionesEmpleadoColumn;
	@FXML
	private Button nuevaLiquidacionButton;
	@FXML
	private Button reportesButton;





	private Stage owner;

	private ObservableList<LiquidacionEmpleado> liquidacionesEmpleado = FXCollections.observableArrayList();

	private ObservableList<Empleado> empleados = FXCollections.observableArrayList();

	private LiquidacionRepository liquidacionesRepository = new LiquidacionRepository();

	private LiquidacionEmpleadoRepository liquidacionEmpleadoRepository = new LiquidacionEmpleadoRepository();

	private ObservableList<LiquidacionEmpleado> empleadosLiquidados = FXCollections.observableArrayList();

	public void setOwner(Stage owner){
		this.owner = owner;

	}
	private ObservableList<Liquidacion> liquidaciones = FXCollections.observableArrayList();

	public void buscarLiquidaciones(){
		this.liquidaciones = LiquidacionRepository.buscarLiquidaciones();
		liquidacionTable.setItems(liquidaciones);
	}

	private void buscarLiquidacionesEmpleado(int idLiquidacion){
		this.liquidacionesEmpleado = liquidacionEmpleadoRepository.getLiqEmpleadoByIdLiq(idLiquidacion);
		liqEmpleadosTable.setItems(liquidacionesEmpleado);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buscarLiquidaciones();
		idLiquidacion.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
		fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaLiquidacionProperty());
		hrColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesRemunerativosProperty().asString());
		hnrColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesNoRemunerativosProperty().asString());
		retencionesColumn.setCellValueFactory(cellData -> cellData.getValue().totalRetencionesProperty().asString());

		liquidacionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				buscarLiquidacionesEmpleado(liquidacionTable.getSelectionModel().getSelectedItem().getId());
			}
		});

		legajoColumn.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().idEmpleadoProperty().asString());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().apellidoProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().nombreProperty());
		categoriaColumn.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().getCategoriaEmpleado().nombreProperty());
		hrEmpleadoColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesRemunerativosProperty().asString());
		hnrEmpleadoColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesNoRemunerativosProperty().asString());
		retencionesEmpleadoColumn.setCellValueFactory(cellData -> cellData.getValue().totalRetencionesProperty().asString());


	}

	@FXML
	private void handleNew(){
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/sueldo/cruds/LiquidacionSueldo.fxml"));
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nueva Liquidacion");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(owner);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			LiquidacionSueldoController controller = loader.getController();
			controller.setOwner(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	@FXML
	public void cargarEmpleadosLiquidados(){
		if (liquidacionTable.getSelectionModel().getSelectedItem() != null){
			empleadosLiquidados = liquidacionesRepository.getEmpleadosLiquidadosByidLiquidacion(liquidacionTable.getSelectionModel().getSelectedItem().getId());
			liqEmpleadosTable.setItems(empleadosLiquidados);
		}
	}
*/
//    @FXML
//    private void cargarLiquidaciones(){
//        if (liquidacionTable.getSelectionModel().getSelectedItem() != null){
//
//        }
//    }
}