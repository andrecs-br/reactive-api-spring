package acs.springframework.reactiveapi.api.v1.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import acs.springframework.reactiveapi.domain.Vendor;
import acs.springframework.reactiveapi.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class VendorControllerTest {

    WebTestClient webTestClient;
    @Mock
    VendorRepository vendorRepository;
    @InjectMocks
    VendorController vendorController;


    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Andre").lastName("Silva").build(),
                        Vendor.builder().firstName("Luciana").lastName("Siqueira").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(vendorRepository.findById("someid"))
                .willReturn(Mono.just(Vendor.builder().firstName("Andre").lastName("Silva").build()));

        webTestClient.get()
                .uri("/api/v1/vendors/someid")
                .exchange()
                .expectBody(Vendor.class);

    }

    @Test
    public void createVendor() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorStream = Mono.just(Vendor.builder().firstName("Andre").lastName("Silva").build());
        
        webTestClient.post()
                .uri("/api/v1/vendors/")
                .body(vendorStream, Vendor.class)
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    public void createVendors() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
        .willReturn(Flux.just(Vendor.builder().build(), Vendor.builder().build()));

        Flux<Vendor> vendorStream = Flux.just(Vendor.builder().firstName("Andre").lastName("Silva").build(), 
        		Vendor.builder().firstName("Luciana").lastName("Siqueira").build());

		webTestClient.post()
		        .uri("/api/v1/vendors/")
		        .body(vendorStream, Vendor.class)
		        .exchange()
		        .expectStatus().isCreated();

    }

    @Test
    public void updateVendor() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendor = Mono.just(Vendor.builder().firstName("Andre").lastName("Silva").build());
        
        webTestClient.put()
                .uri("/api/v1/vendors/1")
                .body(vendor, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class);

    }
    
    @Test
    public void patchVendor() {
    	BDDMockito.given(vendorRepository.findById(anyString()))
        		.willReturn(Mono.just(Vendor.builder().id("1").build()));
    	
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendor = Mono.just(Vendor.builder().firstName("Andre2").build());
        
        webTestClient.patch()
                .uri("/api/v1/vendors/1")
                .body(vendor, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class);

    }

}
