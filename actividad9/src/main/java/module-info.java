module co.edu.poli.actividad9 {
    requires javafx.controls;
    requires javafx.fxml;

    // Exporta los paquetes que JavaFX necesita ver
    opens co.edu.poli.actividad.controlador to javafx.fxml;
    opens co.edu.poli.actividad.vista to javafx.graphics, javafx.fxml;

    // (Opcional si tienes modelos serializables)
    exports co.edu.poli.actividad.modelo;
    exports co.edu.poli.actividad.controlador;
    exports co.edu.poli.actividad.vista;
}
