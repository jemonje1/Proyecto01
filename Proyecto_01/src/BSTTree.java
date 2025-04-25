import java.util.function.Function;

public interface BSTTree <T> {
    void insert (T data);
    boolean contains(T data);
    void delete (T data);
    void bfs ();
    void levelTraversalID();
    Contacto search(Integer id);
    void searchStringer(String s);
    //Inorder para pruebas void inorderTraversalID();
}