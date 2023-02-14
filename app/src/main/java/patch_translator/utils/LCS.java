package patch_translator.utils;

import java.util.ArrayList;
import java.util.Objects;

public class LCS {
    // create findLengthOfLCS() method that returns the longest common sequences
    private ArrayList<Objects> LCS;
    private int lengthOfLCS;
    public static String findLengthOfLCS(String str1, String str2, int p, int q) {

        // create a matrix which act as a table for LCS
        int[][] tableForLCS = new int[p + 1][q + 1];

        // fill the table in the bottom up way
        for (int i = 0; i <= p; i++) {
            for (int j = 0; j <= q; j++) {
                if (i == 0 || j == 0)
                    tableForLCS[i][j] = 0;  // Fill each cell corresponding to first row and first column with 0
                else if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    tableForLCS[i][j] = tableForLCS[i - 1][j - 1] + 1;  // add 1 in the cell of the previous row and column and fill the current cell with it
                else
                    tableForLCS[i][j] = Math.max(tableForLCS[i - 1][j], tableForLCS[i][j - 1]); //find the maximum value from the cell of the previous row and current column and the cell of the current row and previous column
            }
        }

        int index = tableForLCS[p][q];
        int temp = index;

        char[] longestCommonSubsequence = new char[index + 1];
        longestCommonSubsequence[index] = '\0';

        int i = p, j = q;
        String lcs ="";
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {

                longestCommonSubsequence[index - 1] = str1.charAt(i - 1);
                i--;
                j--;
                index--;
            }
            else if (tableForLCS[i - 1][j] > tableForLCS[i][j - 1])
                i--;
            else
                j--;
        }

        for (int k = 0; k <= temp; k++)
            lcs = lcs + longestCommonSubsequence[k];

        return lcs;
    }
}
