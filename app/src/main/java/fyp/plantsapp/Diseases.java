package fyp.plantsapp;

import java.util.List;

public class Diseases {
    String name;
    String type;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    List<String> sprays;
    List<String> symptoms;
    List<String> preventive_measures;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSprays() {
        return sprays;
    }

    public void setSprays(List<String> sprays) {
        this.sprays = sprays;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public List<String> getPreventive_measures() {
        return preventive_measures;
    }

    public void setPreventive_measures(List<String> preventive_measures) {
        this.preventive_measures = preventive_measures;
    }

    public Diseases(String name, String type, List<String> sprays, List<String> symptoms, List<String> preventive_measures,int image) {
        this.name = name;
        this.type = type;
        this.sprays = sprays;
        this.symptoms = symptoms;
        this.preventive_measures = preventive_measures;
        this.image=image;
    }
}
