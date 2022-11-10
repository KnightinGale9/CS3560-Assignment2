public class twitterSimulator {
    public static void main(String[] args) {
        //proving this is a adminFrame implemented singleton properly
        System.out.println(AdminFrame.getInstance().hashCode());
        System.out.println(AdminFrame.getInstance().hashCode());
    }

}
