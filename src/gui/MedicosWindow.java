package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Medico;
import service.MedicoService;

import javax.swing.JScrollPane;

public class MedicosWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane_1;
	private StartWindow startWindow;
	private JTextField txtNomeMedico;
	private JTable tblMedicos;
	private MedicoService medicoService;
	
	public MedicosWindow(StartWindow startWindow) {

		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		
		this.initComponents();
		
		this.medicoService = new MedicoService();
		
		this.startWindow = startWindow;
		
		this.buscarMedicos();
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
	
	private void abrirEditarMedico(Medico medico) {
		
		EditarMedicoWindow editarMedico = new EditarMedicoWindow(this, medico);
		editarMedico.setVisible(true);
		
		this.setVisible(false);
		
	}
	
	protected void buscarMedicos() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblMedicos.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
	
			List<Medico> medicos = medicoService.buscarTodos();
	
			for (Medico medico : medicos) {
	
				modelo.addRow(new Object[] { 
					medico.getNome(), 
					medico.getCrm(), 
					medico.getEspecialidade().getNome()
				});
			}
		
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao buscar Medicos da base de dados.", "Erro Buscar Medicos", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private Medico buscarMedicoPorCrm(String crm) {
		
		try {
			
			return medicoService.buscarMedicoPorCrm(crm);
			
		}catch(Exception e){
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar Medico da base de dados.", "Erro Buscar Medico", JOptionPane.ERROR_MESSAGE);
		}
		return null;

	}
	
	private void procurarMedicosPorNome() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblMedicos.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<Medico> medicos = medicoService.buscarMedicosPorNome(this.txtNomeMedico.getText());
			
			for (Medico medico : medicos) {
				
				modelo.addRow(new Object[] { 
					medico.getNome(), 
					medico.getCrm(), 
					medico.getEspecialidade().getNome()
				});
			}
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar Medicos da base de dados.", "Erro Buscar Medicos", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void editarMedico() {
		
		int selectedRow = tblMedicos.getSelectedRow();
		
		if(selectedRow >= 0) {
			
			Medico medico = buscarMedicoPorCrm((String)(tblMedicos.getValueAt(selectedRow, 1)));
			abrirEditarMedico(medico);
		}else {
			JOptionPane.showMessageDialog(null, "Selecione um médico na tabela para editar.", "Nenhum médico selecionado", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void apagarMedico() {
		
		int selectedRow = tblMedicos.getSelectedRow();

		if(selectedRow >= 0) {
			try {
				
				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar esse médico?", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);

			    if (opcao == JOptionPane.YES_OPTION) {

			    	JOptionPane.showMessageDialog(null, "Escolha confirmada!");
			        	
			        apagarMedico();
			    } else if (opcao == JOptionPane.NO_OPTION) {

			        	JOptionPane.showMessageDialog(null, "Escolha não confirmada.");
			    } else {
			    	
			    	   JOptionPane.showMessageDialog(null, "Operação cancelada.");
			    }
			    
				Medico medico = buscarMedicoPorCrm((String)(tblMedicos.getValueAt(selectedRow, 1)));
				medicoService.excluirMedico(medico);
				this.buscarMedicos();
			}catch(Exception e) {
				
				JOptionPane.showMessageDialog(null, "Erro ao apagar o médico.", "Erro", JOptionPane.WARNING_MESSAGE);

			}
			
		}else {
			
			JOptionPane.showMessageDialog(null, "Selecione um médico na tabela para Apagar.", "Nenhum médico selecionado", JOptionPane.WARNING_MESSAGE);
		}
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
		
		txtNomeMedico = new JTextField();
		txtNomeMedico.setBounds(99, 8, 313, 20);
		contentPane_1.add(txtNomeMedico);
		txtNomeMedico.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar\r\n");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				procurarMedicosPorNome();
			}
		});
		btnBuscar.setBounds(422, 7, 89, 23);
		contentPane_1.add(btnBuscar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 36, 501, 205);
		contentPane_1.add(scrollPane);
		
		tblMedicos = new JTable();
		scrollPane.setViewportView(tblMedicos);
		tblMedicos.setModel(new DefaultTableModel(
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
		
		JButton BtnEditarMedico = new JButton("Editar Médico");
		BtnEditarMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				editarMedico();
			}
		});
		
		BtnEditarMedico.setBounds(194, 252, 154, 46);
		contentPane_1.add(BtnEditarMedico);
		
		JButton BtnApagarMedico = new JButton("Apagar Médico");
		BtnApagarMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				apagarMedico();
			}
		});
		BtnApagarMedico.setBounds(30, 252, 154, 46);
		contentPane_1.add(BtnApagarMedico);
		tblMedicos.getColumnModel().getColumn(0).setPreferredWidth(195);
		tblMedicos.getColumnModel().getColumn(1).setPreferredWidth(123);
		tblMedicos.getColumnModel().getColumn(2).setPreferredWidth(172);
		
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
