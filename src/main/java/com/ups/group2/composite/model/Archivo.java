package com.ups.group2.composite.model;

import jakarta.persistence.Entity;

//Representa un archivo individual.
@Entity
public class Archivo extends ElementoSistemaArchivo {

  private long tamanio;
  private String tipo;

  @Override
  public long getTamanio() {
    return tamanio;
  }

  public void setTamanio(long tamanio) {
    this.tamanio = tamanio;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }


}
