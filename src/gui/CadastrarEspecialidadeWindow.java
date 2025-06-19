package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Especialidade;
import service.EspecialidadeService;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;

public class CadastrarEspecialidadeWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private EspecialidadesWindow especialidadesWindow;
	private JTextField txtEspecialidade;
	private EspecialidadeService especialidadeService;
	
	public CadastrarEspecialidadeWindow(EspecialidadesWindow especialidadesWindow) {
		
		this.especialidadeService = new EspecialidadeService();
				
		this.initComponents();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		this.especialidadesWindow = especialidadesWindow;
		
		this.limparComponentes();
	}
	
	private void fecharJanela() {
		
		this.dispose();
		this.especialidadesWindow.setVisible(true);
		
		especialidadesWindow.buscarEspecialidades();
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}
	
	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}

	private void limparComponentes() {
		
		this.txtEspecialidade.setText("");
			
	}
	
	private void cadastrarEspecialidade() {
		
		Especialidade especialidade = new Especialidade();
		
		especialidade.setNome(this.txtEspecialidade.getText());
		try {
			
			this.especialidadeService.cadastrar(especialidade);
			
	    	JOptionPane.showMessageDialog(null, "Especialidade cadastrada com sucesso!");
	    	
			fecharJanela();
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar Especialidade na base de dados.", "Erro cadastrar Especialidade", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void initComponents() {
		
		setTitle("Cadastrar Especialidade");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 379, 202);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomeEspecialidade = new JLabel("Nome da Especialidade:");
		lblNomeEspecialidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomeEspecialidade.setBounds(30, 31, 165, 14);
		contentPane.add(lblNomeEspecialidade);
		
		txtEspecialidade = new JTextField();
		txtEspecialidade.setBounds(175, 28, 165, 20);
		contentPane.add(txtEspecialidade);
		txtEspecialidade.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar especialidade");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrarEspecialidade();

			}
		});
		btnCadastrar.setBounds(193, 73, 147, 48);
		contentPane.add(btnCadastrar);
		
		JButton btnLimparCampo = new JButton("Limpar campo");
		btnLimparCampo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparComponentes();
			}
		});
		btnLimparCampo.setBounds(30, 73, 147, 48);
		contentPane.add(btnLimparCampo);
		
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
