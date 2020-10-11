/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srcvoting;
//test comment

import java.awt.Color;
import static java.awt.Color.white;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Dennis
 */
public class SignupFrontend {

    final Color errorColor = new Color(255, 128, 128);

    public SignupFrontend(JFrame loginScreen) {

        JFrame SignUp = new JFrame();
        SignUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        SignUp.setMinimumSize(new Dimension(600, 600));
        //SignUp.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel Container = new JPanel();
        //Loads pearson icon into icon Buffer
        BufferedImage icon = null;
        BufferedImage image = null;
        try {
            icon = ImageIO.read(new File("assets/img/pes.png"));
            SignUp.setIconImage(icon);//Set icon to pearson image
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
        /**
         * The Background panel will use the GridBagLayout because its the best
         * LayoutManager to keep a component centered The main panel for the
         * components are added on to the background panel to keep it centered
         * Despite the forms size
         */

        BoxLayout boxLayout = new BoxLayout(Container, BoxLayout.Y_AXIS);
        Container.setLayout(boxLayout);
        Container.setBorder(new EmptyBorder(new Insets(50, 80, 50, 80)));
//The empty border leaves a transparent margin without any associated drawing.
//This ensures the frame closes
        SignUp.setTitle("SRC VOTING SIGN UP");//Title of the Login Frame
        SignUp.setSize(500, 500);//The starting size of the Frame
        SignUp.setLocationRelativeTo(null);

        JLabel Error = new JLabel();
        JLabel Title = new JLabel("Sign UP");
        JLabel lblName = new JLabel("Name");
        JLabel lblSurname = new JLabel("Surname");
        JLabel lblStudentNum = new JLabel("Student Number");
        JLabel lblEmail = new JLabel("Email");
        JLabel lblPassword = new JLabel("Password");
        JLabel lblConPassword = new JLabel("Retype Password");
        JLabel lblPhone = new JLabel("Phone Number");

        Title.setFont(new Font("Arial", Font.BOLD, 30));
        lblName.setFont(new Font("Arial", Font.BOLD, 12));
        lblPhone.setFont(new Font("Arial", Font.BOLD, 12));
        lblSurname.setFont(new Font("Arial", Font.BOLD, 12));
        lblStudentNum.setFont(new Font("Arial", Font.BOLD, 12));
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        lblConPassword.setFont(new Font("Arial", Font.BOLD, 12));
        Error.setFont(new Font("Arial", Font.BOLD, 12));

        JTextField Name = new JTextField();
        JTextField Surname = new JTextField();
        JTextField StudentNum = new JTextField();
        JTextField Email = new JTextField();
        JPasswordField Password = new JPasswordField();
        JPasswordField ConPassword = new JPasswordField();
        JTextField Phone = new JTextField();

        Password.setEchoChar('•');
        ConPassword.setEchoChar('•');

        JButton btnSignUp = new JButton();

        btnSignUp.setText("SIGN UP");
        Title.setForeground(Color.gray);
        Error.setForeground(Color.red);
        Container.setBackground(Color.white);

        /*Container.setSize(250, 220);
        Name.setMaximumSize(Container.getPreferredSize());
        Surname.setMaximumSize(Container.getPreferredSize());
        StudentNum.setMaximumSize(Container.getPreferredSize());
        Email.setMaximumSize(Container.getPreferredSize());
        Password.setMaximumSize(Container.getPreferredSize());
        ConPassword.setMaximumSize(Container.getPreferredSize());
        btnSignUp.setMaximumSize(Container.getPreferredSize());
        Phone.setMaximumSize(Container.getPreferredSize());

        Name.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Surname.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        StudentNum.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Email.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Password.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        ConPassword.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Phone.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        Name.setSize(30, 60);
        Surname.setSize(30, 60);
        StudentNum.setSize(30, 60);
        Email.setSize(30, 60);
        Password.setSize(30, 60);
        ConPassword.setSize(30, 60);
        Phone.setSize(30, 60);*/
        btnSignUp.setSize(200, 30);

        Title.setAlignmentX(CENTER_ALIGNMENT);
        Title.setAlignmentY(CENTER_ALIGNMENT);
        lblName.setAlignmentY(CENTER_ALIGNMENT);
        lblName.setAlignmentX(CENTER_ALIGNMENT);
        lblSurname.setAlignmentY(CENTER_ALIGNMENT);
        lblSurname.setAlignmentX(CENTER_ALIGNMENT);
        lblPhone.setAlignmentY(CENTER_ALIGNMENT);
        lblPhone.setAlignmentX(CENTER_ALIGNMENT);
        lblStudentNum.setAlignmentY(CENTER_ALIGNMENT);
        lblStudentNum.setAlignmentX(CENTER_ALIGNMENT);
        lblEmail.setAlignmentY(CENTER_ALIGNMENT);
        lblEmail.setAlignmentX(CENTER_ALIGNMENT);
        lblPassword.setAlignmentY(CENTER_ALIGNMENT);
        lblPassword.setAlignmentX(CENTER_ALIGNMENT);
        lblConPassword.setAlignmentY(CENTER_ALIGNMENT);
        lblConPassword.setAlignmentX(CENTER_ALIGNMENT);
        btnSignUp.setAlignmentX(CENTER_ALIGNMENT);
        btnSignUp.setAlignmentY(CENTER_ALIGNMENT);
        Error.setAlignmentX(CENTER_ALIGNMENT);
        Error.setAlignmentY(CENTER_ALIGNMENT);
        Phone.setAlignmentX(CENTER_ALIGNMENT);
        Phone.setAlignmentY(CENTER_ALIGNMENT);

        Color Pearson_Blue = new Color(0, 126, 162);

        btnSignUp.setBackground(Pearson_Blue);
        btnSignUp.setForeground(white);

        Container.add(Error);
        Container.add(Box.createRigidArea(new Dimension(5, 5)));
        Container.add(Title);
        Container.add(Box.createRigidArea(new Dimension(20, 20)));
        Container.add(lblName);
        Container.add(Box.createRigidArea(new Dimension(7, 2)));
        Container.add(Name);
        Container.add(Box.createRigidArea(new Dimension(10, 10)));
        Container.add(lblSurname);
        Container.add(Box.createRigidArea(new Dimension(7, 2)));
        Container.add(Surname);
        Container.add(Box.createRigidArea(new Dimension(10, 10)));
        Container.add(lblPhone);
        Container.add(Box.createRigidArea(new Dimension(7, 2)));
        Container.add(Phone);
        Container.add(Box.createRigidArea(new Dimension(10, 10)));
        Container.add(lblStudentNum);
        Container.add(Box.createRigidArea(new Dimension(7, 2)));
        Container.add(StudentNum);
        Container.add(Box.createRigidArea(new Dimension(10, 10)));
        Container.add(lblEmail);
        Container.add(Box.createRigidArea(new Dimension(7, 2)));
        Container.add(Email);
        Container.add(Box.createRigidArea(new Dimension(10, 10)));
        Container.add(lblPassword);
        Container.add(Box.createRigidArea(new Dimension(7, 2)));
        Container.add(Password);
        Container.add(Box.createRigidArea(new Dimension(10, 10)));
        Container.add(lblConPassword);
        Container.add(Box.createRigidArea(new Dimension(7, 2)));
        Container.add(ConPassword);
        Container.add(Box.createRigidArea(new Dimension(10, 30)));
        Container.add(btnSignUp);

        btnSignUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean error = false;
                String name = Name.getText();
                if (name.equals("") || !name.matches("[A-Za-z0-9 -]+") || name.length() > 50) {
                    Name.setBackground(errorColor);
                    //     Error.setText("Please fill in the fields hiligheted in red :)");
                    error = true;
                } else {
                    Name.setBackground(white);
                    //  Error.setText("");
                }

                if (Surname.getText().trim().isEmpty()) {
                    Surname.setBackground(errorColor);
                    //Error.setText("Please fill in the fields hiligheted in red :)");
                    error = true;
                } else {
                    Surname.setBackground(white);
                    //        Error.setText("");
                }

                if (StudentNum.getText().trim().equals("")) {
                    StudentNum.setBackground(errorColor);
                    //  Error.setText("Please fill in the fields hiligheted in red :)");
                    error = true;
                } else {
                    StudentNum.setBackground(white);
                    //  Error.setText("");
                }

                if (Email.getText().trim().isEmpty()) {
                    Email.setBackground(errorColor);
                    //  Error.setText("Please fill in the fields hiligheted in red :)");
                    error = true;
                } else {
                    Email.setBackground(white);
                    //  Error.setText("");
                }

                if (Password.getText().trim().isEmpty()) {
                    Password.setBackground(errorColor);
                    //   Error.setText("Please fill in the fields hiligheted in red :)");
                    error = true;
                } else {
                    Password.setBackground(white);
                    // Error.setText("");
                }

                if (ConPassword.getText().trim().isEmpty()) {
                    ConPassword.setBackground(errorColor);
                    //    Error.setText("Please fill in the fields hiligheted in red :)");
                    error = true;
                } else {
                    ConPassword.setBackground(white);
                    //  Error.setText("");
                }
                if (Phone.getText().trim().isEmpty()) {
                    Phone.setBackground(errorColor);
                } else {
                    Phone.setBackground(white);
                }
                // If any fields with input error is found, it will not allow the user to continue
                if (!error && validateStudentNumber(StudentNum.getText()) == true) {

                    //Check that passwords match
                    if (ConPassword.getText().equals(Password.getText())) {
                        DBMS dbms = new DBMS();
                        //Add user to database and if completed successfully, will close the window and open the login screen
                        if (dbms.addNewStudent(StudentNum.getText(), Name.getText(), Surname.getText(), Phone.getText(), Email.getText(), Password.getText())) {
                            JOptionPane.showMessageDialog(null, "Sign Up Successfull!!");
                            SignUp.dispose();
                            loginScreen.setEnabled(true);
                            loginScreen.setFocusable(true);
                            loginScreen.requestFocus();
                        }
                    } else {
                         JOptionPane.showMessageDialog(null, "Please ensure the passwords match"); 
                        
                    }
                }
                else {
                    if (validateStudentNumber(StudentNum.getText()) == false) { 
                            JOptionPane.showMessageDialog(null, "Invalid Student Number, it does not exist");
                        } else{
                        JOptionPane.showMessageDialog(null, "Please ensure you are using valid characters for you credentials.");
                    }
                }

            }//Click

        });

        //Container.add(Box.createRigidArea(new Dimension(20, 20)));
        Background.setLayout(new GridBagLayout());
        Background.add(Container);

        SignUp.add(Background);
        SignUp.setVisible(true);

        /*Window listener tha overrides the default close operation, 
        * so that it can close the current window, while re-opening the previous one
         */
        SignUp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                loginScreen.setEnabled(true);
                loginScreen.setFocusable(true);
                SignUp.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }//singup method

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
//Erick Lochner
    //Test the new student number to check if it exists at the institute.  

    private boolean validateStudentNumber(String studentNum) {
        //Scanner is created to read file. 
        Scanner sc = null;
        //Flag which will be returend to validate method. 
        boolean studentFlag = false;
        try {
            //FileReader is created to read file in. 
            sc = new Scanner(new FileReader("StudentNumbers.txt"));
            //while loop iterates through the file. 
            while (sc.hasNextLine()) {
                String results = sc.nextLine();
                String split[] = results.split("#");
                String studNum = split[0];
                if (studentNum.equals(studNum)) {
                    studentFlag = true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Text file error, cannot validate student number. ");
        }
        //Returns the boolean. 
        return studentFlag;
    }
}//singup class
