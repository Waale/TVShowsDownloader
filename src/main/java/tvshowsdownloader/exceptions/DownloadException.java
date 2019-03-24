package tvshowsdownloader.exceptions;

import java.util.List;

public class DownloadException extends Exception {
    private List<String> messages;

    public DownloadException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
