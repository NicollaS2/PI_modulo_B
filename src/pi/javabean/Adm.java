
package pi.javabean;


public class Adm {
    String email;
    String senha;
    String nivel;
	int id;

    public Adm(String email, String senha, String nivel, int id) {
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
		this.id = id;
    }

    public Adm() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }




}
