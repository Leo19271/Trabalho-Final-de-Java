package entities;

public class Consulta {

	private int idConsulta;
	private String horaConsulta;
	private Medico medico;
	private Paciente paciente;
	
	public Consulta() {}
	
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
}
