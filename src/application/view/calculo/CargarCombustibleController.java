package application.view.calculo;

import application.Main;
import application.comunes.Alerta;
import application.model.calculo.CargaCombustible;
import application.repository.calculo.CargaCombustibleRepository;
import application.view.calculo.cruds.CargaCombustibleEditController;
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

public class CargarCombustibleController {
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<CargaCombustible> cargaCombustibleTableView;
    @FXML
    private TableColumn<CargaCombustible, String> fechaTableColumn;
    @FXML
    private TableColumn<CargaCombustible, String> horaTableColumn;
    @FXML
    private TableColumn<CargaCombustible, String> cantidadLitrosTableColumn;
    @FXML
    private TableColumn<CargaCombustible, String> nombreConductorTableColumn;
    @FXML
    private TableColumn<CargaCombustible, String> apellidoConductroTableColumn;
    @FXML
    private TableColumn<CargaCombustible, String> marcaCamionTableColumn;
    @FXML
    private TableColumn<CargaCombustible, String> modeloCamionTableColumn;
    @FXML
    private TableColumn<CargaCombustible, String> patenteCamionColumn;

    private Stage owner;
    private ObservableList<CargaCombustible> cargaCombustibleData = FXCollections.observableArrayList();
    private CargaCombustibleRepository cargaCombustibleRepository = new CargaCombustibleRepository();


    /**
     * This method is responsible for charge all the data in the table view
     */
    @FXML
    private void initialize(){
        fechaTableColumn.setCellValueFactory(cellData -> cellData.getValue().fechaCargaProperty());
        horaTableColumn.setCellValueFactory(cellData -> cellData.getValue().horaCargaProperty());
        cantidadLitrosTableColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadLitrosProperty().asString());
        nombreConductorTableColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().nombreProperty());
        apellidoConductroTableColumn.setCellValueFactory(cellData -> cellData.getValue().getConductor().apellidoProperty());
        marcaCamionTableColumn.setCellValueFactory(cellData -> cellData.getValue().getCamion().marcaProperty());
        modeloCamionTableColumn.setCellValueFactory(cellData -> cellData.getValue().getCamion().modeloProperty());
        patenteCamionColumn.setCellValueFactory(cellData -> cellData.getValue().getCamion().patenteProperty());
    }

    @FXML
    public void handleNewCargaCombustible(){
        CargaCombustible cargaCombustible = new CargaCombustible();
        boolean okClicked = this.showCargaDeCombustibleEdit(cargaCombustible,true);
        if(okClicked)
           buscarCargaDeCombustible();
    }


    @FXML
    public void handleEditAcoplado(){
        CargaCombustible selectedItem = cargaCombustibleTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showCargaDeCombustibleEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar carga de combustible",
                    "Por favor seleccione una carga de combustible en la tabla.");

    }
    @FXML
    public void handleEliminarAcoplado(){
        CargaCombustible selectedItem = cargaCombustibleTableView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar carga de combustible",null,
                "Esta seguro de querer borrar la carga de combustible seleccionada? \nPara confirmar presione Aceptar.");
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            cargaCombustibleTableView.getItems().remove(
                    cargaCombustibleTableView.getSelectionModel().getSelectedIndex());
            cargaCombustibleRepository.delete(selectedItem);
        }else
            Alerta.alertaError("Seleccionar carga de combustible","Por favor selecciona una carga de combustible en la tabla");
    }
    public void buscarCargaDeCombustible(){
        this.cargaCombustibleData = cargaCombustibleRepository.view();
        cargaCombustibleTableView.setItems(cargaCombustibleData);
    }
    public void setOwner(Stage owner){
        this.owner = owner;

    }
    private boolean showCargaDeCombustibleEdit(CargaCombustible tempCargaDeCombustible, boolean b) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/cruds/CargaCombustibleEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva carga de combustible");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CargaCombustibleEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setCargaCombustible(tempCargaDeCombustible);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
