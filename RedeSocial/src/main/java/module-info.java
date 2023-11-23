module br.com.projeto.redesocial {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens br.com.projeto.redesocial to javafx.fxml;
    exports br.com.projeto.redesocial;
}