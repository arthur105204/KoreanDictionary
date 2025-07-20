package vn.edu.fpt.koreandictionary.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "translation", strict = false)
public class Translation {
    @Element(name = "trans_lang", required = false)
    private String transLang;

    @Element(name = "trans_word", required = false)
    private String transWord;

    @Element(name = "trans_dfn", required = false)
    private String transDfn;

    public String getTransLang() {
        return transLang;
    }

    public void setTransLang(String transLang) {
        this.transLang = transLang;
    }

    public String getTransWord() {
        return transWord;
    }

    public void setTransWord(String transWord) {
        this.transWord = transWord;
    }

    public String getTransDfn() {
        return transDfn;
    }

    public void setTransDfn(String transDfn) {
        this.transDfn = transDfn;
    }
} 