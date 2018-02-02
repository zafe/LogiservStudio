package application.view.venta;

import application.Main;
import application.comunes.Alerta;
import application.model.calculo.Camion;
import application.model.venta.Cliente;
import application.repository.info.ClienteRepository;
import application.view.calculo.cruds.CamionEditController;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class VentaClienteController implements Initializable{
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Cliente> tableView;
    @FXML
    private TableColumn<Cliente, String> idColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> cuitColumn;
    @FXML
    private TableColumn<Cliente, String> calleColumn;
    @FXML
    private TableColumn<Cliente, String> localidadColumn;

    private Stage owner;
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();
    private ClienteRepository repository = new ClienteRepository();


    /**
     * This method is responsible for charge all the data in the table view
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idClienteProperty().asString());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        cuitColumn.setCellValueFactory(cellData -> cellData.getValue().cuitProperty());

        calleColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().calleProperty());
        calleColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().numeroProperty());
//        localidadColumn.setCellValueFactory(cellData -> cellData.getValue().getDomicilio().nombre_localidadProperty());
    }

    @FXML
    public void handleNew(){
        Cliente temp = new Cliente();
        boolean okClicked = this.showEdit(temp,true);
        if(okClicked)
            clienteData.add(temp);
    }


    @FXML
    public void handleEdit(){
        Cliente selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Cliente",
                    "Por favor seleccione un Cliente en la tabla.");

    }
    @FXML
    public void handleDelete(){
        Cliente selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Cliente",null,
                    "Está seguro de querer borrar el Cliente seleccionado? \nPara confirmar presione Aceptar.");
            if(resultado.isPresent() && resultado.get() == ButtonType.OK){
                tableView.getItems().remove(
                        tableView.getSelectionModel().getSelectedIndex());
                repository.delete(selectedItem.getIdCliente());
            }

        }else{
            Alerta.alertaError("Seleccionar Cliente","Por favor seleccione un Cliente en la tabla");
        }
    }
    public void buscarClientes(){
        this.clienteData = repository.view();
        tableView.setItems(clienteData);
    }
    public void setOwner(Stage owner){
        this.owner = owner;
    }
    private boolean showEdit(Cliente temp, boolean b) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/cruds/CamionEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nuevo Camion");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

/*
            CamionEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setCamion(temp);
*/
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return true;//controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


}
