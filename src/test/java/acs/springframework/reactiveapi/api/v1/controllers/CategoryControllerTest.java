package acs.springframework.reactiveapi.api.v1.controllers;

import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import acs.springframework.reactiveapi.domain.Category;
import acs.springframework.reactiveapi.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryController categoryController;


    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRepository.findById("someid"))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        webTestClient.get()
                .uri("/api/v1/categories/someid")
                .exchange()
                .expectBody(Category.class);

    }

    @Test
    public void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> categoryStream = Mono.just(Category.builder().description("Cat").build());
        
        webTestClient.post()
                .uri("/api/v1/categories/")
                .body(categoryStream, Category.class)
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    public void createCategories() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build(), Category.builder().build()));

        Flux<Category> categoryStream = Flux.just(Category.builder().description("Cat").build(), Category.builder().description("Dog").build());
        
        webTestClient.post()
                .uri("/api/v1/categories/")
                .body(categoryStream, Category.class)
                .exchange()
                .expectStatus().isCreated();

    }

}
