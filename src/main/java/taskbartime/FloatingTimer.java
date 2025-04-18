package taskbartime;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

public class FloatingTimer {
	public static int count = 0;
	public static List<JLabel> labels = new ArrayList<JLabel>();
	
	public static void main(String[] args) {
		addWindow();
        
		// Get date time from system
		String systemDateTime = null;
		int index = 0;
		
		while (true) {
			systemDateTime = LocalDateTime.now().toString();
			index = systemDateTime.indexOf("T") + 1;

			for (int i = 0; i < labels.size(); i++) {
				labels.get(i).setText(systemDateTime.substring(index, index + 8));				
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static JFrame configureWindow() {
		JFrame window = new JFrame();
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(150, 50);
		window.setLocation((int) (width - (width * 0.11)), (int) (height - (height * 0.15)));
		window.setResizable(false);
		window.setUndecorated(true);
		window.setOpacity(0.6f);
		window.getContentPane().setBackground(Color.BLACK);
		window.setAlwaysOnTop(true); 
		
		 // Enable dragging
        Point mousePos = new Point();
        
        window.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	mousePos.setLocation(e.getPoint());
            }
        });

        window.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
            	Point currentPos = e.getLocationOnScreen();
                window.setLocation(currentPos.x - mousePos.x, currentPos.y - mousePos.y);
            }
        });
        
        window.getRootPane().registerKeyboardAction(e -> System.exit(0),
                KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_DOWN_MASK),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        window.getRootPane().registerKeyboardAction(e -> {
        			addWindow();
        		},
        		KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK),
        		JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        window.getRootPane().registerKeyboardAction(e -> {        		
        		for (int i = 0; i < labels.size(); i++) {
        			if (labels.get(i).getName().equals(window.getContentPane().getComponents()[0].getName())) {
        				labels.remove(i);
        			}
        		}
        		
        		window.dispose();
        	},
                KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
       
        return window;
	}
	
	public static JLabel configureLabel() {
		JLabel label = new JLabel("", JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Verdana", Font.BOLD, 18));
		return label;
	}
	
	public static void addWindow() {
		JFrame window = configureWindow();
		JLabel label = configureLabel();
		label.setName(Integer.toString(count));
		
		window.add(label);
		window.setVisible(true);

		labels.add(label);
		count++;
	}
}
