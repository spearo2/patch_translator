package patch_translator.utils;

import com.github.gumtreediff.gen.c.CTreeGenerator;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Parser {
    private String codeText;
    private TreeContext treeContext;
    public Parser (String filePath) {
        try {
            codeText = Files.readString(Paths.get(filePath));
            treeContext = new CTreeGenerator().generateFrom().string(codeText);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCodeText() { return codeText; }
    public TreeContext getTreeContext() { return treeContext; }

    public Tree parse() {
        return treeContext.getRoot();
    }
}
