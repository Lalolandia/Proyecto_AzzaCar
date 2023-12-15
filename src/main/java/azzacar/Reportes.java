package azzacar;

/**
 *
 * @author Luis
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Reportes {

    public static void reporteCantidadVehiculos(ArrayList<Vehiculo> listaVehiculos) {
        int vendidos = 0, reservados = 0, disponibles = 0;

        for (Vehiculo vehiculo : listaVehiculos) {
            switch (vehiculo.getEstado()) {
                case "vendido":
                    vendidos++;
                    break;
                case "reservado":
                    reservados++;
                    break;
                case "disponible":
                    disponibles++;
                    break;
            }
        }

        JOptionPane.showMessageDialog(null,
                "Reporte de Cantidad de Vehículos:\n" +
                        "Vendidos: " + vendidos + "\n" +
                        "Reservados: " + reservados + "\n" +
                        "Disponibles: " + disponibles,
                "Reporte", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void reporteCantidadClientes(ArrayList<Usuario> listaUsuarios, ArrayList<Vehiculo> listaVehiculos) {
        int totalClientes = listaUsuarios.size();
        int clientesQueCompraron = 0, clientesQueReservaron = 0;

        for (Vehiculo vehiculo : listaVehiculos) {
            if (vehiculo.getCliente() != null) {
                clientesQueReservaron++;
                if (vehiculo.getEstado().equals("vendido")) {
                    clientesQueCompraron++;
                }
            }
        }

        JOptionPane.showMessageDialog(null,
                "Reporte de Cantidad de Clientes:\n" +
                        "Total de Clientes: " + totalClientes + "\n" +
                        "Clientes que compraron un vehículo: " + clientesQueCompraron + "\n" +
                        "Clientes que reservaron un vehículo: " + clientesQueReservaron,
                "Reporte", JOptionPane.INFORMATION_MESSAGE);
    }

    // Puedes implementar otros métodos para los demás reportes

}
