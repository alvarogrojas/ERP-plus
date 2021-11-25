package com.ndl.erp.views;



import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.InvoiceDetail;
import com.ndl.erp.util.StringHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class BillViewBuilder extends PdfiTextView {


    public static final int MAX_ROWS = 23;
    public static final float DETAILS_FONT_SIZE = 7f;

    GeneralParameter nombreEmpresaParameter;
    private String website;

    public void getPdf(Map<String, Object> data, String path) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(path));
        doc.open();
        GeneralParameter gp = (GeneralParameter) data.get("gp");
        this.nombreEmpresaParameter =  (GeneralParameter) data.get("nombreEmpresa");
        this.website =  (String) data.get("website");
        Invoice invoice = (Invoice) data.get("invoice");
        Set<InvoiceDetail> invoices = invoice.getDetails();
        Map<String, Object> ebill = (Map<String, Object>) data.get("ebill");

        this.paintHeader(doc, invoice, gp);

        this.paintClient(doc, invoice);

        this.paintDetails(doc, invoice, invoices, MAX_ROWS - 1);

        this.paintFooter(doc, ebill, invoice.getId());

        doc.close();
    }

    public void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> data = (Map<String, Object>) model.get("data");
        GeneralParameter gp = (GeneralParameter) data.get("gp");
        Invoice invoice = (Invoice) data.get("invoice");
        this.website =  (String) data.get("website");
        Set<InvoiceDetail> invoices = invoice.getDetails();
        Map<String, Object> ebill = (Map<String, Object>) data.get("ebill");

        this.paintHeader(doc, invoice, gp);

        this.paintClient(doc, invoice);

        this.paintDetails(doc, invoice, invoices, MAX_ROWS);

        this.paintFooter(doc, ebill,invoice.getId());

    }


   public static final String LOGO_INVOICE = "/img/Logo-Factura.png";
