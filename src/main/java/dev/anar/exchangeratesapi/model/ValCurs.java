package dev.anar.exchangeratesapi.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ValCurs")
@Data
public class ValCurs {

    @XmlAttribute(name = "Date")
    private String date;

    @XmlAttribute(name = "Name")
    private String name;

    @XmlElement(name = "ValType")
    private List<ValType> valTypes;

}
