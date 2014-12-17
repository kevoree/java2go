
package org.kevoree.modeling.java2typescript.translators.expression;

import com.intellij.psi.PsiArrayAccessExpression;
import org.kevoree.modeling.java2typescript.TranslationContext;

public class ArrayAccessExpressionTranslator {

    public static void translate(PsiArrayAccessExpression element, TranslationContext ctx) {
        ExpressionTranslator.translate(element.getArrayExpression(), ctx);
        ctx.append('[');
        ExpressionTranslator.translate(element.getIndexExpression(), ctx);
        ctx.append(']');
    }

}
