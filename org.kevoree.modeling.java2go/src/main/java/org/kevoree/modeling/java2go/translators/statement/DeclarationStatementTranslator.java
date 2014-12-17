package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiStatement;
import org.kevoree.modeling.java2go.TranslationContext;

/**
 * Created by duke on 11/6/14.
 */
public class DeclarationStatementTranslator {

    public static void translate(PsiDeclarationStatement stmt, TranslationContext ctx) {
        for (PsiElement element1 : stmt.getDeclaredElements()) {
            if (element1 instanceof PsiStatement) {
                StatementTranslator.translate((PsiStatement) element1, ctx);
            } else if (element1 instanceof PsiLocalVariable) {
                LocalVariableTranslator.translate((PsiLocalVariable) element1, ctx);
            } else {
                System.err.println("Not managed " + element1);
            }
        }
    }

}
