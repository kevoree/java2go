
package org.kevoree.modeling.java2typescript.translators.statement;

import com.intellij.psi.PsiForeachStatement;
import com.intellij.psi.PsiParameter;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.TypeHelper;
import org.kevoree.modeling.java2typescript.translators.expression.ExpressionTranslator;

public class ForEachStatementTranslator {

    public static void translate(PsiForeachStatement element, TranslationContext ctx) {
        ctx.print("//TODO resolve for-each cycle\n");
        PsiParameter parameter = element.getIterationParameter();
        ctx.print("var ");
        ctx.append(parameter.getName());
        ctx.append(": ");
        ctx.append(TypeHelper.printType(parameter.getType(), ctx));
        ctx.append(";\n");
        ctx.print("for (");
        ctx.append(parameter.getName());
        ctx.append(" in ");
        ExpressionTranslator.translate(element.getIteratedValue(), ctx);
        ctx.append(") {\n");
        ctx.increaseIdent();
        StatementTranslator.translate(element.getBody(), ctx);
        ctx.decreaseIdent();
        ctx.print("}\n");
    }
}
