package org.example.service.impl;

import org.example.exception.IncorrectQuestionAmountException;
import org.example.model.Question;
import org.example.service.ExaminerService;
import org.example.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount <= 0 || amount > questionService.getAll().size()) {
            throw new IncorrectQuestionAmountException();
        }
        Set<Question> result = new HashSet<>();
        while (result.size()<amount) {
            result.add(questionService.getRandomQuestion());
        }
        return result;
    }
}
