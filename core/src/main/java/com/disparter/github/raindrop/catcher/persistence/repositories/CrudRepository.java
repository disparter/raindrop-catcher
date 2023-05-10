package com.disparter.github.raindrop.catcher.persistence.repositories;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    <S extends T> S save(S entity);
    Optional<T> findById(ID id);
    Iterable<T> findAll();
    long count();
    void deleteById(ID id);
    void delete(T entity);
    boolean existsById(ID id);
}
