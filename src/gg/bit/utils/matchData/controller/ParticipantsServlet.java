package gg.bit.utils.matchData.controller;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gg.bit.utils.matchData.dao.ParticipantsDao;
import gg.bit.utils.matchData.dao.ParticipantsDaoMongo;
import gg.bit.utils.matchData.vo.ParticipantsVo;
	// Controller 역할을 담당하는 Servlet
	// web.xml 내에 servlet 과 servlet-mapping 을 등록한 것과 동일한 효과

	@WebServlet(name="ParticipantsData", urlPatterns="/Par")
	public class ParticipantsServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
			ParticipantsDao dao = new ParticipantsDaoMongo();
			List<ParticipantsVo> list = dao.getList();
			req.setAttribute("list", list);
			RequestDispatcher rd = 
					getServletContext()
						.getRequestDispatcher("/WEB-INF/views/match_data/index.jsp");
			rd.forward(req, resp);
		}
	}
