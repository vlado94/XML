//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.12 at 05:18:45 PM CEST 
//


package com.zahtevZaDobijanjeNaloga;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nalog.Nalog;


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
 *         &lt;element name="zahtevZaDobijanjeIzvoda" type="{http://zahtevZaDobijanjeIzvoda.com}zahtevZaDobijanjeIzvoda"/>
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
    "nalog"
})
@XmlRootElement(name = "getZahtevZaDobijanjeNalogaResponse")
public class GetZahtevZaDobijanjeNalogaResponse {

	@XmlElement(required = true)
    protected List<Nalog> nalog;
    /**
     * Gets the value of the zahtevZaDobijanjeNaloga property.
     * 
     * @return
     *     possible object is
     *     {@link ZahtevZaDobijanjeNaloga }
     *     
     */
    public List<Nalog> getNalog() {
        return nalog;
    }

    /**
     * Sets the value of the zahtevZaDobijanjeIzvoda property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZahtevZaDobijanjeIzvoda }
     *     
     */
    public void setNalog(List<Nalog> value) {
        this.nalog = value;
    }

}
