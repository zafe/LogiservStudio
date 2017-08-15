package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GPSWebViewController {

	@FXML
	private WebView webView;
	@FXML
	private Button refreshButton;
	
	
private Stage owner;
	
	@FXML
	private void initialize(){
		WebEngine engine = webView.getEngine();
		engine.load("http://mobilequest.com.ar/home.php");
	}
	
	@FXML
	public void refresh(){
		WebEngine engine = webView.getEngine();
		engine.reload();
	}
	
	public void setOwner(Stage owner){
		this.owner = owner;
	}

}
