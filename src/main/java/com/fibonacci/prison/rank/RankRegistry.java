package com.fibonacci.prison.rank;

import java.util.HashMap;
import java.util.Map;

public class RankRegistry {

	//use for online players. Save ranks to Jedis to access offline players.
	public static Map<String, PrisonRank> getRankByUUID = new HashMap<>();

	public static Map<Integer, Rank> integerRankMap = new HashMap<>();


}
