package Palindromo;


import java.sql.*;

public class Test {

    public static void main(String[] args) {  //используйте exit для выхода, top для таблицы лидеров
        new Test().test();
    }


    private void test() {
        try {
            Connection connection = new Connector("root", "root").connect();
            Statement statement = connection.createStatement();

            Core core = new Core(statement);
            core.game(statement);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
