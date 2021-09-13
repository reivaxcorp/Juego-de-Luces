package com.ungscomuno.tp;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.management.InstanceNotFoundException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;



public class Tpmodel {

	private int alturaAnchoLuces;
	
	private JFrame pFrame;
 	private GridLayout gridLayout;
 	private JComponent[] dialogoComponentes;
	private JButton  [][] luces;
	private ImageIcon luzPrendida;
	private ImageIcon luzApagada;
 	private boolean empezarJuego;
	public static final String NOMBRE_ARCHIVO_FOCO_ON = "foco_on.png";
	public static final String NOMBRE_ARCHIVO_FOCO_OFF = "foco_off.png";
	public static final String TITULO_EMPEZAR_JUEGO = "Empezar Juego";
	public static final String REINICIAR_JUEGO_MSJ = "Has Ganado!!! \n ¿Desea reiniciar el Juego?";
	public static final String JUEGO_GANADO_TITULO = "Juego Ganado!";
	

	public Tpmodel() {}
	
	public Tpmodel(JFrame frame, GridLayout gridLayout, JComponent[] dialogoComponentes) {
		
		this.pFrame = frame;
		this.gridLayout = gridLayout;
		this.dialogoComponentes = dialogoComponentes;
		this.alturaAnchoLuces = 4;
		this.empezarJuego = false;
		
	    luzPrendida = cargarImagenes(NOMBRE_ARCHIVO_FOCO_ON);
	    luzApagada = cargarImagenes(NOMBRE_ARCHIVO_FOCO_OFF);
		dialogoIniciarJuego();
	}
	
	private void dialogoIniciarJuego() {

		
		int result = JOptionPane.showConfirmDialog(
				null,
				dialogoComponentes,
				TITULO_EMPEZAR_JUEGO, 
				JOptionPane.OK_CANCEL_OPTION
				);
		
		if (result == JOptionPane.OK_OPTION) {
			  prepararComponentes();
		} else {
			  System.exit(0);
		}
	}
	
