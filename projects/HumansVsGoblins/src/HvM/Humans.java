package HvM;

import java.util.Scanner;
import java.util.*;

import static java.lang.System.*;

public class Humans {
    public static void main(String[] args) {

        humans();

    }
    public static void humans() {
        String[][] arr = { { "-", "-", "-", "-", "-", "-", "-", "-", "-" }, { "-", "-", "-", "-", "-", "-", "-", "-", "-" }
                , { "-", "-", "-", "-", "-", "-", "-", "-", "-" } , { "-", "-", "-", "-", "-", "-", "-", "-", "-" }
                , { "-", "-", "-", "-", "-", "-", "-", "-", "-" } , { "-", "-", "-", "-", "-", "-", "-", "-", "-" }
                , { "-", "-", "-", "-", "-", "-", "-", "-", "-" } , { "-", "-", "-", "-", "-", "-", "-", "-", "-" }
                , { "-", "-", "-", "-", "-", "-", "-", "-", "-" } };
              arr[4][4] = "H";

        out.println("HUMANS VS GOBLINS ");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                out.print(arr[i][j] + " ");

            }

            out.println();

        }
        out.println("Make a move(N,S,E,W).");
//        // check if the specified element
//        // is present in the array or not
//        // using Linear Search method
//        boolean test = false;
//        for (String[] element : arr) {
//            if (element.equals("H")) {
//                test = true;
//                out.println(test);
//
//                break;
//            }
//        }
        Optional<String[]> indexOfHuman = Arrays.stream(arr).findAny("H");
        Scanner sc=new Scanner(in);
        String direction= sc.nextLine();
        String n="n";
        String s="s";
        String e="e";
        String w="w";
boolean choicen = direction.equals(n);
        boolean choices = direction.equals(s);
        boolean choicee = direction.equals(e);
        boolean choicew = direction.equals(w);
        if (choicen==choicen){



//            // find length of array
//            int len = arr.length;
//            int i = 0;
//
//            // traverse in the array
//            while (i < len) {
//
//                // if the i-th element is t
//                // then return the index
//                if (arr[i] == t) {
//                    int locationOfH=i;
//
//                }
//                else {
//                    i = i + 1;
//                }
//            }
//            int locationOfH=i--;
        }
        }

    }


