package gg.bit.utils.matchData.plot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import gg.bit.utils.matchData.dao.ChampionNameDaoMongo;
import gg.bit.utils.matchData.dao.ChampionWinRateDaoMongo;
import gg.bit.utils.matchData.vo.ChampionWinOrLoseVo;
import jep.*;

public class ChampionWinRatePlot {
	public String winRateCalculator(String path) {
		List<ChampionWinOrLoseVo> list = new ChampionWinRateDaoMongo().getList();
		
		Map<Integer, int[]> map = new HashMap<>();
		Map<Integer, Float> winRateMap = new HashMap<>();
		
		// HashMap
		// key = 챔피언 아이디, value = [ 0, 0] / win, lose count
		for (ChampionWinOrLoseVo vo: list) {
			Integer key = vo.getChampionId();
			if (!map.containsKey(key)) {
				map.put(key, new int[2]);
			}
			if ("True".equals(vo.getWinOrLose())) {
				map.get(key)[0]++;
			} else {
				map.get(key)[1]++;
			}
		}
		
		// map 에 저장된 데이터 반복하여
		// winRateMap 에
		// k: 챔피언 id  , v : 승률
		// 저장
		Set<Integer> keySet = map.keySet();
		List<Float> valueList = new ArrayList<>();
		
		Iterator<Integer> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			Integer key = keyIterator.next();
			int[] value = map.get(key);
			float sum = value[0] + value[1];
			float winRate = value[0] / sum * 100;
			winRateMap.put(key, winRate);
			valueList.add(winRate);
		}
		
		Map<String, Float> championName = new ChampionNameDaoMongo().checkNameById(winRateMap);
		Set<String> keySetByName = championName.keySet();
		
		List<String> keyList = new ArrayList<>(keySetByName);
		
		// 이미지 파일 이름
		String winRateImgName = null;
		
		try (Interpreter interp = new SharedInterpreter()){
			
			interp.exec("import sys");
			interp.exec("sys.argv.append('')");
			interp.exec("from java.lang import System");
			interp.exec("import seaborn as sns");
			
			interp.exec("import matplotlib.pyplot as plt");
			interp.exec("import numpy as np");
			interp.exec("import pandas as pd");
			
			interp.set("championId", keyList);
			interp.exec("System.out.println(championId)");
			interp.set("winRates", valueList);
			interp.exec("data = zip(championId, winRates)");
			interp.exec("sorted_data = sorted(data, key=lambda x: x[1], reverse=True)");
			interp.exec("x, y = zip(*sorted_data)");
			
			interp.exec("x = np.array(x)");
			interp.exec("y = np.array(y)");
			interp.exec("df = pd.DataFrame([x, y])");
			
//			interp.exec("System.out.println(type(championId))");
//			interp.exec("System.out.println(type(winRates))");
			
			interp.set("path", path);
			
			// figure size
			interp.exec("plt.figure(figsize=(15, 40))");
//			interp.exec("plt.barh(x, y)");
			interp.exec("sns.barplot(x=y, y=x, data=df)");
			interp.exec("win_rate_img_name = 'win_rate' + str(id(championId))");
			interp.exec("plt.title('Champions Win Rate')");
			interp.exec("plt.savefig(path + '%s.png' % win_rate_img_name, bbox_inches='tight')");
			interp.exec("plt.close()");
			
			winRateImgName = (String) interp.getValue("win_rate_img_name");
			
		} catch (JepException e) {
			System.out.println("JEP 예외 발생");
			e.printStackTrace();
		}
		
		return winRateImgName;
	}
}
