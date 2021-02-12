package dao;

import java.util.List;

public interface Dao<E> {
    String add(E entity);
    E getById(int id);
    List<E> getList();
    List<E> searchBy(String criterion,String data);
    String setById(E entity);
    String deleteById(int id);
}
