import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Clase Equipo que gestiona jugadores usando Set para garantizar unicidad de código.
 */
public class Equipo {
    private final Set<Jugador> jugadores = new LinkedHashSet<>();
    private int nextCodigo = 1;

    /**
     * Agrega un nuevo jugador, asigna código y evita duplicados.
     */
    public void agregar(String nombre, float rendimiento, String posicion) {
        Jugador j = new Jugador(nextCodigo++, nombre, rendimiento, posicion);
        if (!jugadores.add(j)) {
            throw new DuplicateJugadorException(j.getCodigo());
        }
    }

    /**
     * Actualiza datos de un jugador por código; devuelve true si existe.
     */
    public boolean actualizar(int codigo, String nombre, float rendimiento, String posicion) {
        for (Jugador j : jugadores) {
            if (j.getCodigo() == codigo) {
                j.setNombre(nombre);
                j.setRendimiento(rendimiento);
                j.setPosicion(posicion);
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza código y datos de un jugador; devuelve true si existe.
     */
    public boolean actualizarCodigo(int oldCodigo, int newCodigo, String nombre, float rendimiento, String posicion) {
        // Verifica duplicado de nuevo código
        if (oldCodigo != newCodigo) {
            for (Jugador j : jugadores) {
                if (j.getCodigo() == newCodigo) {
                    throw new DuplicateJugadorException(newCodigo);
                }
            }
        }
        Iterator<Jugador> it = jugadores.iterator();
        while (it.hasNext()) {
            Jugador j = it.next();
            if (j.getCodigo() == oldCodigo) {
                it.remove();
                Jugador actualizado = new Jugador(newCodigo, nombre, rendimiento, posicion);
                jugadores.add(actualizado);
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un jugador por código; devuelve true si se eliminó.
     */
    public boolean eliminar(int codigo) {
        return jugadores.removeIf(j -> j.getCodigo() == codigo);
    }

    /**
     * Retorna una vista inmutable de todos los jugadores.
     */
    public Set<Jugador> getTodos() {
        return Collections.unmodifiableSet(jugadores);
    }

    /**
     * Limpia todos los jugadores y resetea el contador de códigos.
     */
    public void clearAll() {
        jugadores.clear();
        nextCodigo = 1;
    }
}
