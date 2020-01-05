package com.victorskg.service;

import com.victorskg.exception.AnalyzerException;
import com.victorskg.model.Token;
import com.victorskg.model.TokenType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static com.victorskg.model.TokenType.Class;
import static com.victorskg.model.TokenType.Double;
import static com.victorskg.model.TokenType.Void;
import static com.victorskg.model.TokenType.*;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.compile;

@Service
public class LexicalAnalyzerService {

    private Map<TokenType, String> regEx;

    public LexicalAnalyzerService() {
        regEx = new TreeMap<>();
    }

    public List<Token> analyze(String source) throws AnalyzerException {
        Token token;
        var position = 0;
        var tokens = new ArrayList<Token>();

        do {
            token = separateToken(source, position);
            if (nonNull(token)) {
                position = token.getEnd();
                tokens.add(token);
            }
        } while (nonNull(token) && !Objects.equals(position, source.length()));

        if (position != source.length()) {
            throw new AnalyzerException(format("Errro léxico na posição %d!", position), position);
        }

        return tokens;
    }

    public List<Token> getFilteredTokens(List<Token> tokens) {
        return tokens.stream().filter(t -> !t.getTokenType().isAuxiliary()).collect(Collectors.toList());
    }

    private Token separateToken(String source, int fromIndex) {
        if (fromIndex < 0 || fromIndex >= source.length()) {
            throw new IllegalArgumentException("Index fora de alcance!");
        }
        for (var tokenType : TokenType.values()) {
            var p = compile(".{" + fromIndex + "}" + regEx.get(tokenType), DOTALL);
            var m = p.matcher(source);
            if (m.matches()) {
                var lexema = m.group(1);
                return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
            }
        }

        return null;
    }

    @PostConstruct
    private void launchRegEx() {
        regEx.put(BlockComment, "(/\\*.*?\\*/).*");
        regEx.put(LineComment, "(//(.*?)[\r$]?\n).*");
        regEx.put(WhiteSpace, "( ).*");
        regEx.put(OpenBrace, "(\\().*");
        regEx.put(CloseBrace, "(\\)).*");
        regEx.put(Semicolon, "(;).*");
        regEx.put(Comma, "(,).*");
        regEx.put(OpeningCurlyBrace, "(\\{).*");
        regEx.put(ClosingCurlyBrace, "(\\}).*");
        regEx.put(DoubleConstant, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regEx.put(IntConstant, "\\b(\\d{1,9})\\b.*");
        regEx.put(Void, "\\b(void)\\b.*");
        regEx.put(Int, "\\b(int)\\b.*");
        regEx.put(Double, "\\b(int|double)\\b.*");
        regEx.put(Tab, "(\\t).*");
        regEx.put(NewLine, "(\\n).*");
        regEx.put(Public, "\\b(public)\\b.*");
        regEx.put(Private, "\\b(private)\\b.*");
        regEx.put(False, "\\b(false)\\b.*");
        regEx.put(True, "\\b(true)\\b.*");
        regEx.put(Null, "\\b(null)\\b.*");
        regEx.put(Return, "\\b(return)\\b.*");
        regEx.put(New, "\\b(new)\\b.*");
        regEx.put(Class, "\\b(class)\\b.*");
        regEx.put(If, "\\b(if)\\b.*");
        regEx.put(Else, "\\b(else)\\b.*");
        regEx.put(While, "\\b(while)\\b.*");
        regEx.put(Static, "\\b(static)\\b.*");
        regEx.put(Point, "(\\.).*");
        regEx.put(Plus, "(\\+{1}).*");
        regEx.put(Minus, "(\\-{1}).*");
        regEx.put(Multiply, "(\\*).*");
        regEx.put(Divide, "(/).*");
        regEx.put(EqualEqual, "(==).*");
        regEx.put(Equal, "(=).*");
        regEx.put(ExclamationEqual, "(\\!=).*");
        regEx.put(Greater, "(>).*");
        regEx.put(Less, "(<).*");
        regEx.put(Identifier, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
    }
}
