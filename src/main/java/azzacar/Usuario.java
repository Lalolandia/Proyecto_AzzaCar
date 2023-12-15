package azzacar;

/**
 *
 * @author Luis
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Usuario {
    private static ArrayList<Usuario> listaClientes = new ArrayList<>();
    

    private String nombre;
    private String apellidos;
    private String identificacion;
    private String correo;
    private String telefono;
    private String contrasena;
    

    // Constructor
    public Usuario(String nombre, String apellidos, String identificacion, String correo, String telefono, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }
    

    // Métodos estáticos para gestionar la lista de clientes
    public static void agregarCliente(Usuario cliente) {
        if (!existeCliente(cliente.getIdentificacion())) {
            listaClientes.add(cliente);
            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Ya existe un cliente con esa identificación.");
        }
    }
    public static void cargarUsuariosDesdeArchivo(String ruta) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Parsear la línea y agregar el usuario a la lista
                Usuario usuario = parsearUsuarioDesdeString(linea);
                if (usuario != null) {
                    listaClientes.add(usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios desde el archivo.");
            e.printStackTrace();
        }
    }
    public static void guardarUsuariosEnArchivo(String ruta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Usuario usuario : listaClientes) {
                writer.write(usuario.convertirAString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios en el archivo.");
            e.printStackTrace();
        }
    }

    // Métodos de instancia para convertir el objeto a String y viceversa
    public static Usuario parsearUsuarioDesdeString(String linea) {
        String[] partes = linea.split(",");
        if (partes.length == 6) {
            // Asegúrate de manejar las excepciones al convertir tipos
            String nombre = partes[0].trim();
            String apellidos = partes[1].trim();
            String identificacion = partes[2].trim();
            String correo = partes[3].trim();
            String telefono = partes[4].trim();
            String contrasena = partes[5].trim();
            
            return new Usuario(nombre, apellidos, identificacion, correo, telefono, contrasena);
        } else {
            System.out.println("Formato incorrecto en la línea: " + linea);
            return null;
        }
    }
    public boolean validarContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }

    public String convertirAString() {
        return nombre + "," + apellidos + "," + identificacion + "," + correo + "," + telefono + "," + contrasena;
    }
    
    
    public static void mostrarClientes() {
        if (listaClientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados.");
        } else {
            StringBuilder lista = new StringBuilder("Clientes registrados:\n");
            for (Usuario cliente : listaClientes) {
                lista.append(cliente.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, lista.toString());
        }
    }

    // Métodos de instancia para obtener información del cliente
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContrasena() {
        return contrasena;
    }
    

    // Sobrescribir toString para obtener una representación legible del cliente
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nApellidos: " + apellidos + "\nIdentificación: " + identificacion +
               "\nCorreo: " + correo + "\nTeléfono: " + telefono;
    }

    // Método estático para verificar si un cliente ya está registrado
    private static boolean existeCliente(String identificacion) {
        for (Usuario cliente : listaClientes) {
            if (cliente.getIdentificacion().equals(identificacion)) {
                return true;
            }
        }
        return false;
    }
}
