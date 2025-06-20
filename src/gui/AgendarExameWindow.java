package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Consulta;
import entities.Exame;
import entities.Medico;
import entities.Paciente;
import entities.TipoExame;
import service.ExameService;
import service.MedicoService;
import service.PacienteService;
import service.TipoExameService;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AgendarExameWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<Paciente> cbPaciente;
    private JComboBox<Medico> cbMedico;
    private JComboBox<TipoExame> cbTipoExame;
    private JFormattedTextField formattedData;
    private JFormattedTextField formattedHora;
    private ExameService exameService;
    private PacienteService pacienteService;
    private MedicoService medicoService;
    private TipoExameService tipoExameService;
    private StartWindow startWindow;

    public AgendarExameWindow(StartWindow startWindow) {
        this.startWindow = startWindow;
        this.exameService = new ExameService();
        this.pacienteService = new PacienteService();
        this.medicoService = new MedicoService();
        this.tipoExameService = new TipoExameService();

        initComponents();
        carregarPacientes();
        carregarMedicos();
        carregarTiposExame();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                voltar();
            }
        });
    }
    
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}

    private void voltar() {
        this.dispose();
        this.startWindow.setVisible(true);
    }
    
    private Exame verificarHoraExame(Exame exame) {
        
    	try{
    		
    	return exameService.procurarExamePorDataEHora(exame);
    
    	}catch(Exception e) {
    		
    	}
    	return null;
    }

    private void initComponents() {
    	
        setTitle("Agendar Exame");
		setResizable(false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 501, 312);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(20, 20, 80, 14);
        contentPane.add(lblPaciente);

        cbPaciente = new JComboBox<>();
        cbPaciente.setBounds(100, 16, 350, 22);
        contentPane.add(cbPaciente);

        JLabel lblMedico = new JLabel("Médico:");
        lblMedico.setBounds(20, 60, 80, 14);
        contentPane.add(lblMedico);

        cbMedico = new JComboBox<>();
        cbMedico.setBounds(100, 56, 350, 22);
        contentPane.add(cbMedico);

        JLabel lblTipoExame = new JLabel("Tipo de Exame:");
        lblTipoExame.setBounds(20, 100, 100, 14);
        contentPane.add(lblTipoExame);

        cbTipoExame = new JComboBox<>();
        cbTipoExame.setBounds(120, 96, 330, 22);
        contentPane.add(cbTipoExame);

        JLabel lblData = new JLabel("Data:");
        lblData.setBounds(20, 140, 50, 14);
        contentPane.add(lblData);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(180, 140, 40, 14);
        contentPane.add(lblHora);

        criarCamposDataHora();

        JButton btnAgendar = new JButton("Agendar");
        btnAgendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agendarExame();
            }
        });
        btnAgendar.setBounds(300, 200, 150, 35);
        contentPane.add(btnAgendar);

        JButton btnLimpar = new JButton("Limpar Campos");
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        btnLimpar.setBounds(130, 200, 150, 35);
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

    private void carregarTiposExame() {
        try {
            List<TipoExame> tipos = tipoExameService.buscarTodos();
            cbTipoExame.removeAllItems();
            for (TipoExame tipo : tipos) {
                cbTipoExame.addItem(tipo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tipos de exame.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agendarExame() {
    	
        try {
            Paciente paciente = (Paciente) cbPaciente.getSelectedItem();
            Medico medico = (Medico) cbMedico.getSelectedItem();
            TipoExame tipoExame = (TipoExame) cbTipoExame.getSelectedItem();

            if (paciente == null || medico == null || tipoExame == null) {
                JOptionPane.showMessageDialog(this, "Selecione paciente, médico e tipo de exame.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String dataStr = formattedData.getText();
            String horaStr = formattedHora.getText();

            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dataHora = inputFormat.parse(dataStr + " " + horaStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dataHoraFormatada = outputFormat.format(dataHora);

            Exame exame = new Exame();
            exame.setDataRealizacao(dataHoraFormatada);
            exame.setRealizado(false);
            exame.setPaciente(paciente);
            exame.setMedico(medico);
            exame.setTipoExame(tipoExame);
            
            Exame e1 = verificarHoraExame(exame);
            
            if(e1 == null) {
            	JOptionPane.showMessageDialog(this, "Já existe um exame neste horário.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            exameService.cadastrar(exame);

            JOptionPane.showMessageDialog(this, "Exame agendado com sucesso!");
            voltar();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao agendar o exame.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        if (cbPaciente.getItemCount() > 0) cbPaciente.setSelectedIndex(0);
        if (cbMedico.getItemCount() > 0) cbMedico.setSelectedIndex(0);
        if (cbTipoExame.getItemCount() > 0) cbTipoExame.setSelectedIndex(0);
        formattedData.setText("");
        formattedHora.setText("");
    }

    private void criarCamposDataHora() {
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            formattedData = new JFormattedTextField(mascaraData);
            formattedData.setBounds(70, 136, 100, 22);
            contentPane.add(formattedData);

            MaskFormatter mascaraHora = new MaskFormatter("##:##");
            mascaraHora.setPlaceholderCharacter('_');
            formattedHora = new JFormattedTextField(mascaraHora);
            formattedHora.setBounds(220, 136, 70, 22);
            contentPane.add(formattedHora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}