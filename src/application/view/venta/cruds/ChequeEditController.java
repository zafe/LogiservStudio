package application.view.venta.cruds;

import application.comunes.Alerta;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.model.venta.Cheque;
import application.model.venta.Cliente;
import application.repository.info.DomicilioRepository;
import application.repository.info.LocalidadRepository;
import application.repository.info.ProvinciaRepository;
import application.repository.venta.ChequeRepository;
import application.repository.venta.ClienteRepository;
import javafx.beans.property.FloatProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChequeEditController {
    private boolean isNew;
    private Stage dialogStage;
    private boolean okClicked;
    private Cheque cheque;
    private ChequeRepository chequeRepository = new ChequeRepository();



    @FXML
    private DatePicker fechaEmisionPicker;
    @FXML
    private DatePicker fechaPagoPicker;
    @FXML
    private TextField codigoBancarioField;
    @FXML
    private ComboBox<String> bancoComboBox;
    @FXML
    private TextField montoField;
    @FXML
    private ComboBox<String> tipoChequeComboBox;
    @FXML
    private ComboBox<String> estadoChequeComboBox;
    @FXML
    private TextField comisionField;
    @FXML
    private TextField saldoEfectivoField;
    @FXML
    private DatePicker fechaCobroPicker;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }


    public void setDatos(Cheque cheque) {
        this.cheque = cheque;
        if (!isNew){

            //Adapto el formato a la forma en que se ven las fechas en la base de datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

            fechaEmisionPicker.setValue(LocalDate.parse(cheque.getFechaEmision(), formatter));
            fechaPagoPicker.setValue(LocalDate.parse(cheque.getFechaPago(), formatter));
            codigoBancarioField.setText(cheque.getCodigoBancario());
            bancoComboBox.getSelectionModel().select(cheque.getBanco());
            montoField.setText(String.valueOf(cheque.getMonto()));
            tipoChequeComboBox.getSelectionModel().select(cheque.getTipoCheque());
            estadoChequeComboBox.getSelectionModel().select(cheque.getEstadoCheque());
            //TODO HACER EL INITIALIZE DEL PAGO

        }
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (codigoBancarioField.getText() == null || codigoBancarioField.getText().length() == 0) {
            errorMessage += "Codigo bancario invalido\n";
        }
        if(montoField.getText()==null || montoField.getText().length() == 0){
            errorMessage += "Monto invalido\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }

    @FXML
    public void initialize() {

      //  bancoComboBox.setItems();
      //  estadoChequeComboBox.setItems();
      //  estadoChequeComboBox.setItems();

    }

    @FXML
    public void handleOk(){
        if (isInputValid()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            cheque.setFechaEmision(fechaEmisionPicker.getValue().format(formatter));
            cheque.setFechaPago(fechaPagoPicker.getValue().format(formatter));
            cheque.setCodigoBancario(codigoBancarioField.getText());
            cheque.setTipoCheque(tipoChequeComboBox.getValue());
            cheque.setEstadoCheque(estadoChequeComboBox.getValue());

            if(isNew)
                chequeRepository.save(cheque);
            else
                chequeRepository.update(cheque);

            okClicked=true;
            dialogStage.close();
        }


    }
    @FXML
    public void handleCancel(){
        dialogStage.close();

    }




}
