package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.ExameDAO;
import entities.Exame;
import entities.Medico;
import entities.Paciente;
import entities.TipoExame;


public class ExameService {

	public ExameService() {
		
	}
	
	public void cadastrar(Exame exame)  throws SQLException, IOException {
				
		Connection conn = BancoDados.conectar();
		new ExameDAO(conn).cadastrar(exame);

	}
	
	public List<Exame> buscarExamesPorMedico(String nome) throws SQLException, IOException {
	    
		MedicoService medicoService = new MedicoService();
		Medico medico = medicoService.procurarMedicoPorNome(nome);
		
		Exame exame = new Exame();
		exame.setMedico(medico);
		
	    Connection conn = BancoDados.conectar();
	    List<Exame> listaExames = new ExameDAO(conn).buscarExamesPorMedico(exame);
	    
	    PacienteService pacienteService = new PacienteService();
	    TipoExameService tipoExameService = new TipoExameService();
	    
		for (Exame exame1 : listaExames) {
			
			Paciente paciente = pacienteService.buscarPorId(exame1.getPaciente().getId());
			exame1.setPaciente(paciente);
			
			TipoExame tipoExame = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
	        exame1.setTipoExame(tipoExame);
		}
	    
	    return listaExames;
	}
	
	public List<Exame> buscarExamesPorMedicoEData(String nome, String data) throws SQLException, IOException {

	    MedicoService medicoService = new MedicoService();
	    Medico medico = medicoService.procurarMedicoPorNome(nome);

	    Exame exame = new Exame();
	    exame.setMedico(medico);
	    exame.setDataRealizacao(data);

	    Connection conn = BancoDados.conectar();
	    List<Exame> listaExames = new ExameDAO(conn).buscarExamesPorMedicoEData(exame);

	    PacienteService pacienteService = new PacienteService();
	    TipoExameService tipoExameService = new TipoExameService();

	    for (Exame exame1 : listaExames) {
	    	
	        Paciente paciente = pacienteService.buscarPorId(exame1.getPaciente().getId());
	        exame1.setPaciente(paciente);

	        TipoExame tipoExame = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
	        exame1.setTipoExame(tipoExame);
	    }

	    return listaExames;
	}
	
	public void excluirExame(int idExame) throws SQLException, IOException {
		
        Connection conn = BancoDados.conectar();
        new ExameDAO(conn).excluir(idExame);

    }
	
	public void atualizarStatusRealizado(int idExame, boolean realizado) throws SQLException, IOException {
		
	    Connection conn = BancoDados.conectar();
	    new ExameDAO(conn).atualizarStatusRealizado(idExame, realizado);

	}
	
    public List<Exame> buscarExamesPorPaciente(String nome) throws SQLException, IOException {
        
        PacienteService pacienteService = new PacienteService();
        Paciente paciente = pacienteService.procurarPacientePorNome(nome);
        
        Exame exame = new Exame();
        exame.setPaciente(paciente);
        
        Connection conn = BancoDados.conectar();
        List<Exame> listaExames = new ExameDAO(conn).buscarExamesPorPaciente(exame);
        
	    MedicoService medicoService = new MedicoService();
	    TipoExameService tipoExameService = new TipoExameService();
        
		for (Exame exame1 : listaExames) {
			
		    Medico medico = medicoService.procurarMedicoPorId(exame1.getMedico().getId());
		    exame1.setMedico(medico);
			
			TipoExame tipoExame = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
	        exame1.setTipoExame(tipoExame);
		}
        return listaExames;
        
    }

    public List<Exame> buscarExamesPorPacienteEData(String nome, String data) throws SQLException, IOException {
    	
        PacienteService pacienteService = new PacienteService();
        Paciente paciente = pacienteService.procurarPacientePorNome(nome);
        
        Exame exame = new Exame();
        
        exame.setPaciente(paciente);
        exame.setDataRealizacao(data);
        
        Connection conn = BancoDados.conectar();
        List<Exame> listaExames = new ExameDAO(conn).buscarExamesPorPacienteEData(exame);
        
	    MedicoService medicoService = new MedicoService();
	    TipoExameService tipoExameService = new TipoExameService();

		for (Exame exame1 : listaExames) {
			
		    Medico medico = medicoService.procurarMedicoPorId(exame1.getMedico().getId());
		    exame1.setMedico(medico);
			
			TipoExame tipoExame = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
	        exame1.setTipoExame(tipoExame);
		}
		
        return listaExames;
    }
    
