package org.kevoree.modeling.java2typescript.translators.expression;

import com.intellij.psi.PsiClassObjectAccessExpression;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.TypeHelper;

public class ClassObjectAccessExpressionTranslator {

    public static void translate(PsiClassObjectAccessExpression element, TranslationContext ctx) {
        ctx.append(TypeHelper.printType(element.getOperand().getType(), ctx));
    }

}
