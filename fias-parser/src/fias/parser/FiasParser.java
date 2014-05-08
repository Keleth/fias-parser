package fias.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author celt
 */
public class FiasParser {
    public static Boolean generateSQL = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
		String file = "";

		if (args.length != 0) {
			file = args[0];
		} else {
			file = "AS_NORMDOC_20140120_dfe7d570-8f9f-44a2-a6aa-d1291705c2c9.XML";
		}

		if (args.length > 1 &&  args[1].equals("sql")) {
			generateSQL = true;
		}

		System.out.println("Start parsing: " + file);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
                               
                try {
                    parser = factory.newSAXParser();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(FiasParser.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                } catch (SAXException ex) {
                    Logger.getLogger(FiasParser.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                }
                
		XmlParser xmlPars = new XmlParser();
                xmlPars.setToSQL(generateSQL);
		if (generateSQL) {
			QueryBuilder qb = new QueryBuilder();

			String tmp[] = file.split("_");
			qb.filename = tmp[1]+".sql";                        
		}
                

                try {
                    parser.parse(new File(file), xmlPars);
                } catch (SAXException ex) {
                    Logger.getLogger(FiasParser.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                } catch (IOException ex) {
                    Logger.getLogger(FiasParser.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                }

		List<String> struct = new ArrayList();
		struct = xmlPars.getStruct();
		String sStruct = "";

		for (int i = 0; i < struct.size(); i++) {
			sStruct += struct.get(i);
			if (i < struct.size() -1) {
				sStruct+=", ";
			}
		}

		System.out.println(sStruct);

//		db.testConnetction();
        
    }
    
}
