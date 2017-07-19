package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import application.model.info.Usuario;

public class UsuarioEditDialogController {

	@FXML
	private TextField usuarioField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button aceptarButton;

	private Stage dialogStage;
	private Usuario usuario;
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

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;

		usuarioField.setText(usuario.getNombre_usuario());
		passwordField.setText(usuario.getPassword());

	}

	public boolean isOkClicked(){
		return okClicked;
	}
	

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			usuario.setNombre_usuario(usuarioField.getText());
			usuario.setPassword(passwordField.getText());
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

		if (usuarioField.getText() == null || usuarioField.getText().length() == 0) {
			errorMessage += "No valid user name!\n"; 
		}
		if (passwordField.getText() == null || passwordField.getText().length() == 0) {
			errorMessage += "No valid password!\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Datos Invalidos");
			alert.setHeaderText(null);
			alert.setContentText(errorMessage);
			alert.showAndWait();

			return false;
		}
	}

}
