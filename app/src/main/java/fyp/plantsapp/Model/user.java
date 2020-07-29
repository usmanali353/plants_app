package fyp.plantsapp.Model;

public class user {
        public user(String name, String email,String phone,String cropCurrentStage) {
            this.name = name;
            this.email = email;
            this.phone=phone;
            this.cropCurrentStage=cropCurrentStage;
        }
        public user(){

        }
        private String name;
        private String email;
        private String cropCurrentStage;

    public String getCropCurrentStage() {
        return cropCurrentStage;
    }

    public void setCropCurrentStage(String cropCurrentStage) {
        this.cropCurrentStage = cropCurrentStage;
    }

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

