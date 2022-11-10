import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame implements AdminBuilder {
    //Private Variable for Singleton
    private static AdminBuilder instance;
    //All the JFrame Components needed to make the Admin UI
    private JPanel visitorPanel;
    private JLabel visitorLabel;
    private JLabel visitorResult;
    private JTextField textFieldUserID;
    private JTextField textFieldGroupID;
    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton openUserViewButton;
    private JButton showTotalUserButton;
    private JButton showTotalGroupsButton;
    private JButton showTotalMessagesButton;
    private JButton showTotalPositiveMessagesButton;
    private JTree tree;
    private UserGroup rootUser;
    private TreeBuilder root;
    //getter method for AdminUI makes sure there is only one is able to be created
    public static AdminBuilder getInstance(){
        if(instance==null){
            instance =new AdminFrame();
        }
        return instance;
    }
    //constructor with all the Jframe components built onto a null layout
    private AdminFrame(){
        //A class for only action concerning buttons for Single purpose design
        Actions act = new Actions();
        //Create the Jframe baseUI
        this.setTitle("Admin Panel");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(795, 790);
        this.setResizable(false);
        this.setBackground(Color.GRAY);

        //Create the tree
        rootUser = new UserGroup("root");
        root = new TreeBuilder(rootUser);
        tree = new JTree(root.getModel());
        tree.setBounds(10,10,280,790);
        this.add(tree);
        //Create the textfeild and button for adding a user
        AddTextFieldButton userID = new AddTextFieldButton("User",290,50);
        this.add(userID.getLabel());
        this.add(textFieldUserID=userID.getTextField());
        this.add(addUserButton=userID.getButton());
        addUserButton.addActionListener(act);
        //Create the textfeild and button for adding a user group
        AddTextFieldButton groupID = new AddTextFieldButton("Group",290,150);
        this.add(groupID.getLabel());
        this.add(textFieldGroupID=groupID.getTextField());
        this.add(addGroupButton=groupID.getButton());
        addGroupButton.addActionListener(act);
        //Create a button for opening User view
        openUserViewButton=new JButton("Button - Open User View");
        openUserViewButton.setBounds(290,250,450,50);
        this.add(openUserViewButton);
        openUserViewButton.addActionListener(act);
        //Create a button for TotalUserVisitor
        showTotalUserButton=new JButton("Button - Show User Total");
        showTotalUserButton.setBounds(290,350,300,100);
        this.add(showTotalUserButton);
        showTotalUserButton.addActionListener(act);
        //Create a button for TotalGroupVisitor
        showTotalGroupsButton=new JButton("Button - Show Group Total");
        showTotalGroupsButton.setBounds(290,450,300,100);
        this.add(showTotalGroupsButton);
        showTotalGroupsButton.addActionListener(act);
        //Create a button for TotalMessagesVisitor
        showTotalMessagesButton=new JButton("Button - Show Messages Total");
        showTotalMessagesButton.setBounds(290,550,300,100);
        this.add(showTotalMessagesButton);
        showTotalMessagesButton.addActionListener(act);
        //Create a button for TotalPositiveMessages Visitor
        showTotalPositiveMessagesButton=new JButton("Button - Show Positive Percent");
        showTotalPositiveMessagesButton.setBounds(290,650,300,100);
        this.add(showTotalPositiveMessagesButton);
        showTotalPositiveMessagesButton.addActionListener(act);
        //Create a panel to output the visitor result
        visitorPanel=new JPanel(null);
        visitorPanel.setBackground(Color.WHITE);
        visitorPanel.setBounds(600,360,150,150);
        visitorLabel=new JLabel();
        visitorLabel.setBounds(20,0,150,50);
        visitorPanel.add(visitorLabel);
        visitorResult=new JLabel();
        visitorResult.setBounds(40,50,150,50);
        visitorResult.setFont(new Font("Times", Font.PLAIN, 30));
        visitorPanel.add(visitorResult);

        this.add(visitorPanel);

        this.setVisible(true);
    }
    //class that implements action listener for all the buttons
    public class Actions implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*
            Add User button will
                -add a user after the selected user if the user is selected on the tree
                -add a user at the end of the UserGroup if the userGroup is selected on the tree
                -add a user at the end of the root if nothing is selected
             */
            if(e.getSource() == addUserButton)
            {
                Object test = tree.getLastSelectedPathComponent();
//                System.out.println(test.toString());
                if(test instanceof User) {
                    root.addNode(new User(textFieldUserID.getText()),
                            (UserComposite) ((User) test).getParent(),
                            ((User) test).getParent().getIndex((UserComposite)test)+1);
                }
                else if(test instanceof UserGroup){
                    root.addNode(new User(textFieldUserID.getText()),
                            (UserGroup) test, ((UserGroup) test).getChildCount());
                }
                else
                {
                    root.addNode(new User(textFieldUserID.getText()),
                            rootUser,0);
//                    System.out.println(textFieldUserID.getText());
                }
            }
            /*
            Add User button will
                -add a userGroup after the selected user if the user is selected on the tree
                -add a userGroup at the end of the UserGroup if the userGroup is selected on the tree
                -add a userGroup at the end of the root if nothing is selected
             */
            if(e.getSource() == addGroupButton) {
                Object test = tree.getLastSelectedPathComponent();
                if(test instanceof User) {
                    root.addNode(new UserGroup(textFieldGroupID.getText()),
                            (UserComposite) ((User) test).getParent(),
                            ((User) test).getParent().getIndex((UserComposite)test)+1);
//                    System.out.println(textFieldGroupID.getText());
                }
                else if(test instanceof UserGroup){
                    root.addNode(new UserGroup(textFieldGroupID.getText()),
                            (UserGroup) test, ((UserGroup) test).getChildCount());
//                    System.out.println(textFieldGroupID.getText());
                }
                else
                {
                    root.addNode(new UserGroup(textFieldGroupID.getText()),
                            rootUser,0);
//                    System.out.println(textFieldGroupID.getText());
                }
            }
            //Open the User View if a user is selected
            if(e.getSource() == openUserViewButton)
            {
                Object test = tree.getLastSelectedPathComponent();
                if (test instanceof User)
                {
                    UserFrame userFrame = new UserFrame(rootUser,(User)test);
                }
            }

            //Run the TotalUserVisitor and output the results
            if(e.getSource() == showTotalUserButton)
            {
                Visitor group =new TotalUserVisitor();
                group.visit(rootUser);
                visitorLabel.setText("Total Users");
                visitorResult.setText(String.valueOf((int)group.visitorValue()));
            }
            //Run the TotalGroupVisitor and output the results
            if(e.getSource() == showTotalGroupsButton)
            {
                Visitor group =new TotalGroupVisitor();
                group.visit(rootUser);
                visitorLabel.setText("Total Groups");
                visitorResult.setText(String.valueOf((int)group.visitorValue()));
            }
            //Run the TotalMessagesVisitor and output the results
            if(e.getSource() == showTotalMessagesButton)
            {
                Visitor group =new TotalMessagesVisitor();
                group.visit(rootUser);
                visitorLabel.setText("# Messages");
                visitorResult.setText(String.valueOf((int)group.visitorValue()));
            }
            //Run the TotalPositiveMessagesVisitor and output the results
            if(e.getSource() == showTotalPositiveMessagesButton)
            {
                Visitor group =new TotalPositiveMessagesVisitor();
                group.visit(rootUser);

                Visitor count =new TotalMessagesVisitor();
                count.visit(rootUser);

                visitorLabel.setText("# Positive Percent");
                visitorResult.setText(String.valueOf((double)group.visitorValue()/count.visitorValue()));
            }
        }
    }
}

