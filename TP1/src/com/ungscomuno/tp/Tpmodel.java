package com.ungscomuno.tp;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Tpmodel {

	private int cantidadLucesPorLado;
	
	private JFrame pFrame;
	private JSpinner jSpinner;
	private GridLayout gridLayout;
	private JButton  [][] luces;
	private ImageIcon luzOn;
	private ImageIcon luzOff;
	private boolean gameStart;
	
	public Tpmodel(JFrame frame, JSpinner spinner, GridLayout gridLayout) {
		
		this.pFrame = frame;
		this.jSpinner = spinner;
		this.gridLayout = gridLayout;
		this.gameStart = false;
		
		this.cantidadLucesPorLado = 4;
	
		jSpinner.addChangeListener(new ChangeListener() {	
			@Override
			public void stateChanged(ChangeEvent e) {
				modificarDificultad((int) jSpinner.getValue());
			}
		});
		
	
		
		cargarImagenes();
		crearLucesYAniadir(cantidadLucesPorLado);
	}
	
	private void cargarImagenes() {
		try {
			Image imgOn = ImageIO.read(getClass().getResource("foco_on.png"));
			Image imgOff = ImageIO.read(getClass().getResource("foco_off.png"));

			luzOn = new ImageIcon(imgOn);
			luzOff = new ImageIcon(imgOff);
			
		} catch (Exception ex) {
			System.out.println(ex);
		}   
	}

	private void crearLucesYAniadir(int cantidad) {
		
		Random rn = new Random();
		
		luces = null;
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
				
				setListenerBotones(luces[fila][columna], fila, columna);
				pFrame.add(luces[fila][columna]);			
			}
		}
	}
	
	private void setListenerBotones(JButton button, int fila, int columna) {
		JButton b = button;
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				luzOnOff(b, fila, columna);
				comprobarJuegoGanado();
			}
		});
	}
	
	private void luzOnOff(JButton button, int fila, int columna) {
		
		disableSpinner();
		
		switchLuces(fila, columna);
		switchLuces(fila-1, columna);
		switchLuces(fila+1, columna);
		switchLuces(fila, columna-1);
		switchLuces(fila, columna+1);	
		
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
			mostrarMensajeYReiniciar("Juego Ganado!", "Juego Terminado");
		
	}
	
	private void borrarLucesDelFrame() {

		for(int fila = 0; fila < luces.length; fila ++) {
			for(int columna = 0;  columna < luces[fila].length; columna ++) {
				pFrame.remove(luces[fila][columna]);
			}
		}

	}
	
	private void aumentarColumnasYFilas(int lucesPorLado) {
		gridLayout.setColumns(lucesPorLado);
		gridLayout.setRows(lucesPorLado);
	}
	
	private void recrearLuces(int lucesPorLado) {	
		aumentarColumnasYFilas(lucesPorLado);
		borrarLucesDelFrame();
		crearLucesYAniadir(lucesPorLado);
		modificarTamanioFrame(lucesPorLado);
	}
	
	/**
	 * 
	 * @param lucesPorLado
	 *  aumentamos un el tamaño del frame para que sea mas facil de ver,
	 *  al haber mas botones.
	 */
	private void modificarTamanioFrame(int lucesPorLado) {
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
	
	private void mostrarMensajeYReiniciar(String msj, String titulo) {
		int result = JOptionPane.showConfirmDialog(pFrame, msj, titulo, JOptionPane.INFORMATION_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			recrearLuces(cantidadLucesPorLado);
		}
	}
	
	private void disableSpinner() {
		jSpinner.setEnabled(false);
	}
}
