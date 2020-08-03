package fyp.plantsapp.Model;

public class user {
        public user(String name, String email,String phone,String startDate) {
            this.name = name;
            this.email = email;
            this.phone=phone;
            this.startDate=startDate;
        }
        public user(){

        }
        private String name;
        private String email;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private String startDate;
    public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

