
package org.kevoree.modeling.java2typescript.translators.statement;

import com.intellij.psi.PsiBreakStatement;
import org.kevoree.modeling.java2typescript.TranslationContext;

public class BreakStatementTranslator {

    public static void translate(PsiBreakStatement element, TranslationContext ctx) {
        ctx.print("break");
        if (element.getLabelIdentifier() != null) {
            ctx.append(' ');
            ctx.append(element.getLabelIdentifier().getText().trim());
        }
        ctx.append(";\n");
    }

}
