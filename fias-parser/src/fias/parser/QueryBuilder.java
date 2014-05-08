package fias.parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author danila
 */
public class QueryBuilder {

	private static  String url = "";
	public static String filename = "";
	public static int iAction = 1;

	public QueryBuilder() {}



	public void createInsertData(List struct, List<HashMap<String, String>> data)
	{
		int action = 1;

		if ("".equals(filename)) {
			System.exit(1010);
		}
/*
		String[] tmp = filename.split("_");
		if (tmp[0].equals("AS")) {
			action = 1;
		} else {
			action = 0;
		}
*/
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(filename, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(101);
		}

		String fullsql   = "";
		String sql = "";

		if (iAction == 1) {

			String[] tmp = filename.split("\\.");
			sql += "INSERT into "+tmp[0]+" (";

			for (int i=0; i < struct.size(); i++) {
				if (struct.get(i).toString().equals("NAME")) {
					sql+="\""+struct.get(i).toString()+"\"";
				} else {
					sql+=struct.get(i).toString();
				}

				if (i < struct.size() -1) {
					sql += ", ";
				}
			}

			sql += ")";
//			fullsql = fullsql.concat(sql);

			try
			{
				fw.write(fullsql);
			}
			catch (Exception e ) {
				System.out.println(e.getMessage());
				System.exit(102);
			}
//			HashMap<String, String> map = new HashMap<String, String>();

			for (int i =0; i<data.size(); i++) {
				fullsql = sql;



				for (int col = 0; col < struct.size(); col++) {
					if (col ==0 ) {
						fullsql = fullsql.concat(" VALUES ( ");
					}

					String colname =  struct.get(col).toString();

					if (data.get(i).containsKey(colname)) {
						if (colname.contains("DATE")) {
							fullsql = fullsql.concat(" to_date('"+data.get(i).get(colname).toString()+"', 'yyyy-mm-dd')");
						} else {
							fullsql = fullsql.concat("'" // .replace("\"", "\\\"")
									+ data.get(i).get(colname).toString().replace("\\", "\\ ").replace("'", "''")
									+"'");
						}
					} else {
						fullsql = fullsql.concat("null");
					}

					if (col < struct.size()-1) {
						fullsql = fullsql.concat(", ");
					}

					//fullsql+= data.get(i)[struct.get(i).toString()]
				}
				fullsql  =fullsql.concat("); \n");

				try {
					fw.write(fullsql);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.exit(102);
				}
			}

		}

		try {
			fw.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.exit(103);
		}
	}


}
