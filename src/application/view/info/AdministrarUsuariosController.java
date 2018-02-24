package application.view.info;

import application.Main;
import application.model.info.Usuario;
import application.repository.info.UsuarioRepository;
import application.view.info.cruds.UsuarioEditDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministrarUsuariosController {
	
	@FXML
	private TableView<Usuario> usuarioTable;
	@FXML
	private TableColumn<Usuario, String> usuarioColumn;
	@FXML
	private TableColumn<Usuario, String> nombreColumn;
	@FXML
	private TableColumn<Usuario, String> apellidoColumn;
	
	private ObservableList<Usuario> usuarioData = FXCollections.observableArrayList();
	
	Stage owner;
	
	public void buscarUsuarios(){
		this.usuarioData = UsuarioRepository.buscarUsuarios();
		usuarioTable.setItems(usuarioData);
	}
	
	@FXML
	private void initialize(){
		usuarioColumn.setCellValueFactory(cellData -> cellData.getValue().nombre_usuarioProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().nombreProperty());
		apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getEmpleado().apellidoProperty());
		}
	
	@FXML
	private void handleDeleteUsuario(){
		int selectedIndex = usuarioTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0){
			usuarioTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Seleccionar Usuario");
			alert.setHeaderText(null);
			alert.setContentText("Por favor selecciona un usuario en la tabla");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleNewUsuario() {
		Usuario tempUsuario = new Usuario();
		boolean okClicked = this.showUsuarioEditDialog(tempUsuario);
		if (okClicked) {
			usuarioData.add(tempUsuario);
		}
	}
	
	public void setOwner(Stage owner){
		this.owner = owner;
		
	}
	
	@FXML
	private void handleEditUsuario() {
		Usuario selectedUsuario = usuarioTable.getSelectionModel().getSelectedItem();
		if (selectedUsuario != null) {
			this.showUsuarioEditDialog(selectedUsuario);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Seleccionar Usuario");
			alert.setHeaderText(null);
			alert.setContentText("Por favor selecciona un usuario en la tabla");
			alert.showAndWait();
		}
		
		
	}
	
	public boolean showUsuarioEditDialog(Usuario usuario) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/info/cruds/EditarUsuarioDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Usuario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UsuarioEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUsuario(usuario);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
 
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	
	
}