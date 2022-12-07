package Pamatky;

import Zamek.Zamek;

public class Delka implements Comparable<Delka>{
    private Zamek zamek;
    private double delka;

    public Delka(Zamek zamek, double delka) {
        this.zamek = zamek;
        this.delka = delka;
    }

    public Zamek getZamek() {
        return zamek;
    }

    public void setZamek(Zamek zamek) {
        this.zamek = zamek;
    }

    public double getDelka() {
        return delka;
    }

    public void setDelka(double delka) {
        this.delka = delka;
    }

    @Override
    public String toString() {
        return zamek.toString();
    }

    @Override
    public int compareTo(Delka o) {
        return Double.compare(delka, o.delka);
    }
}
