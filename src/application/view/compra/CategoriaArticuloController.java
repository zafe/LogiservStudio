package application.view.compra;

import application.Main;
import application.comunes.Alerta;
import application.model.compra.CategoriaArticulo;
import application.repository.info.CategoriaArticuloRepository;
import application.view.compra.cruds.CategoriaArticuloEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CategoriaArticuloController {
    @FXML
    private Button btnNuevaCategoria;
    @FXML
    private Button btnEditarCategoria;
    @FXML
    private Button btnEliminarCategoria;
    @FXML
    private TableView<CategoriaArticulo> categoriaArticuloTableView;
    @FXML
    private TableColumn<CategoriaArticulo, String> idCategoriaTableColumn;
    @FXML
    private TableColumn<CategoriaArticulo, String> nombreCategoriaTableColumn;

    private Stage owner;
    private ObservableList<CategoriaArticulo> categoriaArticulosData = FXCollections.observableArrayList();
    private CategoriaArticuloRepository categoriaRepo = new CategoriaArticuloRepository();


    @FXML
    private void initialize(){
        idCategoriaTableColumn.setCellValueFactory(cellData -> cellData.getValue().idCategoriaArticuloProperty().asString());
        nombreCategoriaTableColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

    }
    @FXML
    public void handleNuevaCategoria(){
        CategoriaArticulo tempCategoria = new CategoriaArticulo();
        boolean okClicked = this.showCategoriaEdit(tempCategoria,true);
        if(okClicked)
            categoriaArticulosData.add(tempCategoria);
    }
    @FXML
    public void handleEditCategoria(){
        CategoriaArticulo selectedItem = categoriaArticuloTableView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showCategoriaEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Categoría de Artículo",
                    "Por favor selecciona una categoría en la tabla.");

    }
    @FXML
    public void handleEliminarCategoria(){
        CategoriaArticulo categoriaSeleccionada = categoriaArticuloTableView.getSelectionModel().getSelectedItem();
        Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Categoría",null,
                "Esta seguro de querer borrar la categoria seleccionada? \nPara confirmar presione Aceptar.");
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            categoriaArticuloTableView.getItems().remove(
                    categoriaArticuloTableView.getSelectionModel().getSelectedIndex());
            categoriaRepo.delete(categoriaSeleccionada);
        }else
            Alerta.alertaError("Seleccionar Categoría","Por favor selecciona una categoría en la tabla");
    }
    public void buscarCategorias(){
        this.categoriaArticulosData = categoriaRepo.viewAll();
        categoriaArticuloTableView.setItems(categoriaArticulosData);
    }
    public void setOwner(Stage owner){
        this.owner = owner;

    }
    public boolean showCategoriaEdit(CategoriaArticulo categoriaArticulo, boolean tipo){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/application/view/compra/cruds/CategoriaArticuloEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva Categoria Artículo");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            CategoriaArticuloEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(tipo);
            controller.setCategoriaArticulo(categoriaArticulo);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
