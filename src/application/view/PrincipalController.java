package application.view;

import application.Main;
import application.model.info.Usuario;
import application.view.calculo.*;
import application.view.compra.*;
import application.view.info.*;
import application.view.inicio.HomeController;
import application.view.inicio.OrganizacionController;
import application.view.sueldo.ConceptosSalarialesController;
import application.view.sueldo.LiquidacionesController;
import application.view.venta.*;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import javax.swing.text.Style;
import java.io.IOException;
import java.time.LocalTime;

public class PrincipalController {

	private Stage primaryStage;
	private BorderPane rootLayout;
	@FXML
	private Accordion modulosAccordion;
    @FXML
    private TitledPane inicioTitledPane;
    @FXML
    private Label usuarioLabel;
    @FXML
    private Hyperlink cerrarSesion;

    private Usuario userOn;

    @FXML
    private void handleCerrarSesion(){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Login.fxml"));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Acceso a LogiServ App");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            LoginController controller = loader.getController();


            // Show the dialog and wait until the user closes it
            dialogStage.show();

            //Cierro la ventana de login
            Stage stage = (Stage) primaryStage.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public void setUserOn(Usuario userOn) {
        this.userOn = userOn;
        usuarioLabel.setText(userOn.getNombre_usuario());
    }

    public void setRootLayout(BorderPane root){
		this.rootLayout = root;
        new JMetro(JMetro.Style.LIGHT).applyTheme(rootLayout);

    }

	public void setPrimaryStage(Stage primary){
		this.primaryStage = primary;
        this.primaryStage.getIcons().add(new Image("resources/logiserv-icon.png"));

    }

    //---------------MODULO VENTAS------------------------//

    @FXML
    private void showViajesOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/venta/AdministrarViajes.fxml"));
            AnchorPane viajesOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(viajesOverview);

            // Give the controller access to the main app.
            AdministrarViajesController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarViajes();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showFacturacionOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/venta/Facturacion.fxml"));
            AnchorPane viajesOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(viajesOverview);

            // Give the controller access to the main app.
            FacturacionController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarFacturaciones();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showClientesOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/venta/VentaCliente.fxml"));
            AnchorPane clientesOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(clientesOverview);

            // Give the controller access to the main app.
            VentaClienteController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarClientes();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showChequesOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/venta/Cheques.fxml"));
            AnchorPane chequesOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(chequesOverview);

