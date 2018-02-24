package application.view.info.cruds;

import application.model.info.*;
import application.repository.info.*;
import application.view.info.InfoCategoriaEmpleadoController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EmpleadoEditDialogController {

	
	
	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellidoField;
	@FXML
	private TextField cuitField;
	@FXML
	private TextField calleNombreField;
	@FXML
	private TextField calleNumeroField;
	@FXML
	private DatePicker nacimientoPicker;
	@FXML
	private ComboBox<CategoriaEmpleado> categoriaComboBox;
	@FXML
	private ComboBox<Provincia> provinciaComboBox;
	@FXML
	private ComboBox<Localidad> localidadComboBox;
	@FXML
	private Button aceptarButton;
	@FXML
	private Button familiarButton;
	private boolean isNew;
	private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
	private ProvinciaRepository provinciaRepository = new ProvinciaRepository();
	private LocalidadRepository localidadRepository =  new LocalidadRepository();
	private DomicilioRepository domicilioRepository = new DomicilioRepository();

	private Stage dialogStage;
	private Empleado empleado;
	private boolean okClicked = false;

	public void setIsNew(boolean aNew) {
		isNew = aNew;
	}

	@FXML
	private	void initialize(){
		setCategoriaComboBox();
		setProvinciaComboBox();
		localidadComboBox.setDisable(true);
		provinciaComboBox.setOnAction((event) -> {
			localidadComboBox.setDisable(false);
			setLocalidadChoiceBox();
		});

	}
	
	
	public void setCategoriaComboBox(){
		categoriaComboBox.setItems(categoriaEmpleadoRepository.view());
	}
	
	public void setProvinciaComboBox(){
		provinciaComboBox.setItems(provinciaRepository.view());
	}
	
	public void setLocalidadChoiceBox(){
        Provincia provinciaSeleccionada = provinciaComboBox.getSelectionModel().getSelectedItem();
        localidadComboBox.setItems(localidadRepository.view(provinciaSeleccionada.getIdProvincia()));
	}
	

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(Empleado empleado) {
		familiarButton.setDisable(true);
		this.empleado = empleado;
		if (!isNew){
			familiarButton.setDisable(false);
			nombreField.setText(empleado.getNombre());
			apellidoField.setText(empleado.getApellido());
			cuitField.setText(empleado.getCuit());
			calleNombreField.setText(empleado.getDomicilio().getCalle());
			calleNumeroField.setText(empleado.getDomicilio().getNumero());
			categoriaComboBox.setValue(empleado.getCategoriaEmpleado());
			provinciaComboBox.setValue(empleado.getDomicilio().getLocalidad().getProvincia());
			nacimientoPicker.setValue(LocalDate.parse(empleado.getNacimiento()));
			localidadComboBox.setDisable(false);
			setLocalidadChoiceBox();
			localidadComboBox.getSelectionModel().select(empleado.getDomicilio().getLocalidad());
		}
	}

	public boolean isOkClicked(){
		return okClicked;
	}
	

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			//Nuevo Empleado
			empleado.setNombre(nombreField.getText());
			empleado.setApellido(apellidoField.getText());
			empleado.setCuit(cuitField.getText());
			//Set de la categoria empleado
			empleado.setCategoriaEmpleado(categoriaComboBox.getSelectionModel().getSelectedItem());
			//Set Localidad
			empleado.setDomicilio(new Domicilio());
			Localidad localidadEmpleado =  localidadComboBox.getSelectionModel().getSelectedItem();
			empleado.getDomicilio().setLocalidad(localidadEmpleado);
			//Set Provincia
			localidadEmpleado.setProvincia(provinciaComboBox.getSelectionModel().getSelectedItem());

			//Set fecha de alta
			ponerFechaAlta();
			//Set Fecha de Nacimiento del Empleado
			empleado.setNacimiento(nacimientoPicker.getValue().toString());

			if (isNew){
				//Domicilio del Empleado
				empleado.setDomicilio(new Domicilio(0, localidadEmpleado, calleNombreField.getText(),calleNumeroField.getText()));
				domicilioRepository.save(empleado.getDomicilio());
				empleadoRepository.save(empleado);
			} else
				empleadoRepository.edit(empleado);
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
			alert.setTitle("Datos Invalidos");
			alert.setHeaderText(null);
			alert.setContentText(errorMessage);
			alert.showAndWait();

			return false;
		}
	}
	@FXML
	private void handleNewCategoriaEmpleado(){
		InfoCategoriaEmpleadoController controller = new InfoCategoriaEmpleadoController();
		controller.showCategoriaEmpleadoEdit(new CategoriaEmpleado(), true);
		setCategoriaComboBox();
	}
	@FXML
	private void mostrarFamiliares(){
		//todo: hacer metodo
	}

	private void ponerFechaAlta(){
		java.util.Date input = new Date();
		LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		empleado.setFechaAlta(date.toString());
	}
}
