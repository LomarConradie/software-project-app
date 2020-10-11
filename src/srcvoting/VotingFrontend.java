package srcvoting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VotingFrontend extends JFrame implements ActionListener {

    DBMS dbms = new DBMS();
    private String currentPersonNumber = "";

    //stores the path to the candidate fotos
    final File folder = new File("assets/candidate");

    //Array used to keep track of the headings above each 
    private int depNameArrayPosition = 1;
    private final String[] departmentNames = {"Sports and Recreation", "Event Coordinator", "Reception", "Chairman", "Vice Chairman", "Student Support", "Academic Support"};
    private final ArrayList<String> candidatePhotos = new ArrayList();

    //model used to insert the names of the candidates, which is then inserted into the combobox
    private final DefaultComboBoxModel recreationModel = new DefaultComboBoxModel(new String[]{""});
    private final DefaultComboBoxModel eventsModel = new DefaultComboBoxModel(new String[]{""});
    private final DefaultComboBoxModel receptionModel = new DefaultComboBoxModel(new String[]{""});
    private final DefaultComboBoxModel chairmanModel = new DefaultComboBoxModel(new String[]{""});
    private final DefaultComboBoxModel viceChairModel = new DefaultComboBoxModel(new String[]{""});
    private final DefaultComboBoxModel studentSupModel = new DefaultComboBoxModel(new String[]{""});
    private final DefaultComboBoxModel academicSupModel = new DefaultComboBoxModel(new String[]{""});

    //panels used to assist in the allignment and placing of the other componenets
    private final JPanel packPanel1 = new JPanel();
    private final JPanel packPanel2 = new JPanel();
    private final JPanel packPanel3 = new JPanel();
    private final JPanel packPanel4 = new JPanel();
    private final JPanel packPanel5 = new JPanel();

    //components
    private final JPanel submitPanel = new JPanel(new GridLayout(1, 5));
    private final JButton submitButton = new JButton("submit");
    private final JPanel backPanel = new JPanel();
    private final JLabel leftSideImage = new JLabel();
    private final JLabel middleImage = new JLabel();
    private final JLabel rightSideImage = new JLabel();

    //loading the images stored in the file to ImageIcons
    private ImageIcon sportsImage = new ImageIcon("assets/img/Sports and Recreation.png");
    private ImageIcon receptionImage = new ImageIcon("assets/img/Reception.png");
    private ImageIcon eventImage = new ImageIcon("assets/img/Event Coordinator.png");
    private ImageIcon studentImage = new ImageIcon("assets/img/Student Support.png");
    private ImageIcon chairmanImage = new ImageIcon("assets/img/Chairman.png");
    private ImageIcon viceChairImage = new ImageIcon("assets/img/Vice Chairman.png");
    private ImageIcon academicImage = new ImageIcon("assets/img/Academic Support.png");

    //New Next and Back Buttons - Dennis Liebenberg
    private JLabel lblNext = new JLabel();
    private JLabel lblBack = new JLabel();

    //compenents used to display images and of the candidates including the comboboxes comtaining their names
    private JPanel backPanelLeftText = new JPanel(new BorderLayout());
    private final JLabel leftSidePersonImage = new JLabel();
    private JLabel leftsideText = new JLabel();
    private JComboBox leftInput = new JComboBox();

    private JPanel backPanelMiddleText = new JPanel(new BorderLayout());
    private final JLabel middlePersonImage = new JLabel();
    private JLabel middleText = new JLabel();
    private JComboBox middleInput = new JComboBox();

    private JPanel backPanelRightText = new JPanel(new BorderLayout());
    private final JLabel rightSidePersonImage = new JLabel();
    private JLabel rightsideText = new JLabel();
    private JComboBox rightInput = new JComboBox();
    //end of components for displaying the images and names

    public VotingFrontend(String currentPersonNum) {
        super("SCR voting app"); //used to set the title of the form 
        try {
            this.setIconImage(ImageIO.read(new File("assets/img/pes.png")));//Set icon to pearson image
        } catch (IOException ex) {
            Logger.getLogger(VotingFrontend.class.getName()).log(Level.SEVERE, null, ex);
        }
        setLayout(new BorderLayout());//set layout of the frame
        dbms.getNameArray();
        currentPersonNumber = currentPersonNum;

        leftSideImage.setAlignmentY(CENTER_ALIGNMENT);
        middleImage.setAlignmentX(CENTER_ALIGNMENT);
        middleImage.setAlignmentY(CENTER_ALIGNMENT);
        rightSideImage.setAlignmentX(CENTER_ALIGNMENT);
        rightSideImage.setAlignmentY(CENTER_ALIGNMENT);

        this.setMinimumSize(new Dimension(1000,700));
        fullscreen(this);//call method to fullscreen the frame

        //calls all the components to be added to the frame
        panelWithSubmitButton();
        mainBackPanel();
        displayImages();
        addComponents();
        fillComboBoxes();
        //listCandidatePhotoFolder(folder);

        //setting the starting comboboxes with names
        leftInput.setModel(recreationModel);
        middleInput.setModel(eventsModel);
        rightInput.setModel(receptionModel);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);//setting frame to visible

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(VotingFrontend.this, "Are you sure you want to close this window?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    VotingFrontend.this.setEnabled(false);
                    VotingFrontend.this.setFocusable(false);
                    LoginFrontend lf = new LoginFrontend();
                    VotingFrontend.this.dispose();
                }
            }
        });
    }

    private void fullscreen(JFrame aFrame) { //method to fullscreen the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    private void panelWithSubmitButton() {//Method used to add the bottom panel including the 
        //buttons to go to next department page as well as submit the 
        //the selected candidates

        submitPanel.setBackground(Color.gray);

        submitButton.setPreferredSize(new Dimension(300, 100));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.addActionListener(this);
        submitButton.setVisible(true);

//  Create the buttons and load icon from image file - Dennis Liebenberg
        lblNext.setPreferredSize(new Dimension(300, 100));
        lblBack.setPreferredSize(new Dimension(300, 100));
        lblNext.setIcon(new ImageIcon(new ImageIcon("assets/img/next2.png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT)));
        lblBack.setIcon(new ImageIcon(new ImageIcon("assets/img/back2.png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT)));
        lblBack.setHorizontalAlignment(SwingConstants.CENTER);
        lblBack.setVerticalAlignment(SwingConstants.CENTER);
        lblNext.setHorizontalAlignment(SwingConstants.CENTER);
        lblNext.setVerticalAlignment(SwingConstants.CENTER);

        // Set alighnment of images on the voting panel
        leftSidePersonImage.setVerticalAlignment(SwingConstants.CENTER);
        leftSidePersonImage.setHorizontalAlignment(SwingConstants.CENTER);

        middlePersonImage.setVerticalAlignment(SwingConstants.CENTER);
        middlePersonImage.setHorizontalAlignment(SwingConstants.CENTER);

        rightSidePersonImage.setVerticalAlignment(SwingConstants.CENTER);
        rightSidePersonImage.setHorizontalAlignment(SwingConstants.CENTER);

        leftSideImage.setVerticalAlignment(SwingConstants.CENTER);
        leftSideImage.setHorizontalAlignment(SwingConstants.CENTER);

        middleImage.setVerticalAlignment(SwingConstants.CENTER);
        middleImage.setHorizontalAlignment(SwingConstants.CENTER);

        rightSideImage.setVerticalAlignment(SwingConstants.CENTER);
        rightSideImage.setHorizontalAlignment(SwingConstants.CENTER);
        //panels used to place the candidate images on
        packPanel4.setPreferredSize(new Dimension(100, 50));
        packPanel4.setOpaque(false);
        packPanel5.setPreferredSize(new Dimension(100, 50));
        packPanel5.setOpaque(false);
        //end of panel code

        //add all components to the frame
        this.add(submitPanel, BorderLayout.SOUTH);

        submitPanel.add(lblBack);
        submitPanel.add(packPanel4);
        submitPanel.add(submitButton);
        submitPanel.add(packPanel5);
        submitPanel.add(lblNext);

    }

    private void mainBackPanel() {
        backPanel.setLayout(new GridLayout(1, 3));
        add(backPanel, BorderLayout.CENTER);
    }

    private void displayImages() {
        //used to set the starting images for the different departments at the start of the screen
        leftSideImage.setIcon(sportsImage);
        leftSideImage.setLayout(new BorderLayout());

        middleImage.setIcon(eventImage);
        middleImage.setLayout(new BorderLayout());

        rightSideImage.setIcon(receptionImage);
        rightSideImage.setLayout(new BorderLayout());

        backPanel.add(leftSideImage);
        backPanel.add(middleImage);
        backPanel.add(rightSideImage);
    }

    private void addComponents() {

        //used to set the headers text which contains the departments at the beginning of execution
        leftsideText.setText(departmentNames[0]);
        leftsideText.setFont(new Font("Arial", Font.PLAIN, 30));
        leftsideText.setHorizontalAlignment(JLabel.CENTER);
        leftsideText.setVerticalAlignment(JLabel.CENTER);

        middleText.setText(departmentNames[1]);
        middleText.setFont(new Font("Arial", Font.PLAIN, 30));
        middleText.setHorizontalAlignment(JLabel.CENTER);
        middleText.setVerticalAlignment(JLabel.CENTER);

        rightsideText.setText(departmentNames[2]);
        rightsideText.setFont(new Font("Arial", Font.PLAIN, 30));
        rightsideText.setHorizontalAlignment(JLabel.CENTER);
        rightsideText.setVerticalAlignment(JLabel.CENTER);
        //end

        backPanelLeftText.setBackground(Color.WHITE);
        backPanelLeftText.setPreferredSize(new Dimension(100, 40));
        backPanelMiddleText.setBackground(Color.WHITE);
        backPanelMiddleText.setPreferredSize(new Dimension(100, 40));
        backPanelRightText.setBackground(Color.WHITE);
        backPanelRightText.setPreferredSize(new Dimension(100, 40));

        //setting the panels that contain the label to display the candidate images at the start of the 
        packPanel1.setOpaque(false);

        packPanel2.setOpaque(false);

        packPanel3.setOpaque(false);

        //adding the labels that contain the department image as well as the panels on which they are displayed
        backPanelLeftText.add(leftsideText, BorderLayout.CENTER);
        backPanelMiddleText.add(middleText, BorderLayout.CENTER);
        backPanelRightText.add(rightsideText, BorderLayout.CENTER);

        leftSideImage.add(backPanelLeftText, BorderLayout.NORTH);
        leftSideImage.add(packPanel1, BorderLayout.CENTER);
        leftSideImage.add(leftInput, BorderLayout.SOUTH);

        packPanel1.setLayout(new BorderLayout());
        packPanel1.add(leftSidePersonImage, BorderLayout.CENTER);
        packPanel2.setLayout(new BorderLayout());
        packPanel2.add(middlePersonImage, BorderLayout.CENTER);
        packPanel3.setLayout(new BorderLayout());
        packPanel3.add(rightSidePersonImage, BorderLayout.CENTER);
        leftSidePersonImage.setToolTipText("Please select a candidate.");
        packPanel1.setAlignmentX(CENTER_ALIGNMENT);
        packPanel1.setAlignmentY(CENTER_ALIGNMENT);
        packPanel2.setAlignmentX(CENTER_ALIGNMENT);
        packPanel2.setAlignmentY(CENTER_ALIGNMENT);
        packPanel3.setAlignmentX(CENTER_ALIGNMENT);
        packPanel3.setAlignmentY(CENTER_ALIGNMENT);

        lblBack.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                lblBack.setBorder(BorderFactory.createLoweredBevelBorder());
                if (depNameArrayPosition > 1) {
                    depNameArrayPosition--;
                }
                if (depNameArrayPosition == 1) {
                    leftsideText.setText(departmentNames[0]);//sports
                    leftSideImage.setIcon(sportsImage);
                    leftInput.setModel(recreationModel);

                    middleText.setText(departmentNames[1]);//event
                    middleImage.setIcon(eventImage);
                    middleInput.setModel(eventsModel);

                    rightsideText.setText(departmentNames[2]);//reception
                    rightSideImage.setIcon(receptionImage);
                    rightInput.setModel(receptionModel);
                    changeImages();

                }
                if (depNameArrayPosition == 2) {
                    leftsideText.setText(departmentNames[3]); //chairman
                    leftSideImage.setIcon(chairmanImage);
                    leftInput.setModel(chairmanModel);

                    middleText.setText(departmentNames[4]);//vice chairman
                    middleImage.setIcon(viceChairImage);
                    middleInput.setModel(viceChairModel);

                    rightsideText.setText(departmentNames[5]); //student support
                    rightSideImage.setIcon(studentImage);
                    rightInput.setModel(studentSupModel);
                    changeImages();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lblBack.setBorder(null);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblBack.setIcon(new ImageIcon(new ImageIcon("assets/img/back.png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT)));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblBack.setIcon(new ImageIcon(new ImageIcon("assets/img/back2.png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT)));
            }
        });

        lblNext.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                lblNext.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lblNext.setBorder(null);

                if (depNameArrayPosition < 3) {
                    depNameArrayPosition++;
                }
                if (depNameArrayPosition == 3) {
                    leftsideText.setText(departmentNames[4]);//vice chairman
                    leftSideImage.setIcon(viceChairImage);
                    leftInput.setModel(viceChairModel);

                    middleText.setText(departmentNames[5]);//student support
                    middleImage.setIcon(studentImage);
                    middleInput.setModel(studentSupModel);

                    rightsideText.setText(departmentNames[6]);//academic support
                    rightSideImage.setIcon(academicImage);
                    rightInput.setModel(academicSupModel);
                    changeImages();
                }

                if (depNameArrayPosition == 2) {
                    leftsideText.setText(departmentNames[3]);//chairman
                    leftSideImage.setIcon(chairmanImage);
                    leftInput.setModel(chairmanModel);

                    middleText.setText(departmentNames[4]);//vice chairman
                    middleImage.setIcon(viceChairImage);
                    middleInput.setModel(viceChairModel);

                    rightsideText.setText(departmentNames[5]);//student support
                    rightSideImage.setIcon(studentImage);
                    rightInput.setModel(studentSupModel);
                    changeImages();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblNext.setIcon(new ImageIcon(new ImageIcon("assets/img/next.png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT)));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblNext.setIcon(new ImageIcon(new ImageIcon("assets/img/next2.png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT)));
            }
        });

        //checks whether a item was selected in the combo box and display the images of candidates
        leftInput.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String candidate = leftInput.getSelectedItem().toString();
                    if (candidate == "") {
                        leftSidePersonImage.setToolTipText("Please select a candidate.");
                        leftSidePersonImage.setIcon(null);
                    } else {
                        //String temp = candidate;
                        //candidate = temp.substring((temp.indexOf(":") + 2), temp.length());
                        String candidateNum = getStudentNum(candidate.substring(0, (candidate.indexOf(":"))));
                        ImageIcon leftCandidate = resizeImage(folder + "/" + candidateNum + ".jpg");
                        System.out.println("URL " + leftCandidate.toString());
                        leftSidePersonImage.setIcon(leftCandidate);
                        leftSidePersonImage.setToolTipText("Biography: " + dbms.Biography(candidateNum));
                    }
                }
            }
        });

        middleImage.add(backPanelMiddleText, BorderLayout.NORTH);
        middleImage.add(packPanel2, BorderLayout.CENTER);
        middleImage.add(middleInput, BorderLayout.SOUTH);
        middlePersonImage.setToolTipText("Please select a candidate.");
        middlePersonImage.setAlignmentX(CENTER_ALIGNMENT);
        middlePersonImage.setAlignmentY(CENTER_ALIGNMENT);

        middleInput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String candidate = middleInput.getSelectedItem().toString();
                    if (candidate == "") {
                        middlePersonImage.setToolTipText("Please select a candidate.");
                        middlePersonImage.setIcon(null);
                    } else {
                        String candidateNum = getStudentNum(candidate.substring(0, (candidate.indexOf(":"))));
                        ImageIcon middleCandidate = resizeImage(folder + "/" + candidateNum + ".jpg");
                        System.out.println("URL " + middleCandidate.toString());
                        middlePersonImage.setIcon(middleCandidate);
                        middlePersonImage.setToolTipText("Biography: " + dbms.Biography(candidateNum));
                    }
                }
            }
        });

        rightSideImage.add(backPanelRightText, BorderLayout.NORTH);
        rightSideImage.add(packPanel3, BorderLayout.CENTER);
        rightSideImage.add(rightInput, BorderLayout.SOUTH);
        rightSidePersonImage.setToolTipText("Please select a candidate.");
        rightSidePersonImage.setAlignmentX(CENTER_ALIGNMENT);
        rightSidePersonImage.setAlignmentY(CENTER_ALIGNMENT);

        rightInput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String candidate = rightInput.getSelectedItem().toString();
                    if (candidate == "") {
                        rightSidePersonImage.setToolTipText("Please select a candidate.");
                        rightSidePersonImage.setIcon(null);
                    } else {
                        String candidateNum = getStudentNum(candidate.substring(0, (candidate.indexOf(":"))));
                        ImageIcon rightCandidate = resizeImage(folder + "/" + candidateNum + ".jpg");
                        System.out.println("URL " + rightCandidate.toString());
                        rightSidePersonImage.setIcon(rightCandidate);
                        rightSidePersonImage.setToolTipText("Biography: " + dbms.Biography(candidateNum));
                    }
                }
            }
        });
        //end of item listener to check state changed of comboboxes
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "submit") {
            //Checks if all the cnadidates are selected. 
            if (recreationModel.getSelectedItem().equals("") || eventsModel.getSelectedItem().equals("") || receptionModel.getSelectedItem().equals("")
                    || chairmanModel.getSelectedItem().equals("") || viceChairModel.getSelectedItem().equals("") || studentSupModel.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Please select 1 candidate per department before clicking submit ");
            } else {
                Component frame = null;
                //Combobox to validate your choices before exit. 
                int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to continue", "An Inane Question", JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    //submit values
                    boolean voteSuccess = true;
                    //Checks if the person has not voted. 
                    if (!dbms.hasVoted(currentPersonNumber)) {
                        //If the person has not yet voted, it updates the database. 
                        if (voteSuccess) {
                            //Boolean is sent back if the votes has been updated successfully. 
                            voteSuccess = dbms.updateVotes(currentPersonNumber, getStudentNum(recreationModel.getSelectedItem().toString()), departmentNames[0]);
                        }
                        if (voteSuccess) {
                            voteSuccess = dbms.updateVotes(currentPersonNumber, getStudentNum(eventsModel.getSelectedItem().toString()), departmentNames[1]);
                        }
                        if (voteSuccess) {
                            voteSuccess = dbms.updateVotes(currentPersonNumber, getStudentNum(receptionModel.getSelectedItem().toString()), departmentNames[2]);
                        }
                        if (voteSuccess) {
                            voteSuccess = dbms.updateVotes(currentPersonNumber, getStudentNum(chairmanModel.getSelectedItem().toString()), departmentNames[3]);
                        }
                        if (voteSuccess) {
                            voteSuccess = dbms.updateVotes(currentPersonNumber, getStudentNum(viceChairModel.getSelectedItem().toString()), departmentNames[4]);
                        }
                        if (voteSuccess) {
                            voteSuccess = dbms.updateVotes(currentPersonNumber, getStudentNum(studentSupModel.getSelectedItem().toString()), departmentNames[5]);
                        }
                        if (voteSuccess) {
                            voteSuccess = dbms.updateVotes(currentPersonNumber, getStudentNum(academicSupModel.getSelectedItem().toString()), departmentNames[6]);
                        }

                        this.setEnabled(false);
                        this.setFocusable(false);
                        //If all the votes have been successfully submitted, you exit the program. 
                        if (voteSuccess) {
                            dbms.updateVoteField(currentPersonNumber);
                            JOptionPane.showMessageDialog(null, "You have Voted successfully!\nThis screen will now close.");
                            VotingFrontend.this.setEnabled(false);
                            VotingFrontend.this.setFocusable(false);
                            LoginFrontend lf = new LoginFrontend();
                            VotingFrontend.this.dispose();
                        } else {
                            //Error message if the votes have not been successfully submitted to the database. 
                            JOptionPane.showMessageDialog(null, "An error occurred while voting! Check logs for details\nThis screen will now close.");
                            VotingFrontend.this.setEnabled(false);
                            VotingFrontend.this.setFocusable(false);
                            LoginFrontend lf = new LoginFrontend();
                            VotingFrontend.this.dispose();
                        }

                    }
                }
            }
            changeImages();

        }

    }
