import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

//Clase quelee los contactos desde un archivo CSV
public class LectorCSV {
    private BufferedReader lector;
    private String linea;
    private String partes[] = null;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String apodo;
    private String telefono;
    private Date fechaNacimiento;
    public LinkedList<Contacto> lista = new LinkedList<>();

    //Metodo que lee el archivo CSV linea por linea
    public void leerArchivo(String nombreArchivo) {
        try {
            lector = new BufferedReader(new FileReader(nombreArchivo));
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                partes = linea.split(",");
                imprimirLinea();
                System.out.println();

                if (partes.length == 8) {
                    nombre = partes[1];
                    apellido = partes[2];
                    apodo = partes[3];
                    telefono = partes[4];
                    email = partes[5];
                    direccion = partes[6];
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        fechaNacimiento = inputFormat.parse(partes[7]);
                        String fechaFormateada = outputFormat.format(fechaNacimiento);
                    } catch (Exception e) {
                        System.out.println("Error al convertir la fecha: " + partes[7]);
                    }
                    //Crea el contacto y lo añade a la lista
                    Contacto contact = new Contacto(nombre, apellido, apodo, telefono, email, direccion, fechaNacimiento);
                    lista.add(contact);
                }
            }
            lector.close();
            linea = null;
            partes = null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public LinkedList<Contacto> getLista() {
        return lista;
    }

    //Metodo para imprimir en cosola
    public void imprimirLinea() {
        for (int i = 0; i < partes.length; i++) {
            System.out.print(partes[i] + " | ");
        }
    }

}