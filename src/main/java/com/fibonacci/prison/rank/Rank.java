package com.fibonacci.prison.rank;

import com.fibonacci.prison.utils.ColorUtils;

public enum Rank {

	A("&a[A]&f", 1, "a"),
	B("&b[B]&f", 2, "b"),
	C("&d[C]&f", 3, "c"),
	D("&e[D]&f", 4, "d"),
	E("&f[E]&f", 5, "e"),
	F("&4[F]&f", 6, "f"),
	G("&1[G]&f", 7, "g"),
	H("&2[H]&f", 8, "h"),
	I("&3[I]&f", 9, "i"),
	J("&5[J]&f", 10, "j"),
	K("&6[K]&f", 11, "k"),
	L("&9[L]&f", 12, "l"),
	M("&a[M]&f", 13, "m"),
	N("&b[N]&f", 14, "n"),
	O("&d[O]&f", 15, "o"),
	P("&e[P]&f", 16, "p"),
	Q("&f[Q]&f", 17, "q"),
	R("&4[R]&f", 18, "r"),
	S("&1[S]&f", 19, "s"),
	T("&2[T]&f", 20, "t"),
	U("&3[U]&f", 21, "u"),
	V("&5[V]&f", 22, "v"),
	W("&6[W]&f", 23, "w"),
	X("&9[X]&f", 24, "x"),
	Y("&f[Y]&f", 25, "y"),
	Z("&3[Z]&f", 26, "z"),
	FREE("&n&d[Free]&r&f", 27, "free");

	String prefix;
	int index;
	String letter;

	Rank(String prefix, int index, String letter) {
		this.prefix = ColorUtils.colorize(prefix);
		this.index = index;
		this.letter = letter;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getIndex() {
		return index;
	}

	public String getLetter() {
		return letter;
	}

	public Rank getNext() {
		if (index == 27) {
			return A;
		}
		return RankRegistry.integerRankMap.get(index + 1);
	}

	public static void loadRanks() {
		for (Rank rank : Rank.values()) {
			RankRegistry.integerRankMap.put(rank.getIndex(), rank);
		}
	}


}
