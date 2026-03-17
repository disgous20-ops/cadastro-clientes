package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Conexao instancia;
    private static final String DRIVER = "org.sqlite.JDBC";
    private static Connection conexao;

    private Conexao() {}

    public static Conexao getInstacia() {
        if (instancia == null) {
            instancia = new Conexao();
        }
        return instancia;
    }

    public Connection abrirConexao() {

        try {
            Class.forName(DRIVER);

            // 🔥 Caminho dinâmico (mesma pasta do .jar)
            String caminho = new File("").getAbsolutePath() + File.separator + "bdclientes.db";

            String url = "jdbc:sqlite:" + caminho;

            conexao = DriverManager.getConnection(url);
            conexao.setAutoCommit(false);

            System.out.println("Banco em: " + caminho);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao conectar com o banco: " + e.getMessage());
        }

        return conexao;
    }

    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao: " + e.getMessage());
        } finally {
            conexao = null;
        }
    }
}