//    public static final String LOGO_INVOICE = "resources/img/Logo-Factura.png";

    protected Image getLogo() throws IOException, DocumentException {
//        Image image = Image.getInstance(LOGO_INVOICE);
        URL urlFondo= this.getClass().getResource(LOGO_INVOICE);
        Image image = Image.getInstance(urlFondo);
        image.scaleAbsolute(150, 80);
        image.setAlignment(Image.ALIGN_LEFT);
        return image;
    }


    private void paintHeader(Document doc, Invoice invoice, GeneralParameter gp) throws IOException, DocumentException {
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
            if (this.cmpText(this.nombreEmpresaParameter.getVal(), line.trim(), true)) {
                subTable.addCell(this.getCellData(line, 10f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            } else if (this.cmpText("www.", line.trim(), false)) {
                subTable.addCell(this.getCellData(line, 8f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            } else {
                subTable.addCell(this.getCellData(line, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            }
        }
        table.addCell(subTable);
        table.addCell(this.getNOrdenDate(invoice.getNumber().toString(), StringHelper.FORMAT_DATES.getDateInitMonth(invoice.getDate())));
        doc.add(table);
    }

    private boolean cmpText(String origen, String target, boolean eq) {
        return eq ? origen.equalsIgnoreCase(target) : target.startsWith(origen);
    }

    /**
     * Tabla intermedia
     *
     * @param doc
     * @param invoice
     * @throws IOException
     * @throws DocumentException
     */
    private void paintClient(Document doc, Invoice invoice) throws IOException, DocumentException {
        float[] columnWidths = {4, 5, 4, 5};
        PdfPTable mainTable = new PdfPTable(columnWidths);
        mainTable.setWidthPercentage(100);
        mainTable.setSpacingBefore(10);

        mainTable.addCell(this.getCellHeader("CLIENTE", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(invoice.getClient().getName(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));
        mainTable.addCell(this.getCellHeader("IDENTIFICACIÓN", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(invoice.getClient().getEnterpriceId(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));
        mainTable.addCell(this.getCellHeader("DIRECCIÓN", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(invoice.getClient().getAddress(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));
        mainTable.addCell(this.getCellHeader("TELÉFONO", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(invoice.getClient().getPhone(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, null));

        mainTable.addCell(this.getCellData(" ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 4, null));

        mainTable.addCell(this.getCellHeader("ORDEN DE COMPRA", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(invoice.getPoc().getOrderNumber(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, Element.ALIGN_MIDDLE, null, null));
        mainTable.addCell(this.getCellHeader("TÉRMINOS DE PAGO", 11f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        mainTable.addCell(this.getCellData(invoice.getCreditDays().toString() + "   Días ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, Element.ALIGN_MIDDLE, null, null));


        doc.add(mainTable);
    }




    private void paintDetails(Document doc, Invoice invoice, Set<InvoiceDetail> details, int totalRows) throws IOException, DocumentException {


        float[] columnWidths = {15, 95, 25, 30, 30};
        PdfPTable main = new PdfPTable(columnWidths);
        main.setTotalWidth(PageSize.LETTER.getWidth() - 89);
        main.setLockedWidth(true);

        float[] columnTWidths = {15, 100, 20, 30, 30};
        PdfPTable totales = new PdfPTable(columnTWidths);

        totales.setTotalWidth(PageSize.LETTER.getWidth() - 89);
        totales.setLockedWidth(true);

        float[] colWidthsDetails = {7, 70, 15, 20, 20, 20,20,25};
        PdfPTable tDetails = new PdfPTable(colWidthsDetails);
        tDetails.setWidthPercentage(100);
        //tDetails.setSpacingBefore(10);

        String currency = invoice.getCurrency().getSimbol() + " ";

        // write tableNomina header
        main.addCell(this.getCellData(" ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 5, null));

        tDetails.addCell(this.getCellHeader(" ", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("DESCRIPCIÓN", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("CANTIDAD", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("PRECIO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("DESCUENTO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("IVA %", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("EXONERADO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("PRECIO TOTAL", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        Integer index = 1;
        for (InvoiceDetail d : details) {
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
            tDetails.addCell(this.getCellTable(this.blankRows(rowspan), 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, 6, null));
        }

        totales.addCell(this.getCellHeader("OBSERVACIONES", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, 3, null));
        totales.addCell(this.getCellHeader("SUB TOTAL", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(invoice.getSubTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        totales.addCell(this.getCellData(invoice.getObservaciones() !=null ? invoice.getObservaciones() : "", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 3, 4));

        totales.addCell(this.getCellHeader("EXONERADO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(invoice.getExonerated()==null?0:invoice.getExonerated(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        totales.addCell(this.getCellHeader("DESCUENTO", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        if(invoice.getDiscountTotal() > 1)
            totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(invoice.getDiscountTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
        else
            totales.addCell(this.getCellData(currency + "0" + StringHelper.formatNumberCurrency(invoice.getDiscountTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));


        totales.addCell(this.getCellHeader("IVA", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        if(invoice.getIv() > 1)
          totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(invoice.getIv(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
        else
          totales.addCell(this.getCellData(currency + "0" + StringHelper.formatNumberCurrency(invoice.getIv(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        totales.addCell(this.getCellHeader("TOTAL", DETAILS_FONT_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(invoice.getTotal(), true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));


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

    private void paintFooter(Document doc, Map<String, Object> ebill, Integer id) throws IOException, DocumentException {
        float[] columnWidths = {10, 4};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5);
        table.getDefaultCell().setBorder(0);

//        table.addCell(this.getCellData("Autorizado por la DGT mediante resolución 1197 publicada de la Gaceta N° 171 del 5 de Setiembre de 1997", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        table.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, 2, null));

        table.addCell(this.getCellHeader("NOMBRE Y FIRMA, RECIBIDO CONFORME", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, null, null));
        table.addCell(this.getCellHeader("No. CEDULA.", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        table.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, null, 1, null));
        table.addCell(this.getCellData("   ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, null, 1, null));


        table.addCell(this.getCellHeader("CLAVE", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, null, null));
        table.addCell(this.getCellHeader("CONSECUTIVO", 10F, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        String clave = (String) ebill.get("clave");
        String consecutive = (String) ebill.get("consecutive");
        table.addCell(this.getCellData(ebill.get("clave").toString() + " ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 1, null));
        table.addCell(this.getCellData(ebill.get("consecutive").toString() + " ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 1, null));


        doc.add(table);

        float[] columnWidths1 = {10, 2};
        PdfPTable table1 = new PdfPTable(columnWidths1);
        table1.setWidthPercentage(100);
        table1.setSpacingBefore(10);
        table1.getDefaultCell().setBorder(0);

        float[] columnWidthEBill = {5, 15, 5, 20};
        PdfPTable eBillTable = new PdfPTable(columnWidthEBill);
        eBillTable.setWidthPercentage(100);
        eBillTable.setSpacingBefore(10);
        eBillTable.getDefaultCell().setBorder(0);
        eBillTable.getDefaultCell().setBorder(0);
        eBillTable.addCell(this.getCellData(ebill.get("leyenda").toString(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 4, null));
        //doc.add(eBillTable);
        table1.addCell(eBillTable);

        /***
         *   QR
         */

        if (website!=null) {
            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(website + "/factura-view/" + id, 500, 500, null);
            Image codeQrImage = barcodeQRCode.getImage();
//            doc.add(codeQrImage);
            codeQrImage.scaleAbsolute(50, 50);
//            codeQrImage.scaleAbsolute(100, 100);
            table1.addCell(codeQrImage);
        }
        /***
         *   QR
         */

        doc.add(table1);
    }


    private PdfPTable getNOrdenDate(String orden, String date) {
        PdfPTable main = new PdfPTable(1);
        main.setWidthPercentage(100);
        main.getDefaultCell().setBorder(0);
        main.getDefaultCell().setUseBorderPadding(false);
        //main.setSpacingBefore(10);


        PdfPTable ordenTable = new PdfPTable(1);
        ordenTable.setWidthPercentage(100);
        ordenTable.addCell(this.getCellHeader("No. DE FACTURA", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
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
