# ITI 1121 - Lab 11 - Iterators and Binary Search Trees

## Learning objectives

* **Understand** the principle of iterators.

* **Explain** in your own words the concept of iterators.

* **Solve** problems using an iterator.

* **Design** and **implement** recursive methods for a binary search tree.

## Submission

Please read the [junit instructions](JUNIT.en.md) for help
running the tests for this lab.

Please read the [Submission Guidelines](SUBMISSION.en.md) carefully.
Errors in submitting will affect your grades.

Submit answers for the Exercises 1 and 2. Your submission has to include the following files:

* Iterator.java
* BitList.java
* Iterative.java

## Iterators

Iterators are a type of data structure that allows us to traverse a collection of elements in order to interact with those elements. An iterator allows us to access an element of a collection, and then access the next element until the collection is empty.

In Java, the interface Iterator has the methods: **hasNext()**, **next()** and **remove()**.

* **hasNext()** : Returns true if the iterator has a next element
* **next()** : Returns the next element or throws **NoSuchElementException** if no next element exists
* **remove()** : Removes the last element from the collection returned by the iterator, thus it has to be called after at least one call of the method **next()**.

To illustrate the use of an iterator, we will use the class **java.util.LinkedList**. LinkedList is a linked list that implements the interface **iterable**, which specifies the method **iterator()**. The method **iterator()** returns an object implementing the interface Iterator.

Once we have that iterator, we can go through all the elements in the list **al**. To do so, we use a _while_ loop with the condition that **hasNext()** returns true. We can then print every element of the list.
```java
import java.util.*;

public class IteratorDemo{
  public static void main(String args[]) {
    // Create an array list
    LinkedList<String> al = new LinkedList<String>();

    // add elements to the array list
    al.add("dog");
    al.add("bird");
    al.add("fish");
    al.add("cat");
    al.add("monkey");
    al.add("lizard");

    // Use iterator to display contents of al
    System.out.print("Contents of al: ");
    Iterator<String> itr = al.iterator();

    while (itr.hasNext()) {
      String element = itr.next();
      System.out.print(element + " ");
    }
  }
}
```
Note that with a basic operator, we cannot modify the elements of the list. However, many implementations of the interface Iterator have additional methods that allow us to modify the elements, and much more. For example, the class **ListIterator** that implements Iterator has additional methods such as **add**, **hasPrevious**, **previous**, **set** and many others.

#### Practice

Modify the code above to print only the elements of the list **al** that precedes the String “cat” in the list (print all elements until you encounter “cat”).

**Hint**: modify the condition of the while loop.

### _For each_ loop

The _for each_ loop acts as an iterator, and it allows us to directly act on the elements of the collection (without using methods such as set()).

The syntax for the _for each_ loop is as follows:
```java
    for (type elem:collection){
        //more code
    }
```
Where :

* **type** : The type of the elements of the collection
* **elem** : The name of the variable representing each element in the loop
* **collection** : The name of the variable of the collection that we what to iterate through.

By default this loop will go through each element of the collection. However if we want to stop the loop after a certain condition we can use the command **break** that will immediately exit the loop.

Here is a solution of the practice exercise above that uses the _for each_ loop.
```java
import java.util.*;

public class IteratorDemo{

  public static void main(String args[]) {
    // Create an array list
    LinkedList<String> al = new LinkedList<String>();

    // add elements to the array list
    al.add("dog");
    al.add("bird");
    al.add("fish");
    al.add("cat");
    al.add("monkey");
    al.add("lizard");

    System.out.println("\nSolving using augmented for loop: ");

    for (String element:al){
      if (element.equals("cat")){
        break;
      }
      System.out.print(element + " ");
    }
  }
}
```
## Exercise 1

For this exercise, you will use a linked list to save a number of bits (zeros and ones). Unlike other implementations seen before, the values in the nodes are integers (int).

The class **BitList** has an **inner class**. This is a second **private** class definition in the same folder as the public class **BitList**. This inner class is **BitListIterator**.

**BitListIterator** implements the interface Iterator forcing it to implement the three methods: **hasNext**, **next**, and **remove**.

Take the time to understand each method and their implementations.

## BitList

Because most operations on bits work from right to left, your implementation must store the bits in the “right-to-left” order — i.e. with the rightmost bit in the first node of the list.

For example, the bits “11010” must be stored in a list such that the bit 0 is the first, followed by 1, followed by 0, followed by, 1, followed by 1:
-> 0 -> 1 -> 0 -> 1 -> 1.

