//Akiha Kusumoto
//Source: https://www.geeksforgeeks.org/stack-class-in-java/
//

import java.util.*;



public class DoubleStack implements Cloneable {

    private class Node {
        double data;
        Node next;

        Node(double data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;

    public DoubleStack() {
        this.top = null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DoubleStack cloned = (DoubleStack) super.clone();
        if (this.top == null) {
            return cloned;
        }
        cloned.top = new Node(this.top.data);
        Node currClonedNode = cloned.top;
        Node currOriginalNode = this.top.next;

        while (currOriginalNode != null) {
            currClonedNode.next = new Node(currOriginalNode.data);
            currClonedNode = currClonedNode.next;
            currOriginalNode = currOriginalNode.next;
        }
        return cloned;
    }

    public boolean stEmpty() {
        return this.top == null;
    }

    public void push(double a) {
        Node newNode = new Node(a);
        newNode.next = this.top;
        this.top = newNode;
    }

    public double pop() {
        if (this.stEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        double data = this.top.data;
        this.top = this.top.next;
        return data;
    }

    public void op(String s) {
        if (this.top == null || this.top.next == null) {
            throw new RuntimeException("Not enough elements for operation: " + s);        }
        double b = this.pop();
        double a = this.pop();
        switch (s) {
            case "+":
                this.push(a + b);
                break;
            case "-":
                this.push(a - b);
                break;
            case "*":
                this.push(a * b);
                break;
            case "/":
                if (b == 0) throw new ArithmeticException("Division by zero");
                this.push(a / b);
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + s);
        }
    }

    public double tos() {
        if (this.stEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return this.top.data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleStack)) return false;
        DoubleStack that = (DoubleStack) o;

        Node currentA = this.top;
        Node currentB = that.top;

        while (currentA != null && currentB != null) {
            if (currentA.data != currentB.data) {
                return false;
            }
            currentA = currentA.next;
            currentB = currentB.next;
        }
        return currentA == null && currentB == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = this.top;
        while (current != null) {
            sb.insert(0, current.data + (current.next != null ? " " : ""));
            current = current.next;
        }
        return sb.toString();
    }

    public static double interpret(String pol) {
        if (pol == null || pol.trim().isEmpty()) {
            throw new RuntimeException("No expression given: " + pol);
        }
        DoubleStack stack = new DoubleStack();
        String[] tokens = pol.split("\\s+");
        for (String token : tokens) {
            if (token.trim().isEmpty()) continue;
            try {
                double num = Double.parseDouble(token);
                stack.push(num);
            } catch (NumberFormatException e) {
                try {
                    stack.op(token);
                } catch (RuntimeException ex) {
                    throw new RuntimeException("Error in expression: " + pol + " - " + ex.getMessage());
                }
            }
        }
        if (stack.stEmpty() || stack.top.next != null) {
            throw new RuntimeException("Invalid expression or redundant elements in expression: " + pol);
        }
        return stack.pop();
    }
}

}
