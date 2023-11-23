package br.com.projeto.redesocial;

import br.com.projeto.redesocial.backend.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuController {
    private Usuario getUsuarioLogado;

    private Usuario usuarioLogado;

    @FXML
    private ListView<Usuario> listViewAmigos;

    private ObservableList<Usuario> amigosObservaveis;

    @FXML
    private TextField messageTextField; // Campo de texto para escrever a mensagem

    @FXML
    private Button sendButton; // Botão para enviar a mensagem

    @FXML
    private Button adicionarAmigoButton;

    @FXML
    private Button excluirAmigoButton;

    @FXML
    private Button SairButton;

    @FXML
    private TextField codigoAmigoTextFIeld;

    private Usuario amigoselecionado;

    @FXML
    private ListView<Mensagem> listViewMensagens; // ListView para exibir as mensagens




    int codigoAmigo;
    int codigoUsuarioLogado;


    // Inicialização do controlador.
    public void initialize() {
        usuarioLogado = SessaoUsuario.getUsuarioLogado();
        System.out.println(usuarioLogado.getCOD_USUARIO());

        amigosObservaveis = FXCollections.observableArrayList();
        listViewAmigos.setItems(amigosObservaveis);

        carregarAmigos();
        listViewAmigos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                amigoselecionado = newValue;
                System.out.println("Amigo selecionado: " + newValue.getNOM_USUARIO());

                codigoAmigo = amigoselecionado.getCOD_USUARIO();
                codigoUsuarioLogado = usuarioLogado.getCOD_USUARIO();

                mostrarChatComAmigo();
            }
        });

        listViewMensagens.setCellFactory(cell -> new ListCell<Mensagem>() {
            @Override
            protected void updateItem(Mensagem mensagem, boolean empty){
                super.updateItem(mensagem, empty);
                if (empty || mensagem == null) {
                    setText(null);
                    setGraphic(null);
                } else{
                    boolean isMensagemEnviada = isMensagemEnviada(mensagem);
                    if(isMensagemEnviada){
                        setAlignment(Pos.CENTER_RIGHT);
                        setText("EU: " + mensagem.getDES_MENSAGEM());
                        setStyle("-fx-alignment: center-right; -fx-background-color: lightblue; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    }
                    else{
                        setAlignment(Pos.CENTER_LEFT);
                        setText(amigoselecionado.getNOM_USUARIO() + ": " + mensagem.getDES_MENSAGEM());
                        setStyle("-fx-alignment: center-left; -fx-background-color: lightgrey; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    }
                }
            }
        });

    }

    private boolean isMensagemEnviada(Mensagem mensagem){
        return mensagem.getCOD_REMETENTE().getCOD_USUARIO() == codigoUsuarioLogado;
    }

    // Exemplo de método para lidar com o clique no botão.
    @FXML
    private void handleAdicionarAmigoAction(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar Amigo");
        dialog.setHeaderText("Digite o código do amigo: ");
        dialog.setContentText("Código: ");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(codigoDoAmigo -> {
            try {
                int COD_AMIGO = Integer.parseInt(codigoDoAmigo);
                if (!verificarAmigoNoBanco(COD_AMIGO)) {
                    showAlert("Erro", "Usuário não encontrado.", Alert.AlertType.ERROR);
                } else {
                    adicionarAmigo(COD_AMIGO);
                }
            } catch (NumberFormatException e) {
                showAlert("Erro", "Você deve digitar um número inteiro.", Alert.AlertType.ERROR);
            }
        });
    }

    private void adicionarAmigo(int COD_AMIGO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();

        String query = "INSERT INTO amigo (COD_USUARIO, COD_AMIGO) VALUES (?, ?)";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            int COD_USUARIO = usuarioLogado.getCOD_USUARIO();
            statement.setInt(1, COD_USUARIO);
            statement.setInt(2, COD_AMIGO);
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas > 0) {
                showAlert("Sucesso", "Amigo adicionado com sucesso!", Alert.AlertType.INFORMATION);
                atualizarListaAmigos();
            } else {
                showAlert("Falha", "Falha ao adicionar amigo.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao adicionar amigo: " + e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }

    @FXML
    private void handleExcluirAmigoAction(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Excluir Amigo");
        dialog.setHeaderText("Digite o código do amigo que deseja excluir:");
        dialog.setContentText("Código:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(codigoDoAmigo -> {
            try {
                int COD_AMIGO = Integer.parseInt(codigoDoAmigo);
                if (!verificarAmigoNoBanco(COD_AMIGO)) {
                    showAlert("Erro", "Usuário não encontrado.", Alert.AlertType.ERROR);
                    return;
                } else {
                    excluirAmigo(COD_AMIGO);
                }
            } catch (NumberFormatException e) {
                showAlert("Erro", "Você deve digitar um número inteiro válido.", Alert.AlertType.ERROR);
            }
        });
    }

    private void excluirAmigo(int COD_AMIGO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();
        if (isAmigo(COD_AMIGO)) {
            String query = "DELETE FROM amigo WHERE COD_USUARIO = ? AND COD_AMIGO = ?";
            try (PreparedStatement statement = conexao.prepareStatement(query)) {
                statement.setInt(1, usuarioLogado.getCOD_USUARIO());
                statement.setInt(2, COD_AMIGO);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Sucesso", "Amigo excluído com sucesso!", Alert.AlertType.INFORMATION);
                    atualizarListaAmigos();
                } else {
                    showAlert("Erro", "Não foi possível excluir o amigo.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erro no Banco de Dados", "Ocorreu um erro ao tentar excluir o amigo.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erro", "Vocês nao são amigos", Alert.AlertType.WARNING);
        }
    }

    private boolean isAmigo(int COD_AMIGO) {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();
        boolean saoAmigos = false;

        String query = "SELECT COUNT(1) FROM amigo WHERE (COD_USUARIO = ? AND COD_AMIGO = ?) OR (COD_USUARIO = ? AND COD_AMIGO = ?)";
        try {
            PreparedStatement statement = conexao.prepareStatement(query);
            int COD_USUARIO = usuarioLogado.getCOD_USUARIO();
            statement.setInt(1, COD_USUARIO);
            statement.setInt(2, COD_AMIGO);
            statement.setInt(3, COD_AMIGO);
            statement.setInt(4, COD_USUARIO);

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

    private boolean verificarAmigoNoBanco(int COD_USUARIO) {
        BancoDeDados bancoDeDados = new BancoDeDados();

        try (Connection conexao = bancoDeDados.conectar();
             PreparedStatement statement = conexao.prepareStatement("SELECT COUNT(1) FROM usuario WHERE COD_USUARIO = ?")) {

            statement.setInt(1, COD_USUARIO); // Define o código do usuário no parâmetro da consulta

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Retorna verdadeiro se houver pelo menos uma correspondência
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Trate a exceção de maneira apropriada
            showAlert("Erro de Banco de Dados", "Não foi possível verificar o código do amigo.", Alert.AlertType.ERROR);
        }
        return false; // Retorna falso se não encontrou o usuário ou se ocorreu uma exceção
    }

    private void carregarAmigos() {
        // Aqui você chama o método listarAmigos do usuário logado e preenche a lista observável.
        List<Usuario> amigos = usuarioLogado.listarAmigos();
        amigosObservaveis.setAll(amigos);

        if (amigos.isEmpty()) {
            System.out.println("Você não tem amigos cadastrados.");
        }
    }

    public void atualizarListaAmigos() {
        amigosObservaveis.clear(); // Limpa a lista atual
        List<Usuario> amigosAtualizados = usuarioLogado.listarAmigos(); // Obtém a lista atualizada
        amigosObservaveis.addAll(amigosAtualizados); // Adiciona os novos itens à lista observável
    }

    @FXML
    private void handleSendButtonAction(ActionEvent event) {
        amigoselecionado = listViewAmigos.getSelectionModel().getSelectedItem();
        if (amigoselecionado != null) {
            String conteudoMensagem = messageTextField.getText();
            if (!conteudoMensagem.isEmpty()) {
                LocalDateTime agora = LocalDateTime.now();
                Mensagem novaMensagem = new Mensagem(conteudoMensagem, usuarioLogado, amigoselecionado);
                novaMensagem.setDTA_ENVIO(agora); // Definindo a data e hora atual da mensagem
                novaMensagem.enviarMensagem(); // Você precisa implementar este método
                messageTextField.clear(); // Limpar o campo de texto após o envio
                mostrarChatComAmigo();
            } else {
                showAlert("ERRO", "Digite uma mensagem", Alert.AlertType.WARNING);
            }
        } else {
            showAlert("ERRO", "Adicione um amigo", Alert.AlertType.ERROR);
        }
    }

    public void mostrarChatComAmigo() {
        BancoDeDados bancoDeDados = new BancoDeDados();
        Connection conexao = bancoDeDados.conectar();
        ObservableList<Mensagem> mensagensObservaveis = FXCollections.observableArrayList();

        String query = "SELECT * FROM mensagem WHERE " +
                "(COD_REMETENTE = ? AND COD_DESTINATARIO = ?) OR " +
                "(COD_REMETENTE = ? AND COD_DESTINATARIO = ?) " +
                "ORDER BY DTA_ENVIO ASC";

        try (PreparedStatement preparedStatement = conexao.prepareStatement(query)) {
            preparedStatement.setInt(1, codigoUsuarioLogado);
            preparedStatement.setInt(2, amigoselecionado.getCOD_USUARIO());
            preparedStatement.setInt(3, amigoselecionado.getCOD_USUARIO());
            preparedStatement.setInt(4, codigoUsuarioLogado);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Mensagem mensagem = new Mensagem();
                mensagem.setId(resultSet.getInt("COD_MENSAGEM"));
                mensagem.setDES_MENSAGEM(resultSet.getString("DES_MENSAGEM"));
                mensagem.setDTA_ENVIO(resultSet.getTimestamp("DTA_ENVIO").toLocalDateTime());

                Usuario remetente = new Usuario();
                remetente.setCOD_USUARIO(resultSet.getInt("COD_REMETENTE"));
                mensagem.setCOD_REMETENTE(remetente);

                Usuario destinatario = new Usuario();
                destinatario.setCOD_USUARIO(resultSet.getInt("COD_DESTINATARIO"));
                mensagem.setCOD_DESTINATARIO(destinatario);

                mensagensObservaveis.add(mensagem);
                System.out.println("Mensagens carregadas: " + mensagensObservaveis.size());
            }
            // Definindo os itens do ListView.
            listViewMensagens.setItems(mensagensObservaveis);
            System.out.println("ListView deve ser atualizado com " + listViewMensagens.getItems().size() + " mensagens.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            bancoDeDados.desconectar(conexao);
        }
    }


    @FXML
    private void handleSairButtonAction(ActionEvent event) {
        // Fechar a janela atual (Menu)
        Stage stageAtual = (Stage) SairButton.getScene().getWindow();
        stageAtual.close();

        // Abrir a tela de Login
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao voltar para Tela de Login");
        }
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}