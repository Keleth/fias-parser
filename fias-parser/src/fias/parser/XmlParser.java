/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fias.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author celt
 */
public class XmlParser extends DefaultHandler{
	private String Element;
	private int iCountLines = 0;
	private List<String> struct = new ArrayList();
	private HashMap<String, String> map = new HashMap<String, String>();
	List<HashMap<String, String>> mData = new ArrayList();
        
        private boolean toSQL = false;

	public void strartDocument() throws SAXException {
		System.out.println("Start parse XML...");
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException {
		this.Element = qName;

		//System.out.println("qName="+qName.toString());

		if (attrs.getLength() > 0)
		{
			String val = "";

			for (int i = 0; i < attrs.getLength(); i++) {
				if (!struct.contains(attrs.getQName(i))) {
					struct.add(attrs.getQName(i));
				}

				map.put(attrs.getQName(i), attrs.getValue(i));

////				System.out.println(attrs.getQName(i));
//				val += attrs.getValue(i);
//				if (i <= attrs.getLength())
//					val +=";";
////				System.out.println(attrs.getValue(i));

			}

			mData.add(map);
			this.iCountLines ++;

			// save big data into file

			if (toSQL) {
				QueryBuilder db = new QueryBuilder();
				db.createInsertData(struct, mData);

				mData = new ArrayList();
			}

		}

	}

	public void endDocument() {
		System.out.println("Stop parse XML...");
		System.out.println("Found "+iCountLines+" lines");


/*		System.out.println("...Structure:");
		String sStruct = "";
		for (int i = 0; i < struct.size(); i++) {
			sStruct += struct.get(i);
			if (i < struct.size() -1) {
				sStruct+=", ";
			}
		}
		System.out.println(sStruct);

		for (int i =0; i < data.size(); i++) {
			System.out.println(data.get(i).toString());
		}
*/
	}

	public List getStruct(){
		return this.struct;
	}

    public void setToSQL(boolean toSQL) {
        this.toSQL = toSQL;
    }
    
        
        
        

}
