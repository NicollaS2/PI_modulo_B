package pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pi.FabricaConexao;
import pi.javabean.CadastrarCliente;

public class EditarClienteDAO {

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

    public static CadastrarCliente listarCliente(CadastrarCliente c) {

        try {
            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql = "select cidade.cidade, cliente.nome, cliente.sobrenome, "
                    + "cliente.cpf, cliente.cnpj, contato.email, contato.fixo, "
                    + "contato.celular1, contato.celular2, endereco.endereco, "
                    + "endereco.cep, endereco.bairro, estado.sigla, pagamento.descricao, "
                    + "plano.codcontrato, plano.dominio, plano.valor, plano.parcelas "
                    + "from estado "
                    + "inner join cidade on estado.id_estado = cidade.id_estado "
                    + "inner join endereco on cidade.id_cidade = endereco.id_cidade "
                    + "inner join contato on endereco.id_endereco = contato.id_endereco "
                    + "inner join cliente on contato.id_contato = cliente.id_contato "
                    + "inner join plano on cliente.id_cliente = plano.id_cliente "
                    + "inner join pagamento on pagamento.id_pagamento = plano.id_pagamento "
                    + "where plano.codcontrato = ? ";
            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

            stmt.setString(1, c.getCodcontrato());

            ResultSet rs = stmt.executeQuery();

            CadastrarCliente a = new CadastrarCliente();

            if (rs.first()) {
                a.setNome((rs.getString("nome")));
                a.setUltimonome(rs.getString("sobrenome"));
                a.setCpf(rs.getString("cpf"));
                a.setCnpj(rs.getString("cnpj"));
                a.setEmail(rs.getString("email"));
                a.setFone(rs.getString("fixo"));
                a.setCelular(rs.getString("celular1"));
                a.setCelular2(rs.getString("celular2"));
                a.setEndereco(rs.getString("endereco"));
                a.setCep(rs.getString("cep"));
                a.setBairro(rs.getString("bairro"));
                a.setEstado(rs.getString("sigla"));
                a.setCidade(rs.getString("cidade"));
                //a.setFormapgto(rs.getString("descricao"));
                a.setCodcontrato(rs.getString("codcontrato"));
                a.setDominio(rs.getString("dominio"));
                a.setValor(rs.getDouble("valor"));
                a.setParcelas(rs.getInt("parcelas"));


            }
            rs.close();
            stmt.close();
            conexao.close();

            return a;

        } catch (SQLException ex) {

            throw new RuntimeException(ex);


        }

    }

    public static void editar(CadastrarCliente c) {

        try {

            Connection conexao = FabricaConexao.getConnection(); // Abrindo a conexão

            String sql2 = "select id_cidade from cidade where cidade = (?); "; // comando que será executado no BD
            PreparedStatement stmt2 = conexao.prepareStatement(sql2); //pegando a string e preparando para executar

            stmt2.setString(1, c.getCidade()); // trocando valores das incógnitas em ordenação.
            ResultSet rs = stmt2.executeQuery();
            //rs.previous();
            rs.next();
            int idcidade = rs.getInt("id_cidade");

            rs.close();
            stmt2.close(); // fechando execução  */

            String sql = "update cliente as c "
                    + "inner join plano as p on c.id_cliente = p.id_cliente "
                    + "inner join contato as cont on  cont.id_contato = c.id_contato "
                    + "inner join endereco as ende on ende.id_endereco = cont.id_endereco "
                    + "inner join cidade as cid on cid.id_cidade = ende.id_cidade "
                    + "inner join estado as est on est.id_estado = cid.id_estado "
                    + "inner join pagamento as pag on pag.id_pagamento = p.id_pagamento "
                    + " set c.nome = (?), "
                    + " c.sobrenome = (?), "
                    + " c.cpf = (?), "
                    + "c.cnpj = (?),"
                    + " cont.email = (?), "
                    + " cont.fixo = (?), "
                    + " cont.celular1 = (?), "
                    + "cont.celular2 = (?), "
                    + " ende.endereco = (?), "
                    + " ende.cep = (?), "
                    + " ende.bairro = (?), "
                    + " p.dominio = (?), "
                    + " p.valor = (?), "
                    + " p.parcelas = (?), "
                    + " ende.id_cidade = (?), "
                    + " p.id_pagamento = (?), "
                    + " p.datacontrato = ?, "
                    + " p.codcontrato = ? "
                    + " where p.codcontrato = ? ";

            PreparedStatement stmt = conexao.prepareStatement(sql); //pegando a string e preparando para executar

            stmt.setString(1, c.getNome()); // trocando valores das incógnitas em ordenação.
            stmt.setString(2, c.getUltimonome()); // trocando valores das incógnitas em ordenação.
            stmt.setString(3, c.getCpf());
            stmt.setString(4, c.getCnpj());
            stmt.setString(5, c.getEmail());
            stmt.setString(6, c.getFone());
            stmt.setString(7, c.getCelular());
            stmt.setString(8, c.getCelular2());
            stmt.setString(9, c.getEndereco());
            stmt.setString(10, c.getCep());
            stmt.setString(11, c.getBairro());
            stmt.setString(12, c.getDominio());
            stmt.setDouble(13, c.getValor());
            stmt.setInt(14, c.getParcelas());
            stmt.setInt(15, idcidade);
            stmt.setInt(16, c.getFormapgto());
			stmt.setString(17, c.getDatacontratacao());
            stmt.setString(18, c.getConcatenacaoNome());
            stmt.setString(19, c.getCodcontrato());

            stmt.execute(); // executando
            stmt.close(); // fechando execução
            conexao.close(); //fechando conexão

        } catch (SQLException e) { // pegamos a SQLException é obrigada a ser tratada se não da erro.
            throw new RuntimeException(e); // transformamos ela em uma Runtime que não é necessário ser tratada.

        }

    }

}
