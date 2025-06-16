package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EspecialidadesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private StartWindow startWindow;
	
	public EspecialidadesWindow(StartWindow startWindow) {

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
	
	private void abrirCadastrarEspecialidade() {
		
		CadastrarEspecialidadeWindow cadastrarEspecialidadeWindow = new CadastrarEspecialidadeWindow(this);
		cadastrarEspecialidadeWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void abrirEditarEspecialidade() {
		
		EditarEspecialidadeWindow editarEspecialidadeWindow = new EditarEspecialidadeWindow(this);
		editarEspecialidadeWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void initComponents() {
		
		setTitle("Especialidades");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 248, 217);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome"
			}
		));
		
		JButton btnCadastrarEspecialidade = new JButton("Cadastrar Especialidade");
		btnCadastrarEspecialidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirCadastrarEspecialidade();
			}
		});
		btnCadastrarEspecialidade.setBounds(265, 192, 159, 36);
		contentPane.add(btnCadastrarEspecialidade);
		
		JButton btnEditarEspecialidade = new JButton("Editar Especialidade");
		btnEditarEspecialidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirEditarEspecialidade();
			}
		});
		btnEditarEspecialidade.setBounds(265, 145, 159, 36);
		contentPane.add(btnEditarEspecialidade);
		table.getColumnModel().getColumn(0).setPreferredWidth(399);
		
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
