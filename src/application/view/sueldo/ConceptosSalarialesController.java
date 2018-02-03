package application.view.sueldo;

import application.model.sueldo.ConceptoSueldo;
import application.repository.info.ConceptoSueldoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConceptosSalarialesController implements Initializable {
    private Stage owner;
    @FXML
    private TableView<ConceptoSueldo> conceptoSueldoTableView;
    @FXML
    private TableColumn<ConceptoSueldo, String> idConceptoSueldoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> descripcionColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> cantidadColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoConceptoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoCantidadColumn;

    ConceptoSueldoRepository conceptoSueldoRepository = new ConceptoSueldoRepository();
    ObservableList<ConceptoSueldo> conceptoSueldos = FXCollections.observableArrayList();





















    public void setOwner(Stage owner){
        this.owner = owner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTable();
        idConceptoSueldoColumn.setCellValueFactory(cellData -> cellData.getValue().idConceptoSueldoProperty().asString());
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        cantidadColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asString());
        tipoConceptoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoConceptoProperty());
        tipoCantidadColumn.setCellValueFactory(cellData -> cellData.getValue().tipoCantidadProperty());

    }

    private void cargarTable() {
        conceptoSueldos = conceptoSueldoRepository.view();
        conceptoSueldoTableView.setItems(conceptoSueldos);
    }
}
