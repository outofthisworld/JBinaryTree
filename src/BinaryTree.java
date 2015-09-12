import apple.laf.JRSUIUtils;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by daleappleby on 10/09/15.
 *
 * A binary tree with operations to addElements,traverse the tree via a queue
 * return the size of the binary tree
 *
 * This class is not intended for use by multiple threads.
 * @param <T>         the type parameter
 */
public class BinaryTree<T> {
    private Node<T> root = null;
    private int count = 0;
    private Comparator<T> comparator;
    private Node<T> bstTempNode;

    /**
     * Instantiates a new Binary tree.
     *
     * @param comparator the comparator used to place elements in the designated position in the binary tree.
     */
    public BinaryTree(Comparator<T> comparator){
        this.comparator = comparator;
    }

    /**
     * Adds an element to the binary tree.
     *
     * @param element the element to add to the tree.
     *                To successfully add an element to the tree the element must satisfy the following conditions:
     *                1) element != null; otherwise an error is thrown.
     *                2) comparator.compare(element,node) != 0. Otherwise, the element is a duplicate value and will be ignored.
     * @return a boolean value indicating whether or not the item was successfully added to the tree. This is always true unless
     * the item is a duplicate value as determined by the @param comparator passed in via the binary trees constructor.
     */
    public boolean addElement(T element) {
        Objects.requireNonNull(element);
        if (root == null) {
            root = new Node();
            root.setData(element);
        } else {
            Node<T> dummyNode = root;
            Node<T> newNode = new Node<>();
            newNode.setData(element);
            while (true) {
                if (comparator.compare(element, dummyNode.getData()) == 0) {
                    return false; //Duplicate element do not insert
                } else if (comparator.compare(element, dummyNode.getData()) == -1) {
                    if (dummyNode.getN1() == null) {
                        dummyNode.setN1(newNode);
                        break;
                    }
                    dummyNode = dummyNode.getN1();
                } else {
                    if (dummyNode.getN2() == null) {
                        dummyNode.setN2(newNode);
                        break;
                    }
                    dummyNode = dummyNode.getN2();
                }
            }
        }
        count++;
        return true;
    }


    /**
     * Binary search non global.
     *
     * @param item the item
     * @return the boolean
     */
    public boolean binarySearchNonGlobal(T item){
        Node<T> tempNode = root;
        while(true){
            if(comparator.compare(item,tempNode.getData()) == 0){
                return true;
            }else if(comparator.compare(item,tempNode.getData()) == -1){
                tempNode = tempNode.getN1();
            }else{
                tempNode = tempNode.getN2();
            }
            if(tempNode == null)
                break;
        }
        return false;
    }

    /**
     * Binary search for.
     *
     * @param item the item
     * @return the boolean
     */
    public boolean binarySearchFor(T item){
        bstTempNode = root;
        Boolean nodeFound;
        while((nodeFound = binarySearch(item))!=null){
            if(nodeFound == true) {
                bstTempNode = null;
                return true;
            }
        }
        bstTempNode = null;
        return false;
    }

    private Boolean binarySearch(T item){
       if(bstTempNode == null)
           return null;

        if(comparator.compare(item,bstTempNode.getData()) == 0) {
            return true;
        }else if(comparator.compare(item,bstTempNode.getData()) == 1){
            bstTempNode =  bstTempNode.getN2();
        }else{
            bstTempNode =  bstTempNode.getN1();
        }
        return false;
    }

    /**
     * Traverse tree via queue.
     */
    public void traverseTreeViaQueue(){
        if(root == null)
            return;

        LinkedList<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while(queue.size() > 0){
            System.out.println(queue.peek().getData());

            if(queue.peek().getN1() != null)
                queue.add(queue.peek().getN1());

            if(queue.peek().getN2() != null)
                queue.add(queue.peek().getN2());

            queue.remove();
        }
    }

    public void applyOverTreeAndReorder(TreeFunc<T> treeFunc) {
        if (root == null)
            return;

        ArrayList<T> ar = binaryTreeToArrayList();
        ar.sort(comparator);
        root=null;
        ar.stream().forEach(e->addElement(treeFunc.applyOver(e)));
    }

