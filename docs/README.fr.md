# ITI 1121 - Lab 11 - Iterators et Binary Search Trees

## Objectifs d'apprentissage

* **Comprendre** le principe d'itérateur.

* **Expliquer** le concept d’itérateur dans vos propres mots.

* **Résoudre** des problèmes exigeant l’utilisation d’itérateurs.

* **Concevez** et **implémentez** des méthodes récursives pour un arbre de recherche binaire.

## Soumission

Lire le [junit instructions](JUNIT.fr.md) au besoin pour aider
avec les tests du lab.

Lire le [guides de soumission](SUBMISSION.fr.md) avec attention.
Les erreurs de soumission vont affecté votre évaluation.

Soumettre les réponses des exercises 1, et 2. Votre soumission doit inclure:
* Iterator.java
* BitList.java
* Iterative.java

## Itérateurs


Les itérateurs sont un type de structure de données qui nous permet de parcourir une collection d'éléments afin d'interagir avec ces éléments. Un itérateur nous permet d'accéder à un élément d'une collection, puis d'accéder à l'élément suivant jusqu'à ce que la collection soit vide.

Dans Java, l'interface Iterator a les méthodes: **hasNext()**, **next()** et **remove()**.

* **hasNext()** : Renvoie vrai si l'itérateur a un élément suivant
* **next()** : Renvoie l'élément suivant ou lève **NoSuchElementException** si aucun élément suivant n'existe
* **remove()** : Supprime le dernier élément de la collection retournée par l'itérateur, il doit donc être appelé après au moins un appel de la méthode **next ()**.

Pour illustrer l'utilisation d'un itérateur, nous utiliserons la classe **java.util.LinkedList**. LinkedList est une liste chaînée qui implémente l'interface **iterable**, qui spécifie la méthode **iterator ()**. La méthode **iterator ()** renvoie un objet implémentant l'interface Iterator.

Une fois que nous avons cet itérateur, nous pouvons parcourir tous les éléments de la liste **al**. Pour ce faire, nous utilisons une boucle _ while_ avec la condition que **hasNext ()** renvoie true. Nous pouvons ensuite imprimer chaque élément de la liste.
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
Notez qu'avec un opérateur basique, nous ne pouvons pas modifier les éléments de la liste. Cependant, de nombreuses implémentations de l'interface Iterator ont des méthodes supplémentaires qui nous permettent de modifier les éléments, et bien plus encore. Par exemple, la classe **ListIterator** qui implémente Iterator a des méthodes supplémentaires telles que **add**, **hasPrevious**, **previous**, **set** et bien d'autres.

#### Exercise

