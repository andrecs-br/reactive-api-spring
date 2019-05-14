package acs.springframework.reactiveapi.bootstrap;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import acs.springframework.reactiveapi.domain.Category;
import acs.springframework.reactiveapi.domain.Vendor;
import acs.springframework.reactiveapi.repositories.CategoryRepository;
import acs.springframework.reactiveapi.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_={@Autowired})
public class LoadData implements CommandLineRunner {

	@Autowired
	VendorRepository vendorRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public void run(String... args) throws Exception {
		long count = categoryRepository.count().block();
		
		log.info("Category registered: {}", count);
		
		if (count == 0) {
			log.info("Will register vendors and categories");

			loadCategories();
			loadVendors();
			
	        log.info("Data Category Loaded = {}", categoryRepository.count().block());
	        log.info("Data Vendor Loaded = {}", vendorRepository.count().block());
		}
		
	}
	
	private void loadCategories() {
		Category fruits = Category
				.builder()
				.description("Fuits")
				.build();

        Category dried = Category
        		.builder()
        		.description("Dried")
        		.build();;

        Category fresh = Category
        		.builder()
        		.description("Fresh")
        		.build();

        Category exotic = Category
        		.builder()
        		.description("Exotic")
        		.build();

        Category nuts = Category
        		.builder()
        		.description("Nuts")
        		.build();

        categoryRepository.save(fruits).block();
        categoryRepository.save(dried).block();
        categoryRepository.save(fresh).block();
        categoryRepository.save(exotic).block();
        categoryRepository.save(nuts).block();
	}

	private void loadVendors() {
		
		Vendor vendor = Vendor
				.builder()
				.id(UUID.randomUUID().toString())
				.firstName("Andre")
				.lastName("Silva")
				.build();

		Vendor vendor2 = Vendor
				.builder()
				.id(UUID.randomUUID().toString())
				.firstName("Leandro")
				.lastName("Silva")
				.build();

        vendorRepository.save(vendor).block();
        vendorRepository.save(vendor2).block();
	}

}
