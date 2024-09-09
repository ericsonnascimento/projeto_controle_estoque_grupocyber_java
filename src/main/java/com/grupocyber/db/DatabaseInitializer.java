package main.java.com.grupocyber.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:estoque.db";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Criação da tabela de produtos
            String createTableSQL = "CREATE TABLE IF NOT EXISTS produtos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "codigo INTEGER NOT NULL UNIQUE," +
                    "preco REAL NOT NULL," +
                    "quantidade INTEGER NOT NULL" +
                    ");";
            stmt.execute(createTableSQL);

            System.out.println("Banco de dados e tabela criados com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    }
}