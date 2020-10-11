package srcvoting;

import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

class AdminFrontend extends JFrame {

    DBMS dbms = new DBMS();
    DecimalFormat df = new DecimalFormat("#.00");
    private final String[] departmentNames = {"Sports and Recreation", "Event Coordinator", "Reception", "Chairman", "Vice Chairman", "Student Support", "Academic Support"};

    JLabel Title = new JLabel();
    JLabel Name_lbl = new JLabel();
    JTextField C_Name = new JTextField();
    JLabel Surname_lbl = new JLabel();
    JTextField C_Surname = new JTextField();
    JLabel StudentNum_lbl = new JLabel();
    JTextField C_StudentNum = new JTextField();
    JLabel Email_lbl = new JLabel();
    JTextField C_Email = new JTextField();
    JLabel CellNum_lbl = new JLabel();
    JTextField C_CellNum = new JTextField();
    JLabel Department_lbl = new JLabel();
    JComboBox C_Department = new JComboBox();
    JLabel Bio_lbl = new JLabel();
    JTextArea C_Bio = new JTextArea();
    JButton UploadPhoto = new JButton();
    JButton Submit = new JButton();
    JLabel imageUploadLabel;

    JButton resetVotes = new JButton("Reset Votes");
    JButton deleteCandidate = new JButton("Delete All Candidates");

    boolean ImageUploaded;

