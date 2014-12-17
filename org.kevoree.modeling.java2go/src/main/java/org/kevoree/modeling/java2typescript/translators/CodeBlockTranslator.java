package org.kevoree.modeling.java2typescript.translators;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiStatement;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.translators.statement.StatementTranslator;

/**
 * Created by duke on 11/6/14.
 */
public class CodeBlockTranslator {

    public static void translate(PsiCodeBlock block, TranslationContext ctx) {
        for (PsiStatement statement : block.getStatements()) {
            StatementTranslator.translate(statement, ctx);
        }
    }

}
