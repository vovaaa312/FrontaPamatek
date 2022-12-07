package Generator;

import Zamek.GPS;
import Zamek.Zamek;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import Enum.enumTypKey;

public class Generator {
    static int MIN_RG = 0;
    static int MAX_RG = 100;
    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println(generateRandomZamek(enumTypKey.GPS).toString());
    }

    public static Zamek generateRandomZamek(enumTypKey typKey) {
        return new Zamek(ranID(), ranName(), ranGPS(), typKey);
    }

    public static GPS ranGPS() {
        return new GPS(genRandomCoord(), genRandomCoord());
    }

    public static int genRandomCoord() {
        return (int) ((Math.random() * (MAX_RG - MIN_RG)) + MIN_RG);
    }

    public static String ranID() {
        StringBuilder ID = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            ID.append(ranSymbol(false));
        }
        ID.append(random.nextInt(300) + 180).append("_").append(ranSymbol(false));
        return String.valueOf(ID);
    }

    public static String ranName() {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            name.append(ranSymbol(true));
        }
        return String.valueOf(name);
    }

    public static String ranSymbol(boolean toName) {
        ArrayList<String> alphaNum = new ArrayList<>();

        for (char c = 'A'; c <= 'z'; c++) {
            String s = "";
            s += c;
            alphaNum.add(s);
            if (c == 'Z') {
                c = 'a' - 1;
            }
        }
        if (toName == false) {
            for (int c = 0; c < 10; c++) {
                String s = "";
                s += c;
                alphaNum.add(s);
            }
        }

        return (alphaNum.get((int) (Math.random() * alphaNum.size())));
    }

}
