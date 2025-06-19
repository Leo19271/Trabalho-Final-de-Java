package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import entities.TipoExame;
import service.TipoExameService;

public class TipoExameWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private StartWindow startWindow;
    private JTable table;
    private TipoExameService tipoExameService;

    public TipoExameWindow(StartWindow startWindow) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fecharJanela();
            }
        });

        this.tipoExameService = new TipoExameService();
        this.initComponents();
        this.startWindow = startWindow;

        this.buscarTiposExame();
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

    private void abrirCadastrarTipoExameWindow() {
        CadastrarTipoExameWindow cadastrarTipoExameWindow = new CadastrarTipoExameWindow(this);
        cadastrarTipoExameWindow.setVisible(true);
        this.setVisible(false);
    }

    private void abrirEditarTipoExameWindow(TipoExame tipoExame) {
        EditarTipoExameWindow editarTipoExameWindow = new EditarTipoExameWindow(this, tipoExame);
        editarTipoExameWindow.setVisible(true);
        this.setVisible(false);
    }

    protected void buscarTiposExame() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) table.getModel();
            modelo.fireTableDataChanged();
            modelo.setRowCount(0);

            List<TipoExame> tiposExame = tipoExameService.buscarTodos();

            for (TipoExame tipoExame : tiposExame) {
                modelo.addRow(new Object[] {
                    tipoExame.getNome(),
                    tipoExame.getValor(),
                    tipoExame.getOrientacoes()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Tipos de Exame da base de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private TipoExame buscarTipoExamePorNome(String nome) {
        try {
        	
            return tipoExameService.buscarPorNome(nome);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Tipo de Exame.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void editarTipoExame() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
            String nome = (String) table.getValueAt(selectedRow, 0);
            TipoExame tipoExame = buscarTipoExamePorNome(nome);
            abrirEditarTipoExameWindow(tipoExame);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um tipo de exame na tabela para editar.", "Nenhum selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void apagarTipoExame() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
            try {
                int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar esse tipo de exame?", "Confirmação", JOptionPane.YES_NO_CANCEL_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    String nome = (String) table.getValueAt(selectedRow, 0);
                    TipoExame tipoExame = buscarTipoExamePorNome(nome);
                    tipoExameService.excluirTipoExame(tipoExame);
                    JOptionPane.showMessageDialog(null, "Tipo de exame apagado com sucesso!");
                    buscarTiposExame();
                } else if (opcao == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "Escolha não confirmada.");
                } else {
                    JOptionPane.showMessageDialog(null, "Operação cancelada.");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao apagar o tipo de exame.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um tipo de exame na tabela para apagar.", "Nenhum selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void initComponents() {
        setTitle("Tipos de Exame");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 487, 447);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 451, 314);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nome", "Valor", "Orientações" }
        ));

        JButton btnCadastrarExame = new JButton("Cadastrar Exame");
        btnCadastrarExame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCadastrarTipoExameWindow();
            }
        });
        btnCadastrarExame.setBounds(318, 337, 143, 38);
        contentPane.add(btnCadastrarExame);

        JButton btnEditarExame = new JButton("Editar Exame");
        btnEditarExame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarTipoExame();
            }
        });
        btnEditarExame.setBounds(156, 337, 145, 38);
        contentPane.add(btnEditarExame);

        JButton btnApagarExame = new JButton("Apagar Exame");
        btnApagarExame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarTipoExame();
            }
        });
        btnApagarExame.setBounds(10, 337, 136, 38);
        contentPane.add(btnApagarExame);

        table.getColumnModel().getColumn(0).setPreferredWidth(119);
        table.getColumnModel().getColumn(2).setPreferredWidth(297);

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