package AHKT;

public class MainCoba {

    public static void main(String[] args) throws Exception{
        int x[] = {0, 10, 20, 20, 10, 0};
        int y[] = {0, 0, 0, 10, 10, 10};
        Library.LoadCoordinate(x, y);
        
        int result[] = {0,1,2,5,4,3};
        Library.SetResult1(result);
        
        new PanelCoordinate();
        new PanelCoordinateAndResult1();
    }
}