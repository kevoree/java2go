package org.kevoree.modeling.java2typescript;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.java2typescript.translators.ClassTranslator;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by gregory.nain on 03/12/14.
 */
public class FlatJUnitGenerator {

    public void generate(File sourceDir, File targetDir ) {
        try {


            StringBuilder sb = new StringBuilder();
            sb.append("package gentest;\n\n");
            sb.append("public class FlatJUnitTest {\n\n");
            sb.append("public void run() {\n");

            JavaAnalyzer javaAnalyzer = new JavaAnalyzer();
            PsiDirectory parsedDir = javaAnalyzer.analyze(sourceDir);
            parsedDir.acceptChildren(new PsiElementVisitor() {
                @Override
                public void visitElement(PsiElement element) {
                    if (element instanceof PsiClass) {
                        sb.append(generateTestSuite((PsiClass) element));
                    } else {
                        element.acceptChildren(this);
                    }
                }
            });


            sb.append("}\n");
            sb.append("}");

            targetDir.mkdirs();
            File generatedTS = new File(targetDir, "FlatJUnitTest.java");
            FileUtil.writeToFile(generatedTS, sb.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String instanciateClass(PsiClass clazz) {

        return "try {\n" + clazz.getQualifiedName() + " p_" + clazz.getName().toLowerCase() + " = new " +  clazz.getQualifiedName() + "();\n";
    }


    private String generateTestSuite(PsiClass clazz) {
        StringBuilder sb = new StringBuilder();
        boolean classInstanciated = false;
        for(PsiMethod method : clazz.getAllMethods()) {
            PsiAnnotation testAnnot = method.getModifierList().findAnnotation("Test");
            if(testAnnot != null) {
                if(!classInstanciated) {
                    sb.append(instanciateClass(clazz));
                    classInstanciated = true;
                }
                sb.append("p_").append(clazz.getName().toLowerCase()).append(".").append(method.getName()).append("();\n");
            }
        }
        if(classInstanciated) {
            sb.append("}catch(Exception e){\n e.printStackTrace();\n}\n");
        }

        return sb.toString();
    }


    public static void main(String[] args) {
        FlatJUnitGenerator generator = new FlatJUnitGenerator();
        generator.generate(new File("/Users/gregory.nain/Sources/KevoreeRepos/kevoree-modeling-framework/org.kevoree.modeling.microframework/src/test/java"), new File("/Users/gregory.nain/Sources/KevoreeRepos/kevoree-modeling-framework/org.kevoree.modeling.microframework.typescript/src/test/java"));
    }


}
