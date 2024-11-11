package com.codigo.ms_registros.retrofit;

import com.codigo.ms_registros.aggregates.response.ResponseSunat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ClientSunatService {

    @GET("v2/sunat/ruc")
    Call<ResponseSunat> getInfoSunat(@Header("Authorization") String token,
                                     @Query("numero") String numero);
}
