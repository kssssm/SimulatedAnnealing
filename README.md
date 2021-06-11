
# SimulatedAnnealing

---------


## SimulatedAnnealing 

- 고온 물질의 분자가 식어가면서 (annealing) 점차 안정화되어 가는 과정을 묘사(simulation)한 알고리즘

- 온도가 높을수록 분자의 이동이 자유롭고, 온도가 낮을수록 점차 안정화되어 수렴된다.

- 탐색 공간에서 주어진 함수의 전역 최적점(global optimum)의 좋은 근사해를 찾는 확률적 휴리스틱 접근 방식

![simulatedAnnealing](https://user-images.githubusercontent.com/81538527/121649050-1e49df00-cad3-11eb-9f40-4990158ccbdb.png)




### 과정
          초기 온도 t=200, 확률 p=e^(-d/t), 냉각율 a =0.99 로 설정

(1) 임의의 후보해를 설정한뒤 이웃해를 정의한다.

(2) 후보해와 이웃해를 비교한다.
  - 이웃해가 더 좋을 경우
     이웃해를 후보해로 설정
     
  - 후보해가 더 좋을 경우
     온도에 따른 확률(P)를 적용하여 자유롭게 탐색 할 수있는 기회를 준다.

(3) 이 과정을 반복하여 최적해를 찾는다.



#### 적용

- f(x) = 2x^3 + 3x^2 + 70 의 전역 최적점 

- 코드
```java
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
```




- 결과

![결과](https://user-images.githubusercontent.com/81538527/121649288-6832c500-cad3-11eb-901e-6818ae06fb41.png)



# curve fitting


## 모델


한 달 동안 일(독립변수) 마다 핸드폰 어플에 기록된 누적 걸음 수(종속변수)
![모델](https://user-images.githubusercontent.com/81538527/121650008-1474ab80-cad4-11eb-8f2a-6c0bf3f56a74.jpg)





![추정](https://user-images.githubusercontent.com/81538527/121650135-3b32e200-cad4-11eb-968d-464239980045.png")

y=34x-21로 추정 할 수 있다.








