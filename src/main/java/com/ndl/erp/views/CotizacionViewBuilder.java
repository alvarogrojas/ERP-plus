package com.ndl.erp.views;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ndl.erp.domain.CotizacionDetalle;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.dto.CotizacionDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ndl.erp.constants.InvoiceConstants.*;

@Component
public class CotizacionViewBuilder extends com.ndl.erp.views.PdfiTextView {


    final int PAGE_SIZE =12;

    final float MARGIN_OF_ONE_CM = 28.8f;

    static final Float TABLE_BODY_FONT_SIZE = 6f;

    static final Float FONT_HEADER_SIZE = 8f;


    @Autowired
    private ApplicationContext applicationContext;

    private UserServiceImpl userService;

    static final BaseColor BACK_GROUND_TABLE_HEADER_COLOR = new BaseColor(23,169,227);


    static final BaseColor TABLE_HEADER_FONT_COLOR = new BaseColor(35,59,102);
    static final BaseColor TABLE_BODY_FONT_COLOR = new BaseColor(35,59,102);
    static final BaseColor TABLE_BORDER_COLOR = new BaseColor(31,78,121);
    static final BaseColor TOTAL_BK_COLOR = new BaseColor(218,227,243);
    static final BaseColor ORIGINAL_FONT_COLOR = new BaseColor(255,0,0);
    static final BaseColor STANDARD_FONT_COLOR = new BaseColor(0,0,0);
    static final BaseColor NAVY_BLUE_FONT_COLOR = new BaseColor(32,42,68);
    static final BaseColor LIGHT_BLUE_BK_COLOR = new BaseColor(31,111,229);
    static final BaseColor YELLOW_BK_COLOR = new BaseColor(255,255,13);
    static final BaseColor LIGHT_BLUE_FONT_COLOR = new BaseColor(32,112,229);
    static final BaseColor RED_FONT_COLOR = new BaseColor(248,45,40);
    static final BaseColor WHITE_FONT_COLOR = new BaseColor(255,255,255);
    static final BaseColor STANDARD_TABLE_BORDER_COLOR = new BaseColor(0,0,0);

    static final String ESTIMATE_LOGO = "/img/Logo-Factura.png";
    private Integer lines;

    private static final float DETAIL_FONT_SIZE = 6f;


    public static final String DEST = "app.pdf";
    private static final String TITLE_IMG = "title.png";
    private static final String LOGO_IMG = "logo.png";
    private String currencySymbol;


    private NumberFormat formatNumber = new DecimalFormat("#,##0.00");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat largeDateFormat = new SimpleDateFormat("dd MMMM, yyyy");

    private CotizacionDTO cotizacionDto;

    private PdfWriter pdfWriter;

    private Map<String, Object> model;

    private HttpServletRequest request;
    private  HttpServletResponse response;



    @Override
    protected Image getLogo() throws IOException, DocumentException {
        URL urlFondo= this.getClass().getResource(ESTIMATE_LOGO);
        Image image = Image.getInstance(urlFondo);
        image.scaleAbsolute(100,50);
        image.setAlignment(Image.ALIGN_LEFT);
        return image;
    }


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        this.cotizacionDto = (CotizacionDTO) model.get("cotizacionDTO");

        this.pdfWriter = writer;
        this.model = model;
        this.request = request;
        this.response = response;
        if (this.cotizacionDto.getCurrent()== null){
            throw new GeneralInventoryException("No se encontró la cotización!");
        }
        if (this.cotizacionDto.getCurrent().getCurrency() == null){
            throw new GeneralInventoryException("La cotización no tienen moneda!");
        }

        this.currencySymbol = this.cotizacionDto.getCurrent().getCurrency().getSimbol() + " ";


