package gg.bit.utils.matchData.dao;

import java.util.List;

import gg.bit.utils.matchData.vo.TeamVo;

public interface TeamDao {
	
	public List<TeamVo> getList(String winnerOrLoser);
}
