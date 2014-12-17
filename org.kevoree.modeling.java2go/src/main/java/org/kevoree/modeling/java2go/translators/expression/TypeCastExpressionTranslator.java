
package org.kevoree.modeling.java2go.translators.expression;

import com.intellij.psi.PsiTypeCastExpression;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.TypeHelper;

public class TypeCastExpressionTranslator {

    public static void translate(PsiTypeCastExpression element, TranslationContext ctx) {
        ExpressionTranslator.translate(element.getOperand(), ctx);
        ctx.append(".(").append(TypeHelper.printType(element.getType(), ctx, true, false)).append(")");
    }

}
