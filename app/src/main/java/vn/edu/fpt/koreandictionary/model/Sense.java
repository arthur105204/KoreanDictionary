package vn.edu.fpt.koreandictionary.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "sense", strict = false)
public class Sense {
    @Element(name = "sense_order", required = false)
    private String senseOrder;

    @Element(name = "definition", required = false)
    private String definition;

    @ElementList(name = "translation", inline = true, required = false)
    private List<Translation> translations;

    public String getSenseOrder() {
        return senseOrder;
    }

    public void setSenseOrder(String senseOrder) {
        this.senseOrder = senseOrder;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
} 