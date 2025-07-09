import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Coleccion {
    private final List<Transformer> lista = new ArrayList<>();


    public void agregar(Transformer t) {
        if (existeId(t.getId())) {
            throw new DuplicateIdException(t.getId());
        }
        lista.add(t);
    }


    public boolean existeId(int id) {
        return lista.stream().anyMatch(x -> x.getId() == id);
    }


    public boolean actualizar(Transformer tAct) {
        for (Transformer t : lista) {
            if (t.getId() == tAct.getId()) {
                t.setNombre(tAct.getNombre());
                t.setPoder(tAct.getPoder());
                t.setFaccion(tAct.getFaccion());
                return true;
            }
        }
        return false;
    }


    public boolean eliminar(int id) {
        return lista.removeIf(t -> t.getId() == id);
    }


    public List<Transformer> getTodos() {
        return Collections.unmodifiableList(lista);
    }

    public void clearAll() {
        lista.clear();
    }
}
