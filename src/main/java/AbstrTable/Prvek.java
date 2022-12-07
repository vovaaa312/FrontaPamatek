package AbstrTable;

class Prvek<K, V> {
    private K klic;
    private V data;
    private Prvek<K, V> levy, pravy, otec;

    public Prvek(K klic, V data) {
        this.klic = klic;
        this.data = data;

    }

    public K getKlic() {
        return klic;
    }

    public void setKlic(K klic) {
        this.klic = klic;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public Prvek<K, V> getLevy() {
        return levy;
    }

    public void setLevy(Prvek<K, V> levy) {
        this.levy = levy;
    }

    public Prvek<K, V> getPravy() {
        return pravy;
    }

    public void setPravy(Prvek<K, V> pravy) {
        this.pravy = pravy;
    }

    public Prvek<K, V> getOtec() {
        return otec;
    }

    public void setOtec(Prvek<K, V> otec) {
        this.otec = otec;
    }
}
