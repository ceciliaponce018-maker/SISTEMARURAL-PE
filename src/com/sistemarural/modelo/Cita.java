package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cita {
    private String id, pHash, pNombre, tipo, estado;
    private LocalDate fecha; 
    private LocalTime hora;
    
    public Cita(String id, String ph, String pn, LocalDate f, LocalTime h, String t) {
        this.id = id; this.pHash = ph; this.pNombre = pn;
        this.fecha = f; this.hora = h; this.tipo = t; this.estado = "Programada";
    }
    
    public void cancelar() {
        if ("Completada".equals(estado)) throw new IllegalStateException("Cita ya completada.");
        this.estado = "Cancelada";
    }
    
    public String getId() { return id; }
    public String getPH() { return pHash; }
    public String getPN() { return pNombre; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
    
    public String toString() {
        return id + " | " + pNombre + " | " + tipo + " | " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " | " + estado;
    }
}