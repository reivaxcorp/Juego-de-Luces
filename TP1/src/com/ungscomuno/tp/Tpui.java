package com.ungscomuno.tp;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;


/**
 * 
 * @author reivacorp
 *
 */
public class Tpui {

	private JFrame frame;
	private Tpmodel model;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tpui window = new Tpui();
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
	public Tpui() {
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
		
		new Tpmodel(frame, spinner, gridLayout);
		
		JButton jugarBtn = new JButton("Jugar");
		menuBar.add(jugarBtn);
		frame.getContentPane().setLayout(gridLayout);
	
		
	}
}
