# Knuth-Morris-Pratt (KMP) String Matching Algorithm

## Algorithm Description

The KMP algorithm improves upon naive string matching by avoiding redundant character comparisons. It achieves this through preprocessing the pattern to build a "Longest Proper Prefix which is also Suffix" (LPS) array.

### Key Features:
- **Efficient**: O(n + m) time complexity
- **No Backtracking**: Never re-examines text characters
- **Optimal**: Linear time for both preprocessing and searching

## Implementation Details

### Core Components

1. **LPS Array Construction** (`buildLPSArray` method)
    - Preprocesses the pattern to identify prefix-suffix relationships
    - Time: O(m), Space: O(m)
    - The LPS array stores the length of the longest proper prefix, which is also a suffix for each position

2. **Pattern Search** (`search` method)
    - Uses the LPS array to skip redundant comparisons
    - Time: O(n), Space: O(m)
    - Returns all starting indices where the pattern occurs in the text

## Complexity Analysis

### Time Complexity: O(n + m)
- **Preprocessing**: O(m) to build the LPS array
- **Searching**: O(n) to scan through the text
- **Overall**: O(n + m) where:
    - n = length of text
    - m = length of a pattern

### Space Complexity: O(m)
- LPS array requires O(m) space
- The result list stores O(k) matches where k ≤ n
- Overall auxiliary space: O(m)

### Comparison with Naive Approach

| Algorithm | Best Case | Average Case | Worst Case | Space |
|-----------|-----------|--------------|------------|-------|
| Naive     | O(n)      | O(nm)        | O(nm)      | O(1)  |
| KMP       | O(n+m)    | O(n+m)       | O(n+m)     | O(m)  |

## Test Cases

The implementation includes three comprehensive test cases:

### Test 1: Short String
- **Text**: "ABABDABACDABABCABAB" (19 characters)
- **Pattern**: "ABABCABAB" (9 characters)
- **Purpose**: Verify correctness with overlapping patterns
- **Expected**: Pattern found at index 10

### Test 2: Medium String
- **Text**: 2,250 characters (50 repetitions of a sentence)
- **Pattern**: "fox jumps"
- **Purpose**: Test performance on moderate-sized input
- **Expected**: 50 matches found

### Test 3: Long String
- **Text**: 21,000 characters (DNA sequence repetitions)
- **Pattern**: "TAGCTAGCT"
- **Purpose**: Evaluate scalability and performance on large datasets
- **Expected**: Multiple matches in a DNA sequence

### Test 4: No Match Case
- **Text**: "AAAAAAAAAA"
- **Pattern**: "AAAB"
- **Purpose**: Verify behavior when a pattern doesn't exist
- **Expected**: 0 matches


### Expected Output
The program will display:
1. Test results for each test case
2. Number of matches found
3. Positions of matches
4. First few matches shown in context
5. Execution time in microseconds
6. Complexity analysis summary

## Sample Output

```
KNUTH-MORRIS-PRATT (KMP) STRING MATCHING ALGORITHM
Implementation and Testing

================================================================================
TEST: Short String Test
================================================================================
Text length: 19 characters
Pattern length: 9 characters
Text: ABABDABACDABABCABAB
Pattern: "ABABCABAB"

Matches found: 1
Positions: [10]

First 1 match(es) in context:
  [10]: ...DABABCABAB...

Execution time: 45 microseconds
================================================================================
```

## Advantages of KMP

1. **No Backtracking**: A text pointer never moves backward
2. **Linear Time**: Guaranteed O(n+m) performance
3. **Predictable**: Consistent performance regardless of input patterns
4. **Optimal for Streaming**: Can process text as a stream

## Use Cases

- **Text Editors**: Find and replace operations
- **Bioinformatics**: DNA sequence matching
- **Network Security**: Intrusion detection pattern matching
- **Data Processing**: Log file analysis
- **Plagiarism Detection**: Document comparison

## Summary

The **KMP** algorithm is a simple yet effective string matching algorithm that achieves linear time performance on both preprocessing and searching. It is particularly useful in applications where the pattern is expected to occur frequently in the text.