package patch_translator.utils;

import com.github.gumtreediff.actions.EditScript;
import com.github.gumtreediff.actions.EditScriptGenerator;
import com.github.gumtreediff.actions.SimplifiedChawatheScriptGenerator;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.gen.SyntaxException;
import com.github.gumtreediff.gen.c.CTreeGenerator;
import com.github.gumtreediff.matchers.CompositeMatchers;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.Tree;
import com.github.gumtreediff.tree.TreeContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GumtreeRunner {
    private String srcText;
    private String dstText;
    private Tree srcTree;
    private Tree dstTree;
    private EditScript editScript;
    public GumtreeRunner(String donerSrcPath, String donerDstPath) {
        try {
            srcText = Files.readString(Paths.get(donerSrcPath));
            dstText = Files.readString(Paths.get(donerDstPath));
            editScript = CEditScriptGenerator(srcText, dstText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getDstText() { return dstText; }
    public String getSrcText() { return srcText; }
    public Tree getSrcTree() { return srcTree; }
    public Tree getDstTree() { return dstTree; }
    public EditScript getEditScript() { return editScript; }


    public EditScript CEditScriptGenerator(String srcFileSource, String dstFileSource) throws IOException, SyntaxException {
        Run.initGenerators(); // registers the available parsers
        TreeContext srcTC = new CTreeGenerator().generateFrom().string(srcFileSource);
        TreeContext dstTC = new CTreeGenerator().generateFrom().string(dstFileSource);
        EditScript actions = null;
        if (srcTC!= null && dstTC!=null) {
            srcTree = srcTC.getRoot();
            dstTree = dstTC.getRoot();
            MappingStore mappings = new CompositeMatchers.SimpleGumtree().match(srcTree, dstTree); // computes the mappings between the trees
            EditScriptGenerator editScriptGenerator = new SimplifiedChawatheScriptGenerator(); // instantiates the simplified Chawathe script generator
            actions = editScriptGenerator.computeActions(mappings); // computes the edit script
        }
        return actions;
    }


}