    public AdminFrontend() {
        super("SRC VOTING LOGIN");
        JTabbedPane TabPane = new JTabbedPane();
        JTabbedPane TabPane2 = new JTabbedPane();
        JPanel Candidate_pnl = new JPanel();
        final JFileChooser fc = new JFileChooser();

//////////////////////////////////////////////////////////////////////////////////////////////////////
//Images and background
/////////////////////////////////////////////////////////////////////////////////////////////////////
        BufferedImage icon = null;
        BufferedImage image = null;
        try {
            icon = ImageIO.read(new File("assets/img/pes.png"));
            this.setIconImage(icon);//Set icon to pearson image
            image = ImageIO.read(new File("assets/img/Pearson.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(SignupFrontend.class.getName()).log(Level.SEVERE, null, ex);
            loadToErrorLog(ex);
        }

        final BufferedImage finaleImage = image;
        final BufferedImage finaleIcon = icon;

        //Loads background into Buffer
        /**
         * The image gets Drawn by the paintComponent Every time the system
         * calls it This allows the image to be resized to the size of the frame
         * The Panel Background is created here as well
         */
        JPanel Background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int destinationWidth = this.getWidth();
                //Get the width of the frame currently
                int destinationHeight = this.getHeight();
                //Get the height of the frame currently
                //Draw image to the specified size
                g.drawImage(finaleImage, 0, 0, destinationWidth, destinationHeight, 0,
                        0, finaleImage.getWidth(null), finaleImage.getHeight(null), null);
            }
        };
        Background.setLayout(new GridBagLayout());
        Background.add(TabPane);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Frame size and add panels
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel Results_pnl = new JPanel();

        Results_pnl.setLayout(new BoxLayout(Results_pnl, BoxLayout.Y_AXIS));
        JScrollPane AddCandidateScrollPane = new JScrollPane(Candidate_pnl);
        JScrollPane ResultsScrollPane = new JScrollPane(Results_pnl);

        AddCandidateScrollPane.setMinimumSize(new Dimension(700, 650));
        ResultsScrollPane.setMinimumSize(new Dimension(700, 650));
        
        TabPane.addTab("AddCandidatePage", AddCandidateScrollPane);
        TabPane.addTab("ResultsPage", ResultsScrollPane);
        
        this.setSize(920, 780);
        this.setMinimumSize(new java.awt.Dimension(920, 780));
        this.setLocationRelativeTo(null);

        BoxLayout boxLayout = new BoxLayout(Candidate_pnl, BoxLayout.Y_AXIS);
        Candidate_pnl.setLayout(boxLayout);

        boxLayout = new BoxLayout(Results_pnl, BoxLayout.Y_AXIS);
        Results_pnl.setLayout(boxLayout);
        Candidate_pnl.setBorder(new EmptyBorder(new Insets(0, 120, 0, 120)));

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Candidate add components
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Title.setText("Add Candidate");
        Title.setForeground(Color.BLACK);
        Title.setFont(new Font("Arial", Font.PLAIN, 34));
        
        Candidate_pnl.setBackground(Color.white);
        Name_lbl.setText("Name");
        Name_lbl.setForeground(Color.BLACK);
        Name_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        C_Name.setBounds(555, 160, 290, 23);
        Surname_lbl.setText("Surname");
        Surname_lbl.setForeground(Color.BLACK);
        Surname_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        C_Surname.setSize(20, 60);
        StudentNum_lbl.setText("Student Number");
        StudentNum_lbl.setForeground(Color.BLACK);
        StudentNum_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        C_StudentNum.setSize(20, 60);
        Email_lbl.setText("Email");
        Email_lbl.setForeground(Color.BLACK);
        Email_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        C_Email.setSize(20, 60);
        CellNum_lbl.setText("Cellphone Number");
        CellNum_lbl.setForeground(Color.BLACK);
        CellNum_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        C_CellNum.setSize(20, 60);
        Department_lbl.setText("Department");
        Department_lbl.setForeground(Color.BLACK);
        Department_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        C_Department.setSize(20, 60);
        C_Department.setModel(new javax.swing.DefaultComboBoxModel<>(departmentNames));
        Bio_lbl.setText("Biography");
        Bio_lbl.setForeground(Color.BLACK);
        Bio_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        C_Bio.setSize(40, 100);
        C_Bio.setLineWrap(true);
        C_Bio.setWrapStyleWord(true);
        imageUploadLabel = new JLabel("Please upload an image to be able to submit.");

        Submit.setSize(125, 150);
        UploadPhoto.setText("Upload Photo");
        UploadPhoto.setBackground(new Color(0, 126, 162));
        UploadPhoto.setForeground(Color.white);
        Submit.setText("Submit");
        Submit.setBackground(new Color(0, 126, 162));
        Submit.setForeground(Color.white);
        Submit.setEnabled(false);

        Title.setAlignmentX(CENTER_ALIGNMENT);
        Title.setAlignmentY(0.1f);
        Name_lbl.setAlignmentX(CENTER_ALIGNMENT);
        Name_lbl.setAlignmentY(CENTER_ALIGNMENT);
        C_Name.setAlignmentX(CENTER_ALIGNMENT);
        C_Name.setAlignmentY(CENTER_ALIGNMENT);
        Surname_lbl.setAlignmentX(CENTER_ALIGNMENT);
        Surname_lbl.setAlignmentY(CENTER_ALIGNMENT);
        C_Surname.setAlignmentX(CENTER_ALIGNMENT);
        C_Surname.setAlignmentY(CENTER_ALIGNMENT);
        StudentNum_lbl.setAlignmentX(CENTER_ALIGNMENT);
        StudentNum_lbl.setAlignmentY(CENTER_ALIGNMENT);
        Email_lbl.setAlignmentX(CENTER_ALIGNMENT);
        Email_lbl.setAlignmentY(CENTER_ALIGNMENT);
        CellNum_lbl.setAlignmentX(CENTER_ALIGNMENT);
        CellNum_lbl.setAlignmentY(CENTER_ALIGNMENT);
        C_StudentNum.setAlignmentX(CENTER_ALIGNMENT);
        C_StudentNum.setAlignmentY(CENTER_ALIGNMENT);
        Department_lbl.setAlignmentX(CENTER_ALIGNMENT);
        Department_lbl.setAlignmentY(CENTER_ALIGNMENT);
        C_Department.setAlignmentX(CENTER_ALIGNMENT);
        C_Department.setAlignmentY(CENTER_ALIGNMENT);
        Bio_lbl.setAlignmentX(CENTER_ALIGNMENT);
        Bio_lbl.setAlignmentY(CENTER_ALIGNMENT);
        C_Bio.setAlignmentX(CENTER_ALIGNMENT);
        C_Bio.setAlignmentY(CENTER_ALIGNMENT);
        imageUploadLabel.setAlignmentX(CENTER_ALIGNMENT);
        imageUploadLabel.setAlignmentY(CENTER_ALIGNMENT);
        UploadPhoto.setAlignmentX(CENTER_ALIGNMENT);
        UploadPhoto.setAlignmentY(CENTER_ALIGNMENT);
        Submit.setAlignmentX(CENTER_ALIGNMENT);
        Submit.setAlignmentY(CENTER_ALIGNMENT);

        Candidate_pnl.add(Title);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 10)));//gaps
        Candidate_pnl.add(Name_lbl);
        Candidate_pnl.add(C_Name);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));
        Candidate_pnl.add(Surname_lbl);
        Candidate_pnl.add(C_Surname);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));
        Candidate_pnl.add(StudentNum_lbl);
        Candidate_pnl.add(C_StudentNum);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));

        Candidate_pnl.add(Email_lbl);
        Candidate_pnl.add(C_Email);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));
        Candidate_pnl.add(CellNum_lbl);
        Candidate_pnl.add(C_CellNum);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));

        Candidate_pnl.add(Department_lbl);
        Candidate_pnl.add(C_Department);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));
        Candidate_pnl.add(Bio_lbl);
        Candidate_pnl.add(C_Bio);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));
        Candidate_pnl.add(imageUploadLabel);
        Candidate_pnl.add(UploadPhoto);
        Candidate_pnl.add(Box.createRigidArea(new Dimension(10, 20)));
        Candidate_pnl.add(Submit);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
