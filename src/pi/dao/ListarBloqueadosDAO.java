package pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pi.FabricaConexao;
import pi.javabean.CadastrarCliente;

public class ListarBloqueadosDAO {

	public static ArrayList<CadastrarCliente> listar() {
        try {
			Connection conexao = FabricaConexao.getConnection();
			String sql = "select codcontrato, dominio, parcelas, parcelas_pagas, status_plano "+
					" from plano "+
					"where status_plano = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, "Bloqueado");
			ResultSet rs = stmt.executeQuery();

            ArrayList<CadastrarCliente> lista = new ArrayList();

            while (rs.next()) {
				CadastrarCliente a = new CadastrarCliente();
				a.setCodcontrato(rs.getString("codcontrato"));
				a.setDominio(rs.getString("dominio"));
				a.setParcelas(rs.getInt("parcelas"));
				a.setParcelas_pagas(rs.getInt("parcelas_pagas"));
				a.setEstado(rs.getString("status_plano"));
				lista.add(a);
			}

			rs.close();
            stmt.close();
            conexao.close();

            return lista;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
