/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.math.random;

import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.Geometry;

import java.util.Random;

/**
 * Description : Von Mises Random Number Generator
 * Created     : Apr 8, 2007 @ 11:47:04 AM by jim
 *
 * Translated directly from the CircStats R Package   (Jammaladamaya - see Jab Ref + thesis)
 *
 * It works by approximating the vonmises distribution using a wrapped cauchy distribution which can be analytically derived.(i think!!)
 */
public class VonMisesRandomCircStats implements IRandom{

    public VonMisesRandomCircStats(Random random, double mean, double k) {
        _random = random;
        this._mean = mean;
        _k = k;
        double k2 = Math.pow(_k, 2);

        _a = 1 + Math.pow((1 + 4 * k2), 0.5);
        _b = (_a - Math.pow((2 * _a), 0.5)) / (2 * k);
        _r = (1 + Math.pow(_b, 2)) / (2 * _b);
    }

    /**
     * <p/>
     * NOTE THE MODULUS OPERATOR '%' works the same as it does in R
     * <p/>
     * so 360 % 367 = 7
     * <p/>
     * 720 % 360 = 0
     *
     * @return next number drawn from the von mises distribution
     */
    public double nextDouble() {

        double vm = 0;

        boolean found = false;

        while (!found) {
            double U1 = _random.nextDouble();
            double z = Math.cos(Math.PI * U1);
            double f = (1 + _r * z) / (_r + z);
            double c = _k * (_r - f);

            double U2 = _random.nextDouble();
            if (c * (2 - c) - U2 > 0) {
                double U3 = _random.nextDouble();
                vm = DoubleMath.sign(U3 - 0.5) * Math.acos(f) + _mean;
                vm = vm % Geometry.TWO_PI;
                found = true;
            } else {
                if (Math.log(c / U2) + 1 - c >= 0) {
                    double U3 = _random.nextDouble();
                    vm = DoubleMath.sign(U3 - 0.5) * Math.acos(f) + _mean;
                    vm = vm % Geometry.TWO_PI;
                    found = true;
                }
            }
        }

        return vm;
    }

    private Random _random;
    private double _mean;
    private double _k;
    private double _a;
    private double _b;
    private double _r;
}
