import java.io.*;
import java.util.List;

//Clase para exportar los contactos en un archivo CSV
public class ExportarCSV {

    public static void exportarContactos(List<Contacto> contactos, String archivo) {
        File archivos = new File(archivo);
        boolean existe = archivos.exists();

        //Abre en modo sobrescritura
        try (FileWriter fw = new FileWriter(archivo);
             BufferedWriter bw = new BufferedWriter(fw)) {

            //Si el archivo no existia antes, escribe el encabezado del CSV
            if (!existe) {
                bw.write("ID,Nombre,Apellido,Apodo,Teléfono,Correo,Dirección,FechaNacimiento\n");
            }

            for (Contacto c : contactos) {
                bw.write(c.getId() + "," +
                        c.getNombre() + "," +
                        c.getApellido() + "," +
                        c.getApodo() + "," +
                        c.getTelefono() + "," +
                        c.getEmail() + "," +
                        c.getDireccion() + "," +
                        c.getFechaNacimiento() + "\n");
            }
            System.out.println("Contactos exportados a: " + archivo);

        } catch (IOException e) {
            System.out.println("Error al exportar el archivo: " + e.getMessage());
        }
    }
}

