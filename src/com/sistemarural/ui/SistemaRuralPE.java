package ui;

import modelo.Paciente;
import modelo.Cita;
import gestor.Gestor;
import gestor.IObservador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SistemaRuralPE extends JFrame implements IObservador {
    private Gestor g = new Gestor();
    private JTextArea log = new JTextArea();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public SistemaRuralPE() {
        g.addObs(this);
        setTitle("SISTEMARURAL-PE - Centro de Salud Santiago de Chuco");
        setSize(750, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel p = new JPanel(new FlowLayout());
        p.add(btn("Registrar", e -> registrar()));
        p.add(btn("Crear Cita", e -> crearCita()));
        p.add(btn("Cancelar Cita", e -> cancelar()));
        p.add(btn("Buscar Paciente", e -> buscar()));
        p.add(btn("Exportar CSV", e -> exportar()));
        
        log.setEditable(false);
        add(p, BorderLayout.NORTH);
        add(new JScrollPane(log), BorderLayout.CENTER);
        notificar("Sistema iniciado correctamente.");
    }

    private JButton btn(String t, ActionListener l) {
        JButton b = new JButton(t);
        b.addActionListener(l);
        return b;
    }

    private String pedir(String msg, boolean opcional) {
        while (true) {
            String s = JOptionPane.showInputDialog(this, msg + (opcional ? "\n(Opcional - Presione OK para omitir)" : ""));
            if (s == null) return null;
            if (!s.trim().isEmpty()) return s.trim();
            if (!opcional) {
                JOptionPane.showMessageDialog(this, " Campo obligatorio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            return "";
        }
    }

    private void registrar() {
        String n = pedir("Nombre completo:", false); if (n == null) return;
        String d;
        while (true) {
            d = pedir("DNI (8 dígitos):", false); if (d == null) return;
            if (d.length() == 8 && d.matches("\\d+")) break;
            JOptionPane.showMessageDialog(this, " DNI inválido. Debe tener 8 números.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String fn = pedir("F. Nacimiento (DD/MM/YYYY):", false); if (fn == null) return;
        LocalDate f;
        try { f = LocalDate.parse(fn, fmt); }
        catch (Exception e) { JOptionPane.showMessageDialog(this, " Fecha inválida."); return; }
        String dir = pedir("Dirección:", false); if (dir == null) return;
        String tel = pedir("Teléfono (9 dígitos):", true); if (tel == null) return;
        if (!tel.isEmpty() && (tel.length() != 9 || !tel.matches("\\d+"))) {
            JOptionPane.showMessageDialog(this, " Teléfono inválido. Debe tener 9 números."); return;
        }
        g.registrar(new Paciente(n, d, f, dir, tel));
    }

    private void crearCita() {
        if (g.getPacientes().isEmpty()) { JOptionPane.showMessageDialog(this, " Registre un paciente primero."); return; }
        String d = pedir("DNI del paciente:", false); if (d == null) return;
        String t = (String) JOptionPane.showInputDialog(this, "Tipo:", "Cita", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Consulta","CRED","Vacunacion","Obstetricia"}, "Consulta");
        if (t == null) return;
        String fn = pedir("Fecha (DD/MM/YYYY):", false); if (fn == null) return;
        LocalDate f; try { f = LocalDate.parse(fn, fmt); } catch (Exception e) { JOptionPane.showMessageDialog(this, " Fecha inválida."); return; }
        String h = pedir("Hora (HH:MM):", false); if (h == null) return;
        LocalTime hr; try { hr = LocalTime.parse(h); } catch (Exception e) { JOptionPane.showMessageDialog(this, " Hora inválida."); return; }
        g.crearCita(d, t.toLowerCase(), f, hr);
    }

    private void cancelar() {
        List<Cita> activas = g.getCitasActivas();
        if (activas.isEmpty()) { JOptionPane.showMessageDialog(this, " No hay citas activas."); return; }
        String sel = (String) JOptionPane.showInputDialog(this, "Seleccione la cita a cancelar:", "Cancelar", JOptionPane.QUESTION_MESSAGE, null, activas.stream().map(Cita::toString).toArray(), activas.get(0).toString());
        if (sel != null) g.cancelarCita(sel.split(" \\| ")[0].trim());
    }

    private void buscar() {
        String[] ops = {"Por Nombre", "Por DNI"};
        String sel = (String) JOptionPane.showInputDialog(this, "Tipo de búsqueda:", "Buscar Paciente", JOptionPane.QUESTION_MESSAGE, null, ops, ops[0]);
        if (sel == null) return;
        boolean esDNI = sel.equals("Por DNI");
        String c = pedir(esDNI ? "DNI (8 dígitos):" : "Nombre:", false); if (c == null) return;
        if (esDNI && (c.length() != 8 || !c.matches("\\d+"))) { JOptionPane.showMessageDialog(this, " DNI inválido."); return; }
        Paciente p = g.buscar(c, esDNI);
        if (p != null) {
            JOptionPane.showMessageDialog(this, " Paciente encontrado:\n" +
                "Nombre: " + p.getNombre() + "\nDNI: " + p.getDniOfuscado() +
                "\nNacimiento: " + p.getFN().format(fmt) + "\nTel: " + p.getTel());
        } else {
            JOptionPane.showMessageDialog(this, " No encontrado.");
        }
    }

    private void exportar() {
        String i = pedir("Fecha inicio (DD/MM/YYYY):", false); if (i == null) return;
        String f = pedir("Fecha fin (DD/MM/YYYY):", false); if (f == null) return;
        LocalDate fi, ff;
        try { fi = LocalDate.parse(i, fmt); ff = LocalDate.parse(f, fmt); }
        catch (Exception e) { JOptionPane.showMessageDialog(this, " Fecha inválida."); return; }
    
        List<String> data = g.generarCSV(fi, ff);
        try {
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("reporte_sistemarural.csv"), "UTF-8"));
            w.write("\uFEFF"); // BOM de UTF-8
            w.write("NOMBRE; DNI; FECHA; SERVICIO; ESTADO\n");
            data.forEach(l -> { try { w.write(l + "\n"); } catch (Exception e) {} });
            w.close();
            JOptionPane.showMessageDialog(this, " CSV exportado: reporte_sistemarural.csv");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Error al exportar.");
        }
    }

    @Override
    public void onCambio(String m) { log.append(m + "\n"); }
    private void notificar(String m) { log.append(m + "\n"); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaRuralPE().setVisible(true));
    }
}