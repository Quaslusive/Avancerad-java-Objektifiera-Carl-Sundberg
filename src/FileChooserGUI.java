import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FileChooserGUI extends JFrame {

    //Sortera data i kolumner och rader
    private static DefaultTableModel tableD = new DefaultTableModel();
    // Presenterar data med sorterings funktion
    private static JTable tableOfData = new JTable(tableD);

    FileChooserGUI() {
        // "JFileChooser" funkar som GUI
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON, CSV, XML", "json", "csv", "xml"));
        // "data" mappen finns i src
        fileChooser.setCurrentDirectory(new File("data"));
        fileChooser.setDialogTitle("Välj fil :D ");

        // Val av fil, och läser av fil extension
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String path = file.getPath();
            String extension = path.substring(path.lastIndexOf('.') + 1);

            // Vet ej hur effektiv lambda case är, men det är enklare att läsa
            // Har försökt att lägga till XML läsare men det var för bökigt
            switch (extension) {
                case "csv" -> processCSVFile(file);
                case "json" -> processJSONFile(file);
                default -> JOptionPane.showMessageDialog(null, "Kan ej ta emot filformatet :(");
            }

            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }
    }
    // Efter data fördelningen, skickas "tableD" vidare till "tableOfData" där det presenteras till användaren
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

    private void displayData() {
        // navigering i "tableOfData"
        JScrollPane jScrollPane = new JScrollPane(tableOfData);
        jScrollPane.setPreferredSize(new Dimension(1000, 520));
        add(jScrollPane);
        tableOfData.setAutoCreateRowSorter(true);
    }
}