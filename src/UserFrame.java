/*
A Frame that makes the UI for all the Users during user view
 */
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame implements ActionListener,Observer {

    private UserComposite rootUser;
    private User user;

    //Components needed to build the Jframe and UI
    private JButton addUserButton;
    private JTextField textUserField;
    private JButton addTweetButton;
    private JTextField textTweet;
    private DefaultTreeModel followRoot;
    private DefaultTreeModel newsFeedRoot;
    private final JTree followTree;
    private final JTree newsFeedTree;
    //Constructor
    public UserFrame(UserComposite rootUser,User user)
    {
        this.rootUser=rootUser;
        this.user=user;
        //Make the UserFrame a Observer of its own user
        this.user.attach(this);
        //Create the base for the Jframe
        this.setSize(500,800);
        this.setTitle(user+" view");
        this.setLayout(null);

        //Button and Textfield for User ID
        AddTextFieldButton userID = new AddTextFieldButton("Follow User",20,70);
        this.add(userID.getLabel());
        this.add(textUserField =userID.getTextField());
        this.add(addUserButton=userID.getButton());
        addUserButton.addActionListener(this);
        //Tree for followers to easily keep track of who the user follows
        followRoot = new DefaultTreeModel(new DefaultMutableTreeNode("Following"));
        followTree = new JTree(followRoot);
        followTree.setBounds(20,120,450,200);
        for(User temp : user.getFollowing())
        {
            followRoot.insertNodeInto( new DefaultMutableTreeNode(temp.toString()),
                    (DefaultMutableTreeNode) followRoot.getRoot(),0);
        }
        this.add(followTree);
        //Button and Textfield for follow tweeting
        AddTextFieldButton tweet = new AddTextFieldButton("Tweet Message",20,375);
        this.add(userID.getLabel());
        this.add(textTweet =tweet.getTextField());
        this.add(addTweetButton=tweet.getButton());
        addTweetButton.addActionListener(this);
        //tree for the news feed
        newsFeedRoot = new DefaultTreeModel(new DefaultMutableTreeNode("News Feed"));
        newsFeedTree = new JTree(newsFeedRoot);
        for(String temp : user.getTimeline())
        {
            newsFeedRoot.insertNodeInto( new DefaultMutableTreeNode(temp),
                    (DefaultMutableTreeNode) newsFeedRoot.getRoot(),
                    ((DefaultMutableTreeNode) newsFeedRoot.getRoot()).getChildCount());
        }
        newsFeedTree.setBounds(20,420,450,200);
        this.add(newsFeedTree);
        this.setResizable(false);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Add followers and attach yourself to other observers
        if(e.getSource() == addUserButton)
        {
            User followUser=searchForUser(textUserField.getText());
            //checking if the user is not followed yet or even exists
            if(followUser != null &&followUser !=this.user&& !this.user.amFollowing(followUser)) {
                followRoot.insertNodeInto( new DefaultMutableTreeNode(textUserField.getText()),
                        (MutableTreeNode) followRoot.getRoot(), 0);
                this.user.setFollowing(followUser);
                followUser.attach(this);
            }
        }
        //Add a tweet for the User to call all other observers
        if(e.getSource() == addTweetButton)
        {
            user.setLatestMessage(textTweet.getText());
        }
    }
    //Visitor call for finding user
    private User searchForUser(String name)
    {
        Visitor findUser = new FindUser(name);
        findUser.visit(rootUser);
        return ((FindUser) findUser).getUser();
    }
    //implement observer method to insert the people you follow messages into you newsfeed
    @Override
    public void update(User user) {
        newsFeedRoot.insertNodeInto( new DefaultMutableTreeNode(user.getLatestMessage()),
                (MutableTreeNode) newsFeedRoot.getRoot(),
                ((MutableTreeNode) newsFeedRoot.getRoot()).getChildCount());
    }
}
