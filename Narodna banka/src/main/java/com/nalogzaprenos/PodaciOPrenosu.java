//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.12 at 01:06:14 AM CEST 
//


package com.nalogzaprenos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{http://nalogZaPrenos.com}iznos"/>
 *         &lt;element ref="{http://nalogZaPrenos.com}podaci_o_zaduzenju"/>
 *         &lt;element ref="{http://nalogZaPrenos.com}podaci_o_odobrenju"/>
 *         &lt;element name="potpis">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}base64Binary">
 *               &lt;minLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="sifra_placanja" default="153">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;totalDigits value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "iznos",
    "podaciOZaduzenju",
    "podaciOOdobrenju",
    "potpis"
})
@XmlRootElement(name = "podaci_o_prenosu")
public class PodaciOPrenosu {

    @XmlElement(required = true)
    protected Iznos iznos;
    @XmlElement(name = "podaci_o_zaduzenju", required = true)
    protected TPodaci podaciOZaduzenju;
    @XmlElement(name = "podaci_o_odobrenju", required = true)
    protected TPodaci podaciOOdobrenju;
    @XmlElement(required = true)
    protected byte[] potpis;
    @XmlAttribute(name = "sifra_placanja")
    protected Integer sifraPlacanja;

    /**
     * Gets the value of the iznos property.
     * 
     * @return
     *     possible object is
     *     {@link Iznos }
     *     
     */
    public Iznos getIznos() {
        return iznos;
    }

    /**
     * Sets the value of the iznos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Iznos }
     *     
     */
    public void setIznos(Iznos value) {
        this.iznos = value;
    }

    /**
     * Gets the value of the podaciOZaduzenju property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaci }
     *     
     */
    public TPodaci getPodaciOZaduzenju() {
        return podaciOZaduzenju;
    }

    /**
     * Sets the value of the podaciOZaduzenju property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaci }
     *     
     */
    public void setPodaciOZaduzenju(TPodaci value) {
        this.podaciOZaduzenju = value;
    }

    /**
     * Gets the value of the podaciOOdobrenju property.
     * 
     * @return
     *     possible object is
     *     {@link TPodaci }
     *     
     */
    public TPodaci getPodaciOOdobrenju() {
        return podaciOOdobrenju;
    }

    /**
     * Sets the value of the podaciOOdobrenju property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPodaci }
     *     
     */
    public void setPodaciOOdobrenju(TPodaci value) {
        this.podaciOOdobrenju = value;
    }

    /**
     * Gets the value of the potpis property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPotpis() {
        return potpis;
    }

    /**
     * Sets the value of the potpis property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPotpis(byte[] value) {
        this.potpis = value;
    }

    /**
     * Gets the value of the sifraPlacanja property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSifraPlacanja() {
        if (sifraPlacanja == null) {
            return  153;
        } else {
            return sifraPlacanja;
        }
    }

    /**
     * Sets the value of the sifraPlacanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSifraPlacanja(Integer value) {
        this.sifraPlacanja = value;
    }

}
