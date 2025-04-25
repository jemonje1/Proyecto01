public interface AVLT <T>{
    void insert (T value);
    void delete (T value);
    T search (T value);
    int height();
    int size ();
    void LevelOrderTraversal();
    void showID();
    T getContact (T data);
    Contacto search(Integer id);
    void searchStringer(String s);

}