//Method that fills the comboboxes with the candidate data from the database.  

    public void fillComboBoxes() {
        //Calls the method to get the data from the database. 
        dbms.getDetails();
        //Adds the data from the DBMS method to arrays in this method, to add to the comboboxes. 
        String[] departments = dbms.getDepArray();
        String[] surnames = dbms.getSurnameArray();
        String[] names = dbms.getNameArray();
        //Iterates through the array of the candidates. 
        for (int y = 0; y < names.length; y++) {
            //Switch statement checks in what combobox the candidate should be. 
            switch (departments[y]) {
                case "Sports and Recreation":
                    recreationModel.addElement("Candidate " + (y + 1) + " : " + names[y] + " " + surnames[y]);
                    break;
                case "Event Coordinator":
                    eventsModel.addElement("Candidate " + (y + 1) + " : " + names[y] + " " + surnames[y]);
                    break;
                case "Reception":
                    receptionModel.addElement("Candidate " + (y + 1) + " : " + names[y] + " " + surnames[y]);
                    break;
                case "Chairman":
                    chairmanModel.addElement("Candidate " + (y + 1) + " : " + names[y] + " " + surnames[y]);
                    break;
                case "Vice Chairman":
                    viceChairModel.addElement("Candidate " + (y + 1) + " : " + names[y] + " " + surnames[y]);
                    break;
                case "Student Support":
                    studentSupModel.addElement("Candidate " + (y + 1) + " : " + names[y] + " " + surnames[y]);
                    break;
                case "Academic Support":
                    academicSupModel.addElement("Candidate " + (y + 1) + " : " + names[y] + " " + surnames[y]);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "ComboBox Error");
            }
        }
    }
    //Erick Lochner
    //Gets the student number from the comboboxes, and returns it. 

    public String getStudentNum(String details) {
        String[] candidNumbers = dbms.getNumArray();
        String[] candNumArr = details.split(" ", 3);
        int candidNum = Integer.parseInt(candNumArr[1]);
        String candidNumFinal = candidNumbers[candidNum - 1];
        return candidNumFinal;
    }

    private void changeImages() {
        String candidateL = leftInput.getSelectedItem().toString();
        if (candidateL != "") {
            String candidateNum = getStudentNum(candidateL.substring(0, (candidateL.indexOf(":"))));
            System.out.println(candidateL);
            ImageIcon leftCandidate = resizeImage(folder + "/" + candidateNum + ".jpg");
            leftSidePersonImage.setIcon(leftCandidate);
        } else {
            //System.out.println(candidate);
            //ImageIcon leftCandidate = new ImageIcon(folder + "/" + candidate + ".jpg");
            leftSidePersonImage.setIcon(null);
        }

        String candidateM = middleInput.getSelectedItem().toString();
        if (candidateM != "") {
            String candidateNum = getStudentNum(candidateM.substring(0, (candidateM.indexOf(":"))));
            System.out.println(candidateM);
            ImageIcon leftCandidate = resizeImage(folder + "/" + candidateNum + ".jpg");
            middlePersonImage.setIcon(leftCandidate);
        } else {
            //System.out.println(candidate);
            //ImageIcon leftCandidate = new ImageIcon(folder + "/" + candidate + ".jpg");
            middlePersonImage.setIcon(null);
        }

        String candidateR = rightInput.getSelectedItem().toString();
        if (candidateR != "") {
            String candidateNum = getStudentNum(candidateR.substring(0, (candidateR.indexOf(":"))));
            System.out.println(candidateR);
            ImageIcon leftCandidate = resizeImage(folder + "/" + candidateNum + ".jpg");
            rightSidePersonImage.setIcon(leftCandidate);
        } else {
            //System.out.println(candidate);
            //ImageIcon leftCandidate = new ImageIcon(folder + "/" + candidate + ".jpg");
            rightSidePersonImage.setIcon(null);
        }
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

            Image img = candidatePic.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
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
