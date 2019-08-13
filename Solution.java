package AHKT;

public class Solution{
    private int solution[];
    private int distance = 0;
    // 0 tidak bisa, 1 masih bisa di HKATerbatas
    public int bisaHKATerbatas = 1;
    
    // Constructs a blank solution
    public Solution(){
        solution = new int[Library.n];
    }
    
    public Solution(int a[]){
        solution = new int[Library.n];
        generateIndividual(a);
    }
    
    public Solution(int a[], int b){
        solution = a.clone();
        distance = b;
    }

    public int[] getSolution() {
        return solution;
    }
    
    // Creates a random individual
    public void generateRandomIndividual() {
        solution = Library.RandomPermutation(Library.n);
    }
    
    // Creates a random individual
    public void generateIndividual(int a[]) {
        solution = a.clone();
    }
    
    // Gets a node from the solution
    public int getNode(int i) {
        return solution[i];
    }

    // Sets a node in a certain position within a solution
    public void setNode(int i, int value) {
        solution[i] = value;
        // If the solutions been altered we need to reset the distance
        distance = 0;
    }
    
    public void setSolution(int a[]) {
        solution = a;
        distance = 0;
    }
    
    // Gets the total distance of the solution
    public int getDistance(){
        if (distance == 0) {
            distance = Library.CountDistance(solution);
        }
        return distance;
    }
    
    // Gets the total distance of the solution
    public void setDistance(int a){
        distance = a;
    }

    // Check if the solution contains a node
    public boolean containsNode(int a){
        boolean result = false;
        for(int i = 0; i < Library.n; i++) {
            if(solution[i] == a) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    // Get number of nodes on our solution
    public int solutionSize() {
        return Library.n;
    }
    
    public void mutate() {
        int rand1 = (int)(Math.random()*solution.length);
        int rand2 = (int)(Math.random()*solution.length);
        
        if(Math.abs(rand1 - rand2) <= 1 || Math.abs(rand1 - rand2) == solution.length-1) {
            rand2 = (rand1 + 2)%solution.length;
        }
        
        // a b c d e f g h
        // a g c d e f b h
        // b adalah solution[rand1]
        // g adalah solution[rand2]
        int a = solution[(rand1-1+solution.length)%solution.length];
        int b = solution[rand1];
        int c = solution[(rand1+1)%solution.length];
        int f = solution[(rand2-1+solution.length)%solution.length];
        int g = solution[rand2];
        int h = solution[(rand2+1)%solution.length];
        
        // score baru
        distance = getDistance()
                    - Library.d[a][b] - Library.d[b][c] - Library.d[f][g] - Library.d[g][h]
                    + Library.d[a][g] + Library.d[g][c] + Library.d[f][b] + Library.d[b][h];

        // b ditukar dengan g
        solution[rand1] = g;
        solution[rand2] = b;
        
        if(bisaHKATerbatas == 0) {
            bisaHKATerbatas = 1;
        }
    }
    
    public void Print() {
        for (int i = 0; i < Library.n; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println();
    }
    
    public void PrintDistance() {
        System.out.println(getDistance());
    }
}