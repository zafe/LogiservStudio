package application.view.calculo;

import application.Main;
import application.comunes.Alerta;
import application.model.calculo.Finca;
import application.repository.calculo.FincaRepository;
import application.view.calculo.cruds.FincaEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoFincaController implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private ListView<Finca> fincaListView;





    private Stage owner;
    private ObservableList<Finca> fincas = FXCollections.observableArrayList();
    private FincaRepository fincaRepository = new FincaRepository();


    public void setOwner(Stage owner){
        this.owner = owner;
    }


    @FXML
    public void handleNew(){
        Finca tempFinca = new Finca();
        boolean okClicked = this.showEdit(tempFinca,true);
        if(okClicked)
            fincas.add(tempFinca);
    }


    @FXML
    public void handleEdit(){
        Finca selectedItem = fincaListView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
            this.showEdit(selectedItem,false);
        else
            Alerta.alertaError("Seleccionar Finca",
                    "Por favor seleccione una Finca en la lista.");

    }
    @FXML
    public void handleEliminar(){
        Finca selectedItem = fincaListView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Finca",null,
                    "Esta seguro de querer borrar la finca seleccionada? \nPara confirmar presione Aceptar.");
            if(resultado.isPresent() && resultado.get() == ButtonType.OK){
                fincaListView.getItems().remove(
                        fincaListView.getSelectionModel().getSelectedIndex());
                fincaRepository.delete(selectedItem.getIdFinca());
            }

        }else{
            Alerta.alertaError("Seleccionar Finca","Por favor selecciona una Finca en la lista");
        }
    }
    private boolean showEdit(Finca tempFinca, boolean b) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/cruds/FincaEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva Finca");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            FincaEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setFinca(tempFinca);

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
        fincas = fincaRepository.view();
        fincaListView.setItems(fincas);
        WebEngine engine = webView.getEngine();
        engine.load("https://www.google.com.ar/maps/@-26.8115531,-65.2008328,17z");
    }
}
