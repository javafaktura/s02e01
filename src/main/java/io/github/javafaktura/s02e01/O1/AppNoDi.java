package io.github.javafaktura.s02e01.O1;

class AppNoDi {

    public static void main(String[] args) {

        var dbUrl = "jdbc:mysql://localhost:3306/springcore";
        var dbUsername = "user";
        var dbPass = "admin123";

        var service = new NoDiService(dbUrl, dbUsername, dbPass);

        service.mostPopularValue()
                .ifPresent(System.out::println);
    }
}
