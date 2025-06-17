package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Especialidade;
import entities.Medico;
import service.EspecialidadeService;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EspecialidadesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblEspecialidades;
	private StartWindow startWindow;
	private EspecialidadeService especialidadeService;
	
	public EspecialidadesWindow(StartWindow startWindow) {

		this.especialidadeService = new EspecialidadeService();
		
		this.initComponents();
		
		this.buscarEspecialidades();
		
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
	
	protected void buscarEspecialidades() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblEspecialidades.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
	
			List<Especialidade> Especialidades = especialidadeService.buscarTodos();
	
			for (Especialidade especialidade : Especialidades) {
	
				modelo.addRow(new Object[] { 
					especialidade.getNome()
				});
			}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao buscar Especialidades da base de dados.", "Erro Buscar Especialidades", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void abrirCadastrarEspecialidade() {
		
		CadastrarEspecialidadeWindow cadastrarEspecialidadeWindow = new CadastrarEspecialidadeWindow(this);
		cadastrarEspecialidadeWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void abrirEditarEspecialidade(Especialidade especialidade) {
		
		EditarEspecialidadeWindow editarEspecialidadeWindow = new EditarEspecialidadeWindow(this, especialidade);
		editarEspecialidadeWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private Especialidade buscarEspecialidadePorNome(String nome) {
		
		try {
			
			return especialidadeService.buscarPorNome(nome);
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao apagar a especialidade.", "Erro", JOptionPane.WARNING_MESSAGE);
		}
		
		return null;
	}
	
	private void editarEspecialidade() {
		
		int selectedRow = tblEspecialidades.getSelectedRow();

		if(selectedRow >= 0) {
			try {

				Especialidade especialidade = buscarEspecialidadePorNome((String)(tblEspecialidades.getValueAt(selectedRow, 0)));
			    	
				this.abrirEditarEspecialidade(especialidade);
				
			}catch(Exception e) {
				
				JOptionPane.showMessageDialog(null, "Erro ao editar a especialidade.", "Erro", JOptionPane.WARNING_MESSAGE);

			}
			
		}else {
			
			JOptionPane.showMessageDialog(null, "Selecione uma especialidade na tabela para Editar.", "Nenhuma especialidade selecionado", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void apagarEspecialidade() {
		
		int selectedRow = tblEspecialidades.getSelectedRow();

		if(selectedRow >= 0) {
			try {
				
				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar essa especialidade?", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);

			    if (opcao == JOptionPane.YES_OPTION) {

			    	JOptionPane.showMessageDialog(null, "Escolha confirmada!");
			        
					Especialidade especialidade = buscarEspecialidadePorNome((String)(tblEspecialidades.getValueAt(selectedRow, 0)));
			    	
			    	especialidadeService.excluir(especialidade);
			    	
			    	this.buscarEspecialidades();
			        
			    } else if (opcao == JOptionPane.NO_OPTION) {

			        	JOptionPane.showMessageDialog(null, "Escolha não confirmada.");
			    } else {
			    	
			    	   JOptionPane.showMessageDialog(null, "Operação cancelada.");
			    }
				
			}catch(Exception e) {
				
				JOptionPane.showMessageDialog(null, "Erro ao apagar a especialidade.", "Erro", JOptionPane.WARNING_MESSAGE);

			}
			
		}else {
			
			JOptionPane.showMessageDialog(null, "Selecione uma especialidade na tabela para Apagar.", "Nenhuma especialidade selecionada", JOptionPane.WARNING_MESSAGE);
		}
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
		
		tblEspecialidades = new JTable();
		scrollPane.setViewportView(tblEspecialidades);
		tblEspecialidades.setModel(new DefaultTableModel(
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
				
				editarEspecialidade();
			}
		});
		btnEditarEspecialidade.setBounds(265, 145, 159, 36);
		contentPane.add(btnEditarEspecialidade);
		
		JButton btnApagarEspecialidade = new JButton("Apagar Especialidade");
		btnApagarEspecialidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				apagarEspecialidade();
			}
		});
		btnApagarEspecialidade.setBounds(265, 98, 159, 36);
		contentPane.add(btnApagarEspecialidade);
		tblEspecialidades.getColumnModel().getColumn(0).setPreferredWidth(399);
		
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
