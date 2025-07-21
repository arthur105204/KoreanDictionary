package vn.edu.fpt.koreandictionary.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class KRDictSense {
    @XmlElement(name = "sense_order")
    private Integer senseOrder;

    @XmlElement(name = "definition")
    private String definition;

    @XmlElement(name = "translation")
    private List<KRDictTranslation> translations;

    // Getters and setters
    public Integer getSenseOrder() { return senseOrder; }
    public void setSenseOrder(Integer senseOrder) { this.senseOrder = senseOrder; }
    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }
    public List<KRDictTranslation> getTranslations() { return translations; }
    public void setTranslations(List<KRDictTranslation> translations) { this.translations = translations; }
} 