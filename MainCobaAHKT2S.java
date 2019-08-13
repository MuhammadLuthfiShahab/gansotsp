package AHKT;

public class MainCobaAHKT2S {

    public static void main(String[] args) {
        /*
        int x[] = {10, 20, 30, 30, 20, 10};
        int y[] = {10, 10, 10, 20, 20, 20};
        Library.LoadCoordinate(x, y);
        
        int result1[] = {0,1,5,4,3,2};
        int result2[] = {0,1,2,5,3,4};
        */
        
        /*
        int x[] = {10, 20, 30, 40, 40, 30, 20, 10};
        int y[] = {10, 10, 10, 10, 20, 20, 20, 20};
        Library.LoadCoordinate(x, y);
        
        int result1[] = {0,1,6,5,4,7,3,2};
        int result2[] = {0,1,2,5,3,6,7,4};
        */
        
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
            "51.ts225.tsp.txt", //50
            "52.tsp225.tsp.txt", //51
            "53.pr226.tsp.txt", //52
            "54.gr229.tsp.txt", //53
            "55.gil262.tsp.txt", //54
            "56.pr264.tsp.txt", //55
            "57.a280.tsp.txt", //56
            "58.pr299.tsp.txt", //57
            "59.lin318.tsp.txt", //58
            "61.rd400.tsp.txt", //59
            "62.fl417.tsp.txt", //60
            "63.gr431.tsp.txt", //61
            "64.pr439.tsp.txt", //62
            "65.pcb442.tsp.txt", //63
            "66.d493.tsp.txt", //64
            "67.att532.tsp.txt", //65
            "68.ali535.tsp.txt", //66
            "69.si535.tsp.txt", //67
            "70.pa561.tsp.txt", //68
            "71.u574.tsp.txt", //69
            "72.rat575.tsp.txt", //70
            "73.p654.tsp.txt", //71
            "74.d657.tsp.txt", //72
            "75.gr666.tsp.txt", //73
            "76.u724.tsp.txt", //74
            "77.rat783.tsp.txt", //75
            "78.dsj1000.tsp.txt", //76
            "79.pr1002.tsp.txt", //77
            "80.si1032.tsp.txt", //78
            "81.u1060.tsp.txt" // 79
        };
        
        String path1 = "D:\\2. Articles or Conference Papers\\04. Heuristic baru untuk TSP\\2. Data\\2. Data Setelah Disederhanakan\\";
        String path2 = "D:\\2. Articles or Conference Papers\\04. Heuristic baru untuk TSP\\2. Data\\3. Data Full Matrix\\";
        
        // tipe ATT dan EUC_2D : 11, 14, 15, 17, 18, 19, dst
        int index = 49;
        
        Library.LoadDistanceMatrix(path2 + file[index]);
        
        Solution s1 = new Solution();
        s1.generateRandomIndividual();
        
        Solution s2 = new Solution();
        s2.generateRandomIndividual();
        
        int result1[] = s1.getSolution().clone();
        int result2[] = s2.getSolution().clone();
        
        result1 = Library.HKATerbatas1Solusi(result1, 100000);
        result2 = Library.HKATerbatas1Solusi(result2, 100000);
        
        Library.Print(result1);
        System.out.println(Library.Score(result1));
        Library.Print(result2);
        System.out.println(Library.Score(result2));
        
        int result3[] = Library.HKATerbatas2Solusi(result1, result2);
        Library.Print(result3);
        System.out.println(Library.Score(result3));
    }
    
}
