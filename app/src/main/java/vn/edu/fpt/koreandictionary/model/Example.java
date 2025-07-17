package vn.edu.fpt.koreandictionary.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Example {
    @Element(name = "target_code", required = false)
    private String targetCode;

    @Element(name = "word", required = false)
    private String word;

    @Element(name = "sup_no", required = false)
    private String supNo;

    @Element(name = "example", required = false)
    private String example;

    @Element(name = "link", required = false)
    private String link;

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSupNo() {
        return supNo;
    }

    public void setSupNo(String supNo) {
        this.supNo = supNo;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
} 