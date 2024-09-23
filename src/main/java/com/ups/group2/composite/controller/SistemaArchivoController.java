package com.ups.group2.composite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ups.group2.composite.model.Archivo;
import com.ups.group2.composite.model.Carpeta;
import com.ups.group2.composite.model.ElementoSistemaArchivo;
import com.ups.group2.composite.service.SistemaArchivoService;

@RestController
@RequestMapping("/api/v1/filesystem")
public class SistemaArchivoController {

  @Autowired
  private SistemaArchivoService sistemaArchivoService;

  @GetMapping("/elementos")
  public ResponseEntity<List<ElementoSistemaArchivo>> listarTodosLosElementos() {
    List<ElementoSistemaArchivo> elementos = sistemaArchivoService.listarTodosLosElementos();
    return ResponseEntity.ok(elementos);
  }
  
  @GetMapping("/carpetas")
  public ResponseEntity<List<Carpeta>> listarTodasLasCarpetas() {
      List<Carpeta> carpetas = sistemaArchivoService.listarTodasLasCarpetas();
      return ResponseEntity.ok(carpetas);
  }

  @GetMapping("/archivos")
  public ResponseEntity<List<Archivo>> listarTodosLosArchivos() {
      List<Archivo> archivos = sistemaArchivoService.listarTodosLosArchivos();
      return ResponseEntity.ok(archivos);
  }

  @GetMapping("/carpeta/{id}")
  public ResponseEntity<List<ElementoSistemaArchivo>> listarContenidoCarpeta(@PathVariable Long id) {
      return ResponseEntity.ok(sistemaArchivoService.listarContenidoCarpeta(id));
  }

  @PostMapping("/archivo")
  public ResponseEntity<Archivo> crearArchivo(@RequestBody Archivo archivo) {
      return ResponseEntity.ok(sistemaArchivoService.crearArchivo(archivo));
  }

  @PostMapping("/carpeta")
  public ResponseEntity<Carpeta> crearCarpeta(@RequestBody Carpeta carpeta) {
      return ResponseEntity.ok(sistemaArchivoService.crearCarpeta(carpeta));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarElemento(@PathVariable Long id) {
      sistemaArchivoService.eliminarElemento(id);
      return ResponseEntity.ok().build();
  }

  @GetMapping("/tamanio/{id}")
  public ResponseEntity<Long> obtenerTamanio(@PathVariable Long id) {
      return ResponseEntity.ok(sistemaArchivoService.obtenerTamanio(id));
  }

  @PutMapping("/mover/{id}")
  public ResponseEntity<Void> moverElemento(@PathVariable Long id, @RequestParam Long carpetaDestinoId) {
      sistemaArchivoService.moverElemento(id, carpetaDestinoId);
      return ResponseEntity.ok().build();
  }

}
