package KMPAlgorithm;

import java.util.ArrayList;
import java.util.List;


//  Knuth-Morris-Pratt (KMP) String Matching Algorithm
//  This implementation provides efficient pattern searching in a text with O(n+m) time complexity
//  where n is the length of the text and m is the length of the pattern.
public class KMP {
    //  Builds the Longest Proper Prefix, which is also a Suffix (LPS) array,
    //  This preprocessing step is crucial for KMP algorithm's efficiency
    //  Time Complexity: O(m) where m is pattern length
    //  Space Complexity: O(m) for the LPS array
    private static int[] buildLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        // Length of the previous longest prefix suffix
        int len = 0;
        lps[0] = 0; // lps[0] is always 0

        int i = 1;

        // Calculate lps[i] for i = 1 to m-1
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                // Characters match, extend the current prefix-suffix
                len++;
                lps[i] = len;
                i++;
            } else {
                // Mismatch after len matches
                if (len != 0) {
                    // Try with the previous longest prefix suffix
                    // This is the key optimization - we don't start from 0
                    len = lps[len - 1];
                } else {
                    // No prefix-suffix match found
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    //  Searches for all occurrences of a pattern in text using KMP algorithm
    //  Time Complexity: O(n + m) where n is text length and m is a pattern length
    //  Space Complexity: O(m) for the LPS array
    //  return List of starting indices where a pattern is found in text
    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();

        // Edge cases
        if (pattern == null || pattern.isEmpty()) {
            return matches;
        }
        if (text == null || text.isEmpty() || text.length() < pattern.length()) {
            return matches;
        }

        int n = text.length();
        int m = pattern.length();

        // Preprocessing: Build LPS array
        int[] lps = buildLPSArray(pattern);

        // Searching phase
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                // Characters match, move both pointers
                i++;
                j++;
            }

            if (j == m) {
                // Pattern found at index (i - j)
                matches.add(i - j);

                // Continue searching for a next occurrence
                // Use LPS to avoid redundant comparisons
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                // Mismatch after j matches
                if (j != 0) {
                    // Use LPS array to skip redundant comparisons
                    // Don't match lps[0... lps[j-1]] characters, they will match anyway
                    j = lps[j - 1];
                } else {
                    // No match found, move a text pointer
                    i++;
                }
            }
        }

        return matches;
    }

    //  Utility method to print search results in a formatted way
    private static void printResults(String testName, String text, String pattern,
                                     List<Integer> matches, long timeTaken) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST: " + testName);
        System.out.println("=".repeat(80));
        System.out.println("Text length: " + text.length() + " characters");
        System.out.println("Pattern length: " + pattern.length() + " characters");
        System.out.println("Text: " + (text.length() <= 100 ? text : text.substring(0, 100) + "..."));
        System.out.println("Pattern: \"" + pattern + "\"");
        System.out.println("\nMatches found: " + matches.size());

        if (!matches.isEmpty()) {
            System.out.println("Positions: " + matches);

            // Show the first few matches in context
            int showCount = Math.min(3, matches.size());
            System.out.println("\nFirst " + showCount + " match(es) in context:");
            for (int i = 0; i < showCount; i++) {
                int pos = matches.get(i);
                int start = Math.max(0, pos - 10);
                int end = Math.min(text.length(), pos + pattern.length() + 10);
                String context = text.substring(start, end);
                System.out.println("  [" + pos + "]: ..." + context + "...");
            }
        } else {
            System.out.println("No matches found.");
        }

        System.out.println("\nExecution time: " + timeTaken + " microseconds");
        System.out.println("=".repeat(80));
    }

    //  The main method demonstrating KMP algorithm with three test cases:
    //  1. Short string (< 100 characters)
    //  2. Medium string (100-1000 characters)
    //  3. Long string (> 1000 characters)
    public static void main(String[] args) {
        System.out.println("KNUTH-MORRIS-PRATT (KMP) STRING MATCHING ALGORITHM");
        System.out.println("Implementation and Testing\n");

        // Test 1: Short String
        String text1 = "ABABDABACDABABCABAB";
        String pattern1 = "ABABCABAB";

        long start1 = System.nanoTime();
        List<Integer> matches1 = search(text1, pattern1);
        long end1 = System.nanoTime();

        printResults("Short String Test", text1, pattern1, matches1, (end1 - start1) / 1000);

        // Test 2: Medium String
        StringBuilder text2Builder = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            text2Builder.append("The quick brown fox jumps over the lazy dog. ");
        }
        String text2 = text2Builder.toString();
        String pattern2 = "fox jumps";

        long start2 = System.nanoTime();
        List<Integer> matches2 = search(text2, pattern2);
        long end2 = System.nanoTime();

        printResults("Medium String Test", text2, pattern2, matches2, (end2 - start2) / 1000);

        // Test 3: Long String with a DNA sequence a pattern
        StringBuilder text3Builder = new StringBuilder();
        String dnaBase = "ACGTACGTTAGCTAGCTAGCTAGCTGACGTACGTACGTACGT";
        for (int i = 0; i < 500; i++) {
            text3Builder.append(dnaBase);
        }
        String text3 = text3Builder.toString();
        String pattern3 = "TAGCTAGCT";

        long start3 = System.nanoTime();
        List<Integer> matches3 = search(text3, pattern3);
        long end3 = System.nanoTime();

        printResults("Long String Test (DNA Sequence)", text3, pattern3, matches3, (end3 - start3) / 1000);

        // Test4: Pattern not found
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ADDITIONAL TEST: Pattern Not Found");
        System.out.println("=".repeat(80));
        String text4 = "AAAAAAAAAA";
        String pattern4 = "AAAB";

        long start4 = System.nanoTime();
        List<Integer> matches4 = search(text4, pattern4);
        long end4 = System.nanoTime();

        printResults("No Match Test", text4, pattern4, matches4, (end4 - start4) / 1000);

        // Complexity Analysis Summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("Complexity Analysis");
        System.out.println("=".repeat(80));
        System.out.println("Time Complexity:");
        System.out.println("  - Preprocessing (LPS array): O(m)");
        System.out.println("  - Searching: O(n)");
        System.out.println("  - Overall: O(n + m)");
        System.out.println("\nSpace Complexity:");
        System.out.println("  - LPS array: O(m)");
        System.out.println("  - Overall: O(m)");
        System.out.println("\nWhere:");
        System.out.println("  n = length of text");
        System.out.println("  m = length of pattern");
        System.out.println("=".repeat(80));
    }
}
