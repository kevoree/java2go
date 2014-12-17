package org.kevoree.modeling.java2go;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.*;
import org.kevoree.modeling.java2go.translators.ClassTranslator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SourceTranslator {
    //private static final String baseDir = "/Users/duke/Documents/dev/dukeboard/kevoree-modeling-framework/org.kevoree.modeling.microframework/src/main/java";
    //private static final String baseDir = "/Users/duke/Documents/dev/dukeboard/kevoree-modeling-framework/org.kevoree.modeling.microframework.typescript/target/sources";
    //private static final String baseDir = "/Users/duke/IdeaProjects/kmf_mini/src/gen/java";
    private static final String baseDir = "/Users/gregory.nain/Sources/KevoreeRepos/kevoree-modeling-framework/org.kevoree.modeling.microframework/src/main/java";

    public static void main(String[] args) throws IOException {
        SourceTranslator sourceTranslator = new SourceTranslator();
        sourceTranslator.getAnalyzer().addClasspath("/Users/gregory.nain/.m2/repository/org/kevoree/modeling/org.kevoree.modeling.microframework/4.0.0-SNAPSHOT/org.kevoree.modeling.microframework-4.0.0-SNAPSHOT.jar");
        sourceTranslator.getAnalyzer().addClasspath("/Users/gregory.nain/.m2/repository/junit/junit/4.11/junit-4.11.jar");
        sourceTranslator.translateSources(baseDir, "target", "out");
    }

    private JavaAnalyzer analyzer;

    public JavaAnalyzer getAnalyzer() {
        return analyzer;
    }

    public SourceTranslator() {
        analyzer = new JavaAnalyzer();
    }

    private static final String JAVA_D_TS = "java.d.ts";
    private static final String JAVA_JS = "java.js";


    public void translateSources(String sourcePath, String outputPath, String name) throws IOException {
        File sourceFolder = new File(sourcePath);
        File targetFolder = new File(outputPath);
        if (sourceFolder.exists()) {
            if (sourceFolder.isFile()) {
                throw new IllegalArgumentException("Source path is not a directory");
            }
        } else {
            sourceFolder.mkdirs();
        }
        if (targetFolder.exists()) {
            if (targetFolder.isFile()) {
                throw new IllegalArgumentException("Target path is not a directory");
            }
        } else {
            targetFolder.mkdirs();
        }
        //copy default library
        File javaDTS = new File(targetFolder, JAVA_D_TS);
        File javaJS = new File(targetFolder, JAVA_JS);
        Files.copy(this.getClass().getClassLoader().getResourceAsStream(JAVA_D_TS), javaDTS.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(this.getClass().getClassLoader().getResourceAsStream(JAVA_JS), javaJS.toPath(), StandardCopyOption.REPLACE_EXISTING);


        TranslationContext ctx = new TranslationContext();
        PsiDirectory root = analyzer.analyze(sourceFolder);


        PsiElementVisitor localDirectoryGenerator = new PsiElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                if(element instanceof PsiJavaFile){
                    element.acceptChildren(this);
                } else if (element instanceof PsiClass) {
                    if (!((PsiClass) element).getName().startsWith("NoJs_")) {
                        ClassTranslator.translate((PsiClass) element, ctx);
                    }
                }
            }
        };
        root.acceptChildren(new PsiElementVisitor() {
            @Override
            public void visitElement(PsiElement element) {
                if (element instanceof PsiDirectory) {
                    PsiDirectory currentDir = (PsiDirectory) element;
                    if (root != currentDir.getParent()) {
                        ctx.print("export module ");
                    } else {
                        ctx.print("module ");
                    }
                    ctx.append(currentDir.getName());
                    ctx.append(" {");
                    ctx.append("\n");
                    ctx.increaseIdent();
                    element.acceptChildren(localDirectoryGenerator);
                    element.acceptChildren(this);
                    ctx.decreaseIdent();
                    ctx.print("}");
                    ctx.append("\n");
                } else {
                    element.acceptChildren(this);
                }
            }
        });
        File generatedTS = new File(targetFolder, name + ".ts");
        FileUtil.writeToFile(generatedTS, ctx.toString().getBytes());
        System.out.println("Transpile Java2TypeScript ended to " + generatedTS.getAbsolutePath());
    }

}
