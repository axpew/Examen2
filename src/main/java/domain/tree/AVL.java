package domain.tree;

/* *
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * Binary Search Tree AVL (Arbol de Búsqueda Binaria AVL)
 * AVL = Arbol de busqueda binaria auto balanceado
 * */
public class AVL  {
    private BTreeNode root; //se refiere a la raiz del arbol

    public int size() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
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
            throw new TreeException("AVL Binary Search Tree is empty");
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null) return false;
        else if(util.Utility.compare(node.data, element)==0) return true;
        else if(util.Utility.compare(element, node.data)<0)
            return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }


    public void add(Object element) {
        this.root = add(root, element, "root");
    }

    private BTreeNode add(BTreeNode node, Object element, String path){
        if(node==null)
            node = new BTreeNode(element, path);
        else if(util.Utility.compare(element, node.data)<0)
            node.left = add(node.left, element, path+"/left");
        else if(util.Utility.compare(element, node.data)>0)
            node.right = add(node.right, element, path+"/right");

        //una vez agregado el nuevo nodo, debemos determinar si se requiere rebalanceo para siga siendo BST-AVL
        node = reBalance(node, element);
        return node;
    }

    private BTreeNode reBalance(BTreeNode node, Object element) {
        //debemos obtener el factor de balanceo, si es 0, -1, 1 está balanceado, si es <=-2, >=2 hay que rebalancear
        int balance = getBalanceFactor(node);

        // Caso-1. Left Left Case
        if (balance > 1 && util.Utility.compare(element, node.left.data)<0){
            node.path += "/Simple-Right-Rotate";
            return rightRotate(node);
        }

        // Caso-2. Right Right Case
        if (balance < -1 && util.Utility.compare(element, node.right.data)>0){
            node.path += "/Simple-Left-Rotate";
            return leftRotate(node);
        }

        // Caso-3. Left Right Case
        if (balance > 1 && util.Utility.compare(element, node.left.data)>0) {
            node.path += "/Double-Left-Right-Rotate";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Caso-4. Right Left Case
        if (balance < -1 && util.Utility.compare(element, node.right.data)<0) {
            node.path += "/Double-Right-Left-Rotate";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    //retorna el factor de balanceo del árbol a partir del nodo nado
    private int getBalanceFactor(BTreeNode node){
        if(node==null){
            return 0;
        }else{
            return height(node.left) - height(node.right);
        }
    }

    private BTreeNode leftRotate(BTreeNode node) {
        BTreeNode node1 = node.right;
        if (node1 != null){ //importante para evitar NullPointerException
            BTreeNode node2 = node1.left;
            //se realiza la rotacion (perform rotation)
            node1.left = node;
            node.right = node2;
        }
        return node1;
    }

    private BTreeNode rightRotate(BTreeNode node) {
        BTreeNode node1 = node.left;
        if (node1 != null) { //importante para evitar NullPointerException
            BTreeNode node2 = node1.right;
            //se realiza la rotacion (perform rotation)
            node1.right = node;
            node.left = node2;
        }
        return node1;
    }


    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
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
            throw new TreeException("AVL Binary Search Tree is empty");
        return height(root, element, 0);
    }

    //devuelve la altura de un nodo (el número de ancestros)
    private int height(BTreeNode node, Object element, int level){
        if(node==null) return 0;
        else if(util.Utility.compare(node.data, element)==0) return level;
        else return Math.max(height(node.left, element, ++level),
                    height(node.right, element, level));
    }


    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
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
            throw new TreeException("AVL Binary Search Tree is empty");
        return min(root);
    }

    private Object min(BTreeNode node){
        if(node.left!=null)
            return min(node.left);
        return node.data;
    }


    public Object max() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return max(root);
    }

    private Object max(BTreeNode node){
        if(node.right!=null)
            return max(node.right);
        return node.data;
    }


    public String preOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
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

    //recorre el árbol de la forma: nodo-hijo izq-hijo der
    private String preOrderPath(BTreeNode node){
        String result="";
        if(node!=null){
            result  = node.data+"("+node.path+")"+" ";
            result += preOrderPath(node.left);
            result += preOrderPath(node.right);
        }
        return  result;
    }


    public String inOrder() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
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
            throw new TreeException("AVL Binary Search Tree is empty");
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
        String result="AVL Binary Search Tree Content:";
        try {
            result = "\nPreOrder AVL: "+preOrderPath(root);
            result+= "\nPreOrder AVL: "+preOrder();
            result+= "\nInOrder AVL: "+inOrder();
            result+= "\nPostOrder AVL: "+postOrder() + "\n";

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // NUEVOS MÉTODOS IMPLEMENTADOS

    // Método para verificar si el árbol está balanceado
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode node) {
        if (node == null) {
            return true; // Un árbol vacío está balanceado
        }

        // Obtenemos el factor de balance para este nodo
        int balanceFactor = getBalanceFactor(node);

        // Si el factor de balance está fuera del rango [-1,1], el árbol no está balanceado
        if (balanceFactor < -1 || balanceFactor > 1) {
            return false;
        }

        // Verificamos recursivamente los subárboles
        return isBalanced(node.left) && isBalanced(node.right);
    }

    // Método para rebalancear el árbol después de eliminar elementos
    public void reBalanceTree() {
        if (!isEmpty()) {
            root = reBalanceTree(root);
        }
    }

    private BTreeNode reBalanceTree(BTreeNode node) {
        if (node == null) {
            return null;
        }

        // Rebalanceamos los subárboles
        node.left = reBalanceTree(node.left);
        node.right = reBalanceTree(node.right);

        // Obtenemos el factor de balanceo de este nodo
        int balance = getBalanceFactor(node);

        // Si el nodo está desbalanceado, hay 4 casos posibles

        // Caso Left Left
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // Caso Left Right
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Caso Right Right
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // Caso Right Left
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Método modificado para mantener el balanceo después de eliminar
    public void removeWithRebalance(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        root = removeWithRebalance(root, element);
    }

    private BTreeNode removeWithRebalance(BTreeNode node, Object element) throws TreeException {
        // Primero realizamos la eliminación estándar
        if(node == null) return null;

        if(util.Utility.compare(element, node.data) < 0)
            node.left = removeWithRebalance(node.left, element);
        else if(util.Utility.compare(element, node.data) > 0)
            node.right = removeWithRebalance(node.right, element);
        else {
            // Casos 1 y 2: Nodo con un hijo o sin hijos
            if(node.left == null || node.right == null) {
                BTreeNode temp = (node.left != null) ? node.left : node.right;

                // Sin hijos
                if(temp == null)
                    return null;
                else // Un hijo
                    node = temp;
            } else {
                // Caso 3: Nodo con dos hijos
                Object value = min(node.right);
                node.data = value;
                node.right = removeWithRebalance(node.right, value);
            }
        }

        // Si el árbol tenía solo un nodo, retornamos
        if(node == null)
            return null;

        // Obtenemos el factor de balanceo
        int balance = getBalanceFactor(node);

        // Si está desbalanceado, rebalanceamos

        // Caso Left Left
        if (balance > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);

        // Caso Left Right
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Caso Right Right
        if (balance < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);

        // Caso Right Left
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Métodos requeridos en el punto k

    public Object father(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return father(root, element);
    }

    private Object father(BTreeNode node, Object element) {
        if(node == null) return null;

        // Si alguno de los hijos es igual al elemento, este nodo es el padre
        if ((node.left != null && util.Utility.compare(node.left.data, element) == 0) ||
                (node.right != null && util.Utility.compare(node.right.data, element) == 0)) {
            return node.data;
        }

        // Buscar en el subárbol izquierdo
        Object leftResult = null;
        if(util.Utility.compare(element, node.data) < 0 || node.right == null)
            leftResult = father(node.left, element);

        if(leftResult != null)
            return leftResult;

        // Buscar en el subárbol derecho
        return father(node.right, element);
    }

    public Object brother(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return brother(root, element);
    }

    private Object brother(BTreeNode node, Object element) {
        if(node == null) return null;

        // Si este nodo tiene ambos hijos
        if(node.left != null && node.right != null) {
            // Si el hijo izquierdo es el elemento, el hermano es el hijo derecho
            if(util.Utility.compare(node.left.data, element) == 0)
                return node.right.data;

            // Si el hijo derecho es el elemento, el hermano es el hijo izquierdo
            if(util.Utility.compare(node.right.data, element) == 0)
                return node.left.data;
        }

        // Buscar en subárboles
        Object result = null;
        if(util.Utility.compare(element, node.data) < 0)
            result = brother(node.left, element);
        else if(util.Utility.compare(element, node.data) > 0)
            result = brother(node.right, element);

        return result;
    }

    public String children(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return children(root, element);
    }

    private String children(BTreeNode node, Object element) {
        if(node == null) return "No children found";

        // Si encontramos el elemento
        if(util.Utility.compare(node.data, element) == 0) {
            if(node.left == null && node.right == null)
                return "No children";
            else if(node.left != null && node.right == null)
                return "Left child: " + node.left.data;
            else if(node.left == null && node.right != null)
                return "Right child: " + node.right.data;
            else
                return "Left child: " + node.left.data + ", Right child: " + node.right.data;
        }

        // Buscar en subárboles
        if(util.Utility.compare(element, node.data) < 0)
            return children(node.left, element);
        else
            return children(node.right, element);
    }
}