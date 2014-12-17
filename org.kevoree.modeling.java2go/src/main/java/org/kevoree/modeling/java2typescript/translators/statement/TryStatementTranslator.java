
package org.kevoree.modeling.java2typescript.translators.statement;

import com.intellij.psi.PsiCatchSection;
import com.intellij.psi.PsiTryStatement;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.TypeHelper;
import org.kevoree.modeling.java2typescript.translators.CodeBlockTranslator;

public class TryStatementTranslator {

    private static final String EXCEPTION_VAR = "$ex$";

    public static void translate(PsiTryStatement element, TranslationContext ctx) {
        ctx.print("try {\n");
        ctx.increaseIdent();
        CodeBlockTranslator.translate(element.getTryBlock(), ctx);
        ctx.decreaseIdent();
        ctx.print("}");
        PsiCatchSection[] catchSections = element.getCatchSections();
        if (catchSections.length > 0) {
            ctx.append(" catch (").append(EXCEPTION_VAR).append(") {\n");
            ctx.increaseIdent();
            for (int i = 0; i < catchSections.length; i++) {
                PsiCatchSection catchSection = catchSections[i];
                String exceptionType = TypeHelper.printType(catchSection.getCatchType(), ctx);
                ctx.print("if (").append(EXCEPTION_VAR).append(" instanceof ").append(exceptionType).append(") {\n");
                ctx.increaseIdent();
                ctx.print("var ").append(catchSection.getParameter().getName());
                ctx.append(": ").append(exceptionType).append(" = <").append(exceptionType).append(">").append(EXCEPTION_VAR).append(";\n");
                CodeBlockTranslator.translate(catchSection.getCatchBlock(), ctx);
                ctx.decreaseIdent();
                if (i != catchSections.length - 1) {
                    ctx.print("} else ");
                } else {
                    ctx.print("}\n ");
                }
            }
            ctx.decreaseIdent();
            ctx.print("}");
        }
        if (element.getFinallyBlock() != null) {
            ctx.append(" finally {\n");

            ctx.increaseIdent();
            CodeBlockTranslator.translate(element.getFinallyBlock(), ctx);
            ctx.decreaseIdent();
            ctx.print("}");
        }
        ctx.append("\n");
    }

}
