public class Part {
	double x, y;
	int ref;

	// Constructs a part location at chosen x, y location
	public Part(int ref, double x, double y) {
		this.x = x;
		this.y = y;
		this.ref = ref;
	}

	public int getRef() {
		return this.ref;
	}

	// Get part's x 
	public double getX() {
		return this.x;
	}

	// Get part's y 
	public double getY() {
		return this.y;
	}

	// Get the distance to given location
	public double distanceTo(Part part) {
		double xDistance = Math.abs(getX() - part.getX());
		double yDistance = Math.abs(getY() - part.getY());
		return Math.hypot(xDistance, yDistance);
	}

	@Override
	public String toString() {
		return getRef() + ".";
	}
}
