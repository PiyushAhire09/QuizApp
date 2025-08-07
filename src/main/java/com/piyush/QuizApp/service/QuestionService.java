package com.piyush.QuizApp.service;

import com.piyush.QuizApp.entity.Questions;
import com.piyush.QuizApp.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<List<Questions>>  getAllQuestions() {
        return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK) ;
    }

    public ResponseEntity<String> addQuestion(List<Questions> questions) {
        try{
            for(Questions q1 : questions){
                questionRepo.save(q1);
            }
            return new ResponseEntity<>("Created",HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Questions> updateQuestion(int id, Questions questions) {

        try{
            Questions oldQue = questionRepo.findById(id).orElse(null);
            if(questions != null){
                oldQue.setQuestion(questions.getQuestion());
                oldQue.setCategory(questions.getCategory());
                oldQue.setDifficultyLevel(questions.getDifficultyLevel());
                oldQue.setRightAnswer(questions.getRightAnswer());
                oldQue.setOption1(questions.getOption1());
                oldQue.setOption2(questions.getOption2());
                oldQue.setOption3(questions.getOption3());
                oldQue.setOption4(questions.getOption4());
                questionRepo.save(oldQue);
            }
            return new ResponseEntity<>(oldQue,HttpStatus.CREATED) ;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
        }

    }

    public ResponseEntity<?> deleteQuestion(int id) {

        Questions que = questionRepo.findById(id).orElse(null);
        questionRepo.delete(que);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    public ResponseEntity<List<Questions>> getQuestionsByCategory(String category) {
       return new ResponseEntity<>(questionRepo.findByCategory(category),HttpStatus.OK);
    }
}
