package org.gicci.canever.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Properties;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	
	private Properties prop;
	
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
				System.out.println(btnProcess.getText());
				System.out.println(prop.getProperty("frame.label.btnProcess.ready"));
				if (btnProcess.getText().equals(prop.getProperty("frame.label.btnProcess.ready"))){
					System.out.println(prop.getProperty("frame.label.btnProcess.stop"));
					btnProcess.setText(prop.getProperty("frame.label.btnProcess.stop"));
				} else if (btnProcess.getText().equals(prop.getProperty("frame.label.btnProcess.stop"))){
					btnProcess.setText(prop.getProperty("frame.label.btnProcess.ready"));
				}
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
		
		// Layout
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
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
	
	
}
