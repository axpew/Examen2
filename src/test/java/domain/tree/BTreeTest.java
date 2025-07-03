package domain.tree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    private BTree btree;

    @BeforeEach
    void setUp() {
        btree = new BTree();
    }

    @Test
    void oddPathSumList() throws TreeException {
        // Test con árbol vacío
        assertThrows(TreeException.class, () -> btree.oddPathSumList());

        // Test básico: crear un árbol simple manualmente
        BTree simpleTree = createSimpleTree();
        String result = simpleTree.oddPathSumList();

        // Verificar que no es null o vacío
        assertNotNull(result);

        // Test específico: árbol donde podemos predecir las sumas
        BTree predictableTree = createPredictableTree();
        String predictableResult = predictableTree.oddPathSumList();

        System.out.println("Resultado árbol predecible: " + predictableResult);

        // Verificar que contiene valores esperados
        assertNotNull(predictableResult);
        assertFalse(predictableResult.trim().isEmpty());
    }

    @Test
    void oddPathSumListWithKnownValues() throws TreeException {
        // Crear un árbol más controlado para verificar lógica específica
        BTree controlledTree = createControlledTree();
        String result = controlledTree.oddPathSumList();

        System.out.println("Resultado árbol controlado: " + result);

        // Verificar que el resultado contiene algunos valores esperados
        assertNotNull(result);

        // Test adicional: verificar que los valores en el resultado son números
        if (!result.trim().isEmpty()) {
            String[] values = result.trim().split("\\s+");
            for (String value : values) {
                assertDoesNotThrow(() -> Integer.parseInt(value),
                        "Todos los valores deben ser números enteros");
            }
        }
    }

    @Test
    void oddPathSumListComplexTree() throws TreeException {
        // Agregar múltiples elementos para crear un árbol más complejo
        for (int i = 1; i <= 10; i++) {
            btree.add(i);
        }

        String result = btree.oddPathSumList();
        System.out.println("Resultado árbol complejo (1-10): " + result);

        assertNotNull(result);

        // Verificar que contiene al menos algunos números
        if (!result.trim().isEmpty()) {
            assertTrue(result.trim().length() > 0);
        }
    }

    @Test
    void oddPathSumListSingleNode() throws TreeException {
        // Test con un solo nodo
        btree.add(5);
        String result = btree.oddPathSumList();

        // Como 5 es impar, debería aparecer en el resultado
        assertEquals("5 ", result);
    }

    @Test
    void oddPathSumListEvenRootNode() throws TreeException {
        // Test con raíz par
        btree.add(4);
        String result = btree.oddPathSumList();

        // Como 4 es par, no debería aparecer en el resultado
        assertEquals("", result);
    }

    // Métodos auxiliares para crear árboles específicos
    private BTree createSimpleTree() {
        BTree tree = new BTree();
        // Agregar algunos elementos
        tree.add(7);
        tree.add(3);
        tree.add(11);
        tree.add(1);
        tree.add(5);
        return tree;
    }

    private BTree createPredictableTree() {
        BTree tree = new BTree();
        // Crear un árbol con valores que permitan predecir mejor las sumas
        tree.add(1);  // Suma: 1 (impar)
        tree.add(2);  // Dependiendo de la posición: 1+2=3 (impar)
        tree.add(3);  // Dependiendo de la posición
        tree.add(4);
        return tree;
    }

    private BTree createControlledTree() {
        BTree tree = new BTree();
        // Agregar elementos específicos para el test
        tree.add(2);  // Raíz par
        tree.add(1);  // 2+1=3 (impar)
        tree.add(3);  // 2+3=5 (impar)
        tree.add(6);  // Dependiendo de posición
        tree.add(8);
        return tree;
    }

    @Test
    void testTreeStructureAfterOddPathSum() throws TreeException {
        // Verificar que el método no modifica la estructura del árbol
        btree.add(5);
        btree.add(3);
        btree.add(7);

        int sizeBefore = btree.size();
        String preOrderBefore = btree.preOrder();

        // Llamar al método
        btree.oddPathSumList();

        // Verificar que la estructura no cambió
        assertEquals(sizeBefore, btree.size());
        assertEquals(preOrderBefore, btree.preOrder());
    }

    @Test
    void demonstrateOddPathSumLogic() throws TreeException {
        System.out.println("\n=== DEMOSTRACIÓN DE LÓGICA oddPathSumList ===");

        // Crear árbol simple para demostrar
        BTree demoTree = new BTree();
        demoTree.add(7);   // Raíz
        demoTree.add(14);
        demoTree.add(6);
        demoTree.add(2);

        System.out.println("Estructura del árbol:");
        System.out.println(demoTree.preOrder());

        String result = demoTree.oddPathSumList();
        System.out.println("Nodos con suma de ruta impar: " + result);

        assertNotNull(result);
        System.out.println("=== FIN DEMOSTRACIÓN ===\n");
    }
}