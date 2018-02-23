package application.reports;

import application.database.JDBCConnection;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.sql.Connection;

public abstract class AbstractJasperReports {
    private static JasperReport report;
    private static JasperPrint reportFilled;
    private static JasperFX viewer;
    public static void createReport(String path){
        try {
            Connection connection = JDBCConnection.getInstanceConnection();
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report,null, connection);
        }catch (JRException e){
            e.printStackTrace();
        }

    }
    public static void showViewer(){
        viewer = new JasperFX(reportFilled);
        viewer.show();
    }
}
