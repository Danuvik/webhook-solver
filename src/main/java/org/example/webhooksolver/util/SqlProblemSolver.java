package org.example.webhooksolver.util;

public class SqlProblemSolver {

    public static String solveProblem(String regNo) {

        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastDigits = Integer.parseInt(lastTwoDigits);

        if (lastDigits % 2 == 1) {

            return solveQuestion1();
        } else {

            return solveQuestion2();
        }
    }

    private static String solveQuestion1() {
        return "SELECT " +
                "    p.AMOUNT AS SALARY, " +
                "    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
                "    TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, " +
                "    d.DEPARTMENT_NAME " +
                "FROM " +
                "    PAYMENTS p " +
                "JOIN " +
                "    EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                "JOIN " +
                "    DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                "WHERE " +
                "    DAY(p.PAYMENT_TIME) != 1 " +
                "ORDER BY " +
                "    p.AMOUNT DESC " +
                "LIMIT 1;";
    }

    private static String solveQuestion2() {
        return "SELECT " +
                "    e1.EMP_ID, " +
                "    e1.FIRST_NAME, " +
                "    e1.LAST_NAME, " +
                "    d.DEPARTMENT_NAME, " +
                "    COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
                "FROM " +
                "    EMPLOYEE e1 " +
                "JOIN " +
                "    DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                "LEFT JOIN " +
                "    EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT " +
                "               AND e2.DOB > e1.DOB " +
                "GROUP BY " +
                "    e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                "ORDER BY " +
                "    e1.EMP_ID DESC;";
    }
}