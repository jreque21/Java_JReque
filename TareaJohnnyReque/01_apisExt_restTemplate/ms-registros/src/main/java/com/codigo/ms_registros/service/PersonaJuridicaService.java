package com.codigo.ms_registros.service;

import com.codigo.ms_registros.aggregates.response.ResponseSunat;
import com.codigo.ms_registros.entity.PersonaJuridicaEntity;

import java.io.IOException;

public interface PersonaJuridicaService {
    PersonaJuridicaEntity guardar(String ruc) throws IOException;
    ResponseSunat getInfoSunat(String ruc);
}
