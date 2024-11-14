package org.example;

public class TestAutomaton {

    // Оголошуємо перерахування (enum) для станів автомата.
    private enum State {
        START, T, TE, TES, TEST, F
    }

    // Змінна для збереження поточного стану автомата
    private State currentState;

    // ініціалізує поточний стан в початковий (START)
    public TestAutomaton() {
        currentState = State.START;
    }

    // Метод для скидання стану автомата до початкового (START)
    public void reset() {
        currentState = State.START;
    }

    // Приймає рядок, символи з якого будуть оброблятись один за одним
    public int processString(String input) {
        // Спочатку автомат скидається в початковий стан
        reset();

        // Перебираємо кожен символ рядка
        for (char c : input.toCharArray()) {
            //Переход між станами в залежності від поточного стану та символу
            switch (currentState) {
                // Якщо автомат в початковому стані
                case START:
                    // Якщо символ 'T' — автомат переходить в стан T, інакше залишатись в START
                    currentState = (c == 'T') ? State.T : State.START;
                    break;

                // Якщо автомат в стані T (після зустрічі символу 'T')
                case T:
                    currentState = (c == 'E') ? State.TE : (c == 'T' ? State.T : State.START);
                    break;

                case TE:
                    currentState = (c == 'S') ? State.TES : (c == 'T' ? State.T : State.START);
                    break;

                case TES:
                    currentState = (c == 'T') ? State.TEST : State.START;
                    break;

                case TEST:
                    currentState = State.F; // Переходить в кінцевий стан
                    break;

                case F:
                    break; // Залишаємося в стані F, оскільки ми вже знайшли слово
            }
        }
        // Після завершення обробки рядка, повертаємо порядковий номер поточного стану
        return currentState.ordinal();
    }

    // Метод для тестування автомата
    public static void main(String[] args) {
        // Створюємо новий екземпляр автомата
        TestAutomaton automaton = new TestAutomaton();

        // Тестуємо автомат на різних рядках і виводимо результат
        System.out.println(automaton.processString("abcTESTabc")); // Виведе 4 (стан TEST)
        System.out.println(automaton.processString("abcTES")); // Виведе 3 (стан TES)
        System.out.println(automaton.processString("TEST")); // Виведе 4 (стан TEST)
        System.out.println(automaton.processString("abcd")); // Виведе 0 (стан START)
    }
}
