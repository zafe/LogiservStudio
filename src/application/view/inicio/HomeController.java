package application.view.inicio;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    @FXML
    private ImageView imageView;
    @FXML
    private Label nombreOrganizacion;

    private Stage owner;



    private void setImageView(){

    }

    public void setOwner(Stage owner){
        this.owner = owner;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/img/cañasprincipal.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

//    img/cañasprincipal.jpg
}
