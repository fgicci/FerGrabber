package org.gicci.canever.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.gicci.canever.model.TableColumnStyle;
import org.gicci.canever.thread.ProcessUrlFileConverter;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public class FerGrabberFrame extends JFrame {

	private static final int FILE_SELECTED = 0;
	
	private JLabel lblUrlAddress;
	private JLabel lblFilePath;
	private JLabel lblPeriod;
	private JTextField txtUrlAddress;
	private JTextField txtFilePath;
	private JComboBox<ComboBoxItem> cmbPeriod;
	private JButton btnProcess;
	private JButton btnExit;
	private JTable tblRssContent;
	private RSSTableModel rssTableModel;
	
	private Properties prop;
	
	private ProcessUrlFileConverter processUrlFileConverter;
	
	public FerGrabberFrame(Properties prop) {
		super(prop.getProperty("application.name"));
		this.prop = prop;
		int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(inset, inset,
                  	   screenSize.width  - inset * 2,
                  	   screenSize.height - inset * 2);
        initComponents();
	}
	
	@SuppressWarnings("unchecked")
	private void initComponents() {
		// Labels
		lblUrlAddress = new JLabel(prop.getProperty("frame.label.lblUrlAddress"));
		lblFilePath = new JLabel(prop.getProperty("frame.label.lblFilePath"));
		lblPeriod = new JLabel(prop.getProperty("frame.label.lblPeriod"));
		
		// TextFields
		txtUrlAddress = new JTextField(50);
		txtUrlAddress.setText(prop.getProperty("frame.textfield.txtUrlAddress"));
		
		txtFilePath = new JTextField(50);
		txtFilePath.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				selectFilePathText();
			}
		});
		
		// Buttons 
		btnProcess = new JButton(prop.getProperty("frame.label.btnProcess.ready"));
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnProcess.getText().equals(prop.getProperty("frame.label.btnProcess.ready"))){
					btnProcess.setText(prop.getProperty("frame.label.btnProcess.stop"));
					txtUrlAddress.setEditable(false);
					txtFilePath.setEditable(false);
					cmbPeriod.setEnabled(false);
					
					try {
						processUrlFileConverter = new ProcessUrlFileConverter(getMilisecondsTime());
						processUrlFileConverter.setUrls(getUrlListFromTable());
						processUrlFileConverter.execute();
					} catch (MalformedURLException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
													  prop.getProperty("application.error.severe"),
													  JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
					
				} else if (btnProcess.getText().equals(prop.getProperty("frame.label.btnProcess.stop"))){
					btnProcess.setText(prop.getProperty("frame.label.btnProcess.ready"));
					processUrlFileConverter.setProcess(false);
					txtUrlAddress.setEditable(true);
					txtFilePath.setEditable(true);
					cmbPeriod.setEnabled(true);
				}
			}

			private List<URL> getUrlListFromTable() {
				List<URL> urls = new ArrayList<URL>();
				for (int line = 0; line < rssTableModel.getRowCount(); line++) {
					try {
						urls.add(new URL((String) rssTableModel.getValueAt(line, rssTableModel.getColumnIndex("URL"))));
					} catch (MalformedURLException e) {}
				}
				return urls;
			}
		});
		
		btnExit = new JButton(prop.getProperty("frame.label.btnExit"));
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				exitFrame();
			}
		});
		
		// Combobox
		cmbPeriod = new JComboBox<ComboBoxItem>(getPeriodData());
		cmbPeriod.setRenderer(new ComboBoxItemRender());
		cmbPeriod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<ComboBoxItem> comboBox = (JComboBox<ComboBoxItem>)e.getSource();
				ComboBoxItem item = (ComboBoxItem) comboBox.getSelectedItem();
		        System.out.println(item.getId() + " : " + item.getDescription());
			}
		});
		
		// Table
		String[] columns = prop.getProperty("frame.table.column.names").split(";");
		rssTableModel = new RSSTableModel(columns, getTableContentData());
		tblRssContent = new JTable(rssTableModel);
		tblRssContent.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tblRssContent.setFillsViewportHeight(true);
		//Create the scroll pane and add the table to it.
        JScrollPane tblRssContentPane = new JScrollPane(tblRssContent);
        
		// Layout
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        	.addGroup(layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
		        	.addGroup(layout.createParallelGroup(Alignment.LEADING)
		        		.addComponent(lblUrlAddress)
		        		.addComponent(lblFilePath)
		        		.addComponent(lblPeriod)
		        	)
		        	.addGroup(layout.createParallelGroup(Alignment.LEADING)
		            	.addComponent(txtUrlAddress)
		           		.addComponent(txtFilePath)
		           		.addComponent(cmbPeriod)
		            )
		        	.addGroup(layout.createParallelGroup(Alignment.LEADING)
		            	.addComponent(btnProcess)
		           		.addComponent(btnExit)
		            )
		        )
	        	.addComponent(tblRssContentPane)
	        )
        );
        		
        layout.linkSize(SwingConstants.HORIZONTAL, btnProcess, btnExit);
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(lblUrlAddress)
                .addComponent(txtUrlAddress)
                .addComponent(btnProcess)
            )
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(lblFilePath)
                .addComponent(txtFilePath)
                .addComponent(btnExit)
            )
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(lblPeriod)
                .addComponent(cmbPeriod)
            )
            .addComponent(tblRssContentPane)
        );
	}

	private void selectFilePathText() {
		if (txtFilePath.getText().equals("")) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fc.showDialog(null, prop.getProperty("frame.dialog.btnSelect")) == FILE_SELECTED) 
				txtFilePath.setText(fc.getSelectedFile().toString());
		}
	}
	
	private void exitFrame() {
		System.exit(JFrame.EXIT_ON_CLOSE);
	}
	
	private Vector<ComboBoxItem> getPeriodData() {
		Vector<ComboBoxItem> model = new Vector<ComboBoxItem>();
		model.addElement(new ComboBoxItem(1, "15 Minutes"));
		model.addElement(new ComboBoxItem(2, "30 Minutes"));
		model.addElement(new ComboBoxItem(3, "45 Minutes"));
		model.addElement(new ComboBoxItem(4, "1 Hour"));
		model.addElement(new ComboBoxItem(5, "2 Hours"));
		model.addElement(new ComboBoxItem(6, "3 Hours"));
		model.addElement(new ComboBoxItem(7, "12 Hours"));
		model.addElement(new ComboBoxItem(8, "1 Day"));
		return model;
	}
	
	private long getMilisecondsTime() {
		ComboBoxItem item = (ComboBoxItem) cmbPeriod.getSelectedItem();
		
		if (item.getId() == 1) return (long) 15 * (60 * 1000);
		if (item.getId() == 2) return (long) 30 * (60 * 1000);
		if (item.getId() == 3) return (long) 45 * (60 * 1000);
		if (item.getId() == 4) return (long) 60 * (60 * 1000);
		if (item.getId() == 5) return (long) 120 * (60 * 1000);
		if (item.getId() == 6) return (long) 180 * (60 * 1000);
		if (item.getId() == 7) return (long) 720 * (60 * 1000);
		if (item.getId() == 8) return (long) 1440 * (60 * 1000);
		
		return 60 * (60 * 1000);
	}
	
	private String[][] getTableContentData() {
		String[][] data = null;
		String[] tags = prop.getProperty("frame.table.column.content").split(";");
		String[] formats = prop.getProperty("frame.table.column.format").split(";");
		String[] types = prop.getProperty("frame.table.column.type").split(";");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc;
		
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(new URL(txtUrlAddress.getText()).openStream());
			doc.getDocumentElement().normalize();
			
			NodeList items = doc.getElementsByTagName("item");
			data = new String[items.getLength()][tags.length];
			
			for (int line = 0; line < items.getLength(); line++) {
				Node node = items.item(line);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					for (int column = 0; column < tags.length; column++) {
						if (types[column].equals(TableColumnStyle.DATE_STYLE.toString())) {
							SimpleDateFormat fromFormat = new SimpleDateFormat(prop.getProperty("application.rss.date.format"));
							SimpleDateFormat toFormat = new SimpleDateFormat(formats[column]);
							Date date = fromFormat.parse(element.getElementsByTagName(tags[column]).item(0).getTextContent());
							data[line][column] = toFormat.format(date);
						}
						if (types[column].equals(TableColumnStyle.TEXT_STYLE.toString())) {
							data[line][column] = String.format(formats[column], element.getElementsByTagName(tags[column]).item(0).getTextContent());
						}
						if (types[column].equals(TableColumnStyle.REGEX_STYLE.toString())) {
							StringBuffer sb = new StringBuffer();
							Pattern pattern = Pattern.compile(formats[column]);
							Matcher matcher = pattern.matcher(element.getElementsByTagName(tags[column]).item(0).getTextContent());
							while (matcher.find()) {
							      sb.append(matcher.group());
							}
							data[line][column] = sb.toString();
						}
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException | DOMException | ParseException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
										  prop.getProperty("application.error.severe"),
										  JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
		return data;
	}
}
