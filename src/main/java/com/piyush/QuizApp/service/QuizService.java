package com.piyush.QuizApp.service;


import com.piyush.QuizApp.entity.QuestionWrapper;
import com.piyush.QuizApp.entity.Questions;
import com.piyush.QuizApp.entity.Quiz;
import com.piyush.QuizApp.entity.Response;
import com.piyush.QuizApp.repository.QuestionRepo;
import com.piyush.QuizApp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepo questionRepo;

    // first find questions and then put in Quiz
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        try{
            Quiz quiz = new Quiz();
            List<Questions> questions = questionRepo.findRandomQuestionByCategory(category, numQ);
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizRepository.save(quiz);
            return new ResponseEntity<>("Created",HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //get Questions from DB and Convert them to Wrapper/DTO for not to return answers as well
    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {

        try{
            Optional<Quiz> quiz = quizRepository.findById(id);
            List<Questions> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for(Questions q : questionsFromDB){
                QuestionWrapper qw  = new QuestionWrapper(q.getId(),q.getQuestion(),
                        q.getQuestion(),q.getOption2(),q.getOption3(),q.getOption4());
                questionsForUser.add(qw);
            }

            return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> calculateScore(int id, List<Response> responses) {

        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Questions> questions = quiz.get().getQuestions();

        int right = 0;
        int i=0;

        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
