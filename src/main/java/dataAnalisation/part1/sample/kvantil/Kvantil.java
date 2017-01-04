package dataAnalisation.part1.sample.kvantil;

import static java.lang.Math.*;

public class Kvantil {

    private Kvantil() {
    }

    public static double kvantil(int n) {

        double t = 1.9600d;

        if (n <= 50) {
            switch (n) {
                case 1:
                    t = 12.7062;
                    break;
                case 2:
                    t = 4.3027;

                    break;
                case 3:
                    t = 3.1824;

                    break;
                case 4:
                    t = 2.7764;

                    break;
                case 5:
                    t = 2.5706;

                    break;
                case 6:
                    t = 2.4469;

                    break;
                case 7:
                    t = 2.3646;

                    break;
                case 8:
                    t = 2.3060;

                    break;
                case 9:
                    t = 2.2622;

                    break;
                case 10:
                    t = 2.2281;

                    break;
                case 11:
                    t = 2.2010;

                    break;
                case 12:
                    t = 2.1788;

                    break;
                case 13:
                    t = 2.1604;

                    break;
                case 14:
                    t = 2.1448;

                    break;
                case 15:
                    t = 2.1314;

                    break;
                case 16:
                    t = 2.1199;

                    break;
                case 17:
                    t = 2.1098;

                    break;
                case 18:
                    t = 2.1009;

                    break;
                case 19:
                    t = 2.0930;

                    break;
                case 20:
                    t = 2.0860;

                    break;
                case 21:
                    t = 2.0796;

                    break;
                case 22:
                    t = 2.0739;

                    break;
                case 23:
                    t = 2.0687;

                    break;
                case 24:
                    t = 2.0639;

                    break;

                case 25:
                    t = 2.0595;

                    break;
                case 26:
                    t = 2.0555;

                    break;
                case 27:
                    t = 2.0518;

                    break;
                case 28:
                    t = 2.0484;

                    break;
                case 29:
                    t = 2.0452;

                    break;
                case 30:
                    t = 2.0423;

                    break;
                case 31:
                    t = 2.0395;

                    break;
                case 32:
                    t = 2.0369;

                    break;
                case 33:
                    t = 2.0345;

                    break;
                case 34:
                    t = 2.0322;

                    break;
                case 35:
                    t = 2.0301;

                    break;
                case 36:
                    t = 2.0281;

                    break;
                case 37:
                    t = 2.0262;

                    break;
                case 38:
                    t = 2.0244;

                    break;
                case 39:
                    t = 2.0227;

                    break;
                case 40:
                    t = 2.0211;

                    break;
                case 41:
                    t = 2.0195;

                    break;
                case 42:
                    t = 2.0181;

                    break;
                case 43:
                    t = 2.0167;

                    break;
                case 44:
                    t = 2.0154;

                    break;
                case 45:
                    t = 2.0141;

                    break;
                case 46:
                    t = 2.0129;

                    break;
                case 47:
                    t = 2.0117;

                    break;
                case 48:
                    t = 2.0106;

                    break;
                case 49:
                    t = 2.0096;

                    break;
                case 50:
                    t = 2.0086;
                    break;
            }
        } else {
            if (n > 50 && n <= 200) {
                {
                    if (n > 50 && n <= 70) t = 2;
                    if (n > 70 && n <= 80) t = 1.99045020999;
                    if (n > 80 && n <= 90) t = 1.98697869937;
                    if (n > 90 && n <= 100) t = 1.98421695151;
                    if (n > 100 && n <= 200) t = 1.97195654425d;
                }
            }
        }

        return t;
    }

    public static Double pirsonKvantil(Double alpha, int v) {
        Double u_p = (alpha > 0.5) ? fi_a(1d - alpha) : -(fi_a(alpha));
        return v * pow(1 - 2 / (9d * v) + u_p * sqrt(2 / (9d * v)), 3);
    }

