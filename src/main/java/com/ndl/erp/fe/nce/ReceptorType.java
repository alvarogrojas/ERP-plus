//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.13 at 04:00:41 PM CST 
//


package com.ndl.erp.fe.nce;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceptorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceptorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nombre">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="80"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Identificacion" type="{https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica}IdentificacionType" minOccurs="0"/>
 *         &lt;element name="IdentificacionExtranjero" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NombreComercial" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="80"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Ubicacion" type="{https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica}UbicacionType" minOccurs="0"/>
 *         &lt;element name="Telefono" type="{https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica}TelefonoType" minOccurs="0"/>
 *         &lt;element name="Fax" type="{https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica}TelefonoType" minOccurs="0"/>
 *         &lt;element name="CorreoElectronico" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\s*\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*\s*"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceptorType", namespace = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica", propOrder = {
    "nombre",
    "identificacion",
    "identificacionExtranjero",
    "nombreComercial",
    "ubicacion",
    "telefono",
    "fax",
    "correoElectronico"
})
public class ReceptorType {

    @XmlElement(name = "Nombre", required = true)
    protected String nombre;
    @XmlElement(name = "Identificacion")
    protected IdentificacionType identificacion;
    @XmlElement(name = "IdentificacionExtranjero")
    protected String identificacionExtranjero;
    @XmlElement(name = "NombreComercial")
    protected String nombreComercial;
    @XmlElement(name = "Ubicacion")
    protected UbicacionType ubicacion;
    @XmlElement(name = "Telefono")
    protected TelefonoType telefono;
    @XmlElement(name = "Fax")
    protected TelefonoType fax;
    @XmlElement(name = "CorreoElectronico")
    protected String correoElectronico;

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the identificacion property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificacionType }
     *     
     */
    public IdentificacionType getIdentificacion() {
        return identificacion;
    }

    /**
     * Sets the value of the identificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificacionType }
     *     
     */
    public void setIdentificacion(IdentificacionType value) {
        this.identificacion = value;
    }

    /**
     * Gets the value of the identificacionExtranjero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificacionExtranjero() {
        return identificacionExtranjero;
    }

    /**
     * Sets the value of the identificacionExtranjero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificacionExtranjero(String value) {
        this.identificacionExtranjero = value;
    }

    /**
     * Gets the value of the nombreComercial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * Sets the value of the nombreComercial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreComercial(String value) {
        this.nombreComercial = value;
    }

    /**
     * Gets the value of the ubicacion property.
     * 
     * @return
     *     possible object is
     *     {@link UbicacionType }
     *     
     */
    public UbicacionType getUbicacion() {
        return ubicacion;
    }

    /**
     * Sets the value of the ubicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link UbicacionType }
     *     
     */
    public void setUbicacion(UbicacionType value) {
        this.ubicacion = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link TelefonoType }
     *     
     */
    public TelefonoType getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelefonoType }
     *     
     */
    public void setTelefono(TelefonoType value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link TelefonoType }
     *     
     */
    public TelefonoType getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelefonoType }
     *     
     */
    public void setFax(TelefonoType value) {
        this.fax = value;
    }

    /**
     * Gets the value of the correoElectronico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Sets the value of the correoElectronico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreoElectronico(String value) {
        this.correoElectronico = value;
    }

}
