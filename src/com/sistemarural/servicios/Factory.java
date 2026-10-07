package servicios;

public class Factory {
    public static IServicio crear(String tipo) {
        switch(tipo.toLowerCase()) {
            case "consulta": case "cred": case "vacunacion": case "obstetricia":
                return new ServicioImpl(tipo);
            default: throw new IllegalArgumentException("Tipo inválido");
        }
    }
}