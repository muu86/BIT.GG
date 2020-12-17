package gg.bit.utils.matchData.plot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gg.bit.utils.matchData.dao.ChampionWinRateDaoMongo;
import gg.bit.utils.matchData.vo.ChampionWinOrLoseVo;

public class ChampionWinRatePlot {
	public void winRateCalculator() {
		List<ChampionWinOrLoseVo> list = new ChampionWinRateDaoMongo().getList();
		
		Map<Integer, int[]> map = new HashMap<>();
		
		for (ChampionWinOrLoseVo vo: list) {
			Integer key = vo.getChampionId();
			if (map.containsKey(key)) {
				
			}
		}
	}
}
