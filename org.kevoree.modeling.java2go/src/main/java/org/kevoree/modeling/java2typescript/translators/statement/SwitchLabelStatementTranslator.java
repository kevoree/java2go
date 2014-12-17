
package org.kevoree.modeling.java2typescript.translators.statement;

import com.intellij.psi.PsiSwitchLabelStatement;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.translators.expression.ExpressionTranslator;

public class SwitchLabelStatementTranslator {

    public static void translate(PsiSwitchLabelStatement element, TranslationContext ctx) {
        if (element.isDefaultCase()) {
            ctx.print("default: \n");
        } else {
            ctx.print("case ");
            ExpressionTranslator.translate(element.getCaseValue(), ctx);
            ctx.append(": \n");
        }
    }

}
