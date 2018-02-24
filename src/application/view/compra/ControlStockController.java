package application.view.compra;

import application.Main;
import application.comunes.Alerta;
import application.model.compra.Articulo;
import application.model.compra.CategoriaArticulo;
import application.reports.AbstractJasperReports;
import application.repository.compra.ArticuloRepository;
import application.repository.compra.CategoriaArticuloRepository;
import application.view.compra.cruds.StockEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlStockController implements Initializable{
    @FXML
    private Button controlButton;
    @FXML
    private Button reportButton;
    @FXML
    private ComboBox<CategoriaArticulo> categoriasComboBox;
    @FXML
    private TableView<Articulo> articuloTableView;
    @FXML
    private TableColumn<Articulo, String> marcaTableColumn;
    @FXML
    private TableColumn<Articulo, String> descripcionTableColumn;
    @FXML
    private TableColumn<Articulo, String> stockTableColumn;

    private Stage owner;
    private ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    private ObservableList<CategoriaArticulo> categoriasArticulos = FXCollections.observableArrayList();
    private ArticuloRepository articuloRepository = new ArticuloRepository();
    private CategoriaArticuloRepository categoriaArticuloRepository = new CategoriaArticuloRepository();


    @FXML
    public void handleControlStock(){
        Articulo articuloSelected = articuloTableView.getSelectionModel().getSelectedItem();
        if (articuloSelected!=null){
            this.showEdit(articuloSelected);
        }else
            Alerta.alertaError("Seleccionar Artículo",
                    "Por favor seleccione un artículo de la tabla.");
        cargarArticulos();
    }

    public boolean showEdit(Articulo articulo) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/cruds/StockEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();

            dialogStage.setTitle("Controlar Stock de Artículo");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            StockEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
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
        this.articulos = articuloRepository.view();
        articuloTableView.setItems(articulos);
    }
    public void setOwner(Stage owner){
        this.owner = owner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCategoriasComboBox();
        marcaTableColumn.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        descripcionTableColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        stockTableColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asString());

    }

    private void cargarCategoriasComboBox() {
        categoriasArticulos = categoriaArticuloRepository.view();
        categoriasComboBox.setItems(categoriasArticulos);
    }
    @FXML
    private void filtrarCategoria(){
        if (!categoriasComboBox.getSelectionModel().isEmpty()){
            int idCategoriaArticulo = categoriasComboBox.getSelectionModel().getSelectedItem().getIdCategoriaArticulo();
            articuloTableView.setItems(articuloRepository.getArticulosByCategoria(idCategoriaArticulo));
        }
    }
    @FXML
    private void generarReporteStock(){
        AbstractJasperReports.createReport("src\\application\\reports\\ControlStock.jasper");
        AbstractJasperReports.showViewer();
    }
}
