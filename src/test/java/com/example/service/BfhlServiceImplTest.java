package com.example.service;

import com.example.dto.BfhlRequest;
import com.example.dto.BfhlResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BfhlServiceImplTest {

    @Autowired
    private BfhlService bfhlService;

    @Test
    @DisplayName("Example A - mixed input")
    void exampleA() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertNotNull(res.getUserId());
        assertNotNull(res.getEmail());
        assertNotNull(res.getRollNumber());
        assertEquals(List.of("1"), res.getOddNumbers());
        assertEquals(Arrays.asList("334", "4"), res.getEvenNumbers());
        assertEquals(Arrays.asList("A", "R"), res.getAlphabets());
        assertEquals(List.of("$"), res.getSpecialCharacters());
        assertEquals("339", res.getSum());
        assertEquals("Ra", res.getConcatString());
    }

    @Test
    @DisplayName("Example B - multiple special chars")
    void exampleB() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertEquals(List.of("5"), res.getOddNumbers());
        assertEquals(Arrays.asList("2", "4", "92"), res.getEvenNumbers());
        assertEquals(Arrays.asList("A", "Y", "B"), res.getAlphabets());
        assertEquals(Arrays.asList("&", "-", "*"), res.getSpecialCharacters());
        assertEquals("103", res.getSum());
        assertEquals("ByA", res.getConcatString());
    }

    @Test
    @DisplayName("Example C - only alphabets with multi-char strings")
    void exampleC() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertEquals(Arrays.asList("A", "ABCD", "DOE"), res.getAlphabets());
        assertTrue(res.getSpecialCharacters().isEmpty());
        assertEquals("0", res.getSum());
        assertEquals("EoDdCbAa", res.getConcatString());
    }

    @Test
    @DisplayName("empty data array")
    void emptyInput() {
        BfhlRequest req = new BfhlRequest(Collections.emptyList());
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertTrue(res.getAlphabets().isEmpty());
        assertTrue(res.getSpecialCharacters().isEmpty());
        assertEquals("0", res.getSum());
        assertEquals("", res.getConcatString());
    }

    @Test
    @DisplayName("only numbers in input")
    void onlyNumbers() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("1", "2", "3", "100"));
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertEquals(Arrays.asList("1", "3"), res.getOddNumbers());
        assertEquals(Arrays.asList("2", "100"), res.getEvenNumbers());
        assertTrue(res.getAlphabets().isEmpty());
        assertTrue(res.getSpecialCharacters().isEmpty());
        assertEquals("106", res.getSum());
        assertEquals("", res.getConcatString());
    }

    @Test
    @DisplayName("only special characters")
    void onlySpecialChars() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("@", "#", "!", "^"));
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertTrue(res.getAlphabets().isEmpty());
        assertEquals(Arrays.asList("@", "#", "!", "^"), res.getSpecialCharacters());
        assertEquals("0", res.getSum());
        assertEquals("", res.getConcatString());
    }

    @Test
    @DisplayName("single letter input")
    void singleLetter() {
        BfhlRequest req = new BfhlRequest(List.of("z"));
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertEquals(List.of("Z"), res.getAlphabets());
        assertEquals("Z", res.getConcatString());
    }

    @Test
    @DisplayName("negative number is treated as odd")
    void negativeNumber() {
        BfhlRequest req = new BfhlRequest(Arrays.asList("-3", "4"));
        BfhlResponse res = bfhlService.processData(req);

        assertTrue(res.isSuccess());
        assertEquals(List.of("-3"), res.getOddNumbers());
        assertEquals(List.of("4"), res.getEvenNumbers());
        assertEquals("1", res.getSum());
    }

    @Test
    @DisplayName("zero goes into even numbers")
    void zeroIsEven() {
        BfhlRequest req = new BfhlRequest(List.of("0"));
        BfhlResponse res = bfhlService.processData(req);

        assertEquals(List.of("0"), res.getEvenNumbers());
        assertTrue(res.getOddNumbers().isEmpty());
        assertEquals("0", res.getSum());
    }
}
