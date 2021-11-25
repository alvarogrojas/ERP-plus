package com.ndl.erp.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.InvoiceNotaCredito;
import com.ndl.erp.domain.InvoiceNotaCreditoDetail;
import com.ndl.erp.util.StringHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class NoteCeditViewBuilder extends PdfiTextView {


    public static final int MAX_ROWS = 23;
    public static final float DETAILS_FONT_SIZE = 7f;
    public GeneralParameter nombreEmpresa;



    public void getPdf(Map<String, Object> data, String path) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(path));
        doc.open();
        GeneralParameter gp = (GeneralParameter) data.get("gp");
        InvoiceNotaCredito inc = (InvoiceNotaCredito) data.get("noteCredit");
        this.nombreEmpresa = (GeneralParameter) data.get("empInfo");

        List<InvoiceNotaCreditoDetail> details = inc.getDetails();
        Map<String, Object> ebill = (Map<String, Object>) data.get("ebill");

        this.paintHeader(doc, inc, gp);

        this.paintClient(doc, inc);

        this.paintDetails(doc, inc, details, MAX_ROWS - 1);

        this.paintFoother(doc, ebill);

        doc.close();
    }




    public void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = (Map<String, Object>) model.get("data");
        GeneralParameter gp = (GeneralParameter) data.get("gp");
        this.nombreEmpresa = (GeneralParameter) data.get("empInfo");
        InvoiceNotaCredito invoice = (InvoiceNotaCredito) data.get("invoice");
        List<InvoiceNotaCreditoDetail> invoices = invoice.getDetails();
        Map<String, Object> ebill = (Map<String, Object>) data.get("ebill");

        this.paintHeader(doc, invoice, gp);

        this.paintClient(doc, invoice);

        this.paintDetails(doc, invoice, invoices, MAX_ROWS);

        this.paintFoother(doc, ebill);

    }


    public static final String LOGO_INVOICE = "/img/Logo-Factura.png";
//    public static final String LOGO_INVOICE = "resources/img/Logo-Factura.png";

