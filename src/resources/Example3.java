public class Example3 {
    private static double func(double x, int y) {
        if (true) {
            x = x + (7 - x);
        } else{

        }
        if (y < 2) {
            y = 7 * 2;
        } else {
            y = 0;
        }
        return x * y;
    }
}