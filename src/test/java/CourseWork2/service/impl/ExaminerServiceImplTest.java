package CourseWork2.service.impl;

import org.example.exception.IncorrectQuestionAmountException;
import org.example.model.Question;
import org.example.service.ExaminerService;
import org.example.service.QuestionService;
import org.example.service.impl.ExaminerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;
    @InjectMocks
    private ExaminerServiceImpl examinerService;
    private final Collection<Question> questions = Set.of(
            new Question("V1", "O1"),
            new Question("V2", "O2"),
            new Question("V3", "O3"),
            new Question("V4", "O4"),
            new Question("V5", "O5")
            );
    @Test
    public void getQuestionsNegativeTest() {
        when(questionService.getAll()).thenReturn(questions);

        assertThatExceptionOfType(IncorrectQuestionAmountException.class)
                .isThrownBy(() -> examinerService.getQuestions(-1));
        assertThatExceptionOfType(IncorrectQuestionAmountException.class)
                .isThrownBy(() -> examinerService.getQuestions(questions.size() + 1));
    }

    @Test
    public void getQuestionsTest() {
        when(questionService.getAll()).thenReturn(questions);

        when(questionService.getRandomQuestion()).thenReturn(
                new Question("V4", "O4"),
                new Question("V4", "O4"),
                new Question("V5", "O5"),
                new Question("V2", "O2")
        );

        assertThat(examinerService.getQuestions(3))
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Question("V4", "O4"),
                        new Question("V2", "O2"),
                        new Question("V5", "O5")
                );
    }
}
