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

public class MedicosWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane_1;
	private StartWindow startWindow;
	private JTextField textField;
	private JTable table;
	
	public MedicosWindow(StartWindow startWindow) {
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
	
	private void abrirCadastrarMedicos() {
		
		CadastrarMedicoWindow cadastrarMedico = new CadastrarMedicoWindow(this);
		cadastrarMedico.setVisible(true);
		
		this.setVisible(false);
		
	}
	
	private void initComponents() {
		
		
		setTitle("Médicos");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 533, 355);
		
		contentPane_1 = new JPanel();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 549, 370);
		
		setContentPane(contentPane_1);
		contentPane_1.setLayout(null);
		
		JLabel lblNomeMedico = new JLabel("Nome do Médico:");
		lblNomeMedico.setBounds(10, 11, 87, 14);
		contentPane_1.add(lblNomeMedico);
		
		textField = new JTextField();
		textField.setBounds(99, 8, 313, 20);
		contentPane_1.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar\r\n");
		btnBuscar.setBounds(422, 7, 89, 23);
		contentPane_1.add(btnBuscar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 36, 501, 205);
		contentPane_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "CRM", "Especialidade"
			}
		));
		
		JButton BtnCadastrar = new JButton("Cadastrar Médico");
		BtnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirCadastrarMedicos();
			}
		});
		
		BtnCadastrar.setBounds(358, 252, 154, 46);
		contentPane_1.add(BtnCadastrar);
		
		JButton btnEditarMedico = new JButton("Editar Médico");
		btnEditarMedico.setBounds(194, 252, 154, 46);
		contentPane_1.add(btnEditarMedico);
		table.getColumnModel().getColumn(0).setPreferredWidth(195);
		table.getColumnModel().getColumn(1).setPreferredWidth(123);
		table.getColumnModel().getColumn(2).setPreferredWidth(172);
		
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
}
