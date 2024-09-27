package com.ups.group2.composite.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ups.group2.composite.model.Archivo;
import com.ups.group2.composite.model.Carpeta;
import com.ups.group2.composite.model.ElementoSistemaArchivo;
import com.ups.group2.composite.repository.ArchivoRepository;
import com.ups.group2.composite.repository.CarpetaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SistemaArchivoService {

  @Autowired
  private ArchivoRepository archivoRepository;

  @Autowired
  private CarpetaRepository carpetaRepository;

  public List<ElementoSistemaArchivo> listarTodosLosElementos() {
    List<ElementoSistemaArchivo> elementos = new ArrayList<>();
    elementos.addAll(listarTodasLasCarpetas());
    elementos.addAll(listarTodosLosArchivos());
    return elementos;
  }

  public List<Carpeta> listarTodasLasCarpetas() {
    return carpetaRepository.findAll();
  }

  public List<Archivo> listarTodosLosArchivos() {
      return archivoRepository.findAll();
  }

  public List<ElementoSistemaArchivo> listarContenidoCarpeta(Long id) {
      Carpeta carpeta = carpetaRepository.findById(id)
              .orElseThrow(() -> new EntityNotFoundException("Carpeta no encontrada"));
      return carpeta.getElementos();
  }

  public Archivo crearArchivo(Archivo archivo) {
      return archivoRepository.save(archivo);
  }

  public Carpeta crearCarpeta(Carpeta carpeta) {
      return carpetaRepository.save(carpeta);
  }

  @Transactional
  public void eliminarElemento(Long id) {
    ElementoSistemaArchivo elemento = encontrarElemento(id);

    // Remover el elemento de su carpeta contenedora si existe
    for (Carpeta carpeta : carpetaRepository.findAll()) {
        if (carpeta.getElementos().removeIf(e -> e.getId().equals(id))) {
            carpetaRepository.save(carpeta);
            break;
        }
    }

    // Eliminar el elemento
    if (elemento instanceof Archivo) {
        archivoRepository.deleteById(id);
    } else if (elemento instanceof Carpeta) {
        carpetaRepository.deleteById(id);
    }
  }

  public long obtenerTamanio(Long id) {
    Optional<Archivo> archivo = archivoRepository.findById(id);
    if (archivo.isPresent()) {
        return archivo.get().getTamanio();
    }

    Optional<Carpeta> carpeta = carpetaRepository.findById(id);
    if (carpeta.isPresent()) {
        return carpeta.get().getTamanio();
    }

    throw new EntityNotFoundException("Elemento no encontrado");
  }

  @Transactional
  public void moverElemento(Long elementoId, Long carpetaDestinoId) {
      Carpeta carpetaDestino = carpetaRepository.findById(carpetaDestinoId)
              .orElseThrow(() -> new EntityNotFoundException("Carpeta destino no encontrada"));

      ElementoSistemaArchivo elemento = encontrarElemento(elementoId);

      // Remover el elemento de su carpeta actual si existe
      for (Carpeta carpeta : carpetaRepository.findAll()) {
          if (carpeta.getElementos().removeIf(e -> e.getId().equals(elementoId))) {
              carpetaRepository.save(carpeta);
              break;
          }
      }

      // Agregar el elemento a la nueva carpeta
      carpetaDestino.agregarElemento(elemento);
      carpetaRepository.save(carpetaDestino);
  }

  //metodo utils
  private ElementoSistemaArchivo encontrarElemento(Long id) {
    Optional<Archivo> archivo = archivoRepository.findById(id);
    if (archivo.isPresent()) {
        return archivo.get();
    }

    Optional<Carpeta> carpeta = carpetaRepository.findById(id);
    if (carpeta.isPresent()) {
        return carpeta.get();
    }

    throw new EntityNotFoundException("Elemento no encontrado");
  }

}
