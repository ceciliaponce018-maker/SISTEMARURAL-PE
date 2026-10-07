# SISTEMARURAL-PE | Enfoque Multiparadigma para la Gestión de Salud Rural
**Curso:** Lenguajes de Programación (UAIN1288P) | **Grupo Nro. 03**
**Institución de Aplicación:** Centro de Salud de Santiago de Chuco (Categoría I-2), La Libertad.

---

## 1. Descripción del Proyecto
SISTEMARURAL-PE es un módulo de software de escritorio optimizado y de alta eficiencia diseñado para digitalizar el flujo de atención médica en establecimientos rurales vulnerables. Atiende la problemática del Centro de Salud de Santiago de Chuco mitigando la pérdida sistemática de historiales clínicos y garantizando el cumplimiento estricto de la **Política Nacional de Salud Digital del MINSA (2022)** y la **Ley N.º 29733 (Protección de Datos Personales)**.

### Integración Multiparadigma Implementada:
*   **Programación Orientada a Objetos (POO):** Modelado del dominio clínico (`Paciente`, `Cita`) con encapsulamiento estricto y ocultamiento de datos sensibles.
*   **Programación Funcional (PF):** Procesamiento de reportes epidemiológicos inmutables exportables a formato CSV utilizando Java Streams API (`.filter()`, `.map()`, `.collect()`).
*   **Orientada a Eventos:** Interfaz Gráfica de Usuario (GUI) interactiva desarrollada en Swing acoplada mediante listeners reactivos (`ActionListener`) bajo el patrón de diseño Observador.

---

## 2. Requisitos del Sistema
Para garantizar la viabilidad y mitigar el riesgo de obsolescencia tecnológica en entornos rurales (**Riesgo R1**), el sistema fue desarrollado y empaquetado bajo las siguientes especificaciones mínimas:
*   **Lenguaje de Programación:** Java OpenJDK 11 LTS (o superior).
*   **Entorno de Construcción:** Herramientas de código abierto (VS Code / CLI).
*   **Librerías Gráficas:** Java Swing / AWT (Nativas, sin dependencias externas de licenciamiento).
*   **Motor de Pruebas:** JUnit 5.x.
*   **Hardware Mínimo de Ejecución:** Computadoras con sistema operativo Windows 7/10, Procesador Dual-Core y un mínimo de 2 GB de Memoria RAM.

---

## 3. Instalación y Ejecución

### Paso 1: Clonar el Repositorio
```bash
git clone https://github.com/[TU_REPOSITORIO]/SISTEMARURAL-PE.git
cd SISTEMARURAL-PE
```

### Paso 2: Compilación de Módulos (Vía Consola de Comandos)
Compile todas las capas del sistema organizadas por paquetes en el directorio de salida `out`:
```bash
javac -d out src/com/sistemarural/seguridad/*.java src/com/sistemarural/modelo/*.java src/com/sistemarural/servicios/*.java src/com/sistemarural/gestor/*.java src/com/sistemarural/ui/*.java
```

### Paso 3: Ejecución del Módulo Operativo
Inicie la interfaz gráfica interactiva ejecutando la clase principal del sistema:
```bash
java -cp out com.sistemarural.ui.SistemaRuralPE
```

---

## 4. Ejecución de Pruebas Automatizadas

El proyecto incluye una suite de pruebas unitarias automatizadas con JUnit para certificar la calidad técnica del software antes de su puesta en producción, validando flujos de extremo a extremo sin efectos secundarios en el estado original.

Para compilar y ejecutar las pruebas unitarias automatizadas desde la terminal, use:
```bash
javac -cp ".;lib/junit-platform-console-standalone-1.x.x.jar" -d out src/com/sistemarural/test/*.java
java -jar lib/junit-platform-console-standalone-1.x.x.jar --class-path out --select-class com.sistemarural.test.SistemaRuralTest
```

### Casos de Prueba Verificados en Consola:
1.  `testRegistroPacienteYBloqueoDuplicados()`: Garantiza que el bloqueo del 100% de los registros con un DNI duplicado sea ejecutado de manera exitosa (**Criterio de Éxito 3**).
2.  `testManejoErroresCitaCompletada()`: Comprueba el correcto lanzamiento y captura de excepciones controladas (`IllegalStateException`) en flujos de cancelación inconsistentes.
3.  `testTransformacionFuncionalReporteCSV()`: Valida la inmutabilidad de la colección y el mapeo correcto de la función de orden superior para la exportación limpia en CSV (**RF-03**).

---

## Tratamiento Seguro de Datos Ficticios (Ley N.º 29733)
De acuerdo con las restricciones de diseño **RD-02**, toda la información desplegada en la carpeta `/datos_prueba/pacientes_ejemplo.txt` y en las interfaces corresponde estrictamente a **datos personales ficticios**. El sistema implementa un esquema de criptografía híbrida: **AES-256** para el almacenamiento reversible y visualización ofuscada en reportes, y **SHA-256** para procesos internos de validación, garantizando que ninguna credencial, token o dato médico sensible real sea expuesto en texto plano en el repositorio público de GitHub.
