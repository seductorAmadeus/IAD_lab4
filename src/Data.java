import java.util.ArrayList;

public class Data
{
    private static ArrayList arr = new ArrayList();
    public static double[] X = {-3,0,-2,5,2,0,4,-5,3};
    public static double[] Y = {3,-3,-1,-4,1,0,-1,-4,3};

    @SuppressWarnings("unchecked")
    public static void Add()
    {
        for(int i = 0;i<9;i++)
        {
            Ponto newp = new Ponto();
            newp.setX(X[i]);
            newp.setY(Y[i]);
            arr.add(newp);
        }
    }
}
