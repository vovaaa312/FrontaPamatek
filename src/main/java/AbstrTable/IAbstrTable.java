package AbstrTable;

import java.util.Iterator;
import Enum.enumTypProhlidky;


public interface IAbstrTable<K extends Comparable<K>, V>  {

    void zrus();

    boolean jePrazdny();

    V najdi(K key);

    void vloz (K key, V value);

    V odeber(K key);

    Iterator vytvorIterator(enumTypProhlidky typ);

    V najdiNejbliz(K klic);


}
