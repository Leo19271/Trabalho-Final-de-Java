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
	
	public void cadastrarMedico(Medico medico)  throws SQLException, IOException {
		
		EnderecoService enderecoService = new EnderecoService();
		medico.getEndereco().setIdEndereco(enderecoService.cadastrar(medico.getEndereco()));
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).cadastrar(medico);

	}
	
	public void excluirMedico(Medico medico) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).excluir(medico.getId());
		
		EnderecoService enderecoService = new EnderecoService();
		
		enderecoService.excluirEndereco(medico.getEndereco().getIdEndereco());
	}
	
	public void editarMedico(Medico medico) throws SQLException, IOException {
		
		EnderecoService enderecoService = new EnderecoService();
		
		enderecoService.editarEndereco(medico.getEndereco());
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).editarMedico(medico);
		
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
	
	public Medico procurarMedicoPorNome(String nome) throws SQLException, IOException {
	    
	    EnderecoService enderecoService = new EnderecoService();
		EspecialidadeService especialidadeService = new EspecialidadeService();

	    
	    Connection conn = BancoDados.conectar();
	    Medico medico = new MedicoDAO(conn).procurarMedicoPorNome(nome);
	    
	    Endereco endereco = enderecoService.buscarPorId(medico.getEndereco().getIdEndereco());
	    medico.setEndereco(endereco);
	    
	    Especialidade especialidade = especialidadeService.buscarPorId(medico.getEspecialidade().getIdEspecialidade());
		medico.setEspecialidade(especialidade);
	    
	    return medico;
	}
	
	public List<Medico> buscarMedicosPorNome(String nome) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		
		List<Medico> medicos = new MedicoDAO(conn).buscarMedicosPorNome(nome);
		
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
	
	public Medico buscarMedicoPorCrm(String crm) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		
		Medico medico = new MedicoDAO(conn).buscarMedicoPorCrm(crm);
		
		EspecialidadeService especialidadeService = new EspecialidadeService();
		EnderecoService enderecoService = new EnderecoService();
		
		Endereco endereco = enderecoService.buscarPorId(medico.getEndereco().getIdEndereco());
		medico.setEndereco(endereco);
		
		Especialidade especialidade = especialidadeService.buscarPorId(medico.getEspecialidade().getIdEspecialidade());
		medico.setEspecialidade(especialidade);		

		return medico;
	}
	
	public Medico procurarMedicoPorId(int idMedico) throws SQLException, IOException {
	   
	    Connection conn = BancoDados.conectar();
	    Medico medico = new MedicoDAO(conn).procurarMedicoPorId(idMedico);
	    
	    return medico;
	}
}
