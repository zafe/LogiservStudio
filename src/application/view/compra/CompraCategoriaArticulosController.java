package application.view.compra;

import application.model.compra.CategoriaArticulo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CompraCategoriaArticulosController {

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

    @FXML
    private void initialize(){
        /*idCategoriaTableColumn.setCellValueFactory(cellData -> cellData.getValue().idCategoriaArticuloProperty());*/
        nombreCategoriaTableColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

    }

    @FXML
    public void handleNuevaCategoria(){
        CategoriaArticulo categoriaArticulo = new CategoriaArticulo();
        boolean okClicked;

    }
    @FXML
    public void handleEditCategoria(){

    }
    @FXML
    public void handleEliminarCategoria(){
        int seleccionado = categoriaArticuloTableView.getSelectionModel().getSelectedIndex();
        if (seleccionado>=0){
            categoriaArticuloTableView.getItems().remove(seleccionado);
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleccionar Categoria");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona una categoria en la tabla");
            alert.showAndWait();
        }
    }






}
