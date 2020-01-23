package com.victorskg.api;

import com.victorskg.model.Code;
import com.victorskg.model.Token;
import com.victorskg.service.ExampleService;
import com.victorskg.service.LexicalAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@RestController
@RequestMapping(path = "/analise-lexica")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LexicalAnalyzerController {

    private final ExampleService exampleService;

    private final LexicalAnalyzerService analyzerService;

    @Autowired
    public LexicalAnalyzerController(ExampleService exampleService, LexicalAnalyzerService analyzerService) {
        this.exampleService = exampleService;
        this.analyzerService = analyzerService;
    }

    @PostMapping()
    public List<Token> analyze(@RequestBody Code code) {
        return analyzerService.analyze(code.getCode());
    }

    @GetMapping()
    public List<Code> findExamples() {
        return exampleService.findAllExamples();
    }
}
