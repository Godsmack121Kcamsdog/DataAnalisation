package numericMethods.lab1.sample.logic.gauss;

public class Gaus {

    private Gaus() {}

    public static Double[] gaus(Double[][] A, Double[] B) {

        Double[] X = new Double[B.length];
        int Count = B.length;
        // число неизвестных = число уравнений
        // решение системы: прямой ход
        for (int i = 0; i <= Count - 2; i++)
        // по строкам
            for (int j = i + 1; j <= Count - 1; j++)// по столбцам
            {
                for (int k = i + 1; k <= Count - 1; k++)// по строкам
                    A[k][j] += -A[i][j] * A[k][i] / A[i][i];
                B[j] += -B[i] * A[i][j] / A[i][i];
            }

        // решение системы: обратный ход
        for (int j = Count - 1; j >= 0; j--)// по столбцам
        {
            X[j] = B[j];
            for (int k = j + 1; k <= Count - 1; k++)// по строкам
                X[j] += -A[k][j] * X[k];
            X[j] /= A[j][j];
        }
        for (int i = 0; i < X.length; i++) {
            System.out.println("A" + Integer.toString(i) + "=" + Double.toString(X[i]));
        }
        return X;
    }

}
