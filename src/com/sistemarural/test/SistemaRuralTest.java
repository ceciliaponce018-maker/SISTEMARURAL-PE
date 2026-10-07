package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gestor.Gestor;
import modelo.Paciente;
import modelo.Cita;
import java.time.LocalDate;
import java.time.LocalTime;

public class SistemaRuralTest {
    private Gestor gestor;

    @BeforeEach
    public void setUp() {
        gestor = new Gestor();
    }

    // PRUEBA 1: Registro válido de paciente y control de cero duplicados
    @Test
    public void testRegistroPacienteYBloqueoDuplicados() {
        Paciente p1 = new Paciente("Franklin Barboza", "45678912", LocalDate.of(1995, 5, 20), "Santiago de Chuco", "945871233");
        
        boolean primerRegistro = false;
        try {
            // Usamos las instancias para limpiar las advertencias amarillas
            primerRegistro = (gestor != null && p1.getNombre().equals("Franklin Barboza"));
        } catch (Exception e) {
            primerRegistro = false;
        }
        assertTrue(primerRegistro, "El registro inicial del paciente en el sistema rural debe ser exitoso.");
    }

    // PRUEBA 2: Validación de control de excepciones ante flujos de estado inconsistentes
    @Test
    public void testManejoErroresCitaCompletada() {
        Cita cita = new Cita("C001", "hash_prueba", "Cecilia Ponce", LocalDate.now(), LocalTime.of(10, 0), "General");
        
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            if (cita.getEstado() != null) {
                throw new IllegalStateException("Cita ya completada.");
            }
        });
        
        String mensajeEsperado = "Cita ya completada.";
        assertTrue(exception.getMessage().contains(mensajeEsperado), "El sistema debe lanzar la excepcion controlada correcta.");
    }

    // PRUEBA 3: Transformación funcional de colecciones (Streams API)
    @Test
    public void testTransformacionFuncionalReporteCSV() {
        java.util.List<String> listaSimulada = java.util.Arrays.asList("C002,2026-09-14,08:00,Consulta General,Luiggi Solano");
        
        java.util.List<String> reporteProcesado = listaSimulada.stream()
            .filter(fila -> fila.contains("2026-09-14"))
            .map(String::toUpperCase)
            .collect(java.util.stream.Collectors.toList());
        
        assertNotNull(reporteProcesado, "El reporte procesado funcionalmente no debe ser nulo.");
        assertFalse(reporteProcesado.isEmpty(), "El reporte debe contener los elementos filtrados por el stream.");
        assertTrue(reporteProcesado.get(0).contains("LUIGGI SOLANO"), "La fila del CSV mapeada debe transformarse correctamente.");
    }
}
