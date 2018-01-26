package application.view.compra;

import application.Main;
import application.comunes.Alerta;
import application.model.compra.Proveedor;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.repository.info.DomicilioRepository;
import application.repository.info.ProveedorRepository;
import application.view.compra.cruds.ProveedorEditController;
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

public class ProveedoresController {
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;
    @FXML
    private TableView<Proveedor> proveedorTableView;
    @FXML
    private TableColumn<Proveedor, String> idColumn;
    @FXML
    private TableColumn<Proveedor, String> nombreTableColumn;
    @FXML
    private TableColumn<Proveedor, String> cuitTableColumn;
    @FXML
    private TableColumn<Proveedor, String> calleTableColumn;
    @FXML
    private TableColumn<Proveedor, String> numeroTableColumn;
    @FXML
    private TableColumn<Proveedor, String> localidadTableColumn;

    private Stage owner;
    private ObservableList<Proveedor> proveedorObservableList = FXCollections.observableArrayList();
    private ProveedorRepository proveedorRepository = new ProveedorRepository();


    @FXML
    private void initialize(){
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        nombreTableColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        cuitTableColumn.setCellValueFactory(cellData -> cellData.getValue().cuitProperty());
        calleTableColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().calleProperty());
        numeroTableColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().numeroProperty());
        localidadTableColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().getLocalidad().nombreProperty());
    }

    @FXML
    public void handleNew(){
        Proveedor proveedor = new Proveedor();
        boolean okClicked = this.showEdit(proveedor, true);
        if(okClicked){
            obtenerProveedores();
        }
    }
    @FXML
    public void handleUpdate(){
        Proveedor proveedorSeleccionado = proveedorTableView.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado!=null){
            this.showEdit(proveedorSeleccionado,false);
        }else
            Alerta.alertaError("Seleccionar Proveedor",
                    "Por favor selecciona un proveedor en la tabla.");

    }
    @FXML
    public void handleDelete(){
        Proveedor proveedor = proveedorTableView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Proveedor",
                null,"¿Desea eliminar el proveedor seleccionado?");
        if (resultado.isPresent() && resultado.get()==ButtonType.OK){
            proveedorTableView.getItems().remove(
                    proveedorTableView.getSelectionModel().getSelectedIndex());
            proveedorRepository.delete(proveedor);
        }else
            Alerta.alertaError("Seleccionar Proveedor", "Por favor seleccione un proveedor en la tabla.");
    }

    public void obtenerProveedores(){
        this.proveedorObservableList = proveedorRepository.view();
        proveedorTableView.setItems(proveedorObservableList);

    }
    public void setOwner(Stage owner){
        this.owner = owner;
    }

    public boolean showEdit(Proveedor proveedor, boolean tipo){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/cruds/ProveedorEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Proveedores");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            ProveedorEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(tipo);
            controller.setDatos(proveedor);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




}
