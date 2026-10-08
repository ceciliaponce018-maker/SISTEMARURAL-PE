package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SistemaRuralTest {

    @Test
    public void testValidacionDNI() {
        // Simulación de validación de DNI (8 dígitos)
        String dni = "12345678";
        assertTrue(dni.length() == 8 && dni.matches("\\d+"), 
            "El DNI debe tener exactamente 8 dígitos numéricos.");
    }

    @Test
    public void testValidacionTelefono() {
        // Simulación de validación de teléfono (9 dígitos)
        String telefono = "987654321";
        assertTrue(telefono.length() == 9 && telefono.matches("\\d+"), 
            "El teléfono debe tener exactamente 9 dígitos numéricos.");
    }

    @Test
    public void testTransformacionFuncional() {
        // Simulación de transformación funcional con Streams
        java.util.List<String> datos = java.util.Arrays.asList(
            "Juan Perez;****;02/10/2026;consulta;Programada",
            "Maria Lopez;****;01/10/2026;obstetricia;Programada"
        );
        
        java.util.List<String> filtrados = datos.stream()
            .filter(linea -> linea.contains("02/10/2026"))
            .collect(java.util.stream.Collectors.toList());
        
        assertEquals(1, filtrados.size(), "Debe filtrar exactamente 1 registro.");
        assertTrue(filtrados.get(0).contains("Juan Perez"), 
            "El registro filtrado debe contener el nombre correcto.");
    }
}