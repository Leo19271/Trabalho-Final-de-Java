package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.text.NumberFormatter;

import service.TipoExameService;
import javax.swing.JOptionPane;

import entities.TipoExame;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTextArea;

public class CadastrarTipoExameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TipoExameWindow tipoExameWindow;
	private JTextField txtNomeExame;
	private JFormattedTextField fTxtValorExame;
	private JTextArea txtObsExame;
	private TipoExameService tipoExameService;
	
	public CadastrarTipoExameWindow(TipoExameWindow tipoExameWindow) {
		
		this.tipoExameService = new TipoExameService();
		
		this.initComponents();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		this.tipoExameWindow = tipoExameWindow;
	}
	
	private void fecharJanela() {
		
		this.dispose();
		this.tipoExameWindow.setVisible(true);
		
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}
	
	private void limparComponentes() {
		
		this.txtNomeExame.setText("");
		this.fTxtValorExame.setValue(0.0);
		this.txtObsExame.setText("");

	}
	
	private void cadastrarExame() {
	    try {
	        String nome = this.txtNomeExame.getText();
	        String obs = this.txtObsExame.getText();

	        String valorTexto = this.fTxtValorExame.getText().replaceAll("[^\\d,\\.]", "").replace(",", ".");
	        double valor = Double.parseDouble(valorTexto);

	        TipoExame tipoExame = new TipoExame();
	        tipoExame.setNome(nome);
	        tipoExame.setValor(valor);
	        tipoExame.setOrientacoes(obs);

	        tipoExameService.cadastrarTipoExame(tipoExame);

	        JOptionPane.showMessageDialog(this, "Exame cadastrado com sucesso!");

	        limparComponentes();

	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Valor do exame inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Erro ao cadastrar exame: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    }finally {
	    	
	    	tipoExameWindow.buscarTiposExame();
	    	fecharJanela();
	    }
	}
	
	private void configurarCampoValor() {
	    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	    NumberFormatter formatter = new NumberFormatter(formatoMoeda);
	    formatter.setValueClass(Double.class);
	    formatter.setMinimum(0.0);
	    formatter.setAllowsInvalid(false);
	    formatter.setOverwriteMode(false);

	    fTxtValorExame.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formatter));
	    fTxtValorExame.setValue(0.0);
	}
	
	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}

	private void initComponents() {
		
		setTitle("Cadastrar Tipo de Exame");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomeExame = new JLabel("Nome do Exame:");
		lblNomeExame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomeExame.setBounds(10, 30, 107, 14);
		contentPane.add(lblNomeExame);
		
		txtNomeExame = new JTextField();
		txtNomeExame.setBounds(115, 28, 313, 20);
		contentPane.add(txtNomeExame);
		txtNomeExame.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar exame");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrarExame();
			}
		});
		btnCadastrar.setBounds(281, 258, 147, 48);
		contentPane.add(btnCadastrar);
		
		JButton btnLimparCampo = new JButton("Limpar campo");
		btnLimparCampo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparComponentes();
			}
		});
		btnLimparCampo.setBounds(115, 258, 147, 48);
		contentPane.add(btnLimparCampo);
		
		JLabel lblValorExame = new JLabel("Valor do Exame:");
		lblValorExame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblValorExame.setBounds(10, 64, 95, 14);
		contentPane.add(lblValorExame);
		
		fTxtValorExame = new JFormattedTextField();
		fTxtValorExame.setBounds(115, 62, 79, 20);
		contentPane.add(fTxtValorExame);
		
		JLabel lblObservacoes = new JLabel("Observações do Exame:");
		lblObservacoes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblObservacoes.setBounds(10, 98, 158, 20);
		contentPane.add(lblObservacoes);
		
		txtObsExame = new JTextArea();
		txtObsExame.setLineWrap(true);
		txtObsExame.setBounds(10, 117, 418, 130);
		contentPane.add(txtObsExame);
		
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
		
		configurarCampoValor();

		setLocationRelativeTo(null);
	}
}
