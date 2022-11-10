/*
Java Class to help Build a TreeModel In order to easily transfer it into Jtree and the UI.
since the User and User Groups are DefaultMutableTreeNode
 */
import javax.swing.tree.DefaultTreeModel;

public class TreeBuilder {
    private DefaultTreeModel root;

    public TreeBuilder(UserComposite root)
    {
        this.root = new DefaultTreeModel(root);
    }
    //adding nodes to the tree
    public void addNode(UserComposite childNode, UserComposite parentNode,int i )
    {
        Object node = parentNode.getUserObject();
        if(node instanceof UserGroup)
        {
            //making sure that the name being added to the UserGroup is unique and not repeated
            if(!((UserGroup) node).containUniqueNames(childNode.toString())&&
                    !((UserGroup) node).containUniqueNames(parentNode.toString())) {
                if (parentNode.getAllowsChildren()) {
                    root.insertNodeInto(childNode, parentNode, i);
                    ((UserGroup) node).addUniqueNames(childNode.toString());

                }
            }
        }
    }
    //Return the model to put into the JTree
    public DefaultTreeModel getModel()
    {
        return root;
    }
}
