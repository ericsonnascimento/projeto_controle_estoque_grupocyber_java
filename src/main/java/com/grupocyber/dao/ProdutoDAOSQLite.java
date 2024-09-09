package main.java.com.grupocyber.dao;

import main.java.com.grupocyber.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOSQLite implements ProdutoDAO {

    private Connection conn;

    // Construtor: inicializa a conexão com o banco de dados e garante a criação da tabela
    public ProdutoDAOSQLite() {
        try {
            // Inicializa a conexão com o banco de dados SQLite
            String url = "jdbc:sqlite:estoque.db"; // Caminho do banco de dados
            this.conn = DriverManager.getConnection(url);

            // Código para criar a tabela 'produtos' se ela ainda não existir
            String createTableSQL = "CREATE TABLE IF NOT EXISTS produtos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "codigo TEXT NOT NULL," +
                    "preco REAL NOT NULL," +
                    "quantidade INTEGER NOT NULL)";

            // Executa o comando SQL para criar a tabela
            conn.createStatement().execute(createTableSQL);
            System.out.println("Tabela 'produtos' verificada/criada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ou criar tabela no banco de dados: " + e.getMessage());
        }
    }

    // Método para adicionar um novo produto ao banco de dados
    @Override
    public void adicionarProduto(Produto produto) {
        String sql = "INSERT INTO produtos(nome, codigo, preco, quantidade) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getCodigo());
            pstmt.setDouble(3, produto.getPreco());
            pstmt.setInt(4, produto.getQuantidade());
            pstmt.executeUpdate();
            System.out.println("Produto adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    // Método para atualizar um produto existente
    @Override
    public void atualizarProduto(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, codigo = ?, preco = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produto.getNome());
            pstmt.setString(2, produto.getCodigo());
            pstmt.setDouble(3, produto.getPreco());
            pstmt.setInt(4, produto.getQuantidade());
            pstmt.setInt(5, produto.getId());
            pstmt.executeUpdate();
            System.out.println("Produto atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    // Método para deletar um produto pelo ID
    @Override
    public void deletarProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Produto deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }

    // Método para listar todos os produtos
    @Override
    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                );
                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    // Método para vender um produto (subtrair quantidade)
    @Override
    public void venderProduto(int id, int quantidadeVendida) {
        String sql = "UPDATE produtos SET quantidade = quantidade - ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantidadeVendida);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Venda realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao vender produto: " + e.getMessage());
        }
    }

    // Método para obter um produto pelo ID (obrigatório para seguir a interface ProdutoDAO)
    @Override
    public Produto obterProdutoPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter produto: " + e.getMessage());
        }
        return null; // Retorna null se o produto não for encontrado
    }
}