package application.view.info.cruds;

import application.model.info.Empleado;
import application.model.info.Usuario;
import application.repository.info.EmpleadoRepository;
import application.repository.info.UsuarioRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UsuarioEditDialogController {

	@FXML
	private TextField usuarioField;
	@FXML
	private TextField passwordField;
	@FXML
	private TableView<Empleado> empleadoTable;
	@FXML
	private TableColumn<Empleado, String> nombreColumn;
	@FXML
	private TableColumn<Empleado, String> apellidoColumn;
	@FXML
	private TableColumn<Empleado, String> cuitColumn;
	@FXML
	private Button aceptarButton;

	private Stage dialogStage;
	private Usuario usuario;
	private boolean okClicked = false;

	EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	private ObservableList<Empleado> empleadoList = empleadoRepository.buscarEmpleados();

	@FXML
	private	void initialize(){
		empleadoTable.setItems(empleadoList);
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
		//TODO cuitColumn.setCellValueFactory(cellData -> cellData.getValue().);
		
		}
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
		passwordField.setText(usuario.getPassword());
		

	}
	
	
	public boolean isOkClicked(){
		return okClicked;
	}
	

	@FXML
	private void handleOk() {
		Empleado empleado = empleadoTable.getSelectionModel().getSelectedItem();
		if (isInputValid() && empleado != null ) {
			usuario.setNombre_usuario(usuarioField.getText());
			usuario.setPassword(passwordField.getText());
			usuario.setEmpleado(empleado);
			usuarioRepository.create(usuario);
			okClicked = true;
			dialogStage.close();
		}else {
			// Nothing selected.
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Verifique los datos");
		alert.setHeaderText(null);
		alert.setContentText("Alguno de los datos ingresados es incorrecto o no seleccionó un empleado");
		alert.showAndWait();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	
	
	
	private boolean isInputValid() {
		String errorMessage = "";

		if (usuarioField.getText() == null || usuarioField.getText().length() == 0) {
			errorMessage += "No se ingresó un usuario válido\n";
		}
		if (passwordField.getText() == null || passwordField.getText().length() == 0) {
			errorMessage += "No se ingresó una contraseña válida\n";
		}

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

}
