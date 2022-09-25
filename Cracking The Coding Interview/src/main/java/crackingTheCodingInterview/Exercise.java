package crackingTheCodingInterview;

import java.util.*;
import java.util.stream.Collectors;

public class Exercise {
    public static void main(String[] args) {

//        Question1_1.execute();
//        Question1_2.execute();
        Question1_3.execute();
    }
}

class Question1_1 {

    private static final String question = "不重複：實作一個演算法來判斷一個字串中的字元是否不重複。如果不能使用其他資料結構怎麼辦？";
    private static final String notUnique = "asnkfefhwiuorbfvscnbosaehwuhgdfbalxvkuaowehfvjshblkxhvbuidsvert";
    private static final String unique = "abcdefghijklmnopqrstuvwxyz";
    static void execute() {
        System.out.println("Q1_1");

        String input = unique;

        ver1_brutal(input);
        ver2_set(input);
        ver3_boolean_array(input);
        ver4_bit_mask(input);
    }

    static void ver1_brutal(String str) {
        boolean result;

        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    result = false;
                }
            }
        }
        result = true;

        System.out.println("ver1: Are elements of the string unique? " + result);
    }

    static void ver2_set(String str) {
        boolean result;

        Set<Integer> set = new HashSet<>();
        for (int c : str.toCharArray()) {
            try {
                set.add(c);
            } catch (IllegalArgumentException e) {
                result = false;
            }
        }
        result = true;

        System.out.println("ver2: Are elements of the string unique? " + result);
    }

    static void ver3_boolean_array(String str) {
        boolean result = true;

        boolean[] bArr = new boolean[str.length()];
        for (int i : str.toCharArray()) {
            if (bArr[i - 'a']) {
                result = false;
                break;
            }
            bArr[i - 'a'] = true;
        }

        System.out.println("ver3: Are elements of the string unique? " + result);
    }

    static void ver4_bit_mask(String str){
        boolean result;

        int checker = 0;
        for (int i : str.toCharArray()) {
            int val = i - 'a';
            if ((checker & (1 << val)) > 0) {
                result = false;
            }
            checker |= 1 << val;
        }
        result = true;

        System.out.println("ver4: Are elements of the string unique? " + result);
    }
}

class Question1_2 {
    private static final String question = "檢查變位字：給定兩個字串，寫一個方法來判斷一個是否是另一個的變位字。";
    private static final String[] trueArr = new String[]{"listen", "silent"};
    private static final String[] falseArr = new String[]{"apple", "pineapple"};

    static void execute() {
        System.out.println("Q1_2");

        String[] inputArr = trueArr;
        String str1 = inputArr[0];
        String str2 = inputArr[1];
        ver1_intuition(str1, str2);
        ver2_length_and_sort(str1, str2);
        ver3_counts(str1, str2);
    }

    static void ver1_intuition(String str1, String str2) {
        boolean result = true;
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();

        for (char c : str1.toCharArray()) {
            map1.put(c, map1.getOrDefault(c, 0) + 1);
        }

        for (char c : str2.toCharArray()) {
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        }

        // compare
        for (char key : map1.keySet()) {
            int val1 = map1.get(key);
            int val2 = map2.get(key);
            if (val1 != val2) {
                result = false;
                break;
            }
        }

        System.out.println("Ver1: permutation? " + result);
    }

    static void ver2_length_and_sort(String str1, String str2) {
        boolean result = false;

        if (str1.length() != str2.length()) {
            result = false;
        }

        char[] charArr1 = str1.toCharArray();
        char[] charArr2 = str2.toCharArray();
        Arrays.sort(charArr1);
        Arrays.sort(charArr2);
        if (new String(charArr1).equals(new String(charArr2))) {
            result = true;
        }

        System.out.println("Ver2: permutation? " + result);
    }

    static void ver3_counts(String str1, String str2) {
        boolean result = true;

        if (str1.length() != str2.length()) {
            result = false;
        }

        int[] counts = new int[128]; // ASCII elements
        for (char c : str1.toCharArray()) {
            counts[c]++;
        }

        for (char c : str2.toCharArray()) {
            counts[c]--;
            if (counts[c] < 0) {
                result = false;
                break;
            }
        }

        System.out.println("Ver3: permutation? " + result);
    }
}

class Question1_3 {
    private static final String question = "URLify";
    private static final String inputStr = "Mr John Smith     ";
    private static final int inputLen = 13;

    static void execute() {
        System.out.println("Q1_3");

        ver1_stream_joining(inputStr, true);
        ver2_char_array(inputStr, inputLen, true);

        perf();
    }

    static void ver1_stream_joining(String inputStr, boolean print) {
        String[] strArr = inputStr.split(" ");
        String result = Arrays.stream(strArr).collect(Collectors.joining("%20"));

        if (print) System.out.println("Ver1: " + result);
    }

    static void ver2_char_array(String inputStr, int inputLen, boolean print) {
        char[] charArr = inputStr.toCharArray();

        int spaceCount = 0, index;
        // From backwards
        for (int i = 0; i < inputLen; i++) {
            if (charArr[i] == ' ') {
                spaceCount ++;
            }
        }

        index = inputLen + 2 * spaceCount; // URLified string length

        if (inputLen < charArr.length) {
            charArr[inputLen] = '\0'; // 陣列結尾
        }

        for (int i = inputLen - 1; i >= 0; i--) {
            if (charArr[i] == ' ') {
                charArr[index - 1] = '0';
                charArr[index - 2] = '2';
                charArr[index - 3] = '%';
                index -= 3;
            } else {
                charArr[index - 1] = charArr[i];
                index--;
            }
        }

        if (print) System.out.println("Ver2: " + new String(charArr));
    }

    static void perf() {
        long start1 = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            ver1_stream_joining(inputStr, false);
        }
        long end1 = System.nanoTime();
        System.out.println("Ver1 Costs: " + (end1 - start1)/1000000);

        long start2 = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            ver2_char_array(inputStr, inputLen, false);
        }
        long end2 = System.nanoTime();
        System.out.println("Ver2 Costs: " + (end2 - start2)/1000000);
    }

}
