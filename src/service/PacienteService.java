package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.PacienteDAO;
import entities.Endereco;
import entities.Paciente;

public class PacienteService {

	public PacienteService() {
	}
	
	public void cadastrar(Paciente paciente)  throws SQLException, IOException {
		
		EnderecoService enderecoService = new EnderecoService();
		paciente.getEndereco().setIdEndereco(enderecoService.cadastrar(paciente.getEndereco()));
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).cadastrar(paciente);
	}
	
	public List<Paciente> buscarTodos() throws SQLException, IOException {
			
		Connection conn = BancoDados.conectar();
		List <Paciente> pacientes = new PacienteDAO(conn).buscarTodos();
		
		EnderecoService enderecoService = new EnderecoService();
		
		for (Paciente paciente : pacientes) {
							
			Endereco endereco = enderecoService.buscarPorId(paciente.getEndereco().getIdEndereco());
			paciente.setEndereco(endereco);

		}
		
		return pacientes;
	}
	
public void excluirPaciente(Paciente paciente) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).excluir(paciente.getId());
		
		EnderecoService enderecoService = new EnderecoService();
		
		enderecoService.excluirEndereco(paciente.getEndereco().getIdEndereco());
	}
	
	public void editarPacientePorId(Paciente paciente) throws SQLException, IOException {
		
		EnderecoService enderecoService = new EnderecoService();
		enderecoService.editarEndereco(paciente.getEndereco());
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).editarPaciente(paciente);
	}

	public List<Paciente> listarPacientesPorNome(String nome) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new PacienteDAO(conn).listarPacientesPorNome(nome);
	}

	public Paciente procurarPacientePorNome(String nome) throws SQLException, IOException {
		
		EnderecoService enderecoService = new EnderecoService();

		
		Connection conn = BancoDados.conectar();
		Paciente paciente = new PacienteDAO(conn).procurarPacientePorNome(nome);
		
		Endereco endereco = enderecoService.buscarPorId(paciente.getEndereco().getIdEndereco());
		paciente.setEndereco(endereco);
		
		return paciente;
	}
	
	public void editar(Paciente paciente) throws SQLException, IOException {
		
		EnderecoService enderecoService = new EnderecoService();
		
		enderecoService.editarEndereco(paciente.getEndereco());
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).editarPaciente(paciente);
	}
	
    public Paciente buscarPorId(int idPaciente) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new PacienteDAO(conn).buscarPorId(idPaciente);
    }
}
