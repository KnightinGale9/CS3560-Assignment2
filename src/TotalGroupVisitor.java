/*
Visitor to find the Number of UserGroups the tree has
 */
import javax.swing.tree.DefaultMutableTreeNode;

public class TotalGroupVisitor implements Visitor{
    private int groupNumber;
    public TotalGroupVisitor()
    {
        groupNumber=0;
    }
    //Method to find all the usergroups
    @Override
    public void visit(UserComposite node) {
        //Recursively look for all the children
        //Stop condition no more children else look through more children
        for (int i=0;i<node.getChildCount();i++)
        {
            DefaultMutableTreeNode temp = (DefaultMutableTreeNode) node.getChildAt(i);
            this.visit((UserComposite) temp.getUserObject());
        }
        //Increment groupNumber if the node is UserGroup
        if(node.getUserObject() instanceof UserGroup)
        {
            this.groupNumber++;
        }

    }
    //Return the groupNumber
    @Override
    public int visitorValue() {
        return groupNumber;
    }

}
