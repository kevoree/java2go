
package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.PsiSwitchStatement;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.translators.CodeBlockTranslator;
import org.kevoree.modeling.java2go.translators.expression.ExpressionTranslator;

public class SwitchStatementTranslator {

    public static void translate(PsiSwitchStatement element, TranslationContext ctx) {
        ctx.print("switch (");
        ExpressionTranslator.translate(element.getExpression(), ctx);
        ctx.append(") {\n");
        ctx.increaseIdent();
        CodeBlockTranslator.translate(element.getBody(), ctx);
        ctx.decreaseIdent();
        ctx.print("}\n");
    }

}
