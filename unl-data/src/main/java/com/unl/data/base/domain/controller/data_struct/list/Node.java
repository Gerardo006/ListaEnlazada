package com.unl.data.base.domain.controller.data_struct.list;


//K, v --key, v --value
//E --colecciones
//T --tipo generico
/*public class Node<E> {
    private E data;
    private Node <E> next;

    public Node(E data) {
        this.data = data;
        this.next = next;
    }


    public Node() {
        this.data = null;
        this.next = null;
    }


}*/

public class Node<E> {
    private E data;
    private Node<E> next;

    public Node(E data) {
        this.data = data;
        this.next = null; 
    }

    public Node(E data, Node<E> next) {
        this.data = data;
        this.next = next;
    }

    public Node() {
        this.data = null;
        this.next = null;
    }

    
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
}
