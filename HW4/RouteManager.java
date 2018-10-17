import java.util.ArrayList;

// Holds the cities of a route
public class RouteManager {
	// Holds our parts
	private static ArrayList<Part> destinationParts = new ArrayList<Part>();

	// Adds a destination part
	public static void addPart(Part part) {
		destinationParts.add(part);
	}

	// Get a part
	public static Part getPart(int index) {
		return (Part) destinationParts.get(index);
	}

	// Get the number of destination parts
	public static int numberOfParts() {
		return destinationParts.size();
	}
}
