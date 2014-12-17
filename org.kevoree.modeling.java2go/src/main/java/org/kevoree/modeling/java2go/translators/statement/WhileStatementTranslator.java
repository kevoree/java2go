
package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.PsiWhileStatement;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.translators.expression.ExpressionTranslator;

public class WhileStatementTranslator {

    public static void translate(PsiWhileStatement element, TranslationContext ctx) {
        ctx.print("while (");
        ExpressionTranslator.translate(element.getCondition(), ctx);
        ctx.append("){\n");
        ctx.increaseIdent();
        StatementTranslator.translate(element.getBody(), ctx);
        ctx.decreaseIdent();
        ctx.print("}\n");
    }

}
