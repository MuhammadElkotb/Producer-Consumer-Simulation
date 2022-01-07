package Model;

import java.util.concurrent.ThreadLocalRandom;

public class ColorGenerator {
    public static String generate(){
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);


        String rgb = "rgb(" .concat(String.valueOf(r))
                            .concat(", ")
                            .concat(String.valueOf(g))
                            .concat(", ")
                            .concat(String.valueOf(b))
                            .concat(")");

        return rgb;

    }
}
