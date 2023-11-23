package br.com.projeto.redesocial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega o layout do arquivo FXML.
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

            // Define o título da janela (Stage).
            primaryStage.setTitle("Login");

            // Define a cena principal com o layout carregado.
            primaryStage.setScene(new Scene(root));

            // Mostra a janela.
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Lança a aplicação.
        launch(args);
    }
}
