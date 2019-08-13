package AHKT;

import java.util.Arrays;

public class Library {
    public static int n; //number of nodes
    public static int d[][]; //distance matrix
    public static int coordinate[][];
    public static int result[];
    public static int result1[];
    public static int result2[];
    
    public static void LoadCoordinate(String path) {
        java.io.File file = new java.io.File(path);
        
        try (java.util.Scanner input = new java.util.Scanner(file);) {
            input.nextInt();
            n = input.nextInt();
            coordinate = new int[n][2];
            for(int i = 0; i < n; i++) {
                input.nextInt();
                coordinate[i][0] = (int)input.nextDouble();
                coordinate[i][1] = (int)input.nextDouble();
            }
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void LoadCoordinate(int x[], int y[]) {
        n = x.length;
        coordinate = new int[n][2];
        for(int i = 0; i < n; i++) {
            coordinate[i][0] = x[i];
            coordinate[i][1] = y[i];
        }
        LoadDistanceMatrix(x, y);
    }
    
    public static void SetResult1(int a[]) {
        result1 = a;
    }
    
    public static void SetResult2(int a[]) {
        result2 = a;
    }
    
    
    
    public static int[] HKATerbatas2Solusi(int result1[], int result2[]) {
        int best_result[] = new int[1];
        int best_score = 0;
        //int panjang = 3;
        
        for(int panjang = 3; panjang <= n/2; panjang++) {
            // mendaftar yang panjangnya 3 dari result1
            for(int i = 0; i < n; i++) {
                //System.out.println("i = " + (i));

                int a[] = Copy(result1, i, panjang);
                int A[] = a.clone();
                Arrays.sort(A);
                //Print(a);
                //Print(A);

                // mencari yang panjangnya 3 dari result2 dan sama dengan A[]
                for(int j = 0; j < n; j++) {
                    //System.out.println("j = " + (j));

                    int b[] = Copy(result2, j, panjang);
                    int B[] = b.clone();
                    Arrays.sort(B);
                    //Print(b);
                    //Print(B);

                    if(Arrays.equals(A, B)) {
                        //System.out.println("Samasamasamasamasama");
                        // c[] adalah result1[] yang sudah dikurangi dengan a[]
                        int c[] = Copy(result1, (i+panjang)%n, n-panjang);
                        //Print(c);
                        // d[] adalah result2[] yang sudah dikurangi dengan b[]
                        int d[] = Copy(result2, (j+panjang)%n, n-panjang);
                        //Print(d);

                        // a[] digabungkan dengan d[] dan d1[], kemudian diambil yang terbaik
                        int d1[] = Reverse(d);
                        //Print(d1);

                        int e1[] = Combine(a, d);
                        //Print(e1);

                        int e2[] = Combine(a, d1);
                        //Print(e2);

                        int score1 = Score(e1);
                        int score2 = Score(e2);
                        if(score2 < score1) {
                            e1 = e2;
                            score1 = score2;
                        }

                        // b[] digabungkan dengan c[]
                        int c1[] = Reverse(c);
                        //Print(c1);

                        int f1[] = Combine(b, c);
                        //Print(f1);

                        int f2[] = Combine(b, c1);
                        //Print(f2);

                        int score3 = Score(f1);
                        int score4 = Score(f2);
                        if(score4 < score3) {
                            f1 = f2;
                            score3 = score4;
                        }

                        // diambil yang terbaik antara score1 dan score3 dan dimasukkan ke e1 dan score1
                        if(score3 < score1) {
                            e1 = f1;
                            score1 = score3;
                        }

                        // dibandingkan dengan best_result[]
                        if(best_result.length == n) {
                            if(score1 < best_score) {
                                best_result = e1;
                                best_score = score1;
                            }
                        }
                        else {
                            best_result = e1;
                            best_score = score1;
                        }

                        break;
                    }
                }

                //System.out.println("Hasil");
                //Print(best_result);
                //System.out.println("");
            }
        }
        return best_result;
    }
    
    public static int[] Combine(int a[], int b[]) {
        int c[] = new int[a.length + b.length];
        for(int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }
        for(int i = 0; i < b.length; i++) {
            c[a.length + i] = b[i];
        }
        
        return c;
    }
    
    // membalik urutan elemen dalam array
    public static int[] Reverse(int a[]) {
        int b[] = new int[a.length];
        for(int i = 0; i < a.length; i++) {
            b[i] = a[a.length-1-i];
        }
        
        return b;
    }
    
    // mengkopi sebanyak J elemen berurutan di a[], dimulai dari index ke I
    public static int[] Copy(int a[], int I, int J) {
        int b[] = new int[J];
        for(int i = 0; i < J; i++) {
            b[i] = a[(I+i)%a.length];
        }
        
        return b;
    }
    
    public static Solution HKATerbatas1Solusi(Solution s, int max_iteration) {
        int result[] = s.getSolution();
        int skor_lama = s.getDistance();
        
        for(int i = 0; i < max_iteration; i++) {
            if(s.bisaHKATerbatas == 1) {
                int a[] = HKATerbatas1SolusiDari2SampaiNPer2(result, skor_lama);

                Putar(result, a[0], a[1]);

                int skor_baru = a[2];

                if(skor_baru != skor_lama) {
                    skor_lama = skor_baru;
                    s.setSolution(result);
                    s.setDistance(skor_lama);
                }
                else {
                    s.bisaHKATerbatas = 0;
                }
            }
            else {
                break;
            }
        }
        
        return s;
    }
    
    public static int[] HKATerbatas1Solusi(int result[], int max_iteration) {
        int skor_lama = CountDistance(result);
        
        for(int i = 0; i < max_iteration; i++) {
            int a[] = HKATerbatas1SolusiDari2SampaiNPer2(result, skor_lama);
            
            Putar(result, a[0], a[1]);
            
            int skor_baru = a[2];
            
            /*
            print(result);
            System.out.println(skor_baru);
            System.out.println("");
            */
            
            if(skor_baru != skor_lama) {
                skor_lama = skor_baru;
            }
            else {
                break;
            }
        }
        
        return result;
    }
    
    public static int[] HKATerbatas1SolusiDari2SampaiNPer2(int a[], int score_a) {
        int N = a.length/2;
        int index[] = new int[N-1];
        int score[] = new int[N-1];
        
        // mencari index terbaik jika subsolusi sepanjang i di reverse
        for(int i = 2; i <= N; i++) {
            int hasil[] = PutarBNodeDariA(a, i);
            
            index[i-2] = hasil[0];
            //int b[] = a.clone();
            //Putar(b, index[i-2], i);
            //score[i-2] = Score(b);
            
            /*
            // i adalah index dan b adalah panjang
            int A = a[(index[i-2]-1 + a.length) % a.length];
            int B = a[index[i-2]];
            int C = a[(index[i-2]+i-1) % a.length];
            int D = a[(index[i-2]+i) % a.length];
            */
            score[i-2] = score_a + hasil[1];
        }
        
        int index_of_min = IndexOfMin(score);
        
        int result[] = {index[index_of_min], index_of_min+2, score[index_of_min]};
        
        return result;
    }
    
    public static void HKATerbatas1SolusiDari2SampaiNPer2aaa(Solution s) {
        int a[] = s.getSolution();
        int N = a.length/2;
        int index[] = new int[N-1];
        int score[] = new int[N-1];

        // mencari index terbaik jika subsolusi sepanjang i di reverse
        for(int i = 2; i <= N; i++) {
            int hasil[] = PutarBNodeDariA(a, i);

            index[i-2] = hasil[0];
            score[i-2] = s.getDistance() + hasil[1];
        }

        int index_of_min = IndexOfMin(score);
        
        int result[] = {index[index_of_min], index_of_min+2, score[index_of_min]};
        
        int b[] = a.clone();
        Putar(b, index[index_of_min], index_of_min+2);

        int score_awal = Score(a);
        int score_akhir = Score(b);

        if(score_akhir < score_awal) {
            s.setSolution(b);
        }
        else {
            s.bisaHKATerbatas = 0;
        }
    }
    
    public static void HKATerbatas1SolusiDari2SampaiNPer2(Solution s) {
        // masih bisa di improve
        if(s.bisaHKATerbatas == 1) {
            int a[] = s.getSolution();
            int N = a.length/2;
            int index[] = new int[N-1];
            int score[] = new int[N-1];
            
            // mencari index terbaik jika subsolusi sepanjang i di reverse
            for(int i = 2; i <= N; i++) {
                int hasil[] = PutarBNodeDariA(a, i);

                index[i-2] = hasil[0];
                score[i-2] = s.getDistance() + hasil[1];
            }

            int index_of_min = IndexOfMin(score);
            int b[] = a.clone();
            Putar(b, index[index_of_min], index_of_min+2);

            int score_awal = Score(a);
            int score_akhir = Score(b);
            
            if(score_akhir < score_awal) {
                s.setSolution(b);
            }
            else {
                s.bisaHKATerbatas = 0;
            }
        }
        // do nothing, tidak perlu diimprove karena sudah tidak bisa diimprove lagi
        else {
            
        }
    }
    
    // sub-solusi sepanjang b dari a[] di reverse urutannya. 
    public static int[] PutarBNodeDariA(int a[], int b) {
        int score[] = new int[a.length];
        for(int i = 0; i < a.length; i++) {
            //                  i    i+1  i+2
            // diputar menjadi  i+2  i+1  i
            
            // index    A    B         C    D
            // sehingga i-1  i    i+1  i+2  i+3
            // menjadi  i-1  i+2  i+1  i    i+3
            
            int A = a[(i-1+a.length)%a.length];
            int B = a[i];
            int C = a[(i+b-1)%a.length];
            int D = a[(i+b)%a.length];
            
            score[i] = -d[A][B] - d[C][D] + d[A][C] + d[B][D];
        }
        int index_of_min = IndexOfMin(score);
        
        // yang dikembalikan adalah index dimana ia optimal, dan score dari perputaran index itu
        int result[] = {index_of_min, score[index_of_min]};
        
        return result;
    }
    
    public static int IndexOfMin(int a[]) {
        int index = 0;
        double min = a[0];
        for(int i = 1; i < a.length; i++) {
            if(min > a[i]) {
                min = a[i];
                index = i;
            }
        }
        return index;
    }
    
    public static void Putar(int a[], int p, int n) {
        for(int i = 0; i < n/2; i++) {
            int b = (p+i)%a.length;
            int c = (p+n-i-1)%a.length;
            int dummy = a[b];
            a[b] = a[c];
            a[c] = dummy;
        }
    }
    
    public static int CountDistance(int[] a) {
        int n = a.length;
        int result = d[a[0]][a[n-1]];
        for(int i = 1; i < n; i++) {
            result += d[a[i]][a[i-1]];
        }
        
        return result;
    }
    
    public static int Score(int a[]) {
        int n = a.length;
        int score = d[a[0]][a[n-1]];
        for(int i = 1; i < n; i++) {
            score += d[a[i]][a[i-1]];
        }
        //System.out.println(score);
        return score;
    }
    
    public static int[] Sama(int a[]) {
        int result[] = new int[a.length];
        for(int i = 0; i < a.length; i++) {
            result[i] = a[i];
        }
        return result;
    }
    
    
    public static int[] GetResult2() {
        return result2;
    }
    
    public static int[] GetResult1() {
        return result1;
    }
    
    public static int[][] GetCoordinate() {
        return coordinate;
    }
    
    
    
    public static void LoadResult(String path) {
        java.io.File file = new java.io.File(path);
        
        try (java.util.Scanner input = new java.util.Scanner(file);) {
            int n = input.nextInt();
            result = new int[n];
            for(int i = 0; i < n; i++) {
                result[i] = input.nextInt();
            }
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    public static double StandardDeviation(int a[]) {
        double mean = Mean(a);
        double sum = 0;
        for(int i = 0; i < a.length; i++) {
            sum = sum + ((double)a[i] - mean)*((double)a[i]-mean);
        }
        double result = sum / (a.length - 1);
        result = Math.sqrt(result);
        return result;
    }
    
    public static double Mean(int a[]) {
        double jumlah = 0;
        for(int i = 0; i < a.length; i++) {
            jumlah = jumlah + a[i];
        }
        jumlah = jumlah / a.length;
        
        return jumlah;
    }
    
    public static double Mean(double a[]) {
        double jumlah = 0;
        for(int i = 0; i < a.length; i++) {
            jumlah = jumlah + a[i];
        }
        jumlah = jumlah / a.length;
        
        return jumlah;
    }
    
    public static void LoadDistanceMatrix(String path) {
        java.io.File file = new java.io.File(path);
        
        try (java.util.Scanner input = new java.util.Scanner(file);) {
            n = input.nextInt();
            d = new int[n][n];
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    d[i][j] = input.nextInt();
                }
            }
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    //membuat d dari beberapa x_i dan y_i yang diketahui
    public static void LoadDistanceMatrix(int x[], int y[]) {
        n = x.length;
        d = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i != j) {
                    double xd = x[i] - x[j];
                    double yd = y[i] - y[j];
                    double distance = Math.sqrt(xd*xd + yd*yd) + 0.5;
                    int dij = (int)distance;
                    d[i][j] = dij;
                }
                else {
                    d[i][j] = 0;
                }
            }
        }
    }
    
    // index 0 diletakkan di depan
    public static int[] Sort(int[] a) {
        int[] b = new int[a.length];
        
        int position = 0;
        if(a[0] != 0) {
            for(int i = 1; i < a.length; i++) {
                if(a[i] == 0) {
                    position = i;
                    break;
                }
            }
        }
        for(int i = position; i < a.length; i++) {
            b[i-position] = a[i];
        }
        for(int i = 0; i < position; i++) {
            b[i+a.length-position] = a[i];
        }
        
        if(b[1] < b[a.length-1]) {
            return b;
        }
        else {
            int[] c = new int[a.length];
            c[0] = b[0];
            for(int i = 1; i < a.length; i++) {
                c[i] = b[a.length-i];
            }
            return c;
        }
    }
    
    public static int[] Add(int[] a, int b, int position) {
        int[] c = new int[a.length+1];
        for(int i = 0; i < position; i++) {
            c[i] = a[i];
        }
        c[position] = b;
        for(int i = position+1; i < a.length+1; i++) {
            c[i] = a[i-1];
        }
        return c;
    }
    
    public static int[] RandomPermutation(int n) {
        int int1[] = new int[n];
        for(int i = 0; i < n; i++){
            int1[i] = i;
        }
        
        //generate random permutation
        int permutation[] = new int[n];
        for(int i = 0; i < n; i++){
            int j = (int)(Math.random()*(n-i));
            permutation[i] = int1[j];
            int1[j] = int1[n-i-1];
        }
        
        return permutation;
    }
    
    // berakhir di x, melalui S, berawal di 0
    public static int[] g(int x, int S[]) {
        int result[] = new int[2];
        if(S.length == 1) {
            result[0] = d[x][S[0]] + d[S[0]][0];
            result[1] = S[0];
        }
        else if(S.length == 2) {
            int a[] = MinAndIndex(x, x, S[0], S[1]);
        }
        return result;
    }
    
    public static void p(int x, int S[]) {
        if(S.length == 0) {
            System.out.println(0);
        }
        else if(S.length == 1) {
            System.out.println(S[0]);
        }
    }
    
    
    public static void hka5() {
        // 1. GenerateHKA2
        // 2. Copy S[] dari 5x.txt
        // 3. GenerateHKA3
        // 4. Copy x yang sesuai dari 5S.txt
        // 5. Masukkan ke NewMain3.java di tsp4
        int a2_1[] = {d[0][2] + d[2][1] , 2};
        int a3_1[] = {d[0][3] + d[3][1] , 3};
        int a4_1[] = {d[0][4] + d[4][1] , 4};
        int a1_2[] = {d[0][1] + d[1][2] , 1};
        int a3_2[] = {d[0][3] + d[3][2] , 3};
        int a4_2[] = {d[0][4] + d[4][2] , 4};
        int a1_3[] = {d[0][1] + d[1][3] , 1};
        int a2_3[] = {d[0][2] + d[2][3] , 2};
        int a4_3[] = {d[0][4] + d[4][3] , 4};
        int a1_4[] = {d[0][1] + d[1][4] , 1};
        int a2_4[] = {d[0][2] + d[2][4] , 2};
        int a3_4[] = {d[0][3] + d[3][4] , 3};
        
        int a23_1[] = MinAndIndex(
                a3_2[0] + d[2][1] , 2 , 
                a2_3[0] + d[3][1] , 3
                );
        int a24_1[] = MinAndIndex(
                a4_2[0] + d[2][1] , 2 , 
                a2_4[0] + d[4][1] , 4
                );
        int a34_1[] = MinAndIndex(
                a4_3[0] + d[3][1] , 3 , 
                a3_4[0] + d[4][1] , 4
                );
        int a13_2[] = MinAndIndex(
                a3_1[0] + d[1][2] , 1 , 
                a1_3[0] + d[3][2] , 3
                );
        int a14_2[] = MinAndIndex(
                a4_1[0] + d[1][2] , 1 , 
                a1_4[0] + d[4][2] , 4
                );
        int a34_2[] = MinAndIndex(
                a4_3[0] + d[3][2] , 3 , 
                a3_4[0] + d[4][2] , 4
                );
        int a12_3[] = MinAndIndex(
                a2_1[0] + d[1][3] , 1 , 
                a1_2[0] + d[2][3] , 2
                );
        int a14_3[] = MinAndIndex(
                a4_1[0] + d[1][3] , 1 , 
                a1_4[0] + d[4][3] , 4
                );
        int a24_3[] = MinAndIndex(
                a4_2[0] + d[2][3] , 2 , 
                a2_4[0] + d[4][3] , 4
                );
        int a12_4[] = MinAndIndex(
                a2_1[0] + d[1][4] , 1 , 
                a1_2[0] + d[2][4] , 2
                );
        int a13_4[] = MinAndIndex(
                a3_1[0] + d[1][4] , 1 , 
                a1_3[0] + d[3][4] , 3
                );
        int a23_4[] = MinAndIndex(
                a3_2[0] + d[2][4] , 2 , 
                a2_3[0] + d[3][4] , 3
                );
        
        int a234_1[] = MinAndIndex(
                a34_2[0] + d[2][1] , 2 , 
                a24_3[0] + d[3][1] , 3 , 
                a23_4[0] + d[4][1] , 4
                );
        int a134_2[] = MinAndIndex(
                a34_1[0] + d[1][2] , 1 , 
                a14_3[0] + d[3][2] , 3 , 
                a13_4[0] + d[4][2] , 4
                );
        int a124_3[] = MinAndIndex(
                a24_1[0] + d[1][3] , 1 , 
                a14_2[0] + d[2][3] , 2 , 
                a12_4[0] + d[4][3] , 4
                );
        int a123_4[] = MinAndIndex(
                a23_1[0] + d[1][4] , 1 , 
                a13_2[0] + d[2][4] , 2 , 
                a12_3[0] + d[3][4] , 3
                );
        
        int a1234_0[] = MinAndIndex(
                a234_1[0] + d[1][0] , 1 , 
                a134_2[0] + d[2][0] , 2 , 
                a124_3[0] + d[3][0] , 3 , 
                a123_4[0] + d[4][0] , 4
                );
        
        System.out.println(a1234_0[0]);
        
        //Track()
        
        
        System.out.println("0");
        System.out.println(a1234_0[1]);
        
        if(a1234_0[1] == 1) {
            System.out.println(a234_1[1]);
            if(a234_1[1] == 2) {
                System.out.println(a34_2[1]);
                if(a34_2[1] == 3) {
                    System.out.println(a4_3[1]);
                }
                else {
                    System.out.println(a3_4[1]);
                }
            }
            else if(a234_1[1] == 3) {
                System.out.println(a24_3[1]);
                if(a24_3[1] == 2) {
                    System.out.println(a4_2[1]);
                }
                else {
                    System.out.println(a2_4[1]);
                }
            }
            else if(a234_1[1] == 4) {
                System.out.println(a23_4[1]);
                if(a23_4[1] == 2) {
                    System.out.println(a3_2[1]);
                }
                else {
                    System.out.println(a2_3[1]);
                }
            }
        }
        
        else if(a1234_0[1] == 2) {
            System.out.println(a134_2[1]);
            if(a134_2[1] == 1) {
                System.out.println(a34_1[1]);
                if(a34_1[1] == 3) {
                    System.out.println(a4_3[1]);
                }
                else {
                    System.out.println(a3_4[1]);
                }
            }
            else if(a134_2[1] == 3) {
                System.out.println(a14_3[1]);
                if(a14_3[1] == 1) {
                    System.out.println(a4_1[1]);
                }
                else {
                    System.out.println(a1_4[1]);
                }
            }
            else if(a134_2[1] == 4) {
                System.out.println(a13_4[1]);
                if(a13_4[1] == 1) {
                    System.out.println(a3_1[1]);
                }
                else {
                    System.out.println(a1_3[1]);
                }
            }
        }
        
        else if(a1234_0[1] == 3) {
            System.out.println(a124_3[1]);
            if(a124_3[1] == 1) {
                System.out.println(a24_1[1]);
                if(a24_1[1] == 2) {
                    System.out.println(a4_2[1]);
                }
                else {
                    System.out.println(a2_4[1]);
                }
            }
            else if(a124_3[1] == 2) {
                System.out.println(a14_2[1]);
                if(a14_2[1] == 1) {
                    System.out.println(a4_1[1]);
                }
                else {
                    System.out.println(a1_4[1]);
                }
            }
            else if(a124_3[1] == 4) {
                System.out.println(a12_4[1]);
                if(a12_4[1] == 1) {
                    System.out.println(a2_1[1]);
                }
                else {
                    System.out.println(a1_2[1]);
                }
            }
        }
        
        else if(a1234_0[1] == 4) {
            System.out.println(a123_4[1]);
            if(a123_4[1] == 1) {
                System.out.println(a23_1[1]);
                if(a23_1[1] == 2) {
                    System.out.println(a3_2[1]);
                }
                else {
                    System.out.println(a2_3[1]);
                }
            }
            else if(a123_4[1] == 2) {
                System.out.println(a13_2[1]);
                if(a13_2[1] == 1) {
                    System.out.println(a3_1[1]);
                }
                else {
                    System.out.println(a1_3[1]);
                }
            }
            else if(a123_4[1] == 3) {
                System.out.println(a12_3[1]);
                if(a12_3[1] == 1) {
                    System.out.println(a2_1[1]);
                }
                else {
                    System.out.println(a1_2[1]);
                }
            }
        }
        
    }
    
    public static void hka5(int a[]) {
        // 1. GenerateHKA2
        // 2. Copy S[] dari 5x.txt
        // 3. GenerateHKA3
        // 4. Copy x yang sesuai dari 5S.txt
        // 5. Masukkan ke NewMain3.java di tsp4
        int a0 = a[0];
        int a1 = a[1];
        int a2 = a[2];
        int a3 = a[3];
        int a4 = a[4];
        
        int a2_1[] = {d[a0][a2] + d[a2][a1] , 2};
        int a3_1[] = {d[a0][a3] + d[a3][a1] , 3};
        int a4_1[] = {d[a0][a4] + d[a4][a1] , 4};
        int a1_2[] = {d[a0][a1] + d[a1][a2] , 1};
        int a3_2[] = {d[a0][a3] + d[a3][a2] , 3};
        int a4_2[] = {d[a0][a4] + d[a4][a2] , 4};
        int a1_3[] = {d[a0][a1] + d[a1][a3] , 1};
        int a2_3[] = {d[a0][a2] + d[a2][a3] , 2};
        int a4_3[] = {d[a0][a4] + d[a4][a3] , 4};
        int a1_4[] = {d[a0][a1] + d[a1][a4] , 1};
        int a2_4[] = {d[a0][a2] + d[a2][a4] , 2};
        int a3_4[] = {d[a0][a3] + d[a3][a4] , 3};
        
        int a23_1[] = MinAndIndex(
                a3_2[0] + d[a2][a1] , 2 , 
                a2_3[0] + d[a3][a1] , 3
                );
        int a24_1[] = MinAndIndex(
                a4_2[0] + d[a2][a1] , 2 , 
                a2_4[0] + d[a4][a1] , 4
                );
        int a34_1[] = MinAndIndex(
                a4_3[0] + d[a3][a1] , 3 , 
                a3_4[0] + d[a4][a1] , 4
                );
        int a13_2[] = MinAndIndex(
                a3_1[0] + d[a1][a2] , 1 , 
                a1_3[0] + d[a3][a2] , 3
                );
        int a14_2[] = MinAndIndex(
                a4_1[0] + d[a1][a2] , 1 , 
                a1_4[0] + d[a4][a2] , 4
                );
        int a34_2[] = MinAndIndex(
                a4_3[0] + d[a3][a2] , 3 , 
                a3_4[0] + d[a4][a2] , 4
                );
        int a12_3[] = MinAndIndex(
                a2_1[0] + d[a1][a3] , 1 , 
                a1_2[0] + d[a2][a3] , 2
                );
        int a14_3[] = MinAndIndex(
                a4_1[0] + d[a1][a3] , 1 , 
                a1_4[0] + d[a4][a3] , 4
                );
        int a24_3[] = MinAndIndex(
                a4_2[0] + d[a2][a3] , 2 , 
                a2_4[0] + d[a4][a3] , 4
                );
        int a12_4[] = MinAndIndex(
                a2_1[0] + d[a1][a4] , 1 , 
                a1_2[0] + d[a2][a4] , 2
                );
        int a13_4[] = MinAndIndex(
                a3_1[0] + d[a1][a4] , 1 , 
                a1_3[0] + d[a3][a4] , 3
                );
        int a23_4[] = MinAndIndex(
                a3_2[0] + d[a2][a4] , 2 , 
                a2_3[0] + d[a3][a4] , 3
                );
        
        int a234_1[] = MinAndIndex(
                a34_2[0] + d[a2][a1] , 2 , 
                a24_3[0] + d[a3][a1] , 3 , 
                a23_4[0] + d[a4][a1] , 4
                );
        int a134_2[] = MinAndIndex(
                a34_1[0] + d[a1][a2] , 1 , 
                a14_3[0] + d[a3][a2] , 3 , 
                a13_4[0] + d[a4][a2] , 4
                );
        int a124_3[] = MinAndIndex(
                a24_1[0] + d[a1][a3] , 1 , 
                a14_2[0] + d[a2][a3] , 2 , 
                a12_4[0] + d[a4][a3] , 4
                );
        int a123_4[] = MinAndIndex(
                a23_1[0] + d[a1][a4] , 1 , 
                a13_2[0] + d[a2][a4] , 2 , 
                a12_3[0] + d[a3][a4] , 3
                );
        
        int a1234_0[] = MinAndIndex(
                a234_1[0] + d[a1][a0] , 1 , 
                a134_2[0] + d[a2][a0] , 2 , 
                a124_3[0] + d[a3][a0] , 3 , 
                a123_4[0] + d[a4][a0] , 4
                );
        
        System.out.println(a1234_0[0]);
        
        //Track()
        
        int b[] = new int[5];
        
        b[0] = 0;
        b[1] = a1234_0[1];
        
        System.out.println("0");
        System.out.println(a1234_0[1]);
        
        if(a1234_0[1] == 1) {
            b[2] = a234_1[1];
            System.out.println(a234_1[1]);
            if(a234_1[1] == 2) {
                b[3] = a34_2[1];
                System.out.println(a34_2[1]);
                if(a34_2[1] == 3) {
                    b[4] = a4_3[1];
                    System.out.println(a4_3[1]);
                }
                else {
                    b[4] = a3_4[1];
                    System.out.println(a3_4[1]);
                }
            }
            else if(a234_1[1] == 3) {
                b[3] = a24_3[1];
                System.out.println(a24_3[1]);
                if(a24_3[1] == 2) {
                    b[4] = a4_2[1];
                    System.out.println(a4_2[1]);
                }
                else {
                    b[4] = a2_4[1];
                    System.out.println(a2_4[1]);
                }
            }
            else if(a234_1[1] == 4) {
                b[3] = a23_4[1];
                System.out.println(a23_4[1]);
                if(a23_4[1] == 2) {
                    b[4] = a3_2[1];
                    System.out.println(a3_2[1]);
                }
                else {
                    b[4] = a2_3[1];
                    System.out.println(a2_3[1]);
                }
            }
        }
        
        else if(a1234_0[1] == 2) {
            b[2] = a134_2[1];
            System.out.println(a134_2[1]);
            if(a134_2[1] == 1) {
                b[3] = a34_1[1];
                System.out.println(a34_1[1]);
                if(a34_1[1] == 3) {
                    b[4] = a4_3[1];
                    System.out.println(a4_3[1]);
                }
                else {
                    b[4] = a3_4[1];
                    System.out.println(a3_4[1]);
                }
            }
            else if(a134_2[1] == 3) {
                b[3] = a14_3[1];
                System.out.println(a14_3[1]);
                if(a14_3[1] == 1) {
                    b[4] = a4_1[1];
                    System.out.println(a4_1[1]);
                }
                else {
                    b[4] = a1_4[1];
                    System.out.println(a1_4[1]);
                }
            }
            else if(a134_2[1] == 4) {
                b[3] = a13_4[1];
                System.out.println(a13_4[1]);
                if(a13_4[1] == 1) {
                    b[4] = a3_1[1];
                    System.out.println(a3_1[1]);
                }
                else {
                    b[4] = a1_3[1];
                    System.out.println(a1_3[1]);
                }
            }
        }
        
        else if(a1234_0[1] == 3) {
            b[2] = a124_3[1];
            System.out.println(a124_3[1]);
            if(a124_3[1] == 1) {
                b[3] = a24_1[1];
                System.out.println(a24_1[1]);
                if(a24_1[1] == 2) {
                    b[4] = a4_2[1];
                    System.out.println(a4_2[1]);
                }
                else {
                    b[4] = a2_4[1];
                    System.out.println(a2_4[1]);
                }
            }
            else if(a124_3[1] == 2) {
                b[3] = a14_2[1];
                System.out.println(a14_2[1]);
                if(a14_2[1] == 1) {
                    b[4] = a4_1[1];
                    System.out.println(a4_1[1]);
                }
                else {
                    b[4] = a1_4[1];
                    System.out.println(a1_4[1]);
                }
            }
            else if(a124_3[1] == 4) {
                b[3] = a12_4[1];
                System.out.println(a12_4[1]);
                if(a12_4[1] == 1) {
                    b[4] = a2_1[1];
                    System.out.println(a2_1[1]);
                }
                else {
                    b[4] = a1_2[1];
                    System.out.println(a1_2[1]);
                }
            }
        }
        
        else if(a1234_0[1] == 4) {
            b[2] = a123_4[1];
            System.out.println(a123_4[1]);
            if(a123_4[1] == 1) {
                b[3] = a23_1[1];
                System.out.println(a23_1[1]);
                if(a23_1[1] == 2) {
                    b[4] = a3_2[1];
                    System.out.println(a3_2[1]);
                }
                else {
                    b[4] = a2_3[1];
                    System.out.println(a2_3[1]);
                }
            }
            else if(a123_4[1] == 2) {
                b[3] = a13_2[1];
                System.out.println(a13_2[1]);
                if(a13_2[1] == 1) {
                    b[4] = a3_1[1];
                    System.out.println(a3_1[1]);
                }
                else {
                    b[4] = a1_3[1];
                    System.out.println(a1_3[1]);
                }
            }
            else if(a123_4[1] == 3) {
                b[3] = a12_3[1];
                System.out.println(a12_3[1]);
                if(a12_3[1] == 1) {
                    b[4] = a2_1[1];
                    System.out.println(a2_1[1]);
                }
                else {
                    b[4] = a1_2[1];
                    System.out.println(a1_2[1]);
                }
            }
        }
        
        int c[] = new int[5];
        for(int i = 0; i < 5; i++) {
            c[i] = a[b[i]];
        }
        
        Print(c);
        
    }
    
    
    public static int[] HeldKarpAlgorithm4(int a[]) {
        int a21[] = {d[a[0]][a[2]] + d[a[2]][a[1]], 2};
        int a31[] = {d[a[0]][a[3]] + d[a[3]][a[1]], 3};
        int a12[] = {d[a[0]][a[1]] + d[a[1]][a[2]], 1};
        int a32[] = {d[a[0]][a[3]] + d[a[3]][a[2]], 3};
        int a13[] = {d[a[0]][a[1]] + d[a[1]][a[3]], 1};
        int a23[] = {d[a[0]][a[2]] + d[a[2]][a[3]], 2};
        // dari 0, kemudian melewati {2,3}, kemudian ke 1
        int a231[] = MinAndIndex(a32[0] + d[a[2]][a[1]], a23[0] + d[a[3]][a[1]], 2, 3);
        int a132[] = MinAndIndex(a31[0] + d[a[1]][a[2]], a13[0] + d[a[3]][a[2]], 1, 3);
        int a123[] = MinAndIndex(a12[0] + d[a[2]][a[3]], a21[0] + d[a[1]][a[3]], 2, 1);
        int a1230[] = MinAndIndex(a231[0] + d[a[1]][a[0]], a132[0] + d[a[2]][a[0]], a123[0] + d[a[3]][a[0]], 1, 2, 3);
        
        int b[] = new int[4];
        
        //System.out.print("0");
        b[0] = 0;
        //System.out.print(a1230[1]);
        b[1] = a1230[1];
        if(a1230[1] == 1) {
            //System.out.print(a231[1]);
            b[2] = a231[1];
            if(a231[1] == 2) {
                //System.out.println(a32[1]);
                b[3] = a32[1];
            }
            else {
                //System.out.println(a23[1]);
                b[3] = a23[1];
            }
        }
        else if(a1230[1] == 2) {
            //System.out.print(a132[1]);
            b[2] = a132[1];
            if(a132[1] == 1) {
                //System.out.println(a31[1]);
                b[3] = a31[1];
            }
            else {
                //System.out.println(a13[1]);
                b[3] = a13[1];
            }
        }
        else {
            //System.out.print(a123[1]);
            b[2] = a123[1];
            if(a123[1] == 1) {
                //System.out.println(a21[1]);
                b[3] = a21[1];
            }
            else {
                //System.out.println(a12[1]);
                b[3] = a12[1];
            }
        }
        
        int c[] = new int[4];
        for(int i = 0; i < 4; i++) {
            c[i] = a[b[i]];
        }
        
        return c;
    }
    
    public static int[] MinAndIndex(int a, int pos1, int b, int pos2) {
        int c[] = new int[2];
        if(a <= b) {
            c[0] = a;
            c[1] = pos1;
        }
        else {
            c[0] = b;
            c[1] = pos2;
        }
        return c;
    }
    
    public static int[] MinAndIndex(int a, int pos1, int b, int pos2, int c, int pos3) {
        int z[] = new int[2];
        if(a <= b && a <= c) {
            z[0] = a;
            z[1] = pos1;
        }
        else if(b <= a && b <= c){
            z[0] = b;
            z[1] = pos2;
        }
        else {
            z[0] = c;
            z[1] = pos3;
        }
        return z;
    }
    
    public static int[] MinAndIndex(int a, int pos1, int b, int pos2, int c, int pos3, int d, int pos4) {
        int z[] = new int[2];
        if(a <= b && a <= c && a <= d) {
            z[0] = a;
            z[1] = pos1;
        }
        else if(b <= a && b <= c && b < d){
            z[0] = b;
            z[1] = pos2;
        }
        else if(c <= a && c <= b && c <= d) {
            z[0] = c;
            z[1] = pos3;
        }
        else {
            z[0] = d;
            z[1] = pos4;
        }
        return z;
    }
    
    public static int Min(int a, int b) {
        if(a < b) {
            return a;
        }
        else {
            return b;
        }
    }
    
    public static int Min(int a, int b, int c) {
        return Min(a, Min(b,c));
    }
    
    // mulai dari 0, berakhir di x, melalui S
    public static double HeldKarpAlgorithm(int a, int[] S, String hasil) {
        if(S.length == 0) {
            double result = d[a][0];
            //System.out.println(a);
            //System.out.println(hasil);
            return result;
        }
        else {
            double[] c = new double[S.length];
            for(int i = 0; i < S.length; i++) {
                int e = S[i];
                c[i] = d[a][e];
                int[] g = new int[S.length - 1];
                int index = 0;
                for(int j = 0; j < S.length; j++) {
                    if(j != i) {
                        g[index] = S[j];
                        index++;
                    }
                }
                c[i] += HeldKarpAlgorithm(e, g, hasil+e);
            }
            
            //System.out.println(S[IndexOfMin(c)]);
            return Min(c);
        }
    }
    
    public static int IndexOfMin(double[] a) {
        int index = 0;
        double min = a[0];
        for(int i = 1; i < a.length; i++) {
            if(min > a[i]) {
                min = a[i];
                index = i;
            }
        }
        return index;
    }
    
    public static int Min(int[] a) {
        int min = a[0];
        for(int i = 1; i < a.length; i++) {
            if(min > a[i]) {
                min = a[i];
            }
        }
        return min;
    }
    
    public static int Max(int[] a) {
        int max = a[0];
        for(int i = 1; i < a.length; i++) {
            if(max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }
    
    public static double Min(double[] a) {
        double min = a[0];
        for(int i = 1; i < a.length; i++) {
            if(min > a[i]) {
                min = a[i];
            }
        }
        return min;
    }
    
    public static void PrintN() {
        System.out.println(n + "\n");
    }
    
    public static void PrintDistanceMatrix() {
        for(int i = 0; i < d.length; i++) {
            for(int j = 0; j < d[0].length; j++) {
                System.out.print(d[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static void Print(int a[]) {
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
}
