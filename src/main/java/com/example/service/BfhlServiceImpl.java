package com.example.service;

import com.example.dto.BfhlRequest;
import com.example.dto.BfhlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.id}")
    private String userId;

    @Value("${bfhl.user.email}")
    private String email;

    @Value("${bfhl.user.roll-number}")
    private String rollNumber;

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        List<String> data = request.getData();

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long sum = 0;
        List<Character> alphaChars = new ArrayList<>();

        for (String item : data) {
            if (item == null || item.isEmpty()) {
                continue;
            }

            if (isNumeric(item)) {
                long num = Long.parseLong(item);
                sum += num;
                if (num % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlpha(item)) {
                alphabets.add(item.toUpperCase());
                for (char c : item.toCharArray()) {
                    alphaChars.add(c);
                }
            } else {
                boolean hasLetters = false;
                for (char c : item.toCharArray()) {
                    if (Character.isLetter(c)) {
                        hasLetters = true;
                        break;
                    }
                }

                if (!hasLetters) {
                    specialCharacters.add(item);
                } else {
                    specialCharacters.add(item);
                    for (char c : item.toCharArray()) {
                        if (Character.isLetter(c)) {
                            alphaChars.add(c);
                        }
                    }
                }
            }
        }

        Collections.reverse(alphaChars);
        StringBuilder concat = new StringBuilder();
        for (int i = 0; i < alphaChars.size(); i++) {
            char ch = alphaChars.get(i);
            concat.append(i % 2 == 0 ? Character.toUpperCase(ch) : Character.toLowerCase(ch));
        }

        BfhlResponse res = new BfhlResponse();
        res.setSuccess(true);
        res.setUserId(userId);
        res.setEmail(email);
        res.setRollNumber(rollNumber);
        res.setOddNumbers(oddNumbers);
        res.setEvenNumbers(evenNumbers);
        res.setAlphabets(alphabets);
        res.setSpecialCharacters(specialCharacters);
        res.setSum(String.valueOf(sum));
        res.setConcatString(concat.toString());

        return res;
    }

    private boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isAlpha(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }
}
