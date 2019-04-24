package application.view.sueldo.cruds;

import application.comunes.Alerta;
import application.model.info.CategoriaEmpleado;
import application.model.info.Empleado;
import application.model.sueldo.ConceptoCalculado;
import application.model.sueldo.ConceptoSueldo;
import application.model.sueldo.Liquidacion;
import application.model.sueldo.LiquidacionEmpleado;
import application.repository.info.CategoriaEmpleadoRepository;
import application.repository.sueldo.ConceptoSueldoRepository;
import application.repository.info.EmpleadoRepository;
import application.repository.sueldo.LiquidacionRepository;
import application.view.sueldo.EmpleadoALiquidar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.joda.time.DateTime;

import javax.xml.soap.Text;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LiquidacionSueldoController implements Initializable {
    @FXML
    private DatePicker desdeDatePicker;
    @FXML
    private DatePicker hastaDatePicker;
    @FXML
    private ComboBox<CategoriaEmpleado> categoriaEmpleadoComboBox;
    @FXML
    private TableView<Empleado> totalEmpleadoTableView;
    @FXML
    private TableColumn<Empleado, String> idTotalColumn;
    @FXML
    private TableColumn<Empleado, String> nombreTotalColumn;
    @FXML
    private TableColumn<Empleado, String> apellidoTotalColumn;
    @FXML
    private TableView<Empleado> liquidarEmpleadoTableView;
    @FXML
    private TableColumn<Empleado, String> liquidarIdColumn;
    @FXML
    private TableColumn<Empleado, String> liquidarNombreColumn;
    @FXML
    private TableColumn<Empleado, String> liquidarApellidoColumn;

    @FXML
    private Button addEmpleadoButton;
    @FXML
    private Button subsEmpleadoButton;

    @FXML
    private TableView<ConceptoSueldo> novedadesTableView;
    @FXML
    private TableColumn<ConceptoSueldo, String> codigoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> checkLiquidacionColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> conceptoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> cantidadColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> tipoCantidadColumn;
    @FXML
    private TableColumn<ConceptoSueldo, String> factorColumn;
    @FXML
    private DatePicker fechaLiquidacion;
    @FXML
    private Button liquidarButton;
    @FXML
    private TextField factorTextField;
    @FXML
    private Button cancelButton;

    private Stage owner;
    private boolean isNew;
    private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private LiquidacionRepository liquidacionRepository = new LiquidacionRepository();
    private ConceptoSueldoRepository conceptoSueldoRepository=new ConceptoSueldoRepository();
    private ObservableList<CategoriaEmpleado> categorias = FXCollections.observableArrayList();
    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();
    private ObservableList<EmpleadoALiquidar> liquidarEmpleados = FXCollections.observableArrayList();
    private ObservableList<ConceptoSueldo> conceptoSueldos = FXCollections.observableArrayList();

    public void setOwner(Stage owner){
        this.owner = owner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        novedadesTableView.setPlaceholder(new Label("No hay novedades para mostrar"));
        totalEmpleadoTableView.setPlaceholder(new Label("No hay empleados para mostrar"));
        liquidarEmpleadoTableView.setPlaceholder(new Label("No hay empleados a liquidar"));
        idTotalColumn.setCellValueFactory(cellData -> cellData.getValue().idEmpleadoProperty().asString());
        apellidoTotalColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        nombreTotalColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        liquidarIdColumn.setCellValueFactory(cellData -> cellData.getValue().idEmpleadoProperty().asString());
        liquidarApellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        liquidarNombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        checkLiquidacionColumn.setCellValueFactory(new PropertyValueFactory<ConceptoSueldo, String>("select"));
        checkLiquidacionColumn.setStyle( "-fx-alignment: CENTER;");
        codigoColumn.setCellValueFactory(cellData -> cellData.getValue().idConceptoSueldoProperty().asString());
        conceptoColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        tipoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoConceptoProperty());
        cantidadColumn.setCellValueFactory(celData -> celData.getValue().cantidadProperty().asString());
        tipoCantidadColumn.setCellValueFactory(cellData -> cellData.getValue().tipoCantidadProperty());
        factorColumn.setCellValueFactory(cellData -> cellData.getValue().factorProperty().asString());

        ponerFechaActual();
        cargarCategoriaEmpleado();
    }

    private void ponerFechaActual() {
        java.util.Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fechaLiquidacion.setValue(date);
    }

    private void cargarCategoriaEmpleado(){
        categorias = categoriaEmpleadoRepository.view();
        categoriaEmpleadoComboBox.setItems(categorias);
    }

    @FXML
    public void cargarTablaEmpleados(){

        if (categoriaEmpleadoComboBox.getSelectionModel().getSelectedItem() != null) {
            int id = categoriaEmpleadoComboBox.getSelectionModel().getSelectedItem().getIdCategoriaEmpleado();
            totalEmpleadoTableView.setItems(empleadoRepository.getEmpleadosByCategoriaEmpleado(id));
            validarListaEmpleados();
        }

    }

    private void validarListaEmpleados() {

        for (Empleado eLiquidar : liquidarEmpleadoTableView.getItems())
            for(Empleado eDuplicado : totalEmpleadoTableView.getItems())
                if (eLiquidar.getIdEmpleado() == eDuplicado.getIdEmpleado()) {
                    totalEmpleadoTableView.getItems().remove(eDuplicado);
                    break;
                }
    }

    @FXML
    private void agregarEmpleadoALiquidar(){

        if(totalEmpleadoTableView.getSelectionModel().getSelectedItem() != null) {
            liquidarEmpleadoTableView.getItems().add(totalEmpleadoTableView.getSelectionModel().getSelectedItem());
            liquidarEmpleados.add(new EmpleadoALiquidar(totalEmpleadoTableView.getSelectionModel().getSelectedItem()));
        }
        cargarTablaEmpleados();
        System.out.println("Lista Empleados a Liquidar: [agregarEmpleadoALiquidar] ");
        for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados )
            System.out.printf("Empleado a Liquidar : %s %s%n", empleadoALiquidar.getEmpleado().getNombre(),
                    empleadoALiquidar.getEmpleado().getApellido() );
    }

    @FXML
    private void agregarTodos(){

        if(!totalEmpleadoTableView.getItems().isEmpty())
            for(Empleado empleado : totalEmpleadoTableView.getItems()) {
                liquidarEmpleadoTableView.getItems().add(empleado);
                liquidarEmpleados.add(new EmpleadoALiquidar(empleado));
            }
        cargarTablaEmpleados();
    }

    @FXML
    private void quitarEmpleadoALiquidar(){

        if(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem() != null) {
            System.out.println("ID EMPLEADO A LIQUIDAR: " + liquidarEmpleadoTableView.getSelectionModel().getSelectedItem().getIdEmpleado());
            removeEmpleadoALiquidarById(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem().getIdEmpleado());
            liquidarEmpleadoTableView.getItems().remove(liquidarEmpleadoTableView.getSelectionModel().getSelectedItem());
        }
        cargarTablaEmpleados();
        novedadesTableView.getItems().clear();
        System.out.println("Lista Empleados a Liquidar: [quitarEmpleadoALiquidar] ");
        for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados )
            System.out.printf("Empleado a Liquidar : %s %s%n", empleadoALiquidar.getEmpleado().getNombre(),
                    empleadoALiquidar.getEmpleado().getApellido() );
    }

    @FXML
    private void quitarTodos(){

        if(!liquidarEmpleadoTableView.getItems().isEmpty()) {
            liquidarEmpleadoTableView.getItems().removeAll(liquidarEmpleadoTableView.getItems());
            liquidarEmpleados.removeAll(liquidarEmpleados);
        }
        novedadesTableView.getItems().clear();
        cargarTablaEmpleados();

    }


    @FXML
    private void cargarNovedades(){
        if (liquidarEmpleadoTableView.getSelectionModel().getSelectedItem() != null)
            novedadesTableView.setItems(liquidarEmpleados.get(getEmpleadoALiquidarById(
                    liquidarEmpleadoTableView.getSelectionModel().getSelectedItem().getIdEmpleado())).getConceptos());

    }

    @FXML
    private void cargarFactor(){
        if(novedadesTableView.getSelectionModel().getSelectedItem() != null) {
            factorTextField.setText(String.valueOf(novedadesTableView.getSelectionModel().getSelectedItem()
                    .getFactor()));
            liquidarEmpleados.get(getEmpleadoALiquidarById(liquidarEmpleadoTableView.getSelectionModel().
                    getSelectedItem().getIdEmpleado())).getConceptos().get(novedadesTableView.getItems().indexOf(
                    novedadesTableView.getSelectionModel().getSelectedItem())).setSelect(novedadesTableView.
                    getSelectionModel().getSelectedItem().getSelect().isSelected());

        }

    }

    @FXML
    private void agregarFactor(){
        if(novedadesTableView.getSelectionModel().getSelectedItem() != null)
            novedadesTableView.getSelectionModel().getSelectedItem().setFactor(
                    Double.parseDouble(factorTextField.getText())
            );
    }

    private int getEmpleadoALiquidarById(int idEmpleadoALiquidar){
        for(EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados)
            if (empleadoALiquidar.getEmpleado().getIdEmpleado() == idEmpleadoALiquidar)
                return liquidarEmpleados.indexOf(empleadoALiquidar);

        return 0;
    }

    private void removeEmpleadoALiquidarById(int idEmpleadoALiquidar){

        for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados)
            if (empleadoALiquidar.getEmpleado().getIdEmpleado() == idEmpleadoALiquidar) {
                liquidarEmpleados.remove(empleadoALiquidar);
                break;
            }

    }

    @FXML
    private void liquidarEmpleados(){
        separarConceptosByTipoConcepto();
        //crea un nuevo row de Liquidacion
        Liquidacion liquidacion = new Liquidacion();

        //Liquidacion de Empleados
        for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados)
        {
            List<ConceptoCalculado> remCalculados = new ArrayList<>();
            List<ConceptoCalculado> noRemCalculados = new ArrayList<>();
            List<ConceptoCalculado> retCalculados = new ArrayList<>();

            //Muestra lista de conceptos a liquidar
            System.out.println("%nCONCEPTOS A LIQUIDAR%n%n");
            for (ConceptoSueldo conceptoSueldo : empleadoALiquidar.getRemunerativos())
                System.out.printf("CONCEPTO REMUNERATIVO: %s%n" +
                                "              CANTIDAD: %f%n" +
                                "              FACTOR  : %f%n",  conceptoSueldo.getDescripcion(),conceptoSueldo.getCantidad(),
                        conceptoSueldo.getFactor());
            for (ConceptoSueldo conceptoSueldo : empleadoALiquidar.getNoRemunerativos())
                System.out.printf("CONCEPTO NO REMUNERATIVO: %s%n" +
                                "              CANTIDAD: %f%n" +
                                "              FACTOR  : %f%n",  conceptoSueldo.getDescripcion(),conceptoSueldo.getCantidad(),
                        conceptoSueldo.getFactor());
            for (ConceptoSueldo conceptoSueldo : empleadoALiquidar.getRetenciones())
                System.out.printf("CONCEPTO RETENCION: %s%n" +
                                "              CANTIDAD: %f%n" +
                                "              FACTOR  : %f%n",  conceptoSueldo.getDescripcion(),conceptoSueldo.getCantidad(),
                        conceptoSueldo.getFactor());


            //Liquidacion de Haberes Remunerativos
            for (ConceptoSueldo conceptoSueldo : empleadoALiquidar.getRemunerativos())
                remCalculados.add(new ConceptoCalculado(conceptoSueldo));

            //Liquidacion de Haberes No Remunerativos
            for (ConceptoSueldo conceptoSueldo : empleadoALiquidar.getNoRemunerativos())
                noRemCalculados.add(new ConceptoCalculado(conceptoSueldo));

            //Muestra lista de conceptos liquidados [REMUNERATIVOS Y NO REMUNERATIVOS]
            System.out.printf("%n%nCONCEPTOS LIQUIDADOS%n%n");
            for (ConceptoCalculado conceptoCalculado : remCalculados)
                System.out.printf("CALCULADO REMUNERATIVO: %s%n" +
                                "              CANTIDAD: %f%n" +
                                "              FACTOR  : %f%n" +
                                "      MONTO CALCULADO : %f%n",  conceptoCalculado.getDescripcion(),conceptoCalculado.getCantidad(),
                        conceptoCalculado.getFactor(), conceptoCalculado.getMontoCalculado());
            for (ConceptoCalculado conceptoCalculado : noRemCalculados)
                System.out.printf("CALCULADO NO REMUNERATIVO: %s%n" +
                                "              CANTIDAD: %f%n" +
                                "              FACTOR  : %f%n" +
                                "      MONTO CALCULADO : %f%n",  conceptoCalculado.getDescripcion(),conceptoCalculado.getCantidad(),
                        conceptoCalculado.getFactor(), conceptoCalculado.getMontoCalculado());



            //Calcular suma de Haberes Remunerativos
            double totalRemunerativos = 0;

            for (ConceptoCalculado remCalculado : remCalculados)
                totalRemunerativos += remCalculado.getMontoCalculado();

            //Actualizar factor de las retenciones y
            // Liquidacion de Retenciones
            for (ConceptoSueldo retencion : empleadoALiquidar.getRetenciones()) {
                retencion.setFactor(totalRemunerativos);
                retCalculados.add(new ConceptoCalculado(retencion));
            }

            //Calcular suma de Haberes No Remunerativos
            double totalNoRemunerativos = 0;

            for (ConceptoCalculado noRemCalculado : noRemCalculados)
                totalNoRemunerativos += noRemCalculado.getMontoCalculado();

            //Calcular suma de Retenciones
            double totalRetenciones = 0;

            for (ConceptoCalculado retCalculado : retCalculados)
                totalRetenciones += retCalculado.getMontoCalculado();

            //Crear nueva Liquidacion de Empleado y establecer los datos
            LiquidacionEmpleado liquidacionEmpleado = new LiquidacionEmpleado();
            liquidacionEmpleado.setTotalHaberesRemunerativos(totalRemunerativos);
            liquidacionEmpleado.setTotalHaberesNoRemunerativos(totalNoRemunerativos);
            liquidacionEmpleado.setTotalRetenciones(totalRetenciones);
            liquidacionEmpleado.setEmpleado(empleadoALiquidar.getEmpleado());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            liquidacionEmpleado.setInicioPeriodo(desdeDatePicker.getValue().format(formatter));
            liquidacionEmpleado.setFinPeriodo(hastaDatePicker.getValue().format(formatter));
            liquidacionEmpleado.setFechaLiquidacion(LocalDateTime.now().format(formatter));

            BigDecimal totalBruto = BigDecimal.valueOf(totalRemunerativos).add(BigDecimal.valueOf(totalNoRemunerativos));

            liquidacionEmpleado.setTotalBruto(totalBruto.doubleValue());

            BigDecimal importeNeto = BigDecimal.valueOf(totalRemunerativos).add(BigDecimal.valueOf(totalNoRemunerativos));
            importeNeto = importeNeto.subtract(BigDecimal.valueOf(totalRetenciones));

            liquidacionEmpleado.setImporteNeto(importeNeto.doubleValue());

            liquidacionEmpleado.setConceptosLiquidados(remCalculados);
            liquidacionEmpleado.getConceptosLiquidados().addAll(noRemCalculados);
            liquidacionEmpleado.getConceptosLiquidados().addAll(retCalculados);

            System.out.printf("%n%n------- Liquidación Empleado :%s %s ------- %n ", empleadoALiquidar.getEmpleado().getNombre(),
                    empleadoALiquidar.getEmpleado().getApellido());
            System.out.println("TOTAL HABERES REMUNERATIVOS: " + liquidacionEmpleado.getTotalHaberesRemunerativos());
            System.out.printf("Total haberes remunerativos   : $ %f%n" +
                            "Total haberes no remunerativos: $ %f%n" +
                            "Total retenciones             : $ %f%n" +
                            "------------------------------------%n" +
                            "Total Bruto                   : $ %f%n" +
                            "Importe Neto                  : $ %f%n" +
                            "------------------------------------%n",
                    liquidacionEmpleado.getTotalHaberesRemunerativos(), liquidacionEmpleado.getTotalHaberesNoRemunerativos(),
                    liquidacionEmpleado.getTotalRetenciones(), liquidacionEmpleado.getTotalBruto(), liquidacionEmpleado.getImporteNeto());

            //Agregar LiquidacionEmpleado a Liquidacion
            liquidacion.getLiquidacionesEmpleados().add(liquidacionEmpleado);
        }

        //calcular total de liquidacion empleado
        Double totalHaberesRemunerativos = 0.0;
        Double totalHaberesNoRemunerativos = 0.0;
        Double totalRetenciones = 0.0;

        for (LiquidacionEmpleado liquidacionEmpleado : liquidacion.getLiquidacionesEmpleados()) {
            totalHaberesRemunerativos += liquidacionEmpleado.getTotalHaberesRemunerativos();
            totalHaberesNoRemunerativos += liquidacionEmpleado.getTotalHaberesNoRemunerativos();
            totalRetenciones += liquidacionEmpleado.getTotalRetenciones();
        }
        liquidacion.setTotalHaberesRemunerativos(totalHaberesRemunerativos.doubleValue());
        liquidacion.setTotalHaberesNoRemunerativos(totalHaberesNoRemunerativos.doubleValue());
        liquidacion.setTotalRetenciones(totalRetenciones.doubleValue());

        //Grabar los datos en la base de datos
        liquidacionRepository.newLiquidacion(liquidacion);
    }

    private void separarConceptosByTipoConcepto(){
        System.out.println("------- separaConceptosByid -------");
        if (!liquidarEmpleados.isEmpty()){

            for (EmpleadoALiquidar empleadoALiquidar : liquidarEmpleados){

                System.out.printf("Empleado a Liquidar: %s %s %n", empleadoALiquidar.getEmpleado().getNombre(),
                        empleadoALiquidar.getEmpleado().getApellido());

                for(ConceptoSueldo conceptoSueldo : empleadoALiquidar.getConceptos()) {

                    System.out.printf("Concepto a Liquidar: %s %n Seleccionado: %s %n Tipo Concepto: %s %n ", conceptoSueldo.getDescripcion(),
                            conceptoSueldo.getSelect().isSelected(), conceptoSueldo.getTipoConcepto());

                    if (conceptoSueldo.getSelect().isSelected()) {

                        switch (conceptoSueldo.getTipoConcepto()) {
                            case "REMUNERATIVO":
                                empleadoALiquidar.getRemunerativos().add(conceptoSueldo);
                                break;
                            case "NO REMUNERATIVO":
                                empleadoALiquidar.getNoRemunerativos().add(conceptoSueldo);
                                break;
                            case "RETENCION":
                                empleadoALiquidar.getRetenciones().add(conceptoSueldo);
                                break;
                        }
                    }
                }
            }
        }

    }
    private boolean validarFechas(){
        boolean isOkDates = false;
        if (desdeDatePicker.getValue() != null || hastaDatePicker.getValue() != null){
            java.util.Date input = new Date();
            LocalDate actual = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if ( desdeDatePicker.getValue().compareTo(hastaDatePicker.getValue()) <= 0 &&
                    hastaDatePicker.getValue().compareTo(actual) <= 0){
                isOkDates =true;
            }
        }
        return isOkDates;
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (novedadesTableView.getItems().isEmpty())
            errorMessage += "No se selecciono ningun empleado a liquidar";
        if (!validarFechas())
            errorMessage += "\nFechas inválidas: \n Las fechas ingresadas no pueden superar a la fecha actual \n Fecha Desde debe ser menor a Fecha Hasta";
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
    @FXML
    public void handleOk(){
        //Valida que las fechas se ingresen correctamente
        if (isInputValid()) {
            liquidarEmpleados();
            owner.close();
        }

    }
    @FXML
    public void handleCancel(){
        owner.close();
    }




}