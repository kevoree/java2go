
package org.kevoree.modeling.java2go.translators.expression;

import com.intellij.psi.PsiInstanceOfExpression;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.TypeHelper;

public class InstanceOfExpressionTranslator {

    public static void translate(PsiInstanceOfExpression element, TranslationContext ctx) {
        ExpressionTranslator.translate(element.getOperand(), ctx);
        ctx.append(" instanceof ").append(TypeHelper.printType(element.getCheckType().getType(), ctx,false,false));
    }

}
