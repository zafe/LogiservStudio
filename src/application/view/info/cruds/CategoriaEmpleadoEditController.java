package application.view.info.cruds;

import application.comunes.Alerta;
import application.model.info.CategoriaEmpleado;
import application.repository.info.CategoriaEmpleadoRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoriaEmpleadoEditController {
	 @FXML
	    private Label titulo;
	    @FXML
	    private TextField nombreCategoriaField;
	    @FXML
	    private Button btnOk;
	    @FXML
	    private Button btnCancel;

	    private Stage dialogStage;
	    private boolean isNew;
	    private CategoriaEmpleado categoriaEmpleado;
	    private boolean okClicked = false;
	    private CategoriaEmpleadoRepository reporsitory= new CategoriaEmpleadoRepository();

	    public void setIsNew(boolean bandera){
	        this.isNew = bandera;
	    }
	    @FXML
	    private	void initialize(){}

	    @FXML
	    public void handleOk(){

	        if (isInputValid()){
	            categoriaEmpleado.setNombre(nombreCategoriaField.getText());
	            if (isNew){
	                reporsitory.save(categoriaEmpleado);
	            }else {
	                reporsitory.update(categoriaEmpleado);
	            }
	            okClicked=true;
	            dialogStage.close();
	        }

	    }
	    @FXML
	    public void handleCancel(){
	        dialogStage.close();
	    }
	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	    public void setCategoriaEmpleado(CategoriaEmpleado categoriaEmpleado){
	        this.categoriaEmpleado = categoriaEmpleado;
	        nombreCategoriaField.setText(categoriaEmpleado.getNombre());
	    }
	    public boolean isOkClicked(){
	        return okClicked;
	    }
	    private boolean isInputValid() {
	        String errorMessage = "";

	        if (nombreCategoriaField.getText() == null || nombreCategoriaField.getText().length() == 0) {
	            errorMessage += "No se ingresó una categoria válida\n";
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            Alerta.alertaError("Datos inválidos", errorMessage);
	            return false;
	        }
	    }
	}
