package patch_translator.translation;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;

public class Concretizer {
    private String sourceCode;
    private Tree ASTTree;
    private HashMap<Tree, String> nodeMap = new HashMap<>();
    public Concretizer(String sourceCode, Tree ASTTree) {
        this.sourceCode = sourceCode;
        this.ASTTree = ASTTree;
        // this is mapping from AST node to source code line (need some heuristic here)
        String [] lines = sourceCode.split("\n");
        ArrayList<String> mapped = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals("")) {
                continue;
            }
            for (Tree node :ASTTree.getDescendants()) {
                if (lines[i].contains(node.getLabel()) && !mapped.contains(node.toString()) && !node.getLabel().equals("")) {
                    nodeMap.put(node, lines[i]);
                    mapped.add(node.toString());
                    break;
                }
            }

        }
        for (Tree node :nodeMap.keySet()) {
            System.out.println(node.toString() + "###" + nodeMap.get(node));
        }
    }

// use parent node and mapping info to apply action to donee
    public void concretize(Action action) {
        switch (action.getName()) {
            case "update-node":
                System.out.println(action.getNode());
                System.out.println(action.getNode().getParent());
                System.out.println(action);
                break;
            default:
                break;
        }
    }
}
