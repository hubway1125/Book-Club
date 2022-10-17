package crackingTheCodingInterview;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Exercise {
    public static void main(String[] args) throws Exception {

//        Question1_1.execute();
//        Question1_2.execute();
//        Question1_3.execute();
//        Question1_4.execute();
//        Question1_5.execute();
//        Question1_6.execute();
        Question1_7.execute();
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

    private static void ver1_brutal(String str) {
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

    private static void ver2_set(String str) {
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

    private static void ver3_boolean_array(String str) {
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

    private static void ver4_bit_mask(String str) {
        boolean result;

        byte checker = 0;
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

    private static void ver1_intuition(String str1, String str2) {
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

    private static void ver2_length_and_sort(String str1, String str2) {
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

    private static void ver3_counts(String str1, String str2) {
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

    private static void ver1_stream_joining(String inputStr, boolean print) {
        String[] strArr = inputStr.split(" ");
        String result = Arrays.stream(strArr).collect(Collectors.joining("%20"));

        if (print) System.out.println("Ver1: " + result);
    }

    private static void ver2_char_array(String inputStr, int inputLen, boolean print) {
        char[] charArr = inputStr.toCharArray();

        int spaceCount = 0, index;
        // From backwards
        for (int i = 0; i < inputLen; i++) {
            if (charArr[i] == ' ') {
                spaceCount++;
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

    private static void perf() {
        long start1 = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            ver1_stream_joining(inputStr, false);
        }
        long end1 = System.nanoTime();
        System.out.println("Ver1 Costs: " + (end1 - start1) / 1000000);

        long start2 = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            ver2_char_array(inputStr, inputLen, false);
        }
        long end2 = System.nanoTime();
        System.out.println("Ver2 Costs: " + (end2 - start2) / 1000000);
    }

}

class Question1_4 {
    private static final String question = "回文變位字";
    private static final String input = "Tact Coa";

    static void execute() {
        System.out.println("Q1_4");
        System.out.println("Ver1: " + ver1());
        System.out.println("Ver2: " + ver2());
    }

    private static boolean ver1() {
        int[] arr = new int[26];

        for (char c : input.toLowerCase().replace(" ", "").toCharArray()) {
            int val = c - 'a';
            if (arr[val] == 0) {
                arr[val] += 1;
            } else {
                arr[val] -= 1;
            }
        }

        int counter = 0;
        for (int i : arr) {
            if (i == 1) {
                counter++;
                if (counter > 1) {
                    return false;
                }
            }
        }

        return true;

    }

    private static boolean ver2() {
        String filteredStr = input.replace(" ", "").toLowerCase();

        int checker = 0; // bitmask

        // toggle bitmask
        for (int i : filteredStr.toCharArray()) {
            int val = i - 'a';
            int mask = 1 << val;

            // toggle bit ver1
//            if ((checker & mask) == 0) { // 完全交錯
//                checker |= mask;
//            } else {
//                checker &= mask;
//            }

            // toggle bit ver2
            checker ^= mask; // Bitwise XOR
        }

        /*
            check at most a 1 in the bit
            example:
                00001000 ----> 00001000
                00001000 -1 -> 00000111
            &)_________________________
                               00000000 -> if all are 0 means no 1, and this means the original bit has only a 1
         */
        return ((checker & (checker - 1)) == 0);

    }
}

class Question1_5 {
    private static final String question = "一個編輯距離";

    private static final List<String[]> testCases = Arrays.asList(
            new String[]{"pale", "ple", "true"}, // 刪除
            new String[]{"ple", "pale", "true"}, // 插入
            new String[]{"pale", "bale", "true"}, // 替換
            new String[]{"pale", "pale", "true"}, // 完全相同
            new String[]{"pale", "bakery", "false"}, // 錯誤
            new String[]{"pale", "palate", "false"}, // 錯誤
            new String[]{"pale", "bake", "false"} // 錯誤
    );

    static void execute() throws Exception {
        System.out.println("Q1_5");
        String version = "2";
        boolean[] result = validate(version);
        System.out.println("Ver" + version + ": " + toString(result) + "; " + checkResult(result));
    }

    private static boolean ver1(String[] input) {
        String str1 = input[0];
        String str2 = input[1];

        // swap to make str1 longer than str2
        if (str2.length() > str1.length()) {
            String temp = str1;
            str1 = str2;
            str2 = temp;
        }

        if (Math.abs(str1.length() - str2.length()) > 1) {
            return false;
        }

        /*
            iterate through every char of shorter string
            only skip once for longer string means correct
         */
        boolean diff = false;
        for (int iS = 0, iL = 0; iS < str2.length(); iS++, iL++) {
            if (str2.charAt(iS) != str1.charAt(iL)) {
                if (str1.length() == str2.length()) {
                    if (diff) {
                        return false;
                    }
                    diff = true;
                }
                // skip and diff++;
                if (str2.charAt(iS) != str1.charAt(++iL)) { // insert or delete
                    if (diff) {
                        return false;
                    }
                    diff = true;
                }
            }
        }

        return true;
    }

    private static boolean ver2(String[] input) {
        // two strings have more than one char difference
        if (Math.abs(input[0].length() - input[1].length()) > 1) {
            return false;
        }

        // str1 shorter than str2
        String str1 = input[0].length() < input[1].length() ? input[0] : input[1];
        String str2 = input[0].length() < input[1].length() ? input[1] : input[0];

        int i1 = 0, i2 = 0;
        boolean diff = false;
        while (i1 < str1.length() && i2 < str2.length()) {
            if (str1.charAt(i1) != str2.charAt(i2)) {
                if (diff) // already has one diff
                    return false; // second diff
                diff = true; // first diff

                if (str1.length() == str2.length()) { // replace
                    i1++;
                }
                // insert or delete, only moves i2
            } else {
                i1++;
            }
            i2++;
        }
        return true;
    }

    private static boolean[] validate(String ver) throws Exception {
        Method method = Question1_5.class.getDeclaredMethod("ver" + ver, String[].class);
        method.setAccessible(true);

        boolean[] result = new boolean[testCases.size()];
        for (int i = 0; i < testCases.size(); i++) {
            result[i] = (boolean) method.invoke(Question1_5.class, (Object) testCases.get(i));
        }
        return result;
    }

    private static String toString(boolean[] boolArr) {
        List<String> results = new ArrayList<>();
        for (boolean bool : boolArr) {
            results.add(String.valueOf(bool));
        }
        return results.stream().collect(Collectors.joining(", "));
    }

    private static String checkResult(boolean[] boolArr) {
        boolean result = true;
        for (int i = 0; i < testCases.size(); i++) {
            boolean isCorrect = String.valueOf(boolArr[i]).equals(testCases.get(i)[2]);
            result &= isCorrect;
        }
        return result ? "Correct!" : "Incorrect!";
    }

}

class Question1_6 {
    private static final String question = "字串壓縮";
    private static final String input = "aabcccccaaa"; // ^[A-Za-z]+$

    static void execute() {
        System.out.println("Q1_6");
        System.out.println("Ver1: " + ver1());
    }

    private static String ver1() {
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            count++;

            char cur = input.charAt(i);
            // end or not consecutive
            if (i + 1 >= input.length() || cur != input.charAt(i+1)) {
                // write result
                if ('A' <= cur && cur <= 'z') {
                    sb.append(cur);
                    sb.append(count);
                }

                // reset
                count = 0;
            }
        }

        return sb.toString().length() < input.length() ? sb.toString() : input;
    }

    private static String ver2() {
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            count++;

            char cur = input.charAt(i);
            // end or not consecutive
            if (i + 1 >= input.length() || cur != input.charAt(i+1)) {
                // write result
                if ('A' <= cur && cur <= 'z') {
                    sb.append(cur);
                    sb.append(count);
                }

                if (sb.length() > input.length()) {
                    return input;
                }

                // reset
                count = 0;
            }
        }

        return sb.toString().length() < input.length() ? sb.toString() : input;
    }

}

class Question1_7 {
    private static final String question = "旋轉矩陣";
    /*
        1 0 1 1
        0 1 1 1
        1 1 1 1
        1 1 1 0

        (0,1), (1,0), (3,3)
     */
    private static final int[][] input = new int[][]{
            {1, 0, 1, 1},
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 0}
    };

    static void execute() {
        System.out.println("Q1_7");
        System.out.println("Ver1");
        ver1();
    }

    private static void ver1() {
        int[][] copyOfInput = new int[input.length][];
        for (int i = 0; i < input.length; i++) {
            copyOfInput[i] = new int[input[i].length];
            System.arraycopy(input[i], 0, copyOfInput[i], 0, input.length);
        }

        int[] zeroPos = new int[input.length];
        Arrays.fill(zeroPos, -1);

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length; x++) {
                if (input[y][x] == 0) {
                    zeroPos[y] = x;
                }
            }
        }
        printZeroPos(zeroPos);

        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length; x++) {
                int finalX = x;
                if (zeroPos[y] != -1 || Arrays.stream(zeroPos).anyMatch(pos -> pos == finalX)) { // row to 0
                    copyOfInput[y][x] = 0;
                }
            }
        }
        printZeroArr(copyOfInput);
    }

    private static void printZeroPos(int[] zeroPos) {
        String result = "";
        for (int i = 0; i < zeroPos.length; i++) {
            result += String.format("(%d,%d)", i, zeroPos[i]);
        }
        System.out.println(result);
    }

    private static void printZeroArr(int[][] copyOfInput) {
        for (int m = 0; m < copyOfInput.length; m++) {
            StringBuilder sb = new StringBuilder();
            for (int n = 0; n < copyOfInput[m].length; n++) {
                sb.append(copyOfInput[m][n]);
                if (n + 1 < copyOfInput[m].length) {
                    sb.append(", ");
                }
            }
            System.out.println(sb);
        }
    }

}