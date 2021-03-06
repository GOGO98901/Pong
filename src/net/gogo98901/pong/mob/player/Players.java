package net.gogo98901.pong.mob.player;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import net.gogo98901.pong.Pong;

public class Players {
	private List<Player> players = new ArrayList<Player>();

	public Players(Pong pong) {
		players.add(new Player(pong, Player.keySet.LEFT, 0));
		if (pong.playerNo == 1) players.add(new PlayerAI(pong, Player.keySet.RIGHT, 1));
		if (pong.playerNo == 2) players.add(new Player(pong, Player.keySet.RIGHT, 1));
	}

	public void update() {
		for (int i = 0; i < 2; i++) {
			Player p = getPlayers()[i];
			p.update();
			if (p.getScore() == 2) p.setLevel(Player.DEFULTLEVEL - 2);
			if (p.getScore() == 4) p.setLevel(Player.DEFULTLEVEL - 4);
			if (p.getScore() == 6) p.setLevel(Player.DEFULTLEVEL - 6);
			if (p.getScore() == 10) p.setLevel(Player.DEFULTLEVEL - 8);
			if (p.getScore() == 14) p.setLevel(Player.DEFULTLEVEL - 10);
		}
	}

	public void render(Graphics g) {
		for (Player player : players) {
			player.render(g);
		}
	}

	public void reset() {
		for (Player player : players) {
			player.reset();
		}
	}

	public void setLevel(int player, int level) {
		players.get(player).setLevel(level);
	}

	public void setLevelAll(int level) {
		for (Player player : players) {
			player.setLevel(level);
		}
	}

	public Player[] getPlayers() {
		Player[] p = new Player[players.size()];
		for (int i = 0; i < p.length; i++) {
			p[i] = players.get(i);
		}
		return p;
	}

	public void renderScores(Graphics g) {
		for (Player player : players) {
			player.renderScore(g);
		}
	}

	public int getScoreTotal() {
		int score = 0;
		for (Player player : players) {
			score += player.getScore();
		}
		return score;
	}

	public void resetAll() {
		setLevelAll(Player.DEFULTLEVEL);
		for (Player player : players) {
			player.setScore(0);
		}
	}
}
