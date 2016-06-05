package org.gicci.canever.thread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

public class ProcessUrlFileConverter extends SwingWorker<Void, String>{

	private long miliseconds;
	private boolean process;
	private List<URL> urls;
	
	public ProcessUrlFileConverter(long miliseconds) {
		this.miliseconds = miliseconds;
		this.process = false;
		urls = new ArrayList<URL>();
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
				System.out.println(url.toString());
			}
			Thread.sleep(miliseconds);
		}
		
		return null;
	}

	public boolean isProcess() {
		return process;
	}
	public void setProcess(boolean process) {
		this.process = process;
	}	
}
