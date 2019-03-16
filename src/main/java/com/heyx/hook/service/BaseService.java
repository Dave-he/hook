package com.heyx.hook.service;


import com.heyx.hook.dao.BaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseService<T, ID extends Serializable> {
    @Autowired
    BaseRepo<T, ID> repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<T> findAll(Specification<T> specification) {
        return repository.findAll(specification);
    }

    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    public List<T> findAll(Specification<T> specification, Sort sort) {
        return repository.findAll(specification, sort);
    }

    public Optional<T> findById(ID id) {
        if (null == id || "".equals(id)) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    public T getOne(ID id) {
        return repository.getOne(id);
    }

    public <S extends T> S save(S s) {
        return repository.save(s);
    }

    public <S extends T> S saveAndFlush(S s) {
        return repository.saveAndFlush(s);
    }

    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        return repository.saveAll(iterable);
    }

    public void flush() {
        repository.flush();
    }

    public void deleteById(ID id) throws Exception {
        repository.deleteById(id);
    }

    public void delete(T t) {
        repository.delete(t);
    }

    public void deleteAll(Iterable<? extends T> iterable) {
        repository.deleteAll(iterable);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteInBatch(Iterable<T> iterable) {
        repository.deleteInBatch(iterable);
    }

    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

}
