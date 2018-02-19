package application.view.info;

import java.io.IOException;

import application.model.info.CategoriaEmpleado;
import application.view.info.cruds.CategoriaEmpleadoEditController;
import application.view.info.cruds.EmpleadoEditDialogController;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import application.Main;
import application.model.info.Empleado;
import application.repository.info.EmpleadoRepository;

public class AdministrarEmpleadosController {

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
	private Stage owner;
	private ObservableList<Empleado> empleadoData = FXCollections.observableArrayList();
	EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	
	public void buscarEmpleados(){
		this.empleadoData = empleadoRepository.buscarEmpleados();
		empleadoTable.setItems(empleadoData);
	}


	@FXML
	private void initialize() {
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
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteEmpleado() {
		int selectedIndex = empleadoTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			empleadoTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Seleccionar Empleado");
			alert.setHeaderText(null);
			alert.setContentText("Por favor selecciona un empleado en la tabla");
			alert.showAndWait();
		}
	}
	
	public void setOwner(Stage owner){
		this.owner = owner;
		
	}
	public boolean showEmpleadoEditDialog(Empleado empleado, boolean isNew) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/info/cruds/EditarEmpleadoDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (isNew)
				dialogStage.setTitle("Nuevo Empleado");
			else
			dialogStage.setTitle("Editar Empleado");

			dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EmpleadoEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(isNew);
            controller.setPerson(empleado);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
 
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNew() {
		Empleado tempEmpleado = new Empleado();
		boolean okClicked = this.showEmpleadoEditDialog(tempEmpleado, true);
		if (okClicked) {
			buscarEmpleados();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		if (!empleadoTable.getSelectionModel().isEmpty()) {
			Empleado selectedEmpleado = empleadoTable.getSelectionModel().getSelectedItem();
			this.showEmpleadoEditDialog(selectedEmpleado, false);
			buscarEmpleados();
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Seleccionar Empleado");
			alert.setHeaderText(null);
			alert.setContentText("Por favor selecciona un empleado en la tabla");
			alert.showAndWait();
		}
	}


}
