package org.example;

import org.apache.commons.httpclient.util.DateUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException {

        String connectionUrl = "jdbc:postgresql://tjk-zetalif01:5432/zetalifDB";
        String userName = "zetalif";
        String password = "JnmnF54D3Op";
        System.out.println(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_DATE));
        System.out.println(LocalDateTime.now().plusDays(2).format(Da));




        String getNumber = "";
        String getComName = "";
        String getComNameTj = "";
        int getAmount = 0;
        String getNextDate = "";
        int getId = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password)) {
            Methods.send_sms("992911003819", "Старт Смс");

            Statement statementSelect = connection.createStatement();
            Statement statementUpdate = connection.createStatement();
            try {
                int i = 0;
                ResultSet resultSet = statementSelect.executeQuery("select id, number , commercial_name , amount, next_apply_date from payment_per_day where sms_is_send = 'false' and commercial_name is not null;");
                while (resultSet.next()) {
                    getId = resultSet.getInt("id");
                    getNumber = resultSet.getString("number");
                    getComName = resultSet.getString("commercial_name");
                    getComNameTj = resultSet.getString("commercial_name");
                    getAmount = resultSet.getInt("amount");
                    getNextDate = resultSet.getString("next_apply_date");
                    getComName = "\"" + getComName + "\"";
                    getComNameTj = "\"" + getComNameTj + "\"";
                    getId = resultSet.getInt("id");
                    System.out.println(" Это количествооо :" + i++);
                    String sms = "Уважаемый абонент! Срок действия пакета " + getComName + " истекает "  + getNextDate
                            + ". Списание абонентской платы в размере " + getAmount + "  сомони произойдет в ночь с "
                            + getNextDate + " на " + LocalDateTime.parse(getNextDate).plusDays(1).toString()
                            + "  Просим Вас иметь на балансе достаточную сумму на момент списания абонентской платы\n"
                            + getNextDate + " Муштарии мухтарам! Мухлати амали  бастаи  " + getComNameTj + " " + getNextDate + ". тамом мешавад. Муштарипули ба андозаи " + getAmount + " сомони шаби аз "
                            + getNextDate + " ба " + LocalDateTime.parse(getNextDate).plusDays(1).toString()
                            +", гирифта мешавад. Хохишмандем, ки хангоми гирифтани муштарипули, дар суратхисоб маблаги лозима дошта бошед.";
                    String SQL = "update payment_per_day set text_sms = '" + sms + "' , sms_is_send = 'true' where sms_is_send = 'false' and id = " + getId + ";";
                    Methods.send_sms("992911003819", sms);
                 //   Methods.send_sms(getNumber, sms);
                    System.out.println(SQL);
                    System.out.println(getNumber + " " + getComName + " " + getAmount + " " + getNextDate);
                    boolean resultSet1 = statementUpdate.execute(SQL);
                                    }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();




    }
} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}