package application.view;

import application.model.compra.FacturaCompra;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CompraComprasController {

	@FXML
	private TableView<FacturaCompra> comprasTable;
	@FXML
	private TableColumn<FacturaCompra, String> codigoFacturaColumn;
	@FXML
	private TableColumn<FacturaCompra, String> fechaColumn;
	@FXML
	private TableColumn<FacturaCompra, String> montoColumn;
	@FXML
	private TableColumn<FacturaCompra, String> proveedorColumn;
	
	private Stage owner;
	
	@FXML
	private void initialize(){
		
	}
	
	public void setOwner(Stage owner){
		this.owner = owner;
	}
}
