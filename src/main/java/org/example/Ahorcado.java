package org.example;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

public class Ahorcado extends JFrame implements ActionListener {
    private JTextField textField;
    private JLabel wordLabel;
    private JLabel attemptsLabel;
    private JButton submitButton;
    private String wordToGuess;
    private int attemptsLeft;
    private StringBuilder guessedWord;
    private List<String> words;

    public Ahorcado() {
        setTitle("Ahorcado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new FlowLayout());

        wordLabel = new JLabel();
        add(wordLabel);

        attemptsLabel = new JLabel();
        add(attemptsLabel);

        textField = new JTextField(10);
        add(textField);

        submitButton = new JButton("Adivinar");
        submitButton.addActionListener(this);
        add(submitButton);

        loadWords();
        initializeGame();

        setVisible(true);
    }

    private void loadWords() {
        words = new ArrayList<>();

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("words.txt");
            assert is != null;
            Scanner scanner = new Scanner(is);

            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine().toLowerCase());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void initializeGame() {
        wordToGuess = words.get(new Random().nextInt(words.size()));
        attemptsLeft = 6;

        guessedWord = new StringBuilder(wordToGuess.length());
        for (int i = 0; i < wordToGuess.length(); i++) {
            guessedWord.append("_");
        }

        wordLabel.setText(guessedWord.toString());
        attemptsLabel.setText("Intentos restantes: " + attemptsLeft);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = textField.getText().toLowerCase();

        if (input.trim().length() == 1) {
            char guessedChar = input.charAt(0);
            boolean correctGuess = false;

            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == guessedChar) {
                    guessedWord.setCharAt(i, guessedChar);
                    correctGuess = true;
                }
            }

            if (!correctGuess) {
                attemptsLeft--;
                attemptsLabel.setText("Intentos restantes: " + attemptsLeft);
            }

            wordLabel.setText(guessedWord.toString());

            if (guessedWord.toString().equals(wordToGuess)) {
                JOptionPane.showMessageDialog(this, "¡Ganaste!");
                initializeGame();
            } else if (attemptsLeft == 0) {
                JOptionPane.showMessageDialog(this, "¡Perdiste! La palabra era: " + wordToGuess);
                initializeGame();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Solo podes ingresar una letra a adivinar");
        }

        textField.setText("");
    }
}
