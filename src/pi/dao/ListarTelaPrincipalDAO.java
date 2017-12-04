package pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pi.FabricaConexao;
import pi.javabean.CadastrarCliente;

public class ListarTelaPrincipalDAO {

	public static ArrayList<CadastrarCliente> listar() {
        try {
            Connection conexao = FabricaConexao.getConnection();
            String sql = "select plano.datacontrato, plano.codcontrato, plano.valor," +
			"endereco.endereco, endereco.cep, endereco.bairro, contato.email," +
			"cliente.nome, cliente.sobrenome, cliente.cpf, cliente.cnpj," +
			"cidade.cidade, estado.sigla from plano " +
			"inner join cliente on cliente.id_cliente = plano.id_cliente " +
			"inner join contato on contato.id_contato = cliente.id_contato " +
			"inner join endereco on endereco.id_endereco = contato.id_contato " +
			"inner join cidade on cidade.id_cidade = endereco.id_cidade " +
			"inner join estado on estado.id_estado = cidade.id_estado;";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            ArrayList<CadastrarCliente> lista = new ArrayList();

            SimpleDateFormat data1 = new SimpleDateFormat("dd-MM-yyyy"); //define o tipo de data apresentavel

            while (rs.next()) {
                CadastrarCliente a = new CadastrarCliente();
                //a.setData((rs.getDate("datacontrato")));
				a.setDatacontratacao(data1.format(rs.getDate("datacontrato")));
                a.setCodcontrato(rs.getString("codcontrato"));
                a.setValor(rs.getDouble("valor"));
                a.setEndereco(rs.getString("endereco"));
                a.setCep(rs.getString("cep"));
                a.setBairro(rs.getString("bairro"));
                a.setEmail(rs.getString("email"));
                a.setConcatenacaoNome(rs.getString("nome")+" "+ rs.getString("sobrenome"));
                a.setCpf(rs.getString("cpf"));
                a.setCnpj(rs.getString("cnpj"));
                a.setCidade(rs.getString("cidade"));
                a.setEstado(rs.getString("sigla"));

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

		public static ArrayList<CadastrarCliente> listarDia(int dia) {
        try {
            Connection conexao = FabricaConexao.getConnection();
            String sql = "select plano.datacontrato, plano.codcontrato, plano.valor," +
			"endereco.endereco, endereco.cep, endereco.bairro, contato.email," +
			"cliente.nome, cliente.sobrenome, cliente.cpf, cliente.cnpj," +
			"cidade.cidade, estado.sigla from plano " +
			"inner join cliente on cliente.id_cliente = plano.id_cliente " +
			"inner join contato on contato.id_contato = cliente.id_contato " +
			"inner join endereco on endereco.id_endereco = contato.id_contato " +
			"inner join cidade on cidade.id_cidade = endereco.id_cidade " +
			"inner join estado on estado.id_estado = cidade.id_estado "+
			" where date_format(plano.datacontrato, '%d') = ? ";

            PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, dia);

            ResultSet rs = stmt.executeQuery();

            ArrayList<CadastrarCliente> lista = new ArrayList();

            SimpleDateFormat data1 = new SimpleDateFormat("dd-MM-yyyy"); //define o tipo de data apresentavel

            while (rs.next()) {
                CadastrarCliente a = new CadastrarCliente();
                //a.setData((rs.getDate("datacontrato")));
				a.setDatacontratacao(data1.format(rs.getDate("datacontrato")));
                a.setCodcontrato(rs.getString("codcontrato"));
                a.setValor(rs.getDouble("valor"));
                a.setEndereco(rs.getString("endereco"));
                a.setCep(rs.getString("cep"));
                a.setBairro(rs.getString("bairro"));
                a.setEmail(rs.getString("email"));
                a.setConcatenacaoNome(rs.getString("nome")+" "+ rs.getString("sobrenome"));
                a.setCpf(rs.getString("cpf"));
                a.setCnpj(rs.getString("cnpj"));
                a.setCidade(rs.getString("cidade"));
                a.setEstado(rs.getString("sigla"));

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
