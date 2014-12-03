import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class NetZipGUI extends JFrame {
	
	private JButton selectFile = new JButton("Select File");
	private JButton submitFile = new JButton("Submit File");
	private JTextField selectedFileField = new JTextField();
	private JTextArea submittedFiles = new JTextArea();


	public NetZipGUI() {
		//main frame
	//	JFrame frame = new JFrame("NetZip");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(500, 500);
		//Container pane = frame.getContentPane();
		Container cp = getContentPane();
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		selectFile.addActionListener(new selectFileButton());
		topPane.add(selectFile);
		topPane.add(selectedFileField);
		submitFile.addActionListener(new submitFileButton());
		topPane.add(submitFile);
		
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.Y_AXIS));
		JLabel submitted = new JLabel("Submitted Files:");
		bottomPane.add(submitted);
		bottomPane.add(submittedFiles);
		
		cp.add(topPane, BorderLayout.PAGE_START);
		cp.add(bottomPane, BorderLayout.CENTER);

	}

	private class selectFileButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser c = new JFileChooser();
			int rVal = c.showOpenDialog(NetZipGUI.this);
			if(rVal == JFileChooser.APPROVE_OPTION){
				selectedFileField.setText(c.getSelectedFile().getAbsolutePath());
			}
			if(rVal == JFileChooser.CANCEL_OPTION){
				selectedFileField.setText("You pressed cancel");
			}
		}
	}
	
	private class submitFileButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0){
			if(!selectedFileField.equals(null) || !selectedFileField.equals("You pressed cancel")){
				String[] parameters = {selectedFileField.getText()};
			NetZip.main(parameters);
			submittedFiles.setText(submittedFiles.getText() + '\n' + selectedFileField.getText());
			selectedFileField.setText("");
			}
		}
	}
	
	public static void main(String[] args) {
		run(new NetZipGUI(), 600,200);
	}
	
	public static void run(JFrame frame, int width, int height){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
		try {
			ZipServer.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
