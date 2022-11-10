public class TotalMessagesVisitor implements Visitor{
    private int totalMessages;
    //Constructor
    public TotalMessagesVisitor()
    {
        totalMessages=0;
    }
    //Recursively go through the tree and add up all the user nodes
    @Override
    public void visit(UserComposite node) {
        //stop condition if we are a leaf
        if(node.getChildCount()==0)
        {
            if(node instanceof User)
            {
                totalMessages+=((User) node).getMyTweets().size();
            }
        }
        else{
            //Search through more children
            for (int i=0;i<node.getChildCount();i++)
            {
                visit((UserComposite) node.getChildAt(i));
            }
        }
    }
    //getter for totalMessages
    @Override
    public int visitorValue() {
        return totalMessages;
    }
}
