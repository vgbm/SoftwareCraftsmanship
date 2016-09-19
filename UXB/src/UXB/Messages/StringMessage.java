package UXB.Messages;

/**
 * Created by james on 9/19/16.
 */
public class StringMessage implements Message {

    //message as a string
    private final String message;

    public StringMessage(String string) {
        message = string == null ?
                    "" :
                    string;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object anObject) {
        if( anObject == null || !(anObject instanceof StringMessage) ) {
            return false;
        }

        StringMessage objectMessage = (StringMessage) anObject;
        return message.equals(objectMessage.getMessage());
    }

    public int length() {
        return message.length();
    }

    public char charAt(int i) {
        return message.charAt(i);
    }

    public boolean contains(CharSequence charSequence) {
        return message.contains(charSequence);
    }

    public boolean endsWith(String suffix) {
        return message.endsWith(suffix);
    }

    public boolean startsWith(String prefix) {
        return message.startsWith(prefix);
    }


    public int indexOf(int ch) {
        return message.indexOf(ch);
    }

    public int indexOf(int ch, int fromIndex) {
        return message.indexOf(ch, fromIndex);
    }

    public int indexOf(String str) {
        return message.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return message.indexOf(str, fromIndex);
    }


    public int lastIndexOf(int ch) {
        return message.lastIndexOf(ch);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return message.lastIndexOf(ch, fromIndex);
    }

    public int lastIndexOf(String str) {
        return message.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return message.lastIndexOf(str, fromIndex);
    }

    public boolean isEmpty() {
        return message.isEmpty();
    }

    public int hashCode() {
        return message.hashCode();
    }
}
