package com.ungscomuno.tp;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import java.awt.Dimension;


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
		
		GridLayout gridLayout = new GridLayout(4, 4, 0, 0);
		JSpinner spinner = new JSpinner();
		spinner.setMaximumSize(new Dimension(50, 50));
		spinner.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		spinner.setToolTipText("Multiplicador dificultad");
		spinner.setModel(new SpinnerNumberModel(4, 4, 8, 2));
		JLabel labelInfoDificultad = new JLabel("Luces por lado (Dificultad)");
		JLabel infoGame = new JLabel("Apaga todas las luces para ganar el juego!");
		
		JComponent[] dialogoComponentes = new JComponent[] {
				labelInfoDificultad,
				spinner, 
				infoGame
		};
		
		new Tpmodel(frame, gridLayout, dialogoComponentes);

		frame.getContentPane().setLayout(gridLayout);
	
	}
}
