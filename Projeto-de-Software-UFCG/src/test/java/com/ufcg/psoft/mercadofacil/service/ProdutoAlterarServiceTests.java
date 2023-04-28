package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Testes do Serviço de alteração do produto")
public class ProdutoAlterarServiceTests {

    @Autowired
    ProdutoAlterarService driver;

    @MockBean
    ProdutoRepository<Produto, Long> produtoRepository;

    Produto produto;

    @BeforeEach
    void setup() {
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        produto = produtoRepository.find(10L);
    }

    @Test
    @DisplayName("Quando um novo nome válido for fornecido para o produto")
    void quandoNovoNomeValido() {
        // Arrange
        produto.setNome("Produto Dez Atualizado");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez Atualizado")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );

        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals("Produto Dez Atualizado", resultado.getNome());
    }

    @Test
    @DisplayName("Quando altero o nome do produto com dados válidos")
    void alterarNomeDoProduto() {
        /* AAA Pattern */
        //Arrange
        produto.setNome("Nome Produto Alterado");
        //Act
        Produto resultado = driver.alterar(produto);
        //Assert
        assertEquals("Nome Produto Alterado", resultado.getNome());
    }

    @Test
    @DisplayName("Quando o preço é menor ou igual a zero")
    void precoMenorIgualAZero() {
        //Arrange
        produto.setPreco(0.0);
        //Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        //Assert
        assertEquals("Preco invalido!", thrown.getMessage());
    }

    /**
     * Atributos do produto: Id, Nome, Preço, Código de Barras e Fabricante.
     */

    @Test
    @DisplayName("Quando um novo Id válido for fornecido ao produto")
    void idValido() {
        // Arrange
        produto.setId(7L);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(7L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez Atualizado")
                        .preco(450.00)
                        .build()
                );

        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals(7L, resultado.getId());
    }

    @Test
    @DisplayName("Quando altero o nome do produto com dados inválidos")
    void alterarInvalidoNomeDoProduto() {
        //Arrange
        produto.setNome("");
        //Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        //Assert
        assertEquals("Nome invalido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o preço é maior que zero")
    void precoMaiorQueZero() {
        // Arrange
        produto.setPreco(225.50);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez Atualizado")
                        .fabricante("Empresa Dez")
                        .preco(225.50)
                        .build()
                );

        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals(225.50, resultado.getPreco());
    }

    @Test
    @DisplayName("Quando o código de barras é válido")
    void codigoBarraValido() {
    	// Arrange
        produto.setCodigoBarra("7899137550604");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137550604")
                        .nome("Produto Dez Atualizado")
                        .fabricante("Empresa Dez")
                        .preco(225.50)
                        .build()
                );

        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals("7899137550604", resultado.getCodigoBarra());
    }

    @Test
    @DisplayName("Quando o país no código de barras é inválido")
    void codigoBarraInvalidoPais() {
    	// Arrange
        produto.setCodigoBarra("1119137550604");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        //Assert
        assertEquals("País invalido!", thrown.getMessage());
        
    }

    @Test
    @DisplayName("Quando a empresa no código de barras é inválido")
    void codigoBarraInvalidoEmpresa() {
    	// Arrange
        produto.setCodigoBarra("7898137500104");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        //Assert
        assertEquals("Empresa invalido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando o dígito verificador no código de barras é inválido")
    void codigoBarraIvnalidoDigitoVerificador() {
    	// Arrange
        produto.setCodigoBarra("7898137545674");
        // Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        //Assert
        assertEquals("Produto invalido!", thrown.getMessage());
    }

    @Test
    @DisplayName("Quando um novo fabricante válido for fornecido para o produto")
    void quandoNovoFabricanteValido() {
        // Arrange
        produto.setFabricante("Empresa Dez Atualizado");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez Atualizado")
                        .preco(450.00)
                        .build()
                );

        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals("Empresa Dez Atualizado", resultado.getFabricante());
    }

    @Test
    @DisplayName("Quando altero o fabricante do produto com dados válidos")
    void alterarFabricanteDoProduto() {
        /* AAA Pattern */
        //Arrange
        produto.setFabricante("Empresa Dez Alterado");
        //Act
        Produto resultado = driver.alterar(produto);
        //Assert
        assertEquals("Empresa Dez Alterado", resultado.getFabricante());
    }
    
    @Test
    @DisplayName("Quando altero o fabricante do produto com dados inválidos")
    void alterarInvalidoFabricanteDoProduto() {
        //Arrange
        produto.setFabricante("");
        //Act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> driver.alterar(produto)
        );
        //Assert
        assertEquals("Fabricante invalido!", thrown.getMessage());
    }
    
}
