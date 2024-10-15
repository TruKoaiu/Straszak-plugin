package me.trukoaiu.straszak.utility;

public class RadianFunctionality {

    public static double adderRadian180Equalizer(double base, double addValue){
        double result = base + addValue;
        if (result > 180) {
            result -= 360;
        } else if (result < -180) {
            result += 360;
        }

        return result;
    }
}
