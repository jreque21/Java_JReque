package com.jreque.examen1.service;

import com.jreque.examen1.entity.PersonaEntity;

import java.util.List;

public interface PersonaService {
    List<PersonaEntity> listarPersonas();
    PersonaEntity crearPersona(PersonaEntity personaEntity);
    PersonaEntity actualizarPersona(Long id, PersonaEntity personaEntity);
    void eliminarPersona(Long id);
    PersonaEntity buscarPersonaxId(Long id);
    List<PersonaEntity> buscarPersonaxNroDoc(String nrodoc);
}
