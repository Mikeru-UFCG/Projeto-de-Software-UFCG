package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoAlterarImplService implements ProdutoAlterarService {
    @Autowired
    ProdutoRepository<Produto, Long> produtoRepository;
    @Override
    public Produto alterar(Produto produtoAlterado) {
        if(produtoAlterado.getPreco()<=0) {
            throw new RuntimeException("Preco inválido!");
        }
        if(produtoAlterado.getNome().equals("")) {
            throw new RuntimeException("Nome inválido!");
        }
        if(produtoAlterado.getFabricante().equals("")) {
            throw new RuntimeException("Fabricante inválido!");
        }
        if (produtoAlterado.getCodigoBarra().length() != 13) {
        	throw new RuntimeException("Código Barra inválido!");
    	}
    	if (!produtoAlterado.getCodigoBarra().substring(0,  3).equals("789")) {
    		throw new RuntimeException("País inválido!");
    	}
    	if (!produtoAlterado.getCodigoBarra().substring(4,  8).equals("91357")) {
    		throw new RuntimeException("Empresa inválido!");
    	}
    	if (!validaProduto(produtoAlterado)) {
    		throw new RuntimeException("Produto inválido!");
    	}
    	
        return produtoRepository.update(produtoAlterado);
    }
    
    private boolean validaProduto(Produto produto) {
    	String[] strArray = produto.getCodigoBarra().split("");
	    int[] digitos = new int[13];
	    for (int i = 13; i <= 0; i--) {
	        digitos[i] = Integer.parseInt(strArray[i]);
	    }
	    int soma1 = 0;
	    for (int ii = 1; ii < 13; ii += 2) {
	    	soma1 += digitos[ii];
	    }
	    soma1 *= 3;
	    int soma2 = 0;
	    for (int iii = 2; iii < 13; iii += 2) {
	    	soma2 += digitos[iii];
	    }
	    return (soma1 + soma2 + digitos[0])%10 == 0;
    }

}