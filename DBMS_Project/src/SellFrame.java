import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxUI;

import connection_sql.DataGetter;
import connection_sql.DataSetter;

public class SellFrame extends ImageFrame {
	final int FIELD_WIDTH = 25;
	private double[] discounts = { 1, 0.7, 0.6, 0.55 };
	private double price,d=1;
	private JLabel priceLabel;
	private JTextField enterISBN;
	private JComboBox<String> statusCombo;
	private JButton log_out, profile, cart, confirm, cancel;
	private JPanel buttonPanel, backgroundPanel;
	private Statement statement;
	private Member member;

	public SellFrame(Member member,Statement statement) throws SQLException {
		this.statement=statement;
		this.member=member;
		createComp();
		creteButton();
		createPanel();
		setBackgroundImage("Sell.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Sell");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() throws SQLException {
		statusCombo = new JComboBox<String>();
		statusCombo.addItem("Please select the status of the book");
		statusCombo.addItem("Status: New");
		statusCombo.addItem("Status: Great");
		statusCombo.addItem("Status: Medium");
		statusCombo.addItem("Status: Bad");
		statusCombo.setMaximumSize(new java.awt.Dimension(340, 25));
		statusCombo.setOpaque(false);
		statusCombo.setBorder(null);
		statusCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enterISBN.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Please input ISBN first!", "Notification",JOptionPane.WARNING_MESSAGE);
				}else {
					try {
						queryPrice();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String status = statusCombo.getSelectedItem().toString();
					String val = "";
					switch (status) {
					case "Status: New":
						d=discounts[0];
						val = String.format("%.2f", (price * discounts[0]));
						priceLabel.setText("Price: NT$" + val);
						break;
					case "Status: Great":
						d=discounts[1];
						val = String.format("%.2f", (price * discounts[1]));
						priceLabel.setText("Price: NT$" + val);
						break;
					case "Status: Medium":
						d=discounts[2];
						val = String.format("%.2f", (price * discounts[2]));
						priceLabel.setText("Price: NT$" + val);
						break;
					case "Status: Bad":
						d=discounts[3];
						val = String.format("%.2f", (price * discounts[3]));
						priceLabel.setText("Price: NT$" + val);
						break;
				}
				}
			}
		});
		statusCombo.setUI(new BasicComboBoxUI() {
			public void installUI(JComponent comboBox) {
				super.installUI(comboBox);
				listBox.setForeground(Color.BLACK);
				listBox.setBackground(new Color(243, 242, 241, 255));
				listBox.setSelectionBackground(new Color(243, 242, 241, 255));
				listBox.setSelectionForeground(Color.BLACK);
			}

			protected JButton createArrowButton() {
				return null;
			}
		});

		enterISBN = new JTextField(FIELD_WIDTH);
		enterISBN.setMaximumSize(new java.awt.Dimension(300, 20));
		enterISBN.setHorizontalAlignment(JTextField.CENTER);
		enterISBN.setOpaque(false);
		enterISBN.setBorder(null);
		focus();
		

		priceLabel = new JLabel("Price: NT$");
		priceLabel.setOpaque(false);
		priceLabel.setBorder(null);
	}

	private void queryPrice() throws SQLException {
		price = 0;
		String ISBN = enterISBN.getText().trim();
		price=new DataGetter(statement).getBookByISBN(ISBN).getOriginPrice();
	}

	private void focus() {
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				enterISBN.requestFocus();
			}
		});
	}

	private void creteButton() {
		log_out = loadButtonImage("B_log out.png");
		log_out.setActionCommand("Log Out");
		profile = loadButtonImage("B_profile.png");
		profile.setActionCommand("Profile");
		cart = loadButtonImage("B_cart.png");
		cart.setActionCommand("Cart");
		confirm = loadButtonImage("B_confirm.png");
		confirm.setActionCommand("Confirm");
		cancel = loadButtonImage("B_cancel.png");
		cancel.setActionCommand("Cancel");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				
				switch (command) {
				case ("Log Out"):
					close();
					new HomeFrame(statement);
					break;
				case ("Profile"):
					close();
					new ProfileFrame(statement).open();
					break;
				case ("Cart"):
					new CartFrame(statement).open();
					break;
				case ("Confirm"):
					String ISBN = enterISBN.getText();
					try {						
						new DataSetter(statement).createSells( Integer.parseInt(new DataGetter(statement).getBookByISBN(ISBN).getBookID()),LoginFrame.MEMBER.getMember().getMemberID(),d);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					close();
					try {
						new SellFrame(member,statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ("Cancel"):
					close();
					new ProfileFrame(statement).open();
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		log_out.addActionListener(listener);
		profile.addActionListener(listener);
		cart.addActionListener(listener);
		confirm.addActionListener(listener);
		cancel.addActionListener(listener);
	}

	private void createPanel() {
		Box buttBox = new Box(BoxLayout.LINE_AXIS);
		buttBox.add(Box.createVerticalStrut(100));
		buttBox.add(Box.createHorizontalStrut(450));
		buttBox.add(log_out);
		buttBox.add(profile);
		buttBox.add(Box.createHorizontalStrut(8));
		buttBox.add(cart);

		buttonPanel = new JPanel();
		buttonPanel.add(cancel);
		buttonPanel.add(Box.createRigidArea(new Dimension(80, 0)));
		buttonPanel.add(confirm);
		buttonPanel.setOpaque(false);

		Box ISBNBox = new Box(BoxLayout.LINE_AXIS);
		ISBNBox.add(Box.createHorizontalStrut(73));
		ISBNBox.add(enterISBN);
		Box priceBox = new Box(BoxLayout.LINE_AXIS);
		priceBox.add(Box.createHorizontalStrut(80));
		priceBox.add(priceLabel);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(Box.createVerticalStrut(140));
		box.add(statusCombo);
		box.add(Box.createVerticalStrut(48));
		box.add(ISBNBox);
		box.add(Box.createVerticalStrut(40));
		box.add(priceBox);
		box.add(Box.createVerticalStrut(40));
		box.add(buttonPanel);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
