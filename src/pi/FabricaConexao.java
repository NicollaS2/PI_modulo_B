package pi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

//@author Nicollas Ramires

public class FabricaConexao {

    public static Connection getConnection(){
        try {
                return DriverManager.getConnection("jdbc:mysql://localhost/bancopi", "root",""); // credenciais para acesso ao BD.
        } catch (SQLException e) { // se não conseguir acesso exibe tela de erro.
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Erro ao conectar ao BD, credenciais incorretas em: FabricaConexao.java\nou o mysql não está ativado!");
            alerta.setTitle("Erro");
            alerta.showAndWait();
            return (Connection) alerta;
    }
        }
}
