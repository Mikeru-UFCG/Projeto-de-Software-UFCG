package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

import jakarta.servlet.ServletException;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Produtos")
public class ProdutoV1ControllerTests {
    @Autowired
    MockMvc driver;

    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Produto produto;

    @BeforeEach
    void setup() {
        produto = produtoRepository.find(10L);
    }

    @AfterEach
    void tearDown() {
        produto = null;
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class ProdutoValidacaoCamposObrigatorios {

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomeDoProdutoValido() throws Exception {
            // Arrange
            produto.setNome("Produto Dez Alterado");

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals(resultado.getNome(), "Produto Dez Alterado");
            
        }
        
        @Test
        @DisplayName("Quando alteramos o nome do produto com dados inválidos")
        void quandoAlteramosNomeDoProdutoInvalido() throws Exception {
        	// Arrange:
        	produto.setNome("");
        	
        	// Act:
        	ServletException thrown = assertThrows(
        			ServletException.class,
        			() -> driver.perform(put("/v1/produtos/" + produto.getId())
        					.contentType(MediaType.APPLICATION_JSON)
        					.content(objectMapper.writeValueAsString(produto)))
        			.andExpect(status().isOk())
        			.andDo(print())
        			.andReturn().getResponse().getContentAsString());
        	
        	// Assert:
        	String expected = "Request processing failed: java.lang.RuntimeException: Nome inválido!";
        	String actual = thrown.getMessage();
        	assertEquals(expected, actual);
        	
        }

    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da regra sobre o preço")
    class ProdutoValidacaoRegrasDoPreco {
        // Implementar os testes aqui
    	
    	@Test
        @DisplayName("Quando alteramos o preço do produto com dados válidos")
        void quandoAlteramosPrecoDoProdutoValido() throws Exception {
    		// Arrange
            produto.setPreco(225.50);

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals(resultado.getPreco(), 225.50);
    	}
    	
    	@Test
        @DisplayName("Quando alteramos o preço do produto com dados inválidos")
        void quandoAlteramosPrecoDoProdutoInvalido() throws Exception {
    		// Arrange:
        	produto.setPreco(-100.00);
        	
        	// Act:
        	ServletException thrown = assertThrows(
        			ServletException.class,
        			() -> driver.perform(put("/v1/produtos/" + produto.getId())
        					.contentType(MediaType.APPLICATION_JSON)
        					.content(objectMapper.writeValueAsString(produto)))
        			.andExpect(status().isOk())
        			.andDo(print())
        			.andReturn().getResponse().getContentAsString());
        	
        	// Assert:
        	String expected = "Request processing failed: java.lang.RuntimeException: Preço inválido!";
        	String actual = thrown.getMessage();
        	assertEquals(expected, actual);
    	}
    	
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação da validação do código de barras")
    class ProdutoValidacaoCodigoDeBarras {
        // Implementar os testes aqui
    	
    	@Test
        @DisplayName("Quando alteramos o código de barras do produto com dados válidos")
        void quandoAlteramosCodigoBarraDoProdutoValido() throws Exception {
    		// Arrange
            produto.setCodigoBarra("1119137550604");

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals(resultado.getCodigoBarra(), "1119137550604");
    	}
    	
    	@Test
        @DisplayName("Quando alteramos o código de barras do produto com dados inválidos")
        void quandoAlteramosCodigoBarraDoProdutoInvalido() throws Exception {
    		// Arrange:
        	produto.setCodigoBarra("1119137550604");
        	
        	// Act:
        	ServletException thrown = assertThrows(
        			ServletException.class,
        			() -> driver.perform(put("/v1/produtos/" + produto.getId())
        					.contentType(MediaType.APPLICATION_JSON)
        					.content(objectMapper.writeValueAsString(produto)))
        			.andExpect(status().isOk())
        			.andDo(print())
        			.andReturn().getResponse().getContentAsString());
        	
        	// Assert:
        	String expected = "Request processing failed: java.lang.RuntimeException: Código Barra inválido!";
        	String actual = thrown.getMessage();
        	assertEquals(expected, actual);
    	}
    }
    
    @Nested
    @DisplayName("Conjunto de casos de verificação da validação do fabricante")
    class ProdutoValidacaoFabricante {
        // Implementar os testes aqui
    	
    	@Test
        @DisplayName("Quando alteramos o fabricante do produto com dados válidos")
        void quandoAlteramosCodigoBarraDoProdutoValido() throws Exception {
    		// Arrange
            produto.setFabricante("Empresa Dez Alterado");

            // Act
            String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produto)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertEquals(resultado.getFabricante(), "Empresa Dez Alterado");
    	}
    	
    	@Test
        @DisplayName("Quando alteramos o fabricante do produto com dados inválidos")
        void quandoAlteramosCodigoBarraDoProdutoInvalido() throws Exception {
    		// Arrange:
        	produto.setFabricante("");
        	
        	// Act:
        	ServletException thrown = assertThrows(
        			ServletException.class,
        			() -> driver.perform(put("/v1/produtos/" + produto.getId())
        					.contentType(MediaType.APPLICATION_JSON)
        					.content(objectMapper.writeValueAsString(produto)))
        			.andExpect(status().isOk())
        			.andDo(print())
        			.andReturn().getResponse().getContentAsString());
        	
        	// Assert:
        	String expected = "Request processing failed: java.lang.RuntimeException: Fabricante inválido!";
        	String actual = thrown.getMessage();
        	assertEquals(expected, actual);
    	}
    }

}
