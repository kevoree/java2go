package org.kevoree.modeling.java2typescript.translators.statement;

import com.intellij.psi.*;
import org.kevoree.modeling.java2typescript.TranslationContext;
import org.kevoree.modeling.java2typescript.translators.CodeBlockTranslator;

/**
 * Created by duke on 11/6/14.
 */
public class StatementTranslator {

    public static void translate(PsiStatement statement, TranslationContext ctx) {
        if (statement instanceof PsiExpressionStatement) {
            ExpressionStatementTranslator.translate((PsiExpressionStatement) statement, ctx);
        } else if (statement instanceof PsiIfStatement) {
            IfStatementTranslator.translate((PsiIfStatement) statement, ctx);
        } else if (statement instanceof PsiReturnStatement) {
            ReturnStatementTranslator.translate((PsiReturnStatement) statement, ctx);
        } else if (statement instanceof PsiWhileStatement) {
            WhileStatementTranslator.translate((PsiWhileStatement) statement, ctx);
        } else if (statement instanceof PsiBlockStatement) {
            CodeBlockTranslator.translate(((PsiBlockStatement) statement).getCodeBlock(), ctx);
        } else if (statement instanceof PsiForStatement) {
            ForStatementTranslator.translate((PsiForStatement) statement, ctx);
        } else if (statement instanceof PsiThrowStatement) {
            ThrowStatementTranslator.translate((PsiThrowStatement) statement, ctx);
        } else if (statement instanceof PsiDoWhileStatement) {
            DoWhileStatementTranslator.translate((PsiDoWhileStatement) statement, ctx);
        } else if (statement instanceof PsiBreakStatement) {
            BreakStatementTranslator.translate((PsiBreakStatement) statement, ctx);
        } else if (statement instanceof PsiTryStatement) {
            TryStatementTranslator.translate((PsiTryStatement) statement, ctx);
        } else if (statement instanceof PsiContinueStatement) {
            ContinueStatementTranslator.translate((PsiContinueStatement) statement, ctx);
        } else if (statement instanceof PsiSwitchStatement) {
            SwitchStatementTranslator.translate((PsiSwitchStatement) statement, ctx);
        } else if (statement instanceof PsiForeachStatement) {
            ForEachStatementTranslator.translate((PsiForeachStatement) statement, ctx);
        } else if (statement instanceof PsiSwitchLabelStatement) {
            SwitchLabelStatementTranslator.translate((PsiSwitchLabelStatement) statement, ctx);
        } else if (statement instanceof PsiDeclarationStatement) {
            DeclarationStatementTranslator.translate((PsiDeclarationStatement) statement, ctx);
        }
    }

}
