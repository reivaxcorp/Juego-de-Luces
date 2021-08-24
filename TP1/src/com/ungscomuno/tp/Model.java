package com.ungscomuno.tp;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Model {

	private int cantidadLucesPorLado;
	
	private JFrame pFrame;
	private JSpinner jSpinner;
	private GridLayout gridLayout;
	private JButton  [][] luces;
	private ImageIcon luzOn;
	private ImageIcon luzOff;
	
	public Model(JFrame frame, JSpinner spinner, GridLayout gridLayout) {
		
		this.pFrame = frame;
		this.jSpinner = spinner;
		this.gridLayout = gridLayout;
		
		this.cantidadLucesPorLado = 4;
		
		
		
		jSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				modificarDificultad((int) jSpinner.getValue());
			}
		});
		
		crearImagen();
		inicializarLuces(cantidadLucesPorLado);
	}
	
	private void crearImagen() {
		try {
			Image imgOn = ImageIO.read(getClass().getResource("foco_on.png"));
			Image imgOff = ImageIO.read(getClass().getResource("foco_off.png"));

			luzOn = new ImageIcon(imgOn);
			luzOff = new ImageIcon(imgOff);
			
		} catch (Exception ex) {
			System.out.println(ex);
		}   
	}

	private void inicializarLuces(int cantidad) {
		
		Random rn = new Random();
		
		luces = new JButton[cantidad][cantidad];
		for(int fila = 0; fila < luces.length; fila ++) {
			for(int columna = 0;  columna < luces[fila].length; columna ++) {
				luces[fila][columna] = new JButton();
				
		 
				if(rn.nextBoolean()) {
					luces[fila][columna].setIcon(luzOn);
					luces[fila][columna].setBackground(Color.WHITE);
				}else {
					luces[fila][columna].setIcon(luzOff);
					luces[fila][columna].setBackground(Color.BLACK);
				}
				
				setListeners(luces[fila][columna], fila, columna);
				pFrame.add(luces[fila][columna]);			
			}
		}
	}
	private void setListeners(JButton button, int fila, int columna) {
		JButton b = button;
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				apagarLuz(b, fila, columna);
			}
		});
	}
	private void apagarLuz(JButton button, int fila, int columna) {
		
		disableSpinner();
		
		switchLuces(fila, columna);
		
	
		switchLuces(fila-1, columna);
		switchLuces(fila+1, columna);
		switchLuces(fila, columna-1);
		switchLuces(fila, columna+1);
		
		comprobarJuegoGanado();
	}
	
	private void switchLuces(int f, int c){
			
		if(f >= luces.length || f < 0) {
			return;
		}
		if(c >= luces[0].length || c < 0) {
			return;
		}
		if(luces[f][c].getIcon() == luzOn) {
			luces[f][c].setIcon(luzOff);
			luces[f][c].setBackground(Color.BLACK);
		} else {
			luces[f][c].setIcon(luzOn);
			luces[f][c].setBackground(Color.WHITE);
		}
		
	}
	
	private void comprobarJuegoGanado() {
		
		boolean lucesOff = true;
		
		for(int fila = 0; fila < luces.length; fila ++) {
			for(int columna = 0;  columna < luces[fila].length; columna ++) {
				lucesOff = lucesOff && luces[fila][columna].getIcon() == luzOff;
			}
		}
		if(lucesOff)
			mostrarMensaje("Juego Ganado!", "Juego Terminado");
		
	}
	

	
	private void recrearLuces(int lucesPorLado) {
		
		gridLayout.setColumns(lucesPorLado);
		gridLayout.setRows(lucesPorLado);
		
	
		 for(int fila = 0; fila < luces.length; fila ++) {
			 for(int columna = 0;  columna < luces[fila].length; columna ++) {
				 pFrame.remove(luces[fila][columna]);
			 }
			}
			
		 luces = null;
		 inicializarLuces(lucesPorLado);
		 pFrame.setSize(900+lucesPorLado, 600+lucesPorLado);
		
	}
	
	private void modificarDificultad(int nuevaDificultad) {
		
		if(nuevaDificultad > cantidadLucesPorLado) {
			cantidadLucesPorLado  = nuevaDificultad;
			recrearLuces(cantidadLucesPorLado);
		}else {
			cantidadLucesPorLado = nuevaDificultad;
			recrearLuces(cantidadLucesPorLado);
		}
	}
	
	private void mostrarMensaje(String msj, String titulo) {
		int result = JOptionPane.showConfirmDialog(pFrame, msj, titulo, JOptionPane.INFORMATION_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			recrearLuces(cantidadLucesPorLado);
		}
	}
	
	private void disableSpinner() {
		jSpinner.setEnabled(false);
	}
}
