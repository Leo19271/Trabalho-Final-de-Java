	package dao;
	
	import java.sql.Connection;
	import java.sql.Date;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;
	
	import entities.Paciente;
	
	public class PacienteDAO {
	
		private Connection conn;
	
		public PacienteDAO(Connection conn) {
	
			this.conn = conn;
		}
	
		public synchronized int cadastrar(Paciente paciente) throws SQLException {
	
			PreparedStatement st = null;
			
			try {
							
				st = conn.prepareStatement(
						"insert into paciente (nome, dataNascimento, sexo, telefone, formaPagamento, idEndereco, foto) values (?, ?, ?, ?, ?, ?, ?)");
	
				st.setString(1, paciente.getNome());
				st.setDate(2, Date.valueOf(paciente.getDataNascimento()));
				st.setString(3, paciente.getSexo());
				st.setString(4, paciente.getTelefone());
				st.setString(5, paciente.getFormaPagamento());
				st.setInt(6, paciente.getEndereco().getIdEndereco());
				st.setBytes(7, paciente.getFoto());
	
				return st.executeUpdate();
	
			} finally {
	
				BancoDados.finalizarStatement(st);
				BancoDados.desconectar();
			}
		}
		
		public synchronized int excluir(int idPaciente) throws SQLException {
	
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
		
		public synchronized void editarPaciente(Paciente paciente) throws SQLException {
			
			PreparedStatement st = null;
	
			try {
	
				st = conn.prepareStatement("update paciente set nome = ?, dataNascimento = ?, sexo = ?, telefone = ?, foto = ?, formaPagamento = ?, idEndereco = ? where idPaciente = ?");
	
				st.setString(1, paciente.getNome());
				st.setDate(2, Date.valueOf(paciente.getDataNascimento()));
				st.setString(3, paciente.getSexo());
				st.setString(4, paciente.getTelefone());
				st.setBytes(5, paciente.getFoto());
				st.setString(6, paciente.getFormaPagamento());
				st.setInt(7, paciente.getEndereco().getIdEndereco());
				st.setDouble(8, paciente.getId());
	
				st.executeUpdate();
	
			} finally {
	
				BancoDados.finalizarStatement(st);
				BancoDados.desconectar();
			}
			
		}
		
		public List<Paciente> buscarTodos() throws SQLException{
			
			PreparedStatement st = null;
			ResultSet rs = null;
	
			try {
	
				st = conn.prepareStatement("select * from paciente order by nome");
	
				rs = st.executeQuery();
	
				List<Paciente> listaPacientes = new ArrayList<>();
				
				while (rs.next()) {
	
					Paciente paciente = new Paciente();
					
					paciente.setId(rs.getInt("idPaciente"));
					paciente.setNome(rs.getString("nome"));
					paciente.setDataNascimento(rs.getString("dataNascimento"));
					paciente.setSexo(rs.getString("sexo"));
					paciente.setTelefone(rs.getString("telefone"));
					paciente.setFormaPagamento(rs.getString("formaPagamento"));
					paciente.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
					paciente.setFoto(rs.getBytes("foto"));
								
					listaPacientes.add(paciente);
				}
	
				return listaPacientes;
	
			} finally {
	
				BancoDados.finalizarStatement(st);
				BancoDados.finalizarResultSet(rs);
				BancoDados.desconectar();
			}
		}
		
		public List<Paciente> listarPacientesPorNome(String nome) throws SQLException{
			
			PreparedStatement st = null;
			ResultSet rs = null;
			
			try {
	
				st = conn.prepareStatement("select * from paciente where nome LIKE ? order by nome");
	
				st.setString(1, "%" + nome + "%");
	
				rs = st.executeQuery();
				
				List<Paciente> listaPacientes = new ArrayList<>();
	
				while (rs.next()) {
	
					Paciente paciente = new Paciente();
					
					paciente.setId(rs.getInt("idPaciente"));
					paciente.setNome(rs.getString("nome"));
					paciente.setDataNascimento(rs.getString("dataNascimento"));
					paciente.setSexo(rs.getString("sexo"));
					paciente.setTelefone(rs.getString("telefone"));
					paciente.setFormaPagamento(rs.getString("formaPagamento"));
					paciente.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
					paciente.setFoto(rs.getBytes("foto"));

					
					listaPacientes.add(paciente);
	
				}
				
				return listaPacientes;
	
			} finally {
	
				BancoDados.finalizarStatement(st);
				BancoDados.finalizarResultSet(rs);
				BancoDados.desconectar();
			}
		}
		
		public Paciente buscarPorId(int idPaciente) throws SQLException {
			
		    PreparedStatement st = null;
		    ResultSet rs = null;
	
		    try {
		        st = conn.prepareStatement("SELECT * FROM paciente WHERE idPaciente = ?");
		        
		        st.setInt(1, idPaciente);
	
		        rs = st.executeQuery();
		        
	
		        if (rs.next()) {
		        	Paciente paciente = new Paciente();
		        	
					paciente.setId(rs.getInt("idPaciente"));
					paciente.setNome(rs.getString("nome"));
					paciente.setDataNascimento(rs.getString("dataNascimento"));
					paciente.setSexo(rs.getString("sexo"));
					paciente.setTelefone(rs.getString("telefone"));
					paciente.setFormaPagamento(rs.getString("formaPagamento"));
					paciente.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
					paciente.setFoto(rs.getBytes("foto"));
		            
		            return paciente;
		        }
	
		    } finally {
		        BancoDados.finalizarResultSet(rs);
		        BancoDados.finalizarStatement(st);
		        BancoDados.desconectar();
		    }
	
		    return null;
		}
	
		public Paciente procurarPacientePorNome(String nome) throws SQLException {
			
			PreparedStatement st = null;
			ResultSet rs = null;
			
			try {
	
				st = conn.prepareStatement("select * from paciente where nome = ?");
	
				st.setString(1, nome);
	
				rs = st.executeQuery();
	
				if (rs.next()) {
	
					Paciente paciente = new Paciente();
					
					paciente.setId(rs.getInt("idPaciente"));
					paciente.setNome(rs.getString("nome"));
					paciente.setDataNascimento(rs.getString("dataNascimento"));
					paciente.setSexo(rs.getString("sexo"));
					paciente.setTelefone(rs.getString("telefone"));
					paciente.setFormaPagamento(rs.getString("formaPagamento"));
					paciente.getEndereco().setIdEndereco(rs.getInt("idEndereco"));
					paciente.setFoto(rs.getBytes("foto"));
					
					return paciente;
				}
	
				return null;
	
			} finally {
	
				BancoDados.finalizarStatement(st);
				BancoDados.finalizarResultSet(rs);
				BancoDados.desconectar();
			}
		}
	}
