package com.example.appstyle;

public class Exercise {

    private String target;

    private String equipament;

    private String name;

    private String gif;

    public Exercise(String name, String target, String equipament, String gif) {
        this.name = capitalizeFirstLetter(name);
        this.target = capitalizeFirstLetter(target);
        this.equipament = capitalizeFirstLetter(equipament);
        this.gif = gif;
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public String getTarget() {
        return target;
    }

    public String getEquipament() {
        return equipament;
    }

    public String getName() {
        return name;
    }

    public String getGif() {
        return gif;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setEquipament(String equipament) {
        this.equipament = equipament;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }
}
