package application.view.compra.cruds;

import application.comunes.Alerta;
import application.model.compra.Articulo;
import application.model.compra.DetalleCompra;
import application.model.compra.FacturaCompra;
import application.model.compra.Proveedor;
import application.repository.info.ArticuloRepository;
import application.repository.info.DetalleCompraRepository;
import application.repository.info.FacturaCompraRepository;
import application.repository.info.ProveedorRepository;
import application.view.compra.ArticulosController;
import application.view.compra.ProveedoresController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class FacturaEditController implements Initializable {
    @FXML
    private Label idFacturaLabel;
    @FXML
    private DatePicker fechaDatePicker;
    @FXML
    private ComboBox<Proveedor> proveedorComboBox;
    @FXML
    private ComboBox<Articulo> articuloComboBox;
    @FXML
    private Label marcaLabel;
    @FXML
    private Label modeloLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private Label descripcionLabel;
    @FXML
    private TextField precioField;
    @FXML
    private ComboBox<Integer> cantidadCb;

    @FXML
    private TableView<DetalleCompra> lineasTableView;
    @FXML
    private TableColumn<DetalleCompra, String> descripcionColumn;
    @FXML
    private TableColumn<DetalleCompra, String> cantidadColumn;
    @FXML
    private TableColumn<DetalleCompra, String> precioUnitarioColumn;
    @FXML
    private TableColumn<DetalleCompra, String> subTotalColumn;
    @FXML
    private Label totalLabel;
    @FXML
    private Button newProveedorButton;
    @FXML
    private Button newArticuloButton;
    @FXML
    private Button agregarLineaButton;
    @FXML
    private Button quitarLineaButton;
    @FXML
    private Button okButton;
    @FXML
    private Button closeButton;

    private Stage owner;
    private Stage dialogStage;
    private ObservableList<DetalleCompra> lineas = FXCollections.observableArrayList();
    private ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
    private ObservableList<Articulo> articulos = FXCollections.observableArrayList();
    private FacturaCompraRepository facturaCompraRepository = new FacturaCompraRepository();
    private DetalleCompraRepository lineaRepository = new DetalleCompraRepository();
    private ProveedorRepository proveedorRepository = new ProveedorRepository();
    private ArticuloRepository articuloRepository = new ArticuloRepository();
    private FacturaCompra factura;
    private boolean isNew;
    private boolean okClicked=false;
    private ObservableList<Integer> cantidades= FXCollections.observableArrayList();


    private void cargarIdNuevo(){
        int idNuevo = facturaCompraRepository.getLastID()+1;
        idFacturaLabel.setText(String.valueOf(idNuevo));
    }
    private void cargarProveedores(){
        proveedores = proveedorRepository.view();
        proveedorComboBox.setItems(proveedores);
    }
    private void cargarArticulos(){
        articulos = articuloRepository.view();
        articuloComboBox.setItems(articulos);
    }

    @FXML
    public void handleNewProveedor(){
        ProveedoresController proveedoresController = new ProveedoresController();
        proveedoresController.showEdit(new Proveedor(), true);
        refresh();
    }

    private void refresh() {
        cargarProveedores();
        cargarArticulos();
    }

    @FXML
    public void handleNewArticulo(){
        ArticulosController controller = new ArticulosController();
        controller.showEdit(new Articulo(), true);
        refresh();
    }
    @FXML
    public void setLabels(){
        Articulo articulo = articuloComboBox.getSelectionModel().getSelectedItem();
        marcaLabel.setText(articulo.getMarca());
        modeloLabel.setText(articulo.getModelo());
        stockLabel.setText(String.valueOf(articulo.getStock()));
        descripcionLabel.setText(articulo.getDescripcion());
        cantidades = poblarCantidades();
        cantidadCb.setItems(cantidades);
    }
    private void ponerFechaActual(){
        java.util.Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fechaDatePicker.setValue(date);
    }
    private ObservableList<Integer> poblarCantidades(){
        int size = articuloComboBox.getSelectionModel().getSelectedItem().getStock();
        ObservableList<Integer> nros = FXCollections.observableArrayList();
        for (int i=1; i<=size; i++){
            nros.add(i);
        }
        return nros;
    }


    @FXML
    public void handleNewLine(){
        if(isArticuloSetted()){
            DetalleCompra linea = new DetalleCompra();
            linea.setCantidad(cantidadCb.getValue());
            linea.setPrecioUnitario(Float.parseFloat(precioField.getText()));
            linea.setArticulo(articuloComboBox.getValue());
            lineas.add(linea);
            lineasTableView.setItems(lineas);
            borrarElementos(linea.getArticulo());
        }else
            Alerta.alertaError("Error al seleccionar un articulo", "Por Favor ingrese un Articulo");

    }

    private void borrarElementos(Articulo articulo) {
//        articuloComboBox.getItems().remove(articulo);
//        articuloComboBox.getSelectionModel().clearSelection();
       /* modeloLabel.setText("");
        marcaLabel.setText("");
        stockLabel.setText("");
        descripcionLabel.setText("");
        precioField.setText("");*/
        cantidadCb.getItems().removeAll(cantidades);
    }


    private boolean isArticuloSetted(){
        boolean isMyComboBoxEmpty = articuloComboBox.getSelectionModel().isEmpty();
        return !isMyComboBoxEmpty;
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ponerFechaActual();
        cargarIdNuevo();
        cargarProveedores();
        cargarArticulos();
        descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().getArticulo().descripcionProperty());
        cantidadColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asString());
        precioUnitarioColumn.setCellValueFactory(cellData -> cellData.getValue().precioUnitarioProperty().asString());
        subTotalColumn.setCellValueFactory(
                cellData -> cellData.getValue().precioUnitarioProperty()
                        .multiply(
                                cellData.getValue().cantidadProperty()).asString());
    }
    public void setOwner(Stage owner){
        this.owner = owner;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setDatos(FacturaCompra facturaSeleccionada) {
        this.factura = facturaSeleccionada;
        if(!isNew){
            lineas = lineaRepository.view(factura.getIdFacturaCompra());
            lineasTableView.setItems(lineas);
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }
}
