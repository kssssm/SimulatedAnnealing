import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {

    public ArrayList<Double> hist;

    public double solve(Problem p, double a) {

        hist = new ArrayList<>();
        Random r = new Random();
        double x0 = r.nextDouble() * 100;
        return solve(p, a, x0);
    }


    public double solve(Problem p, double a, double x0) {

        Random r = new Random();
        double f0 = p.fit(x0);
        hist.add(f0);


        for (double t = 200; t > 1; t *=  a) {    //초기온도 t=200 , 루프가 끝날때마다 t를 냉각율(a)을 곱하여 새로운 t를 정의
            int kt = (int) t;
            for (int j = 0; j < kt; j++) {
                double upper = x0 + 1;
                double lower = x0 - 1;
                double x1 = r.nextDouble() * (upper - lower) + lower;
                double f1 = p.fit(x1);

                if (p.isNeighborBetter(f0, f1)) { //이웃해(x1)가 더 좋은경우 이웃해를 현재해(x0)로 변경
                    x0 = x1;
                    f0 = f1;
                    hist.add(f0);
                } else {                          //기존해가 더 좋은 해일 경우
                    double d = Math.abs(f1 - f0);
                    double p0 = Math.exp(-d / t); //p=e^(-d/t)정의
                    if (r.nextDouble() < p0) {
                        x0 = x1;
                        f0 = f1;
                        hist.add(f0);
                    }
                }
            }
        }
        return x0;
    }

    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing();
        Problem p = new Problem() {
            @Override
            public double fit(double x) {
                return  -2*x*x*x+ 32*x*x + 70;      //-2x^3+32x^2=70
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 < f1;
            }
        };

        double x = sa.solve(p, 0.99); // 냉각율 0.99로 정의하여 t가 천천히 감소
        System.out.println(x);
        System.out.println(p.fit(x));

    }

    public interface Problem {
        double fit(double x);

        boolean isNeighborBetter(double f0, double f1);
    }
}