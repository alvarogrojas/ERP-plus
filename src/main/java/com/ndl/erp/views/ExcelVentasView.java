package com.ndl.erp.views;

import com.ndl.erp.dto.SaleDetailInfoDTO;
import com.ndl.erp.dto.SalesDetailInfoDTO;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelVentasView extends AbstractXlsView {


    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {


        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"informeVentas.xls\"");
        CreationHelper createHelper = workbook.getCreationHelper();


        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


        // create style for detail text cells
        CellStyle cellTextStyle = workbook.createCellStyle();
        Font detailCellTextfont = workbook.createFont();
        detailCellTextfont.setFontName("Arial");
        cellTextStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        cellTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        detailCellTextfont.setBold(false);
        detailCellTextfont.setColor(HSSFColor.BLACK.index);
        cellTextStyle.setFont(detailCellTextfont);



        @SuppressWarnings("unchecked")
        SalesDetailInfoDTO  salesDetailInfoDTO = (SalesDetailInfoDTO) model.get("salesDetailInfoDTO");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Informe de Ventas");
        sheet.setDefaultColumnWidth(30);

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Fecha Documento");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("Moneda");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("Cliente");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("Cédula");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("Número Documento");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("Tipo Documento");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("Gravado");
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue("Monto Excento");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("Descuento");
        header.getCell(8).setCellStyle(style);
        header.createCell(9).setCellValue("Porcentaje IVA");
        header.getCell(9).setCellStyle(style);
        header.createCell(10).setCellValue("IVA");
        header.getCell(10).setCellStyle(style);
        header.createCell(11).setCellValue("Total");
        header.getCell(11).setCellStyle(style);
        header.createCell(12).setCellValue("Estado");
        header.getCell(12).setCellStyle(style);
        header.createCell(13).setCellValue("Estado Hacienda");
        header.getCell(13).setCellStyle(style);



        //Estilos para los rows
        CellStyle dateStyle = workbook.createCellStyle();
        // Setting format For the date column
        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat()
                .getFormat("dd/MM/yyyy"));


        CellStyle numberCellStyle = workbook.createCellStyle();

        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));


        // Create data cells
        int conteoFila = 1;

        for (SaleDetailInfoDTO row : salesDetailInfoDTO.getSalesDetailInfo()){
            Row courseRow = sheet.createRow(conteoFila++);

            Cell dateCell = courseRow.createCell(0);
            dateCell.setCellStyle(dateStyle);
            if (row.getDate() != null) {
               dateCell.setCellValue(row.getDate());
            } else {
                dateCell.setCellValue("");
            }

            Cell nombreMonedaCell = courseRow.createCell(1);
            nombreMonedaCell.setCellStyle(cellTextStyle);
            if (row.getCurrency() != null) {
                nombreMonedaCell.setCellValue(row.getCurrency().getName());
            } else {
                nombreMonedaCell.setCellValue("");
            }


            Cell clienteCell = courseRow.createCell(2);
            clienteCell.setCellStyle(cellTextStyle);

            if (row.getCliente() != null) {
                clienteCell.setCellValue(row.getCliente());
            } else {
                clienteCell.setCellValue("");
            }

            Cell cedulaCell = courseRow.createCell(3);
            cedulaCell.setCellStyle(cellTextStyle);

            if (row.getCedula() != null) {
                cedulaCell.setCellValue(row.getCedula());
            } else {
                cedulaCell.setCellValue("");
            }


            Cell numeroDocumentoCell = courseRow.createCell(4);
            numeroDocumentoCell.setCellStyle(cellTextStyle);

            if (row.getConsecutivo() != null) {
                numeroDocumentoCell.setCellValue(row.getConsecutivo());
            } else {
                numeroDocumentoCell.setCellValue("");
            }

            Cell tipoDocumentoCell = courseRow.createCell(5);
            tipoDocumentoCell.setCellStyle(cellTextStyle);

            if (row.getTipoDocumento() != null) {
                tipoDocumentoCell.setCellValue(row.getTipoDocumento());
            } else {
                tipoDocumentoCell.setCellValue("");
            }

            Cell gravadoCell = courseRow.createCell(6);
            gravadoCell.setCellStyle(numberCellStyle);

            if (row.getGravado()!= null) {
                gravadoCell.setCellValue(row.getGravado());
            } else {
                gravadoCell.setCellValue("");
            }

            Cell exentoCell = courseRow.createCell(7);
            exentoCell.setCellStyle(numberCellStyle);


            if (row.getMontoExcento()!= null) {
                exentoCell.setCellValue(row.getMontoExcento());
            } else {
                exentoCell.setCellValue("");
            }


            Cell descuentoCell = courseRow.createCell(8);
            descuentoCell.setCellStyle(numberCellStyle);

            if (row.getDescuento()!= null) {
                descuentoCell.setCellValue(row.getDescuento());
            } else {
                descuentoCell.setCellValue("");
            }

            Cell porcentajeIvaCell = courseRow.createCell(9);
            porcentajeIvaCell.setCellStyle(numberCellStyle);

            if (row.getPorcentajeIva() != null) {
                porcentajeIvaCell.setCellValue(row.getPorcentajeIva());
            } else {
                porcentajeIvaCell.setCellValue("");
            }

            Cell ivaCell = courseRow.createCell(10);
            ivaCell.setCellStyle(numberCellStyle);

            if (row.getIva() != null) {
                ivaCell.setCellValue(row.getIva());
            } else {
                ivaCell.setCellValue("");
            }

            Cell totalCell = courseRow.createCell(11);
            totalCell.setCellStyle(numberCellStyle);

            if (row.getTotal() != null) {
                totalCell.setCellValue(row.getTotal());
            } else {
                totalCell.setCellValue("");
            }

            Cell estadoCell = courseRow.createCell(12);
            estadoCell.setCellStyle(cellTextStyle);

            if (row.getEstado() != null) {
                estadoCell.setCellValue(row.getEstado());
            } else {
                estadoCell.setCellValue("");
            }

            Cell estadoHaciendaCell = courseRow.createCell(13);
            estadoHaciendaCell.setCellStyle(cellTextStyle);

            if (row.getEstado() != null) {
                estadoHaciendaCell.setCellValue(row.getEstado());
            } else {
                estadoHaciendaCell.setCellValue("");
            }


        }

    }
}
