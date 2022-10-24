package quru.qa.models.pojo;

public class RegistrationBodyPojoModel {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public RegistrationBodyPojoModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegistrationBodyPojoModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
