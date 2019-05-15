package acs.springframework.reactiveapi.api.v1.controllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Void> createVendors(@RequestBody Publisher<Vendor> vendorStream) {
		return vendorRepository.saveAll(vendorStream).then();
	}
	
	@PutMapping("/{idVendor}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Vendor> updateVendor(@PathVariable String idVendor, @RequestBody Vendor vendor) {
		vendor.setId(idVendor);
		return vendorRepository.save(vendor);
	}

	@PatchMapping("/{idVendor}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Vendor> patchVendor(@PathVariable String idVendor, @RequestBody Vendor vendor) {
		return vendorRepository
				.findById(idVendor)
				.switchIfEmpty(
						Mono.defer(() -> Mono.error(new ResourceNotFoundException())
				))
				.flatMap(vendorSaved -> {
					if (vendor.getFirstName() != null) {
						vendorSaved.setFirstName(vendor.getFirstName());
					}
					if (vendor.getLastName() != null) {
						vendorSaved.setLastName(vendor.getLastName());
					}
					return vendorRepository.save(vendorSaved);
				});
	}
	
}
