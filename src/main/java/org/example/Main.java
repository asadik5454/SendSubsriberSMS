package org.example;

import org.apache.commons.httpclient.util.DateUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {

        String connectionUrl = "jdbc:postgresql://tjk-zetalif01:5432/zetalifDB";
        String userName = "zetalif";
        String password = "JnmnF54D3Op";
//        System.out.println(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_DATE));
        // System.out.println(LocalDate.now().plusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE)..toString().replace("-","."));

        System.out.println(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now().plusDays(1)));
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
                    getNumber = resultSet.getString("number");
                    getComName = resultSet.getString("commercial_name");
                    getComNameTj = resultSet.getString("commercial_name");
                    getAmount = resultSet.getInt("amount");
                    getNextDate = resultSet.getString("next_apply_date");
                    getComName = "\"" + getComName + "\"";
                    getComNameTj = "\"" + getComNameTj + "\"";
                    String sms = "Уважаемый абонент! Срок действия пакета " + getComName + " истекает " + getNextDate
                            + ". Списание абонентской платы в размере " + getAmount + " сомони произойдет в ночь с "
                            + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now().plusDays(1)) + " на " + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now().plusDays(2))
                            + ". Просим Вас иметь на балансе достаточную сумму на момент списания абонентской платы. \n"
                            + "Муштарии мухтарам! Мухлати амали  бастаи " + getComNameTj + " " + getNextDate + " тамом мешавад. Муштарипули ба андозаи " + getAmount + " сомони шаби аз "
                            + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now().plusDays(1)) + " ба " + DateTimeFormatter.ofPattern("dd.MM.yyyy").format(LocalDateTime.now().plusDays(2))
                            + " гирифта мешавад. Хохишмандем, ки хангоми гирифтани муштарипули, дар суратхисоб маблаги лозима дошта бошед.";
                    String SQL = "update payment_per_day set text_sms = '" + sms + "' , sms_is_send = 'true' where sms_is_send = 'false' and id = " + getId + ";";
                    Methods.send_sms(getNumber, sms);
                    System.out.println(getNumber + " " + getComName + " " + getAmount + " " + getNextDate);
                    boolean resultSet1 = statementUpdate.execute(SQL);
                }
                Methods.send_sms("992911003819", "Цикл смс отработал");
                resultSet.close();
                Methods.send_sms("992911003819", "Connect close");
            } catch (SQLException e) {
                e.printStackTrace();
                Methods.send_sms("992911003819", "Что то не так");
            }
        } catch (Exception e) {
            Methods.send_sms("992911003819", "Что то не так 2");
            throw new RuntimeException(e);
        }
    }
}