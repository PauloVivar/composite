package com.ups.group2.composite.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

//Representa una carpeta que puede contener otros elementos.
@Entity
public class Carpeta extends ElementoSistemaArchivo {

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private List<ElementoSistemaArchivo> elementos = new ArrayList<>();

  @Override
  public long getTamanio() {
    return elementos.stream().mapToLong(ElementoSistemaArchivo::getTamanio).sum();
  }

  public List<ElementoSistemaArchivo> getElementos() {
    return elementos;
  }

  public void setElementos(List<ElementoSistemaArchivo> elementos) {
    this.elementos = elementos;
  }

  public void agregarElemento(ElementoSistemaArchivo elemento) {
    elementos.add(elemento);
  }

  public void eliminarElemento(ElementoSistemaArchivo elemento) {
      elementos.remove(elemento);
  }

}