        createPdf(doc);

    }

    public void createPdf(Document document)
            throws Exception, DocumentException {

        document.setMargins(5, 5, 5, 0);

        document.open();

        float[] columnWidths = {10};
        PdfPTable rootTable = initPdfTable(columnWidths);
        rootTable.addCell(createHeaderTable(document));
        rootTable.addCell(createClienteTable(document));
        rootTable.addCell(createDetalleCotizacionTable(document));
        rootTable.addCell(createPiePaginaCotizacion(document));
        document.add(rootTable);
    }


    private PdfPTable initPdfTable(float[] columnWidths) {
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setPaddingLeft(3);
        rootTable.getDefaultCell().setPaddingRight(3);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidthBottom(3);
        rootTable.getDefaultCell().setBorderWidthTop(3);
        rootTable.getDefaultCell().setBorderWidthLeft(3);
        rootTable.getDefaultCell().setBorderWidthRight(3);
        rootTable.getDefaultCell().setBorderColor(STANDARD_TABLE_BORDER_COLOR);
        return rootTable;
    }

    public GeneralParameter findCompanyInfoParameter(List<GeneralParameter> p, String name){
        GeneralParameter g = null;
        for (GeneralParameter param : p){
           if (param.getName().equals(name)) {
               g = param;
               break;
           }
       }
       return g;
    }


    public PdfPTable createHeaderTable(Document d) throws IOException, DocumentException {
        // Tabla de una columna
        float[] columnWidths = {10};
        float[] columnWidthso1 = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(1);

        rootTable.getDefaultCell().setBorderColor(STANDARD_TABLE_BORDER_COLOR);

        //Tabla auxiliar  de una columna
        PdfPTable rootTableo1 = new PdfPTable(columnWidthso1);
        rootTableo1.setWidthPercentage(100);
        rootTableo1.getDefaultCell().setBorder(0);
        rootTableo1.getDefaultCell().setBorderWidth(0);
        rootTableo1.getDefaultCell().setPadding(1);


        float[] columnWidths1 = {1.5F, 3F, 2.5F};
        //Tabla para el logo y el titulo
        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.getDefaultCell().setBorder(0);
        table2.getDefaultCell().setPadding(0);

        //Celda en blanco para completar las filas cuando hace falta
        PdfPCell blankCellTable2 =    getCellData("          ", 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null);


        //celda para el logo
        PdfPCell cellImage = getCellImage(getLogo(), Element.ALIGN_LEFT,false,null,6);
        cellImage.setPaddingTop(1);


        //Celda para el encabezado del numero de cotizacion
        PdfPCell celdaTituloNumeroCotizacion;
        celdaTituloNumeroCotizacion = getCellData("COTIZACIÓN", 8f, Font.BOLD, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);
        celdaTituloNumeroCotizacion.setPaddingTop(10);

        GeneralParameter nombreEmpresaParam = findCompanyInfoParameter(this.cotizacionDto.getEmpresaInfoParameters(),INVOICE_PARAMETER_NOMBRE_EMPRESA);

        //Celda para Nombre de la empresa
        String textoNombreEmpresa = nombreEmpresaParam.getVal();
        PdfPCell celdaTextoNombreEmpresa;

        celdaTextoNombreEmpresa = getCellData(textoNombreEmpresa,
                8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null);
        celdaTextoNombreEmpresa.setPaddingTop(1);

        GeneralParameter cedulaJuridicaEmpresaParam = findCompanyInfoParameter(this.cotizacionDto.getEmpresaInfoParameters(),INVOICE_PARAMETER_CEDULA_EMPRESA );

        //Celda para Datos Empresa
        String textoDatosEmpresa1 = cedulaJuridicaEmpresaParam.getVal();

        PdfPCell celdaDatosEmpresa1;
        celdaDatosEmpresa1 = getCellData(textoDatosEmpresa1, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);
        celdaDatosEmpresa1.setPaddingTop(1);

        GeneralParameter direccionEmpresaParam = findCompanyInfoParameter(this.cotizacionDto.getEmpresaInfoParameters(),INVOICE_PARAMETER_ADDRESS_EMPRESA);


        //Celda para Datos Empresa
        String textoDatosEmpresa2 = direccionEmpresaParam.getVal();
        PdfPCell celdaDatosEmpresa2;
        celdaDatosEmpresa2 = getCellData(textoDatosEmpresa2, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);

        //Celda para Datos Empresa
        String textoDatosEmpresa3 = "";
        PdfPCell celdaDatosEmpresa3;
        celdaDatosEmpresa3 = getCellData(textoDatosEmpresa3, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);


        GeneralParameter correoEmpresaParam = findCompanyInfoParameter(this.cotizacionDto.getEmpresaInfoParameters(),INVOICE_PARAMETER_EMAIL_EMPRESA);

        //Celda para Datos Empresa
        String textoDatosEmpresa4 = correoEmpresaParam.getVal();
        PdfPCell celdaDatosEmpresa4;
        celdaDatosEmpresa4= getCellData(textoDatosEmpresa4, 8f, Font.NORMAL, LIGHT_BLUE_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);

        GeneralParameter telefonoEmpresaParam = findCompanyInfoParameter(this.cotizacionDto.getEmpresaInfoParameters(),INVOICE_PARAMETER_PHONE_EMPRESA);

        //Celda para Datos Empresa
        String textoDatosEmpresa5= telefonoEmpresaParam.getVal();
        PdfPCell celdaDatosEmpresa5;
        celdaDatosEmpresa5 = getCellData(textoDatosEmpresa5, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);




        PdfPCell celdaNumeroCotizacion;
        celdaNumeroCotizacion = getCellData(this.cotizacionDto.getCurrent().getCotizacionNumber(), 12f, Font.BOLD, RED_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.BOX,null,null, null);


        String textoTituloFecha = "Fecha        \n" +  "     Emision         Vencimiento\n";
        PdfPCell celdaTituloFecha;
        celdaTituloFecha = getCellData(textoTituloFecha, 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR, PdfPCell.BOX,null,null, 2);

        Date fechaEmision = this.cotizacionDto.getCurrent().getFechaEmision();
        Date fechaVencimiento = this.cotizacionDto.getCurrent().getFechaVencimiento();

        String textoFechaEmision = dateFormat.format(fechaEmision);
        String textofechaVencimiento = dateFormat.format(fechaVencimiento);

        PdfPCell celdaFechaEmisionVencimiento;
        celdaFechaEmisionVencimiento = getCellData(textoFechaEmision + "       " + textofechaVencimiento, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.BOX,null,null, null);



        //Se agregan las celdas a la tabla
        table2.addCell(cellImage);
        table2.addCell(celdaTextoNombreEmpresa);
        table2.addCell(celdaTituloNumeroCotizacion);
        table2.addCell(celdaDatosEmpresa1);
        table2.addCell(celdaNumeroCotizacion);
        table2.addCell(celdaDatosEmpresa2);
        table2.addCell(celdaTituloFecha);
        table2.addCell(celdaDatosEmpresa3);
        table2.addCell(celdaDatosEmpresa4);
        table2.addCell(celdaFechaEmisionVencimiento);
        table2.addCell(celdaDatosEmpresa5);
        table2.setSpacingBefore(1);
        table2.setSpacingAfter(1);

        //Se agrega la tabla a la tabla auxiliar
        rootTableo1.addCell(table2);

        //Se agrega la tabla auxiliar a la tabla raiz
        rootTable.addCell(rootTableo1);

        return rootTable;
    }



    public PdfPTable createClienteTable(Document d) throws IOException, DocumentException,Exception {
        // Tabla de una columna
        float[] columnWidths = {10};
        float[] columnWidthso1 = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(1);

        rootTable.getDefaultCell().setBorderColor(STANDARD_TABLE_BORDER_COLOR);

        //Tabla auxiliar  de una columna
        PdfPTable rootTableo1 = new PdfPTable(columnWidthso1);
        rootTableo1.setWidthPercentage(100);
        rootTableo1.getDefaultCell().setBorder(1);
        rootTableo1.getDefaultCell().setBorderWidth(0);
        rootTableo1.getDefaultCell().setPadding(0);


        float[] columnWidths1 = {2F, 3F, 2F, 3F};
        //Tabla datos del cliente
        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.getDefaultCell().setBorder(PdfPCell.BOX);
        table2.getDefaultCell().setPadding(0);

        //Celda en blanco para completar las filas cuando hace falta
        PdfPCell blankCellTable2 =    getCellData("          ", 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null);

        //Celda para Asunto
        PdfPCell celdaTituloAsunto;
        celdaTituloAsunto = getCellData("Asunto: ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null);

        //Celda para texto del asunto
        String textoAsunto = this.cotizacionDto.getCurrent().getAsunto();
        PdfPCell celdaTextoAsunto;

        celdaTextoAsunto = getCellData(textoAsunto, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,4, 0);

        //Celda para contacto del cliente
        PdfPCell celdaTituloAtencion;
        celdaTituloAtencion = getCellData("Atención: ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null);

        //Celda para nombre del contacto
        String textoNombreContacto;
        if (this.cotizacionDto.getCurrent().getContactClient() != null) {
            textoNombreContacto =  this.cotizacionDto.getCurrent().getContactClient().getName();
        }else {
            textoNombreContacto = "No disponible";
        }

        PdfPCell celdaTextoNombreContacto;

        celdaTextoNombreContacto = getCellData(textoNombreContacto, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,null, 0);


        //Celda para terminos venta
        PdfPCell celdaTituloTerminoVenta;
        celdaTituloTerminoVenta = getCellData("Crédito a: ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null, null,null,null, null);

        //Celda para texto del asunto
        String textoTerminoVenta = this.cotizacionDto.getCurrent().getCreditDays().toString();
        PdfPCell celdaTextoTerminoVenta;

        celdaTextoTerminoVenta = getCellData(textoTerminoVenta, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, 0);


        //Celda para Cliente
        PdfPCell celdaTituloCliente;
        celdaTituloCliente = getCellData("Cliente: ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null, null,null,null, null);

        //Celda para texto del cliente
        String textoCliente = this.cotizacionDto.getCurrent().getClient().getName();
        PdfPCell celdaTextoCliente;

        celdaTextoCliente = getCellData(textoCliente, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,null, 0);


        //Celda para Vendedor
        PdfPCell celdaTituloVendedor;
        celdaTituloVendedor = getCellData("Vendedor: ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null, null,null,null, null);

        //Celda para texto del vendedor
        String textoVendedor = this.cotizacionDto.getCurrent().getVendedor().getDisplayName();
        PdfPCell celdaTextoVendedor;

        celdaTextoVendedor = getCellData(textoVendedor, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, 0);


        //Celda para email contacto
        PdfPCell celdaTituloEmailContacto;
        celdaTituloEmailContacto = getCellData("Email : ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null, null,null,null, null);


        //Celda para texto del email del contacto
        String textoEmailContacto;
        if (this.cotizacionDto.getCurrent().getContactClient() != null) {
            textoEmailContacto =  this.cotizacionDto.getCurrent().getContactClient().getEmail();
        }else {
            textoEmailContacto = "No disponible";
        }
        PdfPCell celdaTextoEmailContacto;
        celdaTextoEmailContacto = getCellData(textoEmailContacto, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,null, 0);


        //Celda para texto para telefono del vendedor
        PdfPCell celdaTituloTelefonoVendedor;
        celdaTituloTelefonoVendedor = getCellData("Telefono : ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null, null,null,null, null);


        String textoTelefonoVendedor = this.cotizacionDto.getCurrent().getTelefonoVendedor();

        PdfPCell celdaTextoTelefonoVendedor;
        celdaTextoTelefonoVendedor = getCellData(textoTelefonoVendedor, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, 0);



        //Celda para telefono contacto
        PdfPCell celdaTituloTelefonoContacto;
        celdaTituloTelefonoContacto = getCellData("Teléfono : ", 8f,Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null, null,null,null, null);

        //Celda para texto para telefono contacto empresa
        String textoTelefonoContacto;
        if (this.cotizacionDto.getCurrent().getContactClient() != null) {
            textoTelefonoContacto =  this.cotizacionDto.getCurrent().getContactClient().getPhone1();
        }else {
            textoTelefonoContacto = "No disponible";
        }
        PdfPCell celdaTextoTelefonoContacto;
        celdaTextoTelefonoContacto = getCellData(textoTelefonoContacto, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,null, 0);


        //Celda para Vendedor
        PdfPCell celdaTituloEmailVendedor;
        celdaTituloEmailVendedor = getCellData("Email: ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null, null,null,null, null);

        //Celda para texto del email del vendedor
        String textoemailVendedor = this.cotizacionDto.getCurrent().getVendedor().getEmail();
        PdfPCell celdaTextoEmailVendedor;
        celdaTextoEmailVendedor = getCellData(textoemailVendedor, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, 0);

        //Celda para direccion cliente
        PdfPCell celdaTituloDireccionCliente;
        celdaTituloDireccionCliente = getCellData("Dirección: ", 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null);

        //Celda para texto de la dirección del cliente
        String textoDireccionCliente = this.cotizacionDto.getCurrent().getClient().getAddress();
        PdfPCell celdaTextoDireccionCliente;
        celdaTextoDireccionCliente = getCellData(textoDireccionCliente, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,4, 0);


        //Se agregan las celdas a la tabla
        table2.addCell(celdaTituloAsunto);
        table2.addCell(celdaTextoAsunto);
        table2.addCell(celdaTituloAtencion);
        table2.addCell(celdaTextoNombreContacto);
        table2.addCell(celdaTituloTerminoVenta);
        table2.addCell(celdaTextoTerminoVenta);
        table2.addCell(celdaTituloCliente);
        table2.addCell(celdaTextoCliente);
        table2.addCell(celdaTituloVendedor);
        table2.addCell(celdaTextoVendedor);
        table2.addCell(celdaTituloEmailContacto);
        table2.addCell(celdaTextoEmailContacto);
        table2.addCell(celdaTituloTelefonoVendedor);
        table2.addCell(celdaTextoTelefonoVendedor);
        table2.addCell(celdaTituloTelefonoContacto);
        table2.addCell(celdaTextoTelefonoContacto);
        table2.addCell(celdaTituloEmailVendedor);
        table2.addCell(celdaTextoEmailVendedor);
        table2.addCell(celdaTituloDireccionCliente);
        table2.addCell(celdaTextoDireccionCliente);


        table2.setSpacingBefore(1);
        table2.setSpacingAfter(1);

        //Se agrega la tabla a la tabla auxiliar
        rootTableo1.addCell(table2);

        //Se agrega la tabla auxiliar a la tabla raiz
        rootTable.addCell(rootTableo1);

        return rootTable;
    }




    public PdfPTable createDetalleCotizacionTable(Document d) throws IOException, DocumentException,Exception {
        // Tabla de una columna
        float[] columnWidths = {9};
        float[] columnWidthso1 = {9};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(1);

        rootTable.getDefaultCell().setBorderColor(STANDARD_TABLE_BORDER_COLOR);

        //Tabla auxiliar  de una columna
        PdfPTable rootTableo1 = new PdfPTable(columnWidthso1);
        rootTableo1.setWidthPercentage(100);
        rootTableo1.getDefaultCell().setBorder(1);
        rootTableo1.getDefaultCell().setBorderWidth(0);
        rootTableo1.getDefaultCell().setPadding(1);


        float[] columnWidths1 = {1f, 2f, 3f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
        //Tabla datos del cliente
        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.getDefaultCell().setBorder(PdfPCell.BOX);
        table2.getDefaultCell().setPadding(0);

        //Celda en blanco para completar las filas cuando hace falta
        PdfPCell blankCellTable2 =    getCellData("          ", 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null);

        //Celda titulo para la linea
        PdfPCell celdaTituloLinea;
        celdaTituloLinea = getCellData("Línea", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Celda titulo para codigo
        PdfPCell celdaTituloCodigo;
        celdaTituloCodigo = getCellData("Código", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Celda titulo para descripcion
        PdfPCell celdaTituloDescripcion;
        celdaTituloDescripcion = getCellData("Descripción", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);


        //Celda titulo para entrega
        PdfPCell celdaTituloEntrega;
        celdaTituloEntrega = getCellData("Entrega", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);


        //Celda titulo para cantidad
        PdfPCell celdaTituloCantidad;
        celdaTituloCantidad = getCellData("Cantidad", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Celda titulo para precio unitario
        PdfPCell celdaTituloPrecioUnitario;
        celdaTituloPrecioUnitario = getCellData("Precio Unitario", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Celda titulo para precio total
        PdfPCell celdaTituloPrecioTotal;
        celdaTituloPrecioTotal = getCellData("Sub Total", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);


        //Celda titulo para descuento
        PdfPCell celdaTituloDescuento;
        celdaTituloDescuento = getCellData("Descuento", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Celda titulo para Exonerado
        PdfPCell celdaTituloExonerado;
        celdaTituloExonerado = getCellData("Exonerado", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);



        //Celda titulo para Total
        PdfPCell celdaTituloTotal;
        celdaTituloTotal = getCellData("Total", 8f, Font.NORMAL, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);



        //Se agregan las celdas a la tabla
        table2.addCell(celdaTituloLinea);
        table2.addCell(celdaTituloCodigo);
        table2.addCell(celdaTituloDescripcion);
        table2.addCell(celdaTituloEntrega);
        table2.addCell(celdaTituloCantidad);
        table2.addCell(celdaTituloPrecioUnitario);
        table2.addCell(celdaTituloPrecioTotal);
        table2.addCell(celdaTituloDescuento);
        table2.addCell(celdaTituloExonerado);
        table2.addCell(celdaTituloTotal);

        List<CotizacionDetalle> cotizacionDetalle = this.cotizacionDto.getCurrent().getDetalles();


        //Ciclo para llenar la tabla con los datos de la cotización
        for (CotizacionDetalle c :  cotizacionDetalle){

            //Celda dato para la linea
            PdfPCell celdaDatoLinea;
            celdaDatoLinea = getCellData(c.getLineNumber().toString(), 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.BOX,null,null, null);

            //Celda Dato para codigo
            PdfPCell celdaDatoCodigo;
            celdaDatoCodigo = getCellData(c.getInventario().getProducto().getCodigo(), 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.BOX,null,null, null);

            //Celda dato para descripcion
            PdfPCell celdaDatoDescripcion;
            celdaDatoDescripcion = getCellData(c.getDescripcion(), 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.BOX,null,null, null);


            //Celda dato para entrega
            PdfPCell celdaDatoEntrega;
            celdaDatoEntrega = getCellData(c.getEntrega(), 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.BOX,null,null, null);

            //Celda dato para cantidad
            PdfPCell celdaDatoCantidad;
            celdaDatoCantidad = getCellData(formatNumber.format(c.getCantidad()), 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_CENTER, null,PdfPCell.BOX,null,null, null);

            //Celda dato para precio unitario
            PdfPCell celdaDatoPrecioUnitario;
            String precioUnitario = formatNumber.format(c.getPrecioUnitario());
            celdaDatoPrecioUnitario = getCellData(this.currencySymbol + precioUnitario, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);

            //Celda dato para subtotal
            PdfPCell celdaDatoPrecioTotal;
            String precioTotal = formatNumber.format(c.getSubTotal());
            celdaDatoPrecioTotal = getCellData(this.currencySymbol + precioTotal, 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);


            //Celda dato para descuento
            PdfPCell celdaDatoDescuento;
            String descuentoMonto = formatNumber.format(c.getDescuentoMonto());
            celdaDatoDescuento = getCellData(this.currencySymbol + descuentoMonto , 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);

            //Celda dato para Exonerado
            PdfPCell celdaDatoExonerado;
            String exonerated = formatNumber.format(c.getExonerated());
            celdaDatoExonerado = getCellData(this.currencySymbol + exonerated , 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);



            //Celda para total
            PdfPCell celdaDatoTotal;
            String datoTotal = formatNumber.format(c.getTotal());
            celdaDatoTotal = getCellData(this.currencySymbol + datoTotal  , 8f, Font.NORMAL, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);


            table2.addCell(celdaDatoLinea);
            table2.addCell(celdaDatoCodigo);
            table2.addCell(celdaDatoDescripcion);
            table2.addCell(celdaDatoEntrega);
            table2.addCell(celdaDatoCantidad);
            table2.addCell(celdaDatoPrecioUnitario);
            table2.addCell(celdaDatoPrecioTotal);
            table2.addCell(celdaDatoDescuento);
            table2.addCell(celdaDatoExonerado);
            table2.addCell(celdaDatoTotal);

        }



        table2.setSpacingBefore(1);
        table2.setSpacingAfter(1);

        //Se agrega la tabla a la tabla auxiliar
        rootTableo1.addCell(table2);

        //Se agrega la tabla auxiliar a la tabla raiz
        rootTable.addCell(rootTableo1);

        return rootTable;

    }




    public PdfPTable createPiePaginaCotizacion(Document d) throws IOException, DocumentException,Exception {
        // Tabla de una columna
        float[] columnWidths = {10};
        float[] columnWidthso1 = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(0);

        //Tabla auxiliar  de una columna
        PdfPTable rootTableo1 = new PdfPTable(columnWidthso1);
        rootTableo1.setWidthPercentage(100);
        rootTableo1.getDefaultCell().setBorder(0);
        rootTableo1.getDefaultCell().setBorderWidth(0);
        rootTableo1.getDefaultCell().setPadding(0);


        float[] columnWidths1 = {7f,  2f, 2f};
        //Tabla para datos firmas
        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.getDefaultCell().setBorder(0);
        table2.getDefaultCell().setPadding(0);


        //Celda en blanco para completar las filas cuando hace falta
        PdfPCell blankCellTable2 =    getCellData("          ", 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,PdfPCell.BOX,null,null, null);

        //Dato Texto1
        String datoTexto1 = this.cotizacionDto.getCurrent().getObservaciones();
        //Texto1
        String texto1 = "OBSERVACIONES" + "\n" + "\n" + datoTexto1;
        PdfPCell celdaTexto1;
        celdaTexto1 = getCellData(texto1, 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_LEFT, null,PdfPCell.BOX,null,null, 5);



        //Texto2
        String texto2 = "SUB TOTAL";
        PdfPCell celdaTexto2;
        celdaTexto2 = getCellData(texto2, 8f, Font.BOLD, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Dato Texto2
        String datoTexto2 = formatNumber.format(this.cotizacionDto.getCurrent().getSubTotal());
        PdfPCell celdaDatoTexto2;
        celdaDatoTexto2 = getCellData(this.currencySymbol + datoTexto2, 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);


        //Texto3
        String texto3 = "DESCUENTO";
        PdfPCell celdaTexto3;
        celdaTexto3 = getCellData(texto3, 8f, Font.BOLD, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Dato Texto3
        String datoTexto3 = formatNumber.format(this.cotizacionDto.getCurrent().getTotalDescuentos());
        PdfPCell celdaDatoTexto3;
        celdaDatoTexto3 = getCellData(this.currencySymbol + datoTexto3, 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);



        //Texto4
        String texto4 = "I.V.A";
        PdfPCell celdaTexto4;
        celdaTexto4 = getCellData(texto4, 8f, Font.BOLD, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Dato texto 4
        String datoTexto4 = formatNumber.format(this.cotizacionDto.getCurrent().getIva());
        PdfPCell celdaDatoTexto4;
        celdaDatoTexto4 = getCellData(this.currencySymbol + datoTexto4, 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);


        //Texto5
        String texto5 = "EXONERADO";
        PdfPCell celdaTexto5;
        celdaTexto5 = getCellData(texto5, 8f, Font.BOLD, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Dato texto 5
        String datoTexto5 = formatNumber.format(this.cotizacionDto.getCurrent().getExonerated());
        PdfPCell celdaDatoTexto5;
        celdaDatoTexto5 = getCellData(this.currencySymbol + datoTexto5, 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);


        //Texto6
        String texto6 = "TOTAL";
        PdfPCell celdaTexto6;
        celdaTexto6 = getCellData(texto6, 8f, Font.BOLD, WHITE_FONT_COLOR, Element.ALIGN_CENTER, LIGHT_BLUE_BK_COLOR,PdfPCell.BOX,null,null, null);

        //Dato texto 6
        String datoTexto6 = formatNumber.format(this.cotizacionDto.getCurrent().getTotal());
        PdfPCell celdaDatoTexto6;
        celdaDatoTexto6 = getCellData(this.currencySymbol + datoTexto6, 8f, Font.BOLD, STANDARD_FONT_COLOR, Element.ALIGN_RIGHT, null,PdfPCell.BOX,null,null, null);


        //Se agregan las celdas a la tabla
        table2.addCell(celdaTexto1);
        table2.addCell(celdaTexto2);
        table2.addCell(celdaDatoTexto2);
        table2.addCell(celdaTexto3);
        table2.addCell(celdaDatoTexto3);
        table2.addCell(celdaTexto4);
        table2.addCell(celdaDatoTexto4);
        table2.addCell(celdaTexto5);
        table2.addCell(celdaDatoTexto5);
        table2.addCell(celdaTexto6);
        table2.addCell(celdaDatoTexto6);

        table2.setSpacingBefore(1);
        table2.setSpacingAfter(1);

        //Se agrega la tabla a la tabla auxiliar
        rootTableo1.addCell(table2);

        //Se agrega la tabla auxiliar a la tabla raiz
        rootTable.addCell(rootTableo1);

        return rootTable;
    }




}
