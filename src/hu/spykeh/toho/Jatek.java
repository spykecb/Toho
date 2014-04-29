package hu.spykeh.toho;

import hu.spykeh.toho.entities.PlayerMP;
import hu.spykeh.toho.gfx.Screen;
import hu.spykeh.toho.levels.Level;
import hu.spykeh.toho.net.GameClient;
import hu.spykeh.toho.net.GameServer;


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Jatek extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static boolean offline;
	public static int state = 0;
	public static Jatek jatek;
	private String gameName;
	public static int width = 400;
	public static int height = width / 16 * 10;
	private int scale = 3;
	public JFrame frame;
	public Graphics g;
	int asd = 0;
	public BufferStrategy bs;
	public Screen screen;
	public Keyboard keyboard;
	public Level level;
	
	public String jatekosNev;
	private Thread thread;
	private BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();


	private boolean running = false;
	
	public GameClient client;
	public GameServer server;

	Scanner scanner;
	public File configFile;
	public Jatek(String gameName) {
		jatek = this;
		configFile = new File("config.cfg");
		this.gameName = gameName;
		screen = new Screen(width, height);
		frame = new JFrame();
		
		frame.setIgnoreRepaint(true);
		frame.setTitle(gameName);
		setIgnoreRepaint(true);
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		keyboard = new Keyboard();
		addKeyListener(keyboard);
		level = new Level(keyboard,this);
		initFromFile();
		
	}

	public void initFromFile(){
		try {
			scanner = new Scanner(configFile);
		} catch (FileNotFoundException e) {
			System.out.println("nem talalta a filet");
		}
		scanner.findInLine("Name = ");
		jatekosNev = scanner.next();
		scanner.close();
		
	}
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game Thread");
		thread.start();

	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		level.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		if (!bs.contentsLost()) {
			bs.show();
		}

	}

	public void update() {
		keyboard.update();
		level.update();

	}

	public void run() {
		
		int fps = 0;
		int ups = 0;
		int frames = 0;
		int updates = 0;
		long totalTime = 0;
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		requestFocus();
		while (running) {
			long curTime = System.nanoTime();

			totalTime += curTime - lastTime;

			delta += (curTime - lastTime) / ns;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			while (totalTime >= 1000000000.0) {
				totalTime -= 1000000000.0;
				fps = frames;
				ups = updates;
				
				frame.setTitle(gameName + " FPS : " + fps + "  UPS : " + ups);
				frames = 0;
				updates = 0;
			}
			lastTime = System.nanoTime();

			++frames;
			render();
		}
		stop();
	}
	
}
