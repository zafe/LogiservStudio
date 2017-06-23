package application.view;

import org.controlsfx.dialog.Dialogs;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import application.Main;
import application.model.info.Empleado;

public class AdministrarEmpleadosController {

	@FXML
	private TableView<Empleado>  empleadoTable;
	@FXML
	private TableColumn<Empleado, String> nombreColumn;
	@FXML
	private TableColumn<Empleado, String> apellidoColumn;

	private Main main;

	public AdministrarEmpleadosController(){

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


	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Main mainApp) {
		this.main = mainApp;

		// Add observable list data to the table
		empleadoTable.setItems(mainApp.getEmpleadoData());
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewEmpleado() {
		Empleado tempEmpleado = new Empleado();
		boolean okClicked = main.showEmpleadoEditDialog(tempEmpleado);
		if (okClicked) {
			main.getEmpleadoData().add(tempEmpleado);
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
			main.showEmpleadoEditDialog(selectedEmpleado);
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
