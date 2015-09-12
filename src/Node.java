/**
 * Created by daleappleby on 24/08/15.
 */
public class Node<T> {
    private Node<T> n1 = null;
    private Node<T> n2 = null;
    private T data;
    public Node<T> getN1(){return n1;}
    public void setN1(Node<T> node){this.n1 = node;}
    public void setN2(Node<T> node){this.n2 = node;}
    public Node<T> getN2(){return n2;}
    public T getData(){return this.data;}
    public void setData(T data){this.data = data;}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Node<T> node = new Node<>();
        node.setData(this.data);
        if(this.n1 != null)
        node.setN1((Node<T>)this.n1.clone());
        if(this.n2 != null)
        node.setN2((Node<T>)this.n2.clone());
        return node;
    }
}
