# SISTEMARURAL-PE

**Módulo de software multiparadigma para la gestión de establecimientos de salud en zonas rurales del Perú.**

**Curso:** Lenguajes de Programación (UAIN1288P) | **Grupo Nro. 03**
**Institución de Aplicación:** Centro de Salud de Santiago de Chuco (Categoría I-2), La Libertad.
**Fecha:** Octubre 2026

---

## 1. Descripción del Proyecto
SISTEMARURAL-PE es un módulo de software de escritorio optimizado y de alta eficiencia diseñado para digitalizar el flujo de atención médica en establecimientos rurales vulnerables. Atiende la problemática del Centro de Salud de Santiago de Chuco mitigando la pérdida sistemática de historiales clínicos y garantizando el cumplimiento estricto de la **Política Nacional de Salud Digital del MINSA (2022)** y la **Ley N.º 29733 (Protección de Datos Personales)**.

### Integración Multiparadigma Implementada:
*   **Programación Orientada a Objetos (POO):** Modelado del dominio clínico (`Paciente`, `Cita`) con encapsulamiento estricto y ocultamiento de datos sensibles.
*   **Programación Funcional (PF):** Procesamiento de reportes epidemiológicos inmutables exportables a formato CSV utilizando Java Streams API (`.filter()`, `.map()`, `.collect()`).
*   **Orientada a Eventos:** Interfaz Gráfica de Usuario (GUI) interactiva desarrollada en Swing acoplada mediante listeners reactivos (`ActionListener`) bajo el patrón de diseño Observador.

---

## 2. Requisitos del Sistema
Para garantizar la viabilidad y mitigar el riesgo de obsolescencia tecnológica en entornos rurales, el sistema fue desarrollado bajo las siguientes especificaciones mínimas:
*   **Lenguaje:** Java OpenJDK 11 LTS (o superior).
*   **Entorno:** VS Code / CLI (Herramientas de código abierto).
*   **Librerías:** Java Swing / AWT (Nativas, sin dependencias externas pesadas).
*   **Motor de Pruebas:** JUnit 5.x (junit-platform-console-standalone).
*   **Hardware Mínimo:** Windows 7/10, Procesador Dual-Core, 2 GB RAM.

---

## 3. Estructura del Proyecto (Módulos y Paquetes)
El código fuente está estrictamente organizado por responsabilidades para cumplir con el Principio de Responsabilidad Única (SRP):
```text
SISTEMARURAL-PE/
├── src/
│   ├── com/sistemarural/
│   │   ├── seguridad/   # Cifrado AES-256 y Hash SHA-256
│   │   ├── modelo/      # Entidades: Paciente, Cita
│   │   ├── servicios/   # Interfaz IServicio y Patrón Factory
│   │   ├── gestor/      # Lógica de negocio y Streams API
│   │   └── ui/          # Interfaz gráfica (Swing) y Eventos
│   └── test/            # Pruebas automatizadas JUnit 5
├── datos_prueba/        # Datos 100% ficticios para testing
├── evidencias/          # Capturas de los 6 casos de prueba mínimos
├── lib/                 # Librería JUnit (ignorada en .gitignore)
├── .gitignore
└── README.md
```
## 4. Instalación y Ejecución

### Paso 1: Clonar el Repositorio
```bash
git clone https://github.com/ceciliaponce018-maker/SISTEMARURAL-PE.git
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

## 5. Ejecución de Pruebas Automatizadas

El proyecto incluye una suite de pruebas unitarias automatizadas con JUnit para certificar la calidad técnica del software antes de su puesta en producción, validando flujos de extremo a extremo sin efectos secundarios en el estado original.

Para compilar y ejecutar las pruebas unitarias automatizadas desde la terminal, use:
```bash
# 1. Compilar la clase de prueba
javac -cp "out;lib\junit-platform-console-standalone-1.10.2.jar" -d out src\test\SistemaRuralTest.java

# 2. Ejecutar la suite de pruebas
java -jar lib\junit-platform-console-standalone-1.10.2.jar --class-path out --scan-class-path
```

### Casos de Prueba Verificados:
1. **testValidacionDNI()**: Garantiza la validación de entrada de 8 dígitos numéricos.
2. **testValidacionTelefono()**: Comprueba la validación de 9 dígitos numéricos.
3. **testTransformacionFuncional()**: Valida la inmutabilidad y el mapeo correcto de la función de orden superior (.filter()) para la exportación limpia en CSV.

## 6. Tratamiento Seguro de Datos Ficticios (Ley N.º 29733)
De acuerdo con las restricciones de diseño RD-02, toda la información en la carpeta /datos_prueba/ y en las interfaces corresponde estrictamente a datos personales ficticios.
El sistema implementa criptografía híbrida: AES-256 para el almacenamiento reversible y SHA-256 para validaciones internas. Los reportes CSV exportados aplican ofuscación total del DNI (****), garantizando que ninguna credencial o dato médico sensible real sea expuesto en texto plano en este repositorio público.