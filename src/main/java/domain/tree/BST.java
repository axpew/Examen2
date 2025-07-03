package domain.tree;

/* *
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * BST = Binary Search Tree (Arbol de Búsqueda Binaria)
 * */
public class BST {
    private BTreeNode root; //se refiere a la raiz del arbol

    public BTreeNode getRoot(){return root;}

    public int size() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null) return 0;
        else return 1 + size(node.left) + size(node.right);
    }


    public void clear() {
        root = null;
    }


    public boolean isEmpty() {
        return root==null;
    }


    public boolean contains(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element) throws TreeException {
        if(node==null) return false;
        else if(util.Utility.compare(node.data, element)==0) return true;
        else if(util.Utility.compare(element, node.data)<0)
            return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }


    public void add(Object element) {
            this.root = add(root, element);

    }

    private BTreeNode add(BTreeNode node, Object element) {
        if(node==null)
            node = new BTreeNode(element);
        else if(util.Utility.compare(element, node.data)<0)
            node.left = add(node.left, element);
        else if(util.Utility.compare(element, node.data)>0)
            node.right = add(node.right, element);
        return node;
    }


    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        root = remove(root, element);
    }

    private BTreeNode remove(BTreeNode node, Object element) throws TreeException{
        if(node!=null){
            if(util.Utility.compare(element, node.data)<0)
              node.left = remove(node.left, element);
            else if(util.Utility.compare(element, node.data)>0)
                node.right = remove(node.right, element);
            else if(util.Utility.compare(node.data, element)==0){
                //caso 1. es un nodo si hijos, es una hoja
                if(node.left==null && node.right==null) return null;
                //caso 2-a. el nodo solo tien un hijo, el hijo izq
                else if (node.left!=null&&node.right==null) {
                    return node.left;
                } //caso 2-b. el nodo solo tien un hijo, el hijo der
                else if (node.left==null&&node.right!=null) {
                    return node.right;
                }
                //caso 3. el nodo tiene dos hijos
                else{
                //else if (node.left!=null&&node.right!=null) {
                    /* *
                     * El algoritmo de supresión dice que cuando el nodo a suprimir
                     * tiene 2 hijos, entonces busque una hoja del subarbol derecho
                     * y sustituya la data del nodo a suprimir por la data de esa hoja,
                     * luego elimine esa hojo
                     * */
                    Object value = min(node.right);
                    node.data = value;
                    node.right = remove(node.right, value);
                }
            }
        }
        return node; //retorna el nodo modificado o no
    }


    public int height(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return height(root, element, 0);
    }

    //devuelve la altura de un nodo (el número de ancestros)
    private int height(BTreeNode node, Object element, int level) throws TreeException {
        if(node==null) return 0;
        else if(util.Utility.compare(node.data, element)==0) return level;
        else return Math.max(height(node.left, element, ++level),
                    height(node.right, element, level));
    }


    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        //return height(root, 0); //opción-1
        return height(root); //opción-2
    }

    //devuelve la altura del árbol (altura máxima de la raíz a
    //cualquier hoja del árbol)
    private int height(BTreeNode node, int level){
        if(node==null) return level-1;//se le resta 1 al nivel pq no cuente el nulo
        return Math.max(height(node.left, ++level),
                height(node.right, level));
    }

    //opcion-2
    private int height(BTreeNode node){
        if(node==null) return -1; //retorna valor negativo para eliminar el nivel del nulo
        return Math.max(height(node.left), height(node.right)) + 1;
    }

   public Object min() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return min(root);
    }

    private Object min(BTreeNode node){
        if(node.left!=null)
            return min(node.left);
        return node.data;
    }


    public Object max() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return max(root);
    }

    private Object max(BTreeNode node){
        if(node.right!=null)
            return max(node.right);
        return node.data;
    }


    public String preOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return preOrder(root);
    }

    //recorre el árbol de la forma: nodo-hijo izq-hijo der
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result = node.data+" ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return  result;
    }


    public String inOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return inOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-nodo-hijo der
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+" ";
            result += inOrder(node.right);
        }
        return  result;
    }

    //para mostrar todos los elementos existentes
        public String postOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        return postOrder(root);
    }

    //recorre el árbol de la forma: hijo izq-hijo der-nodo,
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+" ";
        }
        return result;
    }

    @Override
    public String toString() {
        String result="Binary Search Tree Content:";
        try {
            result = "\nPreOrder BST: "+preOrder();
            result+= "\nInOrder BST: "+inOrder();
            result+= "\nPostOrder BST: "+postOrder() + "\n";

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //Método isBalanced para BST Test

    public boolean isBalanced () {
       return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode node) {
        if (node == null) return true;

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        int result = Math.abs(leftHeight - rightHeight);

        //Está balanceado si la diferencia es menor o igual a 1 y sus subárboles también están balanceados
        return result <= 1 && isBalanced(node.left) && isBalanced(node.right);
    }


    //------------------------------------------------------------------------------------------------------------------------------

    //METODOS ADICIONALES PARA JAVAFX

    // Método para validar que no hay ciclos ni referencias duplicadas
    public boolean validateStructure() {
        if (isEmpty()) return true;

        java.util.Set<BTreeNode> visited = new java.util.HashSet<>();
        java.util.Set<Object> values = new java.util.HashSet<>();

        return validateStructure(root, visited, values, null);
    }

    private boolean validateStructure(BTreeNode node, java.util.Set<BTreeNode> visited,
                                      java.util.Set<Object> values, BTreeNode parent) {
        if (node == null) return true;

        // Verificar si ya visitamos este nodo (indica ciclo)
        if (visited.contains(node)) {
            System.err.println("ERROR: Ciclo detectado en nodo " + node.data);
            return false;
        }

        // Verificar si ya existe este valor (indica duplicado)
        if (values.contains(node.data)) {
            System.err.println("ERROR: Valor duplicado detectado: " + node.data);
            return false;
        }

        visited.add(node);
        values.add(node.data);

        // Validar recursivamente los hijos
        return validateStructure(node.left, visited, values, node) &&
                validateStructure(node.right, visited, values, node);
    }

    // Método auxiliar para calcular altura de subárbol
    private int getSubtreeHeight(BTreeNode node) {
        if (node == null) return -1;
        return Math.max(getSubtreeHeight(node.left), getSubtreeHeight(node.right)) + 1;
    }

    // Método para debuggear la estructura del árbol
    public void printTreeStructure() {
        System.out.println("=== ESTRUCTURA DEL ÁRBOL ===");
        if (isEmpty()) {
            System.out.println("Árbol vacío");
            return;
        }
        printTreeStructure(root, "", true);
    }

    private void printTreeStructure(BTreeNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + node.data + " (" + node.path + ")");

            // Verificar referencias
            boolean hasLeft = node.left != null;
            boolean hasRight = node.right != null;

            if (hasLeft || hasRight) {
                if (hasLeft) {
                    printTreeStructure(node.left, prefix + (isLast ? "    " : "│   "), !hasRight);
                }
                if (hasRight) {
                    printTreeStructure(node.right, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }



}
