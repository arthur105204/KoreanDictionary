package vn.edu.fpt.koreandictionary.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class KRDictResponse {
    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "link")
    private String link;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "lastBuildDate")
    private String lastBuildDate;

    @XmlElement(name = "total")
    private Integer total;

    @XmlElement(name = "start")
    private Integer start;

    @XmlElement(name = "num")
    private Integer num;

    @XmlElement(name = "item")
    private List<KRDictItem> items;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLastBuildDate() { return lastBuildDate; }
    public void setLastBuildDate(String lastBuildDate) { this.lastBuildDate = lastBuildDate; }
    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }
    public Integer getStart() { return start; }
    public void setStart(Integer start) { this.start = start; }
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }
    public List<KRDictItem> getItems() { return items; }
    public void setItems(List<KRDictItem> items) { this.items = items; }
} 