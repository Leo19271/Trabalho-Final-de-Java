 package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.ConsultaDAO;
import entities.Consulta;
import entities.Medico;
import entities.Paciente;

public class ConsultaService {

    public ConsultaService() {
    }

    public void cadastrar(Consulta consulta) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        new ConsultaDAO(conn).cadastrar(consulta);
    }

    public List<Consulta> buscarConsultasPorMedico(String nome) throws SQLException, IOException {
        MedicoService medicoService = new MedicoService();
        Medico medico = medicoService.procurarMedicoPorNome(nome);

        Consulta consulta = new Consulta();
        consulta.setMedico(medico);

        Connection conn = BancoDados.conectar();
        List<Consulta> listaConsultas = new ConsultaDAO(conn).buscarConsultasPorMedico(consulta);

        PacienteService pacienteService = new PacienteService();

        for (Consulta consulta1 : listaConsultas) {
            Paciente paciente = pacienteService.buscarPorId(consulta1.getPaciente().getId());
            consulta1.setPaciente(paciente);
        }

        return listaConsultas;
    }

    public List<Consulta> buscarConsultasPorMedicoEData(String nome, String data) throws SQLException, IOException {
        MedicoService medicoService = new MedicoService();
        Medico medico = medicoService.procurarMedicoPorNome(nome);

        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setHoraConsulta(data);

        Connection conn = BancoDados.conectar();
        List<Consulta> listaConsultas = new ConsultaDAO(conn).buscarConsultasPorMedicoEData(consulta);

        PacienteService pacienteService = new PacienteService();

        for (Consulta consulta1 : listaConsultas) {
            Paciente paciente = pacienteService.buscarPorId(consulta1.getPaciente().getId());
            consulta1.setPaciente(paciente);
        }

        return listaConsultas;
    }

    public void excluirConsulta(int idConsulta) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        new ConsultaDAO(conn).excluir(idConsulta);
    }

    public void atualizarStatusRealizado(int idConsulta, boolean realizado) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        new ConsultaDAO(conn).atualizarStatusRealizado(idConsulta, realizado);
    }

    public List<Consulta> buscarConsultasPorPaciente(String nome) throws SQLException, IOException {
        PacienteService pacienteService = new PacienteService();
        Paciente paciente = pacienteService.procurarPacientePorNome(nome);

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);

        Connection conn = BancoDados.conectar();
        List<Consulta> listaConsultas = new ConsultaDAO(conn).buscarConsultasPorPaciente(consulta);

        MedicoService medicoService = new MedicoService();

        for (Consulta consulta1 : listaConsultas) {
            Medico medico = medicoService.procurarMedicoPorId(consulta1.getMedico().getId());
            consulta1.setMedico(medico);
        }

        return listaConsultas;
    }

    public List<Consulta> buscarConsultasPorPacienteEData(String nome, String data) throws SQLException, IOException {
        PacienteService pacienteService = new PacienteService();
        Paciente paciente = pacienteService.procurarPacientePorNome(nome);

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setHoraConsulta(data);

        Connection conn = BancoDados.conectar();
        List<Consulta> listaConsultas = new ConsultaDAO(conn).buscarConsultasPorPacienteEData(consulta);

        MedicoService medicoService = new MedicoService();

        for (Consulta consulta1 : listaConsultas) {
            Medico medico = medicoService.procurarMedicoPorId(consulta1.getMedico().getId());
            consulta1.setMedico(medico);
        }

        return listaConsultas;
    }
    
    public Consulta procurarConsultaPorDataEHora(Consulta consulta) throws SQLException, IOException {
    	
    	Connection conn = BancoDados.conectar();
    	Consulta c1 = new ConsultaDAO(conn).buscarConsultaPorDataEHora(consulta);
    	
    	if (c1.getMedico().getNome().equals("")) {
    		return null;
    	}else {
    		return c1;
    	}
    }
    
    public List<Consulta> buscarTodasConsultas() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        List<Consulta> listaConsultas = new ConsultaDAO(conn).buscarTodasConsultas();

        PacienteService pacienteService = new PacienteService();
        MedicoService medicoService = new MedicoService();

        for (Consulta consulta : listaConsultas) {
            Paciente paciente = pacienteService.buscarPorId(consulta.getPaciente().getId());
            consulta.setPaciente(paciente);

            Medico medico = medicoService.procurarMedicoPorId(consulta.getMedico().getId());
            consulta.setMedico(medico);
        }

        return listaConsultas;
    }

    public List<Consulta> buscarConsultasPorData(String data) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        List<Consulta> listaConsultas = new ConsultaDAO(conn).buscarConsultasPorData(data);

        PacienteService pacienteService = new PacienteService();
        MedicoService medicoService = new MedicoService();

        for (Consulta consulta : listaConsultas) {
            Paciente paciente = pacienteService.buscarPorId(consulta.getPaciente().getId());
            consulta.setPaciente(paciente);

            Medico medico = medicoService.procurarMedicoPorId(consulta.getMedico().getId());
            consulta.setMedico(medico);
        }

        return listaConsultas;
    }
}