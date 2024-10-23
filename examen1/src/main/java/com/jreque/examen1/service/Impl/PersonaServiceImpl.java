package com.jreque.examen1.service.Impl;

import com.jreque.examen1.entity.PersonaEntity;
import com.jreque.examen1.repository.PersonaRepository;
import com.jreque.examen1.service.PersonaService;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonaServiceImpl implements PersonaService {

    // Repositorio Persona
    private final PersonaRepository personaRepository;

    // Constructor
    public PersonaServiceImpl(PersonaRepository personaRepository){
        this.personaRepository = personaRepository;
    }

    // CRUD - LISTAR
    // ========================================================================
    // Debe realizar la búsqueda de todas las personas con estado activo
    public List<PersonaEntity> listarPersonas() {
        return personaRepository.findByEstadoQuery(1);
    }

    // CRUD - CREAR
    // ========================================================================
    // Debe permitir registrar una persona con dirección y pedidos
    public PersonaEntity crearPersona(PersonaEntity personaEntity) {
        // Setear auditoría
        personaEntity.setEstado(1);
        personaEntity.setCreated_by("ADMIN");
        personaEntity.setCreated_date(new Timestamp(System.currentTimeMillis()));
        // Grabar
        return personaRepository.save(personaEntity);
    }

    // CRUD - ACTUALIZAR
    // ========================================================================
    // Debe permitir actualizar una persona, Dirección, Pedidos.
    public PersonaEntity actualizarPersona(Long id, PersonaEntity personaEntity) {
        PersonaEntity ThisPersona = buscarPersonaxId(id);
        ThisPersona.setApellidos(personaEntity.getApellidos());
        ThisPersona.setNombres(personaEntity.getNombres());
        ThisPersona.setNrodoc(personaEntity.getNrodoc());
        ThisPersona.setDireccionEntity(personaEntity.getDireccionEntity());
        ThisPersona.setUpdate_by("ADMIN");
        ThisPersona.setUpdate_date(new Timestamp(System.currentTimeMillis()));

        //Gestionando Pedidos
        //gestionarPedidos(clienteExistente, clienteActual.getPedidos());
        //clienteActual.setId(clienteExistente.getId());
        //gestionarPedidos2(clienteActual.getPedidos());
        //clienteExistente.setPedidos(clienteActual.getPedidos());
        return personaRepository.save(ThisPersona);
    }

    // CRUD - ELIMINAR
    // ========================================================================
    // De eliminar un registro de manera lógica (usar el campo de estado)
    public void eliminarPersona(Long id) {
        PersonaEntity personaExistente = buscarPersonaxId(id);
        // Setear auditoría
        personaExistente.setEstado(0);
        personaExistente.setDelete_by("ADMIN");
        personaExistente.setDelete_date(new Timestamp(System.currentTimeMillis()));
        // Grabar eliminación lógica
        personaRepository.save(personaExistente);
    }

    // BUSCAR - Debe buscar una persona por ID.
    public PersonaEntity buscarPersonaxId(Long id) {
        return personaRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Error Persona no encontrada."));
    }

    // BUSCAR - Debe buscar una persona por número de documento
    public List<PersonaEntity> buscarPersonaxNroDoc(String nrodoc) {
        return personaRepository.findByNroDocQuery(nrodoc);
    }

}
