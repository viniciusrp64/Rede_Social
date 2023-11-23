package br.com.projeto.redesocial.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {
    private static final String URL = "jdbc:mysql://localhost:3306/RedeSocial";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456";

    public Connection conectar() {
        try {
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com o banco de dados estabelecida.");
            return conexao;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    public void desconectar(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão com o banco de dados encerrada.");
            } catch (SQLException e) {
                System.out.println("Erro ao encerrar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}