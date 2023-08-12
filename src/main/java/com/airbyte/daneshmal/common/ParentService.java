package com.airbyte.daneshmal.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ParentService<MODEL, REPOSITORY extends JpaRepository<MODEL, String>, DTO> {
    protected final REPOSITORY repository;
    @PersistenceContext
    protected EntityManager entityManager;

    public ParentService(REPOSITORY repository) {
        this.repository = repository;
    }

    public MODEL save(DTO dto) {
        preSave(dto);
        MODEL model = convertDTO(dto);
        model = repository.save(model);
        postSave(model, dto);
        return decryptResponse(model);
    }

    protected MODEL decryptResponse(MODEL model) {
        return model;
    }

    protected void preSave(DTO dto) {
    }

    protected void postFetch(MODEL model) {
    }

    protected List<MODEL> postFetch(List<MODEL> models) {
        return models;
    }

    protected void postSave(MODEL model, DTO dto) {
    }

    public MODEL getOne(String id) {
        Optional<MODEL> model = repository.findById(id);
        if (model.isPresent()) {
            MODEL response = decryptResponse(model.get());
            postFetch(response);
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "model with this id not found");
        }
    }

    public List<MODEL> getAll() {
        List<MODEL> modelList = new ArrayList<>();
        modelList = repository.findAll();
        modelList = postFetch(modelList);
        return modelList;
    }

    public Page<MODEL> getAll(Pageable pageable) {
        List<MODEL> modelList = new ArrayList<>();
        modelList = repository.findAll();
        modelList = postFetch(modelList);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), modelList.size());
        final Page<MODEL> page = new PageImpl<>(modelList.subList(start, end), pageable, modelList.size());
        return page;
    }

    public MODEL update(String id, DTO dto) {
        MODEL model = decryptResponse(getOne(id));
        preSave(dto);
        model = updateModelFromDto(model, dto);
        model = repository.save(model);
        postUpdate(model, dto);
        return decryptResponse(model);
    }

    protected void postUpdate(MODEL model, DTO dto) {
    }

    public void delete(String id) {
        if (repository.findById(id).isPresent()) {
            preDelete(repository.findById(id).get());
            repository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "model with this id not found");
        }
    }

    protected void preDelete(MODEL model) {
    }

    public abstract MODEL updateModelFromDto(MODEL model, DTO dto);

    public abstract MODEL convertDTO(DTO dto);

    public abstract List<MODEL> getWithSearch(DTO search);

    public void deleteAllById(List<String> ids) {
        if (ids.size() > 0) {
            repository.deleteAllById(ids);
        }
    }
}
