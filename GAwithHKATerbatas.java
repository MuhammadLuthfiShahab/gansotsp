package AHKT;

public class GAwithHKATerbatas {
    /* GA parameters */
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    
    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
        int a = 2*pop.populationSize();
        int b = pop.populationSize();

        Population newPopulation = new Population(b, false);

        for (int i = 0; i < b; i++) {
            /*
            Solution parent1 = tournamentSelection(pop);
            Solution parent2 = tournamentSelection(pop);
            Solution child = crossover(parent1, parent2);
            */
            Solution child = new Solution(pop.getSolution(i).getSolution(), pop.getSolution(i).getDistance());
            mutate(child);
            newPopulation.saveSolution(i, child);
        }

        //Solution aa = newPopulation.getFittest();

        /*
        for (int i = 0; i < b; i++) {
            if (newPopulation.getSolution(i) != aa){
                //System.out.println("\n");
                //newPopulation.getSolution(i).Print();
                //newPopulation.getSolution(i).PrintDistance();
                mutate(newPopulation.getSolution(i));
                //newPopulation.getSolution(i).Print();
                //newPopulation.getSolution(i).PrintDistance();
                //if()
            }
        }
        */
        
        Population newPopulation2 = new Population(a, false);

        for (int i = 0; i < pop.populationSize(); i++) {
            newPopulation2.saveSolution(i, pop.getSolution(i));
        }

        for (int i = 0; i < pop.populationSize(); i++) {
            newPopulation2.saveSolution((b+i), newPopulation.getSolution(i));
        }
        
        
        // masih bisa dipercepat dengan metode sort yang lebih baik
        for (int i = 0; i < a; i++){
            int k = i;
            Solution fittest = newPopulation2.getSolution(i);

            for (int j = i+1; j < a; j++) {
                if (newPopulation2.getSolution(j).getDistance() < fittest.getDistance()) {
                    fittest = newPopulation2.getSolution(j);
                    k = j;
                }
            }

            newPopulation2.saveSolution(k, newPopulation2.getSolution(i));
            newPopulation2.saveSolution(i, fittest);
        }
        
        Population newPopulation3 = new Population(b, false);
        
        int m = 0;
        newPopulation3.saveSolution(0, newPopulation2.getSolution(0));

        for (int i = 1; i < b; i++){
            if (newPopulation2.getSolution(i).getDistance() != newPopulation3.getSolution(m).getDistance()){
                m++;
                newPopulation3.saveSolution(m, newPopulation2.getSolution(i));
            }
        }

        if ((m+1) != b){
            for (int i = m+1; i < b; i++){
                int ran = (int) (Math.random() * a);
                newPopulation3.saveSolution(i, newPopulation2.getSolution(ran));
            }
        }

        //evolvePopulationWithHKABaru(newPopulation3);
        evolvePopulationWithHKABaru(newPopulation3);
        
        return newPopulation3;
    }
    
    public static void evolvePopulationWithHKABaru(Population pop) {
        int max = 1;
        for(int i = 0; i < pop.populationSize(); i++) {
            Solution b = Library.HKATerbatas1Solusi(pop.getSolution(i), max);
            pop.saveSolution(i, b);
            /*
            for(int j = 0; j < max; j++) {
                Library.HKATerbatas1SolusiDari2SampaiNPer2(pop.getSolution(i));
            }
            */
        }
    }
    
    // Applies crossover to a set of parents and creates offspring
    public static Solution crossover(Solution parent1, Solution parent2) {
        // Create new child tour
        Solution child = new Solution();

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.solutionSize());
        int endPos = (int) (Math.random() * parent1.solutionSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.solutionSize(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setNode(i, parent1.getNode(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setNode(i, parent1.getNode(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.solutionSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsNode(parent2.getNode(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.solutionSize(); ii++) {
                    // Spare position found, add city
                    if (child.getNode(ii) == 0) {
                        child.setNode(ii, parent2.getNode(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    // Mutate a tour using swap mutation
    private static void mutate(Solution tour) {
        tour.mutate();
        /*
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.solutionSize(); tourPos1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.solutionSize() * Math.random());

                // Get the cities at target position in tour
                int city1 = tour.getNode(tourPos1);
                int city2 = tour.getNode(tourPos2);

                // Swap them around
                tour.setNode(tourPos2, city1);
                tour.setNode(tourPos1, city2);
            }
        }
        */
    }

    // Selects candidate tour for crossover
    private static Solution tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveSolution(i, pop.getSolution(randomId));
        }
        // Get the fittest tour
        Solution fittest = tournament.getFittest();
        return fittest;
    }
}