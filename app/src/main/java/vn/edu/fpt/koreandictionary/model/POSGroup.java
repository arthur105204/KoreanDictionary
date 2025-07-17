package vn.edu.fpt.koreandictionary.model;

import java.util.List;

public class POSGroup {
    private String pos;
    private List<Sense> senses;
    private List<Example> examples;

    public POSGroup(String pos, List<Sense> senses, List<Example> examples) {
        this.pos = pos;
        this.senses = senses;
        this.examples = examples;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }
} 