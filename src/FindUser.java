/*
Visitor to find where the User is located in order to follow them in UserView
 */
public class FindUser implements Visitor{
    private String name;
    //Store the location of User
    private User user;
    public FindUser(String name)
    {
        this.name=name;
    }
    //Recursively look to find where the Name is in the Tree
    @Override
    public void visit(UserComposite node) {
        if(node.toString().equals(name))
        {
            if(node instanceof User)
            {
                user= (User) node;
            }
        }
        else{
            for (int i=0;i<node.getChildCount();i++)
            {
                visit((UserComposite) node.getChildAt(i));
            }
        }
    }
    //User getter method
    public User getUser()
    {
        return user;
    }
    @Override
    public int visitorValue() {
        return 0;
    }
}
