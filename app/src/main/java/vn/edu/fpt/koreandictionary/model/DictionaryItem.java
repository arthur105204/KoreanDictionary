package vn.edu.fpt.koreandictionary.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "item", strict = false)
public class DictionaryItem {
    @Element(name = "target_code", required = false)
    private String targetCode;

    @Element(name = "word", required = false)
    private String word;

    @Element(name = "sup_no", required = false)
    private String supNo;

    @Element(name = "origin", required = false)
    private String origin;

    @Element(name = "pronunciation", required = false)
    private String pronunciation;

    @Element(name = "word_grade", required = false)
    private String wordGrade;

    @Element(name = "pos", required = false)
    private String pos;

    @Element(name = "link", required = false)
    private String link;

    @ElementList(name = "sense", inline = true, required = false)
    private List<Sense> senses;

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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getWordGrade() {
        return wordGrade;
    }

    public void setWordGrade(String wordGrade) {
        this.wordGrade = wordGrade;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }
} 