import java.util.ArrayList;


public abstract class KNNClassifier {

	protected double calculateEuclideanDistance(
			int num,
			ArrayList<Double> pointAValues,
			ArrayList<Double> pointBValues) {

		double squaredDistance = 0.0;
		for (int v = 0; v < num; v++) {
			squaredDistance +=
					Math.pow(pointAValues.get(v) - pointBValues.get(v), 2);
		}
		double distance = Math.sqrt(squaredDistance);

		return distance;
	}

}
