package Pamatky;

import AbstrHeap.AbstrHeap;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import Enum.enumTypProhlidky;
import Enum.enumTypKey;

import Zamek.GPS;
import Zamek.Zamek;
import AbstrHeap.IAbstrHeap;

public class FrontaPamatek<D> implements IAbstrHeap<Delka> {

    private String poloha;
    private final AbstrHeap<Delka> zamekAbstrHeap;

    public FrontaPamatek() {
        zamekAbstrHeap = new AbstrHeap<Delka>();
        this.poloha = "49.069439 13.511995";
    }

    public FrontaPamatek(String poloha) {
        if (Objects.equals(poloha, "") || poloha.length() < 3) throw new IllegalArgumentException();
        zamekAbstrHeap = new AbstrHeap<Delka>();
        this.poloha = poloha;
    }


    public void nactiPamatky(Pamatky pamatky){
        Iterator iterator = pamatky.vytvorIterator(enumTypProhlidky.HLOUBLKA);
        ArrayList<Delka> list = new ArrayList<Delka>();
        while (iterator.hasNext()){
            Zamek zamek = (Zamek)iterator.next();
            Delka delka = new Delka(zamek, calc(poloha,zamek.getPoloha()));
            list.add(delka);
        }
        zamekAbstrHeap.vybuduj(list.toArray(new Delka[0]));
    }

    public void nactiSoubor(String soubor) throws IOException {

        FileReader fileReader = new FileReader(new File("src/main/resources/com/example/GUI/" + soubor));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] nactenyZamek;
        Zamek zamek;
        ArrayList<Delka> list = new ArrayList<Delka>();
        while ((line = bufferedReader.readLine()) != null) {
            nactenyZamek = line.split(",");

            double x = Double.parseDouble(nactenyZamek[1]);
            double y = Double.parseDouble(nactenyZamek[2]);
            String nazev = nactenyZamek[0];


            zamek = new Zamek("", nazev, new GPS(x, y), enumTypKey.GPS);
            if (zamek.getId() != null && zamek.getNazev() != null) {
                String polohaZamku = x + " " + y;

                Delka delka = new Delka(zamek, calc(poloha, polohaZamku));  //TODO поменять 'delka'
                list.add(delka);
            }
        }
        zamekAbstrHeap.vybuduj(list.toArray(new Delka[0]));

    }


    private double calc(String klic1, String klic2) {
        String[]pole1 = klic1.split(" ");
        String[]pole2 = klic2.split(" ");

        Double[] kl1 = new Double[]{Double.parseDouble(pole1[0]), Double.parseDouble(pole1[1])};
        Double[] kl2 = new Double[]{Double.parseDouble(pole2[0]), Double.parseDouble(pole2[1])};
        return calcDist(kl1[0], kl1[1], kl2[0], kl2[1]);
    }



    private double calcDist(double doubleA1, double doubleA2, double doubleB1, double doubleB2) {
        double radius = 6372.795;
        double prvni = doubleA1 * Math.PI / 180;
        double druhy = doubleB1 * Math.PI / 180;
        double prvniZmena = (doubleA1 - doubleB1) * Math.PI / 180;
        double druhaZmena = (doubleA2 - doubleB2) * Math.PI / 180;

        double a = Math.sin(prvniZmena / 2) * Math.sin(prvniZmena / 2) + Math.cos(prvni) * Math.cos(druhy) *
                Math.sin(druhaZmena / 2) * Math.sin(druhaZmena / 2);
        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double result = radius * b;
        return result;


    }

    @Override
    public void vybuduj(Delka[] obj) {
        zamekAbstrHeap.vybuduj(obj);
    }

    @Override
    public void prebuduj() {
        zamekAbstrHeap.prebuduj();
    }

    @Override
    public void zrus() {
        zamekAbstrHeap.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return zamekAbstrHeap.jePrazdny();
    }

    @Override
    public void vloz(Delka vloz) {
        zamekAbstrHeap.vloz(vloz);
    }

    @Override
    public Delka zpristupniMax() {
        return zamekAbstrHeap.zpristupniMax();
    }

    @Override
    public Delka odeberMax() {
        return zamekAbstrHeap.odeberMax();
    }

    @Override
    public Iterator<Delka> iterator(enumTypProhlidky typ) {
        return zamekAbstrHeap.iterator(typ);
    }

    public String getPoloha() {
        return poloha;
    }


    @Override
    public Iterator<Delka> iterator() {
        return iterator(enumTypProhlidky.HLOUBLKA);
    }
}
