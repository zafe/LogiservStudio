package application.view.inicio;

import application.Main;
import application.model.venta.Organizacion;
import application.repository.venta.OrganizacionRepository;
import application.view.inicio.crud.OrganizacionEditController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrganizacionController implements Initializable{
    @FXML
    private Label nombreOrganizacionLabel;
    @FXML
    private Label cuitOrganizacionLabel;
    @FXML
    private Label razonSocialOrganizacionLabel;
    @FXML
    private Label apoderadoOrganizacionLabel;
    @FXML
    private Label domicilioOrganizacionLabel;
    @FXML
    private Label telefonoOrganizacionLabel;
    @FXML
    private Button actualizarDatosOrganizacionLabel;

    Organizacion organizacion = new Organizacion();
    OrganizacionRepository repository = new OrganizacionRepository();
    private Stage owner;

    public void setOwner(Stage owner) {
        this.owner = owner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatosOrganizacion();
    }

    private void cargarDatosOrganizacion() {
        organizacion = repository.get();
        nombreOrganizacionLabel.setText(organizacion.getNombreOrg());
        cuitOrganizacionLabel.setText(organizacion.getCuitOrg());
//        razonSocialOrganizacionLabel.setText(organizacion.getRazonSocial());
        apoderadoOrganizacionLabel.setText(organizacion.getApoderadoOrg().getApellido() + ", " + organizacion.getApoderadoOrg().getNombre());
        domicilioOrganizacionLabel.setText(organizacion.getDomicilioOrg().getCalle() + " " + organizacion.getDomicilioOrg().getNumero());
//        telefonoOrganizacionLabel.setText(organizacion.getTelefono());
    }

    @FXML
    public void handleNew(){
        handleEdit(organizacion);
    }

    private void handleEdit(Organizacion organizacion){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/inicio/crud/OrganizacionEdit.fxml"));
            Group page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar datos de organización");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            OrganizacionEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDatos(organizacion);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
