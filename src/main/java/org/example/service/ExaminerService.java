package org.example.service;

import org.example.model.Question;

import java.util.Collection;
import java.util.Collections;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}
