package gui;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.Consulta;
import entities.Exame;
import entities.Paciente;
import service.ConsultaService;
import service.ExameService;
import java.awt.Font;

public class CalendarioPacienteWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private Paciente paciente;
    private PacientesWindow pacientesWindow;
    private JTable tblExames;
    private JTable tblConsultas;
    private JTextField txtData;
    private ExameService exameService;
    private ConsultaService consultaService;
    private boolean salvarThreadRodando;
    private int segundos;
    private JLabel lblSegundos;

    public CalendarioPacienteWindow(PacientesWindow pacientesWindow, Paciente paciente) {
        this.paciente = paciente;
        this.pacientesWindow = pacientesWindow;
        this.salvarThreadRodando = true;
        this.segundos = 10;
        this.exameService = new ExameService();
        this.consultaService = new ConsultaService();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fecharJanela();
            }
        });

        initComponents();
        iniciarThread();
        buscarExames();
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
    	                salvarExamesRealizados();
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

    private void buscarExames() {
        try {
            String dataStr = txtData.getText();
            List<Exame> exames;

            if (!dataStr.isEmpty()) {
                exames = exameService.buscarExamesPorPacienteEData(paciente.getNome(), dataStr);
            } else {
                exames = exameService.buscarExamesPorPaciente(paciente.getNome());
            }

            DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();
            modelo.setRowCount(0);

            for (Exame exame : exames) {
                modelo.addRow(new Object[] {
                    exame.getIdExame(),
                    exame.getMedico().getNome(),
                    exame.getDataRealizacao().toString(),
                    exame.getTipoExame().getNome(),
                    exame.isRealizado()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar exames: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarExamesRealizados() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idExame = (Integer) modelo.getValueAt(i, 0);
                boolean realizado = (Boolean) modelo.getValueAt(i, 4);

                exameService.atualizarStatusRealizado(idExame, realizado);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar exames: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

    private void buscarConsultas() {
        try {
            String dataStr = txtData.getText();
            List<Consulta> consultas;

            if (!dataStr.isEmpty()) {
                consultas = consultaService.buscarConsultasPorPacienteEData(paciente.getNome(), dataStr);
            } else {
                consultas = consultaService.buscarConsultasPorPaciente(paciente.getNome());
            }

            DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
            modelo.setRowCount(0);

            for (Consulta consulta : consultas) {
                modelo.addRow(new Object[] {
                    consulta.getIdConsulta(),
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
                boolean realizado = (Boolean) modelo.getValueAt(i, 3);

                consultaService.atualizarStatusRealizado(idConsulta, realizado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar consultas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
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

    private void fecharJanela() {
        this.dispose();
        pacientesWindow.setVisible(true);
        this.salvarThreadRodando = false;
    }

    private void gerarTxtExames() {
        
        String caminhoArquivo = "relatorio_exames_do_paciente.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            
            List<Exame> listaExames = exameService.buscarExamesPorPaciente(this.paciente.getNome());

            writer.write("Relatório de Exames do Paciente: " + this.paciente.getNome());
            writer.write("\n=============================\n\n");

            for (Exame exame : listaExames) {
                exame.setPaciente(this.paciente);
                writer.write(exame + "\n");
            }

            JOptionPane.showMessageDialog(this, "Relatório de exames gerado em: " + caminhoArquivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Falha ao gerar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarTxtConsultas() {
        
        String caminhoArquivo = "relatorio_consultas_do_paciente.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            
            List<Consulta> listaConsultas = consultaService.buscarConsultasPorPaciente(this.paciente.getNome());

            writer.write("Relatório de Consultas do Paciente: " + this.paciente.getNome());
            writer.write("\n=============================\n\n");

            for (Consulta consulta : listaConsultas) {
                consulta.setPaciente(this.paciente);
                writer.write(consulta + "\n");
            }

            JOptionPane.showMessageDialog(this, "Relatório de consultas gerado em: " + caminhoArquivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Falha ao gerar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initComponents() {
    	
        setTitle("Calendário do paciente - " + paciente.getNome());
		setResizable(false);

        setBounds(100, 100, 653, 548);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblData = new JLabel("Data (YYYY-MM-DD):");
        lblData.setBounds(20, 10, 150, 20);
        getContentPane().add(lblData);

        txtData = new JTextField();
        txtData.setBounds(127, 10, 128, 20);
        getContentPane().add(txtData);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(265, 10, 100, 20);
        btnBuscar.addActionListener(e -> {
            buscarExames();
            buscarConsultas();
        });
        getContentPane().add(btnBuscar);

        // Tabela Exames
        JScrollPane scrollPaneExames = new JScrollPane();
        scrollPaneExames.setBounds(20, 50, 600, 150);
        getContentPane().add(scrollPaneExames);

        tblExames = new JTable();
        DefaultTableModel modelExames = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Médico", "Data", "Tipo", "Realizado"}
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;
                }
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };
        tblExames.setModel(modelExames);

        tblExames.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblExames.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblExames.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblExames.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblExames.getColumnModel().getColumn(4).setPreferredWidth(80);
        scrollPaneExames.setViewportView(tblExames);

        JButton btnApagarExame = new JButton("Apagar Exame");
        btnApagarExame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		apagarExame();
        	}
        });
        btnApagarExame.setBounds(20, 210, 150, 30);
    
        getContentPane().add(btnApagarExame);

        JLabel lblExames = new JLabel("Exames:");
        lblExames.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblExames.setBounds(20, 30, 108, 20);
        getContentPane().add(lblExames);

        // Tabela Consultas
        JScrollPane scrollPaneConsultas = new JScrollPane();
        scrollPaneConsultas.setBounds(20, 270, 600, 150);
        getContentPane().add(scrollPaneConsultas);

        tblConsultas = new JTable();
        DefaultTableModel modelConsultas = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Médico", "Hora", "Realizado"}
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class;
                }
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };
        tblConsultas.setModel(modelConsultas);

        tblConsultas.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblConsultas.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblConsultas.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblConsultas.getColumnModel().getColumn(3).setPreferredWidth(80);
        scrollPaneConsultas.setViewportView(tblConsultas);

        JButton btnApagarConsulta = new JButton("Apagar Consulta");
        btnApagarConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		apagarConsulta();
        	}
        });
        btnApagarConsulta.setBounds(20, 430, 150, 30);
        
        getContentPane().add(btnApagarConsulta);

        JLabel lblConsultas = new JLabel("Consultas:");
        lblConsultas.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblConsultas.setBounds(20, 245, 108, 20);
        getContentPane().add(lblConsultas);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		fecharJanela();
        	}
        });
        btnVoltar.setBounds(458, 431, 150, 30);
        
        getContentPane().add(btnVoltar);

        JLabel lblNewLabel = new JLabel("Atualizará os realizados em :");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(387, 11, 169, 17);
        getContentPane().add(lblNewLabel);
        
        JButton btnGerarPdfDosExames = new JButton("Gerar txt dos Exames");
        btnGerarPdfDosExames.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		gerarTxtExames();
        	}
        });
        btnGerarPdfDosExames.setBounds(206, 210, 212, 30);
        getContentPane().add(btnGerarPdfDosExames);
        
        JButton btnGerarPdfDasConsultas = new JButton("Gerar txt das Consultas");
        btnGerarPdfDasConsultas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		gerarTxtConsultas();
        	}
        });
        btnGerarPdfDasConsultas.setBounds(206, 431, 212, 30);
        getContentPane().add(btnGerarPdfDasConsultas);

        lblSegundos = new JLabel("");
        lblSegundos.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSegundos.setBounds(563, 11, 45, 17);
        getContentPane().add(lblSegundos);
        
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
    }
    
	private void finalizarAplicacao() {
		
		System.exit(0);
	}

	private void abrirSobre() {
		
		SobreWindow sobreWindow = new SobreWindow(this);
		sobreWindow.setVisible(true);
		
		this.setVisible(false);
	}
}