import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import connection_sql.DataGetter;

public class OrderFrame extends ImageFrame {
	private Vector<Vector> buyData,sellData;
	private Vector<String> buyTitle;
	private Vector<String> sellTitle;
	private JTable buyTable, sellTable;
	private JScrollPane buyPane, sellPane;
	private JButton log_out, profile, cart, buy, buy2, sell, sell2;
	private JPanel backgroundPanel;
	private Statement statement;
	private Member member=null;
	private DataGetter dataGetter;
	
	public OrderFrame(Statement statement) throws SQLException {
		this.statement=statement;
		dataGetter=new DataGetter(statement);
		buyTitle=new Vector();
		buyTitle.addElement("Name");
		buyTitle.addElement("Amount");
		buyTitle.addElement("Subtotal");
		buyTitle.addElement("Date");
		buyTitle.addElement("Status");
		sellTitle=new Vector();
		sellTitle.addElement("Name");
		sellTitle.addElement("Status");
		sellTitle.addElement("Price");
		sellTitle.addElement("Date");
		sellTitle.addElement("Cancel");
		createComp();
		creteButton();
		createPanel();
		setBackgroundImage("Order.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Order");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public OrderFrame(Member member,Statement statement) throws SQLException {
		this.member=member;
		this.statement=statement;
		createComp();
		creteButton();
		createPanel();
		setBackgroundImage("Order.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Order");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() throws SQLException {
		buyData = queryBuyData();
		sellData = querySellData();
		buyTable = new JTable(buyData, buyTitle);
		buyTable.getTableHeader().setReorderingAllowed(false);
		buyTable.getTableHeader().setResizingAllowed(false);
		buyTable.setEnabled(false);
		sellTable = new JTable(sellData, sellTitle);
		sellTable.getTableHeader().setReorderingAllowed(false);
		sellTable.getTableHeader().setResizingAllowed(false);
		sellTable.setEnabled(false);
		buyPane = new JScrollPane(buyTable);
		buyPane.setPreferredSize(new java.awt.Dimension(600, 346));
		buyPane.setMaximumSize(new java.awt.Dimension(600, 346));
		sellPane = new JScrollPane(sellTable);
		sellPane.setPreferredSize(new java.awt.Dimension(600, 346));
		sellPane.setMaximumSize(new java.awt.Dimension(600, 346));
		sellPane.setVisible(false);
	}

	private Vector<Vector> queryBuyData() throws SQLException {
		Vector<Vector>data =dataGetter.getOrder(LoginFrame.MEMBER.getMember().getMemberID());
		return data;
	}

	private Vector<Vector> querySellData() throws SQLException {
		Vector<Vector>data =dataGetter.getSells(LoginFrame.MEMBER.getMember().getMemberID());
		return data;
	}

	public void updateTable(String command) throws SQLException {
		switch (command) {
		case ("Buy"):
			buyData = queryBuyData();
			TableModel buyDataModel = new DefaultTableModel(buyData, buyTitle);
			buyTable.setModel(buyDataModel);
			break;
		case ("Sell"):
			sellData = querySellData();
			TableModel sellDataModel = new DefaultTableModel(sellData, sellTitle);
			sellTable.setModel(sellDataModel);
			break;
		}
	}

	private void creteButton() {
		log_out = loadButtonImage("B_log out.png");
		log_out.setActionCommand("Log Out");
		profile = loadButtonImage("B_profile.png");
		profile.setActionCommand("Profile");
		cart = loadButtonImage("B_cart.png");
		cart.setActionCommand("Cart");
		buy = loadButtonImage("B_buy.png");
		buy2 = loadButtonImage("B_buy2.png");
		buy2.setActionCommand("Buy");
		buy2.setVisible(false);
		sell = loadButtonImage("B_sell2.png");
		sell.setActionCommand("Sell");
		sell2 = loadButtonImage("B_sell3.png");
		sell2.setVisible(false);
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
					new ProfileFrame(statement);
					break;
				case ("Cart"):
					new CartFrame(statement);
					break;
				case ("Buy"):
					buy.setVisible(true);
					buy2.setVisible(false);
					sell.setVisible(true);
					sell2.setVisible(false);
					buyPane.setVisible(true);
					sellPane.setVisible(false);
					break;
				case ("Sell"):
					buy.setVisible(false);
					buy2.setVisible(true);
					sell.setVisible(false);
					sell2.setVisible(true);
					buyPane.setVisible(false);
					sellPane.setVisible(true);
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		log_out.addActionListener(listener);
		profile.addActionListener(listener);
		cart.addActionListener(listener);
		buy2.addActionListener(listener);
		sell.addActionListener(listener);
	}

	private void createPanel() {
		Box buttBox = new Box(BoxLayout.LINE_AXIS);
		buttBox.add(Box.createVerticalStrut(100));
		buttBox.add(Box.createHorizontalStrut(450));
		buttBox.add(log_out);
		buttBox.add(profile);
		buttBox.add(Box.createHorizontalStrut(8));
		buttBox.add(cart);

		Box buttBox2 = new Box(BoxLayout.LINE_AXIS);
		buttBox2.add(Box.createHorizontalStrut(70));
		buttBox2.add(buy);
		buttBox2.add(buy2);
		buttBox2.add(sell);
		buttBox2.add(sell2);
		buttBox2.add(Box.createVerticalStrut(80));

		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(buttBox);
		box.add(buttBox2);
		box.add(buyPane);
		box.add(sellPane);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
