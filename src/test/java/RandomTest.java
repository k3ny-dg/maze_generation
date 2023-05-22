import java.util.Random;

public class RandomTest
{

    public static void main(String[] args)
    {
        Random rand = new Random();

        int upperBound = 3;

        System.out.println(rand.nextInt(upperBound));
        System.out.println(rand.nextInt(upperBound));
        System.out.println(rand.nextInt(upperBound));
        System.out.println(rand.nextInt(upperBound));
    }

}
