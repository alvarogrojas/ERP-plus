//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.09 at 11:07:40 AM CST 
//


package com.ndl.erp.fe.v43.mr;

import com.ndl.erp.fe.dtos.Emisor;
import com.ndl.erp.fe.dtos.Receptor;
import com.ndl.erp.fe.ComprobanteElectronicoBase;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Clave">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\d{50,50}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NumeroCedulaEmisor">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="12"/>
 *               &lt;pattern value="\d{9,12}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FechaEmisionDoc" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Mensaje">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DetalleMensaje" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="160"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MontoTotalImpuesto" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="18"/>
 *               &lt;fractionDigits value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CodigoActividad" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CondicionImpuesto" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="01"/>
 *               &lt;enumeration value="02"/>
 *               &lt;enumeration value="03"/>
 *               &lt;enumeration value="04"/>
 *               &lt;enumeration value="05"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MontoTotalImpuestoAcreditar" type="{https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor}DecimalDineroType" minOccurs="0"/>
 *         &lt;element name="MontoTotalDeGastoAplicable" type="{https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor}DecimalDineroType" minOccurs="0"/>
 *         &lt;element name="TotalFactura">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;totalDigits value="18"/>
 *               &lt;fractionDigits value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NumeroCedulaReceptor">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="12"/>
 *               &lt;pattern value="\d{9,12}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NumeroConsecutivoReceptor">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *               &lt;pattern value="\d{20,20}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "clave",
    "numeroCedulaEmisor",
    "fechaEmisionDoc",
    "mensaje",
    "detalleMensaje",
    "montoTotalImpuesto",
    "codigoActividad",
    "condicionImpuesto",
    "montoTotalImpuestoAcreditar",
    "montoTotalDeGastoAplicable",
    "totalFactura",
    "numeroCedulaReceptor",
    "numeroConsecutivoReceptor",
    "signature"
})
@XmlRootElement(name = "MensajeReceptor", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor")
public class MensajeReceptor extends ComprobanteElectronicoBase {

    @XmlElement(name = "Clave", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor", required = true)
    protected String clave;
    @XmlElement(name = "NumeroCedulaEmisor", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor", required = true)
    protected String numeroCedulaEmisor;
    @XmlElement(name = "FechaEmisionDoc", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaEmisionDoc;
    @XmlElement(name = "Mensaje", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor", required = true)
    protected BigInteger mensaje;
    @XmlElement(name = "DetalleMensaje", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor")
    protected String detalleMensaje;
    @XmlElement(name = "MontoTotalImpuesto", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor")
    protected BigDecimal montoTotalImpuesto;
    @XmlElement(name = "CodigoActividad", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor")
    protected String codigoActividad;
    @XmlElement(name = "CondicionImpuesto", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor")
    protected String condicionImpuesto;
    @XmlElement(name = "MontoTotalImpuestoAcreditar", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor")
    protected BigDecimal montoTotalImpuestoAcreditar;
    @XmlElement(name = "MontoTotalDeGastoAplicable", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor")
    protected BigDecimal montoTotalDeGastoAplicable;
    @XmlElement(name = "TotalFactura", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor", required = true)
    protected BigDecimal totalFactura;
    @XmlElement(name = "NumeroCedulaReceptor", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor", required = true)
    protected String numeroCedulaReceptor;
    @XmlElement(name = "NumeroConsecutivoReceptor", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor", required = true)
    protected String numeroConsecutivoReceptor;
    @XmlElement(name = "Signature", required = true)
    protected SignatureType signature;

    @XmlTransient
    private Emisor emisor;

    @XmlTransient
    private Receptor receptor;

    /**
     * Gets the value of the clave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClave() {
        return clave;
    }

    /**
     * Sets the value of the clave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClave(String value) {
        this.clave = value;
    }

    /**
     * Gets the value of the numeroCedulaEmisor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCedulaEmisor() {
        return numeroCedulaEmisor;
    }

    /**
     * Sets the value of the numeroCedulaEmisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCedulaEmisor(String value) {
        this.numeroCedulaEmisor = value;
    }

    /**
     * Gets the value of the fechaEmisionDoc property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaEmisionDoc() {
        return fechaEmisionDoc;
    }

    /**
     * Sets the value of the fechaEmisionDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaEmisionDoc(XMLGregorianCalendar value) {
        this.fechaEmisionDoc = value;
    }

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMensaje(BigInteger value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the detalleMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalleMensaje() {
        return detalleMensaje;
    }

    /**
     * Sets the value of the detalleMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalleMensaje(String value) {
        this.detalleMensaje = value;
    }

    /**
     * Gets the value of the montoTotalImpuesto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoTotalImpuesto() {
        return montoTotalImpuesto;
    }

    /**
     * Sets the value of the montoTotalImpuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoTotalImpuesto(BigDecimal value) {
        this.montoTotalImpuesto = value;
    }

    /**
     * Gets the value of the codigoActividad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoActividad() {
        return codigoActividad;
    }

    /**
     * Sets the value of the codigoActividad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoActividad(String value) {
        this.codigoActividad = value;
    }

    /**
     * Gets the value of the condicionImpuesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondicionImpuesto() {
        return condicionImpuesto;
    }

    /**
     * Sets the value of the condicionImpuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondicionImpuesto(String value) {
        this.condicionImpuesto = value;
    }

    /**
     * Gets the value of the montoTotalImpuestoAcreditar property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoTotalImpuestoAcreditar() {
        return montoTotalImpuestoAcreditar;
    }

    /**
     * Sets the value of the montoTotalImpuestoAcreditar property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoTotalImpuestoAcreditar(BigDecimal value) {
        this.montoTotalImpuestoAcreditar = value;
    }

    /**
     * Gets the value of the montoTotalDeGastoAplicable property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoTotalDeGastoAplicable() {
        return montoTotalDeGastoAplicable;
    }

    /**
     * Sets the value of the montoTotalDeGastoAplicable property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoTotalDeGastoAplicable(BigDecimal value) {
        this.montoTotalDeGastoAplicable = value;
    }

    /**
     * Gets the value of the totalFactura property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    /**
     * Sets the value of the totalFactura property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalFactura(BigDecimal value) {
        this.totalFactura = value;
    }

    /**
     * Gets the value of the numeroCedulaReceptor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCedulaReceptor() {
        return numeroCedulaReceptor;
    }

    /**
     * Sets the value of the numeroCedulaReceptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCedulaReceptor(String value) {
        this.numeroCedulaReceptor = value;
    }

    /**
     * Gets the value of the numeroConsecutivoReceptor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroConsecutivoReceptor() {
        return numeroConsecutivoReceptor;
    }

    /**
     * Sets the value of the numeroConsecutivoReceptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroConsecutivoReceptor(String value) {
        this.numeroConsecutivoReceptor = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }

    public Receptor getReceptor() {
        return receptor;
    }

    public void setReceptor(Receptor receptor) {
        this.receptor = receptor;
    }
}
