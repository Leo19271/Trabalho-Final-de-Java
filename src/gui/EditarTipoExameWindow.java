package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import dao.BancoDados;
import dao.TipoExameDAO;
import entities.TipoExame;
import service.TipoExameService;

public class EditarTipoExameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TipoExameWindow tipoExameWindow;
	private JTextField txtNomeExame;
	private JTextField txtObsExame;
	private JFormattedTextField fTxtValorExame;
	private TipoExame tipoExameSelecionado;

	public EditarTipoExameWindow(TipoExameWindow tipoExameWindow, TipoExame tipoExameSelecionado) {
		this.tipoExameWindow = tipoExameWindow;
		this.tipoExameSelecionado = tipoExameSelecionado;

		initComponents();
		carregarDadosTipoExame();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				fecharJanela();
			}
		});
	}

	private void fecharJanela() {
		this.dispose();
		this.tipoExameWindow.setVisible(true);
	}

	private void finalizarAplicacao() {
		System.exit(0);
	}

	private void configurarCampoValor() {
		NumberFormat formatoMoeda = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
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

	private void carregarDadosTipoExame() {
		if (tipoExameSelecionado != null) {
			txtNomeExame.setText(tipoExameSelecionado.getNome());
			txtObsExame.setText(tipoExameSelecionado.getOrientacoes());
			fTxtValorExame.setValue(tipoExameSelecionado.getValor());
		}
	}

	private void salvarAlteracoes() {
		try {
			String nome = txtNomeExame.getText().trim();
			String obs = txtObsExame.getText().trim();
			double valor = ((Number) fTxtValorExame.getValue()).doubleValue();

			if (nome.isEmpty()) {
				JOptionPane.showMessageDialog(this, "O nome do exame não pode estar vazio.");
				return;
			}

			tipoExameSelecionado.setNome(nome);
			tipoExameSelecionado.setOrientacoes(obs);
			tipoExameSelecionado.setValor(valor);

			TipoExameService tipoExameService = new TipoExameService();
			tipoExameService.editarTipoExame(tipoExameSelecionado);

			JOptionPane.showMessageDialog(this, "Tipo de exame atualizado com sucesso!");
			
			tipoExameWindow.buscarTiposExame();
			
			fecharJanela();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao editar tipo de exame: ");
		}
	}

	private void limparCampos() {
		carregarDadosTipoExame();
	}

	private void initComponents() {

		setTitle("Editar informações do Tipo de Exame");
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

		JLabel lblValorExame = new JLabel("Valor do Exame:");
		lblValorExame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblValorExame.setBounds(10, 64, 95, 14);
		contentPane.add(lblValorExame);

		fTxtValorExame = new JFormattedTextField();
		fTxtValorExame.setBounds(115, 62, 100, 20);
		contentPane.add(fTxtValorExame);

		JLabel lblObservacoes = new JLabel("Observações do Exame:");
		lblObservacoes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblObservacoes.setBounds(10, 98, 158, 20);
		contentPane.add(lblObservacoes);

		txtObsExame = new JTextField();
		txtObsExame.setColumns(10);
		txtObsExame.setBounds(10, 119, 418, 128);
		contentPane.add(txtObsExame);

		JButton btnSalvar = new JButton("Salvar Alterações");
		btnSalvar.setBounds(281, 258, 147, 48);
		contentPane.add(btnSalvar);

		JButton btnReiniciar = new JButton("Reiniciar Informações");
		btnReiniciar.setBounds(115, 258, 147, 48);
		contentPane.add(btnReiniciar);

		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarAlteracoes();
			}
		});

		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});

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
