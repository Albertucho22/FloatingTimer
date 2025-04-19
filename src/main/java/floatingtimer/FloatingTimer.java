package floatingtimer;

import java.awt.Color;
import java.awt.Dimension;
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
	public static int identifier = 0;
	public static List<JLabel> labels = new ArrayList<JLabel>();
	
	public static void main(String[] args) {
		String systemDateTime = null;
		final int index = 11; // Hardcoded index of system time

		// Add base widget
		addWidget();
		
		while (true) {
			systemDateTime = LocalDateTime.now().toString();

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

	public static void addWidget() {
		JFrame frame = configureFrame();
		JLabel label = configureLabel();
		label.setName(Integer.toString(identifier));
		
		frame.add(label);
		frame.setVisible(true);
		
		labels.add(label);
		identifier++;
	}
	
	public static JFrame configureFrame() {
		JFrame frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(150, 50);
		frame.setLocation((int) (width - (width * 0.11)), (int) (height - (height * 0.15)));
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setOpacity(0.6f);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setAlwaysOnTop(true); 
		
		 // Enable dragging
        Point mousePos = new Point();
        
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	mousePos.setLocation(e.getPoint());
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
            	Point currentPos = e.getLocationOnScreen();
                frame.setLocation(currentPos.x - mousePos.x, currentPos.y - mousePos.y);
            }
        });
        
        frame.getRootPane().registerKeyboardAction(e -> System.exit(0),
                KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_DOWN_MASK),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        
        frame.getRootPane().registerKeyboardAction(e -> {
        			addWidget();
        		},
        		KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK),
        		JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        
        frame.getRootPane().registerKeyboardAction(e -> {        		
        		for (int i = 0; i < labels.size(); i++) {
        			if (labels.get(i).getName().equals(frame.getContentPane().getComponents()[0].getName())) {
        				labels.remove(i);
        			}
        		}
        		
        		frame.dispose();
        	},
                KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
       
        return frame;
	}
	
	public static JLabel configureLabel() {
		JLabel label = new JLabel("", JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Verdana", Font.BOLD, 18));
		return label;
	}	
}
