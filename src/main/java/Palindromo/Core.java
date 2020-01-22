package Palindromo;

import java.sql.*;
import java.util.Scanner;

class Core implements CommandsSQL {


    private Scanner scanner;
    private int selectedUserId;
    private StringBuilder word;
    Core(Statement statement) {
        scanner = new Scanner(System.in);
        word = new StringBuilder();
        isGame = true;

        try {    //initialization
            statement.executeUpdate(createDatabase);
            statement.executeUpdate(createUsersTable);
            statement.executeUpdate(createWordsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("Выберете аккаунт, введя его логин или создайте новый, введя несуществующий\nСписок доступных пользователей:");
        selectUser(statement);
    }

    private boolean isGame;
    void game(Statement statement) {
        while (isGame) {
            System.out.println("\nВведите слово");
            word.append(scanner.nextLine());
            if (isCommand(statement)){ //проверка на команду
                reset();
                continue;
            }


            if (isPalindrome()) {

                if (checkCollision(statement)){ //поиск слова в базе
                    reset();
                    System.out.println("слово уже было использовано");
                    continue;
                }

                try {
                    statement.executeUpdate(setNewWord + ob + selectedUserId + cm + q + word.toString() + q + cb); //add new word
                    statement.executeUpdate(addScoreToUser1 + word.toString().length() + addScoreToUser2 + selectedUserId); //update total score
                    System.out.println("добавлено символов: " + word.toString().length());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Слово не является палиндромом");
            }


            reset();
        }
    }

    private boolean checkCollision(Statement statement) {
        try {
            return statement.executeQuery(checkCollision1 + selectedUserId + checkCollision2 + q + word.toString() + q).next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isCommand(Statement statement) {
        switch (word.toString()) {
            case "top":
                leaderBoard(statement);
                return true;
            case "exit":
                isGame = false;
                return true;
            default:
                return false;
        }
    }

    private void reset() {
        word.setLength(0);
    }

    private void leaderBoard(Statement statement) {
        try {
            ResultSet leaderBoard = statement.executeQuery(getLeaderBoard);
            while (leaderBoard.next()) {
                System.out.println("ID: " + leaderBoard.getInt("ID") + ", user '" +
                        leaderBoard.getString("NAME") + "', score = " + leaderBoard.getInt("SCORE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isPalindrome() { //проверка на палиндром
        permissible();
        if (word.toString().equals("")) return false;

        for (int i = 0; i < word.length() / 2; i++) {
            if (word.charAt(i) != word.charAt(word.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    private void permissible() { //для соответствия общей форме записи
        word.replace(0, word.length(), word.toString()
                .replaceAll("[^\\p{L}\\p{Nd}]+", "")    //соответсвует всем Unicode
                .toLowerCase()                                            //убирает зависимость от заглавных букв
                .replace('ё', 'е')                       //допущения для русского языка
                .replace('й', 'и')
                .replace('щ', 'ш')
                .replace('ъ', 'ь'));
    }


    private void selectUser(Statement statement) {

        try {
            ResultSet resultSet = statement.executeQuery(showUsers);
            while(resultSet.next()){
                System.out.println(resultSet.getInt("ID") + ": '" + resultSet.getString("NAME") + "', score = " + resultSet.getInt("SCORE"));
            }

            selectedUserId = searchUser(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int searchUser(Statement statement) {
        String input = new String(new StringBuilder(scanner.nextLine()));

        try {
            ResultSet resultUserID = statement.executeQuery(searchUser + q + input + q);

            if (!resultUserID.next()) { //создание нового пользователя, при отсутствии соответствий
                statement.executeUpdate(createNewUser + ob + q + input + q + cb);
                ResultSet rs = statement.executeQuery(getSizeUsers);
                rs.next();
                return rs.getInt("COUNT(*)");
            }

            return resultUserID.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
