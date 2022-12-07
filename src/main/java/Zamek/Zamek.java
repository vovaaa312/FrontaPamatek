package Zamek;

import Enum.enumTypKey;

public class Zamek implements Comparable<Zamek> {
    private String id;
    private String nazev;
    private GPS gps;
    private enumTypKey typKey;

    private int x;
    private int y;


    public Zamek(String id, String nazev, GPS gps, enumTypKey typKey) {
        this.id = id;
        this.nazev = nazev;
        this.gps = gps;
        this.typKey = typKey;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public GPS getGps() {
        return gps;
    }

    public void setTypKey(enumTypKey typKey) {
        this.typKey = typKey;
    }

    public String getPoloha(){
        return this.x + " " + this.y;
    }



    @Override
    public String toString() {
        return id + " " + nazev + " " + gps.toString();
    }

    @Override
    public int compareTo(Zamek o) {
        if (typKey.equals(enumTypKey.NAZEV)) return this.nazev.compareTo(o.getNazev());
        else return this.gps.compareTo(o.gps);

    }
}
