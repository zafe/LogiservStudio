package application.view.venta;

import application.Main;
import application.model.venta.FacturaVenta;
import application.model.venta.Viaje;
import application.repository.venta.FacturaVentaRepository;
import application.repository.venta.ViajeRepository;
import application.view.venta.cruds.EmitirFacturaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FacturacionController {

	//Declaracion de la tabla y columnas de la clase Liquidaciones
	@FXML
	private TableView<FacturaVenta> facturacionesTable;
	@FXML
	private TableColumn<FacturaVenta, String> idFacturacion;
	@FXML
	private TableColumn<FacturaVenta, String> fechaColumn;
	@FXML
	private TableColumn<FacturaVenta, String> clienteColumn;
	@FXML
	private TableColumn<FacturaVenta, String> montoTotal;

	//Declaracion de la tabla y columnas de la clase LiquidacionEmpleado
	@FXML
	private TableView<Viaje> viajeTableView;
	@FXML
	private TableColumn<Viaje, String> fincaColumn;
	@FXML
	private TableColumn<Viaje, String> ingenioColumn;
	@FXML
	private TableColumn<Viaje, String> fechaViajeColumn;
	@FXML
	private TableColumn<Viaje, String> montoViajeColumn;
	@FXML
	private TableColumn<Viaje, String > conductorNombreColumn;
	@FXML
	private TableColumn<Viaje, String > conductorApellidoColumn;
	@FXML
	private Button nuevaFacturaButton;

	private Stage owner;


	private ObservableList<Viaje> viajes = FXCollections.observableArrayList();
	private ObservableList<FacturaVenta> facturaVentas = FXCollections.observableArrayList();

	private FacturaVentaRepository facturacionesRepository = new FacturaVentaRepository();
	private ViajeRepository viajesRepository = new ViajeRepository();

	public void setOwner(Stage owner) {
		this.owner = owner;

	}

	private ObservableList<FacturaVenta> facturaciones = FXCollections.observableArrayList();

	public void buscarFacturaciones() {
		this.facturaciones = facturacionesRepository.view();
		facturacionesTable.setItems(facturaciones);
	}

	@FXML
	public void initialize() {
		buscarFacturaciones();
		idFacturacion.setCellValueFactory(cellData -> cellData.getValue().idFacturaVentaProperty().asString());
		fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaEmisionProperty());
		clienteColumn.setCellValueFactory(cellData -> cellData.getValue().getCliente().nombreProperty());
		//montoTotal.setCellValueFactory(cellData -> cellData.getValue().montoFacturaProperty().asString());//TODO: inicializar este dato

		fincaColumn.setCellValueFactory(cellData -> cellData.getValue().getFinca().nombreProperty());
		ingenioColumn.setCellValueFactory(cellData -> cellData.getValue().getIngenio().nombreProperty());
		fechaViajeColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		montoViajeColumn.setCellValueFactory(cellData -> cellData.getValue().montoProperty().asString());
		conductorNombreColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().nombreProperty());
		conductorApellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().apellidoProperty());
	}

	@FXML
	private void handleNew() {

			this.showEdit();


	}

	private boolean showEdit() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/venta/cruds/EmitirFactura.fxml"));
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nuevo cliente");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(owner);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			EmitirFacturaController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setIsNew(true);
			//controller.setDatos(temp);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;//controller.isOkClicked();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}


	private void cargarFacturas() {
		facturaciones = facturacionesRepository.view();
		facturacionesTable.setItems(facturaciones);
	}

	@FXML
	public void cargarViajes() {
		if (facturacionesTable.getSelectionModel().getSelectedItem() != null) {
			viajes = viajesRepository.getViajesByIdFactura(facturacionesTable.getSelectionModel().getSelectedItem()
					.getIdFacturaVenta());
			viajeTableView.setItems(viajes);
		}
	}

}