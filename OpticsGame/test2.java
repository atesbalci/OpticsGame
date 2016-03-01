import javax.swing.*;
import java.awt.*;

public class test2 {	
	public static void main(String[] args) {
		EditorPanel ep;
		JFrame f;
		
		ep = new EditorPanel();
		f = new JFrame("Editor Mode");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(ep);
		f.pack();
		f.setVisible(true);
	}
}
