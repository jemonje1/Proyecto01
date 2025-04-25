import java.io.IOException;
import java.util.*;

/*
 * Parche 0.5
 * Exposición
 * */

public class Main {
    //Instancias
    public static LinkedList<Contacto> list = new LinkedList<>();
    public static BSTTree<Contacto>[] array = new BSTTree[7];
    public static AVLT<Contacto>[] arrayAVL = new AVLT[7];

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        //Bienvenida al usuario
        System.out.println("==============================================\n");
        System.out.println("Bienvenido al programa de Contactos  Version 0.5\n");
        System.out.println("==============================================\n");
        System.out.println("Antes de iniciar elija su primer arbol que desee crear para almacenar sus contactos: ");
        menuArboles();
        boolean salida = false;
        while (!salida) {
            menu();
            int opcionesDos = scanner.nextInt();
            // Validamos la accion que quiere realizar el usuario
            switch (opcionesDos) {
                case 1:
                    crearContacto();
                    break;
                case 2:
                    menuArboles();
                    break;
                case 3:
                    editarContactos();
                    break;
                case 4:
                    borrarContactos();
                    break;
                case 5:
                    System.out.println("=============================================\n");
                    System.out.println("Exportando contactos...");
                    exportarDesdeArbol();
                    System.out.println("=============================================\n");
                    break;
                case 6:
                    insertarEnArboles();
                    break;
                case 7:
                    verArbol();
                    break;
                case 8:
                    mostrarContactos();
                    break;
                case 9:
                    System.out.println("Ingrese el id del contacto que quiere ver: ");
                    int id = new Scanner(System.in).nextInt();
                    Contacto c = null;
                    for (int i = 0; i < array.length; i++) {
                        if (array[i] != null) {
                            c = array[i].search(id);
                            if (c != null) break;
                        }
                        if (arrayAVL[i] != null) {
                            c = arrayAVL[i].search(id);
                            if (c != null) break;
                        }
                    }
                    if (c != null) {
                        c.mostrar();
                    } else {
                        System.out.println("No existe el contacto con el id " + id + ".");
                    }
                    break;
                case 10:
                    System.out.println("Gracias por utilizar nuestros servicios");
                    System.out.println("Apagando sistema");
                    System.out.println(". . .");
                    salida = true;
                    break;
                /*case 11:
                    array[0].inorderTraversalID();
                    break;*/
                default:
                    System.out.println("==============================================\n");
                    System.out.println("Opcion no valida");
                    System.out.println("==============================================\n");
                    break;
            }
        }
    }

    //Muestra el menu principal
    public static void menu() {
        //Solicitamos al usuario la accion que quiere realizar
        System.out.println("\n==============================================\n");
        System.out.println("Elija la opcion que desee realizar");
        System.out.println("1. Crear Contactos\n2. Crear arbol\n3. Editar Contactos\n4. Eliminar Contactos\n5. Exportar contactos\n6. Importar contactos\n7. Ver arbol\n8. Ver Listado de contactos\n9. Buscar contacto específico\n10. Salir\n");
        System.out.println("==============================================\n");
    }

    //Muestra submenu para el tipo de arbol
    public static void menuSeleccion() {
        //Solicitamos la accion que el usuario quiere realizar
        System.out.println("\n==============================================\n");
        System.out.println("Elija una forma de guardar los contactos que almacenara");
        System.out.println("1. Arbol de busqueda");
        System.out.println("2. Arbol AVL");
        System.out.println("\n==============================================\n");
    }

    //Metodo que solicita al usuario datos de un contacto
    public static void crearContacto() {
        Scanner scanner = new Scanner(System.in);
        //Solicitamos la informacion del contacto a agregar
        System.out.println("\n==============================================\n");
        System.out.println("Ingrese el nombre del contacto: ");
        String nombre = new Scanner(System.in).nextLine();
        System.out.println("Ingrese el apellido del contacto: ");
        String apellido = new Scanner(System.in).nextLine();
        System.out.println("Ingrese el apodo del contacto: ");
        String apodo = new Scanner(System.in).nextLine();
        System.out.println("Ingrese el telefono del contacto: ");
        String telefono = new Scanner(System.in).nextLine();
        System.out.println("Ingrese el email del contacto: ");
        String correo = new Scanner(System.in).nextLine();
        System.out.println("Ingrese la direccion del contacto: ");
        String direccion = new Scanner(System.in).nextLine();
        System.out.print("Ingrese el día de nacimiento: ");
        int dia = scanner.nextInt();
        System.out.print("Ingrese el mes de nacimiento: ");
        int mes = scanner.nextInt(); // Ojo: Enero = 0, Diciembre = 11
        System.out.print("Ingrese el año de nacimiento: ");
        int anio = scanner.nextInt();
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio, mes - 1, dia); // Restamos 1 al mes porque en Calendar los meses van de 0 a 11
        Date fechaNacimiento = calendario.getTime();

        Contacto contacto = new Contacto(nombre, apellido, apodo, telefono, correo, direccion, fechaNacimiento);
        list.add(contacto);
        //Recorre ambos arreglos (array y arrayAVL) hasta la longitud del más largo
        // Y tambien inserta el contacto en todas las posiciones no nulas en cada arreglo
        for (int i = 0; i < Math.max(array.length, arrayAVL.length); i++) {
            //Inserta en arbol binario si esta creado
            if (i < array.length && array[i] != null) {
                array[i].insert(contacto);

            }
            //Inserta en arbol AVL si esta creado
            if (i < arrayAVL.length && arrayAVL[i] != null) {
                arrayAVL[i].insert(contacto);
            }
        }
        System.out.println("El contacto de ID " + contacto.getId() + " se ha guardado correctamente");
    }

    //Exporta los contactos guardados a un archivo CSV
    public static void exportarDesdeArbol() {
        //Muestra el submenu
        printsubmenu();
        LinkedList<Contacto> lista = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {
            //Copia los contactos de "list" a "lista"
            lista.add(list.get(i));
        }
        ExportarCSV exportar = new ExportarCSV();
        //Exporta al archivo CSV
        exportar.exportarContactos(lista, "Contactitos.csv");
    }

    //Metodo de submenu de arboles BST
    public static void submenuArbolBST() {
        //Comparadores para ordenar contactos segun sus atributos
        Comparator<Contacto> nombreComparator = Comparator.comparing(c -> c.getNombre());
        Comparator<Contacto> emailComparator = Comparator.comparing(c -> c.getEmail());
        Comparator<Contacto> direccionComparator = Comparator.comparing(c -> c.getDireccion());
        Comparator<Contacto> apellidoComparator = Comparator.comparing(c -> c.getApellido());
        Comparator<Contacto> apodoComparator = Comparator.comparing(c -> c.getApodo());
        Comparator<Contacto> telefonoComparator = Comparator.comparing(c -> c.getTelefono());
        Comparator<Contacto> idComparator = Comparator.comparingInt(c -> c.getId());
        //Lee la entrada del usuario
        Scanner scanner = new Scanner(System.in);


        boolean salida = false;
        while (!salida) {
            //Muestra el submenu
            printsubmenu();
            int opciones = scanner.nextInt();
            //Valida la opcion para crear un arbol BS a un campo si aun no existe
            switch (opciones) {
                case 1:
                    //Arbol por nombre
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        array[opciones - 1] = new BinaryTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                array[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de nombres\n");
                        salida = true;
                    }
                    break;
                case 2:
                    //Arbol por apellido
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        array[opciones - 1] = new BinaryTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                array[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de apellidos\n");
                        salida = true;
                    }
                    break;
                case 3:
                    //Arbol por apodo
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        array[opciones - 1] = new BinaryTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                array[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de apodos\n");
                        salida = true;
                    }
                    break;
                case 4:
                    //Arbol por telefono
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        array[opciones - 1] = new BinaryTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                array[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de telefonos\n");
                        salida = true;
                    }
                    break;
                case 5:
                    //Arbol por direccion
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        array[opciones - 1] = new BinaryTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                array[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de direccion\n");
                        salida = true;
                    }
                    break;
                case 6:
                    //Arbol por emails
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        array[opciones - 1] = new BinaryTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                array[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de emails\n");
                        salida = true;
                    }
                    break;
                case 7:
                    //Arbol por Id
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        array[opciones - 1] = new BinaryTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                array[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de IDs\n");
                        salida = true;
                    }
                    break;
                default:
                    //Opcion no valida
                    System.out.println("=============================================\n");
                    System.out.println("Opcion no valida");
                    System.out.println("=============================================\n");
                    break;
            }
        }
    }

    //Metodo de submenu de arboles AVL
    public static void subMenuArbolAVL() {
        //Comparadores para ordenar contactos segun sus atributos
        Comparator<Contacto> nombreComparator = Comparator.comparing(c -> c.getNombre());
        Comparator<Contacto> emailComparator = Comparator.comparing(c -> c.getEmail());
        Comparator<Contacto> direccionComparator = Comparator.comparing(c -> c.getDireccion());
        Comparator<Contacto> apellidoComparator = Comparator.comparing(c -> c.getApellido());
        Comparator<Contacto> apodoComparator = Comparator.comparing(c -> c.getApodo());
        Comparator<Contacto> telefonoComparator = Comparator.comparing(c -> c.getTelefono());
        Comparator<Contacto> idComparator = Comparator.comparingInt(c -> c.getId());
        //Lee la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        boolean salida = false;
        while (!salida) {
            printsubmenu();
            int opciones = scanner.nextInt();
            //Valida la opcion para crear un arbol AVL a un campo si aun no existe
            switch (opciones) {
                case 1:
                    //Arbol por nombre
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        arrayAVL[opciones - 1] = new AVLTree<>(nombreComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                arrayAVL[opciones - 1].insert(elemento);
                            }
                        }
                        System.out.println("Arbol creado\n");
                        salida = true;
                    } else {
                        System.out.println("Ya tiene un arbol de nombres\n");
                        salida = true;
                    }
                    break;
                case 2:
                    //Arbol por Apellido
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        arrayAVL[opciones - 1] = new AVLTree<>(apellidoComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                arrayAVL[opciones - 1].insert(elemento);
                            }
                        }
                        salida = true;
                        System.out.println("Arbol creado\n");
                    } else {
                        System.out.println("Ya tiene un arbol de apellidos\n");
                        salida = true;
                    }
                    break;
                case 3:
                    //Arbol por apodo
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        arrayAVL[opciones - 1] = new AVLTree<>(apodoComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                arrayAVL[opciones - 1].insert(elemento);
                            }
                        }
                        salida = true;
                        System.out.println("Arbol creado\n");
                    } else {
                        System.out.println("Ya tiene un arbol de apodos\n");
                        salida = true;
                    }
                    break;
                case 4:
                    //Arbol por Telefono
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        arrayAVL[opciones - 1] = new AVLTree<>(telefonoComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                arrayAVL[opciones - 1].insert(elemento);
                            }
                        }
                        salida = true;
                        System.out.println("Arbol creado\n");
                    } else {
                        System.out.println("Ya tiene un arbol de telefonos\n");
                        salida = true;
                    }
                    break;
                case 5:
                    //Arbol por direccion
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        arrayAVL[opciones - 1] = new AVLTree<>(direccionComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                arrayAVL[opciones - 1].insert(elemento);
                            }
                        }
                        salida = true;
                        System.out.println("Arbol creado\n");
                    } else {
                        System.out.println("Ya tiene un arbol de direccion\n");
                        salida = true;
                    }
                    break;
                case 6:
                    //Arbol por emails
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        arrayAVL[opciones - 1] = new AVLTree<>(emailComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                arrayAVL[opciones - 1].insert(elemento);
                            }
                        }
                        salida = true;
                        System.out.println("Arbol creado\n");
                    } else {
                        System.out.println("Ya tiene un arbol de emails\n");
                        salida = true;
                    }
                    break;
                case 7:
                    //Arbol por Id
                    if (array[opciones - 1] == null && arrayAVL[opciones - 1] == null) {
                        arrayAVL[opciones - 1] = new AVLTree<>(idComparator);
                        if (list != null) {
                            for (Contacto elemento : list) {
                                arrayAVL[opciones - 1].insert(elemento);
                            }
                        }
                        salida = true;
                        System.out.println("Arbol creado\n");
                    } else {
                        System.out.println("Ya tiene un arbol de IDs\n");
                        salida = true;
                    }
                    break;
                default:
                    //Opcion no valida
                    System.out.println("=============================================\n");
                    System.out.println("Opcion no valida");
                    System.out.println("=============================================\n");
                    break;
            }
        }
    }

    //Metodo para mostrar el menu en consola
    public static void printsubmenu() {
        //Interfaz del Sub Menu
        System.out.println("\n=============================================\n");
        System.out.println("Elija como le gustaria ver los contactos");
        System.out.println("1. Nombre\n2. Apellido\n3. Apodo\n4. Telefono\n5. Direccion\n6. Email\n7. ID\n");
        System.out.println("=============================================\n");
    }

    public static void opcioncitas() {
        //Interfaz del Sub Menu
        System.out.println("\n=============================================\n");
        System.out.println("1. Nombre\n2. Apellido\n3. Apodo\n4. Telefono\n5. Direccion\n6. Email\n7. ID\n");
        System.out.println("=============================================\n");
    }

    //Metodo para seleccionar el tipo de arbol a utilizar
    public static void menuArboles() {
        Scanner scanner = new Scanner(System.in);
        Boolean SalidaInicio = false;
        while (!SalidaInicio) {
            menuSeleccion();
            int opciones = scanner.nextInt();
            switch (opciones) {
                case 1:
                    SalidaInicio = true;
                    submenuArbolBST();
                    break;
                case 2:
                    SalidaInicio = true;
                    subMenuArbolAVL();
                    break;
                default:
                    System.out.println("=============================================\n");
                    System.out.println("Opcion no valida");
                    System.out.println("=============================================\n");
                    break;
            }
        }
    }

    //C:\Users\javu2\Documents\Proyecto_01
    //Metodo que importa contactos desde archivos CSV y los inserta en todos los arboles
    public static void insertarEnArboles() {
        //Se llama a la clase donde se lee y se imprimen los archivos CSV
        LectorCSV archivoCSV = new LectorCSV();
        archivoCSV.leerArchivo("C:\\Users\\javu2\\Downloads\\contacts.csv");
        //Inserta cada contacto en los arboles BST existentes
        for (BSTTree<Contacto> arbol : array) {
            if (arbol != null) {
                for (int i = 0; i < archivoCSV.lista.size(); i++) {
                    arbol.insert(archivoCSV.lista.get(i));
                }
            }
        }
        //Inserta cada contacto en los arboles AVL existentes
        for (AVLT<Contacto> arbolAVL : arrayAVL) {
            if (arbolAVL != null) {
                for (int i = 0; i < archivoCSV.lista.size(); i++) {
                    arbolAVL.insert(archivoCSV.lista.get(i));
                }
            }
        }

        LinkedList<Contacto> listaPivot = new LinkedList<>();
        listaPivot = archivoCSV.getLista();

        for (int i = 0; i < listaPivot.size(); i++) {
            list.add(listaPivot.get(i));
        }

        System.out.print("Sus contactos han sido insertados en todos los arboles!");
    }

    //Metodo para visualizar un arbol BST o AVL
    public static void verArbol() {
        //Llamada del sub Menu
        printsubmenu();
        int opciones = new Scanner(System.in).nextInt() - 1;

        //Valida que la opcion este dentro del rango
        if (opciones < 0 || opciones >= array.length || opciones >= arrayAVL.length) {
            System.out.println("Opción no válida.");
            return;
        }
        //Verifica si no hay arboles en el campo seleccionado
        if (array[opciones] == null && arrayAVL[opciones] == null) {
            System.out.println("No tiene árboles en esta categoría.");
        } else {
            //Muestra con recorrido por niveles si hay un arbol BST
            if (array[opciones] != null) {
                array[opciones].levelTraversalID();
            } else {
                //Muestra arbol AVL segun el campo
                arrayAVL[opciones].showID();
            }
        }
    }

    //Muestra los contactos que se almacenaron en el arbol seleccionado
    public static void mostrarContactos() {
        //Muestra las opciones disponibles
        printsubmenu();
        int opciones = new Scanner(System.in).nextInt() - 1;

        //Verifica si la opcion esta dentro del rango
        if (opciones < 0 || opciones >= array.length || opciones >= arrayAVL.length) {
            System.out.println("Opción no válida.");
            return;
        }
        //Verifica si existe un arbol en la categoria
        if (array[opciones] == null && arrayAVL[opciones] == null) {
            System.out.println("No tiene árboles en esta categoría.");
        } else {
            //Muestra los contactos en el arbol BST si existe
            if (array[opciones] != null) {
                array[opciones].bfs();
            } else {
                //Muestra los contactos en el arbol AVL si no existe BST
                arrayAVL[opciones].LevelOrderTraversal();
            }
        }
    }

    //Metodo para editar los contactos agregados
    public static void editarContactos() {
        System.out.println("\n==============================================\n");
        System.out.println("Ingrese el id del contacto que quiere editar: ");
        System.out.println("\n==============================================\n");
        int id = new Scanner(System.in).nextInt();
        Contacto c = null;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                c = array[i].search(id);
                if (c != null) {
                    editor(c, array[i], arrayAVL[i]);
                    break;
                }
            }
            if (arrayAVL[i] != null) {
                c = arrayAVL[i].search(id);
                if (c != null) {
                    editor(c, array[i], arrayAVL[i]);
                    break;
                }
            }
        }

        if (c != null) {
            c.mostrar();
        } else {
            System.out.println("No existe el contacto con el id " + id + ".");
        }
    }

    public static void editor(Contacto c, BSTTree<Contacto> tree, AVLT<Contacto> avl) {
        if (tree != null) tree.delete(c);
        if (avl != null) avl.delete(c);
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nIngrese el campo que quiere editar:");
        opcioncitas();
        int id = scanner.nextInt();
        scanner.nextLine(); // limpiar salto de línea
        switch (id) {
            case 1:
                System.out.println("Ingrese el nuevo nombre:");
                c.setNombre(scanner.nextLine());
                break;
            case 2:
                System.out.println("Ingrese el nuevo apellido:");
                c.setApellido(scanner.nextLine());
                break;
            case 3:
                System.out.println("Ingrese el nuevo apodo:");
                c.setApodo(scanner.nextLine());
                break;
            case 4:
                System.out.println("Ingrese el nuevo telefono:");
                c.setTelefono(scanner.nextLine());
                break;
            case 5:
                System.out.println("Ingrese el nuevo email:");
                c.setEmail(scanner.nextLine());
                break;
            case 6:
                System.out.println("Ingrese la nueva direccion:");
                c.setDireccion(scanner.nextLine());
                break;
            case 7:
                System.out.print("Ingrese el día de nacimiento: ");
                int dia = scanner.nextInt();
                System.out.print("Ingrese el mes de nacimiento: ");
                int mes = scanner.nextInt();
                System.out.print("Ingrese el año de nacimiento: ");
                int anio = scanner.nextInt();
                Calendar calendario = Calendar.getInstance();
                calendario.set(anio, mes - 1, dia);
                Date fechaNacimiento = calendario.getTime();
                c.setFechaNacimiento(fechaNacimiento);
                break;
            default:
                System.out.println("=============================================\n");
                System.out.println("Opcion no valida");
                System.out.println("=============================================\n");
                return;
        }
        if (tree != null) tree.insert(c);
        if (avl != null) avl.insert(c);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == c.getId()) {
                list.set(i, c);
                break;
            }
        }
    }

    //Metodo para eliminar los contactos
    public static void borrarContactos() {
        System.out.println("Ingrese el id del contacto que quiere eliminar: ");
        int id = new Scanner(System.in).nextInt();
        Contacto c = null;
        for (int i = 0; i < array.length; i++) {
            // Buscar en BST
            if (array[i] != null) {
                Contacto encontrado = array[i].search(id);
                if (encontrado != null) {
                    borrarContactos(encontrado, array[i], null);
                    c = encontrado; // guardar referencia por si se necesita mostrar luego
                }
            }
            // Buscar en AVL
            if (arrayAVL[i] != null) {
                Contacto encontrado = arrayAVL[i].search(id);
                if (encontrado != null) {
                    borrarContactos(encontrado, null, arrayAVL[i]);
                    c = encontrado;
                }
            }
        }
        if (c != null) {
            System.out.println("Contacto eliminado:");
            c.mostrar();
        } else {
            System.out.println("No existe el contacto con el id " + id + ".");
        }
    }

    public static void borrarContactos(Contacto c, BSTTree<Contacto> tree, AVLT<Contacto> avl) {
        if (tree != null) {
            tree.delete(c);
        }
        if (avl != null) {
            avl.delete(c);
        }
        // Eliminar de la lista
        Iterator<Contacto> iter = list.iterator();
        while (iter.hasNext()) {
            Contacto contact = iter.next();
            if (contact.getId() == c.getId()) {
                iter.remove();
                break;
            }
        }
    }
}