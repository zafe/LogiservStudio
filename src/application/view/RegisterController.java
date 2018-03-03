package application.view;

import application.Main;
import application.comunes.Alerta;
import application.model.info.*;
import application.repository.info.*;
import application.view.info.InfoCategoriaEmpleadoController;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public Hyperlink getHlLogin() {
        return hlLogin;
    }

    @FXML
    private Hyperlink hlLogin;
    @FXML
    private TextField nombreEmpleadoField;
    @FXML
    private TextField apellidoEmpleadoField;
    @FXML
    private ComboBox<CategoriaEmpleado> categoriaEmpleadoComboBox;
    @FXML
    private DatePicker fechaNacimiento;
    @FXML
    private TextField cuitField;
    @FXML
    private TextField calleField;
    @FXML
    private TextField numeroField;
    @FXML
    private ComboBox<Localidad> localidadComboBox;
    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repasswordField;
    @FXML
    private Button registrarButton;

    private Stage dialogStage;

    private CategoriaEmpleadoRepository categoriaEmpleadoRepository = new CategoriaEmpleadoRepository();
    private LocalidadRepository localidadRepository = new LocalidadRepository();
    private DomicilioRepository domicilioRepository = new DomicilioRepository();
    private EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository();



    private Empleado empleado = new Empleado();
    private Usuario usuario = new Usuario();









    @FXML
    public void handleRegistro(){
        if (isInputValid()) {
            empleado.setNombre(nombreEmpleadoField.getText());
            empleado.setApellido(apellidoEmpleadoField.getText());
            empleado.setCategoriaEmpleado(categoriaEmpleadoComboBox.getValue());
            empleado.setNacimiento(fechaNacimiento.getValue().toString());
            empleado.setCuit(cuitField.getText());
            ponerFechaAlta();
            empleado.setDomicilio(new Domicilio(0,localidadComboBox.getValue(), calleField.getText(), numeroField.getText()));
            usuario.setEmpleado(empleado);
            usuario.setNombre_usuario(usuarioField.getText());
            usuario.setPassword(passwordField.getText());
            domicilioRepository.save(empleado.getDomicilio());
            empleadoRepository.save(empleado);
            usuario.getEmpleado().setIdEmpleado(empleadoRepository.getLastID());
            usuarioRepository.create(usuario);
            handleLogin();
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //con esto hacemos que el boton de registro se habilite cuando ingresamos todos los datos, so cool xD
        BooleanBinding boolenBinding =
                nombreEmpleadoField.textProperty().isEmpty()
                        .or(apellidoEmpleadoField.textProperty().isEmpty()
                                .or(calleField.textProperty().isEmpty())
                                .or(numeroField.textProperty().isEmpty())
                                .or(usuarioField.textProperty().isEmpty())
                                .or(passwordField.textProperty().isEmpty())
                                .or(repasswordField.textProperty().isEmpty())
                        );
        registrarButton.disableProperty().bind(boolenBinding);
        cargarCategorias();
        cargarLocalidades();

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }





    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private void handleLogin(){
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
            Stage stage = (Stage) hlLogin.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarCategorias() {
        categoriaEmpleadoComboBox.setItems(categoriaEmpleadoRepository.view());
    }
    private void cargarLocalidades() {
        localidadComboBox.setItems(localidadRepository.localidadesDeTucuman());
    }

    @FXML
    private void handleNewCategoriaEmpleado(){
        InfoCategoriaEmpleadoController controller = new InfoCategoriaEmpleadoController();
        controller.showCategoriaEmpleadoEdit(new CategoriaEmpleado(), true);
        cargarCategorias();
    }

    /**
     *  @Isa
     * Validation Data
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (categoriaEmpleadoComboBox.getValue() == null || categoriaEmpleadoComboBox.getSelectionModel().isEmpty())
            errorMessage += "Categoria Empleado no ingresada correctamente.\n";
        if (localidadComboBox.getValue() == null || localidadComboBox.getSelectionModel().isEmpty())
            errorMessage += "Localidad no selecionada.\n";
        if(!passMatch())
            errorMessage += "Contraseñas no coinciden.\n";
        if (errorMessage.length() == 0)
            return true;
        else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
    private boolean passMatch() {
        boolean passMatch;
        String password = passwordField.getText();
        String rePass = repasswordField.getText();
        if (password.matches(rePass)) {
            passMatch = true;
        } else {
            passMatch = false;
        }
        return passMatch;
    }
    private void ponerFechaAlta(){
        java.util.Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        empleado.setFechaAlta(date.toString());
    }

}
