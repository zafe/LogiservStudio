package application.view.compra.cruds;

import application.comunes.Alerta;
import application.model.compra.Articulo;
import application.model.compra.DetalleCompra;
import application.model.compra.FacturaCompra;
import application.model.compra.Proveedor;
import application.repository.compra.ArticuloRepository;
import application.repository.compra.DetalleCompraRepository;
import application.repository.compra.FacturaCompraRepository;
import application.repository.compra.ProveedorRepository;
import application.view.compra.ArticulosController;
import application.view.compra.ProveedoresController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

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
    private TextField cantidadField;

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
    private double costoTotal=0;
    private double subtotal =0;
    private boolean isNew;
    private boolean okClicked=false;


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
        if (!lineasTableView.getItems().isEmpty()){
            for (DetalleCompra linea :
                    lineasTableView.getItems()) {
                    for (Articulo articuloDuplicado: articuloComboBox.getItems())
                        if (linea.getArticulo().getIdArticulo() == articuloDuplicado.getIdArticulo())
                            articuloComboBox.getItems().remove(articuloDuplicado);
            }
        }
    }

    @FXML
    public void handleNewProveedor(){
        ProveedoresController proveedoresController = new ProveedoresController();
        proveedoresController.showEdit(new Proveedor(), true);
        refresh();
    }

    private void refresh() {
        cargarProveedores();
    }

    @FXML
    public void handleNewArticulo(){
        ArticulosController controller = new ArticulosController();
        controller.showEdit(new Articulo(), true);
        cargarArticulos();
    }
    @FXML
    public void setLabels(){
        if(isArticuloSetted()){
            Articulo articulo = articuloComboBox.getSelectionModel().getSelectedItem();
            marcaLabel.setText(articulo.getMarca());
            modeloLabel.setText(articulo.getModelo());
            stockLabel.setText(String.valueOf(articulo.getStock()));
            descripcionLabel.setText(articulo.getDescripcion());
            descripcionLabel.setWrapText(true);  /**@Isa: Salto de linea :D*/
        }
    }
    private void ponerFechaActual(){
        java.util.Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fechaDatePicker.setValue(date);
    }
    @FXML
    public void handleNewLine(){
        if(isArticuloSetted()){
            DetalleCompra linea = new DetalleCompra();
            linea.setCantidad(Integer.parseInt(cantidadField.getText()));
            linea.setPrecioUnitario(Float.parseFloat(precioField.getText()));
            linea.setArticulo(articuloComboBox.getValue());
            lineas.add(linea);
            lineasTableView.setItems(lineas);
            calcularTotal(linea);
            borrarElementos(linea.getArticulo());
        }else
            Alerta.alertaError("Error al seleccionar un articulo", "Por Favor seleccione un Articulo");

    }
    @FXML
    public void handleQuitLine(){
        
            Alerta.alertaError("Error al seleccionar un articulo", "Por Favor seleccione un Articulo");

    }
    private void calcularTotal(DetalleCompra linea) {
        subtotal = linea.getCantidad()*linea.getPrecioUnitario();
        costoTotal += subtotal;
        totalLabel.setText(String.valueOf(costoTotal));
    }
    private void calcularTotal(){
        for (DetalleCompra linea : lineasTableView.getItems()){
            subtotal = linea.getCantidad() * linea.getPrecioUnitario();
            costoTotal += subtotal;
        }
        totalLabel.setText(String.valueOf(costoTotal));
    }

    private void borrarElementos(Articulo articulo) {
        articuloComboBox.getItems().remove(articulo);
        articuloComboBox.getSelectionModel().clearSelection();
        modeloLabel.setText("");
        marcaLabel.setText("");
        stockLabel.setText("");
        descripcionLabel.setText("");
        precioField.setText("");
        cantidadField.setText("");
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
            idFacturaLabel.setText(String.valueOf(facturaSeleccionada.getIdFacturaCompra()));
            lineas = lineaRepository.view(factura.getIdFacturaCompra());
            lineasTableView.setItems(lineas);
            proveedorComboBox.getSelectionModel().select(facturaSeleccionada.getProveedor());
            calcularTotal();
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }
    /**
     *  @Isa
     * Validation Data
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (fechaDatePicker.getValue() == null || fechaDatePicker.getValue().toString().length() == 0) {
            errorMessage += "Fecha no ingresada correctamente.\n";
        }
        if(proveedorComboBox.getSelectionModel().isEmpty())
            errorMessage += "Proveedor no seleccionado.\n";
        if(lineasTableView.getItems().isEmpty())
            errorMessage += "No se agregó linea de compra de articulos.\n";
/*        if (precioField.getText()== null || precioField.getText().length() ==0 || !NumberUtils.isParsable(precioField.getText()))
            errorMessage += "El precio ingresado no corresponde a un número válido.\n";
        if (cantidadField.getText()== null || cantidadField.getText().length() ==0 || !NumberUtils.isParsable(cantidadField.getText()))
            errorMessage += "La cantidad ingresada no corresponde a un número válido.\n";*/

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }

    @FXML
    public void handleOK(){
        if (isInputValid()){
            factura.setFecha(fechaDatePicker.getValue().toString());
            factura.setProveedor(proveedorComboBox.getValue());
            factura.setTotal(Double.parseDouble(totalLabel.getText()));
            if (isNew){
                facturaCompraRepository.save(factura);
                factura.setIdFacturaCompra(facturaCompraRepository.getLastID());
                for (DetalleCompra linea : lineasTableView.getItems())
                    linea.setFacturaCompra(factura);
                for (DetalleCompra linea :
                        lineasTableView.getItems()) {
                    lineaRepository.save(linea);
                    articuloRepository.addStock(linea.getCantidad(), linea.getArticulo().getIdArticulo());
                }
            }else {
                facturaCompraRepository.update(factura);
                for (DetalleCompra linea :
                        lineasTableView.getItems()) {
                    lineaRepository.update(linea);
                }
            }
            okClicked=true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

}
