package net.gogo98901;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import net.gogo98901.log.Log;
import net.gogo98901.pong.Pong;
import net.gogo98901.pong.handler.Handler;
import net.gogo98901.pong.page.Start;
import net.gogo98901.util.Data;

public class Bootstrap {
	private static JFrame frame;
	private static JLayeredPane pane;
	private static Pong pong;
	private static Start start;
	private static String[] arguments;

	private static boolean started = false;

	private static int width = 1000, height = 575;
	public static final String font = "assets/ARCADECLASSIC.TTF";

	public static void main(String[] args) {
		Log.info("Program Started");
		Data.setDefultLookAndFeel();
		arguments = args;
		checkArgs(arguments, false);
		try {
			frame = new JFrame();
			frame.setSize(new Dimension(width, height));
			frame.setBackground(Color.BLACK);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setIconImage(ImageIO.read(Pong.class.getClassLoader().getResourceAsStream("assets/icon.png")));
			frame.setTitle("Pong");
			Handler.addWindow();
			pane = new JLayeredPane();
			start = new Start();
			start.setSize(new Dimension(width - 6, height - 29));
			start.init();
			pane.add(start, new Integer(0), 0);
			frame.add(pane);
			pong = new Pong();
			pong.setSize(new Dimension(width - 6, height - 29));
			pane.add(pong, new Integer(1), 0);
			pong.setVisible(false);
			frame.setVisible(true);
		} catch (Exception e) {
			Log.severe(e);
			Log.stackTrace(Level.SEVERE, e);
		}
	}

	public static void start(final int players, final int maxRounds) {
		pong.setData(players, maxRounds);
		checkArgs(arguments);
		if (!started) pong.start();
		started = true;
		pong.setVisible(true);
		start.setVisible(false);

		pong.sound.playBackground();
	}

	public static void goToStart() {
		start.setVisible(true);
		pong.setVisible(false);
		if (pong != null) {
			pong.sound.stop();
			pong.resetAll();
		}
	}

	public static JFrame getFrame() {
		return frame;
	}

	private static void checkArgs(String[] args, boolean doGame) {
		if (args != null) {
			for (String arg : args) {
				if (arg.startsWith("-")) {
					arg = arg.replaceFirst("-", "");
					if (arg.equals("small")) {
						width -= 300;
						height -= 125;
					}
					if (doGame) {
						if (arg.equals("debug") || arg.equals("dev")) pong.debug = true;
						if (arg.equals("silent") || arg.equals("mute")) pong.slient = true;
					}
				}
			}
		}
	}

	private static void checkArgs(String[] args) {
		checkArgs(args, true);
	}

	public static boolean isGame() {
		if (pong == null) return false;
		return pong.isVisible();
	}
}
