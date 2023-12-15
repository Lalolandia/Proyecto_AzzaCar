package azzacar;
/**
 * @author Luis
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.ArrayList;

class Vehiculo {
    private String color;
    private int año;
    private int cilindraje;
    private String marca;
    private String modelo;
    private double kilometraje;
    private String tipo;
    private String caracteristicas;
    private String estado;
    private Usuario cliente;
    private Usuario vendedor;

    public Vehiculo(String color, int año, int cilindraje, String marca, String modelo, double kilometraje, String tipo, String caracteristicas, String estado1) {
        this.color = color;
        this.año = año;
        this.cilindraje = cilindraje;
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.tipo = tipo;
        this.caracteristicas = caracteristicas;
        this.estado = "disponible";
        this.cliente = null;
        this.vendedor = null;
    }

    

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }
    

    public void reservarVehiculo(Usuario cliente) {
        if (estado.equals("disponible")) {
            estado = "reservado";
            this.cliente = cliente;
            this.vendedor = vendedor;
            JOptionPane.showMessageDialog(null, "Vehículo reservado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "El vehículo no está disponible para reservar.");
        }
    }

    public void venderVehiculo() {
        if (estado.equals("reservado") && cliente != null && vendedor != null) {
            estado = "vendido";
            JOptionPane.showMessageDialog(null, "Vehículo vendido exitosamente a " + cliente.getNombre() + ".");
        } else {
            JOptionPane.showMessageDialog(null, "El vehículo no puede ser vendido en este momento.");
        }
    }

    public void cancelarReserva() {
        if (estado.equals("reservado")) {
            estado = "disponible";
            cliente = null;
            vendedor = null;
            JOptionPane.showMessageDialog(null, "Reserva cancelada. El vehículo vuelve a estar disponible.");
        } else {
            JOptionPane.showMessageDialog(null, "El vehículo no está reservado en este momento.");
        }
    }

    public String mostrarInformacion() {
        return "Color: " + color + "\nAño: " + año + "\nCilindraje: " + cilindraje + "\nMarca: " + marca +
                "\nModelo: " + modelo + "\nKilometraje: " + kilometraje + "\nTipo: " + tipo +
                "\nCaracterísticas: " + caracteristicas + "\nEstado: " + estado +
                "\nCliente: " + (cliente != null ? cliente.getNombre() : "N/A") +
                "\nVendedor: " + (vendedor != null ? vendedor.getNombre() : "N/A");
    }
}

public class GestionVehiculos {
    private static final ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
    private static final String RUTA_VEHICULOS = "vehiculos.txt";
    

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
        guardarVehiculosEnArchivo();
        

        JOptionPane.showMessageDialog(null, "Vehículo creado exitosamente.");
    }
    
     public static void cargarVehiculosDesdeArchivo(String RUTA_VEHICULOS) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_VEHICULOS))) {
            listaVehiculos.clear(); // Limpiar la lista antes de cargar vehículos

            String linea;
            while ((linea = br.readLine()) != null) {
                Vehiculo vehiculo = parsearLineaYCrearVehiculo(linea);
                if (vehiculo != null) {
                    listaVehiculos.add(vehiculo);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar vehículos desde el archivo.");
            e.printStackTrace();
        }
    }
      public static void guardarVehiculosEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_VEHICULOS))) {
            for (Vehiculo vehiculo : listaVehiculos) {
                bw.write(convertirVehiculoACadena(vehiculo));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar vehículos en el archivo.");
            e.printStackTrace();
        }
    }
      
     private static Vehiculo parsearLineaYCrearVehiculo(String linea) {
    String[] partes = linea.split(",");
    
    if (partes.length >= 9) { 
        
        String color = partes[0].trim();
        int año = Integer.parseInt(partes[1].trim());
        int cilindraje = Integer.parseInt(partes[2].trim());
        String marca = partes[3].trim();
        String modelo = partes[4].trim();
        double kilometraje = Double.parseDouble(partes[5].trim());
        String tipo = partes[6].trim();
        String caracteristicas = partes[7].trim();
        String estado = partes[8].trim();

        

        // Crear y devolver un objeto Vehiculo con los datos parseados
        return new Vehiculo(color, año, cilindraje, marca, modelo, kilometraje, tipo, caracteristicas, estado);
    } else {
        System.out.println("La línea no tiene suficientes partes para crear un vehículo.");
        return null;
    }
}
     
     private static String convertirVehiculoACadena(Vehiculo vehiculo) {
    StringBuilder cadena = new StringBuilder();

    // Añadir cada atributo del vehículo a la cadena, separados por comas
    cadena.append(vehiculo.getColor()).append(",");
    cadena.append(vehiculo.getAño()).append(",");
    cadena.append(vehiculo.getCilindraje()).append(",");
    cadena.append(vehiculo.getMarca()).append(",");
    cadena.append(vehiculo.getModelo()).append(",");
    cadena.append(vehiculo.getKilometraje()).append(",");
    cadena.append(vehiculo.getTipo()).append(",");
    cadena.append(vehiculo.getCaracteristicas()).append(",");
    cadena.append(vehiculo.getEstado());

   

    return cadena.toString();
}

     
    public static void mostrarVehiculos() {
        if (listaVehiculos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos registrados.");
        } else {
            StringBuilder mensaje = new StringBuilder("Vehículos registrados:\n");
            for (Vehiculo vehiculo : listaVehiculos) {
                mensaje.append(vehiculo.mostrarInformacion()).append("\n------------------------\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString());
        }
    }

    public static void actualizarEstadoVehiculo(Usuario cliente, Usuario vendedor, boolean vender) {
        if (listaVehiculos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos registrados.");
        } else {
            mostrarVehiculos();
            int indiceVehiculo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el índice del vehículo que desea actualizar:"));

            if (indiceVehiculo >= 0 && indiceVehiculo < listaVehiculos.size()) {
                Vehiculo vehiculo = listaVehiculos.get(indiceVehiculo);

                if (vender) {
                    vehiculo.venderVehiculo();
                } else {
                    vehiculo.reservarVehiculo(cliente);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Índice de vehículo no válido.");
            }
        }
    }
    public static void reservarVehiculo(Usuario cliente) {
        if (listaVehiculos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos registrados.");
            return;
        }

        mostrarVehiculos();

        int indiceVehiculo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el índice del vehículo que desea reservar:"));

        if (indiceVehiculo >= 0 && indiceVehiculo < listaVehiculos.size()) {
            Vehiculo vehiculo = listaVehiculos.get(indiceVehiculo);

            if (vehiculo.getEstado().equals("disponible")) {
                vehiculo.reservarVehiculo(cliente);
                JOptionPane.showMessageDialog(null, "Vehículo reservado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El vehículo no está disponible para reservar.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Índice de vehículo no válido.");
        }
    }
    public static void venderVehiculo(Usuario cliente) {
        if (listaVehiculos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos registrados.");
            return;
        }

        mostrarVehiculos();

        int indiceVehiculo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el índice del vehículo que desea vender:"));

        if (indiceVehiculo >= 0 && indiceVehiculo < listaVehiculos.size()) {
            Vehiculo vehiculo = listaVehiculos.get(indiceVehiculo);

            if (vehiculo.getEstado().equals("reservado") && vehiculo.getCliente() != null && vehiculo.getVendedor() != null) {
                vehiculo.venderVehiculo();
                JOptionPane.showMessageDialog(null, "Vehículo vendido exitosamente a " + vehiculo.getCliente().getNombre() + ".");
            } else {
                JOptionPane.showMessageDialog(null, "El vehículo no puede ser vendido en este momento.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Índice de vehículo no válido.");
        }
    }

}

