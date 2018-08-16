
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Solution extends Application{    
    Stage stage = null;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePage.fxml"));
        
        Parent root = loader.load();
        HomePageController controller=loader.getController();
        controller.setMain(this);

        Scene scene = new Scene(root);
        stage.setTitle("Johnson's Algorithm");
        stage.setScene(scene);
        stage.show();
        
    }
    
    public static void main(String[] args) throws IOException{
        launch(args);
    }
}
