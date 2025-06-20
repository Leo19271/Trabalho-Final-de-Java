package gui;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.Exame;
import entities.TipoExame;
import service.ExameService;
import service.TipoExameService;

import java.awt.Font;

public class CalendarioExamesWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tblExames;
    private JTextField txtData;
    private JComboBox<TipoExame> cmbTiposExame;
    private ExameService exameService;
    private TipoExameService tipoExameService;
    private boolean salvarThreadRodando;
    private int segundos;
    private JLabel lblSegundos;
    private StartWindow startWindow;

    public CalendarioExamesWindow(StartWindow startWindow) {
        this.salvarThreadRodando = true;
        this.segundos = 10;
        this.startWindow = startWindow;
        this.exameService = new ExameService();
        this.tipoExameService = new TipoExameService();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fecharJanela();
            }
        });

        initComponents();
        iniciarThread();
        carregarTiposExame();
        buscarExames();
    }

    private void iniciarThread() {
        Thread salvarThread = new Thread(() -> {
            while (salvarThreadRodando) {
                try {
                    for (int i = 0; i < 10; i++) {
                        lblSegundos.setText(segundos + "s");
                        Thread.sleep(1000);
                        segundos--;
                    }
                    segundos = 10;
                    salvarExamesRealizados();
                    buscarExames();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        salvarThread.setDaemon(true);
        salvarThread.start();
    }
    
    private void gerarRelatorioTxt() {
        String caminhoArquivo = "relatorio_exames.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            List<Exame> listaExames = exameService.buscarTodosExames();

            writer.write("Relatório Geral de Exames");
            writer.write("\n===========================\n\n");

            for (Exame exame : listaExames) {
                writer.write(exame + "\n");
            }

            JOptionPane.showMessageDialog(this, "Relatório gerado em: " + caminhoArquivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Falha ao gerar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTiposExame() {
        try {
            List<TipoExame> tipos = tipoExameService.buscarTodos();
            cmbTiposExame.addItem(null);
            for (TipoExame tipo : tipos) {
                cmbTiposExame.addItem(tipo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tipos de exame: " + e.getMessage());
        }
    }

    private void buscarExames() {
        try {
            String dataStr = txtData.getText().trim();
            TipoExame tipoSelecionado = (TipoExame) cmbTiposExame.getSelectedItem();
            List<Exame> exames;

            if (!dataStr.isEmpty() && tipoSelecionado != null) {
                exames = exameService.buscarExamesPorDataETipo(dataStr, tipoSelecionado.getNome());
            } else if (!dataStr.isEmpty()) {
                exames = exameService.buscarExamesPorData(dataStr);
            } else if (tipoSelecionado != null) {
                exames = exameService.buscarExamesPorTipo(tipoSelecionado.getNome());
            } else {
                exames = exameService.buscarTodosExames();
            }

            DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();
            modelo.setRowCount(0);

            for (Exame exame : exames) {
                modelo.addRow(new Object[] {
                    exame.getIdExame(),
                    exame.getPaciente().getNome(),
                    exame.getMedico().getNome(),
                    exame.getDataRealizacao().toString(),
                    exame.getTipoExame().getNome(),
                    exame.isRealizado()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar exames: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
	

    private void salvarExamesRealizados() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idExame = (Integer) modelo.getValueAt(i, 0);
                boolean realizado = (Boolean) modelo.getValueAt(i, 5);

                exameService.atualizarStatusRealizado(idExame, realizado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar exames: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void apagarExame() {
        int selectedRow = tblExames.getSelectedRow();

        if (selectedRow >= 0) {
            int idExame = (Integer) tblExames.getValueAt(selectedRow, 0);

            int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o exame?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (opcao == JOptionPane.YES_OPTION) {
                try {
                    exameService.excluirExame(idExame);
                    buscarExames();
                    JOptionPane.showMessageDialog(this, "Exame apagado com sucesso.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao apagar exame: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um exame na tabela.", "Nenhum exame selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fecharJanela() {
        this.dispose();
        startWindow.setVisible(true);
        salvarThreadRodando = false;
    }

    private void initComponents() {
        setTitle("Calendário de Exames");
        setResizable(false);
        setBounds(100, 100, 800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblData = new JLabel("Data (YYYY-MM-DD):");
        lblData.setBounds(20, 10, 150, 20);
        getContentPane().add(lblData);

        txtData = new JTextField();
        txtData.setBounds(150, 10, 120, 20);
        getContentPane().add(txtData);

        JLabel lblTipo = new JLabel("Tipo do Exame:");
        lblTipo.setBounds(300, 10, 100, 20);
        getContentPane().add(lblTipo);

        cmbTiposExame = new JComboBox<>();
        cmbTiposExame.setBounds(400, 10, 150, 20);
        getContentPane().add(cmbTiposExame);

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
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(580, 10, 100, 20);
        btnBuscar.addActionListener(e -> buscarExames());
        getContentPane().add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 750, 300);
        getContentPane().add(scrollPane);

        tblExames = new JTable();
        DefaultTableModel modelo = new DefaultTableModel(
        	    new Object[][] {},
        	    new String[] { "ID", "Paciente", "Médico", "Data", "Tipo", "Realizado" }
        	) {
				private static final long serialVersionUID = 1L;

				@Override
        	    public Class<?> getColumnClass(int columnIndex) {
        	        if (columnIndex == 5) { 
        	            return Boolean.class;
        	        }
        	        return String.class;
        	    }

        	    @Override
        	    public boolean isCellEditable(int row, int column) {
        	        return column == 5;
        	    }
        	};
        	tblExames.setModel(modelo);
        scrollPane.setViewportView(tblExames);

        JButton btnApagar = new JButton("Apagar Exame");
        btnApagar.setBounds(20, 370, 150, 30);
        btnApagar.addActionListener(e -> apagarExame());
        getContentPane().add(btnApagar);

        JLabel lblAtualizacao = new JLabel("Atualiza realizados em:");
        lblAtualizacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblAtualizacao.setBounds(600, 370, 150, 20);
        getContentPane().add(lblAtualizacao);

        lblSegundos = new JLabel("");
        lblSegundos.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSegundos.setBounds(740, 370, 50, 20);
        getContentPane().add(lblSegundos);
        
        JButton btnGerarTxtDos = new JButton("Gerar txt dos Exames");
        btnGerarTxtDos.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        
        		gerarRelatorioTxt();
        	}
        });
        btnGerarTxtDos.setBounds(278, 370, 202, 30);
        getContentPane().add(btnGerarTxtDos);
    }
}
