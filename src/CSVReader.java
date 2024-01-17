import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class CSVReader {

    private DefaultTableModel tableD;

    public CSVReader(DefaultTableModel tableD) {

        this.tableD = tableD;
    }

    public void readCSVFile(File file) {

        try (
            BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean title = true;
            // Matar in och sorterar data i korrekt ordning i "tableD"
            while ((line = reader.readLine()) != null) {
                // Skapar radfält för data som är separerad med "`,`"
                String[] inputCSV = line.split(",");
                // Skapar kolumner till "tableD" från de första raderna
                if (title) {
                    for (String column : inputCSV) {
                        tableD.addColumn(column);
                    }
                    title = false;
                } else {
                    // övrig data matas in på addRow funktionen
                    tableD.addRow(inputCSV);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Något gick fel");
        }
    }
}