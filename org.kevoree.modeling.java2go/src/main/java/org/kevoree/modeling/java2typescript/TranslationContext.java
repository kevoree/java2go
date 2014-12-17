
package org.kevoree.modeling.java2typescript;

public class TranslationContext {

    private StringBuilder sb = new StringBuilder();
    private static final int identSize = 4;
    private int ident = 0;

    public void increaseIdent() {
        ident += identSize;
    }

    public void decreaseIdent() {
        ident -= identSize;
        if (ident < 0) {
            throw new IllegalStateException("Decrease ident was called more times than increase");
        }
    }

    public TranslationContext print(String str) {
        ident();
        sb.append(str);
        return this;
    }

    public TranslationContext print(char str) {
        ident();
        sb.append(str);
        return this;
    }

    public TranslationContext ident() {
        for (int i = 0; i < ident; i++) {
            sb.append(' ');
        }
        return this;
    }

    public TranslationContext append(String str) {
        sb.append(str);
        return this;
    }

    public TranslationContext append(char c) {
        sb.append(c);
        return this;
    }

    public String getText() {
        return sb.toString();
    }

    @Override
    public String toString() {
        return getText();
    }

}
