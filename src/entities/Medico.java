package entities;

public class Medico {

	private int id;
	private String crm;
	private String nome;
	private String telefone;
	private Endereco endereco;
	private Especialidade especialidade;

	public Medico() {
		
		this.especialidade = new Especialidade();
		this.endereco = new Endereco();
	}

	public Medico(String crm, String nome, String telefone, Endereco endereco, Especialidade especialidade) {
		this.crm = crm;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.especialidade = especialidade;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
