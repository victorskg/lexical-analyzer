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
import static java.util.List.of;
import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.compile;

@Service
public class LexicalAnalyzerService {

    public LexicalAnalyzerService() {
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

        if (!Objects.equals(position, source.length()))
            throw new AnalyzerException(format("Erro léxico na posição %d!", position), position);

        return tokens;
    }

    private Token separateToken(String source, int fromIndex) {
        if (fromIndex < 0 || fromIndex >= source.length())
            throw new IllegalArgumentException("Index fora de alcance!");

        /*for (var tokenType : TokenType.values()) {
            var matcher = tokenType.getMatcher(fromIndex, source);
            if (matcher.matches()) {
                var lexema = matcher.group(1);
                return new Token(fromIndex, fromIndex + lexema.length(), lexema, tokenType);
            }
        }*/

        return of(TokenType.values()).stream()
                .filter(tokenType -> tokenType.getMatcher(fromIndex, source).matches()).findFirst()
                .map(tokenType -> new Token(fromIndex, fromIndex + tokenType.getMatcher(fromIndex, source).group(1).length(), tokenType.getMatcher(fromIndex, source).group(1), tokenType)).orElse(null);
    }
}
