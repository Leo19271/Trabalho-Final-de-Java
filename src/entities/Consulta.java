package entities;

public class Consulta {

	private int idConsulta;
	private String horaConsulta;
	private Medico medico;
	private Paciente paciente;
	private boolean realizada;
	
	public Consulta() {
		
		this.medico = new Medico();
		this.paciente = new Paciente();
	}
	
	public Consulta(String horaConsulta, Medico medico, Paciente paciente) {
		this.horaConsulta = horaConsulta;
		this.medico = medico;
		this.paciente = paciente;
	}

	public int getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}

	public String getHoraConsulta() {
		return horaConsulta;
	}

	public void setHoraConsulta(String horaConsulta) {
		this.horaConsulta = horaConsulta;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}
	
	@Override
	public String toString() {
		return "\n\tID do Exame: " + idConsulta + "\n\tMédico: " 
	    + medico.getNome() + "\n\tPaciente: " + paciente.getNome()
	    + "\n\tData e Hora: " + horaConsulta + "\n\tRealizado: " + realizada;
	}
}
