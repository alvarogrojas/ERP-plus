package com.ndl.erp.views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.PurchaseOrderProvider;
import com.ndl.erp.domain.PurchaseOrderProviderDetail;
import com.ndl.erp.util.StringHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Map;


public class POPViewBuilder extends PdfiTextView {
    public GeneralParameter nombreEmpresaParameter;
    public static final String LOGO_INVOICE = "/img/Logo-Factura.png";
//    public static final String LOGO_INVOICE = "resources/img/Logo-Factura.png";

//    protected Image getLogo() throws IOException, DocumentException {
//        Image image = Image.getInstance(LOGO_INVOICE);
//        image.scaleAbsolute(150, 80);
//        image.setAlignment(Image.ALIGN_LEFT);
//        return image;
//    }

//    protected Image getLogo() throws IOException, DocumentException {
//        //ClassLoader.getSystemClassLoader().
//        URL urlFondo= this.getClass().getResource( "/logo/logo.png" );
//        Image image = Image.getInstance(urlFondo);
//        image.scaleAbsoluteHeight(30);
//        image.scaleAbsoluteWidth(30);
//        image.setAlignment(Image.ALIGN_RIGHT);
//        return image;
//    }

    protected Image getLogo() throws IOException, DocumentException {
        URL urlFondo= this.getClass().getResource(LOGO_INVOICE);
        Image image = Image.getInstance(urlFondo);
//        Image image = Image.getInstance(LOGO_INVOICE);
//        Image image = Image.getInstance(LOGO_INVOICE);
        image.scaleAbsolute(150, 80);
        image.setAlignment(Image.ALIGN_LEFT);
        return image;
    }

    public void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        doc.setMargins(2, 2, 500, 2);
        Map<String, Object> data = (Map<String, Object>) model.get("data");

//        GeneralParameter gp = (GeneralParameter) data.get("gp");
        GeneralParameter gp = (GeneralParameter) model.get("gp");
        this.nombreEmpresaParameter = (GeneralParameter) model.get("nombreEmpresa");
        PurchaseOrderProvider pop = (PurchaseOrderProvider) model.get("pop");
//        PurchaseOrderProvider pop = (PurchaseOrderProvider) data.get("pop");
//        List<PurchaseOrderProviderDetail> rds = pop.getDetails();
//        Set<PurchaseOrderProviderDetail> rds = (Set<PurchaseOrderProviderDetail>) data.get("popds");
        int length = pop.getObservations()!=null ? (int) Math.ceil(pop.getObservations().length()/120): 1;

        this.paintHeader(doc, pop, gp);


        this.paintTable2(doc, pop);

        this.paintDetails(doc, pop, length);

