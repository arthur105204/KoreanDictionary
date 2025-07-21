package vn.edu.fpt.koreandictionary.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class KRDictItem {
    @XmlElement(name = "target_code")
    private Integer targetCode;

    @XmlElement(name = "word")
    private String word;

    @XmlElement(name = "sup_no")
    private Integer supNo;

    @XmlElement(name = "origin")
    private String origin;

    @XmlElement(name = "pronunciation")
    private String pronunciation;

    @XmlElement(name = "word_grade")
    private String wordGrade;

    @XmlElement(name = "pos")
    private String pos;

    @XmlElement(name = "link")
    private String link;

    @XmlElement(name = "sense")
    private List<KRDictSense> senses;

    // For example search
    @XmlElement(name = "example")
    private String example;

    // Getters and setters
    public Integer getTargetCode() { return targetCode; }
    public void setTargetCode(Integer targetCode) { this.targetCode = targetCode; }
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }
    public Integer getSupNo() { return supNo; }
    public void setSupNo(Integer supNo) { this.supNo = supNo; }
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getPronunciation() { return pronunciation; }
    public void setPronunciation(String pronunciation) { this.pronunciation = pronunciation; }
    public String getWordGrade() { return wordGrade; }
    public void setWordGrade(String wordGrade) { this.wordGrade = wordGrade; }
    public String getPos() { return pos; }
    public void setPos(String pos) { this.pos = pos; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public List<KRDictSense> getSenses() { return senses; }
    public void setSenses(List<KRDictSense> senses) { this.senses = senses; }
    public String getExample() { return example; }
    public void setExample(String example) { this.example = example; }
} 