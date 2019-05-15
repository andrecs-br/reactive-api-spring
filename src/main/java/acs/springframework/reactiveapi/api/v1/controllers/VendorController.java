package acs.springframework.reactiveapi.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import acs.springframework.reactiveapi.domain.Vendor;
import acs.springframework.reactiveapi.exceptions.ResourceNotFoundException;
import acs.springframework.reactiveapi.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {
	
	private VendorRepository vendorRepository;
	
	@Autowired
	public VendorController(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}
	
	@GetMapping
	public Flux<Vendor> getAllVendors() {
		return vendorRepository
				.findAll()
				.switchIfEmpty(
					Flux.defer(() -> Flux.error(new ResourceNotFoundException())
				));
	}
	
	@GetMapping("/{idVendor}")
	public Mono<Vendor> getVendorById(@PathVariable String idVendor) {
		return vendorRepository
				.findById(idVendor)
				.switchIfEmpty(
					Mono.defer(() -> Mono.error(new ResourceNotFoundException())
				));
	}
}
