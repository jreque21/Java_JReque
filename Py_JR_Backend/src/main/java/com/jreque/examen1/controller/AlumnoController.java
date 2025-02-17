package com.jreque.examen1.controller;
import com.jreque.examen1.entity.AlumnoEntity;
import com.jreque.examen1.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumnos")

public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<AlumnoEntity> listarAlumnos() {
        return alumnoService.listarAlumnos();
    }

    @PostMapping
    public ResponseEntity<AlumnoEntity> crearAlumno(@RequestBody AlumnoEntity alumno) {
        AlumnoEntity nuevoAlumno = alumnoService.crearAlumno(alumno);
        return new ResponseEntity<>(nuevoAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoEntity> actualizarAlumno(@PathVariable Long id, @RequestBody AlumnoEntity alumno) {
        AlumnoEntity thisAlumno = alumnoService.actualizarAlumno(id, alumno);
        return new ResponseEntity<>(thisAlumno, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        alumnoService.eliminarAlumno(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoEntity> buscarAlumnoxId(@PathVariable Long id) {
        AlumnoEntity alumno = alumnoService.buscarAlumnoxId(id);
        return new ResponseEntity<>(alumno, HttpStatus.OK);
    }

}
