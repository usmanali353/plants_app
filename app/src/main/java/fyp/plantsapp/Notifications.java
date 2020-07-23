package fyp.plantsapp;

public class Notifications {
   private String title;
    private String message;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

   public Notifications(){

   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notifications(String title, String message,String date) {
        this.title = title;
        this.message = message;
        this.date=date;
    }
}
