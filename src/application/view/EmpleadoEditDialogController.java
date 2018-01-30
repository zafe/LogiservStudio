/*
 * 2018 © All rights reserved to The Desastre Team 
 */
package application.view;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import application.model.info.CategoriaEmpleado;
import application.model.info.Domicilio;
import application.model.info.Empleado;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.repository.info.CategoriaEmpleadoRepository;
import application.repository.info.DomicilioRepository;
import application.repository.info.EmpleadoRepository;
import application.repository.info.LocalidadRepository;
import application.repository.info.ProvinciaRepository;



// TODO: Auto-generated Javadoc
/**
 * The Class EmpleadoEditDialogController.
 */
public class EmpleadoEditDialogController {

	
	
	/** The nombre field. */
	@FXML
	private TextField nombreField;
	
	/** The apellido field. */
	@FXML
	private TextField apellidoField;
	
	/** The hijos field. */
	@FXML
	private TextField hijosField;
	
	/** The cuit field. */
	@FXML
	private TextField cuitField;
	
	/** The calle nombre field. */
	@FXML
	private TextField calleNombreField;
	
	/** The calle numero field. */
	@FXML
	private TextField calleNumeroField;
	
	/** The nacimiento picker. */
	@FXML
	private DatePicker nacimientoPicker;
	
	/** The categoria combo box. */
	@FXML
	private ComboBox<String> categoriaComboBox;
	
	/** The provincia combo box. */
	@FXML
	private ComboBox<String> provinciaComboBox;
	
	/** The localidad combo box. */
	@FXML
	private ComboBox<String> localidadComboBox;
	
	/** The aceptar button. */
	@FXML
	
	private Button aceptarButton;
	
	/** The empleado repository. */
	private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	
	/** The categoria empleado repository. */
	private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
	
	/** The provincia repository. */
	private ProvinciaRepository provinciaRepository = new ProvinciaRepository();
	
	/** The localidad repository. */
	private LocalidadRepository localidadRepository =  new LocalidadRepository();
	
	/** The domicilio repository. */
	private DomicilioRepository domicilioRepository = new DomicilioRepository();

	/** The categoria empleado list. */
	List<CategoriaEmpleado>	categoriaEmpleadoList = categoriaEmpleadoRepository.view();
	
	/** The provincia list. */
	List<Provincia> provinciaList = provinciaRepository.view2();//luego cambiar view2() por view()
	
	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The empleado. */
	private Empleado empleado;
	
	/** The ok clicked. */
	private boolean okClicked = false;

	/**
	 * Initialize.
	 */
	@FXML
	private	void initialize(){
		setCategoriaComboBox();
		setProvinciaComboBox();
		localidadComboBox.setDisable(true);
		provinciaComboBox.setOnAction((event) -> {
			Integer indexProvincia = provinciaComboBox.getSelectionModel().getSelectedIndex();
			Provincia selectedProvincia = provinciaList.get(indexProvincia);
			setLocalidadChoiceBox(selectedProvincia.getIdProvincia());
			localidadComboBox.setDisable(false);
		});
	}
	
	
	/**
	 * Sets the categoria combo box.
	 */
	public void setCategoriaComboBox(){
		ObservableList<String> ceList = FXCollections.observableArrayList();
		for(CategoriaEmpleado ce : categoriaEmpleadoList) ceList.add(ce.getNombre());
		categoriaComboBox.setItems(ceList);
	}
	
	/**
	 * Sets the provincia combo box.
	 */
	public void setProvinciaComboBox(){
		ObservableList<String> pList = FXCollections.observableArrayList();
		for(Provincia p : provinciaList) pList.add(p.getNombre());
		provinciaComboBox.setItems(pList);
	}
	
	/**
	 * Sets the localidad choice box.
	 *
	 * @param idProvincia the new localidad choice box
	 */
	public void setLocalidadChoiceBox(int idProvincia){
		ObservableList<String> lList = FXCollections.observableArrayList();
		List<Localidad> localidadList = localidadRepository.view2(idProvincia);
		
		for(Localidad l : localidadList) lList.add(l.getNombre());
		localidadComboBox.setItems(lList);
		
	}
	

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage no se que hace
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person.
	 *
	 * @param empleado the new person
	 */
	public void setPerson(Empleado empleado) {
		this.empleado = empleado;
		Domicilio domicilio = domicilioRepository.getDomicilioById(empleado.getIdDomicilio());;

		nombreField.setText(empleado.getNombre());
		apellidoField.setText(empleado.getApellido());
		hijosField.setText(String.valueOf(empleado.getHijos()));
		cuitField.setText(empleado.getCuit());
		calleNombreField.setText(domicilio.getCalle());
		calleNumeroField.setText(domicilio.getNumero());
		categoriaComboBox.getSelectionModel().select(empleado.getCategoria());
		provinciaComboBox.getSelectionModel().select(domicilio.getNombre_provincia());
		System.out.println("ID provincia: "+domicilio.getIdProvincia());
		setLocalidadChoiceBox(domicilio.getIdProvincia());
		localidadComboBox.getSelectionModel().select(domicilio.getNombre_localidad());
		localidadComboBox.setDisable(false);
	}
	
	/**
	 * Sets the domicilio.
	 *
	 * @param calle the calle
	 * @param numero the numero
	 * @param idLocalidad the id localidad
	 */
	//Antes de crear un Empleado es necesario crear el Domicilio donde reside
	public void setDomicilio(String calle, String numero, int idLocalidad){
		Domicilio domicilio = new Domicilio(0, "", "", calle, numero, idLocalidad, 0);
		domicilioRepository.save(domicilio, idLocalidad);
	}

	/**
	 * Checks if is ok clicked.
	 *
	 * @return true, if is ok clicked
	 */
	public boolean isOkClicked(){
		return okClicked;
	}
	

	/**
	 * Handle ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			empleado.setNombre(nombreField.getText());
			empleado.setApellido(apellidoField.getText());
			empleado.setCategoria(categoriaComboBox.getSelectionModel().getSelectedItem().toString());
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Handle cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	
	
	
	/**
	 * Checks if is input valid.
	 *
	 * @return true, if is input valid
	 */
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
