
package org.kevoree.modeling.java2go.translators.statement;

import com.intellij.psi.PsiDoWhileStatement;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.translators.expression.ExpressionTranslator;

public class DoWhileStatementTranslator {

  public static void translate(PsiDoWhileStatement element, TranslationContext ctx) {
    ctx.print("do {\n");
    ctx.increaseIdent();
    StatementTranslator.translate(element.getBody(), ctx);
    ctx.decreaseIdent();
    ctx.print("} while (");
    ExpressionTranslator.translate(element.getCondition(),ctx);
    ctx.append(")\n");
  }
}
