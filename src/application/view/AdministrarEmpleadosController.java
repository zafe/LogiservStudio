package application.view;

import java.io.IOException;

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
	private Stage owner;
	private ObservableList<Empleado> empleadoData = FXCollections.observableArrayList();
	
	public void buscarEmpleados(){
		this.empleadoData = EmpleadoRepository.buscarEmpleados();
		empleadoTable.setItems(empleadoData);
	}


	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
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
	public boolean showEmpleadoEditDialog(Empleado empleado) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditarEmpleadoDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Empleado");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EmpleadoEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
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
	private void handleNewEmpleado() {
		Empleado tempEmpleado = new Empleado();
		boolean okClicked = this.showEmpleadoEditDialog(tempEmpleado);
		if (okClicked) {
			empleadoData.add(tempEmpleado);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		Empleado selectedEmpleado = empleadoTable.getSelectionModel().getSelectedItem();
		if (selectedEmpleado != null) {
			this.showEmpleadoEditDialog(selectedEmpleado);
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
