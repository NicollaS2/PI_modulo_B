package pi.dao;

// @author Nicollas Ramires
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pi.FabricaConexao;
import pi.javabean.CadastrarCliente;

public class CadastrarClienteDAO {

    public static void cadastrar(CadastrarCliente cliente) { // Objeto cadastrar passa parametros da Classe CadastrarCliente (Javbean)

        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "select id_cidade from cidade where cidade = ?"; // comando que será executado no BD
            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

            stmt.setString(1, cliente.getCidade()); // trocando valores das incógnitas em ordenação.
            ResultSet rs = stmt.executeQuery();
            //rs.previous();
            rs.next();
            int idcidade = rs.getInt("id_cidade");

            rs.close();
            stmt.close(); // fechando execução  */

            String sql_endereco = "insert into endereco(id_cidade,endereco,cep,bairro) values (?, ?, ?, ?)";
            PreparedStatement stmt2 = conexao.prepareStatement(sql_endereco);

            stmt2.setInt(1, idcidade);
            stmt2.setString(2, cliente.getEndereco());
            stmt2.setString(3, cliente.getCep());
            stmt2.setString(4, cliente.getBairro());

            stmt2.execute();
            stmt2.close();

            String sql_contato = "insert into contato(id_endereco, email, fixo, celular1, celular2) values (last_insert_id(), ?, ?, ?,?)";
            PreparedStatement stmt3 = conexao.prepareStatement(sql_contato);

            stmt3.setString(1, cliente.getEmail());
            stmt3.setString(2, cliente.getFone());
            stmt3.setString(3, cliente.getCelular());
            stmt3.setString(4, cliente.getCelular2());
            stmt3.execute();
            stmt3.close();

            String sql_cliente = "insert into cliente(id_contato, nome, sobrenome, cpf, cnpj) values (last_insert_id(), ? , ?, ?, ?)";
            PreparedStatement stmt4 = conexao.prepareStatement(sql_cliente);

            stmt4.setString(1, cliente.getNome());
            stmt4.setString(2, cliente.getUltimonome());
            stmt4.setString(3, cliente.getCpf());
            stmt4.setString(4, cliente.getCnpj());
            stmt4.execute();
            stmt4.close();

            String sql_plano = "insert into plano (id_cliente, id_pagamento, dominio, valor, datacontrato, parcelas, codcontrato, status_plano) values (last_insert_id(), ? , ? , ?, ?, ?, ?, ?)";
            PreparedStatement stmt6 = conexao.prepareStatement(sql_plano);

            stmt6.setInt(1, cliente.getFormapgto());
            stmt6.setString(2, cliente.getDominio());
            stmt6.setDouble(3, cliente.getValor());
            stmt6.setString(4, cliente.getDatacontratacao());
            stmt6.setInt(5, cliente.getParcelas());
            stmt6.setString(6, cliente.getCodcontrato());
            stmt6.setString(7, "Regular");
            stmt6.execute();
            stmt6.close();

            conexao.close(); //fechando conexão

        } catch (SQLException e) { // pegamos a SQLException é obrigada a ser tratada se não da erro.
            throw new RuntimeException(e); // transformamos ela em uma Runtime que não é necessário ser tratada.
        }

    }

    public static ArrayList<String> listarEstados() {

        try {
            Connection con = FabricaConexao.getConnection();

            String sql = "select sigla from estado;";

            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> lista = new ArrayList<>();

            while (rs.next()) {
                lista.add(rs.getString("sigla"));
            }

            rs.close();
            stmt.close();
            con.close();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ArrayList<String> listarCidades(CadastrarCliente cidade) {

        try {
            Connection con = FabricaConexao.getConnection();

            String sql = "select cidade from cidade inner join estado on estado.id_estado = cidade.id_estado where estado.sigla = ?;";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, cidade.getEstado());

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> lista = new ArrayList<>();

            while (rs.next()) {
                lista.add(rs.getString("cidade"));
            }

            rs.close();
            stmt.close();
            con.close();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
