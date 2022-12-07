package Zamek;

public class GPS implements Comparable<GPS> {
    private final double x, y;

    public GPS(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getXY() {
        return getX() * getY();
    }

    ;

    @Override
    public int compareTo(GPS o) {
        return Math.toIntExact(binarCompare(x, y) - binarCompare(o.getX(), o.getY()));
    }

    private long binarCompare(double x1, double y1) {
        String[] xBin = Integer.toBinaryString((int) x1).split("");
        String[] yBin = Integer.toBinaryString((int) y1).split("");
        String res = "";
        if (xBin.length > yBin.length) {
            for (int i = 0; i < xBin.length; i++) {
                if (i >= yBin.length) res += xBin[i];
                else res += xBin[i] + yBin[i];
            }
        } else {
            for (int i = 0; i < yBin.length; i++) {
                if (i >= xBin.length) res += yBin[i];
                else res += xBin[i] + yBin[i];
            }
        }
        return Long.parseLong(res, 2);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

}
