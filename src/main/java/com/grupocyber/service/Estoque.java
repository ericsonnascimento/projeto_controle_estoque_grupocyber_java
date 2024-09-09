package main.java.com.grupocyber.service;

import main.java.com.grupocyber.dao.ProdutoDAO;
import main.java.com.grupocyber.model.Produto;
import java.util.List;

public class Estoque {
    private ProdutoDAO produtoDAO;

    public Estoque(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void adicionarProduto(Produto produto) {
        produtoDAO.adicionarProduto(produto);
    }

    public void atualizarProduto(Produto produto) {
        produtoDAO.atualizarProduto(produto);
    }

    public void deletarProduto(int id) {
        produtoDAO.deletarProduto(id);
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.listarProdutos();
    }

    public void venderProduto(int id, int quantidadeVendida) {
        Produto produto = produtoDAO.obterProdutoPorId(id);
        if (produto != null) {
            if (produto.getQuantidade() >= quantidadeVendida) {
                produto.setQuantidade(produto.getQuantidade() - quantidadeVendida);
                produtoDAO.atualizarProduto(produto);
                System.out.println("Venda realizada com sucesso!");
            } else {
                System.out.println("Estoque insuficiente!");
            }
        } else {
            System.out.println("Produto não encontrado!");
        }
    }
}