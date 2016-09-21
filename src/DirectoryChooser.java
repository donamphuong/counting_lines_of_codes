/**
 * Created by donamphuong on 20/09/2016.
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DirectoryChooser extends JPanel implements ActionListener {
    JButton go;
    JFileChooser chooser;
    String choosertitle;
    int numLines;

    public DirectoryChooser() {
        go = new JButton("Do it");
        go.addActionListener(this);
        add(go);
    }


    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result;

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            count_lines_directory(dir);
            System.out.println("Directory " + dir.getName() + " has " + numLines + " lines.");
        } else {
            System.out.println("No selection");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        DirectoryChooser panel = new DirectoryChooser();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.getContentPane().add(panel, "Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }

    public static int count_lines_file(File file) {
        int line = 0;
        try {
            if(file.exists()) {
                Scanner fileReader = new Scanner(file);

                while(fileReader.hasNextLine()) {
                    if(!fileReader.nextLine().equals("")) {
                        line++;
                    }
                }
            } else {
                System.out.println("File does not exist");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    public void count_lines_directory(File dirname) {

        File[] fList = dirname.listFiles();

        for(File file: fList) {
            if(file.isFile()) {
                numLines += count_lines_file(file);
            } else if(file.isDirectory()) {
                count_lines_directory(file);
            }
        }
    }

}
