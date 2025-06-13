package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.Font;

public class SobreWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame Window;

	public SobreWindow(JFrame Window) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
	
				fecharJanela();
			}
		});
		
		this.initComponents();
		this.Window = Window;
	}

	private void fecharJanela() {
		
		this.dispose();
		this.Window.setVisible(true);
		
	}
	
	private void finalizarAplicacao() {
		
		System.exit(0);
	}	
	
	private void initComponents() {
		
		setTitle("Sobre o aplicativo");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 111);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Olá, esse aplicativo foi feito por Leonardo e Leodocir.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 0, 357, 50);
		contentPane.add(lblNewLabel);
		
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
		
		setLocationRelativeTo(null);
	}
}
