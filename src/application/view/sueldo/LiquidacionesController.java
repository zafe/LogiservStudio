package application.view.sueldo;

import application.Main;
import application.model.info.Empleado;
import application.model.sueldo.LiquidacionEmpleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LiquidacionesController implements Initializable{


    @FXML
    private TableView<LiquidacionEmpleado> liquidacionTable;
    @FXML
    private  TableView<Empleado> empleadosTable;
    @FXML
    private Button nuevaLiquidacionButton;
    @FXML
    private Button reportesButton;
    @FXML
    private TableColumn<LiquidacionEmpleado, String> codigoColumn;
    @FXML
    private TableColumn<LiquidacionEmpleado, String> desdeColumn;
    @FXML
    private TableColumn<LiquidacionEmpleado, String> hastaColumn;
    //    @FXML
//    private TableColumn<, String> hrColumn;
//    @FXML
//    private  TableColumn<, String> hnrColumn;
//    @FXML
//    private TableColumn<, String> retencionesColumn;
    @FXML
    private  TableColumn<Empleado, String> legajoColumn;
    @FXML
    private TableColumn<Empleado, String> apellidoColumn;
    @FXML
    private TableColumn<Empleado, String> nombreColumn;
    @FXML
    private TableColumn<Empleado, String> categoriaColumn;
    @FXML
    private TableColumn<LiquidacionEmpleado, String> hrEmpleadoColumn;
    @FXML
    private TableColumn<LiquidacionEmpleado, String> hnrEmpleadoColumn;
    @FXML
    private TableColumn<LiquidacionEmpleado, String> retencionesEmpleadoColumn;


    private Stage owner;

    private ObservableList<LiquidacionEmpleado> liquidacion = FXCollections.observableArrayList();

    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    public void setOwner(Stage owner){
        this.owner = owner;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codigoColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        desdeColumn.setCellValueFactory(cellData -> cellData.getValue().inicioPeriodoProperty());
        hastaColumn.setCellValueFactory(cellData -> cellData.getValue().finPeriodoProperty());
//        hrColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesRemunerativosProperty().asString());
//        hnrColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesNoRemunerativosProperty().asString());
//        retencionesColumn.setCellValueFactory(cellData -> cellData.getValue().totalRetencionesProperty().asString());

        legajoColumn.setCellValueFactory(cellData -> cellData.getValue().idEmpleadoProperty().asString());
        apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        categoriaColumn.setCellValueFactory(cellData -> cellData.getValue().categoriaProperty());
        hrEmpleadoColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesRemunerativosProperty().asString());
        hnrEmpleadoColumn.setCellValueFactory(cellData -> cellData.getValue().totalHaberesNoRemunerativosProperty().asString());
        retencionesEmpleadoColumn.setCellValueFactory(cellData -> cellData.getValue().totalRetencionesProperty().asString());


    }

    @FXML
    private void handleNew(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/sueldo/cruds/LiquidacionSueldo.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva Liquidacion");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);



            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
