package com.teampingui.interfaces;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    int insert(T t) throws Exception;

    void update(int index, T t);

    void delete(T t);
}
