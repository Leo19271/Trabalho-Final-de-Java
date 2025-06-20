package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow frame = new StartWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public StartWindow() {
		
		
		
		this.inicializarComponentes();
	}
	
	
	private void abrirMedicos() {
		
		MedicosWindow WindowMedicos = new MedicosWindow(this);
		WindowMedicos.setVisible(true);
		
		this.setVisible(false);
		
	}
	
	private void abrirPacientes() {
		
		PacientesWindow WindowPacientes = new PacientesWindow(this);
		WindowPacientes.setVisible(true);
		
		this.setVisible(false);
		
	}
	
	private void abrirEspecialidades() {
		
		EspecialidadesWindow especialidadesWindow = new EspecialidadesWindow(this);
		especialidadesWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}
	
	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void abrirAgendarExame() {
	    AgendarExameWindow agendarExameWindow = new AgendarExameWindow(this);
	    agendarExameWindow.setVisible(true);
	    
	    this.setVisible(false);
	}
	
	private void abrirTipoExame() {
		
		TipoExameWindow tipoExameWindow = new TipoExameWindow(this);
		tipoExameWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void abrirAgendarConsulta() {
	    AgendarConsultaWindow agendarConsultaWindow = new AgendarConsultaWindow(this);
	    agendarConsultaWindow.setVisible(true);

	    this.setVisible(false);
	}
	
	private void abrirCalendarioConsultas() {
	    CalendarioConsultasWindow calendarioConsultasWindow = new CalendarioConsultasWindow(this);
	    calendarioConsultasWindow.setVisible(true);

	    this.setVisible(false);
	}
	
	private void abrirCalendarioExames() {
	    CalendarioExamesWindow calendarioExamesWindow = new CalendarioExamesWindow(this);
	    calendarioExamesWindow.setVisible(true);

	    this.setVisible(false);
	}
	
	private void inicializarComponentes() {
		
		setTitle("Aplicativo para Clínicas");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 433);
		
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPacientes = new JButton("Pacientes");
		btnPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirPacientes();
			}
		});
		btnPacientes.setBounds(28, 11, 154, 34);
		contentPane.add(btnPacientes);
		
		JButton btnMedicos = new JButton("Medicos");
		btnMedicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirMedicos();
			}
		});
		btnMedicos.setBounds(192, 11, 192, 34);
		contentPane.add(btnMedicos);
		
		JButton btnAgendarExame = new JButton("Agendar Exame");
		btnAgendarExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirAgendarExame();
			}
		});
		btnAgendarExame.setBounds(192, 56, 192, 34);
		contentPane.add(btnAgendarExame);
		
		JButton btnAgendarConsulta = new JButton("Agendar Consulta");
		btnAgendarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirAgendarConsulta();
			}
		});
		btnAgendarConsulta.setBounds(28, 56, 154, 34);
		contentPane.add(btnAgendarConsulta);
		
		JButton btnEspecialidades = new JButton("Especialidades");
		btnEspecialidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirEspecialidades();
			}
		});
		btnEspecialidades.setBounds(394, 11, 154, 34);
		contentPane.add(btnEspecialidades);
		
		JButton btnTipoExame = new JButton("Tipos de Exame");
		btnTipoExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirTipoExame();
			}
		});
		btnTipoExame.setBounds(394, 56, 154, 34);
		contentPane.add(btnTipoExame);
		
		JButton btnCalendarioConsulta = new JButton("Calendario de Consultas");
		btnCalendarioConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirCalendarioConsultas();
			}
		});
		btnCalendarioConsulta.setBounds(28, 101, 154, 34);
		contentPane.add(btnCalendarioConsulta);
		
		JButton btnCadastrarConsulta_1 = new JButton("Calendario de Exames\r\n");
		btnCadastrarConsulta_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirCalendarioExames();
			}
		});
		btnCadastrarConsulta_1.setBounds(192, 101, 192, 34);
		contentPane.add(btnCadastrarConsulta_1);
		
		setLocationRelativeTo(null);
	}
}
