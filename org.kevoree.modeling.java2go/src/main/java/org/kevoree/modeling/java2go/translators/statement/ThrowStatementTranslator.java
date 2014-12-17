
package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.PsiThrowStatement;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.translators.expression.ExpressionTranslator;

public class ThrowStatementTranslator {

    public static void translate(PsiThrowStatement element, TranslationContext ctx) {
        ctx.print("throw ");
        ExpressionTranslator.translate(element.getException(), ctx);
        ctx.append(";\n");
    }

}
