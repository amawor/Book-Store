import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScheduleFrame extends ImageFrame {
	final int FIELD_WIDTH = 25;
	private JButton back, home, course;
	private JLabel imgLabel;
	private JScrollPane scheduleImg;
	private JPanel backgroundPanel;
	private Statement statement;
	private Member member;

	public ScheduleFrame(Member member,Statement statement) {
		this.member=member;
		this.statement=statement;
		creteButton();
		createComp();
		createPanel();
		setBackgroundImage("Schedule.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Schedule");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() {
		imgLabel = loadLabelImage("Schedule2.png");
		scheduleImg = new JScrollPane(imgLabel); 
		scheduleImg.setPreferredSize(new Dimension(600, 346));
		scheduleImg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new CourseFrame(member,statement);
				close();
			}
		});
	}

	private void creteButton() {
		back = loadButtonImage("B_back3.png");
		back.setActionCommand("Back");
		home = loadButtonImage("B_home.png");
		home.setActionCommand("Home");

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Back"):
					new ProfileFrame(statement);
					close();
					break;
				case ("Home"):
					new HomeFrame(statement);
					close();
					break;
				case ("Course"):
					new CourseFrame(member,statement);
					close();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		back.addActionListener(listener);
		home.addActionListener(listener);
	}

	private void createPanel() {
		Box buttBox = new Box(BoxLayout.LINE_AXIS);
		buttBox.add(back);
		buttBox.add(Box.createVerticalStrut(180));
		buttBox.add(Box.createHorizontalStrut(500));
		buttBox.add(home);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(scheduleImg);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
