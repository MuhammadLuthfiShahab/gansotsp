package AHKT;

public class MainPrintPanel {

    public static void main(String[] args) throws Exception{
        int number_of_populations = 100;
        double max_time_computation = 100;
        int banyak_percobaan = 1;
        
        String file[] = {
            "1.burma14.tsp.txt", //0
            "2.ulysses16.tsp.txt", //1
            "3.gr17.tsp.txt", //2
            "4.gr21.tsp.txt", //3
            "5.ulysses22.tsp.txt", //4
            "6.gr24.tsp.txt", //5
            "7.fri26.tsp.txt", //6
            "8.bayg29.tsp.txt", //7
            "9.bays29.tsp.txt", //8
            "10.dantzig42.tsp.txt", //9
            "11.swiss42.tsp.txt", //10
            "12.att48.tsp.txt", //11
            "13.gr48.tsp.txt", //12
            "14.hk48.tsp.txt", //13
            "15.eil51.tsp.txt", //14
            "16.berlin52.tsp.txt", //15
            "17.brazil58.tsp.txt", //16
            "18.st70.tsp.txt", //17
            "19.eil76.tsp.txt", //18
            "20.pr76.tsp.txt", //19
            "21.gr96.tsp.txt", //20
            "22.rat99.tsp.txt", //21
            "23.kroA100.tsp.txt", //22
            "24.kroB100.tsp.txt", //23
            "25.kroC100.tsp.txt", //24
            "26.kroD100.tsp.txt", //25
            "27.kroE100.tsp.txt", //26
            "28.rd100.tsp.txt", //27
            "29.eil101.tsp.txt", //28
            "30.lin105.tsp.txt", //29
            "31.pr107.tsp.txt", //30
            "32.gr120.tsp.txt", //31
            "33.pr124.tsp.txt", //32
            "34.bier127.tsp.txt", //33
            "35.ch130.tsp.txt", //34
            "36.pr136.tsp.txt", //35
            "37.gr137.tsp.txt", //36
            "38.pr144.tsp.txt", //37
            "39.ch150.tsp.txt", //38
            "40.kroA150.tsp.txt", //39
            "41.kroB150.tsp.txt", //40
            "42.pr152.tsp.txt", //41
            "43.u159.tsp.txt", //42
            "44.si175.tsp.txt", //43
            "45.brg180.tsp.txt", //44
            "46.rat195.tsp.txt", //45
            "47.d198.tsp.txt", //46
            "48.kroA200.tsp.txt", //47
            "49.kroB200.tsp.txt", //48
            "50.gr202.tsp.txt", //49
        };
        
        int best_known_distance[] = {
            3323, //0
            6859, //1
            2085, //2
            2707, //3
            7013, //4
            1272, //5
            937, //6
            1610, //7
            2020, //8
            699, //9
            1273, //10
            10628, //11
            5046, //12
            11461, //13
            426, //14
            7542, //15
            25395, //16
            675, //17
            538, //18
            108159, //19
            55209, //20
            1211, //21
            21282, //22
            22141, //23
            20749, //24
            21294, //25
            22068, //26
            7910, //27
            629, //28
            14379, //29
            44303, //30
            6942, //31
            59030, //32
            118282, //33
            6110, //34
            96772, //35
            69853, //36
            58537, //37
            6528, //38
            26524, //39
            26130, //40
            73682, //41
            42080, //42
            21407, //43
            1950, //44
            2323, //45
            15780, //46
            29368, //47
            29437, //48
            40160, //49
        };
        
        String path1 = "D:\\2. Articles or Conference Papers\\04. Heuristic baru untuk TSP\\2. Data\\3. Data Full Matrix\\";
        String path2 = "D:\\2. Articles or Conference Papers\\04. Heuristic baru untuk TSP\\2. Data\\2. Data Setelah Disederhanakan\\";
        
        // tipe ATT dan EUC_2D : 11, 14, 15, 17, 18, 19, 48, dst
        int index = 48;
        Library.LoadDistanceMatrix(path1 + file[index]);

        Population pop1 = new Population(number_of_populations, true);

        long startTime1 = System.nanoTime();
        long totalTime1;
        double dtotalTime1 = 0;

        // program akan berhenti saat sudah lebih dari 100 detik, atau sudah mendapat solusi optimal
        while(dtotalTime1 <= max_time_computation && pop1.getFittest().getDistance() != best_known_distance[index]) {
            //pop1 = GA.evolvePopulation(pop1);
            pop1 = GAwithHKATerbatas.evolvePopulation(pop1);
            totalTime1 = System.nanoTime() - startTime1;
            dtotalTime1 = (double)totalTime1/1000000000;
        }
        
        Library.LoadCoordinate(path2 + file[index]);
        Library.SetResult1(pop1.getFittest().getSolution());
        new PanelCoordinate();
        new PanelCoordinateAndResult1();
        
        
    }
}