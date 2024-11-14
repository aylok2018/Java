import java.util.InputMismatchException;
import java.util.Scanner;

// Виключення для неправильних вхідних даних
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

// Calculator який виконує арифметичні операції
class Calculator {

    // Додавання
    public double add(double a, double b) {
        return a + b;
    }

    // Віднімання
    public double subtract(double a, double b) {
        return a - b;
    }

    // Множення
    public double multiply(double a, double b) {
        return a * b;
    }

    // Помилка про те що на нуль ділити не можна
    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Помилка: Ділення на нуль неможливе.");
        }
        return a / b;
    }

    // Перевірка на додаткові умови
    public double sqrt(double a) throws InvalidInputException {
        if (a < 0) {
            throw new InvalidInputException("Помилка: Корінь з від'ємного числа неможливо обчислити.");
        }
        return Math.sqrt(a);
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        try {
            System.out.println("Введіть перше число:");
            double num1 = scanner.nextDouble();

            System.out.println("Введіть друге число:");
            double num2 = scanner.nextDouble();

            System.out.println("Оберіть операцію (+, -, *, /):");
            char operation = scanner.next().charAt(0);

            double result = 0;

            // Обробка операцій
            switch (operation) {
                case '+':
                    result = calculator.add(num1, num2);
                    break;
                case '-':
                    result = calculator.subtract(num1, num2);
                    break;
                case '*':
                    result = calculator.multiply(num1, num2);
                    break;
                case '/':
                    result = calculator.divide(num1, num2);
                    break;
                default:
                    System.out.println("Невідома операція.");
                    return;
            }

            System.out.println("Результат: " + result);

        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Помилка: Введено некоректне значення. Будь ласка, введіть числа.");
        } finally {
            System.out.println("Обробка завершена.");
            scanner.close();
        }
    }
}
