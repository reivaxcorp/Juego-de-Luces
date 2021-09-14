package com.ungscomuno.tp;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

public class TpmodelTest {

	private Tpmodel tpmodel;
	private ImageIcon luzPrendida;
	private ImageIcon luzApagada;

	@Before
	public void inicializarComponentes() {
		tpmodel = new Tpmodel();
		luzPrendida = tpmodel.cargarImagenes(tpmodel.NOMBRE_ARCHIVO_FOCO_ON);
		luzApagada = tpmodel.cargarImagenes(tpmodel.NOMBRE_ARCHIVO_FOCO_OFF);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cargarImagenInexistente() {
		tpmodel.cargarImagenes("foco_onn.png");
		tpmodel.cargarImagenes("foco_offf.png");

	}

	@Test
	public void comprobarJuegoNoGanado() {
		// setup
		JButton[][] luces_1 = tpmodel.crearLuces(4, luzApagada);
		JButton[][] luces_2 = tpmodel.crearLuces(6, luzApagada);
		JButton[][] luces_3 = tpmodel.crearLuces(8, luzApagada);

		LinkedList<FilaColumna> lucesRandom_1 = tpmodel.obtenerLucesAleatorias(luces_1, 4);
		LinkedList<FilaColumna> lucesRandom_2 = tpmodel.obtenerLucesAleatorias(luces_2, 6);
		LinkedList<FilaColumna> lucesRandom_3 = tpmodel.obtenerLucesAleatorias(luces_3, 8);


		for (int i = 0; i < lucesRandom_1.size(); i++) {
			if(i == 0) continue; // omitimos la primera
				tpmodel.clickLuz((int) lucesRandom_1.get(i).getFila(), (int) lucesRandom_1.get(i).getColumna(),
						luces_1);
		}
		for (int i = 0; i < lucesRandom_2.size(); i++) {
			if(i == 0) continue; // omitimos la primera
				tpmodel.clickLuz((int) lucesRandom_2.get(i).getFila(), (int) lucesRandom_2.get(i).getColumna(),
						luces_2);
		}
		for (int i = 0; i < lucesRandom_3.size(); i++) {
			if(i == 0) continue; // omitimos la primera
				tpmodel.clickLuz((int) lucesRandom_3.get(i).getFila(), (int) lucesRandom_3.get(i).getColumna(),
						luces_3);
		}

	

		// verify
		assertFalse(tpmodel.comprobarJuegoGanado(luces_1));
		assertFalse(tpmodel.comprobarJuegoGanado(luces_2));
		assertFalse(tpmodel.comprobarJuegoGanado(luces_3));
	}

	@Test
	public void comprobarJuegoGanado() {

		// setup
		JButton[][] luces_1 = tpmodel.crearLuces(4, luzApagada);
		JButton[][] luces_2 = tpmodel.crearLuces(6, luzApagada);
		JButton[][] luces_3 = tpmodel.crearLuces(8, luzApagada);

		LinkedList<FilaColumna> lucesRandom_1 = tpmodel.obtenerLucesAleatorias(luces_1, 4);
		LinkedList<FilaColumna> lucesRandom_2 = tpmodel.obtenerLucesAleatorias(luces_2, 6);
		LinkedList<FilaColumna> lucesRandom_3 = tpmodel.obtenerLucesAleatorias(luces_3, 8);

		for (int i = 0; i < lucesRandom_1.size(); i++) {
			tpmodel.clickLuz((int) lucesRandom_1.get(i).getFila(), (int) lucesRandom_1.get(i).getColumna(), luces_1);
			// System.out.println("Fila: " + lucesRandom.get(i).getFila() + " " + "Columna:
			// " + lucesRandom.get(i).getColumna());

		}
		for (int i = 0; i < lucesRandom_2.size(); i++) {
			tpmodel.clickLuz((int) lucesRandom_2.get(i).getFila(), (int) lucesRandom_2.get(i).getColumna(), luces_2);

		}
		for (int i = 0; i < lucesRandom_3.size(); i++) {
			tpmodel.clickLuz((int) lucesRandom_3.get(i).getFila(), (int) lucesRandom_3.get(i).getColumna(), luces_3);

		}

		// exercise
		// volvemos a apagar las luces en orden en que la prendimos
		for (int i = lucesRandom_1.size() - 1; i >= 0; i--) {
			tpmodel.clickLuz((int) lucesRandom_1.get(i).getFila(), (int) lucesRandom_1.get(i).getColumna(), luces_1);

		}
		for (int i = lucesRandom_2.size() - 1; i >= 0; i--) {
			tpmodel.clickLuz((int) lucesRandom_2.get(i).getFila(), (int) lucesRandom_2.get(i).getColumna(), luces_2);

		}
		for (int i = lucesRandom_3.size() - 1; i >= 0; i--) {
			tpmodel.clickLuz((int) lucesRandom_3.get(i).getFila(), (int) lucesRandom_3.get(i).getColumna(), luces_3);

		}

		// verify
		assertTrue(tpmodel.comprobarJuegoGanado(luces_1));
		assertTrue(tpmodel.comprobarJuegoGanado(luces_2));
		assertTrue(tpmodel.comprobarJuegoGanado(luces_3));

	}

	@Test
	public void cargarImagenExistente() {

		ImageIcon imageNotNull_1 = tpmodel.cargarImagenes("foco_on.png");
		ImageIcon imageNotNull_2 = tpmodel.cargarImagenes("foco_off.png");

		assertNotNull(imageNotNull_1);
		assertNotNull(imageNotNull_2);

	}

	@Test
	public void aniadirLucesCantidadNoPermitida() {

		boolean esFalse_1 = tpmodel.esAnchoDeLucesValido(11);
		boolean esFalse_2 = tpmodel.esAnchoDeLucesValido(-1);
		boolean esFalse_3 = tpmodel.esAnchoDeLucesValido(5);

		boolean esTrue_1 = tpmodel.esAnchoDeLucesValido(4);
		boolean esTrue_2 = tpmodel.esAnchoDeLucesValido(6);
		boolean esTrue_3 = tpmodel.esAnchoDeLucesValido(8);

		assertEquals(esFalse_1, false);
		assertEquals(esFalse_2, false);
		assertEquals(esFalse_3, false);

		assertEquals(esTrue_1, true);
		assertEquals(esTrue_2, true);
		assertEquals(esTrue_3, true);
	}

	@Test
	public void aniadirLucesCantidadPermitida() {

		boolean isOk = tpmodel.esAnchoDeLucesValido(4);

		assertEquals(isOk, true);
	}

	@Test
	public void esLuzExistente() {
		JButton[][] lucesFalse = new JButton[4][4];
		JButton[][] lucesTrue = new JButton[4][4];

		assertFalse(tpmodel.esLuzExistente(4, 4, lucesFalse));
		assertFalse(tpmodel.esLuzExistente(-1, 3, lucesFalse));
		assertFalse(tpmodel.esLuzExistente(-1, -10, lucesFalse));
		assertFalse(tpmodel.esLuzExistente(4, 3, lucesFalse));

		assertTrue(tpmodel.esLuzExistente(3, 3, lucesTrue));
		assertTrue(tpmodel.esLuzExistente(1, 1, lucesTrue));
		assertTrue(tpmodel.esLuzExistente(2, 2, lucesTrue));
		assertTrue(tpmodel.esLuzExistente(0, 0, lucesTrue));

	}

}
