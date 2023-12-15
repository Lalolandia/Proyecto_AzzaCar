
package azzacar;
import static azzacar.Reportes.reporteCantidadVehiculos;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Luis
 */
public class AzzaCar {
    private static final String RUTA_USUARIOS = "usuarios.txt";
    private static final ArrayList<Usuario> listaClientes = new ArrayList<>();
    private static final String RUTA_VEHICULOS = "vehiculos.txt";
    private static final ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
    
    /*
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(RUTA_USUARIOS).getFile());
    */
    public static void main(String[] args) {
        cargarUsuariosDesdeArchivo();
        GestionVehiculos.guardarVehiculosEnArchivo();
        
        
         int opcion;
         Usuario usuarioActual = null;

        do {
            opcion = mostrarMenuPrincipal();

            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa. ¡Hasta luego!");
                    break;
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 3:
                    mostrarInformacionEmpresa();
                    break;
                case 4:
                    Usuario.cargarUsuariosDesdeArchivo(RUTA_USUARIOS);
                    break;
                case 5:
                    gestionarVehiculos(usuarioActual);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, elija nuevamente.");
                    break;
                    
            }       
                    guardarUsuariosEnArchivo();
                    
        } while (opcion != 0);
    }
    
    
    
    private static void gestionarVehiculos(Usuario usuarioActual) {
        int opcion;
        do {
            opcion = mostrarSubMenuVehiculos();

            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo del submenú de gestión de vehículos.");
                    break;
                case 1:
                    GestionVehiculos.crearVehiculo(usuarioActual);
                    break;
                case 2:
                    GestionVehiculos.cargarVehiculosDesdeArchivo(RUTA_VEHICULOS);
                    break;
                case 3:
                    GestionVehiculos.reservarVehiculo(usuarioActual);
                    break;
                case 4:
                    GestionVehiculos.venderVehiculo(usuarioActual);
                    break;
                case 5:
                    GestionVehiculos.actualizarEstadoVehiculo(usuarioActual, usuarioActual, false);
                break;
                case 6:
                    reporteCantidadVehiculos(listaVehiculos);
                    break;
                
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, elija nuevamente.");
                    break;
            }

        } while (opcion != 0);
    }

    private static int mostrarSubMenuVehiculos() {
        String[] opciones = { "Salir","Crear Vehículo", "Mostrar Vehículos", "Reservar Vehículo", "Vender Vehículo", "Actualizar un Vehiculo","Reportes de Veiculos"};
        return JOptionPane.showOptionDialog(null, "Seleccione una opción en la gestión de vehículos:",
                "Gestión de Vehículos", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }
                
    private static int mostrarMenuPrincipal() {
        String[] opciones = {"Salir","Iniciar Session","Registrar Usuario","Mostrar información de la Empresa", "Mostrar clientes", "Gestion Vehiculos"};
        return JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Menú Principal",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }

    private static void mostrarInformacionEmpresa() {
        JOptionPane.showMessageDialog(null, Configuracion.obtenerInformacionEmpresa(), "Información de la Empresa", JOptionPane.INFORMATION_MESSAGE);
    }
    private static void iniciarSesion() {
        String identificacion = JOptionPane.showInputDialog("Ingrese su identificación:");
        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");

        // Buscar el usuario en la lista
        Usuario usuario = buscarUsuario(identificacion);

        if (usuario == null || !usuario.validarContrasena(contrasena)) {
            JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Verifique sus credenciales.");
        } else {
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso. ¡Bienvenido, " + usuario.getNombre() + "!");
        }
    }

    private static void registrarUsuario() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
        String apellidos = JOptionPane.showInputDialog("Ingrese los apellidos:");
        String identificacion = JOptionPane.showInputDialog("Ingrese la identificación:");
        String correo = JOptionPane.showInputDialog("Ingrese el correo electrónico:");
        String telefono = JOptionPane.showInputDialog("Ingrese el número de teléfono:");
        String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");

        // Crear el usuario y almacenar la información en la lista y el archivo .txt
        Usuario usuario = new Usuario(nombre, apellidos, identificacion, correo, telefono, contrasena);
        Usuario.agregarCliente(usuario);
        listaClientes.add(usuario);
    }

    private static Usuario buscarUsuario(String identificacion) {
        for (Usuario usuario : listaClientes) {
            if (usuario.getIdentificacion().equals(identificacion)) {
                return usuario;
            }
        }
        return null;  // Usuario no encontrado
    }

    private static void cargarUsuariosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_USUARIOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Parsear la línea y agregar el usuario a la lista
                Usuario usuario = Usuario.parsearUsuarioDesdeString(linea);
                if (usuario != null) {
                    listaClientes.add(usuario);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar usuarios desde el archivo.");
            e.printStackTrace();
        }
    }
    public static void crearVehiculo(Usuario vendedor) {
        String color = JOptionPane.showInputDialog("Ingrese el color del vehículo:");
        int año = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año del vehículo:"));
        int cilindraje = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el cilindraje del vehículo:"));
        String marca = JOptionPane.showInputDialog("Ingrese la marca del vehículo:");
        String modelo = JOptionPane.showInputDialog("Ingrese el modelo del vehículo:");
        double kilometraje = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el kilometraje del vehículo:"));
        String tipo = JOptionPane.showInputDialog("Ingrese el tipo del vehículo (suv, sedan, hatchback):");
        String caracteristicas = JOptionPane.showInputDialog("Ingrese las características del vehículo:");
        String estado = JOptionPane.showInputDialog("Ingrese el Estado del Vehiculo:");

        Vehiculo nuevoVehiculo = new Vehiculo(color, año, cilindraje, marca, modelo, kilometraje, tipo, caracteristicas, estado);
        listaVehiculos.add(nuevoVehiculo);
    }

    private static void guardarUsuariosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_USUARIOS))) {
            for (Usuario usuario : listaClientes) {
                writer.write(usuario.convertirAString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuarios en el archivo.");
            e.printStackTrace();
        }
        
    }
    
}
 