package application.view.venta;

import application.Main;
import application.comunes.Alerta;
import application.model.venta.Viaje;
import application.repository.venta.ViajeRepository;
import application.view.venta.cruds.ViajeEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AdministrarViajesController {

	@FXML
	private TableView<Viaje> viajeTable;
	@FXML
	private TableColumn<Viaje, String> nombreConductorColumn;
	@FXML
	private TableColumn<Viaje, String> apellidoConductorColumn;
	@FXML
	private TableColumn<Viaje, String> ingenioColumn;
	@FXML
	private TableColumn<Viaje, String> distanciaColumn;
	@FXML
	private TableColumn<Viaje, String> diaColumn;
	@FXML
	private TableColumn<Viaje, String> horaEntradaColumn;
	@FXML
	private TableColumn<Viaje, String> brutoColumn;
	@FXML
	private TableColumn<Viaje, String> taraColumn;


	private Stage owner;
	private ObservableList<Viaje> viajeData = FXCollections.observableArrayList();
	private ViajeRepository repository = new ViajeRepository();
	
	public void buscarViajes(){
		this.viajeData = repository.view();
			viajeTable.setItems(viajeData);
	}


	@FXML
	private void initialize() {
		ingenioColumn.setCellValueFactory(cellData -> cellData.getValue().getIngenio().nombreProperty());
		nombreConductorColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().nombreProperty());
		apellidoConductorColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().apellidoProperty());
		distanciaColumn.setCellValueFactory(cellData -> cellData.getValue().distanciaRecorridaProperty());
		diaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
		horaEntradaColumn.setCellValueFactory(cellData -> cellData.getValue().horaEntradaProperty());
		brutoColumn.setCellValueFactory(cellData -> cellData.getValue().brutoProperty().asString());
		taraColumn.setCellValueFactory(cellData -> cellData.getValue().taraProperty().asString());
	}

	public void setOwner(Stage owner){
		this.owner = owner;

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewViaje() {
		Viaje temp = new Viaje();
		boolean okClicked = this.showViajeEditDialog(temp, true);
		if(okClicked)
			viajeData.add(temp);
	}
	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditViaje() {
		Viaje selectedViaje = viajeTable.getSelectionModel().getSelectedItem();
		if (selectedViaje != null)
			this.showViajeEditDialog(selectedViaje, false);
		else
			Alerta.alertaError("Seleccionar Viajes", "Por favor seleccione un Viaje en la tabla.");
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteViaje() {
		Viaje selectedViaje = viajeTable.getSelectionModel().getSelectedItem();
		if (selectedViaje != null){
			Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Viaje", null,
					"¿Está seguro de borrar el Viaje Seleccionado?");
			if (resultado.isPresent() && resultado.get() == ButtonType.OK){
				viajeTable.getItems().remove(viajeTable.getSelectionModel().getSelectedIndex());
				repository.delete(selectedViaje);
			}
		}


	}

	private boolean showViajeEditDialog(Viaje temp, boolean b) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/venta/cruds/ViajeEdit.fxml"));
			Group page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nuevo Viaje");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(owner);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);


            ViajeEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setViaje(temp);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;//controller.isOkClicked();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
