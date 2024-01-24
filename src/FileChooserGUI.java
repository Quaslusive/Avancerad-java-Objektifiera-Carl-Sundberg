import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FileChooserGUI extends JFrame {

    // Sortera data i kolumner och rader
    private static DefaultTableModel tableD = new DefaultTableModel();
    // Presenterar data med sorterings funktion
    private static JTable tableOfData = new JTable(tableD);

    FileChooserGUI() {
        // "JFileChooser" som funkar som en jFrame
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON, CSV, XML", "json", "csv", "xml"));
        // "data" mappen innehåller diverse filformat
        fileChooser.setCurrentDirectory(new File("data"));
        fileChooser.setDialogTitle("Välj en fil :D ");

        // Val av fil, och läser av filformat
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getPath();
            String extension = path.substring(path.lastIndexOf('.') + 1);

            // Beroende på vilken filformat som används, skickas datan till en av parse funktionerna
            switch (extension) {
                case "csv" -> processCSVFile(file);
                case "json" -> processJSONFile(file);
                default -> JOptionPane.showMessageDialog(null, "Kan ej ta emot filformatet :(");
            }

            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }
    }

    // Efter val av filformat bararbetas datan genom parse funktionerna i var sin klass
    private void processCSVFile(File file) {
        setTitle(file.getName());
        CSVReader csvReader = new CSVReader(tableD);
        csvReader.readCSVFile(file);
        displayData();
    }

    private void processJSONFile(File file) {
        setTitle(file.getName());
        JSONReader jsonReader = new JSONReader(tableD);
        jsonReader.readJSONFile(file);
        displayData();
    }

    // Efter ha bararbetat och fördelat datan, skickas "tableD" vidare till "tableOfData" där det presenteras till
    // användaren
    private void displayData() {
        // navigering i "tableOfData"
        JScrollPane jScrollPane = new JScrollPane(tableOfData);
        jScrollPane.setPreferredSize(new Dimension(1000, 520));
        add(jScrollPane);
        tableOfData.setAutoCreateRowSorter(true);
    }
}