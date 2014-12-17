
package org.kevoree.modeling.java2typescript.translators.expression;

import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiPolyadicExpression;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.translators.JavaTokenTranslator;

public class PolyadicExpressionTranslator {

    public static void translate(PsiPolyadicExpression element, TranslationContext ctx) {
        for (PsiExpression expression : element.getOperands()) {
            PsiJavaToken token = element.getTokenBeforeOperand(expression);
            if (token != null) {
                ctx.append(' ');
                JavaTokenTranslator.translate(token, ctx);
                ctx.append(' ');
            }
            ExpressionTranslator.translate(expression, ctx);
        }
    }
}
