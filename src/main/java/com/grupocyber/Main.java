package main.java.com.grupocyber;

import main.java.com.grupocyber.dao.ProdutoDAO;
import main.java.com.grupocyber.dao.ProdutoDAOSQLite;
import main.java.com.grupocyber.model.Produto;
import main.java.com.grupocyber.service.Estoque;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:sqlite:estoque.db"; // Local do banco de dados

    public static void main(String[] args) {
        // Inicializa o banco de dados e a tabela de produtos
        initializeDatabase();

        ProdutoDAO produtoDAO = new ProdutoDAOSQLite();
        Estoque estoque = new Estoque(produtoDAO);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println("CONTROLE DE ESTOQUE GRUPOCYBER");
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Atualizar Produto");
            System.out.println("3. Deletar Produto");
            System.out.println("4. Listar Produtos");
            System.out.println("5. Vender Produto");
            System.out.println("6. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Nome: ");
                String nome = scanner.next();
                System.out.print("Código: ");
                String codigo = scanner.next();
                System.out.print("Preço: ");
                double preco = scanner.nextDouble();
                System.out.print("Quantidade: ");
                int quantidade = scanner.nextInt();

                Produto produto = new Produto(0, nome, codigo, preco, quantidade);
                estoque.adicionarProduto(produto);
                System.out.println("Produto adicionado com sucesso!");

            } else if (opcao == 2) {
                System.out.print("ID do Produto: ");
                int id = scanner.nextInt();
                System.out.print("Novo Nome: ");
                String nome = scanner.next();
                System.out.print("Novo Código: ");
                String codigo = scanner.next();
                System.out.print("Novo Preço: ");
                double preco = scanner.nextDouble();
                System.out.print("Nova Quantidade: ");
                int quantidade = scanner.nextInt();

                Produto produto = new Produto(id, nome, codigo, preco, quantidade);
                estoque.atualizarProduto(produto);
                System.out.println("Produto atualizado com sucesso!");

            } else if (opcao == 3) {
                System.out.print("ID do Produto: ");
                int id = scanner.nextInt();
                estoque.deletarProduto(id);
                System.out.println("Produto deletado com sucesso!");

            } else if (opcao == 4) {
                List<Produto> produtos = estoque.listarProdutos();
                for (Produto produto : produtos) {
                    System.out.println("ID: " + produto.getId());
                    System.out.println("Nome: " + produto.getNome());
                    System.out.println("Código: " + produto.getCodigo());
                    System.out.println("Preço: " + produto.getPreco());
                    System.out.println("Quantidade: " + produto.getQuantidade());
                    System.out.println("------------------------");
                }

            } else if (opcao == 5) {
                System.out.print("ID do Produto: ");
                int id = scanner.nextInt();
                System.out.print("Quantidade Vendida: ");
                int quantidadeVendida = scanner.nextInt();
                estoque.venderProduto(id, quantidadeVendida);

            } else if (opcao == 6) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }

    /**
     * Método para inicializar o banco de dados e criar a tabela de produtos.
     */
    private static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS produtos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "codigo TEXT NOT NULL UNIQUE," +
                    "preco REAL NOT NULL," +
                    "quantidade INTEGER NOT NULL" +
                    ");";

            stmt.execute(createTableSQL);
            System.out.println("Banco de dados e tabela de produtos inicializados com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
}