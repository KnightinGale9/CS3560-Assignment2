public class TotalUserVisitor implements Visitor{
    private int userNumber;
    //Constructor
    public TotalUserVisitor()
    {
        userNumber=0;
    }
    //Method to find all Users through recursion
    @Override
    public void visit(UserComposite node) {
        if(node.getChildCount()==0)
        {
            if(node instanceof User)
            {
                userNumber++;
            }
        }
        else{
            for (int i=0;i<node.getChildCount();i++)
            {
                visit((UserComposite) node.getChildAt(i));
            }
        }
    }
    @Override
    public int visitorValue() {
        return userNumber;
    }
}
