package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entities.Paciente;

public class PacienteDAO {

	private Connection conn;

	public PacienteDAO(Connection conn) {

		this.conn = conn;
	}

	public int cadastrar(Paciente paciente) throws SQLException {

		PreparedStatement st = null;
		
		try {
			
			EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
			int idEndereco = enderecoDAO.cadastrar(paciente.getEndereco());
			
			st = conn.prepareStatement(
					"insert into paciente (nome, dataNascimento, sexo, telefone, formaPagamento, idEndereco) values (?, ?, ?, ?, ?, ?)");

			st.setString(1, paciente.getNome());
			st.setDate(2, Date.valueOf(paciente.getDataNascimento()));
			st.setString(3, paciente.getSexo());
			st.setString(4, paciente.getTelefone());
			st.setString(5, paciente.getFormaPagamento());
			st.setInt(6, idEndereco);
			

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int idPaciente) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from paciente where idPaciente = ?");

			st.setInt(1, idPaciente);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
