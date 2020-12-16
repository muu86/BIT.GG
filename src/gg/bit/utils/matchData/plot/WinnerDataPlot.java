package gg.bit.utils.matchData.plot;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gg.bit.utils.matchData.dao.WinnerDao;
import gg.bit.utils.matchData.dao.WinnerDaoMongo;
import gg.bit.utils.matchData.vo.WinnerVo;
import jep.*;

@WebServlet(name="WinnerDataPlot", urlPatterns="/winner/plot")
public class WinnerDataPlot extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<WinnerVo> list = (List<WinnerVo>) req.getAttribute("list");
		
		try {
			MainInterpreter.setSharedModulesArgv();
		} catch (JepException e1) {
			// TODO Auto-generated catch block
			System.out.println("mainInterpreter 에러 발생");
			e1.printStackTrace();
			
		}
		try (Interpreter interp = new SharedInterpreter()) {
//			interp.exec("from java.lang import System");
//			interp.exec("import sys");
//			interp.exec("sys.argv.append('')");
			interp.exec("import matplotlib.pyplot as plt");
			interp.exec("import os");
			interp.set("data", list);
			String a = (String) interp.getValue("data.get(0).getFirstBlood()");
			String b = (String) interp.getValue("str(type(data))");
			
			interp.exec("list = []");
			interp.exec("for i in range(100):\n\tlist.append(bool(data.get(i).getFirstBlood()))");
			interp.exec("data = { \"true\": list.count(True), \"false\": list.count(False) }");
			interp.exec("plt.bar(list)");
			interp.exec("plt.savefig('C:/Users/kmj/eclipse-workspace/BIT_GG/src/img/test.png')");
		} catch (JepException e) {
			System.out.println("Jep 예외 발생\n");
			e.printStackTrace();
		} 
		
	}
	
}
