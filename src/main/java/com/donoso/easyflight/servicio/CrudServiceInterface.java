package com.donoso.easyflight.servicio;

import java.util.List;

public interface CrudServiceInterface<T> {

    public void save(T object);

    public void update(T object);

    public void delete(T object);

    public List<T> search(T object);

    public T findById(T object);
}
