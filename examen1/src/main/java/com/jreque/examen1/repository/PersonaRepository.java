package com.jreque.examen1.repository;

import com.jreque.examen1.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
    List<PersonaEntity> findAllByEstado(int estado);

    @Query("SELECT P FROM PersonaEntity P WHERE P.estado=:datoEstado")
    List<PersonaEntity> findByEstadoQuery(@Param("datoEstado") int datoEstado);

    @Query("SELECT P FROM PersonaEntity P WHERE P.nrodoc=:datoNroDoc")
    List<PersonaEntity> findByNroDocQuery(@Param("datoNroDoc") String datoNroDoc);

}
