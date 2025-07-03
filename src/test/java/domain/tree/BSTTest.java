package domain.tree;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BSTTest {

    private BST bst;

    @BeforeEach
    void setUp() {
        bst = new BST();
    }

    @Test
    void testRemoveSubTree1_EmptyTree() {
        // Test con árbol vacío
        assertThrows(TreeException.class, () -> {
            bst.removeSubTree1();
        });
    }

    @Test
    void testRemoveSubTree1_BasicCase() throws TreeException {
        // Crear un BST similar al de las imágenes
        bst.add(33);
        bst.add(14);
        bst.add(52);
        bst.add(13);
        bst.add(22);
        bst.add(34);
        bst.add(91);
        bst.add(16);
        bst.add(23);
        bst.add(45);
        bst.add(70);
        bst.add(92);
        bst.add(41);
        bst.add(42);
        bst.add(69);
        bst.add(89);
        bst.add(55);
        bst.add(67);

        System.out.println("BST antes de removeSubTree1:");
        System.out.println("PreOrder: " + bst.preOrder());
        System.out.println("InOrder: " + bst.inOrder());

        // Aplicar removeSubTree1
        bst.removeSubTree1();

        System.out.println("\nBST después de removeSubTree1:");
        System.out.println("PreOrder: " + bst.preOrder());
        System.out.println("InOrder: " + bst.inOrder());

        // Verificar que los subárboles con cadenas de nodos con exactamente un hijo fueron eliminados
        assertFalse(bst.contains(34), "El nodo 34 debería haber sido eliminado");
        assertFalse(bst.contains(45), "El nodo 45 debería haber sido eliminado");
        assertFalse(bst.contains(41), "El nodo 41 debería haber sido eliminado");
        assertFalse(bst.contains(42), "El nodo 42 debería haber sido eliminado");
        assertFalse(bst.contains(55), "El nodo 55 debería haber sido eliminado");
        assertFalse(bst.contains(67), "El nodo 67 debería haber sido eliminado");

        // Verificar que los nodos que NO forman subárboles válidos siguen existiendo
        assertTrue(bst.contains(33), "El nodo 33 debería seguir existiendo");
        assertTrue(bst.contains(14), "El nodo 14 debería seguir existiendo");
        assertTrue(bst.contains(52), "El nodo 52 debería seguir existiendo");
        assertTrue(bst.contains(22), "El nodo 22 debería seguir existiendo");
        assertTrue(bst.contains(13), "El nodo 13 debería seguir existiendo (es hoja de un nodo con 2 hijos)");
        assertTrue(bst.contains(16), "El nodo 16 debería seguir existiendo");
        assertTrue(bst.contains(23), "El nodo 23 debería seguir existiendo");
        assertTrue(bst.contains(91), "El nodo 91 debería seguir existiendo");
        assertTrue(bst.contains(70), "El nodo 70 debería seguir existiendo");
        assertTrue(bst.contains(69), "El nodo 69 debería seguir existiendo");
        assertTrue(bst.contains(89), "El nodo 89 debería seguir existiendo");
        assertTrue(bst.contains(92), "El nodo 92 debería seguir existiendo");
    }

    @Test
    void testRemoveSubTree1_SingleNodeWithOneChild() throws TreeException {
        // Test con un árbol simple donde la raíz tiene un solo hijo
        bst.add(10);
        bst.add(5);

        System.out.println("BST simple antes:");
        System.out.println("InOrder: " + bst.inOrder());

        bst.removeSubTree1();

        System.out.println("BST simple después:");
        if (!bst.isEmpty()) {
            System.out.println("InOrder: " + bst.inOrder());
        } else {
            System.out.println("Árbol vacío");
        }

        // Si todo el árbol cumple la condición, debería estar vacío
        assertTrue(bst.isEmpty(), "El árbol debería estar vacío");
    }

    @Test
    void testRemoveSubTree1_NoRemoval() throws TreeException {
        // Test con un árbol donde ningún subárbol cumple la condición
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);

        String originalInOrder = bst.inOrder();

        bst.removeSubTree1();

        String newInOrder = bst.inOrder();

        assertEquals(originalInOrder, newInOrder, "El árbol no debería cambiar");
    }

    @Test
    void testRemoveSubTree1_ChainOfSingleChildren() throws TreeException {
        // Test con una cadena de nodos con un solo hijo
        bst.add(50);
        bst.add(40);
        bst.add(30);
        bst.add(20);

        System.out.println("Cadena antes:");
        System.out.println("InOrder: " + bst.inOrder());

        bst.removeSubTree1();

        System.out.println("Cadena después:");
        if (!bst.isEmpty()) {
            System.out.println("InOrder: " + bst.inOrder());
        } else {
            System.out.println("Árbol vacío");
        }

        // La cadena completa debería ser eliminada
        assertTrue(bst.isEmpty(), "Toda la cadena debería ser eliminada");
    }

    @Test
    void testRemoveSubTree1_MixedCase() throws TreeException {
        // Test con un caso mixto
        bst.add(100);
        bst.add(50);
        bst.add(150);
        bst.add(25);
        bst.add(75);
        bst.add(125); // Este nodo con un hijo debería eliminarse
        bst.add(120); // Hijo único de 125

        System.out.println("Caso mixto antes:");
        System.out.println("InOrder: " + bst.inOrder());

        bst.removeSubTree1();

        System.out.println("Caso mixto después:");
        System.out.println("InOrder: " + bst.inOrder());

        // Verificar que 125 y 120 fueron eliminados (si formaban un subárbol válido)
        // pero el resto del árbol se mantiene
        assertTrue(bst.contains(100), "El nodo 100 debería seguir existiendo");
        assertTrue(bst.contains(50), "El nodo 50 debería seguir existiendo");
        assertTrue(bst.contains(150), "El nodo 150 debería seguir existiendo");
    }
}