
package org.kevoree.modeling.java2typescript.translators.expression;

import com.intellij.psi.PsiThisExpression;
import org.kevoree.modeling.java2typescript.TranslationContext;

public class ThisExpressionTranslator {

    public static void translate(PsiThisExpression element, TranslationContext ctx) {
        ctx.append("this");
    }

}
