
package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.PsiBreakStatement;
import org.kevoree.modeling.java2go.TranslationContext;

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
