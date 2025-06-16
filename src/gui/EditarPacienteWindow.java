package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ButtonGroup;

public class EditarPacienteWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private MaskFormatter mascaraDataNascimento;
	private MaskFormatter mascaraTelefone;
	private MaskFormatter mascaraCep;
	private JTextField txtEstado;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField textField;
	private PacientesWindow pacientesWindow;
	private JTextField textField_1;
	private ButtonGroup BGSEXO;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	
	public EditarPacienteWindow(PacientesWindow pacientesWindow) {
		
		this.criarMascaraDataNascimento();
		this.criarMascaraTelefone();
		this.criarMascaraCep();
		this.BGSEXO = new ButtonGroup();
		
		this.initComponents();
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		
		
		this.pacientesWindow = pacientesWindow;
	}
	
	private void fecharJanela() {
		
		this.dispose();
		this.pacientesWindow.setVisible(true);
		
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void initComponents() {
		
		setTitle("Editar informações do Paciente");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 398);
		contentPane = new JPanel();
		contentPane.setToolTipText("");

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomePaciente = new JLabel("Nome do Paciente:");
		lblNomePaciente.setBounds(28, 24, 142, 14);
		lblNomePaciente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblNomePaciente);
		
		txtNome = new JTextField();
		txtNome.setBounds(145, 21, 260, 20);
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JFormattedTextField formattedDataNascimento = new JFormattedTextField(mascaraDataNascimento);
		formattedDataNascimento.setBounds(28, 69, 107, 20);
		formattedDataNascimento.setForeground(new Color(0, 0, 0));
		formattedDataNascimento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(formattedDataNascimento);
		
		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setBounds(28, 53, 142, 14);
		lblDataNascimento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblDataNascimento);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(155, 53, 97, 14);
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblTelefone);
		
		JFormattedTextField formattedTelefone = new JFormattedTextField(mascaraTelefone);
		formattedTelefone.setBounds(154, 69, 115, 20);
		formattedTelefone.setForeground(Color.BLACK);
		formattedTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(formattedTelefone);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 152, 533, 2);
		contentPane.add(separator);
		
		JLabel lblCEP = new JLabel("CEP:\r\n");
		lblCEP.setBounds(28, 165, 27, 14);
		lblCEP.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblCEP);
		
		JFormattedTextField formattedCEP = new JFormattedTextField(mascaraCep);
		formattedCEP.setBounds(63, 165, 72, 20);
		formattedCEP.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(formattedCEP);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(145, 165, 53, 14);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblEstado);
		
		txtEstado = new JTextField();
		txtEstado.setBounds(195, 162, 115, 20);
		txtEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEstado.setColumns(10);
		contentPane.add(txtEstado);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(320, 165, 53, 14);
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(367, 162, 128, 20);
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCidade.setColumns(10);
		contentPane.add(txtCidade);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(28, 199, 53, 14);
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(73, 196, 115, 20);
		txtBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBairro.setColumns(10);
		contentPane.add(txtBairro);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setBounds(199, 199, 53, 14);
		lblRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblRua);
		
		textField = new JTextField();
		textField.setBounds(234, 196, 278, 20);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textField.setColumns(10);
		contentPane.add(textField);
		
		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(28, 230, 53, 14);
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblNumero);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(83, 228, 72, 20);
		contentPane.add(spinner);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 270, 533, 2);
		contentPane.add(separator_1);
		
		JButton btnLimparCampos = new JButton("Reiniciar Informações");
		btnLimparCampos.setBounds(212, 283, 161, 40);
		btnLimparCampos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(btnLimparCampos);
		
		JButton btnCadastrar = new JButton("Confirmar Edição\r\n");
		btnCadastrar.setBounds(382, 283, 161, 40);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(btnCadastrar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Sexo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(277, 52, 128, 89);
		contentPane.add(panel);
		panel.setLayout(null);
		
		rdbtnMasculino = new JRadioButton("Masculino");
		BGSEXO.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(22, 21, 85, 23);
		panel.add(rdbtnMasculino);
		
		rdbtnFeminino = new JRadioButton("Feminino");
		BGSEXO.add(rdbtnFeminino);
		rdbtnFeminino.setBounds(22, 47, 85, 23);
		panel.add(rdbtnFeminino);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Foto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(415, 11, 115, 130);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblFormaPagamento = new JLabel("Forma de Pagamento:");
		lblFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFormaPagamento.setBounds(28, 101, 142, 14);
		contentPane.add(lblFormaPagamento);
		
		textField_1 = new JTextField();
		textField_1.setBounds(28, 121, 224, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
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
	
	private void criarMascaraDataNascimento() {
		
		try {
			
			this.mascaraDataNascimento = new MaskFormatter("##/##/####");
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	private void criarMascaraTelefone() {
		
		try {
			
			this.mascaraTelefone = new MaskFormatter("(##) #####-####");
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	private void criarMascaraCep() {
		
		try {
			
			this.mascaraCep = new MaskFormatter("#####-###");
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
}
