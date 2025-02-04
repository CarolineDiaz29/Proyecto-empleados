package com.profamilia.configuracion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.profamilia.dto.Empleado;
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
public class RestClienteEmpleado {
    private static final Logger logger = LogManager.getLogger(RestClienteEmpleado.class);
    private WebClient webClient;

    public RestClienteEmpleado() {
        initWebCliente();;
    }

    /**
     * Metodo para realizar solicitudes a la API de empleados
     */
    private void initWebCliente() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:7070/api/empleados")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        logger.info("WebClient inicializado correctamente.");
    }

    /**
     * Metodo pra obtener empleados por la sede
     * @param idSede
     * @return
     */
    public List<Empleado> obtenerEmpleadosPorSede(int idSede) {
        try {
            return webClient.get()
                    .uri("http://localhost:7070/api/empleados/sedes/{idsede}", idSede)
                    .retrieve()
                    .bodyToFlux(Empleado.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            logger.error("Error al obtener los empleados por sede");
            return Collections.emptyList();
        }

    }

    /**
     * Metodo para obtener empleados por id de empleado
     * @param idEmpleado
     * @return
     */
    public Empleado obtenerEmpleadoPorId(long idEmpleado) {
        return webClient.get()
                .uri("/{id}", idEmpleado)
                .retrieve()
                .bodyToMono(Empleado.class)
                .block();
    }

    /**
     * Metodo para crear el empleado
     * @param empleado
     */
    public void crearEmpleado(Empleado empleado) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonBody = mapper.writeValueAsString(empleado);
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
     * Metodo para editar un empleado existente
     * @param empleado
     */
    public void editarEmpleado(Empleado empleado) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonBody = mapper.writeValueAsString(empleado);
            long idempleado = empleado.getIdempleado();
            webClient.put()
                    .uri("http://localhost:7070/api/empleados/{idempleado}", idempleado)
                    .bodyValue(jsonBody)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir a JSON", e);
        }
    }

    /**
     * Metodo para eliminar un empleado existente
     * @param idEmpleado
     */
    public void eliminarEmpleado(long idEmpleado) {
        webClient.delete()
                .uri("http://localhost:7070/api/empleados/{idempleado}", idEmpleado)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
