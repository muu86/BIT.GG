package gg.bit.utils.matchData.plot;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gg.bit.utils.matchData.dao.TeamDao;
import gg.bit.utils.matchData.dao.TeamDaoMongo;
import gg.bit.utils.matchData.vo.TeamVo;
import jep.*;

@WebServlet(name="WinnerDataPlot", urlPatterns="/team/plot")
public class TeamDataPlot extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TeamVo> list = (List<TeamVo>) req.getAttribute("list");
		
		String firstBloodImgName = null;
		String firstTowerImgName = null;
		String firstInhibitorImgName = null;
		String firstBaronImgName = null;
		String firstRiftHeraldImgName = null;
		String dragonKillsImgName = null;
		
		Interpreter interp = null; 
		
		try {
			interp = new SharedInterpreter();
//			interp.exec("from java.lang import System");
//			interp.exec("import sys");
//			interp.exec("sys.argv.append('')");
			interp.exec("import matplotlib.pyplot as plt");
			interp.exec("from collections import Counter");
			
			// jep pandas 에러가 발생하므로
			// sys.argv 추가해줘야 함
			interp.exec("import sys");
			interp.exec("sys.argv.append('')");
			
			// import os 로 현재 파이썬이 동작하는 path 를 확인한 결과
			// windows/system ?
			// 여기다 파일을 저장하려고 하니 당연히 관리자 권한이 없다고 안 됐다.
//			interp.exec("import os");
			
			// WinnerVo 객체 List 를 파이썬 data 에 저장
			interp.set("data", list);
			
			// value 저장할 list 생성
			interp.exec("first_blood_list = []");
			interp.exec("first_tower_list = []");
			interp.exec("first_inhibitor_list = []");
			interp.exec("first_baron_list = []");
			interp.exec("first_riftHerald_list = []");
			interp.exec("dragon_kills_list = []");
			
			// first blood list
			interp.exec("for vo in data:\n\tfirst_blood_list.append(vo.getFirstBlood())\n\tfirst_tower_list.append(vo.getFirstTower())\n\tfirst_inhibitor_list.append(vo.getFirstInhibitor())\n\tfirst_baron_list.append(vo.getFirstBaron())\n\tfirst_riftHerald_list.append(vo.getFirstRiftHerald())\n\tdragon_kills_list.append(vo.getDragonKills())");
			// first tower list
			interp.exec("for vo in data:\n\tfirst_tower_list.append(vo.getFirstTower())");
			// first inhibitor list
			interp.exec("for vo in data:\n\tfirst_inhibitor_list.append(vo.getFirstInhibitor())");
			// first baron list
			interp.exec("for vo in data:\n\tfirst_baron_list.append(vo.getFirstBaron())");
			// first riftHerald list
			interp.exec("for vo in data:\n\tfirst_riftHerald_list.append(vo.getFirstRiftHerald())");
			// dragon kills counts
			interp.exec("for vo in data:\n\tdragon_kills_list.append(vo.getDragonKills())");
			
			// True, False 만으로 구성된 데이터  (First ~~)
			interp.exec("blood_data = [ first_blood_list.count('True'), first_blood_list.count('False')]");
			interp.exec("tower_data = [ first_tower_list.count('True'), first_tower_list.count('False')]");
			interp.exec("inhibitor_data = [ first_inhibitor_list.count('True'), first_inhibitor_list.count('False')]");
			interp.exec("baron_data = [ first_baron_list.count('True'), first_baron_list.count('False')]");
			interp.exec("riftHerald_data = [ first_riftHerald_list.count('True'), first_riftHerald_list.count('False')]");
			// dragonKills 데이터는 전처리 필요
			// dragon_data 전처리 Counter 로 드래곤 처치 횟수 데이터를 만든다
			interp.exec("result = Counter(dragon_kills_list)");
			interp.exec("result = sorted(result.items())");
			interp.exec("dragon_kills_count, case_count = zip(*result)");
			
			// 파이썬 data 변수의 주소값을 이미지 이름으로 저장
			// 유형 + 주소값.png
			interp.exec("blood_img_name = 'blood_' + str(id(blood_data))");
			interp.exec("tower_img_name = 'tower_' + str(id(tower_data))");
			interp.exec("inhibitor_img_name = 'inhibitor_' + str(id(inhibitor_data))");
			interp.exec("baron_img_name = 'baron_' + str(id(baron_data))");
			interp.exec("riftHerald_img_name = 'riftHerald_' + str(id(riftHerald_data))");
			interp.exec("dragon_img_name = 'dragon_' + str(id(dragon_kills_count))");
			
			// 톰캣 현재 경로
			ServletContext app = getServletContext();
			String path = app.getRealPath("/");
			// 리눅스 서버로 옮겼을 때 경로를 확인해야 한다
			// server.xml 수정 필요
			System.out.println(path);
			path = path + "/images/";
			
			// 현재 경로를 파이썬에 저장
			interp.set("path", path);
			
			// first blood
			interp.exec("plt.figure()");
			interp.exec("plt.pie(blood_data, labels=['True', 'False'], autopct='%.1f', shadow=True)");
			interp.exec("plt.title('First Blood')");
			interp.exec("plt.savefig(path+ '%s.png' % blood_img_name)");
			interp.exec("plt.close()");
			// first tower
			interp.exec("plt.figure()");
			interp.exec("plt.pie(tower_data, labels=['True', 'False'], autopct='%.1f', shadow=True)");
			interp.exec("plt.title('First Tower')");
			interp.exec("plt.savefig(path + '%s.png' % tower_img_name)");
			interp.exec("plt.close()");
			// first inhibitor
			interp.exec("plt.figure()");
			interp.exec("plt.pie(inhibitor_data, labels=['True', 'False'], autopct='%.1f', shadow=True)");
			interp.exec("plt.title('First Inhibitor')");
			interp.exec("plt.savefig(path + '%s.png' % inhibitor_img_name)");
			interp.exec("plt.close()");
			// first baron
			interp.exec("plt.figure()");
			interp.exec("plt.pie(baron_data, labels=['True', 'False'], autopct='%.1f', shadow=True)");
			interp.exec("plt.title('First Baron')");
			interp.exec("plt.savefig(path + '%s.png' % baron_img_name)");
			interp.exec("plt.close()");
			// first riftHerald
			interp.exec("plt.figure()");
			interp.exec("plt.pie(riftHerald_data, labels=['True', 'False'], autopct='%.1f', shadow=True)");
			interp.exec("plt.title('First RiftHerald')");
			interp.exec("plt.savefig(path + '%s.png' % riftHerald_img_name)");
			interp.exec("plt.close()");
			// dragon kills
			interp.exec("plt.figure()");
			interp.exec("plt.bar(dragon_kills_count, case_count)");
			interp.exec("plt.xlabel('kills')");
			interp.exec("plt.ylabel('matches')");
			interp.exec("plt.title('Dragon Kills')");
			interp.exec("plt.savefig(path + '%s.png' % dragon_img_name)");
			interp.exec("plt.close()");
			
					
			
			// img 이름을 가져옴
			// first blood
			firstBloodImgName = (String) interp.getValue("blood_img_name");
			firstTowerImgName = (String) interp.getValue("tower_img_name");
			firstInhibitorImgName = (String) interp.getValue("inhibitor_img_name");
			firstBaronImgName = (String) interp.getValue("baron_img_name");
			firstRiftHeraldImgName = (String) interp.getValue("riftHerald_img_name");
			dragonKillsImgName = (String) interp.getValue("dragon_img_name");
			
		} catch (JepException e) {
			System.out.println("Jep 예외 발생\n");
			e.printStackTrace();
		} finally {
			try {
				interp.close();
			} catch (JepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		req.setAttribute("blood_img", firstBloodImgName);
		req.setAttribute("tower_img", firstTowerImgName);
		req.setAttribute("inhibitor_img", firstInhibitorImgName);
		req.setAttribute("baron_img", firstBaronImgName);
		req.setAttribute("riftHerald_img", firstRiftHeraldImgName);
		req.setAttribute("dragon_img", dragonKillsImgName);
		
		RequestDispatcher rd = 
				getServletContext()
					.getRequestDispatcher("/WEB-INF/views/winner/winner_plot.jsp");
		rd.forward(req, resp);
		
	}
	
}
