package org.gicci.canever.app;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.gicci.canever.gui.FerGrabberFrame;

public class FerGrabber {

	private static final String APP_PROP_FILE = "application.properties";
	private static Properties prop;
	
	private static void createAndShowGUI() {
		try {
			prop = new Properties();
			prop.load(FerGrabber.class.getClassLoader().getResourceAsStream(APP_PROP_FILE));
			FerGrabberFrame ferGrabberFrame = new FerGrabberFrame(prop);
			ferGrabberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ferGrabberFrame.pack();
			ferGrabberFrame.setVisible(true);
		} catch (NullPointerException | IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(),
										  prop.getProperty("application.error.severe"),
										  JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			System.exit(0);
		}
    }
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}
