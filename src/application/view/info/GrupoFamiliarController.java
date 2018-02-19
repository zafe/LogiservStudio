package application.view.info;

import application.Main;
import application.comunes.Alerta;
import application.model.info.Empleado;
import application.model.info.Familiar;
import application.repository.info.EmpleadoRepository;
import application.repository.info.FamiliarRepository;
import application.view.compra.cruds.FacturaEditController;
import application.view.info.cruds.FamiliarEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GrupoFamiliarController implements Initializable{

	@FXML
	private TableView<Familiar> familiaresTable;
	@FXML
	private TableColumn<Familiar, String> nombreColumn;
	@FXML
	private TableColumn<Familiar, String> apellidoColumn;
	@FXML
	private TableColumn<Familiar, String> nacimientoColumn;
	@FXML
	private TableColumn<Familiar, String> parentescoColumn;
	@FXML
	private ComboBox<Empleado> empleadoComboBox;
	@FXML
	private Button newButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;




	private Stage owner;
	private ObservableList<Familiar> familiares = FXCollections.observableArrayList();
	private FamiliarRepository familiarRepository = new FamiliarRepository();
	private EmpleadoRepository empleadoRepository = new EmpleadoRepository();


	public void setOwner(Stage owner){
		this.owner = owner;
	}
    @FXML
    public void obtenerFamiliares(){
		if (!empleadoComboBox.getSelectionModel().isEmpty()){
			familiares = familiarRepository.view(empleadoComboBox.getSelectionModel().getSelectedItem().getIdEmpleado());
			familiaresTable.setItems(familiares);
		}
	}

	private void cargarEmpleados(){
		empleadoComboBox.setItems(empleadoRepository.buscarEmpleados());

	}
	@FXML
	public void handleNew(){
		if (!empleadoComboBox.getSelectionModel().isEmpty()){
			Familiar temp = new Familiar();
			temp.setEmpleado(empleadoComboBox.getValue());
			boolean okClicked = this.showEdit(temp,true);
			if(okClicked)
				obtenerFamiliares();
		}
		else
			Alerta.alertaInfo("Seleccione un empleado", "Por favor seleccione un empleado para asignar su grupo familiar.");

	}


	private boolean showEdit(Familiar familiar, boolean isNew) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/info/cruds/FamiliarEdit.fxml"));
			Group page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			if (isNew)
			    dialogStage.setTitle("Familiar Nuevo");
			else
			    dialogStage.setTitle("Editar Familiar de " + familiar.getEmpleado().getApellido() + ", " + familiar.getEmpleado().getNombre());
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(owner);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);


			FamiliarEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setIsNew(isNew);
			controller.setDatos(familiar);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	@FXML
	public void handleEdit(){
        if (!empleadoComboBox.getSelectionModel().isEmpty()){
            Familiar selectedItem = familiaresTable.getSelectionModel().getSelectedItem();
            selectedItem.setEmpleado(empleadoComboBox.getValue());
            if(selectedItem!=null)
                this.showEdit(selectedItem,false);
            else
                Alerta.alertaError("Seleccionar Familiar",
                        "Por favor seleccione un Familiar en la lista.");
            obtenerFamiliares();
        }
        else
            Alerta.alertaInfo("Seleccione un empleado", "Por favor seleccione un empleado para asignar su grupo familiar.");
	}
	@FXML
	public void handleEliminar(){
		Familiar selectedItem = familiaresTable.getSelectionModel().getSelectedItem();
		if(selectedItem!=null){
			Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Familiar",null,
					"Esta seguro de querer borrar al Familiar seleccionado? \nPara confirmar presione Aceptar.");
			if(resultado.isPresent() && resultado.get() == ButtonType.OK){
				familiaresTable.getItems().remove(
						familiaresTable.getSelectionModel().getSelectedIndex());
				familiarRepository.delete(selectedItem.getIdFAMILIAR());
			}
		}else{
			Alerta.alertaError("Seleccionar Ingenio","Por favor selecciona un Ingenio en la lista");
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarEmpleados();
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
		nacimientoColumn.setCellValueFactory(cellData -> cellData.getValue().fechaNacimientoProperty());
		parentescoColumn.setCellValueFactory(cellData -> cellData.getValue().parentescoProperty());

	}
}
