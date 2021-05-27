package com.earse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class myXML extends DefaultHandler {
    
	Weather weather;
    boolean inWeather = false;
    public myXML() {     
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {

        if(localName.equals("current_conditions")) {
        	weather = new Weather();
        	inWeather = true; 
        }
        
        if(inWeather) {
            
        	if(localName.equals("condition")) {
                weather.setCondition(attributes.getValue("data"));
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)throws SAXException {
        if(localName.equals("current_conditions")) {
            inWeather  = false;
        }  
    }
    //取回所要的天氣
    public String getCondition(){
    	return weather.condition;
    }
  
  
    

}