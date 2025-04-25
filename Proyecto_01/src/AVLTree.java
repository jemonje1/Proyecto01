import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

//Clase generica de arbol AVL que implementa su interfaz
public class AVLTree<T> implements AVLT<T> {

    private final Comparator<T> comparator;
    private TreeNode<T> root;

    //Constructor
    public AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    //Inserta un valor en el arbol
    @Override
    public void insert(T value) {
        root = insertarRecursivo(root, value);
    }

    //Elimina un valor en el arbol
    @Override
    public void delete(T value) {
        root = deleteRecursivo(root, value);
    }

    //Busca un valor en el arbol y si lo encuentra lo devuelve
    @Override
    public T search(T value) {
        TreeNode<T> node = searchRecursive(root, value);
        return node != null ? node.value : null;
    }

    //Altura del arbol
    @Override
    public int height() {
        return getAltura(root);
    }

    //Numero total de nodos en el arbol
    @Override
    public int size() {
        return calculateSize(root);
    }

    //Recorrido por Niveles BFS
    @Override
    public void LevelOrderTraversal() {
        BFS();
    }

    //Muestra en orden los IDs
    @Override
    public void showID() {
        ID();
    }

    //Devuelve objeto si se encuetra en el arbol
    @Override
    public T getContact (T data){
        return get(root, data);
    }

    @Override
    public Contacto search(Integer id) {
        return searchbyID(root, id);
    }

    @Override
    public void searchStringer(String s) {
        if (root == null) {
            System.out.println("Árbol vacío");
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        boolean encontrado = false;

        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.poll();

            if (node != null && node.value instanceof Contacto contacto) {
                if (
                        contacto.getNombre().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getApellido().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getApodo().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getDireccion().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getEmail().toLowerCase().contains(s.toLowerCase())
                ) {
                    System.out.println("ID: " + contacto.getId() + " | " +
                            contacto.getNombre() + " " +
                            contacto.getApellido() + " | Apodo: " +
                            contacto.getApodo() + " | Tel: " +
                            contacto.getTelefono() + " | Email: " +
                            contacto.getEmail() + " | Dirección: " +
                            contacto.getDireccion());
                    encontrado = true;
                }

                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún contacto con el dato: " + s);
        }
    }

    //Recorrido BFS (nivel por nivel) para imprimir datos de contacto
    private void BFS() {
        if (root == null) {
            System.out.println("No hay contactos agregados");
            return;
        }
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.poll();
            Contacto contacto = (Contacto) node.value;
            System.out.println(contacto.getNombre() + "," +
                    contacto.getApellido() + "," +
                    contacto.getApodo() + "," +
                    contacto.getTelefono() + "," +
                    contacto.getEmail() + "," +
                    contacto.getDireccion() + "," +
                    contacto.getFechaNacimiento() + "\n");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    //En orden muestra los IDs de los contactos
    private void ID() {
        if (root == null) {
            System.out.println("Árbol vacío");
            return;
        }
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        boolean seguir = true;
        while (seguir) {
            int size = queue.size();
            seguir = false;
            for (int i = 0; i < size; i++) {
                TreeNode<T> node = queue.poll();

                if (node == null) {
                    System.out.print("null,");
                } else {
                    if (node.value instanceof Contacto contacto && contacto.getId() != null) {
                        System.out.print(contacto.getId() + ",");
                    } else {
                        System.out.print("null,");
                    }
                    if (node.left != null || node.right != null) {
                        queue.add(node.left);
                        queue.add(node.right);
                        seguir = true;
                    }
                }
            }
        }
        System.out.println();
    }

    // Nodo del árbol AVL
    private static class TreeNode<T> {
        T value;
        TreeNode<T> left, right;
        int height;

        TreeNode(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private TreeNode<T> insertarRecursivo(TreeNode<T> node, T value) {
        if (node == null)
            return new TreeNode<>(value);

        if (comparator.compare(value, node.value) < 0) {
            node.left = insertarRecursivo(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = insertarRecursivo(node.right, value);
        } else {
            return node; // duplicado, no se inserta
        }

        return balance(node);
    }

    //Eliminacion recursiva
    private TreeNode<T> deleteRecursivo(TreeNode<T> node, T value) {
        if (node == null) return null;

        if (comparator.compare(value, node.value) < 0) {
            node.left = deleteRecursivo(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = deleteRecursivo(node.right, value);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            TreeNode<T> min = getMin(node.right);
            node.value = min.value;
            node.right = deleteRecursivo(node.right, min.value);
        }

        return balance(node);
    }

    //Busqueda Recursiva
    private TreeNode<T> searchRecursive(TreeNode<T> node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);
        if (cmp == 0) return node;
        else if (cmp < 0) return searchRecursive(node.left, value);
        else return searchRecursive(node.right, value);
    }

    private int getAltura(TreeNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(TreeNode<T> node) {
        return node == null ? 0 : getAltura(node.left) - getAltura(node.right);
    }

    //Balancea el arbol AVL después de inserciones/eliminaciones
    private TreeNode<T> balance(TreeNode<T> node) {
        actualizarAltura(node);
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    //Se actualiza la altura del nodo
    private void actualizarAltura(TreeNode<T> node) {
        node.height = 1 + Math.max(getAltura(node.left), getAltura(node.right));
    }

    //Rotaciones
    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.left;
        TreeNode<T> temp = x.right;

        x.right = y;
        y.left = temp;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }


    private TreeNode<T> rotateLeft(TreeNode<T> x) {
        TreeNode<T> y = x.right;
        TreeNode<T> temp = y.left;

        y.left = x;
        x.right = temp;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    //Devuelve el minimo del subarbol
    private TreeNode<T> getMin(TreeNode<T> node) {
        TreeNode<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    //Devuelve la cantidad total de los nodos
    private int calculateSize(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + calculateSize(node.left) + calculateSize(node.right);
    }

    //Obtiene el nodo que contiene el valor exacto si existe
    private T get(TreeNode<T> node, T data){
        if(node == null){
            return null;
        }
        if (comparator.compare(data, node.value) == 0) {
            return node.value;
        }
        else if (comparator.compare(data, node.value) < 0) {
            return get(node.left, data);
        }
        else {
            return get(node.right, data);
        }
    }

    private Contacto searchbyID(TreeNode<T> root, Integer id){
        if(root==null){
            return null;
        }
        else if(root.value instanceof Contacto contacto && contacto.getId().equals(id)){
            return contacto;
        }
        else if(id<((Contacto) root.value).getId()){
            return searchbyID(root.left, id);
        }
        else{
            return searchbyID(root.right, id);
        }
    }

}