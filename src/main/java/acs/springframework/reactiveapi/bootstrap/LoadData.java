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
		Category fruits = new Category();
        fruits.setDescription("Fruits");

        Category dried = new Category();
        dried.setDescription("Dried");

        Category fresh = new Category();
        fresh.setDescription("Fresh");

        Category exotic = new Category();
        exotic.setDescription("Exotic");

        Category nuts = new Category();
        nuts.setDescription("Nuts");

        categoryRepository.save(fruits).block();
        categoryRepository.save(dried).block();
        categoryRepository.save(fresh).block();
        categoryRepository.save(exotic).block();
        categoryRepository.save(nuts).block();
	}

	private void loadVendors() {
		Vendor vendor = new Vendor();
        vendor.setId(UUID.randomUUID().toString());
        vendor.setFirstName("Andre");
        vendor.setLastName("Silva");

        Vendor vendor2 = new Vendor();
        vendor2.setId(UUID.randomUUID().toString());
        vendor2.setFirstName("Leandro");
        vendor2.setLastName("Silva");
        
        vendorRepository.save(vendor).block();
        vendorRepository.save(vendor2).block();
	}

}