	public void prepararComponentes() {
		
		try {
			if(dameSpinner().getValue() != null) {
				alturaAnchoLuces = (int) dameSpinner().getValue();
				gridLayout.setColumns(alturaAnchoLuces);
				gridLayout.setRows(alturaAnchoLuces);
			}
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		luces = crearLuces(alturaAnchoLuces, luzApagada);
		agregarLucesAlFrame(luces, pFrame);
		aniadirEventoDeLuz(luces);
		simularClickAleatorios(luces);
	}
	
	 
	private void agregarLucesAlFrame(JButton [][] luces, JFrame pFrame ) {
		
		// Aniadimos al frame
				for(int fila = 0; fila < luces.length; fila ++) {
					for(int columna = 0; columna < luces[0].length; columna ++) {
						pFrame.add(luces[fila][columna]);	
					}
				}
	}
	
	private void aniadirEventoDeLuz(JButton [][] luces) {
		for(int fila = 0; fila < luces.length; fila ++) {
			for(int columna = 0; columna < luces[0].length; columna ++) {
				int pFila = fila;
				int pColumna = columna; 
				luces[fila][columna].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (empezarJuego == true) {
							clickLuz(pFila, pColumna, luces);
							if(comprobarJuegoGanado(luces)) {
								dialogoReiniciarJuego();
							}	
						}
					}
				});
 			}
		}
	}
	

	private void simularClickAleatorios(JButton[][] luces) {
 		// las veces que se presionara en forma aleatoria corresponde tamaño del ancho en horizontal de luces.
		LinkedList<FilaColumna> filasColumnas = obtenerLucesAleatorias(luces, alturaAnchoLuces);
	
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
							clickLuz((int)fl.getFila(), (int)fl.getColumna(), luces);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			        empezarJuego = true; // habilitamos los clicks
			    }  
			}.start();
	}

	public JButton[][] crearLuces(int lucesPorLado, ImageIcon luzApagada) {

		JButton[][] luces = null;

		if (esAnchoDeLucesValido(lucesPorLado) == false) {
			throw new IllegalArgumentException();
		}

		luces = new JButton[lucesPorLado][lucesPorLado];

		for (int fila = 0; fila < luces.length; fila++) {
			for (int columna = 0; columna < luces[fila].length; columna++) {
				luces[fila][columna] = new JButton();
				luces[fila][columna].setIcon(luzApagada);
				luces[fila][columna].setBackground(Color.BLACK);
			}
		}
		return luces;
	}
  
	public LinkedList<FilaColumna> obtenerLucesAleatorias(JButton[][] luces, int cantidadAleatoria) {
		
		LinkedList<FilaColumna> filasColumnas = new LinkedList<FilaColumna>();
			
		Random filaRandon = new Random(); 
		Random columnaRandom = new Random();
		
		for(int i = 0; i < cantidadAleatoria; i++) {
			int filaAleatoria = filaRandon.nextInt(luces.length -1);
			int columnaAleatoria = columnaRandom.nextInt(luces[0].length -1);
			FilaColumna filaColumna = new FilaColumna<Integer, Integer>(
					filaAleatoria, 
					columnaAleatoria
					);
					
				//Se evita que por alguna razon no prenda alguna luz al empezar (poco probable)		
		    	 if(luzYaSeleccionada(filaAleatoria, columnaAleatoria, filasColumnas) == false) {
					 filasColumnas.add(filaColumna);
				 }
	
		}
		return filasColumnas;
		
	}
	
	private boolean luzYaSeleccionada(int fila, int columna, LinkedList<FilaColumna> filasColumnas) {
		for(FilaColumna agregada: filasColumnas) {
			if((int)agregada.getFila() == fila && (int)agregada.getColumna() == columna) {
				return true;
			}
		}
		return false;
	}
	 
	public void clickLuz(int fila, int columna, JButton [][] luces) {
		
		if (esLuzExistente(fila, columna, luces) == false) {
			throw new IllegalArgumentException();
		}
		prenderOApagarLuz(fila, columna, luces); // centro
		prenderOApagarLuz(fila - 1, columna, luces); // izquierda
		prenderOApagarLuz(fila + 1, columna, luces); // derecha
		prenderOApagarLuz(fila, columna - 1, luces); // abajo
		prenderOApagarLuz(fila, columna + 1, luces); // arriba
 
	}
	
	private void prenderOApagarLuz(int f, int c, JButton [][] luces){
			
		if(f >= luces.length || f < 0) {
			return;
		}
		if(c >= luces[0].length || c < 0) {
			return;
		}
		if(luces[f][c].getIcon() == luzPrendida) {
			luces[f][c].setIcon(luzApagada);
			luces[f][c].setBackground(Color.BLACK);
		} else {
			luces[f][c].setIcon(luzPrendida);
			luces[f][c].setBackground(Color.WHITE);
		}
		
	}
	
	public boolean comprobarJuegoGanado(JButton [][] luces) {
		
		boolean lucesOff = true;
		
		for(int fila = 0; fila < luces.length; fila ++) {
			for(int columna = 0;  columna < luces[fila].length; columna ++) {
				lucesOff = lucesOff && luces[fila][columna].getBackground() == Color.BLACK;
			}
		}
		if(lucesOff) {
			return true;
		} else {
			return false;
		}
	}
	
	private void dialogoReiniciarJuego() {
	
		int result = JOptionPane.showConfirmDialog(
				pFrame,
				REINICIAR_JUEGO_MSJ,JUEGO_GANADO_TITULO, 
				JOptionPane.OK_CANCEL_OPTION
				);
		
		if (result == JOptionPane.OK_OPTION) {
			simularClickAleatorios(luces);
		} else if (result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
		
	}
	
	public JSpinner dameSpinner() throws InstanceNotFoundException {
		JSpinner spinner = null;
		for(int i = 0; i < dialogoComponentes.length; i++) {
			if(dialogoComponentes[i] instanceof JSpinner) {
				spinner = (JSpinner) dialogoComponentes[i];
			}
		}
		if(spinner == null) {
			throw new InstanceNotFoundException();
		}
		
		return spinner;
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
	
	public boolean esLuzExistente(int fila, int columna, JButton  [][] luces) {
		if ((fila < 0 || fila > luces.length - 1) || (columna < 0 || columna > luces[0].length - 1)) {
			return false;
		}
		return true;
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
	
}
