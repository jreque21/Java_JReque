package com.jreque.examen1.service.Impl;

import com.jreque.examen1.entity.AlumnoEntity;
import com.jreque.examen1.repository.AlumnoRepository;
import com.jreque.examen1.service.AlumnoService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    // Repositorio Persona
    private final AlumnoRepository alumnoRepository;

    // Constructor
    public AlumnoServiceImpl(AlumnoRepository alumnoRepository){
        this.alumnoRepository = alumnoRepository;
    }

    // CRUD - LISTAR
    // ========================================================================
    // Debe realizar la búsqueda de todas las personas con estado activo
    public List<AlumnoEntity> listarAlumnos() {
        return alumnoRepository.findByEstadoQuery(1);
    }

    // CRUD - CREAR
    // ========================================================================
    // Debe permitir registrar una persona con dirección y pedidos
    public AlumnoEntity crearPersona(AlumnoEntity alumnoEntity) {
        // Setear auditoría
        alumnoEntity.setEstado(1);
        alumnoEntity.setCreated_by("ADMIN");
        alumnoEntity.setCreated_date(new Timestamp(System.currentTimeMillis()));
        // Grabar
        return alumnoRepository.save(alumnoEntity);
    }

    // CRUD - ACTUALIZAR
    // ========================================================================
    // Debe permitir actualizar una persona, Dirección, Pedidos.
    public AlumnoEntity actualizarAlumno(Long id, AlumnoEntity alumnoEntity) {
        AlumnoEntity ThisAlumno = buscarAlumnoxId(id);
        ThisAlumno.setApellidos(alumnoEntity.getApellidos());
        ThisAlumno.setNombres(alumnoEntity.getNombres());
        ThisAlumno.setNrodoc(alumnoEntity.getNrodoc());
        ThisAlumno.setDireccionEntity(alumnoEntity.getDireccionEntity());
        ThisAlumno.setUpdate_by("ADMIN");
        ThisAlumno.setUpdate_date(new Timestamp(System.currentTimeMillis()));

        //Gestionando Pedidos
        //gestionarPedidos(clienteExistente, clienteActual.getPedidos());
        //clienteActual.setId(clienteExistente.getId());
        //gestionarPedidos2(clienteActual.getPedidos());
        //clienteExistente.setPedidos(clienteActual.getPedidos());
        return alumnoRepository.save(ThisAlumno);
    }

    // CRUD - ELIMINAR
    // ========================================================================
    // De eliminar un registro de manera lógica (usar el campo de estado)
    public void eliminarAlumno(Long id) {
        AlumnoEntity alumnoExistente = buscarAlumnoxId(id);
        // Setear auditoría
        alumnoExistente.setEstado(0);
        alumnoExistente.setDelete_by("ADMIN");
        alumnoExistente.setDelete_date(new Timestamp(System.currentTimeMillis()));
        // Grabar eliminación lógica
        alumnoRepository.save(alumnoExistente);
    }

    // BUSCAR - Debe buscar una persona por ID.
    public AlumnoEntity buscarAlumnoxId(Long id) {
        return alumnoRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Error Alumno no encontrado."));
    }

    // BUSCAR - Debe buscar una persona por número de documento
    public List<AlumnoEntity> buscarAlumnoxNroDoc(String nrodoc) {
        return alumnoRepository.findByNroDocQuery(nrodoc);
    }

}
