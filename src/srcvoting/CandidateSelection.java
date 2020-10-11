package srcvoting;

import java.awt.Color;
import static java.awt.Color.white;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CandidateSelection extends JFrame implements ActionListener  
{
    public CandidateSelection(String Department) 
    {
        // Create the new instance of the frame in order
        //to see and vote for candidates
        JFrame CandidateForm = new JFrame(); 
        
        //Setting the default close operation to nothing
        //Set the default size of the frame 
        CandidateForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        CandidateForm.setMinimumSize(new Dimension(600, 600));
        
        //Create a container in order to add the components 
        //that will allow you to select a candidate and view details about them
        //as well as submit your selected candidate
        JPanel Container = new JPanel();
        
         
        //Add image to the jframe 
        BufferedImage icon = null;
        BufferedImage image = null;
        try {
            icon = ImageIO.read(new File("assets/img/pes.png"));
            CandidateForm.setIconImage(icon);//Set icon to pearson image
            image = ImageIO.read(new File("assets/img/Pearson.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(CandidateSelection.class.getName()).log(Level.SEVERE, null, ex);
            
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
        //set the title of the form to the selected department of candidates
        CandidateForm.setTitle(Department);
        
        //set the layout of the backgound in order to set the container in the middle of the screen
        Background.setLayout(new GridBagLayout());
        
        //Set container panel color to white
        Container.setBackground(Color.white);
        
        //set container layout to gridlayout in order to split the JList and the rest of the components
        Container.setLayout(new GridLayout(1, 2));
        Container.setBorder(new EmptyBorder(new Insets(50, 80, 50, 80)));
        
        //Add background image to the JFrame
        CandidateForm.add(Background);
        //add container panel to background
        Background.add(Container);
        
        //create a list in order to add candidates
        //DefaultListModel<String> candidates = new DefaultListModel<String>();
        //add this list to JList
        JList candidateNames = new JList();
        
        //add jpanel in order to add list
        JPanel scrollListPanel = new JPanel();
        scrollListPanel.setBackground(Color.WHITE);
        scrollListPanel.add(candidateNames);
        
        //add border line to panel
        Border blackline = BorderFactory.createLineBorder(Color.black);
        scrollListPanel.setBorder(blackline);
        //Set minimum size of list
        candidateNames.setMinimumSize(new Dimension(100, 300));
        //Add candidate names
        //candidates.addElement("jan");
        
        //Add panel to container
        Container.add(scrollListPanel);
        
        
        JPanel DetailStageingContainer = new JPanel();
        DetailStageingContainer.setBackground(Color.LIGHT_GRAY);
        BoxLayout boxLayout = new BoxLayout(DetailStageingContainer, BoxLayout.Y_AXIS);
        DetailStageingContainer.setLayout(boxLayout);
        DetailStageingContainer.setBorder(new EmptyBorder(new Insets(0, 30, 0, 30)));
        Container.add(DetailStageingContainer);
        
        //components
        JLabel candidate_lbl = new JLabel("Candidate");
        JLabel candidate_img = new JLabel();
        JLabel NameSurname_lbl = new JLabel("Name and Surname");
        JTextField NameSurname_txf = new JTextField();
        JLabel Biography_lbl = new JLabel("Biography");
        JTextArea Biography_txa = new JTextArea();
        JButton okay_btn = new JButton();
        //button settings
        okay_btn.setText("Submit");
        Color Pearson_Blue = new Color(0, 126, 162);
        okay_btn.setBackground(Pearson_Blue);
        okay_btn.setForeground(white);
        //Set allignments of components
        candidate_lbl.setAlignmentX(CENTER_ALIGNMENT);
        candidate_lbl.setAlignmentY(CENTER_ALIGNMENT);
        candidate_img.setAlignmentX(CENTER_ALIGNMENT);
        candidate_img.setAlignmentY(CENTER_ALIGNMENT);
        NameSurname_lbl.setAlignmentX(CENTER_ALIGNMENT);
        NameSurname_lbl.setAlignmentY(CENTER_ALIGNMENT);
        NameSurname_txf.setAlignmentX(CENTER_ALIGNMENT);
        NameSurname_txf.setAlignmentY(CENTER_ALIGNMENT);
        Biography_lbl.setAlignmentX(CENTER_ALIGNMENT);
        Biography_lbl.setAlignmentY(CENTER_ALIGNMENT);
        Biography_txa.setAlignmentX(CENTER_ALIGNMENT);
        Biography_txa.setAlignmentY(CENTER_ALIGNMENT);
        okay_btn.setAlignmentX(CENTER_ALIGNMENT);
        okay_btn.setAlignmentY(CENTER_ALIGNMENT);        
        //set box layout of components
        DetailStageingContainer.add(candidate_lbl);
        DetailStageingContainer.add(Box.createRigidArea(new Dimension(5, 5)));
        DetailStageingContainer.add(candidate_img);
        DetailStageingContainer.add(Box.createRigidArea(new Dimension(20, 20)));
        DetailStageingContainer.add(NameSurname_lbl);
        DetailStageingContainer.add(Box.createRigidArea(new Dimension(7, 2)));
        DetailStageingContainer.add(NameSurname_txf);
        DetailStageingContainer.add(Box.createRigidArea(new Dimension(10, 10)));
        DetailStageingContainer.add(Biography_lbl);
        DetailStageingContainer.add(Box.createRigidArea(new Dimension(7, 2)));
        DetailStageingContainer.add(Biography_txa);
        DetailStageingContainer.add(Box.createRigidArea(new Dimension(10, 10)));
        DetailStageingContainer.add(okay_btn);
        DetailStageingContainer.add(Box.createRigidArea(new Dimension(7, 2)));        
        //Set font of labels
        candidate_lbl.setFont(new Font("Arial", Font.PLAIN, 18));
        NameSurname_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        Biography_lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        //set black border of components
        candidate_img.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        NameSurname_txf.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Biography_txa.setBorder(BorderFactory.createLineBorder(Color.black, 1));                
        
        //set Jframe visible
        CandidateForm.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
