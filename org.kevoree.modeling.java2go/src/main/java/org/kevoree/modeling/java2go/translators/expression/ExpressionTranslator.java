package org.kevoree.modeling.java2go.translators.expression;

import com.intellij.psi.*;
import org.kevoree.modeling.java2go.TranslationContext;

/**
 * Created by duke on 11/6/14.
 */
public class ExpressionTranslator {

    public static void translate(PsiExpression expression, TranslationContext ctx) {
        if (expression instanceof PsiLiteralExpression) {
            LiteralTranslator.translate((PsiLiteralExpression) expression, ctx);
        } else if (expression instanceof PsiNewExpression) {
            NewExpressionTranslator.translate((PsiNewExpression) expression, ctx);
        } else if (expression instanceof PsiBinaryExpression) {
            BinaryExpressionTranslator.translate((PsiBinaryExpression) expression, ctx);
        } else if (expression instanceof PsiReferenceExpression) {
            ReferenceExpressionTranslator.translate((PsiReferenceExpression) expression, ctx);
        } else if (expression instanceof PsiPrefixExpression) {
            PrefixExpressionTranslator.translate((PsiPrefixExpression) expression, ctx);
        } else if (expression instanceof PsiTypeCastExpression) {
            TypeCastExpressionTranslator.translate((PsiTypeCastExpression) expression, ctx);
        } else if (expression instanceof PsiAssignmentExpression) {
            AssignmentExpressionTranslator.translate((PsiAssignmentExpression) expression, ctx);
        } else if (expression instanceof PsiMethodCallExpression) {
            MethodCallExpressionTranslator.translate((PsiMethodCallExpression) expression, ctx);
        } else if (expression instanceof PsiThisExpression) {
            ThisExpressionTranslator.translate((PsiThisExpression) expression, ctx);
        } else if (expression instanceof PsiPolyadicExpression) {
            PolyadicExpressionTranslator.translate((PsiPolyadicExpression) expression, ctx);
        } else if (expression instanceof PsiParenthesizedExpression) {
            ParenthesizedExpressionTranslator.translate((PsiParenthesizedExpression) expression, ctx);
        } else if (expression instanceof PsiPostfixExpression) {
            PostfixExpressionTranslator.translate((PsiPostfixExpression) expression, ctx);
        } else if (expression instanceof PsiArrayAccessExpression) {
            ArrayAccessExpressionTranslator.translate((PsiArrayAccessExpression) expression, ctx);
        } else if (expression instanceof PsiArrayInitializerExpression) {
            ArrayInitializerExpressionTranslator.translate((PsiArrayInitializerExpression) expression, ctx);
        } else if (expression instanceof PsiConditionalExpression) {
            ConditionalExpressionTranslator.translate((PsiConditionalExpression) expression, ctx);
        } else if (expression instanceof PsiInstanceOfExpression) {
            InstanceOfExpressionTranslator.translate((PsiInstanceOfExpression) expression, ctx);
        } else if (expression instanceof PsiSuperExpression) {
            SuperExpressionTranslator.translate((PsiSuperExpression) expression, ctx);
        } else if (expression instanceof PsiClassObjectAccessExpression) {
            ClassObjectAccessExpressionTranslator.translate((PsiClassObjectAccessExpression) expression, ctx);
        } else {
            System.err.println("EXPR " + expression);
        }
    }

}
