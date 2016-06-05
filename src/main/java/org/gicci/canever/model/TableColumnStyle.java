package org.gicci.canever.model;

public enum TableColumnStyle {
	FILE_CONTENT,
	DATE_CONTENT;
	
	public String toString(){
        switch(this){
	        case FILE_CONTENT :
	            return "FILE_CONTENT";
	        case DATE_CONTENT :
	            return "DATE_CONTENT";
	        }
        return null;
    }
}
