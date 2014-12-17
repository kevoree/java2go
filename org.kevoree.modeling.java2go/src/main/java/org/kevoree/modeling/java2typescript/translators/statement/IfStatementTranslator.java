
package org.kevoree.modeling.java2typescript.translators.statement;

import com.intellij.psi.PsiIfStatement;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.translators.expression.ExpressionTranslator;

public class IfStatementTranslator {

    public static void translate(PsiIfStatement element, TranslationContext ctx) {
        ctx.print("if (");
        ExpressionTranslator.translate(element.getCondition(), ctx);
        ctx.append(") {\n");
        ctx.increaseIdent();
        StatementTranslator.translate(element.getThenBranch(), ctx);
        ctx.decreaseIdent();
        if (element.getElseElement() != null) {
            ctx.print("} else {\n");
            ctx.increaseIdent();
            StatementTranslator.translate(element.getElseBranch(), ctx);
            ctx.decreaseIdent();
            ctx.print("}\n");
        } else {
            ctx.print("}\n");
        }
    }

}
