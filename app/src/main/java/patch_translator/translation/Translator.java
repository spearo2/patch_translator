package patch_translator.translation;

public class Translator {
    private String DonerSrcPath;
    private String DonerDstPath;
    private String DoneePath;
    public Translator(String DonerSrcPath, String DonerDstPath, String DoneePath) {
        this.DonerSrcPath = DonerSrcPath;
        this.DonerDstPath = DonerDstPath;
        this.DoneePath = DoneePath;
    }
    public void translate() {
        System.out.println("Doner Program Source Path: " + DonerSrcPath);
        System.out.println("Doner Program Destination Path: " + DonerDstPath);
        System.out.println("Donee Program Path: " + DoneePath);
    }
}
