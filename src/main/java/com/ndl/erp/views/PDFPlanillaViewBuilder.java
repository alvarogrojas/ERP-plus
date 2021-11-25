package com.ndl.erp.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.ndl.erp.domain.PayRollCollaboratorDeductionDetail;
import com.ndl.erp.domain.PayRollCollaboratorDetail;
import com.ndl.erp.domain.PayRollCollaboratorRefundDevolutionDetail;
import com.ndl.erp.domain.PayRollDetail;
import com.ndl.erp.util.StringHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class PDFPlanillaViewBuilder extends PdfiTextView {

    private static final Float TABLE_BODY_FONT_SIZE = 6f;
    @Autowired
    private MessageSource messageSource;

    private static final Float FONT_HEADER_SIZE = 8f;


    private static final BaseColor BACK_GROUND_TABLE_HEADER_COLOR = new BaseColor(23,169,227);


    private static final BaseColor TABLE_HEADER_FONT_COLOR = BaseColor.WHITE;
    private static final GrayColor TABLE_BODY_FONT_COLOR = GrayColor.GRAYBLACK;

    private static final String INGPRO_IMG = "/img/logo-planilla.png";
//    private static final String INGPRO_IMG = "resources/img/logo-planilla.png";


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> data = (Map<String, Object>) model.get("data");

        //PayRoll payRoll = (PayRoll) data.get("payRoll");
        PayRollDetail payRollDetail = (PayRollDetail) model.get("payRollDetail");
        List<PayRollCollaboratorDeductionDetail> payRollCollaboratorDeductionDetailList = (List<PayRollCollaboratorDeductionDetail>) model.get("prc-dd");
        List<PayRollCollaboratorRefundDevolutionDetail> payRollCollaboratorRefundDevolutionDetails =
                (List<PayRollCollaboratorRefundDevolutionDetail>) model.get("prc-rdd");
//        List<PayRollCollaboratorDetail> payRollCollaboratorDetails = (List<PayRollCollaboratorDetail>) data.get("model-d");
        List<PayRollCollaboratorDetail> payRollCollaboratorDetails = (List<PayRollCollaboratorDetail>) model.get("prc-d");

        this.addTitle(doc,"Detalle de planilla",27,Element.ALIGN_CENTER,Font.BOLD,BaseColor.LIGHT_GRAY);

        this.addLogo(doc);

        this.addTitle(doc,"Colaborador: " + payRollDetail.getCollaborator().getName() + " "+ payRollDetail.getCollaborator().getLastName(),18,Element.ALIGN_CENTER,Font.BOLD,BaseColor.BLACK);

        this.addTitle(doc," ",30,Element.ALIGN_CENTER,Font.BOLD,BaseColor.LIGHT_GRAY);


        this.generateNomina(doc, payRollDetail);

        this.generateBeneficios(doc, payRollCollaboratorDetails);

        this.generateDeducciones(doc,payRollCollaboratorDeductionDetailList);

        this.generateSalarioBruto(doc,payRollDetail);

        this.generateDevolucionesReintegrosHeader(doc,payRollDetail);

        this.generateDevReintegro(doc,payRollCollaboratorRefundDevolutionDetails);


    }


    private void addTitle(Document doc,String parafo, float size ,int align,int style,BaseColor baseColor) throws DocumentException {
        Font f = new Font(Font.FontFamily.HELVETICA, size, style, baseColor);
        Paragraph preface=new Paragraph(parafo,f);
        preface.setAlignment(align);
        doc.add(preface);
    }


    private void addLogo(Document doc) throws IOException, DocumentException {
       // Image image = Image.getInstance(INGPRO_IMG);
        URL urlFondo= this.getClass().getResource(INGPRO_IMG);
        Image image = Image.getInstance(urlFondo);
        image.scaleAbsolute(100,50);
        image.setAlignment(Image.ALIGN_RIGHT);
        doc.add(image);
    }



    private void generateNomina(Document doc, PayRollDetail payRollDetail) throws DocumentException {

        Paragraph titleP = new Paragraph("Nómina");
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        titleP.add(dottedline);
        doc.add(titleP);


        float[] columnWidths = {5, 5, 5, 5, 5, 5};
        PdfPTable tableNomina = new PdfPTable(columnWidths);
        tableNomina.setWidthPercentage(100);
        tableNomina.setSpacingBefore(10);

        // write tableNomina header
        tableNomina.addCell(this.getCellHeader("Nómina", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Colaborador", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Fecha Inicio", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Fecha Final", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Costo x Hora", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Salario Neto", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));


        //data
        String data = StringHelper.getNumber2Str(payRollDetail.getCollaborator().getNumberPayroll());
        tableNomina.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.getNumber2Str(payRollDetail.getCollaborator().getName() + " " + payRollDetail.getCollaborator().getLastName());
        tableNomina.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.getDate2String(payRollDetail.getStartDate());
        tableNomina.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.getDate2String(payRollDetail.getEndDate());
        tableNomina.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(payRollDetail.getCollaborator().getRate());
        tableNomina.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(payRollDetail.getNetSalary());
        tableNomina.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));


        doc.add(tableNomina);
    }

    private void generateBeneficios(Document doc, List<PayRollCollaboratorDetail> payRollCollaboratorDetails) throws DocumentException {
        Paragraph titleBeneficios = new Paragraph("Beneficios");
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        titleBeneficios.add(dottedline);
        doc.add(titleBeneficios);

        float[] columnWidths = {10, 5, 5, 5, 5, 5, 5, 5, 5, 5,5};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell(this.getCellHeader("Descripción", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, 2, null));
        table.addCell(this.getCellHeader("Sábado", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Domingo", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Lunes", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Martes", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Miércoles", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Jueves", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Viernes", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Cantidad", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        table.addCell(this.getCellHeader("Total", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));


        table.addCell(this.getCellData("Horas Diurnas", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,2));
        table.addCell(this.getCellData("Semana 1",     TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        this.addCellData(table, getRow("HS", 1, payRollCollaboratorDetails));

        table.addCell(this.getCellData("Semana 2", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        this.addCellData(table, getRow("HS", 2, payRollCollaboratorDetails));

        table.addCell(this.getCellData("Horas Extras Diurnas", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, 2));
        table.addCell(this.getCellData("Semana 1", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        this.addCellData(table, getRow("HM", 1, payRollCollaboratorDetails));

        table.addCell(this.getCellData("Semana 2", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        this.addCellData(table, getRow("HM", 2, payRollCollaboratorDetails));

        table.addCell(this.getCellData("Horas Dobles Diurnas", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, 2));
        table.addCell(this.getCellData("Semana 1", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER,null, null));
        this.addCellData(table, getRow("HD", 1, payRollCollaboratorDetails));

        table.addCell(this.getCellData("Semana 2", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        this.addCellData(table, getRow("HD", 2, payRollCollaboratorDetails));


        table.addCell(this.getCellData("Total", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, 10, null));

        Double total=0d;
        for (PayRollCollaboratorDetail prcd : payRollCollaboratorDetails){
            total += prcd.getTotal();
        }
        table.addCell(this.getCellData(StringHelper.formatNumberCurrency(total), TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        doc.add(table);
    }


    private void generateDeducciones(Document doc,List<PayRollCollaboratorDeductionDetail> payRollCollaboratorDeductionDetailList) throws DocumentException {

        Paragraph titleP = new Paragraph("Deducciones");
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        titleP.add(dottedline);
        doc.add(titleP);


        float[] columnWidths = {5, 5, 5};
        PdfPTable tableNomina = new PdfPTable(columnWidths);
        tableNomina.setWidthPercentage(100);
        tableNomina.setSpacingBefore(10);

        tableNomina.addCell(this.getCellHeader("Descripción", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("%", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Total", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        Double total=0d;
        for (PayRollCollaboratorDeductionDetail prdd:payRollCollaboratorDeductionDetailList) {
            tableNomina.addCell(this.getCellData(prdd.getDescription(),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
            tableNomina.addCell(this.getCellData(StringHelper.formatNumberCurrency(prdd.getPorcent()),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
            tableNomina.addCell(this.getCellData(StringHelper.formatNumberCurrency(prdd.getMount()),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
            total+=prdd.getMount();
        }

        tableNomina.addCell(this.getCellData("Total", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, 2, null));
        tableNomina.addCell(this.getCellData(StringHelper.formatNumberCurrency(total),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));



        doc.add(tableNomina);
    }

    private void generateSalarioBruto(Document doc,PayRollDetail payRollDetail) throws DocumentException {

        float[] columnWidths = {5, 5};
        PdfPTable tableNomina = new PdfPTable(columnWidths);
        tableNomina.setWidthPercentage(100);
        tableNomina.setSpacingBefore(10);

        // write tableNomina header
        tableNomina.addCell(this.getCellHeader("Descripción", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Total", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        tableNomina.addCell(this.getCellData("Salario Bruto",TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellData(StringHelper.formatNumberCurrency(payRollDetail.getCrudeSalary()),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));


        doc.add(tableNomina);
    }

    private void generateDevolucionesReintegrosHeader(Document doc,PayRollDetail payRollDetail) throws DocumentException {

        Paragraph titleP = new Paragraph("Devoluciones / Reintegros");
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        titleP.add(dottedline);
        doc.add(titleP);


        float[] columnWidths = {5, 5, 5};
        PdfPTable tableNomina = new PdfPTable(columnWidths);
        tableNomina.setWidthPercentage(100);
        tableNomina.setSpacingBefore(10);

        tableNomina.addCell(this.getCellHeader("Nómina", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Fecha Inicio", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader(" Fecha Final", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));


        tableNomina.addCell(this.getCellData(StringHelper.getNumber2Str(payRollDetail.getCollaborator().getNumberPayroll()),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellData(StringHelper.getDate2String(payRollDetail.getStartDate()),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellData(StringHelper.getDate2String(payRollDetail.getEndDate()),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        doc.add(tableNomina);
    }


    public static final String DEVOLUTION = "DEVOLUCION";

    private void generateDevReintegro(Document doc, List<PayRollCollaboratorRefundDevolutionDetail> payRollCollaboratorRefundDevolutionDetails) throws DocumentException {

        float[] columnWidths = {1,5, 3};
        PdfPTable tableNomina = new PdfPTable(columnWidths);
        tableNomina.setWidthPercentage(100);
        tableNomina.setSpacingBefore(10);

        // write tableNomina header
        tableNomina.addCell(this.getCellHeader("No. Cuota", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Descripción", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));
        tableNomina.addCell(this.getCellHeader("Monto", FONT_HEADER_SIZE, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        //Double total=0d;
        String simbol = "";

        for (PayRollCollaboratorRefundDevolutionDetail dr:payRollCollaboratorRefundDevolutionDetails) {
            StringBuffer coutas = new StringBuffer(StringHelper.getNumber2Str(dr.getCoutaNumber()));
            coutas.append(" de ").append(dr.getMaxCuota());

            tableNomina.addCell(this.getCellData( coutas.toString() ,TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
            tableNomina.addCell(this.getCellData(dr.getDescription(),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
            simbol=(StringHelper.DEVOLUTION.equals(dr.getType())? "-" : "");
            tableNomina.addCell( this.getCellData( simbol +  StringHelper.formatNumberCurrency(dr.getMount()),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));
            //total +=dr.getMount();
        }
        //tableNomina.addCell(this.getCellData("Total", TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER,2, null));
        //tableNomina.addCell(this.getCellData(StringHelper.formatNumberCurrency(total),TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        doc.add(tableNomina);
    }






    private PayRollCollaboratorDetail getRow(String type, Integer index, List<PayRollCollaboratorDetail> payRollCollaboratorDetails) {
        PayRollCollaboratorDetail ret = null;
        for (PayRollCollaboratorDetail prcd : payRollCollaboratorDetails) {
            if (prcd.getType().equals(type) && prcd.getIndice().equals(index)) {
                ret = prcd;
                break;
            }
        }
        return ret;
    }



    private PdfPCell getCellHeader(String tile, Float size, int style, BaseColor color, int align, Integer colSpan, Integer rowSpan) {
        Font f = new Font(Font.FontFamily.HELVETICA, size, style, color);
        PdfPCell cell = new PdfPCell(new Phrase(tile, f));
        cell.setBackgroundColor(BACK_GROUND_TABLE_HEADER_COLOR);
        cell.setHorizontalAlignment(align);
        //cell.setPadding(5);
        cell.setPaddingBottom(5);
        if (colSpan != null)
            cell.setColspan(colSpan);
        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }


    private PdfPCell getCellData(String data, Float size, int style, GrayColor color, int align, Integer colSpan, Integer rowSpan) {
        Font f = new Font(Font.FontFamily.HELVETICA, size, style, BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(data, f));
        cell.setHorizontalAlignment(align);
        //cell.setPadding(5);
        cell.setPaddingBottom(5);
        if (colSpan != null)
            cell.setColspan(colSpan);
        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }

    private void addCellData(PdfPTable table, PayRollCollaboratorDetail prcd) {
        String data = StringHelper.formatNumberCurrency(prcd.getDay1()!=null?prcd.getDay1():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getDay2()!=null?prcd.getDay2():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getDay3()!=null?prcd.getDay3():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getDay4()!=null?prcd.getDay4():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getDay5()!=null?prcd.getDay5():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getDay6()!=null?prcd.getDay6():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getDay7()!=null?prcd.getDay7():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getCantidad()!=null?prcd.getCantidad():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

        data = StringHelper.formatNumberCurrency(prcd.getTotal()!=null?prcd.getTotal():0d);
        table.addCell(this.getCellData(data, TABLE_BODY_FONT_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, null));

    }
}
