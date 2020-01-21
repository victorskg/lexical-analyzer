package com.victorskg.model;

import java.util.regex.Matcher;

import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.compile;

public enum TokenType {

    BlockComment("(/\\*.*?\\*/).*"),
    LineComment("(//(.*?)[\r$]?\n).*"),
    WhiteSpace("( ).*"),
    OpenBrace("(\\().*"),
    CloseBrace("(\\)).*"),
    Semicolon("(;).*"),
    Comma("(,).*"),
    OpeningCurlyBrace("(\\{).*"),
    ClosingCurlyBrace("(\\}).*"),
    DoubleConstant("\\b(\\d{1,9}\\.\\d{1,32})\\b.*"),
    IntConstant("\\b(\\d{1,9})\\b.*"),
    Void("\\b(void)\\b.*"),
    Int("\\b(int)\\b.*"),
    Double("\\b(int|double)\\b.*"),
    Tab("(\\t).*"),
    NewLine("(\\n).*"),
    Public("\\b(public)\\b.*"),
    Private("\\b(private)\\b.*"),
    False("\\b(false)\\b.*"),
    True("\\b(true)\\b.*"),
    Null("\\b(null)\\b.*"),
    Return("\\b(return)\\b.*"),
    New("\\b(new)\\b.*"),
    Class("\\b(class)\\b.*"),
    If("\\b(if)\\b.*"),
    Else("\\b(else)\\b.*"),
    While("\\b(while)\\b.*"),
    Static("\\b(static)\\b.*"),
    Point("(\\.).*"),
    Plus("(\\+{1}).*"),
    Minus("(\\-{1}).*"),
    Multiply("(\\*).*"),
    Divide("(/).*"),
    EqualEqual("(==).*"),
    Equal("(=).*"),
    ExclamationEqual("(\\!=).*"),
    Greater("(>).*"),
    Less("(<).*"),
    Identifier("\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");

    private String regex;

    TokenType(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(int fromIndex, String source) {
        return compile(".{" + fromIndex + "}" + this.regex, DOTALL).matcher(source);
    }

    public boolean isAuxiliary() {
        return this == BlockComment || this == LineComment || this == NewLine || this == Tab || this == WhiteSpace;
    }
}
