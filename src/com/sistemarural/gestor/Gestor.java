package gestor;

import modelo.Paciente;
import modelo.Cita;
import servicios.Factory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Gestor {
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Cita> citas = new ArrayList<>();
    private List<IObservador> obs = new ArrayList<>();
    private int contador = 0;

    public boolean registrar(Paciente p) {
        if (pacientes.stream().anyMatch(x -> x.mismoDNI(p.getDniHash()))) {
            notificar(" DNI duplicado"); return false;
        }
        pacientes.add(p); notificar("• Paciente: " + p.getNombre()); return true;
    }
    
    public List<Paciente> getPacientes() { return new ArrayList<>(pacientes); }

    public Paciente buscar(String criterio, boolean esDNI) {
        return pacientes.stream().filter(p -> {
            if (esDNI) return p.getDniReal().equals(criterio);
            return p.getNombre().toLowerCase().contains(criterio.toLowerCase());
        }).findFirst().orElse(null);
    }

    public Cita crearCita(String dni, String tipo, LocalDate f, LocalTime h) {
        try {
            Paciente p = pacientes.stream().filter(x -> x.getDniReal().equals(dni))
                .findFirst().orElseThrow(() -> new Exception("Paciente no existe"));
            Factory.crear(tipo).registrar();
            Cita c = new Cita("C-" + String.format("%03d", ++contador), p.getDniHash(), p.getNombre(), f, h, tipo);
            citas.add(c); notificar(" Cita creada: " + c.getId()); return c;
        } catch (Exception e) { notificar(" Error: " + e.getMessage()); return null; }
    }

    public List<Cita> getCitasActivas() {
        return citas.stream().filter(c -> !"Cancelada".equals(c.getEstado())).collect(Collectors.toList());
    }

    public void cancelarCita(String id) {
        citas.stream().filter(c -> c.getId().equals(id)).findFirst().ifPresentOrElse(c -> {
            try { c.cancelar(); notificar(" Cita " + id + " cancelada."); }
            catch (IllegalStateException e) { notificar(" ✘ " + e.getMessage()); }
        }, () -> notificar(" ID no encontrado."));
    }

    public List<String> generarCSV(LocalDate i, LocalDate f) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return citas.stream()
            .filter(c -> !c.getFecha().isBefore(i) && !c.getFecha().isAfter(f))
            .map(c -> String.format("%s; ****; %s; %s; %s",
                c.getPN(), c.getFecha().format(fmt), c.getTipo(), c.getEstado()))
            .collect(Collectors.toList());
    }

    public void addObs(IObservador o) { obs.add(o); }
    private void notificar(String m) { obs.forEach(o -> o.onCambio(m)); }
}// Optimizacion de Streams de Orden Superior para DIRESA 
