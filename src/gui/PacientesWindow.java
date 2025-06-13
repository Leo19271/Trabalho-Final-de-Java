package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class PacientesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane_1;
	private StartWindow startWindow;
	private JTextField textField;
	private JTable table;
	
	public PacientesWindow(StartWindow startWindow) {
		this.initComponents();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		this.startWindow = startWindow;
	}
	
	private void fecharJanela() {
		
		this.dispose();
		this.startWindow.setVisible(true);
		
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void iniciarCadastrarPaciente() {
		
		CadastrarPacienteWindow cadastrarPacienteWindow = new CadastrarPacienteWindow(this);
		cadastrarPacienteWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void initComponents() {
		
		
		setTitle("Pacientes");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 578, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 549, 370);
		
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
		
		contentPane_1 = new JPanel();
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane_1);
		contentPane_1.setLayout(null);
		
		JLabel lblNomePaciente = new JLabel("Nome do Paciente:");
		lblNomePaciente.setBounds(10, 11, 98, 14);
		contentPane_1.add(lblNomePaciente);
		
		textField = new JTextField();
		textField.setBounds(105, 8, 307, 20);
		contentPane_1.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar\r\n");
		btnBuscar.setBounds(422, 7, 89, 23);
		contentPane_1.add(btnBuscar);
		
		JButton BtnCadastrarPaciente = new JButton("Cadastrar Paciente");
		BtnCadastrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				iniciarCadastrarPaciente();
			}
		});
		
		BtnCadastrarPaciente.setBounds(358, 252, 154, 46);
		contentPane_1.add(BtnCadastrarPaciente);
		
		JButton btnEditarPaciente = new JButton("Editar Paciente");
		btnEditarPaciente.setBounds(194, 252, 154, 46);
		contentPane_1.add(btnEditarPaciente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 513, 205);
		contentPane_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Data de Nascimento", "Telefone", "Forma de Pagamento"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(232);
		table.getColumnModel().getColumn(1).setPreferredWidth(153);
		table.getColumnModel().getColumn(2).setPreferredWidth(112);
		table.getColumnModel().getColumn(3).setPreferredWidth(124);
		
		setLocationRelativeTo(null);
	}
}
