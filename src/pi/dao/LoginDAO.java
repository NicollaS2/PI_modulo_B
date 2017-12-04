package pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pi.FabricaConexao;
import pi.javabean.NivelAcesso;

public class LoginDAO {

	public static boolean login(String email, String senha) {
        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "select * from admin where email=? and senha=MD5(?)"; // comando que será executado no BD

            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

            stmt.setString(1, email); // colocando valores nas incógnitas do comando SQL
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery(); // recuperando valor
            boolean resposta = rs.first(); // true ou false na recuperação

            rs.close();
            stmt.close(); // fechando execução
            conexao.close(); //fechando conexão

            return resposta; // retorna a recuperação do valor

        } catch (SQLException e) { // pegamos a SQLException é obrigada a ser tratada se não da erro.
            throw new RuntimeException(e); // transformamos ela em uma Runtime que não é necessário ser tratada.
        }
    }

	public static boolean Nivelacesso(NivelAcesso nivel) {
        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "select nivel from admin where email=?"; // comando que será executado no BD

            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

            stmt.setString(1, nivel.getEmail()); // colocando valores nas incógnitas do comando SQL


            ResultSet rs = stmt.executeQuery(); // recuperando valor
            rs.first(); // true ou false na recuperação

			String pegavalor;
			pegavalor = rs.getString("nivel");
			boolean resultado;

			if(pegavalor.equals("Operador") ){
				resultado = false;
			}else{
				resultado = true;
			}

            rs.close();
            stmt.close(); // fechando execução
            conexao.close(); //fechando conexão

			return resultado;

        } catch (SQLException e) { // pegamos a SQLException é obrigada a ser tratada se não da erro.
            throw new RuntimeException(e); // transformamos ela em uma Runtime que não é necessário ser tratada.
        }
    }
}

