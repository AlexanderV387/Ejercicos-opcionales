public class DuplicateIdException extends RuntimeException {
    public DuplicateIdException(int id) {
        super("ID duplicado: " + id);
    }
}
