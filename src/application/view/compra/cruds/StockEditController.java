package application.view.compra.cruds;

import application.Main;
import application.comunes.Alerta;
import application.model.compra.Articulo;
import application.model.compra.CategoriaArticulo;
import application.repository.compra.ArticuloRepository;
import application.repository.compra.CategoriaArticuloRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockEditController implements Initializable{
    @FXML
    private Label marcaLabel;
    @FXML
    private Label modeloLabel;
    @FXML
    private TextField stockTextField;
    @FXML
    private Label descripcionLabel;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;


    private Stage dialogStage;
    private Articulo articulo;
    private boolean okClicked = false;
    private ArticuloRepository repository = new ArticuloRepository();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setDatos(Articulo datos) {
        articulo = datos;
        marcaLabel.setText(articulo.getMarca());
        modeloLabel.setText(articulo.getModelo());
        descripcionLabel.setText(articulo.getDescripcion());
        stockTextField.setText(String.valueOf(articulo.getStock()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    public void handleOk() {
        if (isInputValid()){
            articulo.setStock(Integer.parseInt(stockTextField.getText()));
            repository.updateStock(articulo);
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
        if (stockTextField.getText() == null || stockTextField.getText().length() == 0 || !NumberUtils.isParsable(stockTextField.getText()))
            errorMessage += "El stock debe ser un numero entero\n";
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        descripcionLabel.setWrapText(true);

    }
}
