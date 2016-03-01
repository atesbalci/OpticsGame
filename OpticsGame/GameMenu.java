import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

class GameMenu extends JPanel {
	final GameMenu gm = this;
	
	JPanel active, dialog;
	JFrame frame;
	JLayeredPane layer;
	BufferedImage back;
	boolean[] completed;
	boolean dialogExists;
	
	public GameMenu() {
       	try {
			FileInputStream saveFile = new FileInputStream("menuSave.sav");
			ObjectInputStream load = new ObjectInputStream(saveFile);
			completed = (boolean[])load.readObject();
			load.close();
		}catch(Exception e) {
			System.out.println("Defeat!");
			completed = new boolean[10];
			for(int i = 0; i < 10; i++) {
				completed[i] = false;
			}
		}
		try {   
          	back = ImageIO.read(new File("menuBack.png"));
       	} catch (IOException ex) {}
		frame = new JFrame("Optics Game");
		frame.addWindowListener(new SaveOnClose());
		frame.setResizable(false);
		frame.setSize(new Dimension(980, 670));
		layer = frame.getLayeredPane();
		layer.add(this, Integer.valueOf(1));
		frame.setVisible(true);
		setBackground(Color.blue);
		setPreferredSize(new Dimension(980, 640));
		setSize(new Dimension(980, 640));
		setLayout(new GridBagLayout());
		navigateTo(main());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back, 0, 0, null);
	}
	
	public void navigateTo(JPanel p) {
		if(p instanceof CorePanel) {
			setLayout(new FlowLayout());
			frame.setTitle(((CorePanel)p).name + " - Optics Game");
		}
		else {
			setLayout(new GridBagLayout());
			frame.setTitle("Optics Game");
		}
		
		removeAll();
		add(p);
		active = p;
		if(dialog != null)
			removeDialog();
		updateUI();
	}
	
	public JPanel main() {
		JPanel main = new JPanel();
		main.setBackground(new Color(132, 132, 132, 125));
		main.setPreferredSize(new Dimension(200, 170));
		JButton play = new JButton("Play");
		play.setPreferredSize(new Dimension(150, 50));
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigateTo(levels());
			}
		});
		JButton editor = new JButton("Editor");
		editor.setPreferredSize(new Dimension(150, 50));
		editor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigateTo(editorOpts());
			}
		});
		JButton help = new JButton("Help");
		help.setPreferredSize(new Dimension(150, 50));
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigateTo(help());
			}
		});
		main.add(play);
		main.add(editor);
		main.add(help);
		
		return main;
	}
	
	public JPanel help() {
		JPanel help = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {   
		          	BufferedImage check = ImageIO.read(new File("hintsDone.png"));
		          	g.drawImage(check, 25, 0, null);
		       	} catch (IOException ex) {}
			}
		};
		help.setBackground(new Color(132, 132, 132, 125));
		help.setPreferredSize(new Dimension(850, 630));
		help.setLayout(new BorderLayout());
		JButton back = new JButton("Back");
		back.addActionListener(new BackAction());
		help.add("South", back);
		
		return help;
	}
	
	public JPanel levels() {
		JPanel levels = new JPanel();
		levels.setBackground(new Color(132, 132, 132, 125));
		levels.setPreferredSize(new Dimension(850, 550));
		JPanel pre = new JPanel();
		pre.setOpaque(false);
		pre.setPreferredSize(new Dimension(400, 500));
		pre.setBorder(BorderFactory.createTitledBorder("Pre-Defined Levels"));
		for(int i = 0; i < 10; i++) {
			LevelButton button = new LevelButton(i + 1, completed[i]);
			pre.add(button);
		}
		levels.add(pre);
		JPanel custom = new JPanel();
		custom.setOpaque(false);
		custom.setPreferredSize(new Dimension(400, 500));
		custom.setBorder(BorderFactory.createTitledBorder("Custom Levels"));
		final JList list = listCustoms();
		custom.add(list);
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex() > -1) {
					File[] files = (new File("custom/")).listFiles();
					File file = files[list.getSelectedIndex()];
					GamePanel gp = new GamePanel();
					gp.load(file.getPath());
					gp.addBackAction(new BackAction());
					gp.setMenu(gm);
					navigateTo(gp);
				}
			}
		});
		custom.add(load);
		levels.add(custom);
		JButton back = new JButton("Back");
		back.addActionListener(new BackAction());
		levels.add(back);
		
		return levels;
	}
	
	public JList listCustoms() {
		File[] files = (new File("custom/")).listFiles();
		String[] names = new String[files.length];
		for(int i = 0; i < files.length; i++) {
			try {
				FileInputStream file = new FileInputStream(files[i]);
				ObjectInputStream load = new ObjectInputStream(file);
				names[i] = (String)load.readObject();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		JList list = new JList(names);
		list.setPreferredSize(new Dimension(350, 400));
		
		return list;
	}
	
	public JPanel editorOpts() {
		JPanel editor = new JPanel();
		editor.setPreferredSize(new Dimension(400, 500));
		editor.setBackground(new Color(132, 132, 132, 125));
		final JList list = listCustoms();
		editor.add(list);
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File[] files = (new File("custom/")).listFiles();
				File file = files[list.getSelectedIndex()];
				EditorPanel ep = new EditorPanel();
				ep.load(file.getPath());
				ep.addBackAction(new BackAction());
				navigateTo(ep);
			}
		});
		final JTextField name = new JTextField(20);
		JButton newEditor = new JButton("New");
		newEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(name.getText().length() > 0) {
					EditorPanel ep = new EditorPanel();
					ep.name = name.getText();
					ep.addBackAction(new BackAction());
					navigateTo(ep);
				}
			}
		});
		JButton back = new JButton("Back");
		back.addActionListener(new BackAction());
		editor.add(load);
		editor.add(name);
		editor.add(newEditor);
		editor.add(back);
		
		return editor;
	}
	
	public void showDialog(JPanel p) {
		if(dialog == null) {
			dialog = p;
			layer.add(p, Integer.valueOf(2));
			if(active instanceof CorePanel)
				((CorePanel)active).disableMouse();
			updateUI();
		}
	}
	
	public void removeDialog() {
		if(dialog != null) {
			layer.remove(dialog);
			if(active instanceof CorePanel)
				((CorePanel)active).enableMouse();
			updateUI();
			dialog = null;
		}
	}
	
	public void gameOver() {
		System.out.println("Victory!");
		int i = ((GamePanel)active).levelNo - 1;
		completed[i] = new Boolean(true);
		JPanel dialog = new JPanel();
		dialog.setSize(new Dimension(300, 75));
		dialog.setLocation(490 - 150, 320 - 37);
		dialog.setBackground(new Color(132, 132, 132, 125));
		JLabel label = new JLabel("                       Level Complete!");
		label.setPreferredSize(new Dimension(300, 20));
		label.setFont(new Font("Arial", Font.BOLD, 16));
		label.setForeground(Color.white);
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigateTo(levels());
				removeDialog();
			}
		});
		dialog.add(label);
		dialog.add(back);
		if(((GamePanel)active).hasNextLevel()) {
			JButton next = new JButton("Next Level");
			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((GamePanel)active).loadNextLevel();
					removeDialog();
				}
			});
			dialog.add(next);
		}
		showDialog(dialog);
	}
	
	public void sureDialog() {
		JPanel dialog = new JPanel();
		dialog.setSize(new Dimension(300, 100));
		dialog.setLocation(490 - 150, 320 - 50);
		dialog.setBackground(new Color(132, 132, 132, 125));
		JLabel label1 = new JLabel("Are you sure?");
		JLabel label2 = new JLabel("Unsaved changes will be lost.");
		label1.setFont(new Font("Arial", Font.BOLD, 16));
		label1.setForeground(Color.white);
		label2.setFont(new Font("Arial", Font.BOLD, 16));
		label2.setForeground(Color.white);
		JButton back = new JButton("Quit");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigateTo(main());
				removeDialog();
			}
		});
		JButton stay = new JButton("Return to Game");
		stay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeDialog();
			}
		});
		dialog.add(label1);
		dialog.add(label2);
		dialog.add(stay);
		dialog.add(back);
		showDialog(dialog);
	}
	
	public class BackAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(active instanceof CorePanel) {
				sureDialog();
			}
			else
				navigateTo(main());
		}
	}
	
	public class SaveOnClose extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			try {
				FileOutputStream saveFile = new FileOutputStream("menuSave.sav");
				ObjectOutputStream save = new ObjectOutputStream(saveFile);
				save.flush();
				save.writeObject(completed);
				save.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
			System.exit(0);
		}
	}
	
	public class LevelButton extends JButton {
		boolean completed;
		
		public LevelButton(int i, Boolean completed) {
			super("" + i);
			final int b = i;
			setPreferredSize(new Dimension(100, 100));
			addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					GamePanel gp = new GamePanel();
					gp.load("preMade/" + b + ".sav");
					gp.addBackAction(new BackAction());
					gp.levelNo = b;
					gp.setMenu(gm);
					navigateTo(gp);
				}
			});
			this.completed = completed.booleanValue();
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			if(completed) {
				try {   
		          	BufferedImage check = ImageIO.read(new File("check.png"));
		          	g.drawImage(check, 0, 0, null);
		       	} catch (IOException ex) {}
			}
		}
	}
}
