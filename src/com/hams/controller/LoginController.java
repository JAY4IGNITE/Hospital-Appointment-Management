package com.hams.controller;
import com.hams.dao.UserDAO;
import com.hams.model.User;
public class LoginController {
    public static User login(String e,String p){return UserDAO.login(e,p);}
}