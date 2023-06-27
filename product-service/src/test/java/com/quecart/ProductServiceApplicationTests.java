package com.quecart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quecart.dto.request.ProductRequest;
import com.quecart.dto.response.ProductResponse;
import com.quecart.repository.ProductRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ProductRepository productRepo;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	@Order(1)
	public void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		mockMvc.perform(MockMvcRequestBuilders.post("/quecart/products/add").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(productRequest))).andExpect(status().isCreated());
		assertEquals(1, productRepo.findAll().size());
	}

	@Test
	@Order(2)
	public void shouldReturnProductById() throws Exception {
		MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/quecart/products/get/1"))
				.andExpect(status().isOk()).andReturn();

		String responseBody = response.getResponse().getContentAsString();
		ProductResponse productResponse = mapper.readValue(responseBody, ProductResponse.class);

		assertEquals("Mouse", productResponse.getName());
	}

	@Test
	@Order(3)
	public void shouldReturnProductList() throws Exception {
		MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/quecart/products/get"))
				.andExpect(status().isOk()).andReturn();

		String responseBody = response.getResponse().getContentAsString();
		List<ProductResponse> productResponses = mapper.readValue(responseBody,
				new TypeReference<List<ProductResponse>>() {
				});
		assertEquals(1, productResponses.size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder().productId(Long.valueOf(1)).name("Mouse").description("Smooth and Light weight")
				.price(BigDecimal.valueOf(15)).build();
	}
}
