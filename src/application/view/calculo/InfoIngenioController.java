package application.view.calculo;

import application.Main;
import application.comunes.Alerta;
import application.model.calculo.Ingenio;
import application.repository.calculo.IngenioRepository;
import application.view.calculo.cruds.IngenioEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoIngenioController implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Ingenio> ingenioTableView;
    @FXML
    private TableColumn<Ingenio, String> idColumn;
    @FXML
    private TableColumn<Ingenio, String> latitudColumn;
    @FXML
    private TableColumn<Ingenio, String> longitudColumn;
    @FXML
    private TableColumn<Ingenio, String> nombreColumn;
    @FXML
    private TableColumn<Ingenio, String> arranqueColumn;
    @FXML
    private TableColumn<Ingenio, String> tarifaColumn;







    private Stage owner;
    private ObservableList<Ingenio> ingenios = FXCollections.observableArrayList();
    private IngenioRepository ingenioRepository = new IngenioRepository();


    public void setOwner(Stage owner){
        this.owner = owner;
    }


    @FXML
    public void handleNew(){
        Ingenio tempIngenio = new Ingenio();
        boolean okClicked = this.showEdit(tempIngenio,true);
        if(okClicked)
            ingenios.add(tempIngenio);
    }


    @FXML
    public void handleEdit(){
        Ingenio selectedItem = ingenioTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Ingenio",
                    "Por favor seleccione un Ingenio en la lista.");

    }
    @FXML
    public void handleEliminar(){
        Ingenio selectedItem = ingenioTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Ingenio",null,
                    "Esta seguro de querer borrar el Ingenio seleccionado? \nPara confirmar presione Aceptar.");
            if(resultado.isPresent() && resultado.get() == ButtonType.OK){
                ingenioTableView.getItems().remove(
                        ingenioTableView.getSelectionModel().getSelectedIndex());
                ingenioRepository.delete(selectedItem.getIdIngenio());
            }
        }else{
            Alerta.alertaError("Seleccionar Ingenio","Por favor selecciona un Ingenio en la lista");
        }
    }
    private boolean showEdit(Ingenio tempIngenio, boolean b) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/cruds/IngenioEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nuevo Ingenio");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            IngenioEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setIngenio(tempIngenio);

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
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idIngenioProperty().asString());
        latitudColumn.setCellValueFactory(cellData-> cellData.getValue().latitudProperty().asString());
        longitudColumn.setCellValueFactory(cellData -> cellData.getValue().longitudProperty().asString());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        arranqueColumn.setCellValueFactory(cellData -> cellData.getValue().arranqueProperty().asString());
        tarifaColumn.setCellValueFactory(cellData -> cellData.getValue().tarifaProperty().asString());

        WebEngine engine = webView.getEngine();
        engine.load("https://www.google.com.ar/maps/@-26.8115531,-65.2008328,17z");
    }
    public void buscarIngenios(){
        this.ingenios = ingenioRepository.view();
        ingenioTableView.setItems(ingenios);
    }
}
