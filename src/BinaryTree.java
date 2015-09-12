import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

/**
 * Created by daleappleby on 10/09/15.
 *
 * A binary tree with operations to addElements,traverse the tree via a queue
 * return the size of the binary tree
 *
 * This class is not intended for use by multiple threads.
 * @param <T>          the type parameter
 */
public class BinaryTree<T> {
    private Node<T> root = null;
    private int count = 0;
    private Comparator<T> comparator;

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
     * Binary search
     *
     * @param item the item
     * @return the boolean
     */
    public boolean binarySearch(T item){
        for(Node<T> tempNode = root; tempNode!=null;tempNode = comparator.compare( item, tempNode.getData() ) == -1? tempNode.getN1() :tempNode.getN2()){
            if(tempNode == null)
                break;
            if(comparator.compare( item ,tempNode.getData() ) == 0){
                return true;
            }
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

    /**
     * Apply over tree and reorder.
     *
     * @param treeFunc the tree func
     */
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
     * Parallel is balanced. Determines is the tree is balanced using fork-join.
     *
     * @return the boolean
     */
    public boolean parallelIsBalanced(){
        if(root == null)
            return false;

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return  (boolean) forkJoinPool.invoke(new ParallelIsBalanced(root));
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
     * @param node the node
     * @return an array made of all elements in the tree
     */
    public int getHeight(Node<?> node) {

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

    private class ParallelIsBalanced extends RecursiveTask{
        private Node<?> nodeToProcess;
        private int height;
        /**
         * The Is.
         */
        boolean is = false;

        /**
         * Instantiates a new Parrallel is balanced.
         *
         * @param node the node
         */
        public ParallelIsBalanced(Node<?> node) {
            nodeToProcess = node;
        }

        private ParallelIsBalanced(Node<?> node,boolean is) {
            nodeToProcess = node;
            this.is = is;
        }

        @Override
        protected Object compute() {
            if(is == false) {
                List<ParallelIsBalanced> col = new ArrayList<>();
                col.add(new ParallelIsBalanced(nodeToProcess.getN1(), true));
                col.add(new ParallelIsBalanced(nodeToProcess.getN2(), true));
                Collection<T> ret = invokeAll((Collection)col);
                return Math.abs(col.get(0).height - col.get(1).height)<= 1;
            }
                this.height = getHeight(nodeToProcess);
                return height;
        }
    }
}
