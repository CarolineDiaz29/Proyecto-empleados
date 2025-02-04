package com.ProfamiliaExcel.controlador;


import com.ProfamiliaExcel.services.ExcelServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/reportes")
public class ExcelControlador {
    @Autowired
    private ExcelServicio excelServicio;

    @PostMapping("/generar")  // Específicamente POST
    public ResponseEntity<byte[]> generarExcel(@RequestBody List<Map<String, Object>> datos) {
        byte[] excelBytes = excelServicio.generarExcel(datos);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte.xlsx")
                .body(excelBytes);
    }
}