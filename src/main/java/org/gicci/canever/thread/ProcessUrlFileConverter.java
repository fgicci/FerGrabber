package org.gicci.canever.thread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.gicci.canever.app.FerGrabber;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ProcessUrlFileConverter extends SwingWorker<Void, String>{

	private static final String APP_PROP_FILE = "application.properties";
	private static Properties prop;
	private long miliseconds;
	private boolean process;
	private List<URL> urls;
	
	public ProcessUrlFileConverter(long miliseconds) {
		prop = new Properties();
		try {
			prop.load(FerGrabber.class.getClassLoader().getResourceAsStream(APP_PROP_FILE));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(),
					  					  prop.getProperty("application.error.severe"),
					  					  JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			System.exit(0);
		}
		this.miliseconds = miliseconds;
		this.process = false;
		urls = new ArrayList<URL>();
		new ArrayList<URL>();
	}
	
	public void setUrls(List<URL> urls) throws MalformedURLException {
		for (URL url : urls) {
			if (!this.urls.contains(url)) this.urls.add(url);
		}
	}
	
	public List<URL> getUrls() {
		return this.urls;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		if (urls.isEmpty()) {
			System.out.println("FerGraber...data empty!");
		} else {
			this.process = true;
		}
		
		while (this.process) {
			for (URL url : getUrls()) {
				createUrlFile(url);
			}
			
			Thread.sleep(miliseconds);
			
			publish("REFRESH");
		}
		
		return null;
	}

	public boolean isProcess() {
		return process;
	}
	
	public void setProcess(boolean process) {
		this.process = process;
	}
	
	private void createUrlFile(URL url) {
		
		String[] tags = prop.getProperty("frame.table.column.content").split(";");
		String[] formats = prop.getProperty("frame.table.column.format").split(";");
		String[] types = prop.getProperty("frame.table.column.type").split(";");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc;
		
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(url.openStream());
			doc.getDocumentElement().normalize();
			
			
		} catch(ParserConfigurationException | SAXException | IOException | DOMException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(),
										  prop.getProperty("application.error.severe"),
										  JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}