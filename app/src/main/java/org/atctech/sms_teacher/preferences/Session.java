package org.atctech.sms_teacher.preferences;

import android.content.SharedPreferences;
import org.atctech.sms_teacher.model.TeacherProfile;

/**
 * Created by Jacky on 3/3/2018.
 */

public class Session {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static Session INSTANCE = null;

    private Session(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized Session getInstance(SharedPreferences prefs)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Session(prefs);
        }
        return INSTANCE;
    }



    public void setLoggedIn(boolean isLogged)
    {
        editor.putBoolean("loggedIn",isLogged);
        editor.commit();
    }

    public boolean isLoggedIn()
    {
        return prefs.getBoolean("loggedIn",false);
    }

    public void saveTeacher(TeacherProfile teacherProfile)
    {
        editor.putString("F_NAME",teacherProfile.getFname()).apply();
        editor.putString("L_NAME",teacherProfile.getLname()).apply();
        editor.putString("EMAIL",teacherProfile.getEmail()).apply();
        editor.putString("U_ID",teacherProfile.getU_id()).apply();
        editor.putString("PHONE",teacherProfile.getPhone()).apply();
        editor.putString("ROLL",teacherProfile.getRole()).apply();
        editor.putString("ADDRESS",teacherProfile.getAddress()).apply();
        editor.putString("DETAILS",teacherProfile.getDetails()).apply();
        editor.putString("BDATE",teacherProfile.getBdate()).apply();
        editor.putString("BLOOD",teacherProfile.getBlood()).apply();
        editor.putString("SEX",teacherProfile.getSex()).apply();
        editor.putString("PRO_PIC",teacherProfile.getPro_pic()).apply();
        editor.putString("SCHEDULE",teacherProfile.getSchedule()).apply();
        editor.putString("PAADDRESS",teacherProfile.getPaddress()).apply();

    }

    public TeacherProfile getTeacher()
    {
       TeacherProfile teacherProfile = new TeacherProfile();
        teacherProfile.setFname(prefs.getString("F_NAME",null));
        teacherProfile.setLname(prefs.getString("L_NAME",null));
        teacherProfile.setEmail(prefs.getString("EMAIL",null));
        teacherProfile.setU_id(prefs.getString("U_ID",null));
        teacherProfile.setPhone(prefs.getString("PHONE",null));
        teacherProfile.setRole(prefs.getString("ROLL",null));
        teacherProfile.setAddress(prefs.getString("ADDRESS",null));
        teacherProfile.setDetails(prefs.getString("DETAILS",null));
        teacherProfile.setBdate(prefs.getString("BDATE",null));
        teacherProfile.setBlood(prefs.getString("BLOOD",null));
        teacherProfile.setSex(prefs.getString("SEX",null));
        teacherProfile.setPro_pic(prefs.getString("PRO_PIC",null));
        teacherProfile.setSchedule(prefs.getString("SCHEDULE",null));
        teacherProfile.setPaddress(prefs.getString("PAADDRESS",null));



        return teacherProfile;
    }
    public void deleteTeacher()
    {
        editor.remove("F_NAME").apply();
        editor.remove("L_NAME").apply();
        editor.remove("EMAIL").apply();
        editor.remove("U_ID").apply();
        editor.remove("PHONE").apply();
        editor.remove("ROLL").apply();
        editor.remove("ADDRESS").apply();
        editor.remove("DETAILS").apply();
        editor.remove("BDATE").apply();
        editor.remove("BLOOD").apply();
        editor.remove("SEX").apply();
        editor.remove("PRO_PIC").apply();
        editor.remove("SCHEDULE").apply();
        editor.remove("PAADDRESS").apply();

    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean("IS_FIRST_TIME_LAUNCH", isFirstTimeLaunch);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean("IS_FIRST_TIME_LAUNCH", true);
    }
}
