package com.profamilia.configuracion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.profamilia.dto.Sede;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;


@Named
@ApplicationScoped
public class RestClienteSede {
    private static final Logger logger = LogManager.getLogger(RestClienteSede.class);
    private WebClient webClient;

    public RestClienteSede() {
        initWebClient();
    }

    /**
     * Metodo para realizar solicitudes a la API de sedes
     */
    private void initWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:7070/api/sedes")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        logger.info("WebClient inicializado correctamente.");
    }

    /**
     * Metodo para obtener todas las sedes
     * @return
     */
    public List<Sede> obtenerTodasLasSedes() {
        try {
            return webClient.get()
                    .retrieve()
                    .bodyToFlux(Sede.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            logger.error("Error al obtener sedes", e);
            return Collections.emptyList();
        }
    }

    /**
     * Metodo para obtener las sedes por su id
     * @param idSede
     * @return
     */
    public Sede obtenerSedePorId(int idSede) {
        return webClient.get()
                .uri("http://localhost:7070/api/sedes/{idsede}", idSede)
                .retrieve()
                .bodyToMono(Sede.class)
                .block();
    }

    /**
     * Metodo para crear las sedes
     * @param sede
     */
    public void crearSede(Sede sede) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonBody = mapper.writeValueAsString(sede);
            webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonBody)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir a JSON", e);
        }
    }

    /**
     * Metodo para editar las sedes
     * @param sede
     */
    public void editarSede(Sede sede) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonBody = mapper.writeValueAsString(sede);
            int idsede = sede.getIdsede();
            webClient.put()
                    .uri("http://localhost:7070/api/sedes/{idsede}", idsede)
                    .bodyValue(sede)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir a JSON", e);
        }
    }
}
