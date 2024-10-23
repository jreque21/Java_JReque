package com.jreque.examen1.controller;
import com.jreque.examen1.entity.PersonaEntity;
import com.jreque.examen1.service.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")

public class PersonaController {
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public List<PersonaEntity> listarPersonas() {
        return personaService.listarPersonas();
    }

    @PostMapping
    public ResponseEntity<PersonaEntity> crearPersona(@RequestBody PersonaEntity persona) {
        PersonaEntity nuevoCliente = personaService.crearPersona(persona);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaEntity> actualizarPersona(@PathVariable Long id, @RequestBody PersonaEntity persona) {
        PersonaEntity thisPersona = personaService.actualizarPersona(id, persona);
        return new ResponseEntity<>(thisPersona, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaEntity> buscarPersonaxId(@PathVariable Long id) {
        PersonaEntity persona = personaService.buscarPersonaxId(id);
        return new ResponseEntity<>(persona, HttpStatus.OK);
    }

}
