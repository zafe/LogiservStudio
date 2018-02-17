package application.view.compra;

import application.Main;
import application.comunes.Alerta;
import application.model.compra.DetalleCompra;
import application.model.compra.FacturaCompra;
import application.repository.compra.DetalleCompraRepository;
import application.repository.compra.FacturaCompraRepository;
import application.view.compra.cruds.FacturaEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CompraComprasController {

	@FXML
	private TableView<FacturaCompra> comprasTable;
	@FXML
	private TableColumn<FacturaCompra, String> codigoFacturaColumn;
	@FXML
	private TableColumn<FacturaCompra, String> fechaColumn;
	@FXML
	private TableColumn<FacturaCompra, String> proveedorColumn;
	@FXML
	private TableColumn<FacturaCompra, String> montoColumn;

	@FXML
	private TableView<DetalleCompra> lineaCompraTableView;
	@FXML
	private TableColumn<DetalleCompra, String> descripcionColumn;
	@FXML
	private TableColumn<DetalleCompra, String> cantidadColumn;
	@FXML
	private TableColumn<DetalleCompra, String> precioUnitarioColumn;
	@FXML
	private TableColumn<DetalleCompra, String> subTotalColumn;

	private Stage owner;
	private ObservableList<FacturaCompra> compras = FXCollections.observableArrayList();
	private ObservableList<DetalleCompra> lineas = FXCollections.observableArrayList();
	private FacturaCompraRepository facturaCompraRepository = new FacturaCompraRepository();
	private DetalleCompraRepository lineasRepository = new DetalleCompraRepository();

	@FXML
	private void initialize(){
	    codigoFacturaColumn.setCellValueFactory(cellData -> cellData.getValue().idFacturaCompraProperty().asString());
	    fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
	    proveedorColumn.setCellValueFactory(cellData -> cellData.getValue().getProveedor().nombreProperty());
	    montoColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asString());


//	    tabla de linea de compra
		descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().getArticulo().descripcionProperty());
		cantidadColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asString());
		precioUnitarioColumn.setCellValueFactory(cellData -> cellData.getValue().precioUnitarioProperty().asString());
		subTotalColumn.setCellValueFactory(
				cellData -> cellData.getValue().precioUnitarioProperty()
						.multiply(
								cellData.getValue().cantidadProperty()).asString());
	}

	public void setOwner(Stage owner){
		this.owner = owner;
	}
	public void obtenerCompras(){
		this.compras = facturaCompraRepository.view();
		comprasTable.setItems(compras);
	}

	@FXML
	public void showLinea(){
	    if (!comprasTable.getSelectionModel().isEmpty()){
            FacturaCompra facturaCompra = comprasTable.getSelectionModel().getSelectedItem();
            lineas=lineasRepository.view(facturaCompra.getIdFacturaCompra());
            lineaCompraTableView.setItems(lineas);
        }
	}

	@FXML
	public void handleNew(){
		FacturaCompra temp = new FacturaCompra();
		boolean okClicked = this.showEdit(temp,true);
		if(okClicked)
			obtenerCompras();

	}


	private boolean showEdit(FacturaCompra facturaTemp, boolean b) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/compra/cruds/FacturaEdit.fxml"));
			Group page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nueva Compra");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(owner);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);


			FacturaEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setIsNew(b);
			controller.setDatos(facturaTemp);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	@FXML
	public void handleEdit(){
		FacturaCompra selectedItem = comprasTable.getSelectionModel().getSelectedItem();
		if(selectedItem!=null)
			this.showEdit(selectedItem,false);
		else
			Alerta.alertaError("Seleccionar Factura",
					"Por favor seleccione una Distancia en la lista.");

	}
	@FXML
	public void handleEliminar(){
		FacturaCompra selectedItem = comprasTable.getSelectionModel().getSelectedItem();
		if(selectedItem!=null){
			Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Factura de Compra",null,
					"Esta seguro de querer borrar la Factura seleccionada? \nPara confirmar presione Aceptar.");
			if(resultado.isPresent() && resultado.get() == ButtonType.OK){
				comprasTable.getItems().remove(
						comprasTable.getSelectionModel().getSelectedIndex());
				facturaCompraRepository.delete(selectedItem.getIdFacturaCompra());
			}
		}else{
			Alerta.alertaError("Seleccionar Ingenio","Por favor selecciona un Ingenio en la lista");
		}
	}

}
