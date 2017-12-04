package pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pi.FabricaConexao;
import pi.javabean.CadastrarCliente;

public class BoletoDAO {

	public static ArrayList<String> listarContratos() {

		try {
			Connection conexao = FabricaConexao.getConnection();

			String sql = "select codcontrato from plano;";

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

	public static ArrayList<CadastrarCliente> listardata(CadastrarCliente info) {
		try {
			Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

			String sql = "select cliente.nome ,plano.datacontrato, plano.valor, plano.parcelas, plano.parcelas_pagas, plano.status_plano, pagamento.descricao,"
					+ " contato.email, contato.fixo, contato.celular1 from plano inner join cliente on cliente.id_cliente = plano.id_cliente"
					+ " inner join pagamento on pagamento.id_pagamento = plano.id_pagamento "
					+ " inner join contato on contato.id_contato = cliente.id_contato"
					+ " where plano.codcontrato = ?; "; // comando que será executado no BD
			PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

			stmt.setString(1, info.getCodcontrato());

			ResultSet rs = stmt.executeQuery();

			ArrayList<CadastrarCliente> encontrado = new ArrayList();

			SimpleDateFormat data1 = new SimpleDateFormat("dd-MM-yyyy"); //define o tipo de data apresentavel

			while (rs.next()) {
				CadastrarCliente a = new CadastrarCliente();
				a.setData((rs.getDate("datacontrato")));
				a.setNome((rs.getString("nome")));
				a.setValor(rs.getDouble("valor"));
				a.setParcelas(rs.getInt("parcelas"));
				a.setDescricaopagto(rs.getString("descricao"));
				a.setFone(rs.getString("fixo"));
				a.setCelular(rs.getString("celular1"));
				a.setEmail(rs.getString("email"));
				a.setParcelas_pagas(rs.getInt("parcelas_pagas"));
				a.setEstado(rs.getString("status_plano"));
				encontrado.add(a);
			}

			rs.close();
			stmt.close();
			conexao.close();

			return encontrado;

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void atualizarPagos(CadastrarCliente pagos) {

        try {
            Connection conexao = FabricaConexao.getConnection();

            String sql = "update plano set parcelas_pagas=?"
                    + " where codcontrato=?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, pagos.getParcelas_pagas());
            stmt.setString(2, pagos.getCodcontrato());
            stmt.execute();

            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
        
        public static void atualizarStatus(CadastrarCliente pagos) {

        try {
            Connection conexao = FabricaConexao.getConnection();

            String sql = "update plano set status_plano=?"
                    + " where codcontrato=?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, pagos.getEstado());
            stmt.setString(2, pagos.getCodcontrato());
            stmt.execute();

            stmt.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
