
package org.kevoree.modeling.java2go.translators.expression;

import com.intellij.psi.PsiParenthesizedExpression;
import org.kevoree.modeling.java2go.TranslationContext;

public class ParenthesizedExpressionTranslator {

    public static void translate(PsiParenthesizedExpression element, TranslationContext ctx) {
        ctx.append('(');
        ExpressionTranslator.translate(element.getExpression(), ctx);
        ctx.append(')');
    }

}
