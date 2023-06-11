package CourseWork2.service.impl;

import org.example.exception.QuestionAtreadyExistsException;
import org.example.exception.QuestionNotFoundException;
import org.example.model.Question;
import org.example.service.QuestionService;
import org.example.service.impl.JavaQuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JavaQuestionServiceTest {
    private final QuestionService questionService = new JavaQuestionService();

    @BeforeEach
    public void beforeEach() {
        questionService.add(new Question("V1", "O1"));
        questionService.add(new Question("V2", "O2"));
        questionService.add(new Question("V3", "O3"));
    }

    @AfterEach
    public void afterEach() {
        new HashSet<>(questionService.getAll()).forEach(questionService::remove);
    }

    @Test
    public void add1Test() {
        int beforeCount = questionService.getAll().size();
        Question question = new Question("V4", "O4");
        assertThat(questionService.add(question))
                .isEqualTo(question)
                .isIn(questionService.getAll());
        assertThat(questionService.getAll()).hasSize(beforeCount + 1);
    }

    @Test
    public void add2Test() {
        int beforeCount = questionService.getAll().size();
        Question question = new Question("V4", "O4");

        assertThat(questionService.add("V4", "O4"))
                .isEqualTo(question)
                .isIn(questionService.getAll());
        assertThat(questionService.getAll()).hasSize(beforeCount + 1);
    }

    @Test
    public void add1NegativeTest() {
        Question question = new Question("V1", "O1");

        assertThatExceptionOfType(QuestionAtreadyExistsException.class).isThrownBy(() -> questionService.add(question));
    }

    @Test
    public void add2NegativeTest() {
        assertThatExceptionOfType(QuestionAtreadyExistsException.class).isThrownBy(() -> questionService.add("V1", "O1"));
    }

    @Test
    public void removeTest() {
        int beforeCount = questionService.getAll().size();
        Question question = new Question("V2", "O2");

        assertThat(questionService.remove(question))
                .isEqualTo(question)
                .isNotIn(questionService.getAll());
        assertThat(questionService.getAll()).hasSize(beforeCount - 1);
    }

    @Test
    public void removeNegTest() {
        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(() -> questionService.remove(new Question("V4", "O4")));
    }

    @Test
    public void getAllTest() {
        assertThat(questionService.getAll())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Question("V1", "O1"),
                        new Question("V2", "O2"),
                        new Question("V3", "O3")
                );
    }
}
