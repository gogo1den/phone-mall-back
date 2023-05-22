package com.example.demo.service;

import com.example.demo.model.ProductEntity;
import com.example.demo.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<ProductEntity> create(final ProductEntity entity) {
        //Validations
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }

        repository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getUserId());

        return retrieve(entity.getUserId());

    }

    public List<ProductEntity>retrieve(final String userId) {
        return repository.findByUserId(userId);

    }

    public List<ProductEntity> retrieveAll() {
        return repository.findAll();
    }

    public Optional<ProductEntity> search(final String title) {
        final Optional<ProductEntity> origin = repository.findByTitle(title);
        return origin;
    }


    public Optional<ProductEntity> update(ProductEntity entity) {
        validate(entity);

        final Optional<ProductEntity> original = repository.findByTitle(entity.getTitle());

        original.ifPresent(product -> {
            product.setTitle(entity.getTitle());
            product.setMaker(entity.getMaker());
            product.setColor(entity.getColor());
            product.setUserId(entity.getUserId());

            repository.save(product);
        });

        return search(entity.getTitle());
    }

    public List<ProductEntity> delete(final ProductEntity entity) {
        validate(entity);

        final ProductEntity origin = search(entity.getTitle()).get();

        try{
            repository.delete(origin);
        } catch(Exception e){
            log.error("error deleting entity", entity.getId(), e);

            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieveAll();
    }

    private void validate(final ProductEntity entity){
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }


}
