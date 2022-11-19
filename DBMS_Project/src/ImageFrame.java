import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ImageFrame extends JFrame {
	private JPanel backgroundPanel;

	public ImageFrame() {

		createComp();
		creteButton();
		createPanel();
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(800, 600);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void createComp() {
		// TODO Auto-generated method stub

	}

	private void creteButton() {
		// TODO Auto-generated method stub

	}

	private void createPanel() {
		// TODO Auto-generated method stub

	}

	public void close() {
		this.setVisible(false);
	}

	public void open() {
		this.setVisible(true);
	}

	protected BufferedImage loadImage(String path) {
		URL imgURL;
		try {
			imgURL = ImageFrame.class.getResource(path);
			BufferedImage img = ImageIO.read(imgURL);
			return img;
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}
	}
	
	protected JLabel loadLabelImage(String path) {
		BufferedImage img = loadImage(path);
		Image dimg = img.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		JLabel imgLabel = new JLabel(imageIcon);
		return imgLabel;
	}
	
	protected JLabel loadLabelImage(String path, int width, int height) {
		BufferedImage img = loadImage(path);
		Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		JLabel imgLabel = new JLabel(imageIcon);
		return imgLabel;
	}
	
	protected JButton loadButtonImage(String path) {
		JButton button = new JButton();
		ImageIcon image = new ImageIcon(loadImage(path));
		image.setImage(
				image.getImage().getScaledInstance(image.getIconWidth(), image.getIconHeight(), Image.SCALE_SMOOTH));
		button.setIcon(image);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		return button;
	}

	protected JButton loadButtonImage(String path, int width, int height) {
		JButton button = new JButton();
		ImageIcon image = new ImageIcon(loadImage(path));
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		button.setIcon(image);
		button.setContentAreaFilled(false);
		button.setBorder(null);
		return button;
	}

	protected JScrollPane loadPanelImage(String path) {
		ImageIcon image = new ImageIcon(loadImage(path));
		JLabel img = new JLabel(image);
		JScrollPane sc = new JScrollPane();
		sc.setViewportView(img);
		return sc;
	}

	protected void setBackgroundImage(String path) {
		backgroundPanel = (JPanel) getContentPane();
		backgroundPanel.setOpaque(false);
		backgroundPanel.setLayout(new FlowLayout());
		BufferedImage img = loadImage(path);
		Image dimg = img.getScaledInstance(img.getWidth() - 15, img.getHeight() - 30, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		JLabel imgLabel = new JLabel(imageIcon);
		getLayeredPane().setLayout(null);
		getLayeredPane().add(imgLabel, Integer.valueOf(Integer.MIN_VALUE));
		imgLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
	}
}
