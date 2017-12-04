package pi.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pi.dao.LoginDAO;
import pi.javabean.NivelAcesso;


// @author Nicollas

public class LoginController implements Initializable {

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private BorderPane raiz;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
    }

    @FXML
    private void logar(ActionEvent event) throws IOException {



        String email =	user.getText(); // pegando valores dos campos usuário e senha.
        String senha = password.getText();

        LoginDAO.login(email,senha); // chamando método para validação.

        if(LoginDAO.login(email,senha)){ // se método retorna true concede acesso para a segunda tela do sistema.
			NivelAcesso.setEmail(email);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pi/layout/TelaPrincipal.fxml"));
			Parent telaNova = (Parent) fxmlLoader.load();
			Stage stage = (Stage) raiz.getScene().getWindow();
			stage.setScene(new Scene(telaNova));
			Screen screen = Screen.getPrimary();
			javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
			stage.setX(bounds.getMinX());
			stage.setY(bounds.getMinY());
			stage.setWidth(bounds.getWidth());
			stage.setHeight(bounds.getHeight());
			telaNova.getStylesheets().add("pi/layout/estilo.css");
			stage.setTitle("Tela principal");
			stage.show();

        }
		else{ // caso contrário exibe erro de autenticação.
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setHeaderText("Erro de autenticação...");
            alerta.setContentText("Senha ou email inválidos");
            alerta.showAndWait();
		}
    }
}
