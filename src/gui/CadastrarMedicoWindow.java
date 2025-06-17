package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Especialidade;
import entities.Medico;
import service.EspecialidadeService;
import service.MedicoService;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.Color;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarMedicoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private MaskFormatter mascaraCrm;
	private MaskFormatter mascaraTelefone;
	private MaskFormatter mascaraCep;
	private JTextField txtEstado;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtRua;
	private MedicosWindow medicoWindow;
	private JTextField txtNum;
	private JFormattedTextField formattedTelefone;
	private JFormattedTextField formattedCRM;
	private JFormattedTextField formattedCEP;
	private JComboBox cbEspecialidade;
	private MedicoService medicoService;
	private EspecialidadeService especialidadeService;
	
	public CadastrarMedicoWindow(MedicosWindow medicoWindow) {
		
		this.medicoService = new MedicoService();
		this.especialidadeService = new EspecialidadeService();
		
		this.criarMascaraCrm();
		this.criarMascaraTelefone();
		this.criarMascaraCep();
		
		
		this.initComponents();
		
		this.buscarEspecialidades();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		
		
		this.medicoWindow = medicoWindow;
	}
	
	private void fecharJanela() {
		
		this.dispose();
		this.medicoWindow.setVisible(true);
		
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void buscarEspecialidades() {
		
		List<Especialidade> listaEspecialidades;
		try {
			listaEspecialidades = this.especialidadeService.buscarTodos();
			
			for(Especialidade especialidade : listaEspecialidades) {
				
				this.cbEspecialidade.addItem(especialidade);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar Especialidades da base de dados.", "Erro Buscar Especialidades", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void limparComponentes() {
		
		this.txtNome.setText("");
		this.txtEstado.setText("");
		this.txtCidade.setText("");
		this.txtBairro.setText("");
		this.txtNum.setText("");
		this.txtRua.setText("");
		this.formattedCEP.setText("");
		this.formattedCRM.setText("");
		this.formattedTelefone.setText("");
		this.cbEspecialidade.setSelectedIndex(0);
		
	}
	
	private void cadastrarMedico() {
		
		Medico medico = new Medico();
		try {
			medico.setNome(this.txtNome.getText());
			medico.setCrm(this.formattedCRM.getText());
			medico.setTelefone(this.formattedTelefone.getText());
			medico.setEspecialidade((Especialidade)this.cbEspecialidade.getSelectedItem());
			medico.getEndereco().setCep(this.formattedCEP.getText());
			medico.getEndereco().setCidade(this.txtCidade.getText());
			medico.getEndereco().setBairro(this.txtBairro.getText());
			medico.getEndereco().setEstado(this.txtEstado.getText());
			medico.getEndereco().setRua(this.txtRua.getText());
			medico.getEndereco().setNumero(this.txtNum.getText());
			
			this.medicoService.cadastrarMedico(medico);
		
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar Medico na base de dados.", "Erro Cadastrar Medico", JOptionPane.ERROR_MESSAGE);
		}finally {
			
			this.medicoWindow.buscarMedicos();
		}
	}
	
	private void verificarCampos() {
		
		if(this.txtNome.getText().equals("")) {
			
			JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro Cadastrar Medico", JOptionPane.ERROR_MESSAGE);
		}else {
			
			cadastrarMedico();
			fecharJanela();
		}
	}
	
	private void initComponents() {
		
		setTitle("Cadastrar Médico");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 536, 387);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomeMedico = new JLabel("Nome do médico:");
		lblNomeMedico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomeMedico.setBounds(28, 24, 107, 14);
		contentPane.add(lblNomeMedico);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtNome.setBounds(136, 22, 368, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		formattedCRM = new JFormattedTextField(mascaraCrm);
		formattedCRM.setForeground(new Color(0, 0, 0));
		formattedCRM.setFont(new Font("Tahoma", Font.PLAIN, 13));
		formattedCRM.setBounds(28, 69, 107, 20);
		contentPane.add(formattedCRM);
		
		JLabel lblCrm = new JLabel("CRM:");
		lblCrm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCrm.setBounds(28, 53, 41, 14);
		contentPane.add(lblCrm);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(177, 53, 97, 14);
		contentPane.add(lblTelefone);
		
		formattedTelefone = new JFormattedTextField(mascaraTelefone);
		formattedTelefone.setForeground(Color.BLACK);
		formattedTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		formattedTelefone.setBounds(177, 69, 115, 20);
		contentPane.add(formattedTelefone);
		
		cbEspecialidade = new JComboBox();
		cbEspecialidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbEspecialidade.setBounds(327, 68, 177, 22);
		contentPane.add(cbEspecialidade);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 118, 494, 2);
		contentPane.add(separator);
		
		JLabel lblCEP = new JLabel("CEP:\r\n");
		lblCEP.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCEP.setBounds(28, 133, 27, 14);
		contentPane.add(lblCEP);
		
		formattedCEP = new JFormattedTextField(mascaraCep);
		formattedCEP.setFont(new Font("Tahoma", Font.PLAIN, 13));
		formattedCEP.setBounds(63, 131, 72, 20);
		contentPane.add(formattedCEP);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEstado.setBounds(145, 134, 53, 14);
		contentPane.add(lblEstado);
		
		txtEstado = new JTextField();
		txtEstado.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEstado.setColumns(10);
		txtEstado.setBounds(195, 131, 115, 20);
		contentPane.add(txtEstado);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCidade.setBounds(327, 134, 53, 14);
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCidade.setColumns(10);
		txtCidade.setBounds(376, 131, 128, 20);
		contentPane.add(txtCidade);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(28, 171, 53, 14);
		contentPane.add(lblBairro);
		
		txtBairro = new JTextField();
		txtBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBairro.setColumns(10);
		txtBairro.setBounds(73, 169, 115, 20);
		contentPane.add(txtBairro);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRua.setBounds(195, 172, 53, 14);
		contentPane.add(lblRua);
		
		txtRua = new JTextField();
		txtRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtRua.setColumns(10);
		txtRua.setBounds(226, 169, 278, 20);
		contentPane.add(txtRua);
		
		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNumero.setBounds(28, 206, 53, 14);
		contentPane.add(lblNumero);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 247, 494, 2);
		contentPane.add(separator_1);
		
		JLabel lblEspecialidade = new JLabel("Especialidade:");
		lblEspecialidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEspecialidade.setBounds(327, 53, 85, 14);
		contentPane.add(lblEspecialidade);
		
		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparComponentes();
			}
		});
		
		btnLimparCampos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLimparCampos.setBounds(226, 260, 128, 40);
		contentPane.add(btnLimparCampos);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				verificarCampos();
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastrar.setBounds(376, 260, 128, 40);
		contentPane.add(btnCadastrar);
		
		txtNum = new JTextField();
		txtNum.setBounds(83, 204, 52, 20);
		contentPane.add(txtNum);
		txtNum.setColumns(10);
		
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
	
	private void criarMascaraCrm() {
		
		try {
			
			this.mascaraCrm = new MaskFormatter("AAA/AA ######");
			
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

