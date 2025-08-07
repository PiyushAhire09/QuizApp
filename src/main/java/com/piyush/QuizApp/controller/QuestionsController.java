package com.piyush.QuizApp.controller;

import com.piyush.QuizApp.entity.Questions;
import com.piyush.QuizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/get-all-question")
    public ResponseEntity<List<Questions>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    // get by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Questions>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }


    @PostMapping("/add-question")
    public ResponseEntity<String> addQuestion(@RequestBody List<Questions> questions){
        return  questionService.addQuestion(questions);
    }

    @PutMapping("/update-question/{id}")
    public ResponseEntity<Questions> updateQuestion(@PathVariable int id , @RequestBody Questions questions){
      return  questionService.updateQuestion(id,questions);
    }

    @DeleteMapping("/delete-question/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }
}