            // Give the controller access to the main app.
            ChequesController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarViajes();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showCalendarioOverview(){

        CalendarView calendarView = new CalendarView();

        Calendar cheques = new Calendar("Cheques");
        Calendar pagos = new Calendar("Pagos");

        Entry<String> dentistAppointment = new Entry<>("Pago Cheque uno");
        cheques.addEntry(dentistAppointment);

        Entry<String> dentisAppointment = new Entry<>("Pago Cheque dos 222");
        pagos.addEntry(dentisAppointment);

        cheques.setStyle(Calendar.Style.STYLE1);
        pagos.setStyle(Calendar.Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(cheques, pagos);


        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        AnchorPane calendarioOverview = new AnchorPane();
        rootLayout.setCenter(calendarView);

        /*
        try{
            // Load category overview.
            //FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(Main.class.getResource("view/venta/Calendario2.fxml"));
            AnchorPane calendarioOverview = new AnchorPane();//loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(calendarioOverview);


            // Give the controller access to the main app.
            CalendarioController controller = new CalendarioController();//loader.getController();
            //controller.setOwner(primaryStage);
            controller.initialize(primaryStage);


        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
    //---------------MODULO INFORMACION------------------------//

	@FXML
    private void showCategoriaEmpleadosOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/info/InfoCategoriaEmpleado.fxml"));
            AnchorPane ceOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(ceOverview);

            // Give the controller access to the main app.
            InfoCategoriaEmpleadoController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarCategoriaEmpleados();


        }catch (IOException e){
            e.printStackTrace();
        }
        }

    @FXML
    private void showFamiliares(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/info/GrupoFamiliar.fxml"));
            AnchorPane anchorPane = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            GrupoFamiliarController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.obtenerFamiliares();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showDesempleados(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/info/Desempleados.fxml"));
            AnchorPane anchorPane = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            DesempleadosController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarEmpleados();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //---------------MODULO CALCULO------------------------//
    @FXML
    private void showCamionesOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/CargarCamion.fxml"));
            AnchorPane camionOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(camionOverview);

            // Give the controller access to the main app.
            CargarCamionController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarCamiones();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void showFincasOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/InfoFinca.fxml"));
            AnchorPane anchorPane = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            InfoFincaController controller = loader.getController();
            controller.setOwner(primaryStage);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    private void showIngeniosOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/InfoIngenio.fxml"));
            AnchorPane anchorPane = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            InfoIngenioController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarIngenios();
        }catch (IOException e){
            e.printStackTrace();
        }
        }

    @FXML
    private void showAcopladosOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/CargarAcoplado.fxml"));
            AnchorPane acopladoOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(acopladoOverview);

            // Give the controller access to the main app.
            CargarAcopladoController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarAcoplados();


        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void showDistanciasOverview(){
	    try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/calculo/InfoOrigenDestino.fxml"));
            AnchorPane anchorPane = loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);
            // Give the controller access to the main app.
            InfoOrigenDestinoController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarDistancias();
	    }catch (IOException e){
	        e.printStackTrace();
	    }
	}
//---------------MODULO COMPRA------------------------//

	@FXML
	private void showComprasOverview() {
    	try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/CompraCompras.fxml"));
            AnchorPane pane = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(pane);

            // Give the controller access to the main app.
            CompraComprasController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.obtenerCompras();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showProveedorOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/Proveedores.fxml"));
            AnchorPane proveedorOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(proveedorOverview);

            // Give the controller access to the main app.
            ProveedoresController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.obtenerProveedores();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    private void showArticuloOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/Articulos.fxml"));
            AnchorPane articuloOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(articuloOverview);

            // Give the controller access to the main app.
           ArticulosController controller = loader.getController();
           controller.setOwner(primaryStage);
           controller.cargarArticulos();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showCategoriaArticuloOverview(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/CategoriaArticulos.fxml"));
            AnchorPane categoryOverview = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(categoryOverview);

            // Give the controller access to the main app.
            CategoriaArticuloController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarCategorias();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    private void showControlStock(){
        try{
            // Load category overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/compra/ControlStock.fxml"));
            AnchorPane anchorPane = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            ControlStockController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.cargarArticulos();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

//---------------MODULO ADMINISTRAR-------------------//
    @FXML
	private void showPersonOverview() {
    	try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/info/AdministrarEmpleados.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            AdministrarEmpleadosController controller = loader.getController();
            controller.setOwner(primaryStage);
            controller.buscarEmpleados();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
  	private void showUsuarioOverview() {
      	try {
              // Load person overview.
              FXMLLoader loaderusuario = new FXMLLoader();
              loaderusuario.setLocation(Main.class.getResource("view/info/AdministrarUsuarios.fxml"));
              AnchorPane usuarioOverview = (AnchorPane) loaderusuario.load();

              // Set person overview into the center of root layout.
              rootLayout.setCenter(usuarioOverview);

              // Give the controller access to the main app.
              AdministrarUsuariosController usercontroller = loaderusuario.getController();
              usercontroller.setOwner(primaryStage);
              usercontroller.buscarUsuarios();
              usercontroller.setUserSesion(userOn);

          } catch (IOException e) {
              e.printStackTrace();
          }
      }

    //---------------MODULO Sueldos-------------------//
    @FXML
    private void showSueldosOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/sueldo/Liquidaciones.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            LiquidacionesController controller = loader.getController();
            controller.setOwner(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showConceptosSalarialesOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/sueldo/ConceptosSalariales.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            ConceptosSalarialesController controller = loader.getController();
            controller.setOwner(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void showInicio() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/inicio/Home.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            HomeController controller = loader.getController();
            controller.setOwner(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showOrganizacionData() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/inicio/Organizacion.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(anchorPane);

            // Give the controller access to the main app.
            OrganizacionController controller = loader.getController();
            controller.setOwner(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion() {
            //Todo: hacer
    }

    public void setHome(){
	    modulosAccordion.setExpandedPane(inicioTitledPane);
	    showInicio();
    }

}
