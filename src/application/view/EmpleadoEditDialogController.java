package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import application.model.info.Empleado;

public class EmpleadoEditDialogController {

	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellidoField;

	private Stage dialogStage;
	private Empleado empleado;
	private boolean okClicked = false;

	@FXML
	private	void initialize(){}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(Empleado empleado) {
		this.empleado = empleado;

		nombreField.setText(empleado.getNombre());
		apellidoField.setText(empleado.getApellido());

	}

	public boolean isOkClicked(){
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			empleado.setNombre(nombreField.getText());
			empleado.setApellido(apellidoField.getText());
			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (nombreField.getText() == null || nombreField.getText().length() == 0) {
			errorMessage += "No valid first name!\n"; 
		}
		if (apellidoField.getText() == null || apellidoField.getText().length() == 0) {
			errorMessage += "No valid last name!\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Datos Invalido");
			alert.setHeaderText(null);
			alert.setContentText(errorMessage);
			alert.showAndWait();

			return false;
		}
	}

}
