package entities;

public class TipoExame {

	private int id;
	private String nome;
	private Double valor;
	private String orientacoes;

	public TipoExame() {
	}
	
	public TipoExame(String nome, Double valor, String orientacoes) {
		this.nome = nome;
		this.valor = valor;
		this.orientacoes = orientacoes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getOrientacoes() {
		return orientacoes;
	}

	public void setOrientacoes(String orientacoes) {
		this.orientacoes = orientacoes;
	}
	
	
}
