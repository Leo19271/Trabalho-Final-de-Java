package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Consulta;
import entities.Medico;
import entities.Paciente;
import service.ConsultaService;
import service.MedicoService;
import service.PacienteService;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AgendarConsultaWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<Paciente> cbPaciente;
    private JComboBox<Medico> cbMedico;
    private JFormattedTextField formattedData;
    private JFormattedTextField formattedHora;
    private ConsultaService consultaService;
    private PacienteService pacienteService;
    private MedicoService medicoService;
    private StartWindow startWindow;

    public AgendarConsultaWindow(StartWindow startWindow) {
        this.startWindow = startWindow;
        this.consultaService = new ConsultaService();
        this.pacienteService = new PacienteService();
        this.medicoService = new MedicoService();

        initComponents();
        carregarPacientes();
        carregarMedicos();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                voltar();
            }
        });
    }

    private void voltar() {
        this.dispose();
        this.startWindow.setVisible(true);
    }

    private void initComponents() {
    	
        setTitle("Agendar Consulta");
		setResizable(false);

        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 462, 279);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 20, 80, 14);
        contentPane.add(lblPaciente);

        cbPaciente = new JComboBox<>();
        cbPaciente.setBounds(100, 16, 320, 22);
        contentPane.add(cbPaciente);

        JLabel lblMedico = new JLabel("Médico:");
        lblMedico.setBounds(20, 60, 80, 14);
        contentPane.add(lblMedico);

        cbMedico = new JComboBox<>();
        cbMedico.setBounds(100, 56, 320, 22);
        contentPane.add(cbMedico);

        JLabel lblData = new JLabel("Data:");
        lblData.setBounds(20, 100, 50, 14);
        contentPane.add(lblData);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(180, 100, 40, 14);
        contentPane.add(lblHora);

        criarCamposDataHora();

        JButton btnAgendar = new JButton("Agendar");
        btnAgendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                agendarConsulta();
            }
        });
        btnAgendar.setBounds(280, 160, 140, 35);
        contentPane.add(btnAgendar);

        JButton btnLimpar = new JButton("Limpar Campos");
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                limparCampos();
            }
        });
        btnLimpar.setBounds(100, 160, 140, 35);
        contentPane.add(btnLimpar);
        
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAplicativo = new JMenu("Aplicativo");
		menuBar.add(mnAplicativo);
		
		JMenuItem mntmFechar = new JMenuItem("Fechar");
		mntmFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				finalizarAplicacao();
			}
		});
		mnAplicativo.add(mntmFechar);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirSobre();
			}
		});
		mnAjuda.add(mntmSobre);

        setLocationRelativeTo(null);
    }

    private void carregarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.buscarTodos();
            
            cbPaciente.removeAllItems();
            
            for (Paciente p : pacientes) {
                cbPaciente.addItem(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pacientes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarMedicos() {
        try {
            List<Medico> medicos = medicoService.buscarTodos();
            
            cbMedico.removeAllItems();
            
            for (Medico m : medicos) {
                cbMedico.addItem(m);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar médicos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agendarConsulta() {
        try {
            Paciente paciente = (Paciente) cbPaciente.getSelectedItem();
            Medico medico = (Medico) cbMedico.getSelectedItem();

            if (paciente == null || medico == null) {
                JOptionPane.showMessageDialog(this, "Selecione paciente e médico.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String dataStr = formattedData.getText();
            String horaStr = formattedHora.getText();

            SimpleDateFormat Digitado = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dataHora = Digitado.parse(dataStr + " " + horaStr);

            SimpleDateFormat paraOBanco = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dataHoraFormatada = paraOBanco.format(dataHora);

            Consulta consulta = new Consulta();
            consulta.setHoraConsulta(dataHoraFormatada);
            consulta.setPaciente(paciente);
            consulta.setMedico(medico);
            consulta.setRealizada(false);

            consultaService.cadastrar(consulta);

            JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
            voltar();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao agendar a consulta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
    
    private void limparCampos() {
        cbPaciente.setSelectedIndex(0);
        cbMedico.setSelectedIndex(0);
        formattedData.setText("");
        formattedHora.setText("");
    }

    private void criarCamposDataHora() {
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            formattedData = new JFormattedTextField(mascaraData);
            formattedData.setBounds(70, 96, 100, 22);
            contentPane.add(formattedData);

            MaskFormatter mascaraHora = new MaskFormatter("##:##");
            mascaraHora.setPlaceholderCharacter('_');
            formattedHora = new JFormattedTextField(mascaraHora);
            formattedHora.setBounds(220, 96, 70, 22);
            contentPane.add(formattedHora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}