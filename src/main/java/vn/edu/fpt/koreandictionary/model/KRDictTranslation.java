package vn.edu.fpt.koreandictionary.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class KRDictTranslation {
    @XmlElement(name = "trans_lang")
    private String transLang;

    @XmlElement(name = "trans_word")
    private String transWord;

    @XmlElement(name = "trans_dfn")
    private String transDfn;

    // Getters and setters
    public String getTransLang() { return transLang; }
    public void setTransLang(String transLang) { this.transLang = transLang; }
    public String getTransWord() { return transWord; }
    public void setTransWord(String transWord) { this.transWord = transWord; }
    public String getTransDfn() { return transDfn; }
    public void setTransDfn(String transDfn) { this.transDfn = transDfn; }
} 