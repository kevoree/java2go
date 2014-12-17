
package org.kevoree.modeling.java2typescript.translators.expression;

import com.intellij.psi.PsiConditionalExpression;
import org.kevoree.modeling.java2typescript.TranslationContext;

public class ConditionalExpressionTranslator {

    public static void translate(PsiConditionalExpression element, TranslationContext ctx) {
        ExpressionTranslator.translate(element.getCondition(), ctx);
        ctx.append(" ? ");
        ExpressionTranslator.translate(element.getThenExpression(), ctx);
        ctx.append(" : ");
        ExpressionTranslator.translate(element.getElseExpression(), ctx);
    }

}
