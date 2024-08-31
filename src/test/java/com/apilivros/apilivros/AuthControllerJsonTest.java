package com.apilivros.apilivros;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.apilivros.Dto.AccountCredentialsDTO;
import com.apilivros.Dto.TokenDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerJsonTest {
    private static TokenDTO tokenDTO;

    @Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO user = new AccountCredentialsDTO("{username}", "{password}");
		
		tokenDTO = given()
				.basePath("/auth/signin")
					.port(8080)
					.contentType("application/json")
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenDTO.class);
		
		assertNotNull(tokenDTO.getAcessToken());
        assertNotNull(tokenDTO.getRefreshToken());
	}
	
    @Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsDTO user = new AccountCredentialsDTO("bombas", "admin123");

		var newtokenDTO = given()
				.basePath("/auth/refresh")
					.port(8080)
					.contentType("application/json")
                        .pathParam("username", tokenDTO.getUsername())
                        .header("Authorization", "Bearer " + tokenDTO.getRefreshToken())
					.when()
				.put("{username}")
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenDTO.class);
		
		assertNotNull(newtokenDTO.getAcessToken());
        assertNotNull(newtokenDTO.getRefreshToken());
	}
    
}
