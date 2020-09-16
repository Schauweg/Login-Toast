package schauweg.logintoast.config;

public class LoginToastConfig {

    private boolean showchatMessage = false;
    private boolean showToast = true;
    private int toastDuration = 5;


    public boolean showChatMessage() {
        return showchatMessage;
    }

    public boolean showToast() {
        return showToast;
    }

    public int getToastDuration() {
        return toastDuration;
    }

    public void setShowchatMessage(boolean showchatMessage) {
        this.showchatMessage = showchatMessage;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public void setToastDuration(int toastDuration) {
        this.toastDuration = toastDuration;
    }
}
