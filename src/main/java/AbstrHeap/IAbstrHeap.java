package AbstrHeap;

import Zamek.Zamek;

import java.io.Serializable;
import java.util.Iterator;

import Enum.enumTypProhlidky;

public interface IAbstrHeap<T extends Comparable<T>> extends Iterable<T>, Serializable {

    void vybuduj(T[] obj);

    void prebuduj();

    void zrus();

    boolean jePrazdny();

    void vloz(T vloz);

    T zpristupniMax();

    T odeberMax();

     Iterator iterator(enumTypProhlidky typ);



    ;
}



    

