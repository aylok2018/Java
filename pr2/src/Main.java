import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Main {

    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        // Читання транзакцій з файлу CSV
        TransactionCSVReader csvReader = new TransactionCSVReader();
        List<Transaction> transactions = csvReader.readTransactions(filePath);

        // Аналіз транзакцій
        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);

        // Генерація звітів
        TransactionReportGenerator reportGenerator = new TransactionReportGenerator();

        // Виведення балансу
        double totalBalance = analyzer.calculateTotalBalance();
        reportGenerator.printBalanceReport(totalBalance);
        System.out.println("Загальний баланс: " + totalBalance);

        // Виведення кількості транзакцій за місяць
        String monthYear = "01-2024";
        int transactionsCount = analyzer.countTransactionsByMonth(monthYear);
        reportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        System.out.println("Кількість транзакцій за " + monthYear + ": " + transactionsCount);

        // Виведення 10 найбільших витрат
        List<Transaction> topExpenses = analyzer.findTopExpenses();
        reportGenerator.printTopExpensesReport(topExpenses);

        // звіт по категоріях і місяцях
        reportGenerator.printCategoryAndMonthlyReport(transactions);
    }
}

class Transaction {
    private String date;
    private double amount;
    private String description;

    public Transaction(String date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}

class TransactionCSVReader {
    public List<Transaction> readTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            URL url = new URL(filePath);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    Transaction transaction = new Transaction(values[0], Double.parseDouble(values[1]), values[2]);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}

class TransactionAnalyzer {
    private final List<Transaction> transactions;
    private final DateTimeFormatter dateFormatter;

    public TransactionAnalyzer(List<Transaction> transactions) {
        this.transactions = transactions;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    // Підрахунок транзакцій за вказаний місяць і рік
    public int countTransactionsByMonth(String monthYear) {
        int count = 0;
        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), dateFormatter);
            String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    // Знаходження 10 найбільших витрат
    public List<Transaction> findTopExpenses() {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Розрахунок загального балансу
    public double calculateTotalBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }
}

class TransactionReportGenerator {

    // Виведення балансу
    public void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    // Виведення кількості транзакцій за місяць
    public void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    // Виведення 10 найбільших витрат
    public void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    // Вивід по категоріях і місяцях
    public void printCategoryAndMonthlyReport(List<Transaction> transactions) {
        // Сумарні витрати по категоріях
        Map<String, Double> expensesByCategory = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(Transaction::getDescription, Collectors.summingDouble(Transaction::getAmount)));

        // витрати по категоріях
        System.out.println("Сумарні витрати по категоріях:");
        expensesByCategory.forEach((category, total) -> {
            String visualRepresentation = generateVisualRepresentation(total);
            System.out.println(category + ": " + visualRepresentation + " (" + total + ")");
        });

        // сума витрат по місяцях
        Map<String, Double> expensesByMonth = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(t -> t.getDate().substring(3, 10), Collectors.summingDouble(Transaction::getAmount)));

        // витрати по місяцях
        System.out.println("\nСумарні витрати по місяцях:");
        expensesByMonth.forEach((month, total) -> {
            String visualRepresentation = generateVisualRepresentation(total);
            System.out.println(month + ": " + visualRepresentation + " (" + total + ")");
        });
    }

    // сума витрат
    private String generateVisualRepresentation(double amount) {
        int symbolsCount = (int) Math.abs(amount) / 1000;  // 1 символ на кожні 1000 грн
        return "*".repeat(Math.max(0, symbolsCount));
    }
}

class TransactionAnalyzerTest {

    // загальний баланс
    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("2023-01-01", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("2023-01-02", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("2023-01-03", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);
        double result = analyzer.calculateTotalBalance();

        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    // транзакції за місяць
    @Test
    public void testCountTransactionsByMonth() {
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);

        int countFeb = analyzer.countTransactionsByMonth("02-2023");
        int countMar = analyzer.countTransactionsByMonth("03-2023");

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    // 10 найбільших витрат
    @Test
    public void testFindTopExpenses() {
        Transaction transaction1 = new Transaction("01-01-2023", -100.0, "Витрата 1");
        Transaction transaction2 = new Transaction("02-01-2023", -300.0, "Витрата 2");
        Transaction transaction3 = new Transaction("03-01-2023", -50.0, "Витрата 3");

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        TransactionAnalyzer analyzer = new TransactionAnalyzer(transactions);

        List<Transaction> topExpenses = analyzer.findTopExpenses();

        Assertions.assertEquals(3, topExpenses.size(), "Список найбільших витрат повинен містити 3 елементи");
        Assertions.assertEquals(-300.0, topExpenses.get(0).getAmount(), "Найбільша витрата має бути -300.0");
    }

    // Тестуємо читання даних із CSV
    @Test
    public void testReadTransactions() {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        TransactionCSVReader csvReader = new TransactionCSVReader();
        List<Transaction> transactions = csvReader.readTransactions(filePath);

        Assertions.assertFalse(transactions.isEmpty(), "Список транзакцій не має бути порожнім");
        Assertions.assertEquals(3, transactions.size(), "Кількість транзакцій має бути 3");
        Assertions.assertEquals("2023-01-01", transactions.get(0).getDate(), "Дата першої транзакції має бути 2023-01-01");
        Assertions.assertEquals(-50.0, transactions.get(1).getAmount(), "Сума другої транзакції має бути -50.0");
    }
}
