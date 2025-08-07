package com.piyush.QuizApp.controller;

import com.piyush.QuizApp.entity.QuestionWrapper;
import com.piyush.QuizApp.entity.Response;
import com.piyush.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                             @RequestParam int numQ,@RequestParam String title){

         return quizService.createQuiz(category,numQ,title);
    }


    // to get Quiz by id and Main thing Only bQuiz Question and its 4 option not whole Quiz entity
    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id){
      return quizService.getQuiz(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitResponse(@PathVariable int id,@RequestBody List<Response> responses){
        return quizService.calculateScore(id,responses);
    }
}
