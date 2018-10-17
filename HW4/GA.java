// Manages algorithms for evolving population
public class GA {

	// Evolves a population over one generation
	public static Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.populationSize(), false);

		// Crossover population
		// Loop over the new population's size and create individuals from
		// Current population
		for (int i = 0; i < newPopulation.populationSize(); i++) {
			// Select parents
			Route parent1 = selection(pop);
			Route parent2 = selection(pop);
			// Crossover parents
			Route child = crossover(parent1, parent2);
			// Add child to new population
			newPopulation.saveRoute(i, child);
		}

		// swap the new population a bit to add some new genetic material
		for (int i = 0; i < newPopulation.populationSize(); i++) {
			swap(newPopulation.getRoute(i));
		}

		return newPopulation;
	}

	// Applies crossover to a set of parents and creates offspring
	public static Route crossover(Route parent1, Route parent2) {
		// Create new child route
		Route child = new Route();

		// Get start and end sub route positions for parent1's route
		int startPos = (int) (Math.random() * parent1.routeSize());
		int endPos = (int) (Math.random() * parent1.routeSize());

		// Loop and add the sub route from parent1 to our child
		for (int i = 0; i < child.routeSize(); i++) {
			// If our start position is less than the end position
			if (startPos < endPos && i > startPos && i < endPos) {
				child.setPart(i, parent1.getPart(i));
			} // If our start position is larger
			else if (startPos > endPos) {
				if (!(i < startPos && i > endPos)) {
					child.setPart(i, parent1.getPart(i));
				}
			}
		}

		// Loop through parent2's part route
		for (int i = 0; i < parent2.routeSize(); i++) {
			// If child doesn't have the part add it
			if (!child.containsPart(parent2.getPart(i))) {
				// Loop to find a spare position in the child's route
				for (int j = 0; j < child.routeSize(); j++) {
					// Spare position found, add part
					if (child.getPart(j) == null) {
						child.setPart(j, parent2.getPart(i));
						break;
					}
				}
			}
		}
		return child;
	}

	// Generate a route using swap mutation
	private static void swap(Route route) {
		// Loop through route
		for (int routePos1 = 0; routePos1 < route.routeSize(); routePos1++) {
			// Apply the swap position
			if (Math.random() < 0.000005) {
				// Get a second random position in the route
				int routePos2 = (int) (route.routeSize() * Math.random());

				// Get the parts at target position in route
				Part part1 = route.getPart(routePos1);
				Part part2 = route.getPart(routePos2);

				// Swap them around
				route.setPart(routePos2, part1);
				route.setPart(routePos1, part2);
			}
		}
	}

	// Selects candidate route for crossover
	private static Route selection(Population pop) {
		// Create a pool population
		Population pool = new Population(5, false);
		// For each place in the pool get a random candidate route and
		// add it
		for (int i = 0; i < 5; i++) {
			int randomId = (int) (Math.random() * pop.populationSize());
			pool.saveRoute(i, pop.getRoute(randomId));
		}
		// Get the fittest route
		return pool.getFittest();
	}
}
