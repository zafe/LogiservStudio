package application.view.info;

import application.Main;
import application.comunes.Alerta;
import application.model.info.CategoriaEmpleado;
import application.repository.info.CategoriaEmpleadoRepository;
import application.view.info.cruds.CategoriaEmpleadoEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class InfoCategoriaEmpleadoController {

	@FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<CategoriaEmpleado> ceTableView;
	@FXML
	private TableColumn<CategoriaEmpleado, String> idCategoriaEmpleadoTableColumn;
	@FXML
	private TableColumn<CategoriaEmpleado, String> nombreCategoriaTableColumn;
	
	private Stage owner;
	private ObservableList<CategoriaEmpleado> ceData = FXCollections.observableArrayList();
	private CategoriaEmpleadoRepository ceRepository = new CategoriaEmpleadoRepository();
	
	/**
     * This method is responsible for charge all the data in the table view
     */
    @FXML
    private void initialize(){
        idCategoriaEmpleadoTableColumn.setCellValueFactory(cellData -> cellData.getValue().idCategoriaEmpleadoProperty().asString());
        nombreCategoriaTableColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
    }
    
    @FXML
    public void handleNewCategoriaEmpleado(){
        CategoriaEmpleado tempCategoriaEmpleado = new CategoriaEmpleado();
        boolean okClicked = this.showCategoriaEmpleadoEdit(tempCategoriaEmpleado,true);
        if(okClicked)
            ceData.add(tempCategoriaEmpleado);
    }


    @FXML
    public void handleEditCategoriaEmpleado(){
        CategoriaEmpleado selectedItem = ceTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showCategoriaEmpleadoEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Categoría de Empleado",
                    "Por favor seleccione un Categoría de Empleado en la tabla.");

    }
    @FXML
    public void handleEliminarAcoplado(){
        CategoriaEmpleado selectedItem = ceTableView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Categoria de Empleado",null,
                "Esta seguro de querer borrar la Categoria de Empleado seleccionada? \nPara confirmar presione Aceptar.");
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            ceTableView.getItems().remove(
                    ceTableView.getSelectionModel().getSelectedIndex());
            ceRepository.delete(selectedItem);
        }else
            Alerta.alertaError("Seleccionar Categoria de Empleado","Por favor selecciona un Categoria de Empleado en la tabla");
    }
    public void buscarCategoriaEmpleados(){
        this.ceData = ceRepository.view();
        ceTableView.setItems(ceData);
    }
    public void setOwner(Stage owner){
        this.owner = owner;

    }
    public boolean showCategoriaEmpleadoEdit(CategoriaEmpleado tempAcoplado, boolean b) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/info/cruds/CategoriaEmpleadoEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva Categoria de Empleado");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            CategoriaEmpleadoEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);        
            controller.setCategoriaEmpleado(tempAcoplado);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
