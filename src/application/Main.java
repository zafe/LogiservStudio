package application;

import application.database.JDBCConnection;
import application.view.LoginController;
import application.view.PrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;

public class Main extends Application {
	private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Logiserv Studio");
        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("resources/logiserv-icon.png"));
        JDBCConnection.getInstanceConnection();
        initRootLayout();
    }

	 /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {

            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            //para usar el principal directamente, cambiar la direccion y descomentar el set
//            loader.setLocation(Main.class.getResource("view/Principal.fxml"));
            loader.setLocation(Main.class.getResource("view/Login.fxml"));
            rootLayout = (BorderPane) loader.load();
            LoginController controller = loader.getController();
//            PrincipalController controller = loader.getController();
//            controller.setRootLayout(rootLayout);     //   <---------- de la ventana principal
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		launch(args);
	}
}
