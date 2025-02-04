package com.profamilia.configuracion;

import com.google.common.net.HttpHeaders;
import com.profamilia.dto.Empleado;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestClienteExcel {
    private static final Logger logger = LogManager.getLogger(RestClienteExcel.class);
    private WebClient webClient;

    public RestClienteExcel() {
        ExcelClientService();
    }

    public void ExcelClientService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:7071/reportes")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        logger.info("WebClient inicializado correctamente.");
    }

    /**
     * Metodo para generar el reporte en Excel
     * @param empleados
     * @return
     */
    public byte[] generarExcelEmpleados(List<Empleado> empleados) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return webClient.post()
                .uri("http://localhost:7071/reportes/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(empleados.stream()
                        .map(e -> {
                            Map<String, Object> datos = new HashMap<>();
                            datos.put("Documento", e.getIdempleado());
                            datos.put("Nombres", e.getNombre());
                            datos.put("Fecha de Nacimiento", e.getFecha() != null ? dateFormat.format(e.getFecha()) : "");
                            datos.put("Edad", e.getEdad());
                            datos.put("Telefono", e.getTelefono());
                            datos.put("Cargo", e.getCargo());
                            datos.put("Salario", e.getSalario());
                            datos.put("Sede", e.getSede().getIdsede());
                            return datos;
                        })
                        .collect(Collectors.toList())
                )
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}
