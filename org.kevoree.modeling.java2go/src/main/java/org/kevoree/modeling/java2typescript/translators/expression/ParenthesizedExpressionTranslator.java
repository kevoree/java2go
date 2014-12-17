
package org.kevoree.modeling.java2typescript.translators.expression;

import com.intellij.psi.PsiParenthesizedExpression;
import org.kevoree.modeling.java2typescript.TranslationContext;

public class ParenthesizedExpressionTranslator {

    public static void translate(PsiParenthesizedExpression element, TranslationContext ctx) {
        ctx.append('(');
        ExpressionTranslator.translate(element.getExpression(), ctx);
        ctx.append(')');
    }

}
