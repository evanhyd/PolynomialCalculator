import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        ArrayList<Double> coefs = new ArrayList<>();
        int degree = input.nextInt();
        while(degree-- >= 0)
        {
            coefs.add(input.nextDouble());
        }

        Polynomial poly = new Polynomial(coefs);
        System.out.println(poly.getDerivative().toString());
        System.out.println(poly.evaluate(3.0));
        poly.drawGraph(400, -20.0, 20.0, 0.01);
    }
}
