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


        Random r = new Random(System.currentTimeMillis());
        for(int i = 0; i < 10000; i++){
            bt.addElement(r.nextInt(10000));
        }


        System.out.println("Is this tree balanced?: ");
        System.out.println(bt.isBalanced());
        System.out.println();

        System.out.println("find out if this tree is balanced using fork-join parallelism (splits the left tree and right tree and combines results)");
        System.out.println(bt.parrallelIsBalanced());
        System.out.println();

        System.out.println("The height of this tree is..");
        System.out.println(bt.getHeight());

        System.out.println("Printing out all elements via tail recursion");
        //Print the elements via tail recursion.
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();

        //Search for an item in the tree using the comparator
        System.out.println("searching binary tree for 7, found? :");
        System.out.println(bt.binarySearch(7)); //true for the set of integers currently in the tree


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

        System.out.println("Setting all elements back to normal:");
        bt.applyOverTree(e->e/10,t->t%2==0);
        //Print the elements
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();

        System.out.println("Now multiplying all elements by 10, but reordering the whole tree at the same time");
        bt.applyOverTreeAndReorder(e->e*10);
        //Print the elements
        System.out.println("The new tree is: ");
        bt.traverseBinaryTreeTailRecursion();
        System.out.println();


        System.out.println();
        System.out.println("Create a copy of the tree structure without the container");
        Node<Integer> b = bt.getRootNode();
        System.out.println("Set the copies root element to 90");
        b.setData(90);
        System.out.println("Check that the root element is 90, root element = ");
        System.out.println(b.getData());
        System.out.println("Now check the binary trees root element to see if it changed:");
        System.out.println(bt.getRootElement());
        //This assertation should hold
        assert b.getData() != bt.getRootElement();
    }
}
