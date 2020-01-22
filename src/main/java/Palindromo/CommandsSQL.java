package Palindromo;

public interface CommandsSQL {

    //for syntax
    String q =  "'"; //quotes
    String ob = "("; //open bracket
    String cb = ")"; //closed bracket
    String cm = ","; //comma

    //initialization
    String createDatabase =    "CREATE DATABASE IF NOT EXISTS PALINDROME";
    String createUsersTable =  "CREATE TABLE IF NOT EXISTS USERS (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(30) UNIQUE, SCORE INT DEFAULT 0)";
    String createWordsTable =  "CREATE TABLE IF NOT EXISTS WORDS (ID_USER INT, WORD VARCHAR(30), FOREIGN KEY (ID_USER) REFERENCES USERS (ID))";
    String createNewUser =     "INSERT USERS(NAME) VALUES ";


    String showUsers =         "SELECT * FROM USERS";
    String searchUser =        "SELECT * FROM USERS WHERE CONCAT(NAME) LIKE ";
    String getLeaderBoard =    "SELECT * FROM USERS ORDER BY SCORE DESC  LIMIT 0,5;";
    String getSizeUsers =      "SELECT COUNT(*) FROM USERS";

    String setNewWord =        "INSERT WORDS (ID_USER, WORD) VALUE ";
    String addScoreToUser1 =   "UPDATE USERS SET SCORE = SCORE + ";
    String addScoreToUser2 =   " WHERE ID LIKE ";

    String checkCollision1 =   "SELECT WORD FROM WORDS WHERE ID_USER LIKE ";
    String checkCollision2 =   " AND WORD LIKE ";


}
