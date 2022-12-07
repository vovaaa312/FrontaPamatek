package AbstrFifo;

import AbstrDoubleList.AbstrDoubleList;

import java.util.Iterator;

public class AbstrFifo<T> implements IAbstrFifo<T> {

    AbstrDoubleList<T> abstrDoubleList = new AbstrDoubleList<T>();

    //zruseni seznamu fronty
    @Override
    public void zrus() {
        abstrDoubleList.zrus();
    }

    //kontrola jestli fronta je prazdna
    @Override
    public boolean jePrazdny() {
        return abstrDoubleList.jePrazdny();
    }

    //vlozeni prvku na prvni pozice
    @Override
    public void vloz(T data) {
        abstrDoubleList.vlozPrvni(data);
    }

    //odebirani prvku z posldeni pozice
    @Override
    public T odeber() {
        return abstrDoubleList.odeberPosledni();
    }

    @Override
    public Iterator iterator() {
        return abstrDoubleList.iterator();
    }
}