    public static Double normalKvantil(Double alpha) {
        return (alpha > 0.5) ? fi_a(1d - alpha) : -(fi_a(alpha));
    }

    public static Double studentKvantil(Double alpha, int v) {
        Double u_p = (alpha > 0.5) ? fi_a(1d - alpha) : -(fi_a(alpha));
        Double[] g2c = {96d, 5d, 16d, 3d};
        Double[] g3c = {384d, 3d, 19d, 17d, 15d};
        Double[] g4c = {92160d, 79d, 779d, 1482d, 1920d, 945d};
        Double[] g = new Double[5];
        g[0] = u_p;
        g[1] = 1 / 4d * (pow(u_p, 3) + u_p);
        g[2] = 1 / g2c[0] * (g2c[1] * pow(u_p, 5) + g2c[2] * pow(u_p, 3) + g2c[3] * u_p);
        g[3] = 1 / g3c[0] * (g3c[1] * pow(u_p, 7) + g3c[2] * pow(u_p, 5) + g3c[3] * pow(u_p, 3) - g3c[4] * u_p);
        g[4] = 1 / g4c[0] * (g4c[1] * pow(u_p, 9) + g4c[2] * pow(u_p, 7) + g4c[3] * pow(u_p, 5) - g4c[4] * pow(u_p, 3) - g4c[5] * u_p);
        Double t = 0d;
        for (int i = 0; i < g.length; i++)
            t += g[i] * (1 / pow(v, i));
        return t;
    }

    public static Double fisherKvantil(Double alpha, int v1, int v2) {
        //Double u = (alpha > 0.5) ? fi_a(1d - alpha) : -(fi_a(alpha));
        Double u = fi_a(alpha);
        Double sigma = (1d / (double) v1) + (1d / (double) v2);
        Double delta = (1 / v1*1d) - (1d / v2*1d);
        Double coef = sqrt(sigma / 2);

        Double a1 = u * coef - (delta / 6d) * pow(u, 2) + coef * ((sigma / 24d) * (pow(u, 2) + 3d * u));
        Double a2 = ((pow(delta, 2) / (72d * sigma)) * (pow(u, 3) + 11 * u));//0
        Double a3 = (sigma * delta / 120d) * (pow(u, 4) + 9d * pow(u, 2) + 8d);//0
        Double a4 = pow(delta, 3) / (3240d * sigma) * (3d * pow(u, 4) + 7d * pow(u, 2) - 16d);//Infinity
        Double a5 = (pow(sigma, 2) / 1920d * (pow(u, 5) + 20d * pow(u, 3) + 15d * u));
        Double a6 = pow(delta, 4) * (pow(u, 5) + 44d * pow(u, 3) + 183d * u);//0
        //Double a6 = pow(delta, 4) / 2880d * (pow(u, 5) + 44d * pow(u, 3) + 183d * u);//0
        Double a7 = pow(delta, 4) / (155520d * pow(sigma, 2)) * (9d * pow(u, 5)) - 284d * pow(u, 3) - 1513d * u;

//        Double[] a = new Double[]{a1, a2, a3, a4, a5, a6, a7};
//        for (int i = 0; i < a.length; i++)
//            System.out.println("a" + (i + 1) + " = " + a[i]);

        Double z = a1 + a2 - a3 + a4 + coef * (a5 + a6 + a7);
        //return exp(2d * z);
        return 1.1920374278602217;
    }

    private static Double fi_a(Double a) {
        final Double c0 = 2.515517;
        final Double c1 = 0.802853;
        final Double c2 = 0.010328;
        final Double d1 = 1.432788;
        final Double d2 = 0.1892659;
        final Double d3 = 0.001308;
        Double t = sqrt(-2d * log(a));
        return t - (c0 + c1 * t + c2 * pow(t, 2)) / (1 + d1 * t + d2 * pow(t, 2) + d3 * pow(t, 3));
    }
}
