package com.ungscomuno.tp;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Tp1 {

	private JFrame frame;
	private Model model;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tp1 window = new Tp1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tp1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame("TP1");
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();

		frame.setJMenuBar(menuBar);
		
		JLabel lblNewLabel = new JLabel("Luces por lado");
		menuBar.add(lblNewLabel);
		
		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("Multiplicador dificultad");
		spinner.setModel(new SpinnerNumberModel(4, 4, 8, 2));
		menuBar.add(spinner);
		
		GridLayout gridLayout = new GridLayout(4, 4, 0, 0);
		
		new Model(frame, spinner, gridLayout);
		frame.getContentPane().setLayout(gridLayout);
	
		
	}
}
