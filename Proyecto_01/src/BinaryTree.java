import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;


import java.util.Comparator;

//Clase generica de arbol BST que implementa su interfaz
public class BinaryTree<T> implements BSTTree<T> {
    private TreeNode<T> root;
    private Comparator<T> comparator;

    public BinaryTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    //Insercion de los contactos
    @Override
    public void insert(T data) {
        root = insertContact(root, data);
    }

    private TreeNode<T> insertContact(TreeNode<T> root, T data) {
        if (root == null) {
            return new TreeNode<>(data);
        }
        if (comparator.compare(data, root.data) < 0) {
            root.left = insertContact(root.left, data);
        } else {
            root.right = insertContact(root.right, data);
        }
        return root;
    }

    //Busca la existencia de los contactos
    @Override
    public boolean contains(T data) {
        return containsContact(root, data);
    }

    private boolean containsContact(TreeNode<T> root, T data) {
        if (root == null) {
            return false;
        }
        if (comparator.compare(data, root.data) == 0) {
            return true;
        }
        return comparator.compare(data, root.data) < 0 ? containsContact(root.left, data) : containsContact(root.right, data);
    }

    //Eliminacion
    @Override
    public void delete(T data) {
        root = deleteContact(root, data);
    }

    private TreeNode<T> deleteContact(TreeNode<T> root, T data) {
        if (root == null) {
            return null;
        }
        if (comparator.compare(data, root.data) < 0) {
            root.left = deleteContact(root.left, data);
        }
        else if (comparator.compare(data, root.data) > 0) {
            root.right = deleteContact(root.right, data);
        } else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            T minimun = minimunContact(root.right);
            root.data = minimun;
            root.right = deleteContact(root.right, minimun);
        }
        return root;
    }

    //Devuelve el valor minimo (mas a la izquierda) en un subarbol
    private T minimunContact(TreeNode<T> root) {
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    //Recorrido BFS
    @Override
    public void bfs() {
        levelTraversal();
    }

    private void levelTraversal() {
        if (root == null) {
            System.out.println("No hay contactos agregados");
            return;
        }
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.poll();
            Contacto contacto = (Contacto) node.data;
            System.out.println(contacto.getId() + " " +
                    contacto.getNombre() + "," +
                    contacto.getApellido() + "," +
                    contacto.getApodo() + "," +
                    contacto.getTelefono() + "," +
                    contacto.getEmail() + "," +
                    contacto.getDireccion() + "," +
                    contacto.getFechaNacimiento()+"\n");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    @Override
    public void levelTraversalID() {
        ID();
    }

    private void ID() {
        if (root == null) {
            System.out.println("Árbol vacío");
            return;
        }

        // Usamos una cola para el recorrido en anchura (BFS)
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            // Recorremos cada nivel
            for (int i = 0; i < size; i++) {
                TreeNode<T> node = queue.poll();

                if (node == null) {
                    System.out.print("null,");
                } else {
                    // Si el nodo es de tipo Contacto, imprimimos su ID
                    if (node.data instanceof Contacto contacto && contacto.getId() != null) {
                        System.out.print(contacto.getId() + ",");
                    } else {
                        System.out.print("null,");
                    }

                    // Solo agregamos los hijos si existen
                    if (node.left != null || node.right != null) {
                        queue.add(node.left);
                        queue.add(node.right);
                    }
                }
            }
        }
        System.out.println();
    }

    //Busqueda por ID
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

            if (node != null && node.data instanceof Contacto contacto) {
                if (
                        contacto.getNombre().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getApellido().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getApodo().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getDireccion().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getEmail().toLowerCase().contains(s.toLowerCase()) ||
                                contacto.getTelefono().toLowerCase().contains(s.toLowerCase())
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


    /*@Override
    public void inorderTraversalID() {
        inOrderTraversal(root);
    }*/

    private Contacto searchbyID(TreeNode<T> root, Integer id){
        if(root==null){
            return null;
        }
        else if(root.data instanceof Contacto contacto && contacto.getId().equals(id)){
            return contacto;
        }
        else if(id<((Contacto) root.data).getId()){
            return searchbyID(root.left, id);
        }
        else{
            return searchbyID(root.right, id);
        }
    }


    //inorder de pruebas
    /*private void inOrderTraversal(TreeNode<T> node) {
        if (node != null) {
            inOrderTraversal(node.left);

            if (node.data instanceof Contacto contacto) {
                System.out.println(contacto.getNombre() + " " + contacto.getApellido() + " - " + contacto.getId());
            } else {
                System.out.println("null");
            }

            inOrderTraversal(node.right);
        }
    }*/
}