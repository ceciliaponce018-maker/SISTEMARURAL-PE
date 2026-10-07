package servicios;

public class ServicioImpl implements IServicio {
    private String tipo;
    public ServicioImpl(String t) { this.tipo = t; }
    public void registrar() { System.out.println(tipo + " registrada."); }
    public String getTipo() { return tipo; }
}