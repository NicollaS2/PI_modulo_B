package pi.javabean;

// @author Nicollas Ramires
import java.sql.Date;

public class CadastrarCliente {

	Date data;

	private String nome;
	private String ultimonome;
	private String email;
	private String fone;
	private String celular;
	private String celular2;
	private String cpf;
	private String cnpj;
	private String endereco;
	private String cep;
	private String cidade;
	private String bairro;
	private String estado;
	private String dominio;
	private String codcontrato;
	private double valor;
	private int formapgto;
	private String descricaopagto;
	private String datacontratacao;
	private int parcelas;
	private int parcelas_pagas;
	private String concatenacaoNome;

	public CadastrarCliente(String nome, String ultimonome, String email, String fone, String cpf,
			String cnpj, String endereco, String cep, String cidade, String bairro, String estado,
			String dominio, String codcontrato, double valor, int formapgto, String descricaopagto,
			String datacontratacao, int parcelas, String celular, String celular2,int parcelas_pagas) {

		// Retorno
		this.nome = nome;
		this.ultimonome = ultimonome;
		this.email = email;
		this.fone = fone;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.cep = cep;
		this.cidade = cidade;
		this.bairro = bairro;
		this.estado = estado;
		this.dominio = dominio;
		this.codcontrato = codcontrato;
		this.valor = valor;
		this.formapgto = formapgto;
		this.datacontratacao = datacontratacao;
		this.parcelas = parcelas;
		this.celular = celular;
		this.celular2 = celular2;
		this.descricaopagto = descricaopagto;
		this.parcelas_pagas = parcelas_pagas;
	}

	public String getDescricaopagto() {
		return descricaopagto;
	}

	public void setDescricaopagto(String descricaopagto) {
		this.descricaopagto = descricaopagto;
	}

	public String getConcatenacaoNome() {
		return concatenacaoNome;
	}

	public void setConcatenacaoNome(String concatenacaoNome) {
		this.concatenacaoNome = concatenacaoNome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public CadastrarCliente() {
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUltimonome() {
		return ultimonome;
	}

	public void setUltimonome(String ultimonome) {
		this.ultimonome = ultimonome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getCodcontrato() {
		return codcontrato;
	}

	public void setCodcontrato(String codcontrato) {
		this.codcontrato = codcontrato;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getFormapgto() {
		return formapgto;
	}

	public void setFormapgto(int formapgto) {
		this.formapgto = formapgto;
	}

	public String getDatacontratacao() {
		return datacontratacao;
	}

	public void setDatacontratacao(String datacontratacao) {
		this.datacontratacao = datacontratacao;
	}

	public int getParcelas() {
		return parcelas;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}
	public int getParcelas_pagas() {
		return parcelas_pagas;
	}

	public void setParcelas_pagas(int parcelas_pagas) {
		this.parcelas_pagas = parcelas_pagas;
	}

	public String getCelular2() {
		return celular2;
	}

	public void setCelular2(String celular2) {
		this.celular2 = celular2;
	}

}
