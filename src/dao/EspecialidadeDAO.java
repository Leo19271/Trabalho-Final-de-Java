package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Especialidade;

public class EspecialidadeDAO {

	private Connection conn;

	public EspecialidadeDAO(Connection conn) {

		this.conn = conn;
	}

	public int cadastrar(Especialidade especialidade) throws SQLException {

		PreparedStatement st = null;

		try {
			
			st = conn.prepareStatement(
					"insert into especialidade (nome) values (?)");

			st.setString(1, especialidade.getNome());
			
			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int idEspecialidade) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from especialidade where idEspecialidade = ?");

			st.setInt(1, idEspecialidade);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public List<Especialidade> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from especialidade order by idEspecialidade");

			rs = st.executeQuery();

			List<Especialidade> listaEspecialidades = new ArrayList<>();
			
			while (rs.next()) {

				Especialidade especialidade = new Especialidade();
				
				especialidade.setIdEspecialidade(rs.getInt("idEspecialidade"));
				especialidade.setNome(rs.getString("nome"));
				
				listaEspecialidades.add(especialidade);
			}

			return listaEspecialidades;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public Especialidade buscarPorId(int Id) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from especialidade where idEspecialidade = ?");

			st.setInt(1, Id);

			rs = st.executeQuery();

			if (rs.next()) {

				Especialidade especialidade = new Especialidade();
				
				especialidade.setIdEspecialidade(rs.getInt("idEspecialidade"));
				especialidade.setNome(rs.getString("nome"));

				return especialidade;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