    /**
     * Apply the specified function over all items in the tree.
     *
     * @param treeFunc the tree func
     */
    public void applyOverTree(TreeFunc<T> treeFunc){
        if(root == null)
            return;

        LinkedList<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while(queue.size() > 0){
            queue.peek().setData(treeFunc.applyOver(queue.peek().getData()));

            if(queue.peek().getN1() != null)
                queue.add(queue.peek().getN1());

            if(queue.peek().getN2() != null)
                queue.add(queue.peek().getN2());

            queue.remove();
        }
    }

    /**
     * Apply over tree.
     *
     * @param treeFunc the tree func
     * @param pred the pred
     */
    public void applyOverTree(TreeFunc<T> treeFunc,Predicate<T> pred){
        if(root == null)
            return;

        LinkedList<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while(queue.size() > 0){
            if(pred.test(queue.peek().getData()))
                queue.peek().setData(treeFunc.applyOver(queue.peek().getData()));

            if(queue.peek().getN1() != null)
                queue.add(queue.peek().getN1());

            if(queue.peek().getN2() != null)
                queue.add(queue.peek().getN2());

            queue.remove();
        }
    }

    /**
     * Get the number of elements in the binary tree.
     *
     * @return the int
     */
    public int getCount(){
        return count;
    }


    /**
     * Get the root element of the binary tree.
     *
     * @return the root element
     */
    public T getRootElement(){
        return root.getData();
    }

    /**
     * Traverse binary tree tail recursion.
     */
    public void traverseBinaryTreeTailRecursion(){
        ArrayList<T> ar = new ArrayList<>();
        ar = traverseTree(ar,root);
        ar.stream().forEach(e-> {
                    if(e!=null)
            System.out.println(e);
        }
        );
    }

    /**
     * Binary tree to array list.
     *
     * @return the array list
     */
    public ArrayList<T> binaryTreeToArrayList(){
        ArrayList<T> ar = new ArrayList<>();
        ar = traverseTree(ar,root);
        return ar;
    }

    /**
     * Binary tree to array.
     *
     * @param arr the arr
     * @return the t [ ]
     */
    public T[] binaryTreeToArray(T[] arr){
        if(arr.length < count)
            return null;

        traverseTree(arr, root, 0);
        return arr;
    }

    /**
     * Traverse the tree using tail recursion
     *
     * @return an array made of all elements in the tree
     */
    private void traverseTree(T[] myArray,Node<T> node,int count){
        if(node == null) {
            return;
        }else{
            myArray[count] = node.getData();
        }

        if(node.getN1() != null)
            traverseTree(myArray,node.getN1(),++count);

        if(node.getN2() != null)
            traverseTree(myArray,node.getN2(),++count);
    }

    /**
     * Is balanced.
     *
     * @return the boolean
     */
    public boolean isBalanced(){
        if(root == null)
            return false;

        return Math.abs((getHeight(root.getN1()) - getHeight(root.getN2())))<= 1;
    }

    /**
     * Get height.
     *
     * @return the int
     */
    public int getHeight(){
        return getHeight(root);
    }

    /**
     * Traverse the tree using tail recursion
     *
     * @return an array made of all elements in the tree
     */
    private int getHeight(Node<T> node) {

        if (node == null)
        {
            return 0;
        }
        else
        {
            return 1 +
                    Math.max(getHeight(node.getN1()),
                            getHeight(node.getN2()));
        }
    }

    /**
     * Traverse the tree using tail recursion
     *
     * @return arrayList made all elements in the tree
     */
    private ArrayList<T> traverseTree(ArrayList<T> ar, Node<T> node){
            if (node == null) {
                return ar;
            } else {
                ar.add(node.getData());
            }

            if (node.getN1() != null)
                traverseTree(ar, node.getN1());

            if (node.getN2() != null)
                traverseTree(ar, node.getN2());

            return ar;
    }

    /**
     * Get a clone of the root node and all subsequent nodes.
     *
     * @return a clone of the node
     */
    public Node<T> getRootNode(){
        Node<T> clone = null;
        try {
            clone = (Node<T>) root.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

}
