package application.view.calculo.cruds;

import application.comunes.Alerta;
import application.model.calculo.OrigenDestino;
import application.repository.calculo.OrigenDestinoRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class DistanciasEditController implements Initializable{
    @FXML
    private ListView<String> ingenioListView;
    @FXML
    private ListView<String> fincaListView;
    @FXML
    private TextField distanciaTextField;
    @FXML
    private Button btnOk;
    @FXML
    private CheckBox ingenioCheckBox;
    @FXML
    private CheckBox fincaCheckBox;


    private Stage dialogStage;
    private boolean isNew;
    private OrigenDestino origenDestino;
    private boolean okClicked = false;
    private OrigenDestinoRepository repository = new OrigenDestinoRepository();



    @FXML
    public void handleOk(){
        if (isInputValid()){
            origenDestino.setNombreFinca(fincaListView.getSelectionModel().getSelectedItem());
            origenDestino.setNombreIngenio(ingenioListView.getSelectionModel().getSelectedItem());
            origenDestino.setDistanciaKM(Float.parseFloat(distanciaTextField.getText()));
            if (isNew){
                repository.save(origenDestino);
            }else {
                repository.update(origenDestino);
            }
            okClicked=true;
            dialogStage.close();
        }

    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (distanciaTextField.getText() == null || distanciaTextField.getText().length() == 0 || !NumberUtils.isParsable(distanciaTextField.getText())) {
            errorMessage += "Distancia no Ingresada correctamente.\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }

    public void setDistancia(OrigenDestino distancia) {
        origenDestino = distancia;
        if (!isNew){
            fincaListView.getSelectionModel().select(origenDestino.getNombreFinca());
            ingenioListView.getSelectionModel().select(origenDestino.getNombreIngenio());
            ingenioListView.setDisable(true);
            fincaListView.setDisable(true);
        }
        distanciaTextField.setText(String.valueOf(origenDestino.getDistanciaKM()));

    }
    public void cargarListas(ObservableList<String> fincas, ObservableList<String> ingenios){
        fincaListView.setItems(fincas);
        ingenioListView.setItems(ingenios);
    }
    @FXML
    public void enableListFinca(){
        if(fincaListView.isDisable())
            fincaListView.setDisable(false);
        else
            fincaListView.setDisable(true);
    }
    @FXML
    public void enableListIngenio(){
        if(ingenioListView.isDisable())
            ingenioListView.setDisable(false);
        else
            ingenioListView.setDisable(true);
    }

    public void quitarChecks() {
        fincaCheckBox.setVisible(false);
        ingenioCheckBox.setVisible(false);
    }
}