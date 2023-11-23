package br.com.projeto.redesocial;

import br.com.projeto.redesocial.backend.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class LoginController {

        @FXML
        private TextField emailField;

        @FXML
        private PasswordField passwordField;

        @FXML
        private Button loginButton;

        @FXML
        private Button signUpButton;

        @FXML
        private void initialize() {
            // Inicialização, se necessário
        }

        @FXML
        private void handleLoginButtonAction(ActionEvent event) {
            // Obter email e senha dos campos de texto
            String email = emailField.getText();
            String senha = passwordField.getText();

            // Criar uma instância de Usuario
            Usuario usuario = new Usuario(email, senha);

            // Tentar fazer login
            boolean loginBemSucedido = usuario.fazerLogin(email, senha);
            if (loginBemSucedido) {
                SessaoUsuario.setUsuarioLogado(usuario);
                // Mostrar mensagem de sucesso
                showAlert("Login realizado com sucesso!", "Seu código de amizade é: " + usuario.getCOD_USUARIO(), Alert.AlertType.INFORMATION);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensagem de erro
                showAlert("Erro de Login", "Email ou senha incorretos. Login falhou.", Alert.AlertType.ERROR);
            }
        }

        private void showAlert(String title, String content, Alert.AlertType type) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }

        @FXML
        private void handleSignUpButtonAction(ActionEvent event) {
            try {
                // Carregar o arquivo FXML para o cadastro
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cadastro.fxml"));
                Parent root = loader.load();

                // Obter o palco atual (janela/scene) a partir do evento, e definir a nova cena
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
