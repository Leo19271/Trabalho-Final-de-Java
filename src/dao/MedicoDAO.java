package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Medico;

public class MedicoDAO {

	private Connection conn;

	public MedicoDAO(Connection conn) {

		this.conn = conn;
	}

	public int cadastrar(Medico medico) throws SQLException {

		PreparedStatement st = null;

		try {
			
			EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
			int idEndereco = enderecoDAO.cadastrar(medico.getEndereco());
			
			st = conn.prepareStatement(
					"insert into medico (nome, numeroCRM, telefone, idEndereco, idEspecialidade) values (?, ?, ?, ?, ?)");

			st.setString(1, medico.getNome());
			st.setString(2, medico.getCrm());
			st.setString(3, medico.getTelefone());
			st.setInt(4, idEndereco);
			st.setInt(5, 1);
			

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int idMedico) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from medico where idMedico = ?");

			st.setInt(1, idMedico);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public List<Medico> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from medico order by nome");

			rs = st.executeQuery();

			List<Medico> listaMedicos = new ArrayList<>();
			
			while (rs.next()) {

				Medico medico = new Medico();
				
				medico.setNome(rs.getString("nome"));
				medico.setCrm(rs.getString("numeroCRM"));
				medico.setTelefone(rs.getString("telefone"));
				medico.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
				medico.getEspecialidade().setIdEspecialidade(rs.getInt("idEspecialidade"));
				
				//medico.setEndereco(new EnderecoDAO(conn).buscarPorId(rs.getInt("idEndereco")));
				//medico.setEspecialidade(new EspecialidadeDAO(conn).buscarPorId(rs.getInt("idEspecialidade")));
				
				listaMedicos.add(medico);
			}

			return listaMedicos;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}