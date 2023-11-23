package br.com.projeto.redesocial.backend;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int COD_USUARIO;
    private String NOM_USUARIO;
    private String DES_EMAIL;
    private String DES_SENHA;

    public Usuario(int COD_USUARIO, String NOM_USUARIO, String DES_EMAIL, String DES_SENHA) {
        this.COD_USUARIO = COD_USUARIO;
        this.NOM_USUARIO = NOM_USUARIO;
        this.DES_EMAIL = DES_EMAIL;
        this.DES_SENHA = DES_SENHA;
    }

    public Usuario(int COD_USUARIO) {
        this.COD_USUARIO = COD_USUARIO;
    }

    public Usuario() {

    }

    public Usuario(String nomeUsuario) {
    }

    public Usuario(String NOM_USUARIO, String DES_SENHA) {
    }

    public int getCOD_USUARIO() {
        return COD_USUARIO;
    }

    public void setCOD_USUARIO(int COD_USUARIO) {
        this.COD_USUARIO = COD_USUARIO;
    }

    public String getNOM_USUARIO() {
        return this.NOM_USUARIO;
    }

    public void setNOM_USUARIO(String NOM_USUARIO) {
        this.NOM_USUARIO = NOM_USUARIO;
    }

    @Override
    public String toString() {
        return COD_USUARIO + " - " + NOM_USUARIO;
    }

    public String getDES_EMAIL() {
        return DES_EMAIL;
    }

    public void setDES_EMAIL(String DES_EMAIL) {
        this.DES_EMAIL = DES_EMAIL;
    }

    public String getDES_SENHA() {
        return DES_SENHA;
    }

    public void setDES_SENHA(String DES_SENHA) {
        this.DES_SENHA = DES_SENHA;
    }

    //FAZER LOGIN
    public boolean fazerLogin(String email, String senha) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();

        String query = "SELECT * FROM usuario WHERE DES_EMAIL = ? AND DES_SENHA = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, senha);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                // Se encontrarmos um usuário com o e-mail e senha fornecidos, definimos seus atributos e retornamos true
                this.COD_USUARIO = resultado.getInt("COD_USUARIO");
                this.NOM_USUARIO = resultado.getString("NOM_USUARIO");
                this.DES_EMAIL = resultado.getString("DES_EMAIL");
                this.DES_SENHA = resultado.getString("DES_SENHA");
                return true;
            } else {
                // Se não encontrarmos um usuário com o e-mail e senha fornecidos, retornamos false
                return false;
            }
        } catch (SQLException e) {
            // Em caso de erro ao executar a consulta SQL, imprimimos a mensagem de erro e retornamos false
            System.out.println("Erro ao fazer login: " + e.getMessage());
            return false;
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }

    //FAZER REGISTRO
    public boolean fazerRegistro(String DES_EMAIL, String DES_SENHA, String NOM_USUARIO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();

        String query = "INSERT INTO usuario (NOM_USUARIO, DES_EMAIL, DES_SENHA) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1, NOM_USUARIO);
            statement.setString(2, DES_EMAIL);
            statement.setString(3, DES_SENHA);
            int resultado = statement.executeUpdate();

            return resultado > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao fazer registro: " + e.getMessage());
            return false;
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }

    //ADICIONAR AMIGO
    public void adicionarAmigo(int COD_AMIGO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();

        String query = "INSERT INTO amigo (COD_USUARIO, COD_AMIGO) VALUES (?, ?)";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, this.COD_USUARIO);
            statement.setInt(2, COD_AMIGO);
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Amigo adicionado com sucesso!");
            } else {
                System.out.println("Falha ao adicionar amigo.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar amigo: " + e.getMessage());
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }

    public Usuario getUsuarioByCod(int codUsuario) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();
        Usuario usuario = null;

        String query = "SELECT * FROM usuario WHERE COD_USUARIO = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, codUsuario);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                usuario = new Usuario(
                        resultado.getInt("COD_USUARIO"),
                        resultado.getString("NOM_USUARIO"),
                        resultado.getString("DES_EMAIL"),
                        resultado.getString("DES_SENHA")
                );
            }
        } catch (SQLException e) {
            // Tratamento de erro
            e.printStackTrace();
        } finally {
            bancoDeDados.desconectar(conexao);
        }

        return usuario;
    }



    //LISTAR AMIGO
    public List<Usuario> listarAmigos() {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();
        List<Usuario> amigos = new ArrayList<>();

        String query = "SELECT u.COD_USUARIO, u.NOM_USUARIO FROM AMIGO a JOIN USUARIO u ON a.COD_AMIGO = u.COD_USUARIO WHERE a.COD_USUARIO = ?";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, this.COD_USUARIO);
            ResultSet resultado = statement.executeQuery();


            while (resultado.next()) {
                int codAmigo = resultado.getInt("COD_USUARIO");
                Usuario amigo = getUsuarioByCod(codAmigo);
                amigos.add(amigo);
            }


        } catch (SQLException e) {
            System.out.println("Erro ao listar amigos: " + e.getMessage());
        } finally {
            bancoDeDados.desconectar(conexao);
        }
        return amigos;
    }

    public boolean excluirAmigo(int COD_AMIGO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();
        boolean sucesso = false;

        // Primeiro, verificamos se a amizade existe
        if (isAmigo(COD_AMIGO)) {
            String query = "DELETE FROM amigo WHERE (COD_USUARIO = ? AND COD_AMIGO = ?) OR (COD_USUARIO = ? AND COD_AMIGO = ?)";
            try {
                PreparedStatement statement = conexao.prepareStatement(query);
                statement.setInt(1, this.COD_USUARIO);
                statement.setInt(2, COD_AMIGO);
                statement.setInt(3, COD_AMIGO);
                statement.setInt(4, this.COD_USUARIO);

                int linhasAfetadas = statement.executeUpdate();

                if (linhasAfetadas > 0) {
                    sucesso = true;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao excluir amigo: " + e.getMessage());
            } finally {
                bancoDeDados.desconectar(conexao);
            }
        }
        return sucesso;
    }

    private boolean isAmigo(int COD_AMIGO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();
        boolean saoAmigos = false;

        String query = "SELECT COUNT(1) FROM amigo WHERE (COD_USUARIO = ? AND COD_AMIGO = ?) OR (COD_USUARIO = ? AND COD_AMIGO = ?)";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, this.COD_USUARIO);
            statement.setInt(2, COD_AMIGO);
            statement.setInt(3, COD_AMIGO);
            statement.setInt(4, this.COD_USUARIO);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next() && resultado.getInt(1) > 0) {
                saoAmigos = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar amizade: " + e.getMessage());
        } finally {
            bancoDeDados.desconectar(conexao);
        }

        return saoAmigos;
    }

}