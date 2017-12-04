package pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pi.FabricaConexao;
import pi.javabean.CadastrarCliente;

public class RenovarDAO {

    public static ArrayList<String> listarContratos() {

        try {
            Connection conexao = FabricaConexao.getConnection();

            String sql = "select codcontrato from plano where status_plano = 'Finalizado';";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            ArrayList<String> lista = new ArrayList<>();

            while (rs.next()) {
                lista.add(rs.getString("codcontrato"));
            }

            rs.close();
            stmt.close();
            conexao.close();

            return lista;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static CadastrarCliente listarCliente(CadastrarCliente info) {
        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "select cliente.nome , cliente.sobrenome ,plano.datacontrato, plano.valor, plano.parcelas, plano.status_plano, pagamento.descricao"
                    + " from plano inner join cliente on cliente.id_cliente = plano.id_cliente"
                    + " inner join pagamento on pagamento.id_pagamento = plano.id_pagamento "
                    + " where plano.codcontrato = ?; "; // comando que será executado no BD
            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

            stmt.setString(1, info.getCodcontrato());

            ResultSet rs = stmt.executeQuery();

            CadastrarCliente a = new CadastrarCliente();

            if (rs.first()) {
                a.setNome((rs.getString("nome")));
                a.setUltimonome((rs.getString("sobrenome")));
                a.setValor(rs.getDouble("valor"));
                a.setParcelas(rs.getInt("parcelas"));
                a.setDescricaopagto(rs.getString("descricao"));
                a.setEstado(rs.getString("status_plano"));
            }

            rs.close();
            stmt.close();
            conexao.close();

            return a;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void renovar(CadastrarCliente c) {

        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "update plano set plano.datacontrato = ?, "
                    + "plano.parcelas_pagas = ?, "
                    + "plano.status_plano = ? "
                    + "where plano.codcontrato = ? ";
            
            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar
            stmt.setString(1, c.getDatacontratacao()); // trocando valores das incógnitas em ordenação.
            stmt.setInt(2, 0); // trocando valores das incógnitas em ordenação.
            stmt.setString(3, "Regular"); // trocando valores das incógnitas em ordenação.
            stmt.setString(4, c.getCodcontrato()); // trocando valores das incógnitas em ordenação.
           
            
            stmt.execute();
            stmt.close();
            conexao.close();
          
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
