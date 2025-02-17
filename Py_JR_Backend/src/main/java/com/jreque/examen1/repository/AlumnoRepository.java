package com.jreque.examen1.repository;

import com.jreque.examen1.entity.AlumnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Long> {
    List<AlumnoEntity> findAllByEstado(int estado);

    @Query("SELECT P FROM PersonaEntity P WHERE P.estado=:datoEstado")
    List<AlumnoEntity> findByEstadoQuery(@Param("datoEstado") int datoEstado);

    @Query("SELECT P FROM PersonaEntity P WHERE P.nrodoc=:datoNroDoc")
    List<AlumnoEntity> findByNroDocQuery(@Param("datoNroDoc") String datoNroDoc);

}
