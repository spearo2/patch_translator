package patch_translator.translation;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;

public class Concretizer {
    private String sourceCode;
    private String [] lines;
    private Tree ASTTree;
    private HashMap<Tree, Integer> nodeMap = new HashMap<>();

    public Concretizer(String sourceCode, Tree ASTTree) {
        System.out.println("============================================================");
        this.sourceCode = sourceCode;
        this.ASTTree = ASTTree;

        // Mapping Algorithm
        lines = sourceCode.split("\n");
        ArrayList<String> mapped = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals("") || lines[i].equals("{") || lines[i].equals("}") || lines[i].equals(";")) {
                continue;
            }
            for (Tree node : ASTTree.preOrder()) {
                if (!node.getLabel().matches("[a-zA-Z]+")) {
                    continue;
                }
                for (String s : lines[i].split("[\s,;(){}\\[\\]]")) {
                    if (s.equals(node.getLabel()) && !mapped.contains(node.toString())) {
                        nodeMap.put(node, i);
                        mapped.add(node.toString());
                        break;
                    }
                }
            }
        }
        System.out.println("Line to Node Mapping Result");
        for (Tree node : nodeMap.keySet()) {
            System.out.println(node + "   <----->   " + lines[nodeMap.get(node)] + "  #" + nodeMap.get(node));
        }
        System.out.println("============================================================");
    }

/**
 * Concretize the action to the source code
 * @param action
 * @return none (each concretized component translated and saved as a field value)
 * **/
    public void concretize(Action action) {
        switch (action.getName()) {
            case "update-node":
                String fromNameSpace = "";
                String toNameSpace = "";
                for (String s : action.toString().split("\n")) {
                    if (s.contains("replace")) { // this might not be the ultimate guideline
                        String[] tokens = s.split(" ");
                        for (int i = 0; i < tokens.length; i++) {
                            if (tokens[i].contains("by")) {
                                fromNameSpace = tokens[i - 1];
                                toNameSpace = tokens[i + 1];
                                break;
                            }
                        }
                        if (!fromNameSpace.equals("") && !toNameSpace.equals("")) {
                            break;
                        }
                    }
                }

                Tree changedNode = action.getNode();
                Tree parentNode = changedNode.getParent();
                String finalFromNameSpace = fromNameSpace;
                String finalToNameSpace = toNameSpace;
                ASTTree.preOrder().forEach(node -> {
                    if (node.getType() == changedNode.getType() &&
                            node.getLabel().equals(changedNode.getLabel()) &&
                            node.getParent().getType().equals(parentNode.getType()) &&
                            node.getParent().getLabel().equals(parentNode.getLabel())
                    ) {
                        System.out.println("Mapping node found");
                        System.out.println("============================================================");

                        for (Tree ASTnode : nodeMap.keySet()) {
                            if (ASTnode.toString().equals(node.toString())) {
                                System.out.println("Relevant line found");
                                int line = nodeMap.get(ASTnode);
                                System.out.println("\nLine #: " + line);
                                System.out.println("Before: " + lines[line]);
                                lines[line] = lines[line].replace(finalFromNameSpace, finalToNameSpace);
                                System.out.println("After: " + lines[line]);
                                break;
                            }
                        }
                    }
                });
                break;
            case "insert-tree":
                ArrayList<Tree> flatenedSubTree = new ArrayList<>();
                flatenedSubTree.add(action.getNode());
                for (Tree node : action.getNode().getDescendants()) {
                    flatenedSubTree.add(node.getParent());
                    flatenedSubTree.add(node);
                }
                System.out.println("Inserted SubTree is flatened as following: ");
                for (Tree node : flatenedSubTree) {
                    System.out.print(node + "\t");
                }



            default:
                System.out.println("Undefined Action Type " + action.getName());
                break;
        }
    }
    public void printTranslation() {
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
