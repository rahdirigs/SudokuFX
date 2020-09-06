package org.example;

import java.util.HashSet;
import java.util.Set;

public class Solver {
    Set<Integer> test;

    public Solver() {
        test = new HashSet<>();
        for (int i = 1; i <= 9; i++)
            test.add(i);
    }

    public boolean isValidRow(Board b, int row) {
        Set<Integer> nums = new HashSet<>(test);
        for (int c = 0; c < 9; c++) {
            Integer ret = b.get(c, row);
            if (ret == null)
                continue;
            if (!nums.contains(ret))
                return false;
            nums.remove(ret);
        }
        return true;
    }

    public boolean isValidCol(Board b, int col) {
        Set<Integer> nums = new HashSet<>(test);
        for (int r = 0; r < 9; r++) {
            Integer ret = b.get(col, r);
            if (ret == null)
                continue;
            if (!nums.contains(ret))
                return false;
            nums.remove(ret);
        }
        return true;
    }

    public boolean isValidSquare(Board b, int col, int row) {
        Set<Integer> nums = new HashSet<>(test);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Integer ret = b.get(col + c, row + r);
                if (ret == null)
                    continue;
                if (!nums.contains(ret))
                    return false;
                nums.remove(ret);
            }
        }
        return true;
    }

    public boolean isValidAllRows(Board b) {
        for (int r = 0; r < 9; r++)
            if (!isValidRow(b, r))
                return false;
        return true;
    }

    public boolean isValidAllCols(Board b) {
        for (int c = 0; c < 9; c++)
            if (!isValidCol(b, c))
                return false;
        return true;
    }

    public boolean isValidAllSquares(Board b) {
        for (int c = 0; c < 9; c += 3) {
            for (int r = 0; r < 9; r += 3) {
                if (!isValidSquare(b, c, r))
                    return false;
            }
        }
        return true;
    }

    public boolean isValidBoard(Board b) {
        return isValidAllRows(b) && isValidAllCols(b) && isValidAllSquares(b);
    }

    public boolean isBoardComplete(Board b) {
        for (int c = 0; c < 9; c++) {
            for (int r = 0; r < 9; r++) {
                Integer val = b.get(c, r);
                if (val == null)
                    return false;
            }
        }
        return true;
    }

    public boolean completeAndValidBoard(Board b) {
        return isValidBoard(b) && isBoardComplete(b);
    }

    public Board solveBoard(Board in) {
        if (!isValidBoard(in))
            return null;
        if (completeAndValidBoard(in))
            return in;

        Board b = new Board(in);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Integer i = b.get(c, r);
                if (i == null) {
                    Set<Integer> set = new HashSet<>(test);
                    for (Integer s : set) {
                        b.set(c, r, s);
                        Board sol = solveBoard(b);
                        if (sol != null) {
                            return sol;
                        }
                    }
                    return null;
                }
            }
        }
        return null;
    }
}
