package gui;

import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.Consulta;
import service.ConsultaService;

import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class CalendarioConsultasWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tblConsultas;
    private JTextField txtData;
    private ConsultaService consultaService;
    private boolean salvarThreadRodando;
    private int segundos;
    private JLabel lblSegundos;
    private StartWindow startWindow;
    
    public CalendarioConsultasWindow(StartWindow startWindow) {
        this.salvarThreadRodando = true;
        this.segundos = 10;
        this.startWindow = startWindow;
        this.consultaService = new ConsultaService();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fecharJanela();
            }
        });

        initComponents();
        iniciarThread();
        buscarConsultas();
    }

    private void iniciarThread() {
    	
    	Thread salvarThread = new Thread(new Runnable() {
    	    @Override
    	    public void run() {
    	        while (salvarThreadRodando) {
    	            try {
    	                for (int i = 0; i < 10; i++) {
    	                    lblSegundos.setText(String.valueOf(segundos) + "s");
    	                    Thread.sleep(1000);
    	                    segundos--;
    	                }
    	                segundos = 10;
    	                salvarConsultasRealizadas();
    	            } catch (InterruptedException e) {
    	                Thread.currentThread().interrupt();
    	                break;
    	            }
    	        }
    	    }
    	});
    	salvarThread.setDaemon(true);
    	salvarThread.start();
    }

    private void buscarConsultas() {
        try {
            String dataStr = txtData.getText();
            List<Consulta> consultas;

            if (!dataStr.isEmpty()) {
            	
                consultas = consultaService.buscarConsultasPorData(dataStr);
            } else {
            	
                consultas = consultaService.buscarTodasConsultas();
            }

            DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
            modelo.setRowCount(0);

            for (Consulta consulta : consultas) {
                modelo.addRow(new Object[] {
                    consulta.getIdConsulta(),
                    consulta.getPaciente().getNome(),
                    consulta.getMedico().getNome(),
                    consulta.getHoraConsulta().toString(),
                    consulta.isRealizada()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar consultas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarConsultasRealizadas() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idConsulta = (Integer) modelo.getValueAt(i, 0);
                boolean realizado = (Boolean) modelo.getValueAt(i, 4);

                consultaService.atualizarStatusRealizado(idConsulta, realizado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar consultas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
	

    private void apagarConsulta() {
        int selectedRow = tblConsultas.getSelectedRow();

        if (selectedRow >= 0) {
            int idConsulta = (Integer) tblConsultas.getValueAt(selectedRow, 0);

            int opcao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir a consulta?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (opcao == JOptionPane.YES_OPTION) {
                try {
                    consultaService.excluirConsulta(idConsulta);
                    buscarConsultas();
                    JOptionPane.showMessageDialog(this, "Consulta apagada com sucesso.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao apagar consulta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela.", "Nenhuma consulta selecionada", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void gerarRelatorioTxt() {
        String caminhoArquivo = "relatorio_consultas.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            List<Consulta> listaConsultas = consultaService.buscarTodasConsultas();

            writer.write("Relatório Geral de Consultas");
            writer.write("\n=============================\n\n");

            for (Consulta consulta : listaConsultas) {
                writer.write(consulta + "\n");
            }

            JOptionPane.showMessageDialog(this, "Relatório gerado em: " + caminhoArquivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Falha ao gerar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fecharJanela() {
    	
        this.dispose();
        startWindow.setVisible(true);
        
        salvarThreadRodando = false;
    }

    private void initComponents() {
        setTitle("Calendário Geral de Consultas");
        setResizable(false);
        setBounds(100, 100, 700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

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
        
        JLabel lblData = new JLabel("Data (YYYY-MM-DD):");
        lblData.setBounds(20, 10, 150, 20);
        getContentPane().add(lblData);

        txtData = new JTextField();
        txtData.setBounds(138, 10, 150, 20);
        getContentPane().add(txtData);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(320, 10, 100, 20);
        btnBuscar.addActionListener(e -> {
            buscarConsultas();
        });
        getContentPane().add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 650, 300);
        getContentPane().add(scrollPane);

        tblConsultas = new JTable() {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Só permite editar a coluna de checkboxes (Realizado)
                return column == 4;
            }
        };

        tblConsultas.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "ID", "Paciente", "Médico", "Hora", "Realizado" }
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        });
        scrollPane.setViewportView(tblConsultas);

        JButton btnApagar = new JButton("Apagar Consulta");
        btnApagar.setBounds(20, 370, 150, 30);
        btnApagar.addActionListener(e -> apagarConsulta());
        getContentPane().add(btnApagar);

        JButton btnGerarRelatorio = new JButton("Gerar relatório txt");
        btnGerarRelatorio.setBounds(266, 370, 180, 30);
        btnGerarRelatorio.addActionListener(e -> gerarRelatorioTxt());
        getContentPane().add(btnGerarRelatorio);

        JLabel lblAtualizacao = new JLabel("Atualiza realizados em:");
        lblAtualizacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblAtualizacao.setBounds(459, 9, 150, 20);
        getContentPane().add(lblAtualizacao);

        lblSegundos = new JLabel("");
        lblSegundos.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSegundos.setBounds(601, 10, 50, 20);
        getContentPane().add(lblSegundos);
    }
}