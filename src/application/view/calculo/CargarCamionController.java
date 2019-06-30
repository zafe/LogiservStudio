package application.view.calculo;

import application.Main;
import application.comunes.Alerta;
import application.model.calculo.Camion;
import application.repository.calculo.CamionRepository;
import application.view.calculo.cruds.CamionEditController;
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

public class CargarCamionController {
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Camion> camionTableView;
    @FXML
    private TableColumn<Camion, String> marcaTableColumn;
    @FXML
    private TableColumn<Camion, String> modeloTableColumn;
    @FXML
    private TableColumn<Camion, String> patenteTableColumn;
    @FXML
    private TableColumn<Camion, String> motorColumn;
    @FXML
    private TableColumn<Camion, String> chasisColumn;

    private Stage owner;
    private ObservableList<Camion> camionData = FXCollections.observableArrayList();
    private CamionRepository camionRepository = new CamionRepository();


    /**
     * This method is responsible for charge all the data in the table view
     */
    @FXML
    private void initialize(){
        marcaTableColumn.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        modeloTableColumn.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());
        patenteTableColumn.setCellValueFactory(cellData -> cellData.getValue().patenteProperty());
        motorColumn.setCellValueFactory(cellData -> cellData.getValue().motorProperty());
        chasisColumn.setCellValueFactory(cellData -> cellData.getValue().chasisProperty());
    }

    @FXML
    public void handleNewCamion(){
        Camion tempCamion = new Camion();
        boolean okClicked = this.showCamionEdit(tempCamion,true);
        if(okClicked)
           buscarCamiones();
    }


    @FXML
    public void handleEditCamion(){
        Camion selectedItem = camionTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showCamionEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Camión",
                    "Por favor seleccione un Camion en la tabla.");

    }
    @FXML
    public void handleEliminarCamion(){
        Camion selectedItem = camionTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Camión",null,
                    "Esta seguro de querer borrar el Camión seleccionado? \nPara confirmar presione Aceptar.");
            if(resultado.isPresent() && resultado.get() == ButtonType.OK){
                camionTableView.getItems().remove(
                        camionTableView.getSelectionModel().getSelectedIndex());
                camionRepository.delete(selectedItem.getId());
            }

        }else{
            Alerta.alertaError("Seleccionar Camión","Por favor selecciona un Camión en la tabla");
        }
    }
    public void buscarCamiones(){
        this.camionData = camionRepository.view();
        camionTableView.setItems(camionData);
    }
    public void setOwner(Stage owner){
        this.owner = owner;

    }
    private boolean showCamionEdit(Camion tempCamion, boolean b) {
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


            CamionEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setCamion(tempCamion);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
