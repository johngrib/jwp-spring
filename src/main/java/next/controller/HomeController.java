package next.controller;

import next.dao.QuestionDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import next.dao.JdbcQuestionDao;

@Controller
public class HomeController {

	private QuestionDao questionDao;

	public HomeController(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() throws Exception {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("questions", questionDao.findAll());
		return mav;
	}
}
