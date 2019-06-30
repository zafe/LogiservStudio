package application.view.calculo;

import application.Main;
import application.comunes.Alerta;
import application.model.calculo.Ingenio;
import application.model.calculo.OrigenDestino;
import application.repository.calculo.FincaRepository;
import application.repository.calculo.IngenioRepository;
import application.repository.calculo.OrigenDestinoRepository;
import application.view.calculo.cruds.DistanciasEditController;
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

public class InfoOrigenDestinoController implements Initializable {
    @FXML
    private TableView<OrigenDestino> tableView;
    @FXML
    private TableColumn<OrigenDestino, String> idColumn;
    @FXML
    private TableColumn<OrigenDestino, String> ingenioColumn;
    @FXML
    private TableColumn<OrigenDestino, String> fincaColumn;
    @FXML
    private TableColumn<OrigenDestino, String> distanciaColumn;
    @FXML
    private ComboBox<Ingenio> buscarPorIngenioComboBox;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button eliminarButton;


    private Stage owner;
    private ObservableList<OrigenDestino> distancias = FXCollections.observableArrayList();
    private OrigenDestinoRepository distanciasRepository = new OrigenDestinoRepository();


    private FincaRepository fincaRepository = new FincaRepository();
    private IngenioRepository ingenioRepository = new IngenioRepository();
    private ObservableList<String> fincas = FXCollections.observableArrayList();
    private ObservableList<String> ingenios = FXCollections.observableArrayList();
    public void setOwner(Stage owner){
        this.owner = owner;
    }


    @FXML
    public void handleNew(){
        OrigenDestino temp = new OrigenDestino();
        boolean okClicked = this.showEdit(temp,true);
        if(okClicked)
            distancias.add(temp);
    }


    @FXML
    public void handleEdit(){
        OrigenDestino selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Distancia",
                    "Por favor seleccione una Distancia en la lista.");

    }
    @FXML
    public void handleEliminar(){
        OrigenDestino selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Distancia",null,
                    "Esta seguro de querer borrar la Distancia seleccionada? \nPara confirmar presione Aceptar.");
            if(resultado.isPresent() && resultado.get() == ButtonType.OK){
                tableView.getItems().remove(
                        tableView.getSelectionModel().getSelectedIndex());
                distanciasRepository.delete(selectedItem.getIdOrigenDestino());
            }
        }else{
            Alerta.alertaError("Seleccionar Ingenio","Por favor selecciona un Ingenio en la lista");
        }
    }
    private boolean showEdit(OrigenDestino distancia, boolean b) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/cruds/DistanciasEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva Distancia");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            DistanciasEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            if (b)
                controller.quitarChecks();
            controller.setIsNew(b);
            controller.setDistancia(distancia);
            fincas=fincaRepository.listOfFincas();
            ingenios=ingenioRepository.listOfIngenios();

            controller.cargarListas(fincas,ingenios);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarIngenioComboBox();
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idOrigenDestinoProperty().asString());
        ingenioColumn.setCellValueFactory(cellData -> cellData.getValue().nombreIngenioProperty());
        fincaColumn.setCellValueFactory(cellData -> cellData.getValue().nombreFincaProperty());
        distanciaColumn.setCellValueFactory(cellData -> cellData.getValue().distanciaKMProperty().asString());

    }

    private void cargarIngenioComboBox() {
        buscarPorIngenioComboBox.setItems(ingenioRepository.view());
    }

    public void buscarDistancias(){
        this.distancias = distanciasRepository.view();
        tableView.setItems(distancias);
        if (!buscarPorIngenioComboBox.getSelectionModel().isEmpty()){
            distancias = distanciasRepository.viewByIngenio(buscarPorIngenioComboBox.getSelectionModel().getSelectedItem().getIdIngenio());
            tableView.setItems(distancias);
        }
    }
}