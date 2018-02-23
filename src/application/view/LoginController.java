package application.view;

import application.Main;
import application.model.compra.Proveedor;
import application.model.info.Usuario;
import application.repository.info.UsuarioRepository;
import com.sun.prism.paint.Color;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {
    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button btnLogin;
    @FXML
    private Hyperlink hlCrateAccount;
    @FXML
    private Label errorLoginLabel;

    private String nombreUsuario;
    private String password;

    Usuario usuario = new Usuario();
    UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding boolenBinding = usuarioField.textProperty().isEmpty()
                .or(passwordField.textProperty().isEmpty());
        btnLogin.disableProperty().bind(boolenBinding);
    }
    @FXML
    private void hlCreateAnAccount(){
        loadRegistration();
    }
    
    
    @FXML
    private void btnLogin(ActionEvent event){
        nombreUsuario = usuarioField.getText();
        password = passwordField.getText();

        String passMD5 = usuario.encryptMD5(password);
        boolean okLogin = usuarioRepository.login(nombreUsuario,passMD5);

        System.out.println("Login: "+ okLogin);
        if (okLogin){
            try {
                // Load root layout from fxml file.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/Principal.fxml"));
                BorderPane root = loader.load();

                Parent parent = loader.getRoot();
                Stage adminPanelStage = new Stage();
                adminPanelStage.setTitle("LogiServ app - usuario: " + nombreUsuario + " conectado.");
                adminPanelStage.setMaximized(true);
                PrincipalController controller = loader.getController();
                controller.setRootLayout(root);
                controller.setHome();
                // Show the scene containing the root layout.
                Scene scene = new Scene(parent);
                adminPanelStage.setScene(scene);
                adminPanelStage.show();

                //Cierro la ventana de login
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
            errorLoginLabel.setText("Usuario y/o contraseña incorrectos.");
    }
    

    private void loadRegistration() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Register.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registro de usuario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            RegisterController controller = loader.getController();
            controller.setDialogStage(dialogStage);


            // Show the dialog and wait until the user closes it
            dialogStage.show();

            //Cierro la ventana de login
            Stage stage = (Stage) hlCrateAccount.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void pfUserNameOnHitEnter(ActionEvent event) {
        btnLogin(event);
    }


    @FXML
    private void pfUserPassOnHitEnter(ActionEvent event) {
        btnLogin(event);
    }
}
