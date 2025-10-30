package co.edu.poli.actividad.modelo;

import java.io.Serializable;

/**
 * Representa una actividad turística de tipo Paracaidismo.
 * Hereda de {@link Actividad_turistica} e incluye atributos específicos como tipo de paracaídas y nivel de dificultad.
 *
 * @author Laura
 */
public class Paracaidismo extends Actividad_turistica implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Tipo de paracaídas utilizado en la actividad */
    private String tipoParacaidas;

    /** Nivel de dificultad de la actividad (bajo, medio, alto) */
    private String nivelDificultad;

    /**
     * Constructor completo que coincide con la firma del constructor largo de Actividad_turistica.
     * (Asegúrate de que la clase padre tenga exactamente esta firma).
     */
    public Paracaidismo(String lugar, int duracion, int precio, String nombre, String fecha, String tipo,
                        String idActividad, Pais pais, Tour tour, Reserva[] reserva, Equipaje[] equipaje,
                        Itinerario itinerario, Guia[] guia, int creacion,
                        String tipoParacaidas, String nivelDificultad) {
        super(lugar, duracion, precio, nombre, fecha, tipo, idActividad, pais, tour, reserva, equipaje, itinerario, guia, creacion);
        setTipoParacaidas(tipoParacaidas);
        setNivelDificultad(nivelDificultad);
    }

    /**
     * Constructor simplificado que mantiene compatibilidad con tu patrón de uso previo:
     * llama al constructor largo de la superclase con valores por defecto/null.
     */
    public Paracaidismo(String idActividad, String nombre, int precio, int duracion) {
        super(null, duracion, precio, nombre, null, null, idActividad, null, null, null, null, null, null, 0);
        this.tipoParacaidas = "estándar";
        this.nivelDificultad = "medio";
    }

    public String getTipoParacaidas() {
        return tipoParacaidas;
    }

    public void setTipoParacaidas(String tipoParacaidas) {
        if (tipoParacaidas == null || tipoParacaidas.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de paracaídas no puede estar vacío.");
        }
        this.tipoParacaidas = tipoParacaidas;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        if (nivelDificultad == null || nivelDificultad.trim().isEmpty()) {
            throw new IllegalArgumentException("El nivel de dificultad no puede estar vacío.");
        }
        this.nivelDificultad = nivelDificultad;
    }

    @Override
    public String toString() {
        return "Paracaidismo{" +
                "id='" + getIdActividad() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", precio=" + getPrecio() +
                ", duracion=" + getDuracion() +
                ", tipoParacaidas='" + tipoParacaidas + '\'' +
                ", nivelDificultad='" + nivelDificultad + '\'' +
                '}';
    }

    /**
     * Calcula el costo total de la actividad según el nivel de dificultad.
     * Compatibilidad amplia: usa switch clásico y evita NPE.
     */
    @Override
    public double calcularCosto() {
        double factor = 1.2; // por defecto

        if (nivelDificultad != null) {
            String nivel = nivelDificultad.toLowerCase();
            // switch clásico para compatibilidad con versiones de Java < 14
            switch (nivel) {
                case "bajo":
                    factor = 1.1;
                    break;
                case "medio":
                    factor = 1.2;
                    break;
                case "alto":
                    factor = 1.4;
                    break;
                default:
                    factor = 1.2;
                    break;
            }
        }

        return getPrecio() * factor;
    }
}