        this.paintObservations(doc, pop.getObservations());
    }


    private void paintHeader(Document doc, PurchaseOrderProvider pop, GeneralParameter gp) throws IOException, DocumentException {

        PdfPTable subTable = new PdfPTable(1);
        subTable.setSpacingBefore(0);
        subTable.setSpacingAfter(0);
        subTable.getDefaultCell().setBorder(0);

        float[] columnWidths = {7, 10, 7};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.getDefaultCell().setBorder(0);

        table.addCell(this.getCellImage(this.getLogo(), Element.ALIGN_LEFT,false,null,null));

        String[] address = gp.getDescription().split("##");

        for(String line : address) {
            if(this.cmpText(this.nombreEmpresaParameter.getVal(),line.trim(),true)){
                subTable.addCell(this.getCellData(line, 10f, Font.BOLD, BACK_GROUND_TABLE_HEADER_COLOR_INV, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            }else if(this.cmpText("www.",line.trim(),false)){
                subTable.addCell(this.getCellData(line, 9f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            }else {
                subTable.addCell(this.getCellData(line, 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            }
        }


        table.addCell(subTable);
        table.addCell(this.getTables(pop.getOrderNumber(), StringHelper.FORMAT_DATES.getDateInitMonth(pop.getDate())));
        doc.add(table);
    }

    /**
     * Tabla intermedia
     *
     * @param doc
     * @param pop
     * @throws IOException
     * @throws DocumentException
     */
    private void paintTable2(Document doc, PurchaseOrderProvider pop) throws IOException, DocumentException {
        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);
        mainTable.setSpacingBefore(10);
        //mainTable.getDefaultCell().setBorder(0);

        mainTable.addCell(this.forTable(pop));
        mainTable.addCell(this.deliveryTable(pop));
        doc.add(mainTable);
    }

    public static final int MAX_ROWS = 29;
    private void paintDetails(Document doc, PurchaseOrderProvider pop, int len) throws IOException, DocumentException {
        int calMaxRow = MAX_ROWS - (len == 0 ? 1: len);
        float[] columnWidths = {15,100, 20,20,30};
        PdfPTable main = new PdfPTable(columnWidths);
        main.setTotalWidth(PageSize.LETTER.getWidth()-89);
        main.setLockedWidth(true);

        float[] columnTWidths = {15,100, 20,30,30};
        PdfPTable totales = new PdfPTable(columnTWidths);

        totales.setTotalWidth(PageSize.LETTER.getWidth()-89);
        totales.setLockedWidth(true);

        PdfPTable tDetails = new PdfPTable(columnWidths);
        tDetails.setWidthPercentage(100);

        String currency = pop.getCurrency().getSimbol() + " ";

        main.addCell(this.getCellData(" ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,PdfPCell.NO_BORDER,null,5, null));


        // write tableNomina header
        tDetails.addCell(this.getCellHeader("LÍNEA", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("DESCRIPCIÓN", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("CANT.", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("PRECIO UNT.", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        tDetails.addCell(this.getCellHeader("PRECIO TOTAL", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));

        Integer index=1;
        int linesNumberReal =1;
        String descrip ="";
        int lines=0;
        for (PurchaseOrderProviderDetail d : pop.getDetails()) {
            if(d.getDescription()!=null){
                descrip = d.getDescription().replace("\n", "");
                lines=(int) Math.ceil(descrip.length()/46);
            }
            linesNumberReal+= lines == 0 ? 1:lines;
            tDetails.addCell(this.getCellData((index++).toString(), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(descrip, FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(StringHelper.formatNumberCurrency(d.getQuantity(),true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(d.getPrice(),true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));
            tDetails.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(d.getTotal(),true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.NO_BORDER, null, null, null));
        }

        Integer rowspan=null;
        if(!pop.getDetails().isEmpty()){
            rowspan = calMaxRow - linesNumberReal;//details.size();
            tDetails.addCell(this.getCellTable(this.blankRows(rowspan), 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.NO_BORDER,null,5, null));
        }

        totales.addCell(this.getCellData(" ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.NO_BORDER,null,3, null));
        totales.addCell(this.getCellHeader("SUB-TOTAL", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(pop.getSubTotal(),true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
        totales.addCell(this.getCellData(" ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.NO_BORDER,null,3, null));
        totales.addCell(this.getCellHeader("I.V", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(pop.getIv(),true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
        totales.addCell(this.getCellData(" ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.NO_BORDER,null,3, null));
        totales.addCell(this.getCellHeader("FLETE", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(pop.getFreight(),true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));
        totales.addCell(this.getCellData(" ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.NO_BORDER,null,3, null));
        totales.addCell(this.getCellHeader("TOTAL", FONT_HEADER_SIZE, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        totales.addCell(this.getCellData(currency + StringHelper.formatNumberCurrency(pop.getTotal(),true), FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, PdfPCell.BOX, null, null, null));

        main.addCell(this.getCellTable(tDetails,10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,5, null));
        doc.add(main);
        doc.add(totales);
    }


    private PdfPTable blankRows(int rows)  throws IOException, DocumentException{
        float[] columnWidths = {1,5, 2,2,2};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.getDefaultCell().setBorder(0);
        table.addCell(this.getCellData("-------- ÚLTIMA LÍNEA ------", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.NO_BORDER,null,5, null));
        for(int i=1; i<rows;i++){
            table.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.NO_BORDER,null,5, null));
        }
        return table;
    }

    private void paintObservations(Document doc, String Observation) throws IOException, DocumentException {
        PdfPTable main = new PdfPTable(5);
        main.setWidthPercentage(100);
        main.setSpacingBefore(10);
        // write tableNomina header
        main.addCell(this.getCellHeader("Observaciones", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        main.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.NO_BORDER,null,5, 2));
        main.addCell(this.getCellData(Observation!=null ? Observation.replace("\n", ""):"", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.BOX, null, 5, null));
        main.addCell(this.getCellData("    ", FONT_HEADER_SIZE, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.NO_BORDER,null,5, 2));
        doc.add(main);
    }

    private PdfPTable forTable(PurchaseOrderProvider pop) {
        float fontSize = 9f;
        float[] columnWidths = {4,10};
        PdfPTable main = new PdfPTable(columnWidths);
        main.setWidthPercentage(100);
        main.setSpacingBefore(0);
        main.getDefaultCell().setBorder(0);
        main.getDefaultCell().setUseBorderPadding(false);
        main.addCell(this.getCellHeader("PARA:", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.NO_BORDER, null, null));
        main.addCell(this.getCellData(pop.getSourceName(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, null, null));
        main.addCell(this.getCellData("Atención:" + pop.getSourceAttention(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        main.addCell(this.getCellData("Tel:" + pop.getSourcePhone(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        main.addCell(this.getCellData("Email:" + pop.getSourceEmail(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        main.addCell(this.getCellData("Referencia:" + pop.getSourceRef(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));

        return main;
    }

    private PdfPTable deliveryTable(PurchaseOrderProvider pop) {
        float fontSize = 9f;
        float[] columnWidths = {5,10};
        PdfPTable main = new PdfPTable(columnWidths);
        main.setWidthPercentage(100);
        main.getDefaultCell().setBorder(0);
        main.setSpacingBefore(0);
        main.getDefaultCell().setUseBorderPadding(false);
        main.addCell(this.getCellHeader("ENTREGAR A:", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.NO_BORDER, null, null));
        main.addCell(this.getCellData(" ", fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, null, null));
        main.addCell(this.getCellData(pop.getTargetName(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        main.addCell(this.getCellData("Contacto:" + pop.getTargetContact(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        main.addCell(this.getCellData("Tel:" + pop.getTargetPhone(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));
        main.addCell(this.getCellData("Email:" + pop.getTargetEmail(), fontSize, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, PdfPCell.NO_BORDER, null, 2, null));

        return main;
    }


    private PdfPTable getTables(String orden, String date) {
        PdfPTable main = new PdfPTable(1);
        main.setWidthPercentage(100);
        main.getDefaultCell().setBorder(0);
        main.getDefaultCell().setUseBorderPadding(false);
        //main.setSpacingBefore(10);


        PdfPTable ordenTable = new PdfPTable(1);
        ordenTable.setWidthPercentage(100);
        ordenTable.addCell(this.getCellHeader("ORDEN DE COMPRA Nº", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX, null, null));
        ordenTable.addCell(this.getCellData(orden, 10f, Font.NORMAL, BaseColor.RED, Element.ALIGN_CENTER, null, PdfPCell.BOX, null, null, null));


        PdfPTable dateTable = new PdfPTable(1);
        dateTable.setWidthPercentage(100);
        dateTable.addCell(this.getCellHeader("FECHA", 12f, Font.NORMAL, BACK_GROUND_TABLE_HEADER_COLOR_INV,TABLE_HEADER_FONT_COLOR, Element.ALIGN_LEFT, PdfPCell.BOX, null, null));
        dateTable.addCell(this.getCellData(date, 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null, PdfPCell.BOX, Element.ALIGN_CENTER, null, null));

        main.addCell(ordenTable);
        main.addCell(dateTable);

        return main;
    }


    private boolean cmpText(String origen, String target, boolean eq) {
        return eq ? origen.equalsIgnoreCase(target) : target.startsWith(origen);
    }

}
