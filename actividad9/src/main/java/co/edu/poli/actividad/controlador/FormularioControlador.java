package co.edu.poli.actividad.controlador;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import co.edu.poli.actividad.modelo.Paracaidismo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador para gestionar el CRUD y la serialización/deserialización
 * de actividades turísticas en la interfaz JavaFX.
 */
public class FormularioControlador implements Initializable {

    // ========= CAMPOS DEL FORMULARIO =========
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtAlturaMax;
    @FXML private TextArea txtDescripcion;

    @FXML private ComboBox<String> cmbTipo;
    @FXML private DatePicker dateDisponible;

    @FXML private RadioButton rbFacil;
    @FXML private RadioButton rbMedia;
    @FXML private RadioButton rbDificil;
    @FXML private ToggleGroup grupoDificultad;

    @FXML private CheckBox chkGuia;
    @FXML private CheckBox chkEquipo;
    @FXML private CheckBox chkTransporte;

    // ========= TABLA =========
    @FXML private TableView<Paracaidismo> tblActividades;
    @FXML private TableColumn<Paracaidismo, String> colId;
    @FXML private TableColumn<Paracaidismo, String> colNombre;
    @FXML private TableColumn<Paracaidismo, Integer> colDuracion;
    @FXML private TableColumn<Paracaidismo, Integer> colPrecio;
    @FXML private TableColumn<Paracaidismo, String> colTipo;
    @FXML private TableColumn<Paracaidismo, String> colDificultad;

    // Lista observable para la tabla
    private final ObservableList<Paracaidismo> listaActividades = FXCollections.observableArrayList();

    // Archivo para serialización
    private final File archivo = new File("actividades.dat");

    // ========= MÉTODOS PRINCIPALES =========
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Opciones del combo
        cmbTipo.getItems().addAll("Deportiva", "Militar", "Tándem", "Libre");

        // Asegurar que los radio buttons pertenezcan al ToggleGroup (por seguridad)
        if (rbFacil != null) rbFacil.setToggleGroup(grupoDificultad);
        if (rbMedia != null) rbMedia.setToggleGroup(grupoDificultad);
        if (rbDificil != null) rbDificil.setToggleGroup(grupoDificultad);

        // Configurar columnas de tabla (los nombres deben coincidir con getters)
        colId.setCellValueFactory(new PropertyValueFactory<>("idActividad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoParacaidas"));
        colDificultad.setCellValueFactory(new PropertyValueFactory<>("nivelDificultad"));

