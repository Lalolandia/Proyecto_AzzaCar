
package azzacar;

/**
 *
 * @author Luis
 */
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Login {
    public static void crearUsuario() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
        String apellidos = JOptionPane.showInputDialog("Ingrese los apellidos:");
        String identificacion = JOptionPane.showInputDialog("Ingrese la identificación:");
        String correo = JOptionPane.showInputDialog("Ingrese el correo electrónico:");
        String telefono = JOptionPane.showInputDialog("Ingrese el número de teléfono:");
        String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");

        // Crear el usuario y almacenar la información en un archivo .txt
        Usuario usuario = new Usuario(nombre, apellidos, identificacion, correo, telefono, contrasena);
        Usuario.agregarCliente(usuario);
    }

    private static void guardarUsuario(Usuario usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
            writer.write(usuario.toString());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario.");
            e.printStackTrace();
        }
    }
}
