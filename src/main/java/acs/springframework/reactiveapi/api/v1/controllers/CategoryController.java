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

import acs.springframework.reactiveapi.domain.Category;
import acs.springframework.reactiveapi.exceptions.ResourceNotFoundException;
import acs.springframework.reactiveapi.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryController(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@GetMapping()
	public Flux<Category> listCategories() {
		return categoryRepository
				.findAll()
				.switchIfEmpty(
						Flux.defer(() -> Flux.error(new ResourceNotFoundException())
				));
	}

	@GetMapping("/{idCategory}")
	public Mono<Category> getCategory(@PathVariable String idCategory) {
		return categoryRepository
				.findById(idCategory)
				.switchIfEmpty(
						Mono.defer(() -> Mono.error(new ResourceNotFoundException())
				));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Void> createCategories(@RequestBody Publisher<Category> categoryStream) {
		return categoryRepository.saveAll(categoryStream).then();
	}
	
	@PutMapping("/{idCategory}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Category> updateCategory(@PathVariable String idCategory, @RequestBody Category category) {
		category.setId(idCategory);
		return categoryRepository.save(category);
	}

	@PatchMapping("/{idCategory}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Category> patchCategory(@PathVariable String idCategory, @RequestBody Category category) {
		return categoryRepository
				.findById(idCategory)
				.switchIfEmpty(
						Mono.defer(() -> Mono.error(new ResourceNotFoundException())
				))
				.flatMap(categorySaved -> {
					if (category.getDescription() != null) {
						categorySaved.setDescription(category.getDescription());
					}
					return categoryRepository.save(categorySaved);
				});

	}

}
