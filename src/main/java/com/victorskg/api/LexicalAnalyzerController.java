package com.victorskg.api;

import com.victorskg.exception.AnalyzerException;
import com.victorskg.model.Code;
import com.victorskg.model.Token;
import com.victorskg.service.LexicalAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@RestController
@RequestMapping(path = "/analise-lexica")
public class LexicalAnalyzerController {

    private final LexicalAnalyzerService analyzerService;

    @Autowired
    public LexicalAnalyzerController(LexicalAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @PostMapping()
    public List<Token> analyze(@RequestBody Code code) throws AnalyzerException {
        return analyzerService.analyze(code.getCode());
    }
}
