package application.view.compra.cruds;

import application.Main;
import application.comunes.Alerta;
import application.model.compra.Articulo;
import application.model.compra.CategoriaArticulo;
import application.repository.info.ArticuloRepository;
import application.repository.info.CategoriaArticuloRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ArticuloEditController {
    @FXML
    private TextField marcaTextField;
    @FXML
    private TextField modeloTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private TextArea descripcionTextArea;
    @FXML
    private ComboBox<String> categoriaComboBox;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;


    private Stage dialogStage;
    private boolean isNew;
    private Articulo articulo;
    private boolean okClicked = false;
    private ArticuloRepository repository = new ArticuloRepository();
    private CategoriaArticuloRepository categoriaArticuloRepository = new CategoriaArticuloRepository();
    private ArrayList<Integer> categoriaIndices = guardarIndices(categoriaArticuloRepository.view());


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setDatos(Articulo datos) {
        articulo = datos;
        marcaTextField.setText(articulo.getMarca());
        modeloTextField.setText(articulo.getModelo());
        descripcionTextArea.setText(articulo.getDescripcion());
        categoriaComboBox.getSelectionModel().select(articulo.getCategoria());
        stockTextField.setText(String.valueOf(articulo.getStock()));

    }

    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private	void initialize(){
        refreshComboBox();
    }

    private void refreshComboBox(){
    ObservableList<String> categorias =  this.createAObservableListOfStrings(categoriaArticuloRepository.view());
    categoriaComboBox.setItems(categorias);
}
    private ObservableList<String> createAObservableListOfStrings(ObservableList<CategoriaArticulo> categoriaArticulos) {
        Iterator<CategoriaArticulo> iterator = categoriaArticulos.iterator();
        ObservableList<String> categorias = FXCollections.observableArrayList();
        while (iterator.hasNext()){
            categorias.add(iterator.next().getNombre());
        }
        return categorias;
    }
    private ArrayList<Integer> guardarIndices(ObservableList<CategoriaArticulo> categoriaArticulos){
        ArrayList<Integer> indices = new ArrayList<>();
        Iterator<CategoriaArticulo> iterator = categoriaArticulos.iterator();
        while (iterator.hasNext()){
            indices.add(iterator.next().getIdCategoriaArticulo());
        }
        return indices;
    }

    @FXML
    public void handleOk() {
        articulo.setMarca(marcaTextField.getText());
        articulo.setModelo(modeloTextField.getText());
        articulo.setDescripcion(descripcionTextArea.getText());
        articulo.setStock( Integer.parseInt(stockTextField.getText()));
        if (isInputValid()) {
            int indice = categoriaComboBox.getSelectionModel().getSelectedIndex();
            if (isNew) {
                repository.save(articulo,categoriaIndices.get(indice));
            } else {
                repository.update(articulo,categoriaIndices.get(indice));
            }
            okClicked = true;
            dialogStage.close();
        }

    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    public boolean isInputValid() {
        String errorMessage = "";
        if (marcaTextField.getText() == null || marcaTextField.getText().length() == 0) {
            errorMessage += "No valid brand name!\n";
        }
        if (modeloTextField.getText() == null || modeloTextField.getText().length() == 0) {
            errorMessage += "No valid model name!\n";
        }
        if (descripcionTextArea.getText() == null || descripcionTextArea.getText().length() == 0) {
            errorMessage += "No valid description!\n";
        }
       /* if (categoriaComboBox.is == null || modeloTextField.getText().length() == 0) {
            errorMessage += "No valid model name!\n";
        }*/
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
    @FXML
    public void handleNuevaCategoria(){
        CategoriaArticulo tempCategoria = new CategoriaArticulo();
        this.showCategoriaEdit(tempCategoria,true);
        refreshComboBox();

    }

    private boolean showCategoriaEdit(CategoriaArticulo tempCategoria, boolean b) {

        try {
            // Load the fxml file and create a new stage for the popup dialog.

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/application/view/compra/cruds/CategoriaArticuloEdit.fxml"));
            Group page = loader.load();


            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Artículo");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            CategoriaArticuloEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setCategoriaArticulo(tempCategoria);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
