package main.java.com.grupocyber.dao;

import main.java.com.grupocyber.model.Produto;

import java.util.List;

public interface ProdutoDAO {
    void adicionarProduto(Produto produto);
    void atualizarProduto(Produto produto);
    void deletarProduto(int id);
    List<Produto> listarProdutos();
    Produto obterProdutoPorId(int id);

    // Certifique-se de que a assinatura do método venderProduto está assim
    void venderProduto(int id, int quantidadeVendida);
}