package application.view.compra;

import application.Main;
import application.comunes.Alerta;
import application.model.compra.Articulo;
import application.repository.compra.ArticuloRepository;
import application.view.compra.cruds.ArticuloEditController;
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

public class ArticulosController {
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonUpdate;
    @FXML
    private TableView<Articulo> articuloTableView;
    @FXML
    private TableColumn<Articulo, String> marcaTableColumn;
    @FXML
    private TableColumn<Articulo, String> modeloTableColumn;
    @FXML
    private TableColumn<Articulo, String> descripcionTableColumn;
    @FXML
    private TableColumn<Articulo, String> categoriaTableColumn;
    @FXML
    private TableColumn<Articulo, String> stockTableColumn;

    private Stage owner;
    private ObservableList<Articulo> articuloObservableList = FXCollections.observableArrayList();
    private ArticuloRepository articuloRepository = new ArticuloRepository();
    @FXML
    private void initialize(){
        marcaTableColumn.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        modeloTableColumn.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());
        descripcionTableColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        categoriaTableColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoria().nombreProperty());
        stockTableColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asString());
    }
    @FXML
    public void handleNew(){
        Articulo articulo = new Articulo();
        boolean okClicked = this.showEdit(articulo, true);
        if(okClicked)
            cargarArticulos();
    }

    @FXML
    public void handleUpdate(){
        Articulo articuloSelected = articuloTableView.getSelectionModel().getSelectedItem();
        if (articuloSelected!=null){
            this.showEdit(articuloSelected,false);
        }else
            Alerta.alertaError("Seleccionar Artículo",
                    "Por favor seleccione un artículo de la tabla.");

    }

    public boolean showEdit(Articulo articulo, boolean tipo) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/cruds/ArticuloEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (tipo)
                dialogStage.setTitle("Ingresar nuevo Artículo");
            else
                dialogStage.setTitle("Editar Artículo");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            ArticuloEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(tipo);
            controller.setDatos(articulo);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void cargarArticulos(){
        this.articuloObservableList = articuloRepository.view();
        articuloTableView.setItems(articuloObservableList);
    }
    public void setOwner(Stage owner){
        this.owner = owner;
    }
}
