package entities;

public class Especialidade {

	private int idEspecialidade;
	private String nome;

	public Especialidade() {
	}

	public Especialidade(String nome) {

		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdEspecialidade() {
		return idEspecialidade;
	}

	public void setIdEspecialidade(int idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}
	
	public String toString() {
	    return this.nome;
	}
}