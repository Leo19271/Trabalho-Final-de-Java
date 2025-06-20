package gui;

import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.Consulta;
import entities.Exame;
import entities.Medico;
import service.ConsultaService;
import service.ExameService;
import java.awt.Font;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CalendarioMedicoWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private Medico medico;
    private MedicosWindow medicosWindow;
    private JTable tblExames;
    private JTextField txtData;
    private ExameService exameService;
    private boolean salvarThreadRodando;
    private int segundos;
    private JLabel lblSegundos;
    private JTable tblConsultas;
    private ConsultaService consultaService;

    
    public CalendarioMedicoWindow(MedicosWindow medicosWindow, Medico medico) {
        this.medico = medico;
        this.medicosWindow = medicosWindow;
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

        this.initComponents();

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
            	
                exames = exameService.buscarExamesPorMedicoEData(medico.getNome(), dataStr);
            } else {
                exames = exameService.buscarExamesPorMedico(medico.getNome());
            }

            DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();
            modelo.setRowCount(0);

            for (Exame exame : exames) {
                modelo.addRow(new Object[] {
                		exame.getIdExame(),
                        exame.getPaciente().getNome(),
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar exames: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        medicosWindow.setVisible(true);
        
		this.salvarThreadRodando = false;
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
    
    private void buscarConsultas() {
        try {
            String dataStr = txtData.getText();
            List<Consulta> consultas;

            if (!dataStr.isEmpty()) {
                consultas = consultaService.buscarConsultasPorMedicoEData(medico.getNome(), dataStr);
            } else {
                consultas = consultaService.buscarConsultasPorMedico(medico.getNome());
            }

            DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
            modelo.setRowCount(0);

            for (Consulta consulta : consultas) {
                modelo.addRow(new Object[] {
                    consulta.getIdConsulta(),
                    consulta.getPaciente().getNome(),
                    consulta.getHoraConsulta().toString(),
                    consulta.isRealizada()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar consultas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gerarTxtExames() {
    	
        String caminhoArquivo = "relatorio_exames_do_medico.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
        	
            List<Exame> listaExames = exameService.buscarExamesPorMedico(this.medico.getNome());

    
            writer.write("Relatório de Exames do Médico: " + this.medico.getNome());
            writer.write("\n=============================\n\n");

            for (Exame exame : listaExames) {
            	
            	exame.setMedico(this.medico);
            	
                writer.write(exame + "\n");
            }

            JOptionPane.showMessageDialog(this, "Relatório gerado em: " + caminhoArquivo);

        } catch (Exception e) {
        	
            JOptionPane.showMessageDialog(this, "Falha ao gerar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void gerarTxtConsultas() {
        
        String caminhoArquivo = "relatorio_consultas_do_medico.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            
            List<Consulta> listaConsultas = consultaService.buscarConsultasPorMedico(this.medico.getNome());

            writer.write("Relatório de Consultas do Médico: " + this.medico.getNome());
            writer.write("\n=============================\n\n");

            for (Consulta consulta : listaConsultas) {
                
                consulta.setMedico(this.medico);
                
                writer.write(consulta + "\n");
            }

            JOptionPane.showMessageDialog(this, "Relatório de consultas gerado em: " + caminhoArquivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Falha ao gerar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
    	
        setTitle("Calendário do médico - " + medico.getNome());
		setResizable(false);

        setBounds(100, 100, 657, 576);
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
        btnBuscar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		buscarExames();
        		buscarConsultas();
        	}
        });
        btnBuscar.setBounds(265, 10, 100, 20);
        getContentPane().add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 78, 600, 138);
        getContentPane().add(scrollPane);

        tblExames = new JTable();
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Paciente", "Data", "Tipo", "Realizado"}
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
        tblExames.setModel(model);

        tblExames.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblExames.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblExames.getColumnModel().getColumn(2).setPreferredWidth(180);
        tblExames.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblExames.getColumnModel().getColumn(4).setPreferredWidth(80);
        scrollPane.setViewportView(tblExames);

        JButton btnApagar = new JButton("Apagar Exame");
        btnApagar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		apagarExame();
        	}
        });
        btnApagar.setBounds(20, 229, 150, 30);
        
        getContentPane().add(btnApagar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		fecharJanela();
        	}
        });
        btnVoltar.setBounds(470, 460, 150, 30);
        
        getContentPane().add(btnVoltar);
        
        JLabel lblNewLabel = new JLabel("Atualizará os realizados em :");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(387, 11, 169, 17);
        getContentPane().add(lblNewLabel);
        
        lblSegundos = new JLabel("");
        lblSegundos.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSegundos.setBounds(563, 11, 45, 17);
        getContentPane().add(lblSegundos);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(22, 303, 598, 146);
        getContentPane().add(scrollPane_1);
        
        tblConsultas = new JTable();
        scrollPane_1.setViewportView(tblConsultas);
        tblConsultas.setModel(new DefaultTableModel(
        	    new Object[][] {},
        	    new String[] {"ID", "Paciente", "Data da Consulta", "Realizado"}
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
        	});
        
        tblConsultas.getColumnModel().getColumn(0).setPreferredWidth(45);
        tblConsultas.getColumnModel().getColumn(1).setPreferredWidth(210);
        tblConsultas.getColumnModel().getColumn(2).setPreferredWidth(270);
        tblConsultas.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        JButton btnApagarConsulta = new JButton("Apagar Consulta");
        btnApagarConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		apagarConsulta();
        	}
        });
        btnApagarConsulta.setBounds(20, 460, 150, 30);
        getContentPane().add(btnApagarConsulta);
        
        JButton btnGerarPdfDosExames = new JButton("Gerar txt dos Exames");
        btnGerarPdfDosExames.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		gerarTxtExames();
        	}
        });
        btnGerarPdfDosExames.setBounds(209, 229, 212, 30);
        getContentPane().add(btnGerarPdfDosExames);
        
        JButton btnGerarPdfDasConsultas = new JButton("Gerar txt das Consultas");
        btnGerarPdfDasConsultas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		gerarTxtConsultas();
        	}
        });
        btnGerarPdfDasConsultas.setBounds(209, 460, 212, 30);
        getContentPane().add(btnGerarPdfDasConsultas);
        
        JLabel lblExames = new JLabel("Exames:");
        lblExames.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblExames.setBounds(30, 41, 108, 26);
        getContentPane().add(lblExames);
        
        JLabel lblConsultas = new JLabel("Consultas:");
        lblConsultas.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblConsultas.setBounds(30, 270, 108, 26);
        getContentPane().add(lblConsultas);
        
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