The linked list makes use of the private instance variable **first** of type Node that represents the first element in the list.

```java
import java.util.NoSuchElementException;

// Stores the bits in reverse order!

public class BitList {

  // useful constants

  public static final int ZERO = 0;
  public static final int ONE = 1;

  // instance variables

  private Node first;

  // constructors

  public BitList() {
    first = null;
  }

  public BitList(String s) {
    throw new UnsupportedOperationException("not implemented yet!");
  }

  public void addFirst(int bit) {
    if ((bit != ZERO) && (bit != ONE)) {
      throw new IllegalArgumentException(Integer.toString(bit));
    }

    first = new Node(bit, first);
  }

  public int removeFirst() {

    if (first == null) {
      throw new NoSuchElementException();
    }

    int saved = first.bit;

    first = first.next;

    return saved;
  }

  public Iterator iterator() {
    return new BitListIterator();
  }

  public String toString() {
    String str = "";

    if (first == null) {
        str += ZERO;
    } else {
        Node p = first;
        while (p!=null) {
            str = p.bit + str; // reverses the order!
            p = p.next;
        }
    }
    return str;
  }

  // The implementation of the nodes (static nested class)

  private static class Node {

    private int bit; // <- NEW
    private Node next;

    private Node(int bit, Node next) { // <- ACCORDINGLY ...
      this.bit = bit;
      this.next = next;
    }
  }

  // The implementation of the iterators (inner class)

  private class BitListIterator implements Iterator {

    private Node current = null;

    private BitListIterator() {
      current = null;
    }

    public boolean hasNext() {
      return ((current == null && first != null)
             || (current != null && current.next != null));
    }

    public int next() {

      if (current == null) {
        current = first ;
      } else {
        current = current.next ; // move the cursor forward
      }

      if (current == null) {
        throw new NoSuchElementException() ;
      }

      return current.bit;
    }

    public void add(int bit) {

      if ((bit != ZERO) && (bit != ONE)) {
        throw new IllegalArgumentException(Integer.toString(bit));
      }

      Node newNode;

      if (current == null) {
        first = new Node(bit, first);
        current = first;
      } else {
        current.next = new Node(bit, current.next);
        current = current.next;
      }
    }
  }
}
```
#### Complete the implementation of the class BitList

##### `public BitList(String s)` 

This constructor has to create a list representing the chain of 0s and 1s given as parameter.

The given string, **s**, must be a string of 0s and 1s, otherwise the constructor must throw an exception of type **IllegalArgumentException**.

This constructor initializes the new BitList instance to represent the value in the string. Each character in the string represents one bit of the list, with the rightmost character in the string being the low order bit.

For example, given the string “1010111” the constructor should initialize the new BitList to include this list of bits: -> 1 -> 1 -> 1 -> 0 -> 1 -> 0 -> 1

If given the empty string, the constructor should create an empty list — null is a not a valid argument.

The constructor must not remove the leading zeroes. For example, given “0001” the constructor must create a list of size 4: -> 1 -> 0 -> 0 -> 0


## Exercise 2

Create a class called `Iterative` and implement the methods below. Your solutions must be iterative (i.e. must use iterators).

#### 2.1. static BitList complement(BitList in)

Write a static iterative method that returns a new BitList that is the complement of its input. The complement of 0 is 1, and vice-versa. The complement of a list of bits is a new list such that each bit is the complement of the bit at the same position in the original list. Here are 4 examples:

```
1011
0100  

0
1

01
10  

0000111
1111000
```

#### 2.2 static BitList or(BitList a, BitList b)

Write a static iterative method that returns the **or** of two BitLists. This is a list of the same length as **a** and **b** such that each bit is the **or** of the bits at the equivalent position in the lists **a** and **b**. It throws an exception, **IllegalArgumentException**, if either list is empty or the lists are of different lengths.

When comparing two bits with the binary operation **or**, the result is 1 if at least one of the bits is 1, and 0 if both bits are 0s.

```java
a = 10001
b = 00011
a OR b = 10011
```

#### 2.3 **and** and **xor**

Create the methods **static BitList and(BitList a, BitList b)** and **static BitList xor(BitList a, BitList b)**. These methods are similar to the method **or** but implement the binary operation **and** as well as the **exclusive or** (**xor**).

When using the binary operation **and**, the result of the operation is 1 if both bits are 1s, 0 otherwise.

