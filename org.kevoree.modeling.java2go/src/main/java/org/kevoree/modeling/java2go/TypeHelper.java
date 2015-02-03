
package org.kevoree.modeling.java2go;

import com.google.common.collect.ImmutableSet;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class TypeHelper {

    public static String printType(PsiType element, TranslationContext ctx) {
        return printType(element, ctx, true, false);
    }

    public static String printType(PsiType element, TranslationContext ctx, boolean withGenericParams, boolean explicitType) {
        String result = element.getPresentableText();
        if (result.equals(Integer.class.getName()) || result.equals(Integer.class.getSimpleName()) || result.equals("int")) {
            return "int";
        }
        if (result.equals(String.class.getName()) || result.equals(String.class.getSimpleName())) {
            return "string";
        }
        if (result.equals(Character.class.getName()) || result.equals(Character.class.getSimpleName()) || result.equals("char")) {
            return "char";
        }
        if (result.equals(Long.class.getName()) || result.equals(Long.class.getSimpleName()) || result.equals("long")) {
            return "int64";
        }
        if (result.equals(Byte.class.getName()) || result.equals(Byte.class.getSimpleName()) || result.equals("byte")) {
            return "byte";
        }
        if (result.equals(Float.class.getName()) || result.equals(Float.class.getSimpleName()) || result.equals("float")) {
            return "float64";
        }
        if (result.equals(Boolean.class.getName()) || result.equals(Boolean.class.getSimpleName()) || result.equals("boolean")) {
            return "bool";
        }
        if (objects.contains(result)) {
            return "interface{}";
        }


        if (element instanceof PsiPrimitiveType) {
            if (result == null || result.equals("null")) {
                System.err.println("TypeHelper::printType -> Result null with elem:" + element.toString());
            }
            return result;
        } else if (element instanceof PsiArrayType) {
            PsiArrayType typedElement = (PsiArrayType) element;
            String partialResult = printType(typedElement.getComponentType(), ctx);

            if (typedElement.getComponentType() instanceof PsiClassReferenceType) {
                PsiClass resolvedClass = ((PsiClassReferenceType) typedElement.getComponentType()).resolve();
                if (resolvedClass != null) {
                    if (isCallbackClass(resolvedClass) && !explicitType) {
                        //'{ (p: KEvent): void; }[]' is not assignable to type '(p: KEvent) => void[]'.
                        result = "{" + partialResult.replace("=>", ":") + ";}[]";
                        return result;
                    }
                }
            }
            result = partialResult + "[]";
            return result;
        } else if (element instanceof PsiClassReferenceType) {
            PsiClass resolvedClass = ((PsiClassReferenceType) element).resolve();
            if (resolvedClass != null) {
                if (isCallbackClass(resolvedClass) && !explicitType) {
                    PsiMethod method = resolvedClass.getAllMethods()[0];
                    PsiParameter[] parameters = method.getParameterList().getParameters();
                    String[] methodParameters = new String[parameters.length];
                    for (int i = 0; i < methodParameters.length; i++) {
                        PsiType parameterType = parameters[i].getType();
                        for (int j = 0; j < resolvedClass.getTypeParameters().length; j++) {
                            if (resolvedClass.getTypeParameters()[j].getName().equals(parameterType.getPresentableText())) {
                                if (((PsiClassReferenceType) element).getParameters().length <= j) {
                                    parameterType = null;
                                } else {
                                    parameterType = ((PsiClassReferenceType) element).getParameters()[j];
                                }
                            }
                        }
                        if (parameterType == null) {
                            methodParameters[i] = parameters[i].getName() + " : any";
                        } else {
                            methodParameters[i] = parameters[i].getName() + " : " + printType(parameterType, ctx);
                        }

                    }
                    result = "(" + String.join(", ", methodParameters) + ") => " + printType(method.getReturnType(), ctx);
                    return result;
                } else {
                    String qualifiedName = resolvedClass.getQualifiedName();
                    if (qualifiedName != null) {
                        result = resolvedClass.getQualifiedName();
                    }
                    if (resolvedClass.getTypeParameters().length > 0) {
                        String[] generics = new String[resolvedClass.getTypeParameters().length];
                        Arrays.fill(generics, "any");
                        if (withGenericParams) {
                            result += "<" + String.join(", ", generics) + ">";
                        }
                    }
                }
            } else {
                String tryJavaUtil = javaTypes.get(((PsiClassReferenceType) element).getClassName());
                if (tryJavaUtil != null) {
                    result = tryJavaUtil;
                }
                if (((PsiClassReferenceType) element).getParameterCount() > 0) {
                    String[] generics = new String[((PsiClassReferenceType) element).getParameterCount()];
                    PsiType[] genericTypes = ((PsiClassReferenceType) element).getParameters();
                    for (int i = 0; i < genericTypes.length; i++) {
                        generics[i] = printType(genericTypes[i], ctx);
                    }
                    if (withGenericParams) {
                        result += "<" + String.join(", ", generics) + ">";
                    }
                }
            }
        } else {
            System.err.println("TypeHelper: InstanceOf:" + element.getClass());
        }

        if (result == null || result.equals("null")) {
            System.err.println("TypeHelper::printType -> Result null with elem:" + element.toString());
        }

        return result;
    }

    public static boolean isCallbackClass(PsiClass clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.isInterface() && clazz.getAllMethods().length == 1;
    }

    public static String primitiveStaticCall(String clazz) {
        if (clazz.equals("String")) {
            return "StringUtils";
        }
        String result = javaTypes.get(clazz);
        if (result != null) {
            return result;
        }
        return clazz;
    }


    public static final HashMap<String, String> javaTypes = new HashMap<String, String>();

    static {
        javaTypes.put("Arrays", "java.util.Arrays");
        javaTypes.put("Collections", "java.util.Collections");
        javaTypes.put("Map", "java.util.Map");
        javaTypes.put("HashMap", "java.util.HashMap");
        javaTypes.put("List", "java.util.List");
        javaTypes.put("Set", "java.util.Set");
        javaTypes.put("HashSet", "java.util.HashSet");
        javaTypes.put("ArrayList", "java.util.ArrayList");
        javaTypes.put("LinkedList", "java.util.LinkedList");
        //   javaTypes.put("Assert", "org.junit.Assert");
        javaTypes.put("Random", "java.util.Random");

        javaTypes.put("Long", "java.lang.Long");
        javaTypes.put("Double", "java.lang.Double");
        javaTypes.put("Float", "java.lang.Float");
        javaTypes.put("Integer", "java.lang.Integer");
        javaTypes.put("Short", "java.lang.Short");
        javaTypes.put("StringBuilder", "java.lang.StringBuilder");

        javaTypes.put("Throwable", "java.lang.Throwable");
        javaTypes.put("Exception", "java.lang.Exception");
        javaTypes.put("RuntimeException", "java.lang.RuntimeException");
        javaTypes.put("IndexOutOfBoundsException", "java.lang.IndexOutOfBoundsException");
    }

    public static final Set<String> primitiveNumbers = ImmutableSet.of("byte", "short", "int", "long", "float", "double", "boolean");

    public static final Set<String> objectNumbers = ImmutableSet.of(
            Byte.class.getName(),
            Byte.class.getSimpleName(),
            Short.class.getName(),
            Short.class.getSimpleName(),
            Integer.class.getName(),
            Integer.class.getSimpleName(),
            Long.class.getName(),
            Long.class.getSimpleName(),
            Float.class.getName(),
            Float.class.getSimpleName(),
            Double.class.getName(),
            Double.class.getSimpleName(),
            BigInteger.class.getName(),
            BigInteger.class.getSimpleName(),
            BigDecimal.class.getName(),
            BigDecimal.class.getSimpleName()
    );

    public static final Set<String> strings = ImmutableSet.of(
            "char",
            Character.class.getName(),
            Character.class.getSimpleName(),
            String.class.getName(),
            String.class.getSimpleName()
    );

    public static final Set<String> booleans = ImmutableSet.of(
            "boolean",
            Boolean.class.getName(),
            Boolean.class.getSimpleName()
    );

    public static final Set<String> objects = ImmutableSet.of(
            Object.class.getName(),
            Object.class.getSimpleName()
    );

}
