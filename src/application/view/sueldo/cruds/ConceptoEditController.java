package application.view.sueldo.cruds;

import application.comunes.Alerta;
import application.model.enums.TipoCantidad;
import application.model.info.CategoriaEmpleado;
import application.model.sueldo.ConceptoSueldo;
import application.repository.info.CamionRepository;
import application.repository.info.CategoriaEmpleadoRepository;
import application.repository.info.ConceptoSueldoRepository;
import application.repository.info.TipoLiquidacionRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ConceptoEditController implements Initializable{

    @FXML
    private RadioButton haberRemunerativoRadioButton;
    @FXML
    private RadioButton haberNoRemunerativoRadioButton;
    @FXML
    private RadioButton retencionRadioButton;
    @FXML
    private TextField cantidadTextField;
    @FXML
    private TextField descripcionColField;
    @FXML
    private ComboBox<String> tipoCantidadComboBox;
    @FXML
    private TableView<CategoriaEmpleado> categoriaEmpleadoTableView;
    @FXML
    private TableColumn<CategoriaEmpleado, String> nombreCategoriaColumn;
    @FXML
    private TableColumn<CategoriaEmpleado, String> categoriaSeleccionadaColumn;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private Stage dialogStage;
    private boolean isNew;
    private boolean okClicked = false;
    private ConceptoSueldo conceptoSueldo;
    private ConceptoSueldoRepository repository = new ConceptoSueldoRepository();
    private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
    private TipoLiquidacionRepository tipoLiquidacionRepository = new TipoLiquidacionRepository();
    private ObservableList<CategoriaEmpleado> categorias = FXCollections.observableArrayList();

    private ToggleGroup group = new ToggleGroup();

    private void configRadioButtons(){
        haberRemunerativoRadioButton.setToggleGroup(group);
        haberNoRemunerativoRadioButton.setToggleGroup(group);
        retencionRadioButton.setToggleGroup(group);
    }


    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }


    @FXML
    public void handleOk(){
        if (isInputValid()){
            ConceptoSueldo conceptoSueldo = new ConceptoSueldo();
            conceptoSueldo.setDescripcion(descripcionColField.getText());
            conceptoSueldo.setCantidad(Float.parseFloat(cantidadTextField.getText()));
            conceptoSueldo.setTipoCantidad(tipoCantidadComboBox.getSelectionModel().getSelectedItem());
            if (haberNoRemunerativoRadioButton.isSelected())
                conceptoSueldo.setTipoConcepto("NO REMUNERATIVO");
            if (haberRemunerativoRadioButton.isSelected())
                conceptoSueldo.setTipoConcepto("REMUNERATIVO");
            if (retencionRadioButton.isSelected())
                conceptoSueldo.setTipoConcepto("RETENCION");
            if (isNew){
                repository.save(conceptoSueldo);
                tipoLiquidacionRepository.save(categoriaEmpleadoTableView.getSelectionModel().getSelectedItem().getIdCategoriaEmpleado());
            }
            else {
                repository.update(conceptoSueldo);
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

    public void setDatos(ConceptoSueldo conceptoSueldo){
   /*     this.conceptoSueldo = conceptoSueldo;
        marcaField.setText(conceptoSueldo.getMarca());
        modeloField.setText(conceptoSueldo.getModelo());
        patenteField.setText(conceptoSueldo.getPatente());*/
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (descripcionColField.getText() == null || descripcionColField.getText().length() == 0)
            errorMessage += "Descripcion del concepto no ingresado\n";
        if (haberRemunerativoRadioButton.isSelected() || haberNoRemunerativoRadioButton.isSelected() || retencionRadioButton.isSelected())
            errorMessage += "Tipo de concepto no seleccionado\n";
        if (cantidadTextField.getText() == null || cantidadTextField.getText().length() == 0 || !NumberUtils.isParsable(cantidadTextField.getText()))
            errorMessage += "Cantidad no ingresada correctamente\n";
        if (tipoCantidadComboBox.getSelectionModel().isEmpty())
            errorMessage += "Por favor seleecione un tipo de cantidad (porcentaje, fijo o unidad).\n";
        if (tablas()==0)
            errorMessage += "Seleccione la/las categorias asignadas al concepto.";
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configRadioButtons();
        setComboBox();
        cargarCategorias();
        nombreCategoriaColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        categoriaSeleccionadaColumn.setCellValueFactory(new PropertyValueFactory<CategoriaEmpleado, String>("select"));
        categoriaSeleccionadaColumn.setStyle( "-fx-alignment: CENTER;");
    }

    private void cargarCategorias(){
        categorias = categoriaEmpleadoRepository.view();
        categorias.add(new CategoriaEmpleado(0, "Todos"));
        categoriaEmpleadoTableView.setItems(categorias);
    }
    private void setComboBox(){
        ObservableList<String> tipos = FXCollections.observableArrayList();
        tipos.setAll(TipoCantidad.FIJO.toString(), TipoCantidad.UNIDAD.toString(),TipoCantidad.PORCENTAJE.toString());
        tipoCantidadComboBox.setItems(tipos);
    }
    private int tablas(){
        int cantidad=0;
        for (CategoriaEmpleado categoria :
                categoriaEmpleadoTableView.getItems()) {
            if (categoria.getSelect().isSelected())
                cantidad++;
        }
        return cantidad;
    }
}