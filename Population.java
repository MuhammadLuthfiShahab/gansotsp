package AHKT;

public class Population {
    // Holds population of solution
    Solution solution[];

    // Construct a population
    public Population(int n, boolean b) {
        solution = new Solution[n];
        // If we need to initialise a population of solution do so
        if(b) {
            // Loop and create individuals
            for(int i = 0; i < n; i++) {
                Solution newTour = new Solution();
                newTour.generateRandomIndividual();
                saveSolution(i, newTour);
            }
        }
    }

    // Saves a tour
    public void saveSolution(int index, Solution s) {
        solution[index] = s;
    }
    
    // Gets a tour from population
    public Solution getSolution(int index) {
        return solution[index];
    }

    // Gets the best tour in the population
    public Solution getFittest() {
        Solution fittest = solution[0];
        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++) {
            if (getSolution(i).getDistance() < fittest.getDistance()) {
                fittest = getSolution(i);
            }
        }
        return fittest;
    }

    // Gets population size
    public int populationSize() {
        return solution.length;
    }
    
    public void Print() {
        for(int i = 0; i < solution.length; i++) {
            solution[i].Print();
        }
        System.out.println();
    }
    
    public void PrintDistance() {
        for(int i = 0; i < solution.length; i++) {
            solution[i].PrintDistance();
        }
        System.out.println();
    }
}
