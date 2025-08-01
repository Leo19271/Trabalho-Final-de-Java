package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Paciente;
import service.PacienteService;

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
	private JTextField txtRua;
	private PacientesWindow pacientesWindow;
	private JTextField txtFormaPagamento;
	private ButtonGroup BGSEXO;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	private Paciente paciente;
	private JTextField txtNum;
	private JFormattedTextField formattedDataNascimento;
	private JFormattedTextField formattedCEP;
	private JFormattedTextField formattedTelefone;
	private PacienteService pacienteService;
	private JLabel lblFoto;
	private File fotoArquivo;
	
	public EditarPacienteWindow(PacientesWindow pacientesWindow, Paciente paciente) {
		
		this.pacienteService = new PacienteService();
		this.paciente = paciente;
		
		this.criarMascaraDataNascimento();
		this.criarMascaraTelefone();
		this.criarMascaraCep();
		this.BGSEXO = new ButtonGroup();
		
		this.initComponents();
		this.popularComponents();
		
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
		
		pacientesWindow.buscarPacientes();
		
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void popularComponents() {

		this.txtNome.setText(paciente.getNome());
		this.txtCidade.setText(paciente.getEndereco().getCidade());
		this.txtEstado.setText(paciente.getEndereco().getEstado());
		this.txtBairro.setText(paciente.getEndereco().getBairro());
		this.txtFormaPagamento.setText(paciente.getFormaPagamento());
		this.txtRua.setText(paciente.getEndereco().getRua());
		this.formattedCEP.setText(paciente.getEndereco().getCep());
		this.formattedDataNascimento.setText(paciente.getDataNascimento());
		this.formattedTelefone.setText(paciente.getTelefone());
		this.txtNum.setText(paciente.getEndereco().getNumero());
		System.out.println(paciente.getFoto());
		this.popularFoto();
		this.popularSexo();
		
	}
	
	private void popularFoto() {
		
		byte[] fotoBytes = paciente.getFoto();
		if (fotoBytes != null && fotoBytes.length > 0) {
		    ImageIcon icon = new ImageIcon(fotoBytes);
		    this.lblFoto.setIcon(icon);
		} else {
		    this.lblFoto.setIcon(null);
		}
	}
	
	private void enviarFoto() {
		
	    JFileChooser fileChooser = new JFileChooser();
	    int resultado = fileChooser.showOpenDialog(this);
	    
	    if (resultado == JFileChooser.APPROVE_OPTION) {
	        fotoArquivo = fileChooser.getSelectedFile();
	        ImageIcon icon = new ImageIcon(fotoArquivo.getAbsolutePath());

	        Image img = icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
	        lblFoto.setIcon(new ImageIcon(img));
	    }
	}
	
	private void popularSexo() {
		
		if(paciente.getSexo().equals("Masculino")) {
			
			rdbtnMasculino.setSelected(true);
		}else {
			
			rdbtnFeminino.setSelected(true);
		}
	}
	
	private LocalDate corrigirData() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(this.formattedDataNascimento.getText(), formatter);
	}
	
	private String escolhaSexoPaciente() {
		
		if (this.rdbtnMasculino.isSelected()) {
			return this.rdbtnMasculino.getText();
		} else if (this.rdbtnFeminino.isSelected()) {
			return this.rdbtnFeminino.getText();
		}
		return null;
	}
	
	private void confirmarEdicao() {
		
		paciente.setNome(this.txtNome.getText());
		paciente.setDataNascimento(corrigirData().toString());
		paciente.setSexo(this.escolhaSexoPaciente());
		paciente.setTelefone(this.formattedTelefone.getText());
		paciente.setFormaPagamento(this.txtFormaPagamento.getText());
		paciente.getEndereco().setCep(this.formattedCEP.getText());
		paciente.getEndereco().setEstado(this.txtEstado.getText());
		paciente.getEndereco().setCidade(this.txtCidade.getText());
		paciente.getEndereco().setBairro(this.txtBairro.getText());
		paciente.getEndereco().setRua(this.txtRua.getText());
		paciente.getEndereco().setNumero(this.txtNum.getText());
		paciente.setFoto(this.pegarFotoDoLabel(lblFoto));
		
		try {
			
			pacienteService.editar(paciente);
			
	    	JOptionPane.showMessageDialog(null, "Paciente editado com sucesso!");
	    	
			fecharJanela();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao editar Paciente na base de dados.", "Erro Editar Paciente", JOptionPane.ERROR_MESSAGE);

		}
	}
	
	private byte[] pegarFotoDoLabel(JLabel lblFoto) {
	    try {

	        ImageIcon icon = (ImageIcon) lblFoto.getIcon();
	        if (icon == null) {
	            return null;
	        }
	        

	        Image image = icon.getImage();
	        BufferedImage buffered = new BufferedImage(
	            image.getWidth(null),
	            image.getHeight(null),
	            BufferedImage.TYPE_INT_ARGB
	        );
	        
	        java.awt.Graphics2D bGr = buffered.createGraphics();
	        bGr.drawImage(image, 0, 0, null);
	        bGr.dispose();

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(buffered, "png", baos);
	        baos.flush();

	        byte[] imageInByte = baos.toByteArray();
	        baos.close();

	        return imageInByte;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
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
		
		formattedDataNascimento = new JFormattedTextField(mascaraDataNascimento);
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
		
		formattedTelefone = new JFormattedTextField(mascaraTelefone);
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
		
		formattedCEP = new JFormattedTextField(mascaraCep);
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
		
		txtRua = new JTextField();
		txtRua.setBounds(234, 196, 278, 20);
		txtRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtRua.setColumns(10);
		contentPane.add(txtRua);
		
		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(28, 230, 53, 14);
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblNumero);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 270, 533, 2);
		contentPane.add(separator_1);
		
		JButton btnLimparCampos = new JButton("Reiniciar Informações");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				popularComponents();
			}
		});
		btnLimparCampos.setBounds(212, 283, 161, 40);
		btnLimparCampos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(btnLimparCampos);
		
		JButton btnCadastrar = new JButton("Confirmar Edição\r\n");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				confirmarEdicao();
			}
		});
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
		panel_1.setBounds(415, 11, 115, 143);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblFoto = new JLabel("\r\n");
		lblFoto.setBounds(10, 11, 95, 97);
		panel_1.add(lblFoto);
		
		JButton btnEnviarFoto = new JButton("Enviar Foto\r\n");
		btnEnviarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				enviarFoto();
			}
		});
		btnEnviarFoto.setBounds(10, 109, 89, 23);
		panel_1.add(btnEnviarFoto);
		
		JLabel lblFormaPagamento = new JLabel("Forma de Pagamento:");
		lblFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFormaPagamento.setBounds(28, 101, 142, 14);
		contentPane.add(lblFormaPagamento);
		
		txtFormaPagamento = new JTextField();
		txtFormaPagamento.setBounds(28, 121, 224, 20);
		contentPane.add(txtFormaPagamento);
		txtFormaPagamento.setColumns(10);
		
		txtNum = new JTextField();
		txtNum.setBounds(83, 228, 86, 20);
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
