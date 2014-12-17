
package org.kevoree.modeling.java2go.translators;

import com.intellij.psi.*;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.TypeHelper;
import org.kevoree.modeling.java2go.translators.expression.ExpressionTranslator;

public class FieldTranslator {

    public static void translate(PsiField element, TranslationContext ctx) {
        if (element instanceof PsiEnumConstant) {
            translateEnumConstant((PsiEnumConstant) element, ctx);
        } else {
            translateClassField(element, ctx);
        }
    }

    private static void translateEnumConstant(PsiEnumConstant element, TranslationContext ctx) {
        String enumName = ((PsiClass) element.getParent()).getName();
        ctx.print("public static ").append(element.getName()).append(": ").append(enumName);
        ctx.append(" = new ").append(enumName);
        ctx.append('(');
        if (element.getArgumentList() != null) {
            PsiExpression[] arguments = element.getArgumentList().getExpressions();
            for (int i = 0; i < arguments.length; i++) {
                ExpressionTranslator.translate(arguments[i], ctx);
                if (i != arguments.length - 1) {
                    ctx.append(", ");
                }
            }
        }
        ctx.append(");\n");
    }

    private static void translateClassField(PsiField element, TranslationContext ctx) {
        PsiModifierList modifierList = element.getModifierList();
        if (modifierList != null && modifierList.hasModifierProperty("private")) {
            ctx.print("private ");
        } else {
            ctx.print("public ");
        }
        if (modifierList != null && modifierList.hasModifierProperty("static")) {
            ctx.append("static ");
        }
        ctx.append(element.getName()).append(": ");
        ctx.append(TypeHelper.printType(element.getType(), ctx));
        if (element.hasInitializer()) {
            ctx.append(" = ");
            ExpressionTranslator.translate(element.getInitializer(), ctx);
            ctx.append(";\n");
        } else {
            /*
            if (TypeHelper.isPrimitiveField(element)) {
                ctx.append(" = 0");
            } else {
                ctx.append(" = null");
            }*/
            ctx.append(";\n");
        }
    }

}
