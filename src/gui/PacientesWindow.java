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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entities.Paciente;
import entities.Paciente;
import service.PacienteService;

import javax.swing.JScrollPane;

public class PacientesWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane_1;
	private StartWindow startWindow;
	private JTextField txtNome;
	private JTable tblPacientes;
	private PacienteService pacienteService;
	private boolean salvarThreadRodando;
	private int segundos;
	private JLabel lblSegundos;
	
	public PacientesWindow(StartWindow startWindow) {
		
		this.salvarThreadRodando = true;
		this.segundos = 10;
		
		this.pacienteService = new PacienteService();
		
		this.initComponents();
		
		this.buscarPacientes();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

				fecharJanela();
			}
		});
		this.startWindow = startWindow;
		
		this.iniciarThread();
	}
	
protected void buscarPacientes() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblPacientes.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);

			List<Paciente> pacientes = pacienteService.buscarTodos();
	
			for (Paciente paciente : pacientes) {
	
				modelo.addRow(new Object[] { 
					paciente.getNome(), 
					converterData(paciente.getDataNascimento()),
					paciente.getTelefone(),
					paciente.getFormaPagamento()
				});
			}
		
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao buscar Pacientes da base de dados.", "Erro Buscar Pacientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void fecharJanela() {
		
		this.dispose();
		this.startWindow.setVisible(true);
		
		this.salvarThreadRodando = false;
	}
	
    private void iniciarThread() {
    	
        Thread atualizarThreadPacientes = new Thread(() -> {
            while (salvarThreadRodando) {
                try {
                	for(int i = 0; i < 10; i++) {
                		this.lblSegundos.setText("Atualizará em " + (String.valueOf(segundos) + "s"));
                		Thread.sleep(1000);
                		segundos--;
                	}
                	segundos = 10;
                    this.buscarPacientes();  
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        atualizarThreadPacientes.start();
    }
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void iniciarCadastrarPaciente() {
		
		CadastrarPacienteWindow cadastrarPacienteWindow = new CadastrarPacienteWindow(this);
		cadastrarPacienteWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void iniciarEditarPaciente(Paciente paciente) {
		
		EditarPacienteWindow editarPacienteWindow = new EditarPacienteWindow(this, paciente);
		editarPacienteWindow.setVisible(true);
		
		this.setVisible(false);
	}
	
	private void ListarPacientesPorNome() {

		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblPacientes.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<Paciente> pacientes = pacienteService.listarPacientesPorNome(this.txtNome.getText());
			
			for (Paciente paciente : pacientes) {
				
				modelo.addRow(new Object[] { 
					paciente.getNome(),
					converterData(paciente.getDataNascimento()),
					paciente.getTelefone(),
					paciente.getFormaPagamento()
				});
			}
		}catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar Medicos da base de dados.", "Erro Buscar Medicos", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String converterData(String dataOriginal) {
	    LocalDate data = LocalDate.parse(dataOriginal);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return data.format(formatter);
	}
	
	private Paciente buscarPacientePorNome(String nome) {
		
		try {
			
			return pacienteService.procurarPacientePorNome(nome);
			
		}catch(Exception e){
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar Paciente da base de dados.", "Erro Buscar Paciente", JOptionPane.ERROR_MESSAGE);
		}
		return null;

	}
	
	private void editarPaciente() {
		
		int selectedRow = tblPacientes.getSelectedRow();
		
		if(selectedRow >= 0) {
			
			Paciente paciente = buscarPacientePorNome((String)(tblPacientes.getValueAt(selectedRow, 0)));
			
			paciente.setDataNascimento(converterData(paciente.getDataNascimento()));
			
			iniciarEditarPaciente(paciente);
			
		}else {
			JOptionPane.showMessageDialog(null, "Selecione um Paciente na tabela para editar.", "Nenhum Paciente selecionado", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void abrirCalendarioPaciente() {
	    int selectedRow = tblPacientes.getSelectedRow();
	    try {
		    if (selectedRow >= 0) {
		        String nomePaciente = (String) tblPacientes.getValueAt(selectedRow, 0);
		        
		        Paciente paciente = pacienteService.procurarPacientePorNome(nomePaciente);
		        
		        CalendarioPacienteWindow calendarioWindow = new CalendarioPacienteWindow(this, paciente);
		        calendarioWindow.setVisible(true);
		        this.setVisible(false);
	
		    } else {
		        JOptionPane.showMessageDialog(null, "Selecione um paciente na tabela para visualizar o calendário.", "Nenhum paciente selecionado", JOptionPane.WARNING_MESSAGE);
		    }
	    }catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir calendario paciente.", "Erro", JOptionPane.WARNING_MESSAGE);

	    }
	}
	
	private void apagarPaciente() {
		
		int selectedRow = tblPacientes.getSelectedRow();

		if(selectedRow >= 0) {
			try {
				
				int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar esse Paciente?", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);

			    if (opcao == JOptionPane.YES_OPTION) {

			    	JOptionPane.showMessageDialog(null, "Paciente deletado com sucesso!");
			    	
					Paciente paciente = buscarPacientePorNome((String)(tblPacientes.getValueAt(selectedRow, 0)));
					pacienteService.excluirPaciente(paciente);
					this.buscarPacientes();
			    	
			    } else if (opcao == JOptionPane.NO_OPTION) {

			        	JOptionPane.showMessageDialog(null, "Escolha não confirmada.");
			    } else {
			    	
			    	   JOptionPane.showMessageDialog(null, "Operação cancelada.");
			    }
			    
			}catch(Exception e) {
				
				JOptionPane.showMessageDialog(null, "Erro ao apagar o paciente.", "Erro", JOptionPane.WARNING_MESSAGE);

			}
			
		}else {
			
			JOptionPane.showMessageDialog(null, "Selecione um paciente na tabela para Apagar.", "Nenhum paciente selecionado", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void initComponents() {
		
		
		setTitle("Pacientes");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 549, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
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
		
		contentPane_1 = new JPanel();
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane_1);
		contentPane_1.setLayout(null);
		
		JLabel lblNomePaciente = new JLabel("Nome do Paciente:");
		lblNomePaciente.setBounds(10, 11, 98, 14);
		contentPane_1.add(lblNomePaciente);
		
		txtNome = new JTextField();
		txtNome.setBounds(105, 8, 175, 20);
		contentPane_1.add(txtNome);
		txtNome.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar\r\n");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ListarPacientesPorNome();
			}
		});
		btnBuscar.setBounds(290, 7, 89, 23);
		contentPane_1.add(btnBuscar);
		
		JButton BtnCadastrarPaciente = new JButton("Cadastrar Paciente");
		BtnCadastrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				iniciarCadastrarPaciente();
			}
		});
		
		BtnCadastrarPaciente.setBounds(358, 252, 154, 46);
		contentPane_1.add(BtnCadastrarPaciente);
		
		JButton BtnEditarPaciente = new JButton("Editar Paciente");
		BtnEditarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				editarPaciente();
			}
		});
		
		BtnEditarPaciente.setBounds(194, 252, 154, 46);
		contentPane_1.add(BtnEditarPaciente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 513, 205);
		contentPane_1.add(scrollPane);
		
		tblPacientes = new JTable();
		scrollPane.setViewportView(tblPacientes);
		tblPacientes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Data de Nascimento", "Telefone", "Forma de Pagamento"
			}
		));
		
		JButton BtnApagarPaciente = new JButton("Apagar Paciente");
		BtnApagarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				apagarPaciente();
			}
		});
		BtnApagarPaciente.setBounds(30, 252, 154, 46);
		contentPane_1.add(BtnApagarPaciente);
		
		JButton BtnCalendarioPaciente = new JButton("Ver Calendario do Paciente");
		BtnCalendarioPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirCalendarioPaciente();
			}
		});
		BtnCalendarioPaciente.setBounds(105, 309, 334, 46);
		contentPane_1.add(BtnCalendarioPaciente);
		tblPacientes.getColumnModel().getColumn(0).setPreferredWidth(232);
		tblPacientes.getColumnModel().getColumn(1).setPreferredWidth(153);
		tblPacientes.getColumnModel().getColumn(2).setPreferredWidth(112);
		tblPacientes.getColumnModel().getColumn(3).setPreferredWidth(124);
		
		lblSegundos = new JLabel("Atualizará em:");
		lblSegundos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSegundos.setBounds(389, 11, 134, 14);
		contentPane_1.add(lblSegundos);
		
		setLocationRelativeTo(null);
	}
}
