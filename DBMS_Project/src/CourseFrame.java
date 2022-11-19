import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CourseFrame extends ImageFrame {
	final int FIELD_WIDTH = 25;
	private JButton back, home;
	private JLabel titleLabel, teacherLabel, subLabel, bookLabel;
	private JPanel backgroundPanel;
	private Statement statement;
	private Member member;

	public CourseFrame(Member member,Statement statement) {
		this.member=member;
		this.statement=statement;
		creteButton();
		createComp();
		createPanel();
		setBackgroundImage("Course.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Course");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() {
		titleLabel = new JLabel("Statistics (2)");
		titleLabel.setFont(new Font("", Font.BOLD, 28));
		teacherLabel = new JLabel("Teacher: PT Chou");
		teacherLabel.setFont(new Font("", Font.BOLD, 20));
		subLabel = new JLabel("Books:");
		subLabel.setFont(new Font("", Font.BOLD, 20));
		bookLabel = new JLabel("(1) Business Statistics: Communicating with Numbers 4th Edition");
		bookLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new BookFrame(new Obj.Book("0","Business Statistics", "Sanjiv Jaggia, Alison Kelly ", "McGraw-Hill",
						"2021-07-15", "9781260597561", 1667, "English"),statement);
				close();
			}
		});
		bookLabel.setForeground(Color.blue);
		bookLabel.setFont(new Font("", Font.BOLD, 20));
	}

	private void creteButton() {
		back = loadButtonImage("B_back2.png");
		back.setActionCommand("Back");
		home = loadButtonImage("B_home.png");
		home.setActionCommand("Home");

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Back"):
					new ScheduleFrame(member,statement);
					close();
					break;
				case ("Home"):
					new HomeFrame(statement);
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
		buttBox.add(Box.createVerticalStrut(180));
		buttBox.add(Box.createHorizontalStrut(500));
		buttBox.add(home);
		buttBox.setAlignmentX(Component.RIGHT_ALIGNMENT);

		Box infoBox = new Box(BoxLayout.Y_AXIS);
		infoBox.add(titleLabel);
		infoBox.add(teacherLabel);
		infoBox.add(subLabel);
		infoBox.add(bookLabel);
		infoBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		Box buttBox2 = new Box(BoxLayout.LINE_AXIS);
		buttBox2.add(Box.createVerticalStrut(180));
		buttBox2.add(back);
		buttBox2.add(Box.createHorizontalStrut(250));
		buttBox2.setAlignmentX(Component.RIGHT_ALIGNMENT);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(infoBox);
		box.add(buttBox2);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
