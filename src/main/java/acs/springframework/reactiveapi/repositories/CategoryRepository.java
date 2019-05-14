package acs.springframework.reactiveapi.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import acs.springframework.reactiveapi.domain.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
