
package org.kevoree.modeling.java2go.translators.expression;

import com.intellij.psi.PsiSuperExpression;
import org.kevoree.modeling.java2go.TranslationContext;

public class SuperExpressionTranslator {

  public static void translate(PsiSuperExpression element, TranslationContext ctx) {
    ctx.append("super");
  }

}
