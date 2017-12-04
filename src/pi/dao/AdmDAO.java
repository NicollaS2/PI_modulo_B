package pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pi.FabricaConexao;
import pi.javabean.Adm;

public class AdmDAO {

    public static void cadastrar(Adm adm) { // Objeto cadastrar passa parametros da Classe CadastrarCliente (Javbean)

        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "insert into admin (email, senha, nivel) values (?,md5(?),?);"; // comando que será executado no BD
            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

            stmt.setString(1, adm.getEmail()); // trocando valores das incógnitas em ordenação.
            stmt.setString(2, adm.getSenha()); // trocando valores das incógnitas em ordenação.
            stmt.setString(3, adm.getNivel()); // trocando valores das incógnitas em ordenação.

            stmt.executeUpdate();

            stmt.close(); // fechando execução  */

            conexao.close(); //fechando conexão

        } catch (SQLException e) { // pegamos a SQLException é obrigada a ser tratada se não da erro.
            throw new RuntimeException(e); // transformamos ela em uma Runtime que não é necessário ser tratada.
        }

    }

    public static ArrayList<Adm> listar() { // Objeto cadastrar passa parametros da Classe CadastrarCliente (Javbean)

        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "select email, nivel, id from admin;"; // comando que será executado no BD
            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar
            ResultSet rs = stmt.executeQuery();
            ArrayList<Adm> lista = new ArrayList();
            rs.next();
            while (rs.next()) {

                Adm a = new Adm();
                a.setEmail(rs.getString("email"));
                a.setNivel(rs.getString("nivel"));
                a.setId(rs.getInt("id"));
                lista.add(a);
            }

            rs.close();
            stmt.close(); // fechando execução  */
            conexao.close(); //fechando conexão

            return lista;

        } catch (SQLException e) { // pegamos a SQLException é obrigada a ser tratada se não da erro.
            throw new RuntimeException(e); // transformamos ela em uma Runtime que não é necessário ser tratada.
        }
    }

    public static void remover(Adm a) {
        try {
            Connection conexao = FabricaConexao.getConnection();

            String sql = "delete from admin where id=?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, a.getId());

            stmt.execute();

            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editar(Adm a) {
        try {
            Connection conexao = FabricaConexao.getConnection();

            String sql = "update admin set email=?, senha=md5(?), nivel=? where id=?";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, a.getEmail());
            stmt.setString(2, a.getSenha());
            stmt.setString(3, a.getNivel());
            stmt.setInt(4, a.getId());

            stmt.execute();

            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
