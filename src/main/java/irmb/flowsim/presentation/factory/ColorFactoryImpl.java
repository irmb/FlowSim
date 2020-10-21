package irmb.flowsim.presentation.factory;

import irmb.flowsim.presentation.Color;

/**
 * Created by sven on 09.03.17.
 */
public class ColorFactoryImpl implements ColorFactory {
    @Override
    public Color makeColorForValue(double min, double max, double value) {
        if (value < min) value = min;
        if (value > max) value = max;
        double scale = (value - min) / (max - min);
        int r, g, b;
        r = b = g = 0;

        double a = (1 - scale) / 0.25;    //invert and group
        int X = (int) Math.floor(a);    //this is the integer part
        int Y = (int) Math.round(255 * (a - X)); //fractional part from 0 to 255
        switch (X) {
            case 0 -> {
                r = 255;
                g = Y;
            }
            case 1 -> {
                r = 255 - Y;
                g = 255;
            }
            case 2 -> {
                g = 255;
                b = Y;
            }
            case 3 -> {
                g = 255 - Y;
                b = 255;
            }
            case 4 -> b = 255;
            default -> throw new IllegalStateException("Unexpected value: " + X);
        }

        r = Math.max(r, 0);
        g = Math.max(g, 0);
        b = Math.max(b, 0);

        r = Math.min(r, 255);
        g = Math.min(g, 255);
        b = Math.min(b, 255);
        return new Color(r, g, b);
    }
}

