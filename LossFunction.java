package bj;

public interface LossFunction {
    // Fehlergradient "gradient()": Ableitung der Loss-Funktion nach der Knotenausgabe (dL/da)
    // Beschreibt, wie stark sich der Verlust �ndert, wenn die Ausgabe des Knotens minimal ver�ndert wird

    // Hinweis:
    // - Bei linearen Ausg�ngen entspricht der Fehlergradient direkt dem Delta, das f�r Backpropagation verwendet wird
    // - Bei nichtlinearen Aktivierungen muss im Backward-Pass Delta = Fehlergradient * Aktivierungsableitung berechnet werden
    // - F�r Softmax + CrossEntropy liefert der Fehlergradient direkt die Werte, die als Delta in Backpropagation genutzt werden
    double[] gradient(double[] prediction, double[] target);
    double loss(double[] predictions, double[] labels);
}
