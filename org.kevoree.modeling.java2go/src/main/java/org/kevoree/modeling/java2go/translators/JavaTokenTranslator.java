
package org.kevoree.modeling.java2go.translators;

import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.impl.source.tree.ElementType;
import org.kevoree.modeling.java2go.TranslationContext;

public class JavaTokenTranslator {

  public static void translate(PsiJavaToken element, TranslationContext ctx) {
    if (ElementType.OPERATION_BIT_SET.contains(element.getTokenType())) {
      ctx.append(element.getText());
    }
  }

}
