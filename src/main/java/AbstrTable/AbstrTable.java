package AbstrTable;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import AbstrFifo.AbstrFifo;
import AbstrLifo.AbstrLifo;
import Enum.enumTypProhlidky;


public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    private Prvek<K, V> koren;

    public AbstrTable() {
        this.koren = null;
    }

    @Override
    public void zrus() {
        koren = null;
    }

    @Override
    public boolean jePrazdny() {
        return koren == null;
    }


    @Override
    public V najdi(K key) {
        return najdi(koren, key);

    }

    private V najdi(Prvek<K, V> koren, K key) {
        if (key == null) throw new IllegalArgumentException();
        if (koren == null) return null;
        int cmp = key.compareTo(koren.getKlic());
        if (cmp < 0) return najdi(koren.getLevy(), key);
        else if (cmp > 0) return najdi(koren.getPravy(), key);
        else return koren.getData();


    }

    @Override
    public void vloz(K key, V value) {
        if (key == null) throw new IllegalArgumentException();
        if (value == null) {
            odeber(key);
            return;
        }
        koren = vloz(koren, key, value);
    }

    private Prvek vloz(Prvek<K, V> koren, K key, V value) {
        if (koren == null) return new Prvek(key, value);
        Prvek<K, V> vloz = null;
        int cmp = key.compareTo(koren.getKlic());
        if (cmp < 0) {
            koren.setLevy(vloz(koren.getLevy(), key, value));
            koren.getLevy().setOtec(koren);
        } else if (cmp > 0) {
            koren.setPravy(vloz(koren.getPravy(), key, value));
            koren.getPravy().setOtec(koren);
        } else koren.setData(value);
        return koren;
    }

    @Override
    public V odeber(K key) {
        Prvek<K, V> prv = koren;
        while (prv != null) {
            int cmp = key.compareTo(prv.getKlic());
            if (cmp > 0) prv = prv.getPravy();
            else if (cmp < 0) prv = prv.getLevy();
            else return odeber(prv);
        }
        return null;
    }

    private V odeber(Prvek<K, V> koren) {

        V ret = koren.getData();
        if (koren.getLevy() == null && koren.getPravy() == null) {
            if (koren.getOtec().getPravy() == this.koren) koren.getOtec().setPravy(null);
            else koren.getOtec().setLevy(null);
        } else if (koren.getLevy() == null) {
            if (koren.getOtec().getPravy() == koren) koren.getOtec().setPravy(koren.getPravy());
            else koren.getOtec().setLevy(koren.getPravy());
            koren.getPravy().setOtec(koren.getOtec());
        } else if (koren.getPravy() == null) {
            if (koren.getOtec().getPravy() == this.koren) koren.getOtec().setPravy(koren.getLevy());
            else koren.getOtec().setLevy(koren.getLevy());
            koren.getLevy().setOtec(koren.getOtec());
        } else {
            if (koren.getOtec().getLevy() == this.koren) koren.getOtec().setLevy(koren.getPravy());
            else koren.getOtec().setPravy(koren.getPravy());
            Prvek<K, V> prvLevy = koren.getLevy();
            koren = koren.getPravy();
            while (koren.getLevy() != null) koren = koren.getLevy();
            koren.setLevy(prvLevy);
        }
        return ret;
    }


    @Override
    public Iterator<V> vytvorIterator(enumTypProhlidky typ) {
        return switch (typ) {
            case SIRKA -> sirkaIter();
            case HLOUBLKA -> hloubkaIter();
        };
    }

    @Override
    public V najdiNejbliz(K klic) {
        int minInt = Integer.MAX_VALUE;
        Prvek<K, V> kor = koren;
        V najdi = null;
        while (kor != null) {
            int cmp = klic.compareTo(kor.getKlic());
            int pom = (int) Math.sqrt(Math.abs(cmp * cmp - minInt * minInt));
            if (pom < minInt) {
                minInt = pom;
                najdi = kor.getData();
            }
            if (cmp > 0) kor = kor.getPravy();
            else if (cmp < 0) kor = kor.getLevy();
            else return kor.getData();
        }
        return najdi;
    }

    private Iterator<V> hloubkaIter() {
        AbstrLifo<Prvek<K, V>> lifo = new AbstrLifo<Prvek<K, V>>();
        lifo.vloz(koren);
        return new Iterator<V>() {
            @Override
            public boolean hasNext() {
                return !lifo.jePrazdny();
            }

            @Override
            public V next() {
                if(!hasNext())throw new NoSuchElementException();
                Prvek<K, V>  prvek = lifo.odeber();
                if (prvek.getLevy() != null) lifo.vloz(prvek.getLevy());
                if (prvek.getPravy() != null) lifo.vloz(prvek.getPravy());
                return prvek.getData();
            }
        };
    }

    private Iterator<V> sirkaIter() {
        AbstrFifo<Prvek<K, V>> fifo = new AbstrFifo<Prvek<K, V>>();
        fifo.vloz(koren);
        return new Iterator<V>() {

            @Override
            public boolean hasNext() {
                return !fifo.jePrazdny();
            }

            @Override
            public V next() {
                if(!hasNext())throw new NoSuchElementException();

                Prvek<K, V> prvek = fifo.odeber();
                if (prvek.getPravy()!=null){
                    vlevo(prvek.getLevy());
                }


//                prvek = fifo.odeber();
//                if (prvek.getLevy() != null) fifo.vloz(prvek.getLevy());
//                if (prvek.getPravy() != null) fifo.vloz(prvek.getPravy());


                return prvek.getData();


            }
            private void vlevo(Prvek<K,V>akt){
                while (akt!=null) {
                    fifo.vloz(akt);
                    akt = akt.getLevy();
                }
            }
        };
    }


//    public static void main(String[] args) {
//        AbstrTable<String, Integer> st = new AbstrTable<String, Integer>();
//        for (int i = 1; i < 25; i++) {
//            String key = "key" + i;
//            st.vloz(key, i);
//        }
//
//        System.out.println(st.koren.getData());
//        System.out.println(st.najdi("key3"));
//         System.out.println("odebirame: " + st.odeber("key3"));
//         System.out.println("odebirame: " + st.odeber("key5"));
//        Iterator iterator = st.vytvorIterator(enumTypProhlidky.SIRKA);
//        while (iterator.hasNext()) System.out.print(iterator.next() + " ");
//
//    }
}
