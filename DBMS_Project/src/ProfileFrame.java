import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import connection_sql.DataSetter;

public class ProfileFrame extends ImageFrame {
	final int FIELD_WIDTH = 25;
	private JLabel idLabel, usernameLabel, passLabel;
	private JTextField enterSID;
	private JButton back, home, sell, order, upgrade, schedule;
	private JPanel backgroundPanel;
	private Statement statement;
	private Member member;

	public ProfileFrame(Statement statement) {
		this.member=LoginFrame.MEMBER;
		this.statement=statement;
		creteButton();
		createComp();
		createPanel();
		setBackgroundImage("Profile.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Profile");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() {
		enterSID = new JTextField(FIELD_WIDTH);
		enterSID.setText(LoginFrame.MEMBER.getSID());
		enterSID.setOpaque(false);
		enterSID.setBorder(null);

		if (!enterSID.getText().trim().equals("")) {
			idLabel = new JLabel("Campus User");
			schedule.setVisible(true);
		} else {
			idLabel = new JLabel("Normal User");
			upgrade.setVisible(true);
		}

		usernameLabel = new JLabel(LoginFrame.MEMBER.getUsername());
		usernameLabel.setOpaque(false);
		usernameLabel.setBorder(null);

		passLabel = new JLabel(LoginFrame.MEMBER.getPassword());
		passLabel.setOpaque(false);
		passLabel.setBorder(null);
	}

	private void creteButton() {
		back = loadButtonImage("B_back3.png");
		back.setActionCommand("Back");
		home = loadButtonImage("B_home.png");
		home.setActionCommand("Home");
		sell = loadButtonImage("B_sell.png");
		sell.setActionCommand("Sell");
		order = loadButtonImage("B_order.png");
		order.setActionCommand("Order");
		upgrade = loadButtonImage("B_upgrade.png");
		upgrade.setActionCommand("Upgrade");
		upgrade.setVisible(false);
		schedule = loadButtonImage("B_schedule.png");
		schedule.setActionCommand("Schedule");
		schedule.setVisible(false);

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Back"):
					close();
					break;
				case ("Home"):
					new HomeFrame(statement);
					close();
					break;
				case ("Sell"):
					try {
						new SellFrame(member,statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					close();
					break;
				case ("Order"):
					try {
						new OrderFrame(statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ("Upgrade"):
					upgrade.setVisible(false);
					schedule.setVisible(true);
					new DataSetter(statement).upgradeMember(LoginFrame.MEMBER.getUsername(), enterSID.getText().trim());

					break;
				case ("Schedule"):
					new ScheduleFrame(member,statement);
					close();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		back.addActionListener(listener);
		home.addActionListener(listener);
		sell.addActionListener(listener);
		order.addActionListener(listener);
		upgrade.addActionListener(listener);
		schedule.addActionListener(listener);
	}

	private void createPanel() {
		Box buttBox = new Box(BoxLayout.LINE_AXIS);
		buttBox.add(back);
		buttBox.add(Box.createVerticalStrut(180));
		buttBox.add(Box.createHorizontalStrut(500));
		buttBox.add(home);

		Box idBox = new Box(BoxLayout.Y_AXIS);
		idBox.add(idLabel);
		idBox.add(Box.createVerticalStrut(36));
		idBox.add(enterSID);
		idBox.add(Box.createVerticalStrut(37));
		idBox.add(usernameLabel);
		idBox.add(Box.createVerticalStrut(38));
		idBox.add(passLabel);

		Box buttBox2 = new Box(BoxLayout.LINE_AXIS);
		buttBox2.add(sell);
		buttBox2.add(Box.createHorizontalStrut(50));
		buttBox2.add(order);
		buttBox2.add(Box.createHorizontalStrut(50));
		buttBox2.add(upgrade);
		buttBox2.add(schedule);
		buttBox2.add(Box.createHorizontalStrut(30));

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(Box.createVerticalStrut(15));
		box.add(idBox);
		box.add(Box.createVerticalStrut(80));
		box.add(buttBox2);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
