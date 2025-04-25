import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Contacto {
    //Atributos del contacto
    String nombre;
    String apellido;
    String apodo;
    String telefono;
    String email;
    String direccion;
    Date fechaNacimiento;
    Integer id;


    private static int idCounter = 0;

    //Constructor
    public Contacto(String nombre, String apellido, String apodo, String telefono, String email, String direccion, Date fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.apodo = apodo;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.id = ++idCounter;
    }

    //Getters del contacto (para obtener su informacion)
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getApodo() {
        return apodo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    //Setters del contacto (Para modificar informacion)

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void mostrar(){
        System.out.println("Nombre: " + nombre+"\n");
        System.out.println("Apellido: " + apellido+"\n");
        System.out.println("Apodo: " + apodo+"\n");
        System.out.println("Telefono: " + telefono+"\n");
        System.out.println("Email: " + email+"\n");
        System.out.println("Direccion: " + direccion+"\n");
        System.out.println("Fecha Nacimiento: " + fechaNacimiento+"\n");
    }

}