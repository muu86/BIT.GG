package gg.bit.utils.matchData.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gg.bit.utils.matchData.dao.ChampionWinRateDao;
import gg.bit.utils.matchData.dao.ChampionWinRateDaoMongo;
import gg.bit.utils.matchData.plot.ChampionWinRatePlot;
import gg.bit.utils.matchData.vo.ChampionWinOrLoseVo;


@WebServlet(name="ChampionWinRate", urlPatterns="/champion")
public class ChampionWinRateServlet
	extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ChampionWinRateDao dao = new ChampionWinRateDaoMongo();
		List<ChampionWinOrLoseVo> list = dao.getList();
		
		// 톰캣 현재 경로
		ServletContext app = getServletContext();
		String path = app.getRealPath("/");
		// 리눅스 서버로 옮겼을 때 경로를 확인해야 한다
		// server.xml 수정 필요
		System.out.println(path);
		path = path + "/images/";
		
		String winRateImgName = new ChampionWinRatePlot().winRateCalculator(path);
		
		req.setAttribute("list", list);
		req.setAttribute("winRateImgName", winRateImgName);
		
		RequestDispatcher rd = 
				getServletContext()
					.getRequestDispatcher("/WEB-INF/views/champion/index.jsp");
		rd.forward(req, resp);
	}
	
}
