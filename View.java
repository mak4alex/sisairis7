package sisairis7;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;


public class View extends JFrame {

    private static final int INIT_INPUT_COLUMN = 10;

    JPanel mainPanel;
    JPanel controlPanel;
    JPanel inputPanel;

    JList<Student> studentJList;
    DefaultListModel<Student> studentListModel;

    JTextField idField;
    JTextField nameField;
    JTextField markField;

    JButton addButton;
    JButton deleteButton;

    JFileChooser fileChooser;

    public View() {
        super("XML Parser Lab");

        createMenu();

        createGUI();

        setControls();

        this.add(mainPanel);

        studentListModel.addElement(new Student(0, "lol", 0.0));
        studentListModel.addElement(new Student(0, "lol", 0.0));
        studentListModel.addElement(new Student(0, "lol", 0.0));

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem openMenuItem = new JMenuItem("Open");

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new XMLFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        openMenuItem.addActionListener(al -> {

            int returnVal = fileChooser.showDialog(View.this, "Select file");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dataFile = fileChooser.getSelectedFile();

                try {

                    JAXBContext jaxbContext = JAXBContext.newInstance(Students.class);

                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                    Students students = (Students) jaxbUnmarshaller.unmarshal( dataFile );

                    studentListModel.removeAllElements();

                    students.getStudents().forEach(studentListModel::addElement);

                } catch (JAXBException e) {

                    e.printStackTrace();

                    JOptionPane.showMessageDialog(mainPanel,
                            "Wrong file!", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }

            //Reset the file chooser for the next time it's shown.
            fileChooser.setSelectedFile(null);
        });


        JMenuItem saveMenuItem = new JMenuItem("Save");


        saveMenuItem.addActionListener( al -> {

            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(View.this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {

                File fileToSave = fileChooser.getSelectedFile();


                try {

                    JAXBContext jaxbContext = JAXBContext.newInstance(Students.class);

                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    Students students = new Students();
                    for (int i = 0; i < studentListModel.size(); ++i) {
                        students.addStudent(studentListModel.elementAt(i));
                    }

                    jaxbMarshaller.marshal(students, fileToSave);

                } catch (JAXBException e) {
                    e.printStackTrace();
                }

            }

            fileChooser.setSelectedFile(null);
        });


        JMenuItem exitMenuItem = new JMenuItem("Close");
        exitMenuItem.addActionListener( al -> System.exit(0) );


        file.add(openMenuItem);
        file.add(saveMenuItem);
        file.add(exitMenuItem);
        menuBar.add(file);
        setJMenuBar(menuBar);

    }

    private void setControls() {

        addButton.addActionListener(al -> {

            if (idField.getText().isEmpty() ||
                    nameField.getText().isEmpty() ||
                    markField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel,
                        "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = nameField.getText();

            int id;
            try {
                id = Integer.parseInt(idField.getText());
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(mainPanel,
                        "Invalid id format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double mark;
            try {
                mark = Double.parseDouble(markField.getText());
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(mainPanel,
                        "Invalid mark format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            studentListModel.addElement(new Student(id, name, mark));


            idField.setText("");
            nameField.setText("");
            markField.setText("");

        });


        deleteButton.addActionListener( al -> {

            int index = studentJList.getSelectedIndex();

            if (index != -1) {
                studentListModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(mainPanel,
                        "Need to select element!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

    }

    private void createGUI() {

        mainPanel = new JPanel(new BorderLayout());

        studentListModel = new DefaultListModel<>();
        studentJList = new JList<>(studentListModel);
        studentJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        inputPanel = new JPanel(new BorderLayout());

        idField = new JTextField(INIT_INPUT_COLUMN);
        JPanel idPanel = new JPanel(new BorderLayout());
        idPanel.add(new JLabel("id:"), BorderLayout.LINE_START);
        idPanel.add(idField, BorderLayout.LINE_END);

        nameField = new JTextField(INIT_INPUT_COLUMN);
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(new JLabel("name:"), BorderLayout.LINE_START);
        namePanel.add(nameField, BorderLayout.LINE_END);

        markField = new JTextField(INIT_INPUT_COLUMN);
        JPanel markPanel = new JPanel(new BorderLayout());
        markPanel.add(new JLabel("mark:"), BorderLayout.LINE_START);
        markPanel.add(markField, BorderLayout.LINE_END);


        inputPanel.add(idPanel, BorderLayout.PAGE_START);
        inputPanel.add(namePanel, BorderLayout.CENTER);
        inputPanel.add(markPanel, BorderLayout.PAGE_END);

        addButton = new JButton("Add item");
        deleteButton  = new JButton("Delete item");

        controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(inputPanel, BorderLayout.PAGE_START);
        controlPanel.add(addButton, BorderLayout.CENTER);
        controlPanel.add(deleteButton, BorderLayout.PAGE_END);


        mainPanel.add(new JScrollPane(studentJList), BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.LINE_END);
    }
}
