package com.ProfamiliaExcel.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExcelServicio {
    @Value("${ruta.guardado.excel:C://apl}")
    private String rutaGuardado;

    public byte[] generarExcel(List<Map<String, Object>> datos) {
        try {
            Files.createDirectories(Paths.get(rutaGuardado));

            String nombreArchivo = "reporte_" + System.currentTimeMillis() + ".xlsx";
            Path archivoPath = Paths.get(rutaGuardado, nombreArchivo);

            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOut = new FileOutputStream(archivoPath.toFile())) {

                Sheet sheet = workbook.createSheet("Reporte");

                if (!datos.isEmpty()) {
                    Row headerRow = sheet.createRow(0);
                    List<String> columnas = new ArrayList<>(datos.get(0).keySet());

                    for (int i = 0; i < columnas.size(); i++) {
                        headerRow.createCell(i).setCellValue(columnas.get(i));
                    }

                    // Llenar datos
                    for (int rowNum = 1; rowNum <= datos.size(); rowNum++) {
                        Row row = sheet.createRow(rowNum);
                        Map<String, Object> registro = datos.get(rowNum - 1);

                        int colNum = 0;
                        for (String columna : columnas) {
                            Cell cell = row.createCell(colNum++);
                            Object valor = registro.get(columna);

                            // Conversión de tipos
                            if (valor instanceof String) {
                                cell.setCellValue((String) valor);
                            } else if (valor instanceof Number) {
                                cell.setCellValue(((Number) valor).doubleValue());
                            }
                            else if (valor instanceof Date) {
                                cell.setCellValue((Date) valor);
                                CellStyle dateCellStyle = workbook.createCellStyle();
                                CreationHelper createHelper = workbook.getCreationHelper();
                                dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
                                cell.setCellStyle(dateCellStyle);
                            } else {
                                cell.setCellValue(valor != null ? valor.toString() : "");
                            }
                        }
                    }
                }

                workbook.write(fileOut);
                return Files.readAllBytes(archivoPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error generando Excel", e);
        }
    }
}
