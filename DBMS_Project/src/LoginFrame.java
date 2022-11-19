import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends ImageFrame {
	final int FIELD_WIDTH = 25;
	static Member MEMBER=null;
	private JTextField enterUsername, enterPassword;
	private JButton confirm, cancel;
	private JPanel buttonPanel, backgroundPanel;
	private Statement statement;

	public LoginFrame(Statement statement) {
		this.statement=statement;
		createComp();
		creteButton();
		createPanel();
		setBackgroundImage("Login.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() {
		enterUsername = new JTextField(FIELD_WIDTH);
		enterUsername.setOpaque(false);
		enterUsername.setBorder(null);
		focus();

		enterPassword = new JPasswordField(FIELD_WIDTH);
		enterPassword.setOpaque(false);
		enterPassword.setBorder(null);
		enterPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String username = enterUsername.getText();
				String password = enterPassword.getText();
				try {
					if (MEMBER.login(username, password,statement) == true) {
						close();
						new HomeFrame(statement);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				enterUsername.setText("");
				enterPassword.setText("");
				focus();
			}
		});
	}

	private void focus() {
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				enterUsername.requestFocus();
			}
		});
	}

	private void creteButton() {
		MEMBER = new Member();
		confirm = loadButtonImage("B_confirm.png");
		confirm.setActionCommand("Login");
		cancel = loadButtonImage("B_cancel.png");
		cancel.setActionCommand("Cancel");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String username = enterUsername.getText();
				String password = enterPassword.getText();
				String command = event.getActionCommand();

				switch (command) {
				case ("Login"):
					try {
						if (MEMBER.login(username, password,statement) == true) {
							close();
							new HomeFrame(statement);
							focus();
							break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					enterUsername.setText("");
					enterPassword.setText("");
					break;
				case ("Cancel"):
					close();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		confirm.addActionListener(listener);
		cancel.addActionListener(listener);
	}

	private void createPanel() {
		buttonPanel = new JPanel();
		buttonPanel.add(cancel);
		buttonPanel.add(Box.createRigidArea(new Dimension(80, 0)));
		buttonPanel.add(confirm);
		buttonPanel.setOpaque(false);

		Box userBox = new Box(BoxLayout.LINE_AXIS);
		userBox.add(Box.createHorizontalStrut(100));
		userBox.add(enterUsername);
		Box passBox = new Box(BoxLayout.LINE_AXIS);
		passBox.add(Box.createHorizontalStrut(100));
		passBox.add(enterPassword);
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createVerticalStrut(257));
		box.add(userBox);
		box.add(Box.createVerticalStrut(43));
		box.add(passBox);
		box.add(Box.createVerticalStrut(60));
		box.add(buttonPanel);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
