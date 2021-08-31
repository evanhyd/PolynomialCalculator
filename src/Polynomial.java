import java.util.ArrayList;

public class Polynomial
{
    private ArrayList<Double> equation;

    //constructors
    public Polynomial()
    {
        this.equation = new ArrayList<>();
    }

    public Polynomial(ArrayList<Double> newEquation)
    {
        this.equation = new ArrayList<>();

        for(int degree = newEquation.size()-1; degree >= 0; --degree)
        {
            this.equation.add(newEquation.get(degree));
        }

        this.shrinkEquation();
    }

    public Polynomial(Polynomial newPolynomial)
    {
        this.equation = new ArrayList<>(newPolynomial.equation);
    }

    //accessor
    public double get(int degree)
    {
        if(degree <= this.getDegree()) return this.equation.get(degree);
        else return 0.0;
    }

    //mutator
    public void set(int degree, double newCoefficient)
    {
        while(this.getDegree() < degree)
        {
            this.equation.add(0.0);
        }
        this.equation.set(degree, newCoefficient);
        this.shrinkEquation();
    }

    //polynomial functions
    public int getDegree()
    {
        return this.equation.size()-1;
    }

    public double evaluate(double x)
    {
        double y = 0.0;
        double base = 1.0;

        for(double coefficient : this.equation)
        {
            y += coefficient * base;
            base *= x;
        }

        return y;
    }

    public Polynomial getDerivative()
    {
        Polynomial derivative = new Polynomial();
        for(int degree = 1; degree <= this.getDegree(); ++degree)
        {
            //a*x^n -> a*n*x^(n-1)
            derivative.set(degree-1, this.get(degree) * degree);
        }

        derivative.shrinkEquation();
        return derivative;
    }


    //polynomial attributes
    public boolean isEven()
    {
        for(int degree = 0; degree <= this.getDegree(); ++degree)
        {
            //all non zero terms must have even degree exponents
            if(degree % 2 == 1 && this.get(degree) != 0.0) return false;
        }

        return true;
    }

    public boolean isOdd()
    {
        for(int degree = 0; degree <= this.getDegree(); ++degree)
        {
            //all non zero terms must have odd degree exponents
            if(degree % 2 == 0 && this.get(degree) != 0.0) return false;
        }

        return true;
    }

    //arithmetic operators
    public Polynomial add(Polynomial other)
    {
        Polynomial sum = new Polynomial(this);

        for(int degree = 0; degree <= other.getDegree(); ++degree)
        {
            sum.set(degree, sum.get(degree) + other.get(degree));
        }

        sum.shrinkEquation();
        return sum;
    }

    public Polynomial subtract(Polynomial other)
    {
        Polynomial diff = new Polynomial(this);

        for(int degree = 0; degree <= other.getDegree(); ++degree)
        {
            diff.set(degree, diff.get(degree) - other.get(degree));
        }

        diff.shrinkEquation();
        return diff;
    }

    public boolean equals(Polynomial other)
    {
        return this.equation.equals(other.equation);
    }

    //miscellaneous
    public String toString()
    {
        String str = "";

        for(int degree = this.getDegree(); degree >= 0; --degree)
        {
            double num = this.get(degree);

            //remove 0 unless this is the only term
            if(num == 0.0 && this.getDegree() != 0) continue;

            //remove leading "+"
            if(num > 0.0 && degree != this.getDegree()) str += "+";

            //remove "1" except the constant term
            if(num == -1.0 && degree != 0) str += "_";
            else if(num == 1.0 && degree != 0) {}
            else str += num;

            //remove "x^" in the constant term
            if(degree == 0) break;
            else str += "x";

            if(degree > 1) str += "^" + degree;
        }

        return str;
    }

    private void shrinkEquation()
    {
        //always keep the constant 0
        for(int degree = this.getDegree(); degree >= 1; --degree)
        {
            if(equation.get(degree) == 0.0) equation.remove(degree);
            else break;
        }
    }

    public void drawGraph(int axis, double domainLeft, double domainRight, double precision)
    {
        double ratio = (domainRight - domainLeft) / axis;
        int midline = axis / 2;

        char[][] graph = new char[axis][axis];

        for(int x = 0; x < axis; ++x)
        {
            for(int y = 0; y < axis; ++y)
            {
                graph[x][y] = ' ';
            }
        }

        for(int column = 0; column < axis; ++column)
        {
            graph[midline][column] = '-';
        }

        for(int row = 0; row < axis; ++row)
        {
            graph[row][midline] = '|';
        }

        for(double domain = domainLeft; domain <= domainRight; domain += precision)
        {
            double range = this.evaluate(domain);
            int x = (int)(domain / ratio) + midline;
            int y = (int)(range / ratio) + midline;

            if(y >= 0 && y < axis && x >= 0 && x < axis) graph[y][x] = '*';
        }

        for(int row = axis-1; row >= 0; --row)
        {
            for(int column = 0; column < axis; ++column)
            {
                System.out.print(graph[row][column]);
            }
            System.out.println("");
        }
    }

    public static Polynomial add(Polynomial poly1, Polynomial poly2)
    {
        return poly1.add(poly2);
    }

    public static Polynomial subtract(Polynomial poly1, Polynomial poly2)
    {
        return poly1.subtract(poly2);
    }
}
