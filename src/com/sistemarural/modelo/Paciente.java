package modelo;

import seguridad.Cifrador;
import java.time.LocalDate;

public class Paciente {
    private String nombre, dniCif, dniHash, dir, tel;
    private LocalDate fn;
    
    public Paciente(String n, String d, LocalDate f, String dir, String t) {
        this.nombre = n;
        this.dniCif = Cifrador.cifrar(d);
        this.dniHash = Cifrador.hash(d);
        this.fn = f;
        this.dir = dir;
        this.tel = (t == null || t.isEmpty()) ? "No registrado" : t;
    }
    
    public String getNombre() { return nombre; }
    public String getDniHash() { return dniHash; }
    public String getDniReal() { return Cifrador.descifrar(dniCif); }
    public LocalDate getFN() { return fn; }
    public String getDir() { return dir; }
    public String getTel() { return tel; }
    
    public String getDniOfuscado() {
        String r = getDniReal();
        return r.length() >= 4 ? "****" + r.substring(r.length() - 4) : "****";
    }
    
    public boolean mismoDNI(String h) { return this.dniHash.equals(h); }
}