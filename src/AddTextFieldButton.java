import javax.swing.*;
/*
A class made to simplify the creationg of textfeilds with buttons
 */
public class AddTextFieldButton {
    private JTextField textField;
    private JButton button;
    private JLabel label;

    public AddTextFieldButton(String text,int x,int y)
    {
        textField = new JTextField();
        textField.setBounds(x,y,250,40);

        button = new JButton(String.format("Button - Add %s",text));
        button.setBounds(x+250, y-10,200,50);

        label = new JLabel(String.format("Text Area - %s ID", text));
        label.setBounds(x+10,y-40,200,40);

    }
    public JTextField getTextField()
    {
        return textField;
    }
    public JButton getButton()
    {
        return button;
    }
    public JLabel getLabel()
    {
        return label;
    }
}
