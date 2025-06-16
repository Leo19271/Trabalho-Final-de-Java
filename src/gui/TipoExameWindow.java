package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

public class TipoExameWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private StartWindow startWindow;
    private JTable table;

    public TipoExameWindow(StartWindow startWindow) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fecharJanela();
            }
        });

        this.initComponents();
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

    private void abrirCadastrarTipoExameWindow() {
        CadastrarTipoExameWindow cadastrarTipoExameWindow = new CadastrarTipoExameWindow(this);
        cadastrarTipoExameWindow.setVisible(true);
        this.setVisible(false);
    }

    private void abrirEditarTipoExameWindow() {
        EditarTipoExameWindow editarTipoExameWindow = new EditarTipoExameWindow(this);
        editarTipoExameWindow.setVisible(true);
        this.setVisible(false);
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
                abrirEditarTipoExameWindow();
            }
        });
        btnEditarExame.setBounds(156, 337, 145, 38);
        contentPane.add(btnEditarExame);

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