    public Exame procurarExamePorDataEHora(Exame exame) throws SQLException, IOException {
    	
    	Connection conn = BancoDados.conectar();
    	Exame e1 = new ExameDAO(conn).buscarExamePorDataEHora(exame);
    	
    	if (e1.getMedico().getNome().equals("")) {
    		return null;
    		
    	}else {
    		
    		return e1;
    	}
    }
    
    public List<Exame> buscarExamesPorData(String data) throws SQLException, IOException {
        
        Connection conn = BancoDados.conectar();
        List<Exame> listaExames = new ExameDAO(conn).buscarPorData(data);

        PacienteService pacienteService = new PacienteService();
        MedicoService medicoService = new MedicoService();
        TipoExameService tipoExameService = new TipoExameService();

        for (Exame exame1 : listaExames) {
            Paciente paciente = pacienteService.buscarPorId(exame1.getPaciente().getId());
            exame1.setPaciente(paciente);

            Medico medico = medicoService.procurarMedicoPorId(exame1.getMedico().getId());
            exame1.setMedico(medico);

            TipoExame tipoExame = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
            exame1.setTipoExame(tipoExame);
        }

        return listaExames;
    }

    public List<Exame> buscarExamesPorTipo(String nomeTipo) throws SQLException, IOException {
        
        TipoExameService tipoExameService = new TipoExameService();
        TipoExame tipoExame = tipoExameService.buscarPorNome(nomeTipo);
        
        Exame exame = new Exame();
        exame.setTipoExame(tipoExame);
        
        Connection conn = BancoDados.conectar();
        List<Exame> listaExames = new ExameDAO(conn).buscarPorTipo(exame.getTipoExame().getId());

        PacienteService pacienteService = new PacienteService();
        MedicoService medicoService = new MedicoService();

        for (Exame exame1 : listaExames) {
            Paciente paciente = pacienteService.buscarPorId(exame1.getPaciente().getId());
            exame1.setPaciente(paciente);

            Medico medico = medicoService.procurarMedicoPorId(exame1.getMedico().getId());
            exame1.setMedico(medico);

            TipoExame tipo = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
            exame1.setTipoExame(tipo);
        }

        return listaExames;
    }

    public List<Exame> buscarExamesPorDataETipo(String data, String nomeTipo) throws SQLException, IOException {
        
        TipoExameService tipoExameService = new TipoExameService();
        TipoExame tipoExame = tipoExameService.buscarPorNome(nomeTipo);
        
        Exame exame = new Exame();
        exame.setDataRealizacao(data);
        exame.setTipoExame(tipoExame);
        
        Connection conn = BancoDados.conectar();
        List<Exame> listaExames = new ExameDAO(conn).buscarPorDataETipo(exame);

        PacienteService pacienteService = new PacienteService();
        MedicoService medicoService = new MedicoService();

        for (Exame exame1 : listaExames) {
            Paciente paciente = pacienteService.buscarPorId(exame1.getPaciente().getId());
            exame1.setPaciente(paciente);

            Medico medico = medicoService.procurarMedicoPorId(exame1.getMedico().getId());
            exame1.setMedico(medico);

            TipoExame tipo = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
            exame1.setTipoExame(tipo);
        }

        return listaExames;
    }

    public List<Exame> buscarTodosExames() throws SQLException, IOException {
        
        Connection conn = BancoDados.conectar();
        List<Exame> listaExames = new ExameDAO(conn).buscarTodos();

        PacienteService pacienteService = new PacienteService();
        MedicoService medicoService = new MedicoService();
        TipoExameService tipoExameService = new TipoExameService();

        for (Exame exame1 : listaExames) {
            Paciente paciente = pacienteService.buscarPorId(exame1.getPaciente().getId());
            exame1.setPaciente(paciente);

            Medico medico = medicoService.procurarMedicoPorId(exame1.getMedico().getId());
            exame1.setMedico(medico);

            TipoExame tipoExame = tipoExameService.buscarPorId(exame1.getTipoExame().getId());
            exame1.setTipoExame(tipoExame);
        }

        return listaExames;
    }
}
