public class DuplicateJugadorException extends RuntimeException {
    public DuplicateJugadorException(int codigo) {
        super("Jugador con código duplicado: " + codigo);
    }
}
