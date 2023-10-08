package utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class client implements ActionListener {
	JTextField prompt;
	static JPanel wallpaper;
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream dout;

	static JFrame body = new JFrame();

	// constructor
	public client() {

		body.setLayout(null);

		// top panel
		JPanel chat_header = new JPanel();
		chat_header.setBackground(new Color(58, 147, 207));
		chat_header.setBounds(0, 0, 450, 70);
		chat_header.setLayout(null);
		body.add(chat_header);

		// back button
		ImageIcon img1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image img2 = img1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon img3 = new ImageIcon(img2);
		JLabel back = new JLabel(img3);
		back.setBounds(5, 20, 25, 25);
		chat_header.add(back);// add on top of top panel

		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		// profile_pic
		ImageIcon img4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		Image img5 = img4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		ImageIcon img6 = new ImageIcon(img5);
		JLabel profile_pic = new JLabel(img6);
		profile_pic.setBounds(40, 15, 40, 40);
		chat_header.add(profile_pic);// add on top of top panel

		// video-call icon
		ImageIcon img7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image img8 = img7.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon img9 = new ImageIcon(img8);
		JLabel video_call = new JLabel(img9);
		video_call.setBounds(300, 20, 25, 25);
		chat_header.add(video_call);// add on top of top panel

		// call icon
		ImageIcon img10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image img11 = img10.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon img12 = new ImageIcon(img11);
		JLabel call = new JLabel(img12);
		call.setBounds(360, 20, 25, 25);
		chat_header.add(call);// add on top of top panel

		// more icon
		ImageIcon img13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image img14 = img13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon img15 = new ImageIcon(img14);
		JLabel more = new JLabel(img15);
		more.setBounds(420, 20, 25, 25);
		chat_header.add(more);// add on top of top panel

		// name
		JLabel name = new JLabel("God Ussop");
		name.setForeground(Color.WHITE);
		name.setBounds(90, 15, 120, 20);
		name.setFont(new Font("SAN_SERIF", Font.BOLD, 15));
		chat_header.add(name);

		// status
		JLabel status = new JLabel("Online");
		status.setForeground(Color.WHITE);
		status.setBounds(90, 35, 120, 20);
		status.setFont(new Font("SAN_SERIF", Font.PLAIN, 10));
		chat_header.add(status);

		// chat_wallpaper
		wallpaper = new JPanel();
		wallpaper.setBounds(0, 70, 450, 560);
		body.add(wallpaper);// add on top of top panel

		// chat prompt
		prompt = new JTextField();
		prompt.setBounds(10, 630, 250, 40);
		body.add(prompt);

		// ImageIcon img19 = new
		// ImageIcon(ClassLoader.getSystemResource("icons/send.png"));
		// Image img20 = img19.getImage().getScaledInstance(28, 28,
		// Image.SCALE_DEFAULT);
		// ImageIcon img21 = new ImageIcon(img20);
		// JLabel send = new JLabel(img21);
		// send.setBounds(265, 635, 28, 28);
		// add(send);

		// send
		JButton send = new JButton("send");
		send.setBounds(265, 635, 35, 28);
		send.addActionListener(this);
		body.add(send);

		// gallery icon
		ImageIcon img22 = new ImageIcon(ClassLoader.getSystemResource("icons/gallery.png"));
		Image img23 = img22.getImage().getScaledInstance(33, 33, Image.SCALE_DEFAULT);
		ImageIcon img24 = new ImageIcon(img23);
		JLabel gallery = new JLabel(img24);
		gallery.setBounds(331, 633, 33, 33);
		body.add(gallery);

		// mic icon
		ImageIcon img25 = new ImageIcon(ClassLoader.getSystemResource("icons/mic.png"));
		Image img26 = img25.getImage().getScaledInstance(21, 21, Image.SCALE_DEFAULT);
		ImageIcon img27 = new ImageIcon(img26);
		JLabel mic = new JLabel(img27);
		mic.setBounds(400, 639, 21, 21);
		body.add(mic);

		// main layout
		body.setSize(450, 700);
		body.setLocation(600, 100);
		body.getContentPane().setBackground(Color.WHITE);
		body.setVisible(true);// always at last

	}

	public void actionPerformed(ActionEvent e) {
		try {
			String text = prompt.getText();

			JPanel p1 = formatLable(text);

			// setting the layout of wallpaper
			wallpaper.setLayout(new BorderLayout());

			// the panel which hold the text
			JPanel right = new JPanel(new BorderLayout());

			// we can't add string so we have to convert to panel p1
			right.add(p1, BorderLayout.LINE_END);

			// add all the right panel verticlly in vertical
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
			wallpaper.add(vertical, BorderLayout.PAGE_START);

			dout.writeUTF(text);

			prompt.setText("");
			// refresh ui
			body.repaint();
			body.invalidate();
			body.validate();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static JPanel formatLable(String text) {
		JPanel chat_panel = new JPanel();

		chat_panel.setLayout(new BoxLayout(chat_panel, BoxLayout.Y_AXIS));

		JLabel output = new JLabel("<html><p style=\"width: 150px\">" + text + "</html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 16));
		output.setBackground(new Color(186, 224, 247));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 15, 15, 50));

		chat_panel.add(output);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");

		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		chat_panel.add(time);

		return chat_panel;
	}

	public static void main(String[] args) {
		// anonimus obj to execute this class
		new client();
		try {
			Socket s = new Socket("localhost", 6001);
			DataInputStream din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

			while (true) {
				wallpaper.setLayout(new BorderLayout());
				String msg = din.readUTF();
				JPanel panel = formatLable(msg);

				JPanel left = new JPanel(new BorderLayout());
				left.add(panel, BorderLayout.LINE_START);
				vertical.add(left);

				vertical.add(Box.createVerticalStrut(15));
				body.add(vertical, BorderLayout.PAGE_START);

				body.validate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
