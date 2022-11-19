import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BookFrame extends ImageFrame {
	private Obj.Book book;
	private double[] discounts = { 1, 0.7, 0.6, 0.55 };
	private JButton login, sign_up, log_out, profile, cart, back, add;
	private JComboBox<String> statusCombo;
	private JLabel imgLabel, tagLabel, titleLabel, priceLabel, subLabel, authorLabel, pubLabel, dateLabel, ISBNLabel, lanLabel;
	private JPanel backgroundPanel;
	private Statement statement;
	private Member member=null;

	public BookFrame(Obj.Book book,Statement statement) {
		
		this.statement=statement;
		this.book = book;
		createComp();
		creteButton();
		createPanel();
		setBackgroundImage("Book.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Book");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		if(LoginFrame.MEMBER!=null) {
			logIn();
		}
	}
	public void logIn() {
		login.setVisible(false);
		sign_up.setVisible(false);
		log_out.setVisible(true);
		profile.setVisible(true);
	}

	private void createComp() {
		imgLabel = loadLabelImage("book1.png", 150, 176);
		tagLabel = loadLabelImage("tag.png");
		String title=book.getBookName();
		titleLabel = new JLabel(title);		
		titleLabel.setFont(new Font("", Font.BOLD, 10));
		statusCombo = new JComboBox<String>();
		statusCombo.addItem("Status: New");
		statusCombo.addItem("Status: Great");
		statusCombo.addItem("Status: Medium");
		statusCombo.addItem("Status: Bad");
		statusCombo.setMaximumSize(new java.awt.Dimension(150, 20));
		
		priceLabel = new JLabel("Price: NT$" + String.format("%.2f", (book.getOriginPrice() * discounts[0])));
		statusCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String status = statusCombo.getSelectedItem().toString();
				String val = "";
				switch (status) {
				case "Status: New":
					val = String.format("%.2f", (book.getOriginPrice() * discounts[0]));
					priceLabel.setText("Price: NT$" + val);
					break;
				case "Status: Great":
					val = String.format("%.2f", (book.getOriginPrice() * discounts[1]));
					priceLabel.setText("Price: NT$" + val);
					break;
				case "Status: Medium":
					val = String.format("%.2f", (book.getOriginPrice() * discounts[2]));
					priceLabel.setText("Price: NT$" + val);
					break;
				case "Status: Bad":
					val = String.format("%.2f", (book.getOriginPrice() * discounts[3]));
					priceLabel.setText("Price: NT$" + val);
					break;
				}
			}
		});

		subLabel = new JLabel("Book Details: ");
		subLabel.setFont(new Font("", Font.BOLD, 20));
		authorLabel = new JLabel("Author: " + book.getAuthor());
		pubLabel = new JLabel("Publisher: " + book.getPublisher());
		dateLabel = new JLabel("Published: " + book.getPublicDate());
		ISBNLabel = new JLabel("ISBN/ISSN: " + book.getISBN());
		lanLabel = new JLabel("Language: " + book.getLanguage());
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
		back = loadButtonImage("B_back.png");
		back.setActionCommand("Back");
		add = loadButtonImage("B_add.png");
		add.setActionCommand("Add");
		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Login"):
					new LoginFrame(statement);
					break;
				case ("Sign Up"):
					new RegisterFrame(statement);
					break;
				case ("Log Out"):
					LoginFrame.MEMBER=null;
					close();
					new BookFrame(book,statement);
					break;
				case ("Profile"):
					new ProfileFrame(statement);
					break;
				case ("Cart"):
					new CartFrame(statement);
					break;
				case ("Back"):
					close();
					break;
				case ("Add"):
					// add something to cart
					String status = statusCombo.getSelectedItem().toString();
					double val=0;
					switch (status) {
					case "Status: New":
						val = book.getOriginPrice() * discounts[0];
						break;
					case "Status: Great":
						val = book.getOriginPrice() * discounts[1];
						break;
					case "Status: Medium":
						val = book.getOriginPrice() * discounts[2];
						break;
					case "Status: Bad":
						val = book.getOriginPrice() * discounts[3];
						break;
					}
					String val2=String.format("%.2f",val);
					
						int nQ=1;
						if(CartFrame.BookIDsInCart.contains(book.getBookID())&&CartFrame.Prices.contains(val2)) {
							int indexB=CartFrame.BookIDsInCart.indexOf(book.getBookID());
							int indexP=CartFrame.Prices.indexOf(val2);
							if(CartFrame.BookIDsInCart.get(indexP).equals(book.getBookID())) {
								int befQ=Integer.parseInt(CartFrame.Amounts.get(indexB));
								nQ=befQ+1;
								CartFrame.Amounts.set(indexP,Integer.toString(nQ));	
								String subTotal2=String.format("%.2f",nQ*val);
								CartFrame.SubTotals.set(indexP,subTotal2);
							}else {
								CartFrame.BookIDsInCart.add(book.getBookID());
								CartFrame.BookNamesInCart.add(book.getBookName());
								CartFrame.Amounts.add(Integer.toString(nQ));	
								String subTotal2=String.format("%.2f",nQ*val);
								CartFrame.SubTotals.add(subTotal2);
								CartFrame.Prices.add(val2);
							}
							
						}else {
							CartFrame.BookIDsInCart.add(book.getBookID());
							CartFrame.BookNamesInCart.add(book.getBookName());
							CartFrame.Amounts.add(Integer.toString(nQ));	
							String subTotal2=String.format("%.2f",nQ*val);
							CartFrame.SubTotals.add(subTotal2);
							CartFrame.Prices.add(val2);
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
		back.addActionListener(listener);
		add.addActionListener(listener);
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

		Box labelBox = new Box(BoxLayout.Y_AXIS);
		labelBox.add(titleLabel);
		Box miniBox = new Box(BoxLayout.X_AXIS);
		miniBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		miniBox.add(statusCombo);
		miniBox.add(Box.createHorizontalStrut(10));
		miniBox.add(priceLabel);
		labelBox.add(miniBox);
		labelBox.add(subLabel);
		labelBox.add(authorLabel);
		labelBox.add(pubLabel);
		labelBox.add(dateLabel);
		labelBox.add(ISBNLabel);
		labelBox.add(lanLabel);
		
		Box imgBox = new Box(BoxLayout.LINE_AXIS);
		imgBox.add(imgLabel);
		imgBox.add(Box.createHorizontalStrut(110));
		imgBox.add(labelBox);
		
		Box buttBox2 = new Box(BoxLayout.LINE_AXIS);
		buttBox2.add(back);
		buttBox2.add(Box.createRigidArea(new Dimension(80, 0)));
		buttBox2.add(add);
		
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(Box.createVerticalStrut(80));
		box.add(imgBox);
		box.add(Box.createVerticalStrut(50));
		box.add(buttBox2);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
