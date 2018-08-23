package application.view.venta;

import application.Main;
import application.comunes.Alerta;
import application.model.venta.Cheque;
import application.repository.venta.ChequeRepository;
import application.view.venta.cruds.ChequeEditController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ChequesController {

	@FXML
	private TableView<Cheque> chequeTable;
	@FXML
	private TableColumn<Cheque, String> fechaEmisionColumn;
	@FXML
	private TableColumn<Cheque, String> fechaPagoColumn;
	@FXML
	private TableColumn<Cheque, String> codigoBancarioColumn;
	@FXML
	private TableColumn<Cheque, String> bancoColumn;
	@FXML
	private TableColumn<Cheque, String> montoColumn;
	@FXML
	private TableColumn<Cheque, String> tipoChequeColumn;
	@FXML
	private TableColumn<Cheque, String> estadoColumn;



	private Stage owner;
	private ObservableList<Cheque> chequeData = FXCollections.observableArrayList();
	private ChequeRepository repository = new ChequeRepository();
	
	public void buscarViajes(){
		this.chequeData = repository.view();
			chequeTable.setItems(chequeData);
	}


	@FXML
	private void initialize() {
		codigoBancarioColumn.setCellValueFactory(cellData -> cellData.getValue().codigoBancarioProperty());
		fechaEmisionColumn.setCellValueFactory(cellData -> cellData.getValue().fechaEmisionProperty());
		fechaPagoColumn.setCellValueFactory(cellData -> cellData.getValue().fechaPagoProperty());
		bancoColumn.setCellValueFactory(cellData -> cellData.getValue().bancoProperty());
		montoColumn.setCellValueFactory(cellData -> cellData.getValue().montoProperty().asString());
		tipoChequeColumn.setCellValueFactory(cellData -> cellData.getValue().tipoChequeProperty());
		estadoColumn.setCellValueFactory(cellData -> cellData.getValue().estadoChequeProperty());
	}

	public void setOwner(Stage owner){
		this.owner = owner;

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewCheque() {
		Cheque temp = new Cheque();
		boolean okClicked = this.showChequeEditDialog(temp, true);
		if(okClicked)
			chequeData.add(temp);
	}
	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditCheque() {
		Cheque selectedCheque = chequeTable.getSelectionModel().getSelectedItem();
		if (selectedCheque != null)
			this.showChequeEditDialog(selectedCheque, false);
		else
			Alerta.alertaError("Seleccionar Viajes", "Por favor seleccione un Viaje en la tabla.");
	}
	/**
	 * Called when the user clicks on the logicDelete button.
	 */
	@FXML
	private void handleDeleteCheque() {
		Cheque selectedCheque = chequeTable.getSelectionModel().getSelectedItem();
		if (selectedCheque != null){
			Optional<ButtonType> resultado = Alerta.alertaConfirmacion("Eliminar Viaje", null,
					"¿Está seguro de borrar el Viaje Seleccionado?");
			if (resultado.isPresent() && resultado.get() == ButtonType.OK){
				chequeTable.getItems().remove(chequeTable.getSelectionModel().getSelectedIndex());
				repository.delete(selectedCheque.getIdCheque());
			}
		}


	}

	private boolean showChequeEditDialog(Cheque temp, boolean b) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/venta/cruds/ChequeEdit.fxml"));
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nuevo Cheque");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(owner);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

//TODO Editar Viaje por Cheque
            ChequeEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsNew(b);
            controller.setDatos(temp);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
