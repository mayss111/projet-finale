package Interfaces;

import Entities.ResetPassword;
import Entities.User;

public interface IResetPasswordService {

    public void create(ResetPassword entity);
    public ResetPassword get(User user);

    public void ResetPassword(User user);

}
