package AbstrHeap;

import java.util.Iterator;
import java.util.NoSuchElementException;

import AbstrLifo.AbstrLifo;
import Enum.enumTypProhlidky;

public class AbstrHeap<T extends Comparable<T>> implements IAbstrHeap<T> {

    private Prvek<T>[] pole;
    private int pocetPrvku;

    public AbstrHeap() {
        pole = new Prvek[10];
        pocetPrvku = 0;
    }

    @Override
    public void vybuduj(T[] obj) {
        pole = new Prvek[obj.length + 1];
        pocetPrvku = obj.length;
        for (int i = 0; i < pocetPrvku; i++) this.pole[i + 1] = new Prvek<T>(obj[i]);
        prebuduj();
    }

    @Override
    public void prebuduj() {
        for (int i = 1; i <= pocetPrvku / 2; i++) {
            for (int j = i; j < pocetPrvku; j++) {
                int min;
                if (j * 2 <= pocetPrvku) {
                    if (j * 2 + 1 <= pocetPrvku) {
                        if (pole[j * 2].data.compareTo(pole[j * 2 + 1].data) < 0) min = j * 2;
                        else min = j * 2 + 1;
                    } else min = j * 2;
                } else {
                    if (j * 2 + 1 <= pocetPrvku) min = j * 2 + 1;
                    else min = 0;
                }
                if (min != 0 && pole[min].data.compareTo(pole[j].data) < 0) {
                    T pom = pole[min].data;
                    pole[min] = new Prvek<T>(pole[j].data);
                    pole[j] = new Prvek<T>(pom);
                    j = min;
                } else break;
            }
        }
    }

    @Override
    public void zrus() {
        new AbstrHeap();
    }

    @Override
    public boolean jePrazdny() {
        return pocetPrvku == 0;
    }

    @Override
    public void vloz(T vloz) {
        if (jePrazdny()) {
            pole[1] = new Prvek<>(vloz);
            pocetPrvku++;
        } else {
            if (pocetPrvku + 1 >= pole.length) zvys();
            pole[pocetPrvku + 1] = new Prvek<>(vloz);
            pocetPrvku++;
            for (int i = pocetPrvku; i > 0; i = i / 2) {
                if (i / 2 > 0 && pole[i / 2].data.compareTo(pole[i].data) > 0) {
                    T pom = pole[i].data;
                    pole[i] = new Prvek<T>(pole[i / 2].data);
                    pole[i / 2] = new Prvek<T>(pom);
                } else break;

            }
        }
    }

    private void zvys() {
        Prvek<T>[] copy = new Prvek[pole.length * 2];
        System.arraycopy(pole, 1, copy, 1, pole.length - 1);
        pole = copy;
    }

    @Override
    public T zpristupniMax() {
        return pole[1].data;
    }

    @Override
    public T odeberMax() {
        if (jePrazdny()) throw new NullPointerException();
        T odeber = pole[1].data;
        if (pocetPrvku == 1) zrus();
        else {
            pole[1] = new Prvek<T>(pole[pocetPrvku].data);
            pole[pocetPrvku] = null;
            pocetPrvku--;
            for (int i = 1; i < pocetPrvku; i++) {
                int min;
                if (i * 2 <= pocetPrvku) {
                    if (i * 2 + 1 <= pocetPrvku) {
                        if (pole[i * 2].
                                data.compareTo(pole[i * 2 + 1].data) < 0) min = i * 2;
                        else min = i * 2 + 1;
                    } else min = i * 2;
                } else {
                    if (i * 2 + 1 <= pocetPrvku) min = i * 2 + 1;
                    else min = 0;
                }
                if (min != 0 && pole[min].data.compareTo(pole[i].data) < 0) {
                    T pom = pole[min].data;
                    pole[min] = new Prvek<T>(pole[i].data);
                    pole[i] = new Prvek<T>(pom);
                    i = min;
                } else break;
            }
        }
        return odeber;
    }

    @Override
    public Iterator iterator(enumTypProhlidky typ) {
        switch (typ) {
            case SIRKA -> {
                return iterator();
            }
            case HLOUBLKA -> {
                return iteratorHloubky();
            }
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int i = 1;

            @Override
            public boolean hasNext() {
                return pocetPrvku <= i;
            }

            @Override
            public T next() {
                i++;
                return pole[i - 1].data;
            }
        };
    }

    private Iterator<T> iteratorHloubky() {
        AbstrLifo<Integer> lifo = new AbstrLifo<Integer>();
        lifo.vloz(1);
        return new Iterator<T>() {
            int i = 1;

            @Override
            public boolean hasNext() {
                return !lifo.jePrazdny();
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                while (i * 2 <= pocetPrvku) {
                    i *= 2;
                    lifo.vloz(i);
                }
                int posledni = lifo.odeber();
                if (posledni * 2 + 1 <= pocetPrvku) {
                    i = posledni * 2 + 1;
                    lifo.vloz(i);
                }
                return pole[posledni].data;
            }
        };
    }


    private class Prvek<T> {
        T data;

        public Prvek(T data) {
            this.data = data;
        }
    }

//    public static void main(String[] args) {
//        AbstrHeap<Integer>priorityQueue = new AbstrHeap<Integer>();
//        priorityQueue.vloz(5);
//        priorityQueue.vloz(2);
//        priorityQueue.vloz(6);
//
//        Iterator iterator = priorityQueue.iteratorHloubky();
//        while (iterator.hasNext()) System.out.println(iterator.next().toString());
//
//        priorityQueue.prebuduj();
//        System.out.println(priorityQueue.zpristupniMax());
//    }

}
