package co.edu.poli.actividad.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 * Contexto: Gestión de Actividades Turísticas
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        // Carga del archivo FXML principal (asegúrate que el nombre y ruta sean correctos)
        AnchorPane root = FXMLLoader.load(getClass().getResource("/co/edu/poli/actividad/vista/Formulario.fxml"));

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestión de Actividades Turísticas");
        stage.show();
    }

    // Método para cambiar vistas si lo necesitas en el futuro
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Método auxiliar para cargar vistas FXML
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/co/edu/poli/actividad/vista/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
