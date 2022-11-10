import java.util.Set;
import java.util.HashSet;

public class TotalPositiveMessagesVisitor implements Visitor{
    private int positiveMessages;
    private Set<String> positiveWord;
    //constructor to create a set of positive words
    public TotalPositiveMessagesVisitor()
    {
        positiveMessages=0;
        positiveWord = new HashSet<>();
        positiveWord.add("happy");
        positiveWord.add("good");
        positiveWord.add("positive");
    }
    //Recursively go through the tree to find all users and their messages
    @Override
    public void visit(UserComposite node) {
        //Stop condition if we are a leaf
        if(node.getChildCount()==0)
        {
            if(node instanceof User) {
                //iterate through the user tweets to find if message have positive words
                for (String message : ((User) node).getMyTweets()) {
                    //iterate through the positive word set
                    for (String word : positiveWord) {
                        if (message.toLowerCase().contains(word)) {
                            positiveMessages += 1;
                        }
                    }
                }
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
        return positiveMessages;
    }
}
