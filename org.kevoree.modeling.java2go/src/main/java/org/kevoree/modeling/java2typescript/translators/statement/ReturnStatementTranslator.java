
package org.kevoree.modeling.java2typescript.translators.statement;

import com.intellij.psi.PsiReturnStatement;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.translators.expression.ExpressionTranslator;

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
