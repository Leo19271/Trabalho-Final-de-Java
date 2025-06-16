package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.MedicoDAO;
import entities.Endereco;
import entities.Especialidade;
import entities.Medico;

public class MedicoService {

	public MedicoService() {
		
	}
	
	public List<Medico> buscarTodos() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		List <Medico> medicos = new MedicoDAO(conn).buscarTodos();
	
		EspecialidadeService especialidadeService = new EspecialidadeService();
		EnderecoService enderecoService = new EnderecoService();
		
		for (Medico medico : medicos) {
						
			Endereco endereco = enderecoService.buscarPorId(medico.getEndereco().getIdEndereco());
			medico.setEndereco(endereco);
			
			Especialidade especialidade = especialidadeService.buscarPorId(medico.getEspecialidade().getIdEspecialidade());
			medico.setEspecialidade(especialidade);
		}
	
		return medicos;
	}
}
