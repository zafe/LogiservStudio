package application.view.sueldo;

import application.Main;
import application.model.info.Empleado;
import application.model.sueldo.LiquidacionEmpleado;
import application.model.sueldo.Liquidaciones;
import application.reports.AbstractJasperReports;
import application.repository.sueldo.LiquidacionesRepository;
import javafx.beans.binding.BooleanBinding;
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
	private TableView<Liquidaciones> liquidacionTable;
	@FXML
	private TableColumn<Liquidaciones, String> idLiquidacion;
	@FXML
	private TableColumn<Liquidaciones, String> fechaColumn;
	@FXML
	private TableColumn<Liquidaciones, String> hrColumn;
	@FXML
	private  TableColumn<Liquidaciones, String> hnrColumn;
	@FXML
	private TableColumn<Liquidaciones, String> retencionesColumn;

	//Declaracion de la tabla y columnas de la clase LiquidacionEmpleado
	@FXML
	private  TableView<LiquidacionEmpleado> empleadosTable;
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
	private Button imprimirRecibos;
	@FXML
	private Button imprimirReciboDeEmpleado;





	private Stage owner;

	private ObservableList<LiquidacionEmpleado> liquidacion = FXCollections.observableArrayList();

	private ObservableList<Empleado> empleados = FXCollections.observableArrayList();

	private LiquidacionesRepository liquidacionesRepository = new LiquidacionesRepository();

	private ObservableList<LiquidacionEmpleado> empleadosLiquidados = FXCollections.observableArrayList();

	public void setOwner(Stage owner){
		this.owner = owner;

	}
	private ObservableList<Liquidaciones> liquidaciones = FXCollections.observableArrayList();

	public void buscarLiquidaciones(){
		this.liquidaciones = LiquidacionesRepository.buscarLiquidaciones();
		liquidacionTable.setItems(liquidaciones);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		BooleanBinding boolenBinding = liquidacionTable.getSelectionModel().selectedItemProperty().isNull();
		imprimirRecibos.disableProperty().bind(boolenBinding);

		buscarLiquidaciones();
		idLiquidacion.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
		fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaLiquidacionProperty());
		hrColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesRemunerativosProperty().asString());
		hnrColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesNoRemunerativosProperty().asString());
		retencionesColumn.setCellValueFactory(cellData -> cellData.getValue().totalRetencionesProperty().asString());

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



			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void cargarEmpleadosLiquidados(){
		if (liquidacionTable.getSelectionModel().getSelectedItem() != null){
			empleadosLiquidados = liquidacionesRepository.getEmpleadosLiquidadosByidLiquidacion(liquidacionTable.getSelectionModel().getSelectedItem().getId());
			empleadosTable.setItems(empleadosLiquidados);
		}
	}
@FXML
	private void handleImprimirRecibos(){
		System.out.println("se imprimieron recibos de sueldo");
		int idLiquidacion = liquidacionTable.getSelectionModel().getSelectedItem().getId();
		AbstractJasperReports.createReport("src\\application\\reports\\ReciboSueldo.jasper",
				"idLiquidacion", idLiquidacion);
		AbstractJasperReports.showViewer();
}
//    @FXML
//    private void cargarLiquidaciones(){
//        if (liquidacionTable.getSelectionModel().getSelectedItem() != null){
//
//        }
//    }
}