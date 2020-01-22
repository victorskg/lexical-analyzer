package com.victorskg.service;

import com.victorskg.model.Token;
import com.victorskg.model.TokenType;
import io.vavr.control.Option;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.vavr.API.Tuple;
import static io.vavr.collection.List.ofAll;
import static java.lang.String.format;
import static java.util.List.of;

@Service
public class LexicalAnalyzerService {

    public LexicalAnalyzerService() {
    }

    public List<Token> analyze(String source) {
        var token = new Token();
        var tokens = new ArrayList<Token>();

        do {
            /*token = separateToken(source, fromIndex);
            if (nonNull(token)) {
                fromIndex = token.getEnd();
                tokens.add(token);
            }*/
            var finalToken = token;
            token = Option.of(separateToken(source, token.getEnd())).peek(tokens::add).getOrElseThrow(() -> {
                throw new IllegalArgumentException(format("Erro léxico na posição %d!", finalToken.getEnd()));
            });
        } while (token.getEnd() != source.length());

        if (token.getEnd() != source.length())
            throw new IllegalArgumentException(format("Erro léxico na posição %d!", token.getEnd()));

        return tokens;
    }

    private Token separateToken(String source, int fromIndex) {
        if (fromIndex < 0 || fromIndex >= source.length())
            throw new IllegalArgumentException("Index fora de alcance!");

        return ofAll(of(TokenType.values()))
                .map(tokenType -> Tuple(tokenType, tokenType.getMatcher(fromIndex, source)))
                .find(tuple -> tuple._2.matches())
                .map(tuple -> new Token(fromIndex, fromIndex + tuple._2.group(1).length(), tuple._2.group(1), tuple._1)).getOrNull();
    }
}
