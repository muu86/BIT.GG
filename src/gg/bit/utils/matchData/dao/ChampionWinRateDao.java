package gg.bit.utils.matchData.dao;

import java.util.List;

import gg.bit.utils.matchData.vo.ChampionWinOrLoseVo;

public interface ChampionWinRateDao {
	public List<ChampionWinOrLoseVo> getList();
}