//    protected Image getLogo() throws IOException, DocumentException {
//        Image image = Image.getInstance(LOGO_INVOICE);
//        image.scaleAbsolute(150, 80);
//        image.setAlignment(Image.ALIGN_LEFT);
//        return image;
//    }

    protected Image getLogo() throws IOException, DocumentException {
        URL urlFondo= this.getClass().getResource(LOGO_INVOICE);
        Image image = Image.getInstance(urlFondo);
        image.scaleAbsolute(150, 80);
        image.setAlignment(Image.ALIGN_LEFT);
        return image;
    }



    private void paintHeader(Document doc, InvoiceNotaCredito note, GeneralParameter gp) throws IOException, DocumentException {
        float[] columnWidths = {8, 10, 5};

        PdfPTable subTable = new PdfPTable(1);
        subTable.setSpacingBefore(0);
        subTable.setSpacingAfter(0);
        subTable.getDefaultCell().setBorder(0);

        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.getDefaultCell().setBorder(0);

        table.addCell(this.getCellImage(this.getLogo(), Element.ALIGN_LEFT, false, null, null));

        String[] address = gp.getDescription().split("##");
        for (String line : address) {
            if (this.cmpText(this.nombreEmpresa.getVal(), line.trim(), true)) {
                subTable.addCell(this.getCellData(line, 10f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            } else if (this.cmpText("www.", line.trim(), false)) {
                subTable.addCell(this.getCellData(line, 8f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            } else {
                subTable.addCell(this.getCellData(line, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            }
        }
        table.addCell(subTable);
        table.addCell(this.getNOrdenDate(note.getNumber().toString(), StringHelper.FORMAT_DATES.getDateInitMonth(note.getDate())));
        doc.add(table);
    }

    private boolean cmpText(String origen, String target, boolean eq) {
        return eq ? origen.equalsIgnoreCase(target) : target.startsWith(origen);
    }

    /**
     * Tabla intermedia
     *
     * @param doc
     * @param note
     * @throws IOException
     * @throws DocumentException
     */
    private void paintClient(Document doc, InvoiceNotaCredito note) throws IOException, DocumentException {
        float[] columnWidths = {4, 5, 4, 5};
        PdfPTable mainTable = new PdfPTable(columnWidths);
        mainTable.setWidthPercentage(100);
        mainTable.setSpacingBefore(10);

        mainTable.addCell(this.getCellHeader("CLIENTE", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(note.getClient().getName(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));
        mainTable.addCell(this.getCellHeader("IDENTIFICACIÓN", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(note.getClient().getEnterpriceId(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));
        mainTable.addCell(this.getCellHeader("DIRECCIÓN", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(note.getClient().getAddress(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));
        mainTable.addCell(this.getCellHeader("TELÉFONO", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(note.getClient().getPhone(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));

        mainTable.addCell(this.getCellData(" ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 4, null));

        mainTable.addCell(this.getCellHeader("ORDEN DE COMPRA", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        //mainTable.addCell(this.getCellData(note.getPoc().getOrderNumber(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, Element.ALIGN_MIDDLE, null, null));
        mainTable.addCell(this.getCellHeader("TÉRMINOS DE PAGO", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        //mainTable.addCell(this.getCellData(note.getCreditDays().toString() + "   Días ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, Element.ALIGN_MIDDLE, null, null));


        doc.add(mainTable);
    }




    private void paintDetails(Document doc, InvoiceNotaCredito note, List<InvoiceNotaCreditoDetail> details, int totalRows) throws IOException, DocumentException {


        float[] columnWidths = {15, 95, 25, 30, 30};
        PdfPTable main = new PdfPTable(columnWidths);
        main.setTotalWidth(PageSize.LETTER.getWidth() - 89);
        main.setLockedWidth(true);

        float[] columnTWidths = {15, 100, 20, 30, 30};
        PdfPTable totales = new PdfPTable(columnTWidths);

        totales.setTotalWidth(PageSize.LETTER.getWidth() - 89);
        totales.setLockedWidth(true);

//        float[] colWidthsDetails = {10, 85, 15, 25, 15, 20,25};
        float[] colWidthsDetails = {7, 70, 15, 20, 20, 20,20,25};
        PdfPTable tDetails = new PdfPTable(colWidthsDetails);
        tDetails.setWidthPercentage(100);

        String currency = note.getCurrency().getSimbol() + " ";

        // write tableNomina header
        main.addCell(this.getCellData(" ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 5, null));

        tDetails.addCell(this.getCellHeader("LÍNEA", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("DESCRIPCIÓN", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("CANTIDAD", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("PRECIO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("DESCUENTO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        tDetails.addCell(this.getCellHeader("IVA %", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("EXONERADO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("PRECIO TOTAL", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        Integer index = 1;
        for (InvoiceNotaCreditoDetail d : details) {
            tDetails.addCell(this.getCellData((index++).toString(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(d.getDescription() != null ? d.getDescription().replace("\n", "") : " ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(StringHelper.formatNumberCurrency(d.getQuantity(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(d.getPrice(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(d.getDiscountAmount(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));

            tDetails.addCell(this.getCellData( StringHelper.formatNumberCurrency(d.getIva().getTaxPorcent(), true) + " %" , FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData( currency + StringHelper.formatNumberCurrency(d.getExonerated()==null?0:d.getExonerated(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));

            tDetails.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(d.getTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));
        }

        Integer rowspan = null;
        if (!details.isEmpty()) {
            rowspan = totalRows - details.size();
            tDetails.addCell(this.getCellTable(this.blankRows(rowspan), 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, 5, null));
        }

//        totales.addCell(this.getCellData(" ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, 3, null));
//        totales.addCell(this.getCellHeader("SUB TOTAL", 10f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
//        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getSubTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
//
//        totales.addCell(this.getCellData(" ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, 3, null));
//        totales.addCell(this.getCellHeader("I.V", 10f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
//        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getIv(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
//
//        totales.addCell(this.getCellData(" ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, 3, null));
//        totales.addCell(this.getCellHeader("TOTAL", 10f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
//        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        totales.addCell(this.getCellHeader("OBSERVACIONES", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, 3, null));
        totales.addCell(this.getCellHeader("SUB TOTAL", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getSubTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        totales.addCell(this.getCellData(note.getObservaciones() !=null ? note.getObservaciones() : "", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, 4));

        totales.addCell(this.getCellHeader("EXONERADO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getExonerated()==null?0:note.getExonerated(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        totales.addCell(this.getCellHeader("DESCUENTO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        if(note.getDiscountTotal() > 1)
            totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getDiscountTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
        else
            totales.addCell(this.getCellData(currency + "0" + StringHelper.formatNumberCurrency(note.getDiscountTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));



        totales.addCell(this.getCellHeader("IVA", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        if(note.getIv() > 1)
            totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getIv(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
        else
            totales.addCell(this.getCellData(currency + "0" + StringHelper.formatNumberCurrency(note.getIv(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        totales.addCell(this.getCellHeader("TOTAL", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(note.getTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));



        main.addCell(this.getCellTable(tDetails, 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, 5, null));
        doc.add(main);
        doc.add(totales);
    }


    private PdfPTable blankRows(int rows) throws IOException, DocumentException {
        float[] columnWidths = {1, 5, 2, 2, 2};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.getDefaultCell().setBorder(0);
        table.addCell(this.getCellData("-------- Última Línea ------", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, 5, null));
        for (int i = 1; i < rows; i++) {
            table.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, 5, null));
        }
        return table;
    }

    private void paintFoother(Document doc, Map<String, Object> ebill) throws IOException, DocumentException {
        float[] columnWidths = {10, 4};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.getDefaultCell().setBorder(0);

//        table.addCell(this.getCellData("Autorizado por la DGT mediante resolución 1197 publicada de la Gaceta N° 171 del 5 de Setiembre de 1997", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        table.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, 2, null));

        table.addCell(this.getCellHeader("NOMBRE Y FIRMA, RECIBIDO CONFORME", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, null, null));
        table.addCell(this.getCellHeader("No. CEDULA.", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        table.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, null, 1, null));
        table.addCell(this.getCellData("   ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, null, 1, null));


        table.addCell(this.getCellHeader("Clave", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, null, null));
        table.addCell(this.getCellHeader("Consecutivo", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, null, null));

        table.addCell(this.getCellData(ebill.get("clave").toString(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 1, null));
        table.addCell(this.getCellData(ebill.get("consecutive").toString(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 1, null));


        doc.add(table);


        float[] columnWidthEBill = {5, 15, 8, 20};
        PdfPTable eBillTable = new PdfPTable(columnWidthEBill);
        eBillTable.setWidthPercentage(100);
        eBillTable.setSpacingBefore(10);
        eBillTable.getDefaultCell().setBorder(0);
        eBillTable.addCell(this.getCellData(ebill.get("leyenda").toString(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 4, null));
        doc.add(eBillTable);
    }


    private PdfPTable getNOrdenDate(String orden, String date) {
        PdfPTable main = new PdfPTable(1);
        main.setWidthPercentage(100);
        main.getDefaultCell().setBorder(0);
        main.getDefaultCell().setUseBorderPadding(false);
        //main.setSpacingBefore(10);


        PdfPTable ordenTable = new PdfPTable(1);
        ordenTable.setWidthPercentage(100);
        ordenTable.addCell(this.getCellHeader("No. DE NOTA CREDITO", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        ordenTable.addCell(this.getCellData(orden, 10f, Font.NORMAL, BaseColor.RED, Element.ALIGN_CENTER, null, PdfPCell.BOX, null, null, null));


        PdfPTable dateTable = new PdfPTable(1);
        dateTable.setWidthPercentage(100);
        dateTable.addCell(this.getCellHeader("FECHA", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        dateTable.addCell(this.getCellData(date, 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, null, null, null));

        main.addCell(ordenTable);
        main.addCell(dateTable);

        return main;
    }


}
