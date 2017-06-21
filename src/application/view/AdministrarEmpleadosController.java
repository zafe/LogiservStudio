package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import application.Main;
import application.model.info.Empleado;

public class AdministrarEmpleadosController {

	@FXML
	private TableView<Empleado>  empleadoTable;
	@FXML
	private TableColumn<Empleado, String> nombreColumn;
	@FXML
	private TableColumn<Empleado, String> apellidoColumn;
	
	private Main main;
	
	public AdministrarEmpleadosController(){
		
	}
	
	@FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
    }
	

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.main = mainApp;

        // Add observable list data to the table
        empleadoTable.setItems(mainApp.getEmpleadoData());
    }
}
