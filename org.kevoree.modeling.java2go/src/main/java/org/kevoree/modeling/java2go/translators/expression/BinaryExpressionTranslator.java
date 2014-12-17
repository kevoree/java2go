
package org.kevoree.modeling.java2go.translators.expression;

import com.intellij.psi.PsiBinaryExpression;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.translators.JavaTokenTranslator;

public class BinaryExpressionTranslator {

    public static void translate(PsiBinaryExpression element, TranslationContext ctx) {
        ExpressionTranslator.translate(element.getLOperand(), ctx);
        ctx.append(' ');
        JavaTokenTranslator.translate(element.getOperationSign(), ctx);
        ctx.append(' ');
        ExpressionTranslator.translate(element.getROperand(), ctx);
    }

}
