package com.jreque.examen1.service;

import com.jreque.examen1.entity.AlumnoEntity;

import java.util.List;

public interface AlumnoService {
    List<AlumnoEntity> listarAlumnos();
    AlumnoEntity crearAlumno(AlumnoEntity alumnoEntity);
    AlumnoEntity actualizarAlumno(Long id, AlumnoEntity alumnoEntity);
    void eliminarAlumno(Long id);
    AlumnoEntity buscarAlumnoxId(Long id);
    List<AlumnoEntity> buscarAlumnoxNroDoc(String nrodoc);
}
