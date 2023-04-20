package dev.anar.exchangeratesapi.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValType {

    @XmlAttribute(name = "Type")
    private String type;

    @XmlElement(name = "Valute")
    private List<Valute> valutes;

}