```java
a = 10001  
b = 00011  
a AND b = 00001
```

When using the binary operation **xor**, the result of the operation is 1 if one and only one of the bits is a 1. The result will be 0 if both bits are 1 or 0.

```java
a = 10001  
b = 00011  
a xor b = 10010
```

## Exercise 3 (Optional)

This part is optional and is not to be handed in. However, we strongly recommend doing it because it is excellent practice for the final exam.

For this section, you will need to use the class **BinarySearchTree**.
```java
public class BinarySearchTree<E extends Comparable<E>> {

  // A static nested class used to store the elements of this tree

  private static class Node<E> {
    private E value;
    private Node<E> left;
    private Node<E> right;
    private Node(E value) {
      this.value = value;
      left = null;
      right = null;
    }
  }

  private Node<E> root = null;

  /**
   * Inserts an object into this BinarySearchTree.
   *
   * @param obj item to be added
   * @return true if the object has been added and false otherwise
   */

  public boolean add(E obj) {

    // pre-condtion:

    if (obj == null) {
        throw new IllegalArgumentException("null");
    }

    // special case:

    if (root == null) {
        root = new Node<E>(obj);
        return true;
    }

    // general case:

    return add(obj, root);
  }

  private boolean add(E obj, Node<E> current) {

    boolean result;
    int test = obj.compareTo(current.value);

    if (test == 0) {
      result = false;
    } else if (test < 0) {
      if (current.left == null) {
        current.left = new Node<E>(obj);
        result = true;
      } else {
        result = add(obj, current.left);
      }
    } else {
      if (current.right == null) {
        current.right = new Node<E>(obj);
        result = true;
      } else {
        result = add(obj, current.right);
      }
    }
    return result;
  }

  /**
   * Looks up for obj in this BinarySearchTree, returns true
   * if obj is found and false otherwise.
   *
   * @param obj value to look for
   * @return true if the object has been found and false otherwise
   */

  public boolean contains(E obj) {

    // pre-condtion:

    if (obj == null) {
        throw new IllegalArgumentException("null");
    }

    return contains(obj, root);
  }

  private boolean contains(E obj, Node<E> current) {

    boolean result;

    if (current == null) {
      result = false;
    } else {
      int test = obj.compareTo(current.value);

      if (test == 0) {
          result = true;
      } else if (test < 0) {
          result = contains(obj, current.left);
      } else {
          result = contains(obj, current.right);
      }
    }
    return result;
  }

  // Implement the method max()
  public E max() {

    throw new UnsupportedOperationException("not implemented yet!");

  }

  // Implement the method min()
  public E min() {

    throw new UnsupportedOperationException("not implemented yet!");

  }

  // Implement the method depth()
  public int depth() {

    throw new UnsupportedOperationException("not implemented yet!");

  }

  // Implement the method isTwoTree()
  public boolean isTwoTree() {

    throw new UnsupportedOperationException("not implemented yet!");

  }


  public String toString() {
    return toString(root);
  }

  private String toString(Node<E> current) {

    if (current == null) {
      return "()";
    }

    return "(" + toString(current.left) + current.value + toString(current.right) + ")";
  }

}
```

You will need to implement the following methods:

#### 1. E max()

Returns the highest value of the tree, throws the exception `NoSuchElementException` if the tree is empty.

For example, if you declare `BinarySearchTree t;`, and add “Lion” to the tree

```java
BinarySearchTree t;
t = new BinarySearchTree();
t.add("Lion");
```

and call `t.max()`, it should return **"Lion"**.

#### 2. E min()

Returns the lowest value of the tree, throws the exception `oSuchElementException` if the tree is empty.

For example, if you call `t.min()` to the basic tree of the example above, it should return **"Lion"**.

#### 3. int depth()

Returns the depth of the tree, that is the depth of the deepest node.

For example, if you call `t.depth()` to the basic tree of the example above, it should return **0**.

#### 4. boolean isTwoTree()

A binary tree is a **two-tree** when it is empty or if all the internal nodes have 2 childs.

For example, if you call `t.isTwoTree()` to the basic tree of the example above, it should return **true**.

### Resources

* [https://docs.oracle.com/javase/tutorial/getStarted/application/index.html](https://docs.oracle.com/javase/tutorial/getStarted/application/index.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/cupojava/win32.html](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/win32.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/cupojava/unix.html](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/unix.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/problems/index.html](https://docs.oracle.com/javase/tutorial/getStarted/problems/index.html)
