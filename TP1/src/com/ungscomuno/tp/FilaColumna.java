package com.ungscomuno.tp;

public class FilaColumna<F, C> {
  private F fila;
  private C columna;
     
  public FilaColumna( F fila,  C columna) {
	  this.fila = fila;
	  this.columna = columna;
  }

  public F getFila() {
	  return fila;
  }

  public C getColumna() {
	  return columna;
  }
 
  
}
