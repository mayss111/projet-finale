package Entities;

import java.sql.Timestamp;

public class ResetPassword {

    private int id,code;
    private User user;
    private Timestamp dateCreation;

    public ResetPassword() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "id=" + id +
                ", code=" + code +
                ", user=" + user.getEmail() +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
