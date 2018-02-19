package application.view.calculo;

import application.Main;
import application.comunes.Alerta;
import application.model.calculo.Acoplado;
import application.repository.calculo.AcopladoRepository;
import application.view.calculo.cruds.AcopladoEditController;
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

public class CargarAcopladoController {
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Acoplado> acopladoTableView;
    @FXML
    private TableColumn<Acoplado, String> idAcopladoTableColumn;
    @FXML
    private TableColumn<Acoplado, String> marcaTableColumn;
    @FXML
    private TableColumn<Acoplado, String> patenteTableColumn;

    private Stage owner;
    private ObservableList<Acoplado> acopladoData = FXCollections.observableArrayList();
    private AcopladoRepository acopladoRepository = new AcopladoRepository();


    /**
     * This method is responsible for charge all the data in the table view
     */
    @FXML
    private void initialize(){
        idAcopladoTableColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        marcaTableColumn.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        patenteTableColumn.setCellValueFactory(cellData -> cellData.getValue().patenteProperty());

    }

    @FXML
    public void handleNewAcoplado(){
        Acoplado tempAcoplado = new Acoplado();
        boolean okClicked = this.showAcopladoEdit(tempAcoplado,true);
        if(okClicked)
            acopladoData.add(tempAcoplado);
    }


    @FXML
    public void handleEditAcoplado(){
        Acoplado selectedItem = acopladoTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showAcopladoEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Acoplado",
                    "Por favor seleccione un Acoplado en la tabla.");

    }
    @FXML
    public void handleEliminarAcoplado(){
        Acoplado selectedItem = acopladoTableView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Acoplado",null,
                "Esta seguro de querer borrar el Acoplado seleccionado? \nPara confirmar presione Aceptar.");
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            acopladoTableView.getItems().remove(
                    acopladoTableView.getSelectionModel().getSelectedIndex());
            acopladoRepository.delete(selectedItem);
        }else
            Alerta.alertaError("Seleccionar Acoplado","Por favor selecciona un Acoplado en la tabla");
    }
    public void buscarAcoplados(){
        this.acopladoData = acopladoRepository.view();
        acopladoTableView.setItems(acopladoData);
    }
    public void setOwner(Stage owner){
        this.owner = owner;

    }
    private boolean showAcopladoEdit(Acoplado tempAcoplado, boolean b) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/cruds/AcopladoEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nuevo Acoplado");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            AcopladoEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setAcoplado(tempAcoplado);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
