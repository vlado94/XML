//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.12 at 12:12:37 AM CEST 
//


package com.nalogzaprenos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="nalogZaPrenos" type="{http://nalogZaPrenos.com}nalog_za_prenos"/>
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
    "nalogZaPrenos"
})
@XmlRootElement(name = "getNalogZaPrenosRequest")
public class GetNalogZaPrenosRequest {

    @XmlElement(required = true)
    protected NalogZaPrenos nalogZaPrenos;

    /**
     * Gets the value of the nalogZaPrenos property.
     * 
     * @return
     *     possible object is
     *     {@link NalogZaPrenos }
     *     
     */
    public NalogZaPrenos getNalogZaPrenos() {
        return nalogZaPrenos;
    }

    /**
     * Sets the value of the nalogZaPrenos property.
     * 
     * @param value
     *     allowed object is
     *     {@link NalogZaPrenos }
     *     
     */
    public void setNalogZaPrenos(NalogZaPrenos value) {
        this.nalogZaPrenos = value;
    }

}
