package patch_translator.translation;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.tree.Tree;
import patch_translator.utils.GumtreeRunner;
import patch_translator.utils.Parser;

import java.util.ArrayList;

public class Translator {
    private String DonerSrcPath;
    private String DonerDstPath;
    private String DoneePath;
    private GumtreeRunner gumtreeRunner;
    private Parser doneeParser;
    private Concretizer concretizer;
    public Translator(String DonerSrcPath, String DonerDstPath, String DoneePath) {
        this.DonerSrcPath = DonerSrcPath;
        this.DonerDstPath = DonerDstPath;
        this.DoneePath = DoneePath;
    }
    public void printScripts() {
        gumtreeRunner = new GumtreeRunner(DonerSrcPath, DonerDstPath);
        System.out.println("======================Doner Edit Script======================");
        for (Action action : gumtreeRunner.getEditScript()) {
            System.out.println(action);
        }
        System.out.println("=========================Donee AST=========================");
        doneeParser = new Parser(DoneePath);
        System.out.println(doneeParser.parse().toTreeString());
    }

    public void translate() {
        gumtreeRunner = new GumtreeRunner(DonerSrcPath, DonerDstPath);
        doneeParser = new Parser(DoneePath);
        Tree doneeTree = doneeParser.parse();
        concretizer = new Concretizer(doneeParser.getCodeText(), doneeTree);
        System.out.println(gumtreeRunner.getEditScript().size() + " actions detected");
        int count = 1;
        for (Action action : gumtreeRunner.getEditScript()) {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("Concretizing: " + action.getName() + " (" + count++ +"/"+gumtreeRunner.getEditScript().size()+ ")\n");
            concretizer.concretize(action);
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n Translation Result: \n");
        concretizer.printTranslation();
    }
}
