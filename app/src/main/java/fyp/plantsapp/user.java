package fyp.plantsapp;

public class user {

        public user(String name, String cnic, String email, String address,String phone) {
            this.name = name;
            this.cnic = cnic;
            this.email = email;
            this.address = address;
            this.phone=phone;
        }
        public user(){

        }
        private String name;
        private String cnic;
        private String email;
        private String address;

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

        public String getCnic() {
            return cnic;
        }

        public void setCnic(String cnic) {
            this.cnic = cnic;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

