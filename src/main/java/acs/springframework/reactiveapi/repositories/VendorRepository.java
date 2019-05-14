package acs.springframework.reactiveapi.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import acs.springframework.reactiveapi.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}
