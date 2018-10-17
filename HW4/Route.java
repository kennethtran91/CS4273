import java.util.ArrayList;
import java.util.Collections;

// Stores a candidate route
public class Route {
	// Holds our route of robot
	private ArrayList<Part> route = new ArrayList<Part>();
	private double fitness = 0;
	private double distance = 0;

	// Constructs a blank route
	public Route() {
		for (int i = 0; i < RouteManager.numberOfParts(); i++) {
			route.add(null);
		}
	}

	public Route(ArrayList<Part> route) {
		this.route = route;
	}

	// Creates a random individual
	public void generateIndividual() {
		// Loop through all our destination locations and add them to our route
		for (int partIndex = 0; partIndex < RouteManager.numberOfParts(); partIndex++) {
			setPart(partIndex, RouteManager.getPart(partIndex));
		}
		// Randomly reorder the route
		Collections.shuffle(route);
	}

	// Gets a part from the route
	public Part getPart(int routePosition) {
		return (Part) route.get(routePosition);
	}

	// Sets a part in a certain position within a route
	public void setPart(int routePosition, Part part) {
		route.set(routePosition, part);
		// If the routes been altered we need to reset the fitness and distance
		fitness = 0;
		distance = 0;
	}

	// Gets the routes fitness
	public double getFitness() {
		if (fitness == 0) {
			fitness = 1 / (double) getDistance();
		}
		return fitness;
	}

	// Gets the total distance of the route
	public double getDistance() {
		if (distance == 0) {
			double routeDistance = 0;
			// Loop through our route's cities
			for (int partIndex = 0; partIndex < routeSize(); partIndex++) {
				// Get part we're travelling from
				Part partFrom = getPart(partIndex);
				// Part we're travelling to
				Part partTo;
				// Check we're not on our route's last location, if we are set our
				// route's final destination part to our starting location
				if (partIndex + 1 < routeSize()) {
					partTo = getPart(partIndex + 1);
				} else {
					partTo = getPart(0);
				}
				// Get the distance between the two parts
				routeDistance += partFrom.distanceTo(partTo);
			}
			distance = routeDistance;
		}
		return distance;
	}

	// Get number of locations on our route
	public int routeSize() {
		return route.size();
	}

	// Check if the route contains a part
	public boolean containsPart(Part part) {
		return route.contains(part);
	}

	@Override
	public String toString() {
		String geneString = "";
		for (int i = 0; i < routeSize(); i++) {
			geneString += getPart(i);
		}
		return geneString;
	}
}
