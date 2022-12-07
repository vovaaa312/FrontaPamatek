package Pamatky;

import AbstrTable.AbstrTable;
import Zamek.Zamek;

import java.io.*;
import java.util.*;

import Enum.enumTypKey;
import Enum.enumTypProhlidky;
import AbstrTable.IAbstrTable;

import Zamek.GPS;

public class Pamatky implements IPamatky {

    private IAbstrTable<Zamek, Zamek> table = new AbstrTable<Zamek, Zamek>();
    enumTypKey typKey = enumTypKey.NAZEV;

    private  String id;
    private  int idInt;

    public Pamatky() {
        idInt = 0;
        id = String.valueOf(idInt);
    }


    @Override
    public int importDatTXT(String soubor) throws IOException {
        int pocet = 0;

        FileReader fileReader = new FileReader(new File("src/main/resources/com/example/GUI/" + soubor));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] nactenyZamek;
        Zamek zamek;
        while ((line = bufferedReader.readLine()) != null) {

            nactenyZamek = line.split(",");

            double x = Double.parseDouble(nactenyZamek[1]);
            double y = Double.parseDouble(nactenyZamek[2]);
            String nazev = nactenyZamek[0];

            zamek = new Zamek(id, nazev, new GPS(x,y), typKey);
            if (zamek.getId() != null && zamek.getNazev() != null) {
                table.vloz(zamek, zamek);

            }
            System.out.println(zamek.getNazev() + "," + zamek.getGps().getX() + "," + zamek.getGps().getY());
            pocet++;
            idInt++;
        }
        return pocet;
    }



    public void ulozData(String soubor) throws IOException {
        FileWriter writer = new FileWriter(new File("src/main/resources/com/example/GUI/" + soubor));
        Iterator iterator = table.vytvorIterator(enumTypProhlidky.HLOUBLKA);
        while (iterator.hasNext()) {
            //writer.write((Zamek)iterator.next().);
        }
    }


    @Override
    public void vlozZamek(Zamek zamek) {
        if (zamek == null) throw new NullPointerException();
        table.vloz(zamek, zamek);
        idInt++;
    }


    @Override
    public Zamek najdiZamek(String klic) {
        enumTypKey akt = getTypKey();
        nastavKlic(enumTypKey.NAZEV);
        Zamek najdi = table.najdi(new Zamek(null, klic, null, enumTypKey.NAZEV));
        if (najdi == null) throw new NullPointerException("Zamek not found");
        else {
            nastavKlic(akt);
            return najdi;
        }

    }

    @Override
    public Zamek odeberZamek(String klic) {
        Zamek odeber = null;
        switch (typKey) {

            case NAZEV -> odeber = table.odeber(new Zamek(null, klic, null, typKey));
            case ID -> odeber = table.odeber(new Zamek(klic, null, null, typKey));
            case GPS -> {
                String[] gps = klic.split(" ");
                int x = (int) Double.parseDouble(gps[0]);
                int y = (int) Double.parseDouble(gps[1]);
                odeber = table.odeber(new Zamek(null, null, new GPS(x, y), typKey));
            }
        }
        idInt--;
        return odeber;
        //return table.odeber(new Zamek(null, null, null, 0, 0, typKey));
    }

    @Override
    public boolean jePrazdny() {
        return table.jePrazdny();
    }

    @Override
    public void zrus() {
        table.zrus();
        idInt = 0;
    }

    @Override
    public void prebuduj() {
        Iterator<Zamek> iter = table.vytvorIterator(enumTypProhlidky.HLOUBLKA);
        List<Zamek> list = new ArrayList<>();
        while (iter.hasNext()) {
            Zamek zamek = iter.next();
            zamek.setTypKey(typKey);
            list.add(zamek);
        }
        Comparator<Zamek> comparator = null;
        switch (typKey) {
            case NAZEV -> comparator = Comparator.comparing(Zamek::getNazev);
            case GPS -> comparator = Comparator.comparing(Zamek::getGps);

        }
        list.sort(comparator);
        table.zrus();
        prebuduj(list);
    }

    private void prebuduj(List<Zamek> list) {
        if (list.size() == 0) return;

        if (list.size() == 1) {
            vlozZamek(list.get(0));
            return;
        }
        int str = list.size() / 2;
        vlozZamek(list.get(str));
        prebuduj(list.subList(0, str));
        prebuduj(list.subList(str + 1, list.size()));
    }

    @Override
    public Zamek najdiNejbliz(String klic) {
        enumTypKey akt = getTypKey();
        nastavKlic(enumTypKey.GPS);
        String[] key = klic.split(" ");
        int x = Integer.valueOf(key[0]);
        int y = Integer.valueOf(key[1]);
        Zamek najdi = table.najdiNejbliz(new Zamek("", "", new GPS(x, y), enumTypKey.GPS));
        if (najdi == null) throw new NullPointerException("Zamek not found");
        else {
            nastavKlic(akt);
            return najdi;
        }


    }

    @Override
    public Iterator vytvorIterator(enumTypProhlidky typ) {
        return table.vytvorIterator(typ);
    }

    @Override
    public void nastavKlic(enumTypKey typ) {
        switch (typ) {
            case NAZEV -> typKey = enumTypKey.NAZEV;
            case GPS -> typKey = enumTypKey.GPS;
        }

    }

    public enumTypKey getTypKey() {
        return typKey;
    }

}
