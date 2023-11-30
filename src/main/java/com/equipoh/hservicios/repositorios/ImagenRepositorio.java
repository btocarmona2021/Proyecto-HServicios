package com.equipoh.hservicios.repositorios;

import com.equipoh.hservicios.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Martb
 */

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    @Query("SELECT i FROM Imagen i WHERE i.nombre  LIKE  '%efecto_image_service%' ")
    public Imagen imagenXDefecto();
}
