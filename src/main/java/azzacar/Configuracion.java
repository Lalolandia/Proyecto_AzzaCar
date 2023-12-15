package azzacar;



public class Configuracion {
    private static final String NOMBRE_EMPRESA = "AzzaCar";
    private static final String TELEFONO_EMPRESA = "8888-8888";
    private static final String DIRECCION_EMPRESA = "Cartago, Cartago, Occidental";

    public static String obtenerInformacionEmpresa() {
        return "Nombre: " + NOMBRE_EMPRESA + "\nTeléfono: " + TELEFONO_EMPRESA + "\nDirección: " + DIRECCION_EMPRESA;
    }
}