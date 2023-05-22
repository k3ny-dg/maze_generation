import generation.DisjointSets;

public class DisjointsSetsTest
{
    public static void main(String[] args) {

        DisjointSets sets = new DisjointSets(20);

//        sets.union(5, 3);
//        sets.union(7, 3);
//        sets.union(8, 9);
//        sets.union(1, 15);
//        sets.union(8, 2);
//        sets.union(6, 5);
//        sets.union(3, 2);
//        sets.union(7, 6);

        System.out.println(sets.find(0) == sets.find(5)); // false
//        System.out.println(sets.find(3) == sets.find(4)); // false
//        System.out.println(sets.find(8) == sets.find(9)); // true
//        System.out.println(sets.find(3) == sets.find(2)); // true
//        System.out.println(sets.find(7) == sets.find(3)); // true
//        System.out.println(sets.find(2) == sets.find(7)); // true
//        System.out.println(sets.find(5) == sets.find(15)); // false

//        System.out.println("Is this in the same set? " + sets.sameSet(1, 15)); // true
//        System.out.println("Is this in the same set? " + sets.sameSet(6, 15)); // false
//        System.out.println("Is this in the same set? " + sets.sameSet(8, 9)); // true


        System.out.println("--------------------------");
        System.out.println(sets.find(0) == sets.find(4));

    }
}
