//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.01 at 03:08:59 PM CET 
//

package com;

import java.sql.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1 extends XmlAdapter<String, Date>{

    public Date unmarshal(String value) {
    	java.util.Date utilDate = MyDatatypeConverter.parseDate(value);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    	
        return sqlDate;
    }

    public String marshal(Date value) {
        return (MyDatatypeConverter.printDate(value));
    }

}
