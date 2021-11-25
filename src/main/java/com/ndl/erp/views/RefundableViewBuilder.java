package com.ndl.erp.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ndl.erp.domain.Refundable;
import com.ndl.erp.domain.RefundableDetail;
import com.ndl.erp.util.StringHelper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RefundableViewBuilder extends PdfiTextView {



    public void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = (Map<String, Object>) model.get("data");
        Refundable rfd = (Refundable) model.get("rfd");
//        List <RefundableDetail> rds = (List<RefundableDetail>) data.get("rds");
        List <RefundableDetail> rds = rfd.getDetails();

        MyFooter event = new MyFooter();
        writer.setPageEvent(event);

        PdfPTable table= this.getHeader(rfd);
        table.setHeaderRows(7);

        this.addDetails(table,rds,rfd);

        this.addFoother(table,rfd,rds);

        this.addSignatures(table);

        doc.add(table);
    }



    private PdfPTable getHeader(Refundable rfd) throws IOException, DocumentException {
        float[] columnWidths = {2, 5, 5, 4, 4, 3};
        PdfPTable tableHeader = new PdfPTable(columnWidths);
        tableHeader.setWidthPercentage(100);
        tableHeader.setSpacingBefore(10);
        tableHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        //1 linea
        tableHeader.addCell(this.getCellTitle("REPORTE DE GASTOS REEMBOLSABLES",18f, Font.BOLD, BaseColor.RED, Element.ALIGN_CENTER, false,6, 3));

        //2 linea
        tableHeader.addCell(this.getCellImage(this.getLogo(), Element.ALIGN_LEFT,false,6,null));

        //3 linea
        tableHeader.addCell(this.getCellData("Nombre del colaborador", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData( rfd.getCollaborator().getName() + " " + rfd.getCollaborator().getLastName(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_LEFT, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Departamento", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX, null,null,null));
        tableHeader.addCell(this.getCellData( rfd.getCollaborator().getDepartment().getName(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_LEFT, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Fecha", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(StringHelper.getDate2String(rfd.getDateInvoice()), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_LEFT, null,Rectangle.BOX,null,null, null));

        //3 Liena
        tableHeader.addCell(this.getCellData("Tipo de Moneda", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(rfd.getCurrency().getName(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_LEFT, null,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData("Razón del Reporte", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, GrayColor.LIGHT_GRAY,Rectangle.BOX,null,null, null));
        tableHeader.addCell(this.getCellData(rfd.getReason(), TABLE_BODY_FONT_SIZE, Font.NORMAL, null, Element.ALIGN_LEFT, null,Rectangle.BOX,null,3, null));

        //4 liena
        tableHeader.addCell(this.getCellHeader("Fecha", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, Rectangle.BOX,null, null));
        tableHeader.addCell(this.getCellHeader("Proyecto", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, Rectangle.BOX,null, null));
        tableHeader.addCell(this.getCellHeader("Descripción del gasto", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, Rectangle.BOX,null, null));
        tableHeader.addCell(this.getCellHeader("Nombre del proveedor", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, Rectangle.BOX,null, null));
        tableHeader.addCell(this.getCellHeader("Número de factura", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, Rectangle.BOX,null, null));
        tableHeader.addCell(this.getCellHeader("Monto", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, Rectangle.BOX,null, null));
        return tableHeader;
    }



    private void addSignatures(PdfPTable tableSignatures) throws DocumentException {
        tableSignatures.addCell(this.getCellData(" ", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, Rectangle.NO_BORDER,Element.ALIGN_BOTTOM,6, null));
        tableSignatures.addCell(this.getCellData(" ", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, Rectangle.NO_BORDER,Element.ALIGN_BOTTOM,6, null));
        tableSignatures.addCell(this.getCellData(" ", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, Rectangle.NO_BORDER,Element.ALIGN_BOTTOM,6, null));
        tableSignatures.addCell(this.getCellData(" ", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, Rectangle.NO_BORDER,Element.ALIGN_BOTTOM,6, null));
        tableSignatures.addCell(this.getCellData("Colaborador:____________________________", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT,null, Rectangle.NO_BORDER,Element.ALIGN_BOTTOM,2, 6));
        tableSignatures.addCell(this.getCellData("Firma 1:____________________________", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT,null,Rectangle.NO_BORDER,Element.ALIGN_BOTTOM,2, 6));
        tableSignatures.addCell(this.getCellData("Firma 2:____________________________", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT,null,Rectangle.NO_BORDER,Element.ALIGN_BOTTOM,2, 6));
    }

    private void addFoother(PdfPTable tableFoother ,Refundable rfd,List <RefundableDetail> rds) throws DocumentException {
        tableFoother.addCell(this.getCellData("ANTICIPOS RECIBIDOS", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,Rectangle.BOX,null,5, null));
        String anticipos =  rfd.getCurrency().getSimbol() +" " + StringHelper.get2DecimalToStr(rfd.getAdvancesReceived());
        tableFoother.addCell(this.getCellData(anticipos, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX, null,null,null));

        tableFoother.addCell(this.getCellData("REINTEGRO REALIZADO", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,Rectangle.BOX,null,5, null));
        String reintegros =  rfd.getCurrency().getSimbol() +" " + StringHelper.get2DecimalToStr(rfd.getTotal());
        tableFoother.addCell(this.getCellData(reintegros, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null, null,null));

        tableFoother.addCell(this.getCellData("NETO PENDIENTE(a favor de la compañia)", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,Rectangle.BOX,null,5, null));
        String netoPendienteCompany = rfd.getCurrency().getSimbol() +" " +  StringHelper.get2DecimalToStr(rfd.getNetoPendingIngpro());
        tableFoother.addCell(this.getCellData(netoPendienteCompany, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));

        tableFoother.addCell(this.getCellData("NETO PENDIENTE(a favor del empleado)", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,Rectangle.BOX,null,5, null));
        String netoPendienteEmpleado=  rfd.getCurrency().getSimbol() +" " + StringHelper.get2DecimalToStr(rfd.getNetoPendingCollab());
        tableFoother.addCell(this.getCellData(netoPendienteEmpleado, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX, null,null,null));
    }


    private void addDetails(PdfPTable tbody,List <RefundableDetail> rds,Refundable rfd ) throws DocumentException {
        for(RefundableDetail rd:rds){
            tbody.addCell(this.getCellData(StringHelper.getDate2String(rd.getDateDetail()), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null,null, null));
            tbody.addCell(this.getCellData(rd.getCostCenter().getCode() + " - " + rd.getCostCenter().getName(), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX, null,null,null));
            tbody.addCell(this.getCellData(rd.getDescription(), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX, null,null,null));
            tbody.addCell(this.getCellData(rd.getProvider(), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX,null, null,null));
            tbody.addCell(this.getCellData(rd.getBillNumber(), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX, null,null,null));
            tbody.addCell(this.getCellData(StringHelper.getNumber2Str(rd.getSubTotal()), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,Rectangle.BOX, null,null,null));
        }
    }

}
