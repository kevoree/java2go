
package org.kevoree.modeling.java2go.translators.expression;

import com.intellij.psi.PsiLiteralExpression;
import org.kevoree.modeling.java2go.TranslationContext;

public class LiteralTranslator {

    public static void translate(PsiLiteralExpression element, TranslationContext ctx) {
        String trimmed = element.getText().trim();
        if( !trimmed.toLowerCase().equals("null") && (trimmed.toLowerCase().endsWith("l") || trimmed.toLowerCase().endsWith("d"))) {
            ctx.append(trimmed.substring(0, trimmed.length()-1));
        } else {
            ctx.append(trimmed);
        }
    }

}
