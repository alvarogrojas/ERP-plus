package com.ndl.erp.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ndl.erp.domain.FeeVehiculeFuel;
import com.ndl.erp.domain.Kilometer;
import com.ndl.erp.domain.KilometerDetail;
import com.ndl.erp.util.StringHelper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class KmViewBuilder extends PdfiTextView {


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> data = (Map<String, Object>) model.get("data");

        Kilometer km = (Kilometer) model.get("km");
        FeeVehiculeFuel fee = (FeeVehiculeFuel) model.get("fee");
//        List<KilometerDetail> kmds = (List<KilometerDetail>) data.get("kmd");
        List<KilometerDetail> kmds = km.getDetails();

        MyFooter event = new MyFooter();
        writer.setPageEvent(event);

        PdfPTable table= this.getHeader(km,fee);
        table.setHeaderRows(10);

        this.generateDetail(table,km,fee,kmds);

        this.addSignatures(table);

        doc.add(table);
    }

    private void addSignatures(PdfPTable table) {
        table.addCell(this.getCellData(" ", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, 0, Element.ALIGN_BOTTOM,6, null));
        table.addCell(this.getCellData(" ", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, 0, Element.ALIGN_BOTTOM,6, null));
        table.addCell(this.getCellData(" ", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, 0, Element.ALIGN_BOTTOM,6, null));
        table.addCell(this.getCellData("Fima Colaborador:",8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT,null,Rectangle.BOX, Element.ALIGN_BOTTOM,2, 3));
        table.addCell(this.getCellData("Firma Aprobación 1:",8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT,null,Rectangle.BOX, Element.ALIGN_BOTTOM,2, 3));
        table.addCell(this.getCellData("Firma Aprobación 2:",8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT,null,Rectangle.BOX,Element.ALIGN_BOTTOM, 2, 3));
    }



    private void generateDetail(PdfPTable tableBody, Kilometer km, FeeVehiculeFuel fee, List<KilometerDetail> kmds) throws DocumentException {
        for(KilometerDetail kmd:kmds){
            String fecha = StringHelper.getDate2String(kmd.getDateKmDetail());
            tableBody.addCell(this.getCellData(fecha, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

            String proyect = kmd.getCostCenter().getName();
            tableBody.addCell(this.getCellData(proyect, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

            String description = kmd.getDescription();
            tableBody.addCell(this.getCellData(description, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

            String kms = StringHelper.getNumber2Str(kmd.getKm());
            tableBody.addCell(this.getCellData(kms, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

            String payFactor = km.getCurrency().getSimbol() + " " + StringHelper.getNumber2Str(kmd.getPayFactor());
            tableBody.addCell(this.getCellData(payFactor, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

            String subTotal = km.getCurrency().getSimbol() + " " +  StringHelper.getNumber2Str(kmd.getSubTotal());
            tableBody.addCell(this.getCellData(subTotal, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
        }

        String labelNeto = "NETO PENDIENTE POR CONCEPTO DE KILOMETRAJE";
        tableBody.addCell(this.getCellData(labelNeto, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,Rectangle.BOX,null,5, null));
        tableBody.addCell(this.getCellData(km.getCurrency().getSimbol() + " " +  StringHelper.get2DecimalToStr(km.getTotal()), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, Rectangle.BOX,null,null, null));
//        tableBody.addCell(this.getCellData(km.getCurrency().getSimbol() + " " +  StringHelper.getNumber2Str(km.getTotal()), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, Rectangle.BOX,null,null, null));
    }






    private PdfPTable getHeader(Kilometer km,FeeVehiculeFuel fee) throws IOException, DocumentException {
        float sizeFont = 7f;
        float[] columnWidths = {5, 5, 5, 5, 5, 5};
        PdfPTable tableHeader = new PdfPTable(columnWidths);
        tableHeader.setWidthPercentage(100);
        tableHeader.setSpacingBefore(10);
        tableHeader.getDefaultCell().setBorder(Rectangle.BOX);

        //1 linea
        tableHeader.addCell(this.getCellTitle("LIQUIDACIÓN DE KILOMETRAJE",18f, Font.BOLD, BaseColor.RED, Element.ALIGN_CENTER, false,6, 3));

        //2 linea
        tableHeader.addCell(this.getCellImage(this.getLogo(), Element.ALIGN_LEFT,false,6,null));

        //3 linea
        tableHeader.addCell(this.getCellData("Nombre del colaborador", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getCollaborator().getName() + " " + km.getCollaborator().getLastName(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Departamento", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getCollaborator().getDepartment().getName(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Fecha", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(StringHelper.getDate2String(km.getDateKm()), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

        //4  linea
        tableHeader.addCell(this.getCellData("Centímetros Cúbicos del Motor", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getCollaborator().getCcMotor(), sizeFont, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Marca del Vehículo", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getCollaborator().getVehicleBrand(), sizeFont, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Año del Vehículo", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(StringHelper.getNumber2Str(km.getCollaborator().getVehicleYear()), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

        //5  linea
        tableHeader.addCell(this.getCellData("Antigüedad del Vehículo", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(StringHelper.getNumber2Str(StringHelper.yearDif(km.getCollaborator().getVehicleYear())), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Tipo de Tracción", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getCollaborator().getTraction(), sizeFont, Font.NORMAL, null, Element.ALIGN_CENTER, null,1,null,null, null));
        tableHeader.addCell(this.getCellData("Tipo de combustible", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(fee==null?"":fee.getFuel().getName(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

        //6 linea
        tableHeader.addCell(this.getCellData("Tipo de Moneda", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getCurrency().getName() + "(" + km.getCurrency().getSimbol() +")", TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Monto por Kilómetro", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getCurrency().getSimbol() + " " +  StringHelper.getNumber2Str(fee==null?0d:fee.getAmount()), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,3, null));

        //7 liena
        tableHeader.addCell(this.getCellData("Razón del Reporte", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(km.getReason(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_CENTER, null,Rectangle.BOX,null,5, null));


        //8 linea
        tableHeader.addCell(this.getCellData("Fecha", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Proyecto", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Descripción de la Visita", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Kilómetro Recorridos", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Factor de Pago (Monto por Kilómetro)", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Sub Total", sizeFont, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));

        return tableHeader;
    }




}
