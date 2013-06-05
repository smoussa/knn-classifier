import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The WeightedKNNClassifier class computes a simple KNN classification but
 * with weighted values inversely proportional to distance
 */
public class WeightedKNNClassifier extends KNNClassifier {

	private ArrayList<DataPoint> dataList;
	private int dataSize;
	private int numVariables;

	/**
	 * Constructor for initialising variables
	 * @param dataList The list of data points
	 */
	public WeightedKNNClassifier(ArrayList<DataPoint> dataList) {
		this.dataList = dataList;
		dataSize = dataList.size();
		numVariables = dataList.get(0).getVariables().size();
	}

	/**
	 * Gets the nearest neighbour for a given data point
	 * @param pointA The data point
	 * @return the list of neighbour data points
	 */
	private ArrayList<DataPoint> getNearestNeighbours(DataPoint pointA) {

		ArrayList<Double> pointAValues = pointA.getVariables();
		ArrayList<DataPoint> pointANeighbours = new ArrayList<DataPoint>();

		// for each other data point
		for (int j = 0; j < dataSize; j++) {

			DataPoint pointB = dataList.get(j);
			ArrayList<Double> pointBValues = pointB.getVariables();

			if (pointA.getIndex() != j) { // if they are not the same

				double distance = calculateEuclideanDistance(
						numVariables,
						pointAValues,
						pointBValues);
				pointB.setRelativeDistance(distance);
				pointANeighbours.add(pointB);

			}
		}

		Collections.sort(pointANeighbours);

		return pointANeighbours;
	}

	/**
	 * Predicts the category for a given data point
	 * @param neighbours The neighbours of the data point
	 * @param k The number of neighbours, K
	 * @return the predicted category/class
	 */
	private String predictCategory(ArrayList<DataPoint> neighbours, int k) {

		// create a map for weighting the data points according to distance
		HashMap<String, Double> map = new HashMap<String, Double>(k);

		// initialise best prediction
		String category = neighbours.get(0).toString();
		double highest = 0.0;

		// for each neighbour
		for (int n = 0; n < k - 1; n++) {

			DataPoint neighbour = neighbours.get(n);
			String neighbourClass = neighbour.toString();
			double distance = neighbour.getRelativeDistance();

			if (map.get(neighbourClass) != null) { // if category was already seen

				double rating = map.get(neighbourClass);
				rating += (1 / distance);

				if (rating > highest) {
					highest = rating;
					category = neighbourClass;
				}

				map.put(neighbourClass, rating);

			} else { // if not seen before
				map.put(neighbourClass, 1.0);
			}

		}

		return category;
	}

	/**
	 * This method loops through each data point and classifies them
	 * @param k The number of neighbours, K
	 * @return the score for the classification
	 */
	public double classify(int k) {

		double score = 0;

		// for each data point
		for (int i = 0; i < dataSize; i++) {

			DataPoint pointA = dataList.get(i);
			ArrayList<DataPoint> pointANeighbours = getNearestNeighbours(pointA);
			String category = predictCategory(pointANeighbours, k);

			if (category.equals(pointA.toString())) {
				score++;
			}

		}

		return score;
	}

}
