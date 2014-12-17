
package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.*;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.translators.expression.ExpressionTranslator;

public class ExpressionStatementTranslator {

  public static void translate(PsiExpressionStatement element, TranslationContext ctx) {

    boolean loopDeclaration = false;
    PsiElement parent = element.getParent();
    if (parent instanceof PsiLoopStatement && !((PsiLoopStatement)parent).getBody().equals(element)) {
      loopDeclaration = true;
    }
    if (!loopDeclaration) {
      ctx.print("");
    }
    ExpressionTranslator.translate(element.getExpression(),ctx);
    if (!loopDeclaration) {
      ctx.append(";\n");
    }
  }

}
