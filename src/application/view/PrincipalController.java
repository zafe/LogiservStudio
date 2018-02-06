package application.view;

import java.io.IOException;

import application.view.calculo.InfoFincaController;
import application.view.calculo.InfoIngenioController;
import application.view.calculo.InfoOrigenDestinoController;
import application.view.compra.ArticulosController;
import application.view.compra.CategoriaArticuloController;
import application.view.compra.CompraComprasController;
import application.view.compra.ProveedoresController;
import application.view.info.InfoCategoriaEmpleadoController;
import application.view.calculo.CargarCamionController;
import application.view.sueldo.LiquidacionController;
import application.view.sueldo.cruds.LiquidacionSueldoController;
import application.view.venta.CargarAcopladoController;
import javafx.fxml.FXML;
import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalController {
	
	private Stage primaryStage;
	private BorderPane rootLayout;

	public void setRootLayout(BorderPane root){
		this.rootLayout = root;
	}
	
	public void setPrimaryStage(Stage primary){
		this.primaryStage = primary;
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
            loader.setLocation(Main.class.getResource("view/venta/CargarAcoplado.fxml"));
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

//---------------MODULO ADMINISTRAR-------------------//
    @FXML
	private void showPersonOverview() {
    	try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AdministrarEmpleados.fxml"));
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
              loaderusuario.setLocation(Main.class.getResource("view/AdministrarUsuarios.fxml"));
              AnchorPane usuarioOverview = (AnchorPane) loaderusuario.load();

              // Set person overview into the center of root layout.
              rootLayout.setCenter(usuarioOverview);

              // Give the controller access to the main app.
              AdministrarUsuariosController usercontroller = loaderusuario.getController();
              usercontroller.setOwner(primaryStage);
              usercontroller.buscarUsuarios();

          } catch (IOException e) {
              e.printStackTrace();
          }
      }
    
    @FXML
  	private void showGPSOverview() {
      	try {
              // Load person overview.
              FXMLLoader loaderusuario = new FXMLLoader();
              loaderusuario.setLocation(Main.class.getResource("view/GPSWebView.fxml"));
              AnchorPane usuarioOverview = (AnchorPane) loaderusuario.load();

              // Set person overview into the center of root layout.
              rootLayout.setCenter(usuarioOverview);

              // Give the controller access to the main app.
              GPSWebViewController usercontroller = loaderusuario.getController();
              usercontroller.setOwner(primaryStage);
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
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            LiquidacionController controller = loader.getController();
            controller.setOwner(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
