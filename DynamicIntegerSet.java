package cse214hw2;
public class DynamicIntegerSet implements DynamicSet {
    public static class Node implements PrintableNode {
        Integer data;
        Node left, right;

        Node(int x) {
            this(x, null, null);
        }

        Node(int x, Node left, Node right) {
            this.data = x;
            this.left = left;
            this.right = right;
        }

        @Override
        public String getValueAsString() {
            return data.toString();
        }

        @Override
        public PrintableNode getLeft() {
            return left;
        }

        @Override
        public PrintableNode getRight() {
            return right;
        }
    }

    Node root;

    // this method must be there exactly in this form
    public Node root() {
        return this.root;
    }

    // rest of your code for this class, including the size, contains, add, and remove methods
    @Override
    public int size() {
        if (root == null) return 0;
        else if (root.left == null && root.right == null) return 1;
        else if (root.left == null) return (size(root.right) + 1);
        else if (root.right == null) return (size(root.left) + 1);
        return (size(root.left) + size(root.right) + 1);
    }

    private int size(Node leg) {
        if (leg.left == null && leg.right == null) return 1;
        else if (leg.left == null) return (size(leg.right) + 1);
        else if (leg.right == null) return (size(leg.left) + 1);
        return (size(leg.left) + size(leg.right) + 1);
    }

    @Override
    public boolean contains(Integer x) {
        if (root.data.equals(x)) return true;
        int comp = Integer.compare(x, root.data);
        if (comp > 0) {
            if (root.right == null) {
                return false;
            }
            return contains(root.right, x);
        } else {
            if (root.left == null) {
                return false;
            }
            return contains(root.left, x);
        }
    }

    private boolean contains(Node leg, Integer x) {
        if (leg.data.equals(x)) return true;
        int comp = Integer.compare(x, leg.data);
        if (comp > 0) {
            if (leg.right == null) {
                return false;
            }
            return contains(leg.right, x);
        } else {
            if (leg.left == null) {
                return false;
            }
            return contains(leg.left, x);
        }
    }

    @Override
    public boolean add(Integer x) {
        if (root == null){
            root = new Node(x);
            return true;
        }
        if(contains(x)) return false;
        int comp = Integer.compare(x, root.data);
        if (comp > 0) {
            if (root.right == null) {
                root.right = new Node(x);
                return true;
            }
            return add(root.right, x);
        } else {
            if (root.left == null) {
                root.left = new Node(x);
                return true;
            }
            return add(root.left, x);
        }
    }

    private boolean add(Node leg, Integer x) {
        int comp = Integer.compare(x, leg.data);
        if (comp > 0) {
            if (leg.right == null) {
                leg.right = new Node(x);
                return true;
            }
            return add(leg.right, x);
        } else {
            if (leg.left == null) {
                leg.left = new Node(x);
                return true;
            }
            return add(leg.left, x);
        }
    }

    @Override
    public boolean remove(Integer x) {
        if (!contains(x)) return false;
        int comp = Integer.compare(x, root.data);
        if (comp < 0) {
            return remove(root.left, x, root);
        } else if (comp > 0) {
            return remove(root.right, x, root);
        } else {
            if (root.left == null && root.right == null) root = null;
            else if (root.right == null) root = root.left;
            else if (root.left == null) root = root.right;
            else {
                Node next = root.right;
                if(next.left != null) {
                    while (next.left.left != null) {
                        next = next.left;
                    }
                    next.left.left = root.left;
                    next.left.right = root.right;
                    root = next.left;
                    next.left = null;
                }else{
                    next.left = root.left;
                    root = next;
                }
            }
        }
        return false;
    }

    private boolean remove(Node n, Integer x, Node parent) {
        int comp = Integer.compare(x, n.data);
        int lOr = Integer.compare(x, parent.data);
        if(comp < 0){
            remove(n.left, x, n);
        }else if(comp > 0){
            remove(n.right, x, n);
        }else{
            if(n.left == null & n.right == null){
                if(lOr < 0){
                    parent.left = null;
                }else{
                    parent.right = null;
                }
            }else if(n.right == null){
                if(lOr < 0){
                    parent.left = n.left;
                }else{
                    parent.right = n.left;
                }
            }else if(n.left == null){
                if(lOr < 0){
                    parent.left = n.right;
                }else{
                    parent.right = n.right;
                }
            }else{
                Node s = n.right;
                if(s.left != null){
                    while(s.left.left != null){
                        s = s.left;
                    }
                    s.left.left = n.left;
                    s.left.right = n.right;
                    if(lOr < 0){
                        parent.left = s.left;
                    }else{
                        parent.right = s.left;
                    }
                    s.left = null;
                }else{
                    if(lOr < 0){
                        s.right = n.right;
                        parent.left = s;
                    }else{
                        s.left = n.left;
                        parent.right = s;
                    }
                }
            }
        }
        return true;
    }
    
    public static void printTree(PrintableNode node) {
        List<List<String>>  lines = new ArrayList<>();
        List<PrintableNode> level = new ArrayList<>();
        List<PrintableNode> next  = new ArrayList<>();

        level.add(node);
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getValueAsString();
                    line.add(aa);
                    if (aa.length() > widest)
                        widest = aa.length();
                    next.add(n.getLeft());
                    next.add(n.getRight());
                    if (n.getLeft() != null)
                        nn++;
                    if (n.getRight() != null)
                        nn++;
                }
            }
            if (widest%2 == 1)
                widest++;
            lines.add(line);
            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }
    
        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;
    
            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
    
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);
    
                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }
    
            // print line of numbers
            for (String f : line) {
                if (f == null) f = "";
                final float a    = perpiece / 2f - f.length() / 2f;
                int   gap1 = (int) Math.ceil(a);
                int   gap2 = (int) Math.floor(a);
    
                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
             perpiece /= 2;
        }
    }
}
