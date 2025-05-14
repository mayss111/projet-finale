package services;
import java.util.List;


public interface IService<T>   {
    void ajouter (T t);
    void modifier (T t);
    void suprrimer (int id);

    void supprimer(int idquiz);

    List <T> recuperer ();



}
