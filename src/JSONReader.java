import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JSONReader {

    private DefaultTableModel tableD;

    public JSONReader(DefaultTableModel tableD) {

        this.tableD = tableD;
    }

    public void readJSONFile(File file) {

        try (
            FileReader reader = new FileReader(file)) {
            // json.simple biblioteket för att parse JSON data
            JSONParser jsonParser = new JSONParser();
            // Parser JSON data i "obj"
            Object obj = jsonParser.parse(reader);
            // Kollar om ett objekt kan castas till en klasstyp
            if (obj instanceof JSONArray jsonArray) {

                // Lägger till kolumner
                if (!jsonArray.isEmpty()) {
                    JSONObject firstObject = (JSONObject) jsonArray.get(0);
                    for (Object key : firstObject.keySet()) {
                        tableD.addColumn(key.toString());
                    }

                    // Lägger till rader
                    for (Object jsonObject : jsonArray) {
                        JSONObject jsonRow = (JSONObject) jsonObject;
                        Iterator keys = jsonRow.keySet().iterator();
                        Object[] rowData = new Object[firstObject.size()];
                        int index = 0;

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            rowData[index++] = jsonRow.get(key);
                        }
                        tableD.addRow(rowData);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(null, "Något gick fel");
        }
    }
}