import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Component;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSliderUI.ComponentHandler;

public class HomeFrame extends ImageFrame {
	final int FIELD_WIDTH = 540;
	private JTextField enterKeyword;
	private JButton login, sign_up, log_out, profile, cart, search, book1, book2, book3, book4;
	private BookManager BM;
	private JPanel backgroundPanel;
	private Action searchAction;
	private Statement statement;
	private Member member = null;
	private Box bookBox;
	private ArrayList<Obj.Book> results;

	public HomeFrame(Statement statement) {
		this.statement = statement;

		createComp();
		creteButton();
		createPanel();
		setBackgroundImage("Home.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		if (LoginFrame.MEMBER != null) {
			logIn();
		}

	}

	public HomeFrame(ArrayList<Obj.Book> results, Statement statement) {
		this.statement = statement;
		this.results = results;
		createComp();
		creteResultButton();
		createResultPanel();
		setBackgroundImage("Home.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		if (LoginFrame.MEMBER != null) {
			logIn();
		}
	}

	private void createComp() {
		searchAction = new AbstractAction("Search") {
			public void actionPerformed(ActionEvent e) {
				String keyword = enterKeyword.getText();
				try {
					ArrayList<Obj.Book> books = new ArrayList();
					books = BM.findBook(enterKeyword.getText().trim(), statement);
					close();
					new HomeFrame(books, statement);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		BM = new BookManager();

		enterKeyword = new JTextField();
		enterKeyword.setMaximumSize(new java.awt.Dimension(FIELD_WIDTH, 20));
		enterKeyword.setMinimumSize(new java.awt.Dimension(FIELD_WIDTH, 20));
		enterKeyword.setPreferredSize(new java.awt.Dimension(FIELD_WIDTH, 20));
		enterKeyword.setOpaque(false);
		enterKeyword.setBorder(null);
		enterKeyword.setAction(searchAction);
		class JTextFieldHintListener implements FocusListener {
			private String hintText;
			private JTextField textField;

			public JTextFieldHintListener(JTextField jTextField, String hintText) {
				this.textField = jTextField;
				this.hintText = hintText;
				jTextField.setText(hintText);
				jTextField.setForeground(Color.GRAY);
			}

			public void focusGained(FocusEvent e) {
				String temp = textField.getText();
				if (temp.equals(hintText)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}

			}

			public void focusLost(FocusEvent e) {
				String temp = textField.getText();
				if (temp.equals("")) {
					textField.setForeground(Color.GRAY);
					textField.setText(hintText);
				}

			}

		}
		enterKeyword.addFocusListener(new JTextFieldHintListener(enterKeyword, "Search"));
	}

	private void creteButton() {
		login = loadButtonImage("B_login.png");
		login.setActionCommand("Login");
		sign_up = loadButtonImage("B_sign up.png");
		sign_up.setActionCommand("Sign Up");
		log_out = loadButtonImage("B_log out.png");
		log_out.setActionCommand("Log Out");
		log_out.setVisible(false);
		profile = loadButtonImage("B_profile.png");
		profile.setActionCommand("Profile");
		profile.setVisible(false);
		cart = loadButtonImage("B_cart.png");
		cart.setActionCommand("Cart");
		search = loadButtonImage("B_search.png");
		search.setActionCommand("Search");
		book1 = loadButtonImage("book1.png");
		book1.setActionCommand("Book1");
		book2 = loadButtonImage("book2.png");
		book2.setActionCommand("Book2");
		book3 = loadButtonImage("book3.png");
		book3.setActionCommand("Book3");
		book4 = loadButtonImage("book4.png");
		book4.setActionCommand("Book4");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String keyword = enterKeyword.getText();
				String command = event.getActionCommand();

				switch (command) {
				case ("Login"):
					close();
					new LoginFrame(statement);
					break;
				case ("Sign Up"):
					new RegisterFrame(statement);
					break;
				case ("Log Out"):
					logOut();
					break;
				case ("Profile"):
					new ProfileFrame(statement);
					break;
				case ("Cart"):
					new CartFrame(statement);
					break;
				case ("Search"):
					try {
						ArrayList<Obj.Book> books = new ArrayList();
						books = BM.findBook(enterKeyword.getText().trim(), statement);
						close();
						new HomeFrame(books, statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ("Book1"):
					try {
						new BookFrame(BM.findBook((int) (Math.random() * 250) + 1, statement), statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ("Book2"):
					try {
						new BookFrame(BM.findBook((int) (Math.random() * 250) + 1, statement), statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ("Book3"):
					try {
						new BookFrame(BM.findBook((int) (Math.random() * 250) + 1, statement), statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ("Book4"):
					try {
						new BookFrame(BM.findBook((int) (Math.random() * 250) + 1, statement), statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		login.addActionListener(listener);
		sign_up.addActionListener(listener);
		log_out.addActionListener(listener);
		profile.addActionListener(listener);
		cart.addActionListener(listener);
		search.addActionListener(listener);
		book1.addActionListener(listener);
		book2.addActionListener(listener);
		book3.addActionListener(listener);
		book4.addActionListener(listener);
	}

	private void logOut() {
		setVisible(false);
		LoginFrame.MEMBER = null;
		new HomeFrame(statement).open();
	}

	public void logIn() {
		login.setVisible(false);
		sign_up.setVisible(false);
		log_out.setVisible(true);
		profile.setVisible(true);
		setVisible(true);
	}

	private void createPanel() {

		Box buttBox = new Box(BoxLayout.LINE_AXIS);
		buttBox.add(Box.createVerticalStrut(100));
		buttBox.add(Box.createHorizontalStrut(450));
		buttBox.add(login);
		buttBox.add(sign_up);
		buttBox.add(log_out);
		buttBox.add(profile);
		buttBox.add(Box.createHorizontalStrut(8));
		buttBox.add(cart);

		Box searchBox = new Box(BoxLayout.LINE_AXIS);
		searchBox.add(enterKeyword);
		searchBox.add(search);

		bookBox = new Box(BoxLayout.LINE_AXIS);
		bookBox.add(book1);
		bookBox.add(Box.createHorizontalStrut(40));
		bookBox.add(book2);
		bookBox.add(Box.createHorizontalStrut(40));
		bookBox.add(book3);
		bookBox.add(Box.createHorizontalStrut(40));
		bookBox.add(book4);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(Box.createVerticalStrut(156));
		box.add(searchBox);
		box.add(Box.createVerticalStrut(45));
		box.add(bookBox);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}

	private void createResultPanel() {
		Box buttBox = new Box(BoxLayout.LINE_AXIS);
		buttBox.add(Box.createVerticalStrut(100));
		buttBox.add(Box.createHorizontalStrut(450));
		buttBox.add(login);
		buttBox.add(sign_up);
		buttBox.add(log_out);
		buttBox.add(profile);
		buttBox.add(Box.createHorizontalStrut(8));
		buttBox.add(cart);

		Box searchBox = new Box(BoxLayout.LINE_AXIS);
		searchBox.add(enterKeyword);
		searchBox.add(search);

		bookBox = new Box(BoxLayout.Y_AXIS);
		bookBox.setPreferredSize(new Dimension(580, 1000));
		bookBox.setMaximumSize(new Dimension(580, 1000));
		bookBox.setMinimumSize(new Dimension(580, 1000));
		int i = 1;
		Box tempBox = new Box(BoxLayout.X_AXIS);
		for (Obj.Book b : results) {
			JButton book = new JButton();
			book = loadButtonImage("book1.png");
			book.setVerticalTextPosition(SwingConstants.BOTTOM);
			book.setHorizontalTextPosition(SwingConstants.CENTER);
			book.setAlignmentX(Component.RIGHT_ALIGNMENT);
			book.setAlignmentY(Component.TOP_ALIGNMENT);
			book.setPreferredSize(new Dimension(book.getWidth(), book.getHeight() + 20));
//			book.setText("<html><body style='width: 10spx'>" + b.getBookName() + "<br>" + b.getCourseName() + "<br>"
//					+ b.getTeacher() + "<br>" + b.getSemester() + "</body></html>");

			book.setText("<html><body style='width: 8spx'>" + b.getBookName() + "</body></html>");

			book.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					new BookFrame(b, statement);
				}
			});

			if (i % 4 == 0) {
				tempBox.add(book);
				bookBox.add(tempBox);
				bookBox.add(Box.createVerticalStrut(20));
				tempBox = new Box(BoxLayout.X_AXIS);
			} else {
				tempBox.add(book);
			}

			if (i == results.size()) {
				int index = results.size() % 4;
				switch (index) {
				case (1):
					tempBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
					bookBox.add(tempBox);
					break;
				case (2):
					tempBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
					bookBox.add(tempBox);
					break;
				case (3):
					tempBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
					bookBox.add(tempBox);
					break;
				}
			}
			i++;
		}

		JScrollPane sc = new JScrollPane(bookBox);
		sc.setPreferredSize(new Dimension(600, 200));
		sc.setMaximumSize(new Dimension(600, 200));
		sc.setMinimumSize(new Dimension(600, 200));
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sc.setOpaque(false);
		sc.getViewport().setOpaque(false);
		sc.setBorder(null);
		sc.getViewport().setBorder(null);

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(Box.createVerticalStrut(156));
		box.add(searchBox);
		box.add(Box.createVerticalStrut(45));
		box.add(sc);
		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}

	private void creteResultButton() {
		login = loadButtonImage("B_login.png");
		login.setActionCommand("Login");
		sign_up = loadButtonImage("B_sign up.png");
		sign_up.setActionCommand("Sign Up");
		log_out = loadButtonImage("B_log out.png");
		log_out.setActionCommand("Log Out");
		log_out.setVisible(false);
		profile = loadButtonImage("B_profile.png");
		profile.setActionCommand("Profile");
		profile.setVisible(false);
		cart = loadButtonImage("B_cart.png");
		cart.setActionCommand("Cart");
		search = loadButtonImage("B_search.png");
		search.setActionCommand("Search");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String keyword = enterKeyword.getText();
				String command = event.getActionCommand();

				switch (command) {
				case ("Login"):
					new LoginFrame(statement);
					break;
				case ("Sign Up"):
					new RegisterFrame(statement);
					break;
				case ("Log Out"):
					logOut();
					break;
				case ("Profile"):
					new ProfileFrame(statement);
					break;
				case ("Cart"):
					new CartFrame(statement);
					break;
				case ("Search"):
					try {
						ArrayList<Obj.Book> books = new ArrayList();
						books = BM.findBook(enterKeyword.getText().trim(), statement);
						new HomeFrame(books, statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		login.addActionListener(listener);
		sign_up.addActionListener(listener);
		log_out.addActionListener(listener);
		profile.addActionListener(listener);
		cart.addActionListener(listener);
		search.addActionListener(listener);
	}
}
