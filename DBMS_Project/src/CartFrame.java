import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import connection_sql.DataSetter;

public class CartFrame extends ImageFrame {
	final int FIELD_WIDTH = 25;
	private Vector<Vector> data;
	private Vector<String> dataTitle;

	private JTable cartTable;
	private JScrollPane cartPane;
	private JButton clear, check_out;
	private JPanel backgroundPanel;
	private Statement statement;
	private DefaultTableModel DFMO;
	private Obj.Member member = null;

	static ArrayList<String> BookIDsInCart = new ArrayList();
	static ArrayList<String> BookNamesInCart = new ArrayList();
	static ArrayList<String> Amounts = new ArrayList();
	static ArrayList<String> Prices = new ArrayList();
	static ArrayList<String> SubTotals = new ArrayList();

	public CartFrame(Statement statement) {
		this.statement = statement;
		dataTitle = new Vector();
		dataTitle.addElement("Name");
		dataTitle.addElement("Price");
		dataTitle.addElement("Amount");
		dataTitle.addElement("Subtotal");
		creteButton();
		createComp();
		createPanel();
		setBackgroundImage("Cart.png");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(600, 800);
		setTitle("Cart");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() {
		data = queryData();
		DFMO = new DefaultTableModel(data, dataTitle);
		cartTable = new JTable(data, dataTitle);
		cartTable.getTableHeader().setReorderingAllowed(false);
		cartTable.getTableHeader().setResizingAllowed(false);
		cartTable.setEnabled(false);
		cartPane = new JScrollPane(cartTable);
	}

	private Vector<Vector> queryData() {
		// query data here
		Vector<Vector> datas = new Vector();

		for (int i = 0; i < BookIDsInCart.size(); i++) {
			Vector<String> data = new Vector();
			data.addElement(BookNamesInCart.get(i));
			data.addElement(Prices.get(i));
			data.addElement(Amounts.get(i));
			data.addElement(SubTotals.get(i));
			datas.addElement(data);
		}

		return datas;
	}

	public void updateTable() {
		data = queryData();
		TableModel dataModel = new DefaultTableModel(data, dataTitle);
		cartTable.setModel(dataModel);
	}

	public void checkout(Obj.Member member) throws SQLException {
		if (LoginFrame.MEMBER == null) {
			JOptionPane.showMessageDialog(null, "Please log in or sign up first!", "Notification",
					JOptionPane.WARNING_MESSAGE);
		} else {
			data = null;
			TableModel dataModel = new DefaultTableModel(data, dataTitle);
			cartTable.setModel(dataModel);
			new DataSetter(statement).createOrder(LoginFrame.MEMBER.getMember().getMemberID(), BookIDsInCart, Amounts,
					Prices);
		}
	}

	private void creteButton() {
		clear = loadButtonImage("B_clear.png");
		clear.setActionCommand("Clear");
		check_out = loadButtonImage("B_check out.png");
		check_out.setActionCommand("Check Out");
		check_out.setAlignmentX(Component.CENTER_ALIGNMENT);

		class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();

				switch (command) {
				case ("Clear"):
					data = null;
					TableModel dataModel = new DefaultTableModel(data, dataTitle);
					cartTable.setModel(dataModel);
				case ("Check Out"):
					try {
						checkout(member);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		ActionListener listener = new ButtonListener();
		clear.addActionListener(listener);
		check_out.addActionListener(listener);
	}

	private void createPanel() {
		Box clearBox = new Box(BoxLayout.LINE_AXIS);
		clearBox.add(Box.createHorizontalStrut(380));
		clearBox.add(clear);
		
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(Box.createVerticalStrut(120));
		box.add(clearBox);
		box.add(Box.createVerticalStrut(20));
		box.add(cartPane);
		box.add(Box.createVerticalStrut(25));
		box.add(check_out);

		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.add(box);
	}
}
