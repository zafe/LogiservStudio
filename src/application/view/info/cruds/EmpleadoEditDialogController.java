package application.view.info.cruds;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

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

public class EmpleadoEditDialogController {

	
	
	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellidoField;
	@FXML
	private TextField hijosField;
	@FXML
	private TextField cuitField;
	@FXML
	private TextField calleNombreField;
	@FXML
	private TextField calleNumeroField;
	@FXML
	private DatePicker nacimientoPicker;
	@FXML
	private ComboBox<String> categoriaComboBox;
	@FXML
	private ComboBox<String> provinciaComboBox;
	@FXML
	private ComboBox<String> localidadComboBox;
	@FXML
	
	private Button aceptarButton;
	private boolean isNew;
	private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
	private ProvinciaRepository provinciaRepository = new ProvinciaRepository();
	private LocalidadRepository localidadRepository =  new LocalidadRepository();
	private DomicilioRepository domicilioRepository = new DomicilioRepository();

	List<CategoriaEmpleado>	categoriaEmpleadoList = categoriaEmpleadoRepository.view();
	List<Provincia> provinciaList = provinciaRepository.view2();//luego cambiar view2() por view()
	List<Localidad> localidadList = new ArrayList<>();
	
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
			Integer indexProvincia = provinciaComboBox.getSelectionModel().getSelectedIndex();
			Provincia selectedProvincia = provinciaList.get(indexProvincia);
			setLocalidadChoiceBox(selectedProvincia.getIdProvincia());
			localidadComboBox.setDisable(false);
		});

	}
	
	
	public void setCategoriaComboBox(){
		ObservableList<String> ceList = FXCollections.observableArrayList();
		for(CategoriaEmpleado ce : categoriaEmpleadoList) ceList.add(ce.getNombre());
		categoriaComboBox.setItems(ceList);
	}
	
	public void setProvinciaComboBox(){
		ObservableList<String> pList = FXCollections.observableArrayList();
		for(Provincia p : provinciaList) pList.add(p.getNombre());
		provinciaComboBox.setItems(pList);
	}
	
	public void setLocalidadChoiceBox(int idProvincia){
		ObservableList<String> lList = FXCollections.observableArrayList();
		localidadList = localidadRepository.view2(idProvincia);
		
		for(Localidad l : localidadList) lList.add(l.getNombre());
		localidadComboBox.setItems(lList);
		
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
		this.empleado = empleado;
		Domicilio domicilio = domicilioRepository.getDomicilioById(empleado.getDomicilio().getIdDomicilio());;

		nombreField.setText(empleado.getNombre());
		apellidoField.setText(empleado.getApellido());
		hijosField.setText(String.valueOf(empleado.getHijos()));
		cuitField.setText(empleado.getCuit());
		calleNombreField.setText(domicilio.getCalle());
		calleNumeroField.setText(domicilio.getNumero());
		categoriaComboBox.setValue(empleado.getCategoria());
		provinciaComboBox.setValue(empleado.getDomicilio().getLocalidad().getProvincia().getNombre());

		if (empleado.getNacimiento() != null){
			//Seteo del dia de Nacimiento del Empleado

			//Adapto el formato a la forma en que se ven las fechas en la base de datos
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
			String nacimiento = empleado.getNacimiento();

			//convert String to LocalDate
			LocalDate diaNacimiento = LocalDate.parse(nacimiento, formatter);

			nacimientoPicker.setValue(diaNacimiento);
		}

		if (provinciaComboBox.getSelectionModel().getSelectedItem() != null)
			localidadComboBox.setDisable(false);
		setLocalidadChoiceBox(empleado.getDomicilio().getLocalidad().getProvincia().getIdProvincia());
		localidadComboBox.setValue(empleado.getDomicilio().getLocalidad().getNombre());

	}
	
	//Antes de crear un Empleado es necesario crear el Domicilio donde reside
	public void setDomicilio(String calle, String numero, int idLocaliad){
		Domicilio domicilio = new Domicilio(0, new Localidad(), calle, numero);
		domicilioRepository.save(domicilio);
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
			empleado.setHijos(Integer.parseInt(hijosField.getText()));
			empleado.setCuit(cuitField.getText());
			//Set de la categoria empleado
			Integer indexCategoria = categoriaComboBox.getSelectionModel().getSelectedIndex();
			CategoriaEmpleado selectedCategoriaEmpleado = categoriaEmpleadoList.get(indexCategoria);
			//empleado.getCategoriaEmpleado().setIdCategoriaEmpleado(selectedCategoriaEmpleado.getIdCategoriaEmpleado());
			empleado.setCategoriaEmpleado(selectedCategoriaEmpleado);
			//Domicilio del Empleado
			empleado.getDomicilio().setCalle(calleNombreField.getText());
			empleado.getDomicilio().setNumero(calleNumeroField.getText());
			//Set Localidad
			Integer indexLocalidad = localidadComboBox.getSelectionModel().getSelectedIndex();
			Localidad localidadEmpleado = localidadList.get(indexLocalidad);
			empleado.getDomicilio().setLocalidad(localidadEmpleado);
			//Set Provincia
			Integer indexProvincia = provinciaComboBox.getSelectionModel().getSelectedIndex();
			Provincia provinciaEmpleado = provinciaList.get(indexProvincia);
			empleado.getDomicilio().getLocalidad().setProvincia(provinciaEmpleado);
			//Set Fecha de Nacimiento del Empleado
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
			empleado.setNacimiento(nacimientoPicker.getValue().format(formatter));
			System.out.println("Informacion del empleado a guardar: ");
			System.out.printf("Nombre: %s %n " +
					"Apellido: %s %n" +
					"Hijos: %s %n" +
					"CUIT: %s %n" +
					"Categoria Emp: %s %n" +
					"Nacimiento: %s %n" +
					"Domicilio id: %s %n" +
					"  Calle: %s %n" +
					"  Numero: %s %n" +
					"  Localidad: %s %n" +
					"  Provincia: %s %n",
					empleado.getNombre(),
					empleado.getApellido(),
					empleado.getHijos(),
					empleado.getCuit(),
					empleado.getCategoriaEmpleado().getNombre(),
					empleado.getNacimiento(),
					empleado.getDomicilio().getIdDomicilio(),
					empleado.getDomicilio().getCalle(),
					empleado.getDomicilio().getNumero(),
					empleado.getDomicilio().getLocalidad().getNombre(),
					empleado.getDomicilio().getLocalidad().getProvincia().getNombre());
			if (isNew){
					empleadoRepository.save(empleado);
			} else{
					empleadoRepository.edit(empleado);
			}



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
