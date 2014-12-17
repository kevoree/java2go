
package org.kevoree.modeling.java2go.translators;

import com.intellij.psi.*;
import org.kevoree.modeling.java2go.TranslationContext;
import org.kevoree.modeling.java2go.TypeHelper;

public class ClassTranslator {

    public static void translate(PsiClass clazz, TranslationContext ctx) {
        if (clazz.isInterface()) {
            ctx.print("export interface ");
        } else {
            ctx.print("export class ");
        }
        ctx.append(clazz.getName());
        PsiTypeParameter[] typeParameters = clazz.getTypeParameters();
        if (typeParameters.length > 0) {
            ctx.append('<');
            for (int i = 0; i < typeParameters.length; i++) {
                PsiTypeParameter p = typeParameters[i];
                ctx.append(p.getName());
                PsiClassType[] extentions = p.getExtendsList().getReferencedTypes();
                if (extentions.length > 0) {
                    ctx.append(" extends ");
                    for (PsiClassType ext : extentions) {
                        ctx.append(TypeHelper.printType(ext, ctx));
                    }
                }
                if (i != typeParameters.length - 1) {
                    ctx.append(", ");
                }
            }
            ctx.append('>');
        }
        PsiClassType[] extendsList = clazz.getExtendsListTypes();
        if (extendsList.length != 0 && !clazz.isEnum()) {
            ctx.append(" extends ");
            writeTypeList(ctx, extendsList);
        }
        PsiClassType[] implementsList = clazz.getImplementsListTypes();
        if (implementsList.length != 0) {
            ctx.append(" implements ");
            writeTypeList(ctx, implementsList);
        }
        ctx.append(" {\n\n");
        printClassMembers(clazz, ctx);
        ctx.print("}\n");
        ctx.append("\n");
        printInnerClasses(clazz, ctx);
    }

    private static void printInnerClasses(PsiClass element, TranslationContext ctx) {
        PsiClass[] innerClasses = element.getInnerClasses();
        if (innerClasses.length > 0) {
            ctx.print("export module ").append(element.getName()).append(" { \n");
            ctx.increaseIdent();
            for (PsiClass innerClass : innerClasses) {
                translate(innerClass, ctx);
                ctx.append("\n");
            }
            ctx.decreaseIdent();
            ctx.print("}\n");
        }
    }

    private static void printClassMembers(PsiClass clazz, TranslationContext ctx) {
        ctx.increaseIdent();
        PsiField[] fields = clazz.getFields();
        for (PsiField field : fields) {
            FieldTranslator.translate(field, ctx);
        }
        PsiClassInitializer[] initializers = clazz.getInitializers();
        for (PsiClassInitializer initializer : initializers) {
            if (initializer.hasModifierProperty("static")) {
                ctx.print("//TODO Resolve static initializer\n");
                ctx.print("static {\n");
            } else {
                ctx.print("//TODO Resolve instance initializer\n");
                ctx.print("{\n");
            }
            ctx.increaseIdent();
            CodeBlockTranslator.translate(initializer.getBody(), ctx);
            ctx.decreaseIdent();
            ctx.print("}\n");
        }
        PsiMethod[] methods = clazz.getMethods();
        for (PsiMethod method : methods) {
            MethodTranslator.translate(method, ctx);
        }
        if (clazz.isEnum()) {
            ctx.print("public equals(other: any): boolean {\n");
            ctx.increaseIdent();
            ctx.print("return this == other;\n");
            ctx.decreaseIdent();
            ctx.print("}\n");
            ctx.print("public static _" + clazz.getName() + "VALUES : " + clazz.getName() + "[] = [\n");
            ctx.increaseIdent();
            boolean isFirst = true;
            for (int i = 0; i < clazz.getFields().length; i++) {
                if (clazz.getFields()[i].hasModifierProperty("static")) {
                    if (!isFirst) {
                        ctx.print(",");
                    } else {
                        ctx.print("");
                    }
                    ctx.append(clazz.getName());
                    ctx.append(".");
                    ctx.append(clazz.getFields()[i].getName());
                    ctx.append("\n");
                    isFirst = false;
                }
            }
            ctx.decreaseIdent();
            ctx.print("];\n");

            ctx.print("public static values():");
            ctx.append(clazz.getName());
            ctx.append("[]{\n");
            ctx.increaseIdent();
            ctx.print("return ");
            ctx.append(clazz.getName());
            ctx.append("._");
            ctx.append(clazz.getName());
            ctx.append("VALUES;\n");
            ctx.decreaseIdent();
            ctx.print("}\n");

        }
        ctx.decreaseIdent();
    }

    private static void writeTypeList(TranslationContext ctx, PsiClassType[] typeList) {
        for (int i = 0; i < typeList.length; i++) {
            PsiClassType type = typeList[i];
            ctx.append(TypeHelper.printType(type, ctx,true,true));
            if (i != typeList.length - 1) {
                ctx.append(", ");
            }
        }
    }

}
