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

	public synchronized int cadastrar(Medico medico) throws SQLException {

		PreparedStatement st = null;

		try {
			
			st = conn.prepareStatement(
					"insert into medico (nome, numeroCRM, telefone, idEndereco, idEspecialidade) values (?, ?, ?, ?, ?)");

			st.setString(1, medico.getNome());
			st.setString(2, medico.getCrm());
			st.setString(3, medico.getTelefone());
			st.setInt(4, medico.getEndereco().getIdEndereco());
			st.setInt(5, medico.getEspecialidade().getIdEspecialidade());
			

			return st.executeUpdate();
			
		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
    public Medico procurarMedicoPorNome(String nome) throws SQLException {
    	
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement("SELECT * FROM medico WHERE nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();

            if (rs.next()) {
                Medico medico = new Medico();

                medico.setId(rs.getInt("idMedico"));
                medico.setNome(rs.getString("nome"));
                medico.setCrm(rs.getString("numeroCRM"));
                medico.setTelefone(rs.getString("telefone"));
                medico.getEspecialidade().setIdEspecialidade(rs.getInt("idEspecialidade"));
                medico.getEndereco().setIdEndereco(rs.getInt("idEndereco"));

                return medico;
            }
            return null;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }
 
    
	public synchronized void editarMedico(Medico medico) throws SQLException {
		
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update medico set nome = ?, numeroCRM = ?, telefone = ?, idEndereco = ?, idEspecialidade = ? where idMedico = ?");

			st.setString(1, medico.getNome());
			st.setString(2, medico.getCrm());
			st.setString(3, medico.getTelefone());
			st.setInt(4, medico.getEndereco().getIdEndereco());
			st.setInt(5, medico.getEspecialidade().getIdEspecialidade());
			st.setInt(6, medico.getId());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public synchronized int excluir(int idMedico) throws SQLException {

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
				
				medico.setId(rs.getInt("idMedico"));
				medico.setNome(rs.getString("nome"));
				medico.setCrm(rs.getString("numeroCRM"));
				medico.setTelefone(rs.getString("telefone"));
				medico.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
				medico.getEspecialidade().setIdEspecialidade(rs.getInt("idEspecialidade"));
							
				listaMedicos.add(medico);
			}

			return listaMedicos;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Medico> buscarMedicosPorNome(String nome) throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {

			st = conn.prepareStatement("select * from medico where nome LIKE ? order by nome");

			st.setString(1, "%" + nome + "%");

			rs = st.executeQuery();
			
			List<Medico> listaMedicos = new ArrayList<>();

			while (rs.next()) {

				Medico medico = new Medico();
				
				medico.setId(rs.getInt("idMedico"));
				medico.setNome(rs.getString("nome"));
				medico.setCrm(rs.getString("numeroCRM"));
				medico.setTelefone(rs.getString("telefone"));
				medico.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
				medico.getEspecialidade().setIdEspecialidade(rs.getInt("idEspecialidade"));
				
				listaMedicos.add(medico);

			}
			
			return listaMedicos;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public Medico buscarMedicoPorCrm(String crm) throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {

			st = conn.prepareStatement("select * from medico where numeroCRM = ? order by nome");

			st.setString(1, crm);

			rs = st.executeQuery();

			if (rs.next()) {

				Medico medico = new Medico();
				
				medico.setId(rs.getInt("idMedico"));
				medico.setNome(rs.getString("nome"));
				medico.setCrm(rs.getString("numeroCRM"));
				medico.setTelefone(rs.getString("telefone"));
				medico.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
				medico.getEspecialidade().setIdEspecialidade(rs.getInt("idEspecialidade"));
				
				return medico;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}