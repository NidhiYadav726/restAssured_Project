package crudOperations;

public class randomDataGenerator {

    public static String createRandomName() {
        int randomInt = (int) (Math.random() * 1000);
        return "rohan" + randomInt;
    }

    public static String createRandomEmail() {
        return createRandomName() + "@gmail.com";
    }
}







