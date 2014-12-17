
package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.PsiReturnStatement;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.translators.expression.ExpressionTranslator;

public class ReturnStatementTranslator {

  public static void translate(PsiReturnStatement element, TranslationContext ctx) {
    ctx.print("return");
    if (element.getReturnValue() != null) {
      ctx.append(' ');
      ExpressionTranslator.translate(element.getReturnValue(),ctx);
    }
    ctx.append(";\n");
  }

}
