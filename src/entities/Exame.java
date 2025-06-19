package entities;

public class Exame {

	private int idExame;
	private boolean realizado;
	private String dataRealizacao;
	private Paciente paciente;
	private Medico medico;
	private TipoExame tipoExame;

	public Exame() {
		
		this.medico = new Medico();
		this.paciente = new Paciente();
	}

	public Exame(String dataRealizacao, Paciente paciente, Medico medico, TipoExame tipoExame) {
		this.dataRealizacao = dataRealizacao;
		this.paciente = paciente;
		this.medico = medico;
		this.tipoExame = tipoExame;
	}

	public int getIdExame() {
		return idExame;
	}

	public void setIdExame(int idExame) {
		this.idExame = idExame;
	}

	public String getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(String dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public TipoExame getTipoExame() {
		return tipoExame;
	}

	public void setTipoExame(TipoExame tipoExame) {
		this.tipoExame = tipoExame;
	}

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}
}
