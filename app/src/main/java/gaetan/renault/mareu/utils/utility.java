package gaetan.renault.mareu.utils;


public final class utility {

    public static String formatOneInTwoNumber(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return "" + number;
    }
}
