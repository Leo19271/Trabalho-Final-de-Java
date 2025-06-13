package dao;

import entities.*;

public class daoMain {

	public static void main(String[] args) {

		Medico medico = new Medico();
		Paciente paciente = new Paciente();
		TipoExame tipo1 = new TipoExame();
		
		medico.setId(2);
		paciente.setId(1);
		tipo1.setId(1);
		
		Consulta consulta1 = new Consulta("2025-06-15 12:00:00", medico, paciente);
		
		try {
			ConsultaDAO consultaDAO = new ConsultaDAO(BancoDados.conectar());

			consultaDAO.cadastrar(consulta1);
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
		
	}
}