//Results page components
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel R_Title = new JLabel();
        JPanel ResultsDepartment_pnl = new JPanel();

        Results_pnl.setBackground(Color.white);

        R_Title.setText("Results");
        R_Title.setForeground(Color.BLACK);
        R_Title.setFont(new Font("Arial", Font.PLAIN, 34));
        R_Title.setAlignmentX(CENTER_ALIGNMENT);
        R_Title.setAlignmentY(CENTER_ALIGNMENT);

        ResultsDepartment_pnl.setBackground(Color.LIGHT_GRAY);
        ResultsDepartment_pnl.setPreferredSize(new Dimension(600, 250));

        Results_pnl.add(R_Title);
////////////////////////////////////////////////////////////////////////////////
//Panel for results department
////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < departmentNames.length; i++) {
            ResultsDepartment_pnl = new JPanel();
            ResultsDepartment_pnl.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            ResultsDepartment_pnl.setBackground(Color.LIGHT_GRAY);
            ResultsDepartment_pnl.setBorder(BorderFactory.createLineBorder(Color.black));

            String[] topCandidate = dbms.getTopCandidatesForDepartment(departmentNames[i]);

            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 3;
            c.gridheight = 1;
            JLabel depName = new JLabel(departmentNames[i]);
            depName.setFont(new Font("Arial", Font.PLAIN, 30));
            ResultsDepartment_pnl.add(depName, c);

            if (topCandidate[i].equals("No Votes")) {
                c.gridx = 1;
                c.gridy = 1;
                c.gridwidth = 3;
                c.gridheight = 1;
                JLabel noVotes = new JLabel("NO VOTES YET");

                ResultsDepartment_pnl.add(noVotes, c);
            } else {

                c.fill = GridBagConstraints.HORIZONTAL;

                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 2;
                c.gridheight = 7;
                JLabel pic = new JLabel();
                pic.setIcon(resizeImage("assets/candidate/" + topCandidate[4] + ".jpg"));
                ResultsDepartment_pnl.add(pic, c);

                c.gridx = 2;
                c.gridy = 1;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel("Candidate Name: "), c);

                c.gridx = 2;
                c.gridy = 2;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel("Vote Count: "), c);

                c.gridx = 2;
                c.gridy = 3;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel("Candidate Student Number: "), c);

                c.gridx = 2;
                c.gridy = 4;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel("Candidate Email: "), c);

                c.gridx = 2;
                c.gridy = 5;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel("Candidate Contact Number: "), c);

                c.gridx = 2;
                c.gridy = 6;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel("Bio: "), c);

                c.gridx = 3;
                c.gridy = 1;
                c.gridwidth = 1;
                c.gridheight = 1;
                JLabel nameSurname = new JLabel(topCandidate[0] + " " + topCandidate[1]);
                nameSurname.setFont(new Font("Arial", Font.BOLD, 12));
                ResultsDepartment_pnl.add(nameSurname, c);

                c.gridx = 3;
                c.gridy = 2;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel(topCandidate[6] + " ( " + df.format(Double.parseDouble(topCandidate[7])) + "% )"), c);

                c.gridx = 3;
                c.gridy = 3;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel(topCandidate[4]), c);

                c.gridx = 3;
                c.gridy = 4;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel(topCandidate[3]), c);

                c.gridx = 3;
                c.gridy = 5;
                c.gridwidth = 1;
                c.gridheight = 1;
                ResultsDepartment_pnl.add(new JLabel(topCandidate[2]), c);

                c.gridx = 3;
                c.gridy = 6;
                c.gridwidth = 1;
                c.gridheight = 2;
                c.anchor = GridBagConstraints.NORTHWEST;
                JTextArea bioText = new JTextArea(topCandidate[8]);
                JScrollPane js = new JScrollPane(bioText);
                js.setBorder(null);
                bioText.setMaximumSize(new Dimension(100, 200));
                bioText.setLineWrap(true);
                bioText.setWrapStyleWord(true);
                bioText.setOpaque(false);
                bioText.setBackground(Color.lightGray);
                bioText.setBorder(null);
                bioText.setFocusable(false);
                ResultsDepartment_pnl.add(js, c);

            }
            Results_pnl.add(ResultsDepartment_pnl);
        }
        JPanel resetButtons = new JPanel();
        resetButtons.setLayout(new BoxLayout(resetButtons, BoxLayout.Y_AXIS));
        resetButtons.setBackground(Color.white);
        resetButtons.add(resetVotes);
        resetButtons.add(deleteCandidate);

        resetVotes.setBackground(new Color(0, 126, 162));
        deleteCandidate.setBackground(new Color(0, 126, 162));
        resetVotes.setForeground(Color.white);
        deleteCandidate.setForeground(Color.white);

        resetVotes.setAlignmentX(CENTER_ALIGNMENT);
        resetVotes.setAlignmentY(CENTER_ALIGNMENT);
        deleteCandidate.setAlignmentX(CENTER_ALIGNMENT);
        deleteCandidate.setAlignmentY(CENTER_ALIGNMENT);

        Results_pnl.add(resetButtons);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.add(Background);

        resetVotes.addActionListener((e) -> {
            DBMS dbms = new DBMS();
            if (dbms.resetVotes()) {
                JOptionPane.showMessageDialog(null, "All Votes have been cleared successfully!\nThis window will now close.");
                this.setEnabled(false);
                this.setFocusable(false);
                LoginFrontend lf = new LoginFrontend();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Votes could not be cleared.\nPlease try again or contact the administrator.");
            }

        });

        deleteCandidate.addActionListener((e) -> {
            DBMS dbms = new DBMS();
            if (dbms.deleteAllCandidates()) {
                JOptionPane.showMessageDialog(null, "All Candidate have been deleted successfully along with their acompanying votes!\nThis window will now close.");
                this.setEnabled(false);
                this.setFocusable(false);
                LoginFrontend lf = new LoginFrontend();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Candidates could not be deleted.\nPlease try again or contact the administrator.");
            }

        });

        UploadPhoto.addActionListener((e) -> {
            //In response to a button click:
            FileFilter filter = new FileNameExtensionFilter("JPEG & PNG Image Files", "jpg", "png");
            fc.setFileFilter(filter);
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                DBMS dbms = new DBMS();
                dbms.copyFileUsingStream(file, C_StudentNum.getText().toUpperCase());
                ImageUploaded = true;
                Submit.setEnabled(true);
                imageUploadLabel.setText("");
                JOptionPane.showMessageDialog(null, "Image Successfully added, you may now proceed by submitting");
            } else {
                System.out.println("Upload cancelled");
            }
        });

        Submit.addActionListener((e) -> {
            if (checkCandidateInputError()) {
                JOptionPane.showMessageDialog(null, "Please check that the candidate input fields filled in and are correct and try again.\nAlso please make sure that you have uploaded an image for the candidate.");
            } else {
                DBMS dbms = new DBMS();
                if (dbms.addNewCandidate(C_StudentNum.getText().toUpperCase(), C_Name.getText(), C_Surname.getText(), C_CellNum.getText(), C_Email.getText(), C_Department.getSelectedItem().toString(), C_Bio.getText())) {
                    JOptionPane.showMessageDialog(null, "Candidate added Succesfully!");
                    ImageUploaded = false;
                    C_Name.setText("");
                    C_Surname.setText("");
                    C_StudentNum.setText("");
                    C_Email.setText("");
                    C_CellNum.setText("");
                    C_Bio.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Candidate could not be added at this time.\nEnsure that you entered the details correctly or contact the administrator");
                }

            }
        });

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(AdminFrontend.this, "Are you sure you want to close this window?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    AdminFrontend.this.setEnabled(false);
                    AdminFrontend.this.setFocusable(false);
                    LoginFrontend lf = new LoginFrontend();
                    AdminFrontend.this.dispose();
                }
            }
        });

    }

    public boolean checkCandidateInputError() {
        boolean error = false;

        if (C_Name.getText().equals("")) {
            error = true;
        }
        if (C_Surname.getText().equals("")) {
            error = true;
        }
        if (C_StudentNum.getText().equals("")) {
            error = true;
        }
        if (C_Email.getText().equals("")) {
            error = true;
        }
        if (C_CellNum.getText().equals("")) {
            error = true;
        }
        if (C_Bio.getText().equals("")) {
            error = true;
        }
        if (!ImageUploaded) {
            error = true;
        }
        return error;
    }

    public ImageIcon resizeImage(String filePath) {
        BufferedImage candidatePic;
        ImageIcon newIcon = null;
        try {
            File imgFile = new File(filePath);
            if (imgFile.exists() && !imgFile.isDirectory()) {
                // do something
                candidatePic = ImageIO.read(imgFile);
            } else {
                candidatePic = ImageIO.read(new File("assets/candidate/NULL.jpg"));
            }

            Image img = candidatePic.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
            Graphics g = resized.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            newIcon = new ImageIcon(resized);
        } catch (IOException ex) {
            Logger.getLogger(AdminFrontend.class.getName()).log(Level.SEVERE, null, ex);
            loadToErrorLog(ex);
        }
        return newIcon;
    }

    private void loadToErrorLog(IOException ex) {
        BufferedWriter bw = null;

        try {
            // APPEND MODE SET HERE
            bw = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
            bw.write("Error date:" + LocalDateTime.now() + " - Error:" + ex);
            bw.newLine();
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        } // end try/catch/finally
    }

}
