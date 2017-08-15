package application.view.sueldo;

import application.model.info.Empleado;
import application.model.info.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SueldoLiquidarSueldoController {

	
	@FXML
	private TableView<Empleado> empleadoTable;
	@FXML
	private TableColumn<Empleado, String> nombreColumn;
	@FXML
	private TableColumn<Empleado, String> apellidoColumn;
	@FXML
	private TableColumn<Empleado, String> cuitColumn;
	@FXML
	private TableColumn<Empleado, String> categoriaColumn;
	@FXML
	private ComboBox<String> mesCombo;
	
	private ObservableList<Empleado> empleadoData = FXCollections.observableArrayList();

}
