package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Endereco;

public class EnderecoDAO {

	private Connection conn;

	public EnderecoDAO(Connection conn) {

		this.conn = conn;
	}

	public synchronized int cadastrar(Endereco endereco) throws SQLException {

		PreparedStatement st = null;
		
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"insert into endereco (estado, cidade, bairro, rua, numero, cep) values (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				
			st.setString(1, endereco.getEstado());
			st.setString(2, endereco.getCidade());
			st.setString(3, endereco.getBairro());
			st.setString(4, endereco.getRua());
			st.setString(5, endereco.getNumero());
			st.setString(6, endereco.getCep());
		
			st.executeUpdate();
			
			rs = st.getGeneratedKeys();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
		}
		return 0;
	}
	
	public synchronized void editarEndereco(Endereco endereco) throws SQLException {
		
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update endereco set estado = ?, cidade = ?, bairro = ?, rua = ?, numero = ?, cep = ? where idEndereco = ?");

			st.setString(1, endereco.getEstado());
			st.setString(2, endereco.getCidade());
			st.setString(3, endereco.getBairro());
			st.setString(4, endereco.getRua());
			st.setString(5, endereco.getNumero());
			st.setString(6, endereco.getCep());
			st.setInt(7, endereco.getIdEndereco());
			
			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public synchronized int excluir(int idEndereco) throws SQLException {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("delete from endereco where idEndereco = ?");

			st.setInt(1, idEndereco);

			return st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public Endereco buscarPorId(int Id) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from endereco where idEndereco = ?");

			st.setInt(1, Id);

			rs = st.executeQuery();

			if (rs.next()) {

				Endereco endereco = new Endereco();
				
				endereco.setIdEndereco(rs.getInt("idEndereco"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setRua(rs.getString("rua"));
				endereco.setNumero(rs.getString("numero"));
				endereco.setCep(rs.getString("cep"));
			
				return endereco;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
