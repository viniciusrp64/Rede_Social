package br.com.projeto.redesocial;

import br.com.projeto.redesocial.backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class CadastroController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private void initialize() {
        // Inicialização, se necessário
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (nome.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Cadastro");
            alert.setHeaderText(null);
            alert.setContentText("Todos os campos devem ser preenchidos.");
            alert.showAndWait(); // Exibe o alerta.
            return;
        }

        if (!email.contains("@")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Cadastro");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, insira um e-mail válido.");
            alert.showAndWait(); // Exibe o alerta.
            return;
        }

        // Substitua esta parte pelo seu método de cadastro real.
        Usuario novoUsuario = new Usuario();
        boolean sucessoCadastro = novoUsuario.fazerRegistro(email, password, nome);

        if (sucessoCadastro) {
            // Mostrar mensagem de sucesso
            showAlert("Cadastro realizado com sucesso!", Alert.AlertType.INFORMATION);
            // Se o cadastro for bem-sucedido, volta para a tela de login.
            try {
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Se o cadastro não for bem-sucedido, exibe uma mensagem de erro.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Cadastro");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível realizar o cadastro. Por favor, revise os dados inseridos e tente novamente.");
            alert.showAndWait(); // Exibe o alerta.
        }
    }

    private void showAlert(String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
