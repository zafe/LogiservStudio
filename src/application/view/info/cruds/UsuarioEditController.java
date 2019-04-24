package application.view.info.cruds;

import application.model.info.Empleado;
import application.model.info.Usuario;
import application.repository.info.EmpleadoRepository;
import application.repository.info.UsuarioRepository;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioEditController implements Initializable{

	@FXML
	private TextField usuarioField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField newPasswordField;
	@FXML
	private PasswordField reNewPasswordField;
	@FXML
	private ComboBox<Empleado> empleadoComboBox;

	@FXML
	private Button aceptarButton;

	private Stage dialogStage;
	private Usuario usuario;
	private boolean okClicked = false;

	EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	private ObservableList<Empleado> empleadoList = empleadoRepository.buscarEmpleados();
    UsuarioRepository usuarioRepository = new UsuarioRepository();
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
		empleadoComboBox.getSelectionModel().select(usuario.getEmpleado());
	}
	
	
	public boolean isOkClicked(){
		return okClicked;
	}
	

	@FXML
	private void handleOk() {
		if (isInputValid()){
			usuario.setPassword(newPasswordField.getText());
			usuario.setEmpleado(empleadoComboBox.getValue());
			usuarioRepository.edit(usuario);
			dialogStage.close();
		}

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	
	
	
	private boolean isInputValid() {
		String errorMessage = "";
		if (usuarioField.getText() == null || usuarioField.getText().length() == 0)
			errorMessage += "No se ingresó un usuario válido\n";
		if (passwordField.getText() == null || passwordField.getText().length() == 0)
			errorMessage += "No se ingresó una contraseña válida\n";
		if (!passMatch())
			errorMessage += "La contraseña nueva no coincide, por favor ingreselas correctamente.\n";
		if (!isDiferentPassword())
			errorMessage += "La contraseña nueva es identica a la antigua.\n";
		if (!isCorrectPassword())
			errorMessage += "Contraseña incorrecta";

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Datos Inválidos");
			alert.setHeaderText(null);
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarComboBoxEmpleados();

	}

	private void cargarComboBoxEmpleados() {
		empleadoComboBox.setItems(empleadoRepository.buscarEmpleados());
	}

	private boolean passMatch() {
		boolean passMatch;
		String password = newPasswordField.getText();
		String rePass = reNewPasswordField.getText();
		if (password.matches(rePass)) {
			passMatch = true;
		} else {
			passMatch = false;
		}
		return passMatch;
	}
	private boolean isDiferentPassword(){
		boolean isDiferent;
		String oldPassword = passwordField.getText();
		String newPassword = newPasswordField.getText();

		if (oldPassword.matches(newPassword))
			isDiferent = false;
		else
			isDiferent = true;

		return isDiferent;
	}

	private boolean isCorrectPassword(){
		String passMD5 = usuario.encryptMD5(passwordField.getText());
		boolean okLogin = usuarioRepository.login(usuario.getNombre_usuario(), passMD5);
		System.out.println("Login: " + okLogin);
		return okLogin;
	}

}