Modifiez le code ci-dessus pour imprimer uniquement les éléments de la liste **al** qui précède la chaîne "cat" dans la liste (imprimez tous les éléments jusqu'à ce que vous rencontriez "cat").

**Indice**: modifier la condition de la boucle while.

### _For each_ loop

La boucle _for each_ agit comme un itérateur, et elle nous permet d'agir directement sur les éléments de la collection (sans utiliser de méthodes telles que set ()).

La syntaxe de la boucle _for each_ est la suivante:
```java
    for (type elem:collection){
        //more code
    }
```
Où :

* **type** : Le type des éléments de la collection
* **elem** : Le nom de la variable représentant chaque élément de la boucle
* **collection** : Nom de la variable de la collection à travers laquelle itérer.

Par défaut, cette boucle passera par chaque élément de la collection. Cependant, si nous voulons arrêter la boucle après une certaine condition, nous pouvons utiliser la commande **break** qui quittera immédiatement la boucle.

Voici une solution de l'exercice pratique ci-dessus qui utilise la boucle _for each_.
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

Pour ce laboratoire, vous utiliserez une liste chaînée afin de sauvegarder un nombre illimité de bits : des zéros et des uns. Contrairement aux implémentations vues jusqu’ici, les valeurs à l’intérieur des noeuds sont des entiers (int).

La classe **BitList** possède une **classe interne**. Il s'agit en fait d'une seconde définition de classe dont la visibilité est **private** se trouvant dans le même fichier de définition de la classe **public BitList**. Cette classe interne (inner en anglais) est **BitListIterator**.

**BitListIterator** réalise l'interface **Iterator** qui oblige l'implémentation de 3 méthodes, soit **hasNext**, **next** et **add**.

Prenez le temps de bien comprendre le comportement de chacune des méthodes et leur implémentation.

## BitList

Puisque plusieurs opérations sur les bits nécessitent un traitement de droite à gauche, l’implémentation doit sauvegarder les bits dans l’ordre «droite à gauche», c.-à-d. le bit le plus à droite dans le mot binaire sera en en première position dans la liste (premier noeud).

Par exemple, les bits «11010» doivent être sauvegardés dans une liste telle que le premier noeud contienne 0, le second 1, suivi d’un 0, puis 1, et 1 :
-> 0 -> 1 -> 0 -> 1 -> 1.

La liste liée utilise la variable d'instance privée ** first ** de type Node qui représente le premier élément de la liste.

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
#### Terminer l'implémentation de la classe BitList

##### `public BitList(String s)` 

Ce constructeur doit créer une liste représentant la chaîne de 0s et 1s donnée en entrée.

La chaîne s doit contenir que des 0s et des 1s sinon il faudra lancer l'exception **IllegalArgumentException**.

Le constructeur initialise cette nouvelle liste de bits afin de représenter la valeur de la chaîne. Chaque caractère de la chaîne représente un bit de la liste.

Par exemple, étant donné la chaîne «1010111», le constructeur doit initialiser cette liste afin d’y inclure les bits suivants (portez attention à l'ordre des bits encore une fois!).
-> 1 -> 1 -> 1 -> 0 -> 1 -> 0 -> 1

Si la chaîne est vide, le constructeur doit créer une liste vide — la valeur null n’est pas valide.

Le constructeur ne doit pas retirer les zéros de la partie gauche. Par exemple, étant donné «0001» le constructeur doit initialiser cette liste comme suit.
-> 1 -> 0 -> 0 -> 0

## Exercise 2

Créez une classe nommée Iterative. Implémentez les méthodes ci-bas dans cette classe. Vos solutions doivent être itératives (c.-à-d. utilise des itérateurs).

#### 2.1. static BitList complement(BitList in)

Créez une méthode «static» itérative retournant une nouvelle liste de bits (BitList) qui soit le complément de celle en entrée. Le complément de 0 est 1 ; et vice-versa. Le complément d’une liste de bits est une nouvelle liste, de même longueur que l’entrée, telle que chaque bit est le complément du bit à la même position dans la liste d’entrée. Voici 4 exemples.

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

Vous devez écrire une méthode de classe retournant le ou («or») des deux listes passées en paramètre. Cette liste a la même longueur que les listes en entrée et chacun des bits de cette liste est le ou («or») des bits à cette position dans les listes en entrée. La méthode lance une exception, **IllegalArgumentException**, si l’une ou l’autre des deux listes est vide ou si les listes sont de longueur différente.

Lorsque l’on compare deux bits avec le ou, le résultat est 1 si au moins l’un des bit est un 1, 0 si les deux bits sont des 0.

```java
a = 10001
b = 00011
a OR b = 10011
```

#### 2.3 **and** and **xor**

Créer les méthodes **static BitList and(BitList a, BitList b)** et **static BitList xor(BitList a, BitList b)**. Ces méthodes sont similaires à la méthode or mais il implémente respectivement le et binaire ainsi que le **ou exclusif** (xor).

Avec le **et** binaire, le résultat d’une comparaison est 1 si les deux bits sont 1, sinon le résultat est 0.

```java
a = 10001  
b = 00011  
a AND b = 00001
```

Avec le **ou exclusif** binaire, le résultat d’une comparaison est 1 si et seulement si l’un des deux bits est 1. Donc si les deux bits sont 0 ou 1, le résultat sera 0.
```java
a = 10001  
b = 00011  
a xor b = 10010
```

## Exercise 3 (Optionelle)

Cette partie est optionnelle et ne doit pas être remise. Cependant, nous vous le recommandons fortement, car il s'agit d'une excellente pratique pour l'examen final.

Pour cette partie du laboratoire, vous devez utiliser la classe **BinarySearchTree**.
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

Vous devez implémenter les méthodes qui suivent.

#### 1. E max()

Retourne la plus grande valeur de cet arbre. Lance l’exception **NoSuchElementException** si l’arbre est vide.

Par exemple, si vous déclarez BinarySearchTree t, et ajoute un animal "Lion" à l'arbre

```java
BinarySearchTree t;
t = new BinarySearchTree();
t.add("Lion");
```

et appelez **t.max()**, cela devrait retourner **"Lion"**.

#### 2. E min()

Retourne la plus petite valeur de cet arbre. Lance l’exception NoSuchElementException si l’arbre est vide.

Par exemple, si vous appelez **t.min()** sur la base de l'exemple ci-dessus, cela devrait retourner **"Lion"**.

#### 3. int depth()

Retourne la profondeur de l’arbre, c’est-à-dire la profondeur du noeud le plus profond.

Par exemple, si vous appelez **t.depth()** sur la base de l'exemple ci-dessus, cela devrait retourner **0**.

#### 4. boolean isTwoTree()

Un arbre binaire est un two-tree s’il est vide ou si tous ses noeuds internes ont deux fils.

Par exemple, si vous appelez **t.isTwoTree()** sur la base de l'exemple ci-dessus, cela devrait retourner **true**.

### Resources

* [https://docs.oracle.com/javase/tutorial/getStarted/application/index.html](https://docs.oracle.com/javase/tutorial/getStarted/application/index.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/cupojava/win32.html](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/win32.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/cupojava/unix.html](https://docs.oracle.com/javase/tutorial/getStarted/cupojava/unix.html)
* [https://docs.oracle.com/javase/tutorial/getStarted/problems/index.html](https://docs.oracle.com/javase/tutorial/getStarted/problems/index.html)
