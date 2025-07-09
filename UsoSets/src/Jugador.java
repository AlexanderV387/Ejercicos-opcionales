public class Jugador {
    private final int codigo;
    private String nombre;
    private float rendimiento;
    private String posicion;

    public Jugador(int codigo, String nombre, float rendimiento, String posicion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        if (rendimiento < 0) {
            throw new IllegalArgumentException("Rendimiento debe ser mayor o igual a 0");
        }
        if (posicion == null || posicion.trim().isEmpty()) {
            throw new IllegalArgumentException("Posición no puede estar vacía");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.rendimiento = rendimiento;
        this.posicion = posicion;
    }

    public int getCodigo() { return codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public float getRendimiento() { return rendimiento; }
    public void setRendimiento(float rendimiento) {
        if (rendimiento < 0) {
            throw new IllegalArgumentException("Rendimiento debe ser mayor o igual a 0");
        }
        this.rendimiento = rendimiento;
    }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) {
        if (posicion == null || posicion.trim().isEmpty()) {
            throw new IllegalArgumentException("Posición no puede estar vacía");
        }
        this.posicion = posicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador j = (Jugador) o;
        return codigo == j.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - Rend: %.2f - %s", codigo, nombre, rendimiento, posicion);
    }
}
