package application.comunes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Alerta {
    protected Alerta(){

    }
    public static void alertaInfo(String titulo, String contenido){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();

    }
    public static void alertaInfo(String titulo, String header, String contenido){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
    }
    public static void alertaError(String titulo, String contenido){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
    }
    public static Optional<ButtonType> alertaConfirmacion(String titulo, String header, String contenido){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.initStyle(StageStyle.UNDECORATED);
        return alert.showAndWait();
    }
}
