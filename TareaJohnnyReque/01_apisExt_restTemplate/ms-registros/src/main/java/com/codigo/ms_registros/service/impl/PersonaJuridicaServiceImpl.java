package com.codigo.ms_registros.service.impl;

import com.codigo.ms_registros.aggregates.constants.Constants;
import com.codigo.ms_registros.aggregates.response.ResponseSunat;
import com.codigo.ms_registros.client.ClientSunat;
import com.codigo.ms_registros.entity.PersonaJuridicaEntity;
import com.codigo.ms_registros.redis.RedisService;
import com.codigo.ms_registros.repository.PersonaJuridicaRepository;
import com.codigo.ms_registros.retrofit.ClientSunatService;
import com.codigo.ms_registros.retrofit.impl.ClientSunatServiceImpl;
import com.codigo.ms_registros.service.PersonaJuridicaService;
import com.codigo.ms_registros.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

@Service
@Log4j2
public class PersonaJuridicaServiceImpl implements PersonaJuridicaService {
    private final PersonaJuridicaRepository personaJuridicaRepository;
    private final ClientSunat clientSunat;
    private final RestTemplate restTemplate;
    private final RedisService redisService;

    ClientSunatService sunatServiceRetrofit = ClientSunatServiceImpl
            .getRetrofit()
            .create(ClientSunatService.class);

    @Value("${token.api}")
    private String token;

    public PersonaJuridicaServiceImpl(PersonaJuridicaRepository personaJuridicaRepository,
                                      ClientSunat clientSunat,
                                      RestTemplate restTemplate, RedisService redisService) {
        this.personaJuridicaRepository = personaJuridicaRepository;
        this.clientSunat = clientSunat;
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    @Override
    public PersonaJuridicaEntity guardar(String ruc) throws IOException{
        //PersonaNaturalEntity personaNatural = getEntity(dni); //Retrofit
        PersonaJuridicaEntity personaJuridica = getEntityForRestTemplate(ruc);  //RestTemplate
        if(Objects.nonNull(personaJuridica)){
            return personaJuridicaRepository.save(personaJuridica);
        }else {
            return null;
        }
    }

    @Override
    public ResponseSunat getInfoSunat(String ruc) {
        ResponseSunat datosSunat = new ResponseSunat();
        //Recupero la Información de Redis
        String redisInfo = redisService.getDataFromRedis(Constants.REDIS_KEY_API_SUNAT+ruc);
        //Valido que exista la info
        if(Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
            return datosSunat;
        }else{
            //Sino existe la data en redis me voy a Sunat api
            datosSunat = executeRestTemplate(ruc);
            //Convertir a String para poder guardarlo en Redis
            String dataForRedis = Util.convertirAString(datosSunat);
            //Guardando en Redis la información
            redisService.saveInRedis(Constants.REDIS_KEY_API_SUNAT+ruc,dataForRedis,Constants.REDIS_TTL);
            return datosSunat;
        }
    }

    private PersonaJuridicaEntity getEntity(String ruc) throws IOException {
        PersonaJuridicaEntity personaJuridicaEntity = new PersonaJuridicaEntity();
        //Ejecuta a Reniec usando OpenFeign
        //ResponseReniec datosReniec = executionReniec(dni);

        //Preparo el objeto Reniec usando Retrofit:
        Call<ResponseSunat> clientRetrofit = prepareSunatRetrofit(ruc);
        log.info("getEntity -> Se Preparo todo el cliente Retrofit, listo para ejecutar!");
        //Ejecuto a Reniec usando Retrofit:
        Response<ResponseSunat> executeSunat = clientRetrofit.execute();
        log.info("getEntity -> CLiente Retrofit Ejecutado");

        //Validar el resultado
        ResponseSunat datosSunat = null;
        if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())){
            datosSunat = executeSunat.body();
            log.error("getEntity -> valores del body: " + executeSunat.body().toString());
        }

        if(Objects.nonNull(datosSunat)){
            personaJuridicaEntity.setRazonSocial(datosSunat.getRazonSocial());

            personaJuridicaEntity.setEstadoReg(Constants.ESTADO_ACTVO);
            personaJuridicaEntity.setUserCreated(Constants.USER_CREATED);
            personaJuridicaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }
        return personaJuridicaEntity;
    }

    private PersonaJuridicaEntity getEntityForRestTemplate(String ruc) throws IOException {

        // =============================================================================
        // USANDO REST TEMPLATE
        // =============================================================================

        // Instanciar
        PersonaJuridicaEntity personaJuridicaEntity = new PersonaJuridicaEntity();
        ResponseSunat datosSunat = new ResponseSunat();

        // Recuperar la Información de Redis
        String redisInfo = redisService.getDataFromRedis(ruc);

        // Validar que exista la info
        if(Objects.nonNull(redisInfo)){
            datosSunat = Util.convertirDesdeString(redisInfo, ResponseSunat.class);
        }else{

            // Sino existe la data en redis me voy a Sunat api
            datosSunat = executeRestTemplate(ruc);

            //Convertir a String para poder guardarlo en Redis
            String dataForRedis = Util.convertirAString(datosSunat);

            //Guardando en Redis la información
            redisService.saveInRedis(ruc,dataForRedis,Constants.REDIS_TTL);

        }

        // Validar el resultado
        if(Objects.nonNull(datosSunat)){
            personaJuridicaEntity.setRazonSocial(datosSunat.getRazonSocial());

            personaJuridicaEntity.setEstadoReg(Constants.ESTADO_ACTVO);
            personaJuridicaEntity.setUserCreated(Constants.USER_CREATED);
            personaJuridicaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }

        // Retorno
        return personaJuridicaEntity;

    }

    //Metodo que ejecuta el client OpenFeign de Reniec
    private ResponseSunat executionSunat(String ruc){
        //String tokenOk = "Bearer "+token;
        String tokenOk = Constants.BEARER+token;
        return clientSunat.getPersonaSunat(ruc,tokenOk);
    }

    //Metodo que prepara el client Retrofit de Reniec
    private Call<ResponseSunat> prepareSunatRetrofit(
            String ruc){
        String tokenComplete = "Bearer "+token;
        log.info("prepareSunatRetrofit -> Ejecutando Metodo de Apoyo que crea el objeto retrofit completo");
        return sunatServiceRetrofit.getInfoSunat(tokenComplete,ruc);
    }

    //ASINCRONA ->  eS CUANDO EJECUTAS UNSA SOLICITUD PERO NO TIENES RESPUESTA
    //SINCRONA -> Es cuadno tu ejecutas una solicitus y esperas una respuesta.

    private ResponseSunat executeRestTemplate(String ruc){
        //Configurar una URL completa como String
        String url = "https://api.apis.net.pe/v2/sunat/ruc?numero="+ruc;
        //Genero mi CLient RestTemplate y Ejecuto
        ResponseEntity<ResponseSunat> executeRestTemplate =
                restTemplate.exchange(
                        url, //URL A LA CUAL VAS A EJECUTAR
                        HttpMethod.GET, //TIPO DE SOLICITUD AL QUE PERTENCE LA URL
                        new HttpEntity<>(createHeaders()), //CABECERAS || HEADERS
                        ResponseSunat.class // RESPONSE A CASTEAR
                );

        if(executeRestTemplate.getStatusCode().equals(HttpStatus.OK)){
            return executeRestTemplate.getBody();
        }else {
            return null;
        }
    }

    private HttpHeaders createHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+token);
        return headers;
    }

}
