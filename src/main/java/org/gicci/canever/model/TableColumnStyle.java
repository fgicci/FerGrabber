package org.gicci.canever.model;

public enum TableColumnStyle {
	TEXT_STYLE,
	FILE_STYLE,
	URL_STYLE,
	DATE_STYLE,
	REGEX_STYLE;
	
	public String toString(){
        switch(this){
	        case TEXT_STYLE :
	            return "TEXT_STYLE";
	        case FILE_STYLE :
	            return "FILE_STYLE";
	        case URL_STYLE :
	            return "URL_STYLE";
	        case DATE_STYLE :
	            return "DATE_STYLE";
	        case REGEX_STYLE :
	            return "REGEX_STYLE";
	    }
        return null;
    }
}
