package com.codigo.ms_registros.controller;

import com.codigo.ms_registros.aggregates.response.ResponseSunat;
import com.codigo.ms_registros.entity.PersonaJuridicaEntity;
import com.codigo.ms_registros.service.PersonaJuridicaService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v2/personaJuridica")
public class PersonaJuridicaController {
    private final PersonaJuridicaService personaJuridicaService;
    public PersonaJuridicaController(PersonaJuridicaService personaJuridicaService) {
        this.personaJuridicaService = personaJuridicaService;
    }

    @PostMapping
    @Operation(summary = "Guardar una nueva Persona juridica",
            description = "Crea una nueva Persona juridica y la almacena en la BD de la aplicación"

    )

    public ResponseEntity<PersonaJuridicaEntity> guardarPersona(
            @Parameter(description = "RUC de la Persona a guardar",
                    required = true,
                    example = "12345678"
            )
            @RequestParam("ruc") String ruc) throws IOException {
        PersonaJuridicaEntity personaJuridica = personaJuridicaService.guardar(ruc);
        return new ResponseEntity<>(personaJuridica, HttpStatus.CREATED);
    }

    @GetMapping("/sunat/{ruc}")
    public ResponseEntity<ResponseSunat> getInfoSunat(
            @PathVariable String ruc){
        return new ResponseEntity<>(personaJuridicaService
                .getInfoSunat(ruc),HttpStatus.OK);
    }

}
