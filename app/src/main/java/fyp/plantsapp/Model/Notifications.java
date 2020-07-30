package fyp.plantsapp.Model;

public class Notifications {
   private String title;
    private String message;
   private Diseases diseases;

    public Diseases getDiseases() {
        return diseases;
    }

    public void setDiseases(Diseases diseases) {
        this.diseases = diseases;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    private String videoId;



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

    public Notifications(String title, String message,String date,String videoId,Diseases diseases) {
        this.title = title;
        this.message = message;
        this.date=date;
        this.videoId=videoId;
        this.diseases=diseases;
    }
}
