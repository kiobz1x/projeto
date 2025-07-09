package dao;

import java.util.List;

public interface Persistivel<T> {
    List<T> carregar();
    void salvar(List<T> lista);
}
