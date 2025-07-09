public class Transformer {
    private int id;
    private String nombre;
    private String poder;
    private String faccion;

    public Transformer(int id, String nombre, String poder, String faccion) {
        this.id       = id;
        this.nombre   = nombre;
        this.poder    = poder;
        this.faccion  = faccion;
    }

    public int getId()                  { return id; }
    public String getNombre()           { return nombre; }
    public String getPoder()            { return poder; }
    public String getFaccion()          { return faccion; }

    public void setNombre(String nombre)   { this.nombre = nombre; }
    public void setPoder(String poder)     { this.poder = poder; }
    public void setFaccion(String faccion) { this.faccion = faccion; }

    @Override
    public String toString() {
        return String.format("Transformer[id=%d, nombre=%s, poder=%s, faccion=%s]",
                id, nombre, poder, faccion);
    }
}
