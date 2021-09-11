package com.ungscomuno.tp;


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;





public class Tpmodel {

	private int cantidadLucesPorLado;
	
	private JFrame pFrame;
 	private GridLayout gridLayout;
	private JButton  [][] luces;
	private ImageIcon luzOn;
	private ImageIcon luzOff;
 	private boolean empezarJuego;
	private static final String NOMBRE_ARCHIVO_FOCO_ON = "foco_on.png";
	private static final String NOMBRE_ARCHIVO_FOCO_OFF = "foco_off.png";
	private static final String NOMBRE_ARCHIVO_CARGA_JUEGO = "loading.png";
	private static final String TITULO_EMPEZAR_JUEGO = "Empezar Juego";
	private static final String REINICIAR_JUEGO_MSJ = "¿Desea reiniciar el Juego?";
	private static final String JUEGO_GANADO_TITULO = "Juego Ganado!";
	private static final int CANTIDAD_VECES_RANDOM = 6;


	public Tpmodel() {}
	
	public Tpmodel(JFrame frame, GridLayout gridLayout) {
		
		this.pFrame = frame;
		this.gridLayout = gridLayout;
		this.cantidadLucesPorLado = 4;
		this.empezarJuego = false;
		
	    luzOn = cargarImagenes(NOMBRE_ARCHIVO_FOCO_ON);
	    luzOff = cargarImagenes(NOMBRE_ARCHIVO_FOCO_OFF);
		dialogoIniciarJuego();
	}
	
	
	public ImageIcon cargarImagenes(String nombreImagen) {
		
		Image img = null;
 
		try {
			img = ImageIO.read(getClass().getResource(nombreImagen));

		} catch (Exception ex) {
			 throw new IllegalArgumentException();
		}   
		
		return new ImageIcon(img);
	}

	public void crearLucesYAniadir(int lucesPorLado) {
		
		if(esAnchoDeLucesValido(lucesPorLado) == false) {
			throw new IllegalArgumentException();
		}
		
 		
		luces = null;
		luces = new JButton[lucesPorLado][lucesPorLado];
		for(int fila = 0; fila < luces.length; fila ++) {
			for(int columna = 0;  columna < luces[fila].length; columna ++) {
				luces[fila][columna] = new JButton();
		
					luces[fila][columna].setIcon(luzOn);
					luces[fila][columna].setBackground(Color.WHITE);
		
				aniadirEventoDeLuz(luces[fila][columna], fila, columna);
				pFrame.add(luces[fila][columna]);			
			}
		}
	}
  
	private LinkedList<FilaColumna> dameLucesAleatorias() {
		
		LinkedList<FilaColumna> filasColumnas = new LinkedList<FilaColumna>();

		Random filaRandon = new Random(); 
		Random columnaRandom = new Random();

		for(int i = 0; i < CANTIDAD_VECES_RANDOM; i++) {
			filasColumnas.add(new FilaColumna<Integer, Integer>(
					filaRandon.nextInt(luces.length -1), 
					columnaRandom.nextInt(luces[0].length -1)
					));
		}
		return filasColumnas;
		
	}
	
	private void simularClickAleatorios() {
 		
		LinkedList<FilaColumna> filasColumnas = dameLucesAleatorias();
 				
	
		/**
		 * Muestra al usuario la secuancia aleatoria. 
		 * En un nuevo hilo para no bloquear el hilo principal.
		 */
		 new Thread() {
			    public void run() {
			    	
			    	Iterator<FilaColumna> iterator = filasColumnas.iterator();
			    	
			        while(iterator.hasNext()) {
						
						try {
							TimeUnit.MILLISECONDS.sleep(300);
							FilaColumna fl = iterator.next();
							apagarYPrenderLuces((int)fl.getFila(), (int)fl.getColumna());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			        empezarJuego = true;
			    }  
			}.start();
	}
	
	private void aniadirEventoDeLuz(JButton button, int fila, int columna) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (empezarJuego == true) {
					apagarYPrenderLuces(fila, columna);
					comprobarJuegoGanado();
				}
			}
		});
	}
 
	
	private void apagarYPrenderLuces(int fila, int columna) {
		
		prenderYApagarLuces(fila, columna);
		prenderYApagarLuces(fila-1, columna);
		prenderYApagarLuces(fila+1, columna);
		prenderYApagarLuces(fila, columna-1);
		prenderYApagarLuces(fila, columna+1);	
		
	}
	
	private void prenderYApagarLuces(int f, int c){
			
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
			dialogoReiniciarJuego();
		
	}
	


	
	/**
	 *  aumentamos un el tamaño del frame para que sea mas facil de ver,
	 *  al haber mas botones.
	 */
	private void modificarTamanioFrame(int lucesPorLado) {
		 pFrame.setSize(900+lucesPorLado, 600+lucesPorLado);
	}
	
  
	private void dialogoReiniciarJuego() {
	
		int result = JOptionPane.showConfirmDialog(pFrame, JUEGO_GANADO_TITULO, REINICIAR_JUEGO_MSJ, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			simularClickAleatorios();
		} else if (result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
		
	}
	
	private void dialogoIniciarJuego() {
	
		JSpinner spinner = new JSpinner();
		spinner.setMaximumSize(new Dimension(50, 50));
		spinner.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		spinner.setToolTipText("Multiplicador dificultad");
		spinner.setModel(new SpinnerNumberModel(4, 4, 8, 2));
		JLabel lblNewLabel = new JLabel("Luces por lado (Dificultad)");

		JComponent[] inputs = new JComponent[] {
				lblNewLabel,
				spinner 
		};
		
		int result = JOptionPane.showConfirmDialog(null, inputs, TITULO_EMPEZAR_JUEGO, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			
			if(spinner.getValue() != null) {
				cantidadLucesPorLado = (int) spinner.getValue();
				gridLayout.setColumns(cantidadLucesPorLado);
				gridLayout.setRows(cantidadLucesPorLado);
			}
			
			modificarTamanioFrame(cantidadLucesPorLado);
			crearLucesYAniadir(cantidadLucesPorLado);
			simularClickAleatorios();
		} else {
			  System.exit(0);
		}
	}
 
	
	public boolean esAnchoDeLucesValido(int cantidadLucesLado) {
		
		if(cantidadLucesLado == 4) {
			return true;
		} else if (cantidadLucesLado == 6) {
			return true;
		} else if (cantidadLucesLado == 8) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public int getLucesPorLado() {
		return cantidadLucesPorLado;
	}
}