        tblActividades.setItems(listaActividades);
    }

    @FXML
    private void guardarActividad(ActionEvent event) {
        try {
            String id = txtId.getText() == null ? "" : txtId.getText().trim();
            String nombre = txtNombre.getText() == null ? "" : txtNombre.getText().trim();
            String precioTxt = txtPrecio.getText() == null ? "" : txtPrecio.getText().trim();
            String duracionTxt = txtDuracion.getText() == null ? "" : txtDuracion.getText().trim();

            if (id.isEmpty() || nombre.isEmpty() || precioTxt.isEmpty() || duracionTxt.isEmpty()) {
                mostrarAlerta("Error", "ID, Nombre, Precio y Duración son obligatorios.");
                return;
            }

            int precio = Integer.parseInt(precioTxt);
            int duracion = Integer.parseInt(duracionTxt);

            String tipo = cmbTipo.getValue();
            String dificultad = obtenerDificultad();

            if (tipo == null || dificultad == null) {
                mostrarAlerta("Error", "Seleccione Tipo y Dificultad.");
                return;
            }

            Paracaidismo actividad = new Paracaidismo(id, nombre, precio, duracion);
            actividad.setTipoParacaidas(tipo);
            actividad.setNivelDificultad(dificultad);

            listaActividades.add(actividad);
            limpiarCampos();
            mostrarAlerta("Éxito", "Actividad guardada.");
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Precio y Duración deben ser números válidos.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo guardar la actividad: " + e.getMessage());
        }
    }

    @FXML
    private void modificarActividad(ActionEvent event) {
        Paracaidismo seleccionada = tblActividades.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Atención", "Seleccione una actividad para modificar.");
            return;
        }

        try {
            String nombre = txtNombre.getText() == null ? "" : txtNombre.getText().trim();
            String precioTxt = txtPrecio.getText() == null ? "" : txtPrecio.getText().trim();
            String duracionTxt = txtDuracion.getText() == null ? "" : txtDuracion.getText().trim();

            if (nombre.isEmpty() || precioTxt.isEmpty() || duracionTxt.isEmpty()) {
                mostrarAlerta("Error", "Nombre, Precio y Duración son obligatorios.");
                return;
            }

            seleccionada.setNombre(nombre);
            seleccionada.setPrecio(Integer.parseInt(precioTxt));
            seleccionada.setDuracion(Integer.parseInt(duracionTxt));
            if (cmbTipo.getValue() != null) seleccionada.setTipoParacaidas(cmbTipo.getValue());
            String dif = obtenerDificultad();
            if (dif != null) seleccionada.setNivelDificultad(dif);

            tblActividades.refresh();
            limpiarCampos();
            mostrarAlerta("Éxito", "Actividad modificada.");
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Precio y Duración deben ser números válidos.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    private void eliminarActividad(ActionEvent event) {
        Paracaidismo seleccionada = tblActividades.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            listaActividades.remove(seleccionada);
            limpiarCampos();
            mostrarAlerta("Éxito", "Actividad eliminada.");
        } else {
            mostrarAlerta("Atención", "Seleccione una actividad para eliminar.");
        }
    }

    @FXML
    private void serializarActividades(ActionEvent event) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(new ArrayList<>(listaActividades));
            mostrarAlerta("Éxito", "Actividades guardadas correctamente.");
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void deserializarActividades(ActionEvent event) {
        if (!archivo.exists()) {
            mostrarAlerta("Aviso", "No existe archivo serializado.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            @SuppressWarnings("unchecked")
            ArrayList<Paracaidismo> leidas = (ArrayList<Paracaidismo>) ois.readObject();
            listaActividades.setAll(leidas);
            tblActividades.refresh();
            mostrarAlerta("Éxito", "Actividades cargadas correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            mostrarAlerta("Error", "Error al leer archivo: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarSeleccion(MouseEvent event) {
        Paracaidismo seleccionada = tblActividades.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            txtId.setText(seleccionada.getIdActividad());
            txtNombre.setText(seleccionada.getNombre());
            txtDuracion.setText(String.valueOf(seleccionada.getDuracion()));
            txtPrecio.setText(String.valueOf(seleccionada.getPrecio()));
            cmbTipo.setValue(seleccionada.getTipoParacaidas());
            seleccionarDificultad(seleccionada.getNivelDificultad());
        }
    }

    // ========= MÉTODOS AUXILIARES =========
    private void limpiarCampos() {
        if (txtId != null) txtId.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtPrecio != null) txtPrecio.clear();
        if (txtDuracion != null) txtDuracion.clear();
        if (txtAlturaMax != null) txtAlturaMax.clear();
        if (txtDescripcion != null) txtDescripcion.clear();
        if (cmbTipo != null) cmbTipo.setValue(null);
        if (grupoDificultad != null) grupoDificultad.selectToggle(null);
        if (chkGuia != null) chkGuia.setSelected(false);
        if (chkEquipo != null) chkEquipo.setSelected(false);
        if (chkTransporte != null) chkTransporte.setSelected(false);
        if (dateDisponible != null) dateDisponible.setValue(null);
    }

    private String obtenerDificultad() {
        if (grupoDificultad == null) return null;
        Toggle t = grupoDificultad.getSelectedToggle();
        if (t == null) return null;
        RadioButton seleccionado = (RadioButton) t;
        return seleccionado.getText();
    }

    private void seleccionarDificultad(String dificultad) {
        if (dificultad == null || grupoDificultad == null) return;
        String d = dificultad.toLowerCase().replace("á", "a").replace("í", "i").replace("é", "e").trim();
        if (d.equals("facil") || d.equals("fácil")) {
            grupoDificultad.selectToggle(rbFacil);
        } else if (d.equals("media")) {
            grupoDificultad.selectToggle(rbMedia);
        } else if (d.equals("dificil") || d.equals("difícil")) {
            grupoDificultad.selectToggle(rbDificil);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
