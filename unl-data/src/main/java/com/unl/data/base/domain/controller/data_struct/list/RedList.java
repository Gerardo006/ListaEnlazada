package com.unl.data.base.domain.controller.data_struct.list;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedList<E> {
    private Node<E> head;
    private Node<E> last;
    private Integer length;
    private List<E> repeatedElements;
    private Map<E, Integer> elementCount;
    private List<E> allElements;

    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data) {
            this(data, null);
        }

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    public RedList() {
        head = null;
        last = null;
        length = 0;
        repeatedElements = new ArrayList<>();
        elementCount = new HashMap<>();
        allElements = new ArrayList<>();
    }

    public Boolean isEmpty() {
        return head == null;
    }

    private Node<E> getNode(Integer pos) {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");
        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Index out of range");
        } else {
            Node<E> search = head;
            Integer cont = 0;
            while (cont < pos) {
                search = search.getNext();
                cont++;
            }
            return search;
        }
    }

    private void addFirst(E data) {
        Node<E> aux = new Node<>(data);
        if (isEmpty()) {
            head = aux;
            last = aux;
        } else {
            aux.setNext(head);
            head = aux;
        }
        updateElementCount(data);
        length++;
    }

    public void add(E data, Integer pos) throws Exception {
        if (pos < 0 || pos > length) {
            throw new ArrayIndexOutOfBoundsException("Index out of range");
        }

        if (pos == 0) {
            addFirst(data);
        } else if (pos == length) {
            addLast(data);
        } else {
            Node<E> previous = getNode(pos - 1);
            Node<E> newNode = new Node<>(data, previous.getNext());
            previous.setNext(newNode);
            updateElementCount(data);
            length++;
        }
    }

    public void add(E data) {
        addLast(data);
    }

    private void addLast(E data) {
        if (isEmpty()) {
            addFirst(data);
        } else {
            Node<E> aux = new Node<>(data);
            last.setNext(aux);
            last = aux;
            updateElementCount(data);
            length++;
        }
    }

    private void updateElementCount(E data) {
        allElements.add(data);
        
        int count = elementCount.getOrDefault(data, 0) + 1;
        elementCount.put(data, count);
        
        
        if (count == 2) {
            repeatedElements.add(data);
        }
    }

    public String print() {
        if (isEmpty()) {
            return "La lista está vacía.";
        } else {
            StringBuilder resp = new StringBuilder();
            Node<E> help = head;
            while (help != null) {
                resp.append(help.getData()).append(" -> ");
                help = help.getNext();
            }
            resp.append("null");
            return resp.toString();
        }
    }

    public String printRepeatedElements() {
        if (repeatedElements.isEmpty()) {
            return "No hay elementos repetidos.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Elementos repetidos (").append(repeatedElements.size()).append("):\n");
            for (E element : repeatedElements) {
                sb.append(element).append(" (")
                  .append(elementCount.get(element)).append(" veces)\n");
            }
            return sb.toString();
        }
    }

    
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        if (isEmpty()) {
            return null;
        }
        
        
        E[] array = (E[]) new Object[length];
        Node<E> current = head;
        for (int i = 0; i < length; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    
    @SuppressWarnings("unchecked")
    public E[][] toMatrix(int rows, int cols) {
        if (isEmpty() || rows * cols != length) {
            throw new IllegalArgumentException("Las dimensiones no coinciden con el tamaño de la lista");
        }
        
       
        E[][] matrix = (E[][]) new Object[rows][cols];
        Node<E> current = head;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = current.getData();
                current = current.getNext();
            }
        }
        return matrix;
    }

   
   public void readFromFile(String filename) {
    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)))) {
        String line;
        while ((line = br.readLine()) != null) {
            add((E) line.trim());
        }
    } catch (Exception e) {
        System.err.println("Error al leer el archivo desde recursos: " + e.getMessage());
    }
}

    
    public List<E> getAllElements() {
        return new ArrayList<>(allElements);
    }

    
    public Map<E, Integer> getElementCount() {
        return new HashMap<>(elementCount);
    }

    
    public List<E> getRepeatedElements() {
        return new ArrayList<>(repeatedElements);
    }

    
    public int getElementFrequency(E element) {
        return elementCount.getOrDefault(element, 0);
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // Tiempo inicial
    
        RedList<String> list = new RedList<>();
    
        list.readFromFile("data.txt");
    

        System.out.println("Total de elementos leídos: " + list.length);
        System.out.println("Elementos únicos: " + (list.length - list.repeatedElements.size()));
        System.out.println("Elementos duplicados: " + list.repeatedElements.size());
    
        System.out.println("\n Algunos elementos duplicados:");
        int limit = Math.min(20, list.repeatedElements.size());
        for (int i = 0; i < limit; i++) {
            String element = list.repeatedElements.get(i);
            System.out.println("- " + element + " (" + list.elementCount.get(element) + " veces)");
        }
    
        System.out.println("\n Algunos elementos más repetidos:");
        list.elementCount.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .forEach(entry -> System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " veces"));
    
        // Mostrar el arreglo
        Object[] array = list.toArray(); 
        for (int i = 0; i < Math.min(10, array.length); i++) {
            System.out.print(array[i] + " ");
        }
        
    
        
        try {
            int totalElements = list.length;
            int rows = (int) Math.sqrt(totalElements);
            while (totalElements % rows != 0) {
                rows--;
            }
            int cols = totalElements / rows;
    
            System.out.println("\n Matriz generada (" + rows + "x" + cols + "):");
            String[][] matrix = list.toMatrix(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(String.format("%-15s", matrix[i][j]));
                }
                System.out.println();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n No se puede convertir a matriz: " + e.getMessage());
        }
    
        // Tiempo de ejecución
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;
        System.out.println("\n Tiempo total de ejecución: " + durationMs + " ms");
    }
}   