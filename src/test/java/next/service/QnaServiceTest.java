package next.service;

import com.google.common.collect.Lists;
import next.CannotOperateException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by johngrib on 2017. 5. 15..
 */
@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;


    private QnaService qnaService;

    @Before
    public void setup() {
        qnaService = new QnaService(questionDao, answerDao);
    }

    // 로그인 사용자와 질문한 사람이 같아야 한다.
    @Test
    public void Positive_질문한_사람과_삭제하려는_사람이_같으면_삭제할_수_있다() throws CannotOperateException {

        final User user = new User("질문하는 사람 아이디", "password", "name", "email");

        final Question question = new Question(0, user.getUserId(), "제목", "내용", new Date(), 0);

        when(questionDao.findById(question.getQuestionId())).thenReturn(question);

        qnaService.deleteQuestion(question.getQuestionId(), user);
    }

    // 로그인 사용자와 질문한 사람이 같아야 한다.
    @Test(expected = CannotOperateException.class)
    public void Negative_질문한_사람과_삭제하려는_사람이_다르면_삭제할_수_없다() throws CannotOperateException {

        final Question question = new Question(0, "질문하는 사람 아이디", "제목", "내용", new Date(), 0);
        final User otherUser = new User("삭제하는 사람 아이디", "password", "name", "email");

        when(questionDao.findById(question.getQuestionId())).thenReturn(question);

        qnaService.deleteQuestion(question.getQuestionId(), otherUser);
    }

    // 답변이 없는 경우 삭제가 가능하다.
    @Test
    public void Positive_답변이_없는_경우_삭제가_가능하다() throws CannotOperateException {
        final User user = new User("질문하는 사람 아이디", "password", "name", "email");
        final Question question = new Question(0, user.getUserId(), "제목", "내용", new Date(), 0);

        when(questionDao.findById(question.getQuestionId())).thenReturn(question);
        when(answerDao.findAllByQuestionId(question.getQuestionId())).thenReturn(Collections.EMPTY_LIST);

        qnaService.deleteQuestion(question.getQuestionId(), user);
    }

    // 답변이 없는 경우 삭제가 가능하다.
    @Test(expected = CannotOperateException.class)
    public void Negative_답변이_있다면_삭제할_수_없다() throws CannotOperateException {

        final User user = new User("질문하는 사람 아이디", "password", "name", "email");
        final Question question = new Question(0, user.getUserId(), "제목", "내용", new Date(), 0);
        final Answer answer = new Answer(0, "답변하는 사람 아이디", "내용", new Date(), question.getQuestionId());

        when(questionDao.findById(question.getQuestionId())).thenReturn(question);
        when(answerDao.findAllByQuestionId(question.getQuestionId())).thenReturn(Arrays.asList(answer));

        qnaService.deleteQuestion(question.getQuestionId(), user);
    }

    // 질문자와 답변자가 같은 경우 삭제가 가능하다.
    @Test
    public void Positive_답변이_있어도_질문자와_답변자가_같다면_삭제할_수_있다() throws CannotOperateException {
        final User user = new User("질문하는 사람 아이디", "password", "name", "email");
        final Question question = new Question(0, user.getUserId(), "제목", "내용", new Date(), 0);
        final Answer answer = new Answer(0, user.getUserId(), "내용", new Date(), question.getQuestionId());

        when(questionDao.findById(question.getQuestionId())).thenReturn(question);
        when(answerDao.findAllByQuestionId(question.getQuestionId())).thenReturn(Arrays.asList(answer));

        qnaService.deleteQuestion(question.getQuestionId(), user);
    }

    // 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
    @Test(expected = CannotOperateException.class)
    public void Negative_답변이_있을_때_질문자와_답변자가_다르면_삭제할_수_없다() throws CannotOperateException {
        final User user = new User("질문하는 사람 아이디", "password", "name", "email");
        final Question question = new Question(0, "답변하는 사람 아이디", "제목", "내용", new Date(), 0);
        final Answer answer = new Answer(0, user.getUserId(), "내용", new Date(), question.getQuestionId());

        when(questionDao.findById(question.getQuestionId())).thenReturn(question);
        when(answerDao.findAllByQuestionId(question.getQuestionId())).thenReturn(Arrays.asList(answer));

        qnaService.deleteQuestion(question.getQuestionId(), user);
    }
}