//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.12 at 05:18:45 PM CEST 
//


package com.presek;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ZaglavljePreseka complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZaglavljePreseka">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brojRacuna" type="{http://presek.com}Str18"/>
 *         &lt;element name="datumNaloga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="brojPreseka" type="{http://presek.com}Number2"/>
 *         &lt;element name="prethodnoStanje" type="{http://presek.com}Decimal15-2"/>
 *         &lt;element name="brojPromenaUKorist" type="{http://presek.com}AccountNumber"/>
 *         &lt;element name="ukupnoUKorist" type="{http://presek.com}Decimal15-2"/>
 *         &lt;element name="brojPromenaNaTeret" type="{http://presek.com}AccountNumber"/>
 *         &lt;element name="ukupnoNaTeret" type="{http://presek.com}Decimal15-2"/>
 *         &lt;element name="novoStanje" type="{http://presek.com}Decimal15-2"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZaglavljePreseka", propOrder = {
    "brojRacuna",
    "datumNaloga",
    "brojPreseka",
    "prethodnoStanje",
    "brojPromenaUKorist",
    "ukupnoUKorist",
    "brojPromenaNaTeret",
    "ukupnoNaTeret",
    "novoStanje"
})
public class ZaglavljePreseka {

    @XmlElement(required = true)
    protected String brojRacuna;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datumNaloga;
    @XmlElement(required = true)
    protected BigInteger brojPreseka;
    @XmlElement(required = true)
    protected BigDecimal prethodnoStanje;
    @XmlElement(required = true)
    protected BigInteger brojPromenaUKorist;
    @XmlElement(required = true)
    protected BigDecimal ukupnoUKorist;
    @XmlElement(required = true)
    protected BigInteger brojPromenaNaTeret;
    @XmlElement(required = true)
    protected BigDecimal ukupnoNaTeret;
    @XmlElement(required = true)
    protected BigDecimal novoStanje;

    /**
     * Gets the value of the brojRacuna property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojRacuna() {
        return brojRacuna;
    }

    /**
     * Sets the value of the brojRacuna property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojRacuna(String value) {
        this.brojRacuna = value;
    }

    /**
     * Gets the value of the datumNaloga property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatumNaloga() {
        return datumNaloga;
    }

    /**
     * Sets the value of the datumNaloga property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatumNaloga(XMLGregorianCalendar value) {
        this.datumNaloga = value;
    }

    /**
     * Gets the value of the brojPreseka property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrojPreseka() {
        return brojPreseka;
    }

    /**
     * Sets the value of the brojPreseka property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrojPreseka(BigInteger value) {
        this.brojPreseka = value;
    }

    /**
     * Gets the value of the prethodnoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrethodnoStanje() {
        return prethodnoStanje;
    }

    /**
     * Sets the value of the prethodnoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrethodnoStanje(BigDecimal value) {
        this.prethodnoStanje = value;
    }

    /**
     * Gets the value of the brojPromenaUKorist property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrojPromenaUKorist() {
        return brojPromenaUKorist;
    }

    /**
     * Sets the value of the brojPromenaUKorist property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrojPromenaUKorist(BigInteger value) {
        this.brojPromenaUKorist = value;
    }

    /**
     * Gets the value of the ukupnoUKorist property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupnoUKorist() {
        return ukupnoUKorist;
    }

    /**
     * Sets the value of the ukupnoUKorist property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupnoUKorist(BigDecimal value) {
        this.ukupnoUKorist = value;
    }

    /**
     * Gets the value of the brojPromenaNaTeret property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBrojPromenaNaTeret() {
        return brojPromenaNaTeret;
    }

    /**
     * Sets the value of the brojPromenaNaTeret property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBrojPromenaNaTeret(BigInteger value) {
        this.brojPromenaNaTeret = value;
    }

    /**
     * Gets the value of the ukupnoNaTeret property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUkupnoNaTeret() {
        return ukupnoNaTeret;
    }

    /**
     * Sets the value of the ukupnoNaTeret property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUkupnoNaTeret(BigDecimal value) {
        this.ukupnoNaTeret = value;
    }

    /**
     * Gets the value of the novoStanje property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNovoStanje() {
        return novoStanje;
    }

    /**
     * Sets the value of the novoStanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNovoStanje(BigDecimal value) {
        this.novoStanje = value;
    }

}
