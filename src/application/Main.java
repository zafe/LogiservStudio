package application;

import application.view.*;
import java.io.IOException;
import application.database.JDBCConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Logiserv Studio");
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
            loader.setLocation(Main.class.getResource("view/Login.fxml"));
            rootLayout = (BorderPane) loader.load();
            LoginController controller = loader.getController();
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
