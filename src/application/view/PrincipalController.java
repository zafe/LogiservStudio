package application.view;

import java.io.IOException;

import application.view.compra.ArticulosController;
import application.view.compra.CategoriaArticuloController;
import application.view.compra.ProveedoresController;
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
//---------------MODULO COMPRA------------------------//
	
	@FXML
	private void showComprasOverview() {
    	try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CompraCompras.fxml"));
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
    
    
	

}
