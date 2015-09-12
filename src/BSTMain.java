import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by daleappleby on 10/09/15.
 */
public class BSTMain {


    public static void main(String[] args) {
        //Create a comparator for ordering the elements
        Comparator<Integer> comp = (i,v) -> i<v?-1:i>v?1:0;

        //Create a binary tree to hold Integers
        BinaryTree<Integer> bt = new BinaryTree<>(comp);


        //Create a balanced binary tree
        bt.addElement(50);
        bt.addElement(17);
        bt.addElement(12);
        bt.addElement(23);
        bt.addElement(9);
        bt.addElement(14);
        bt.addElement(19);
        bt.addElement(72);
        bt.addElement(54);
        bt.addElement(76);
        bt.addElement(67);

        System.out.println("Printing out all elements via tail recursion");
        //Print the elements via tail recursion.
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();

        //Search for an item in the tree using the comparator
        System.out.println("searching binary tree for 7, found? :");
        System.out.println(bt.binarySearchNonGlobal(7)); //true for the set of integers currently in the tree

        System.out.println("searching binary tree for 5, found?: ");
        //Search for an item in the tree using a global node (not thread safe)
        System.out.println(bt.binarySearchFor(5)); //false for the set of numbers currently in the tree

        System.out.println("Printing number of elements in the binary tree:");
        //Returns the number of nodes in the tree
        System.out.println(bt.getCount());
        System.out.println();

        System.out.println("Printing out all elements in the tree via a queue");
        //Print the elements in the tree via queue
        bt.traverseTreeViaQueue();
        System.out.println();

        System.out.println("Turning tree into an array and printing each element");
        //Turn the binary tree into an array of integers
        Integer[] myInt = bt.binaryTreeToArray(new Integer[bt.getCount()]);

        //Print each element.
        Stream.of(myInt).forEach(e-> System.out.println(e));
        System.out.println();

        System.out.println("Turning tree into an array list and printing each element");
        //Turn the binary tree into an array list.
        ArrayList<Integer> ar = bt.binaryTreeToArrayList();
        ar.stream().forEach(e-> System.out.println(e));
        System.out.println();

        System.out.println("Applying a function over the tree to multiple each element by two");
        //Multiply every data item in the tree by two
        bt.applyOverTree(e-> e*2);

        System.out.println("Output after multplying ever item by two:");
        //Print the elements
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();

        System.out.println("Raising each element to the second power");
        bt.applyOverTree(e-> (int) Math.pow(e,2));
        //Print the elements
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();

        System.out.println("Setting elements back to normal sqrt(e)/2");
        bt.applyOverTree(e-> (int)(Math.sqrt(e)/2));
        //Print the elements
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();

        System.out.println("Multiplying all even nodes in the tree by 10 (via two callback functions)");
        bt.applyOverTree(e->e*10,t->t%2==0);
        //Print the elements
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();

        System.out.println("Is this tree balanced?: ");
        System.out.println(bt.isBalanced());
        System.out.println();
        System.out.println("The height of this tree is..");
        System.out.println(bt.getHeight());
    }
}
