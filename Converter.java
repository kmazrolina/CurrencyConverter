import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.net.*;

class Converter {

    public static String getData(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (Exception e) {
            System.out.println(e);
            return "error";

        }
    }

    // kupno waluty
    public static double ask(double bidAmt, String askCurr) {
        try {
            URL askUrl = new URL("http://api.nbp.pl/api/exchangerates/rates/C/" + askCurr + "/");
            String askData = getData(askUrl);
            Pattern askPtrn = Pattern.compile("ask\":[0-9]*[.][0-9]*");
            Matcher askMtch = askPtrn.matcher(askData);
            askMtch.find();
            double askRate = Double.parseDouble((askMtch.group(0)).substring(5));
            return bidAmt / askRate;
        } catch (MalformedURLException e) {
            System.out.println(e);
            return -1;
        }

    }

    // spredaz waluty
    public static double bid(double bidAmt, String bidCurr) {
        try {
            URL bidUrl = new URL("http://api.nbp.pl/api/exchangerates/rates/C/" + bidCurr + "/");
            String bidData = getData(bidUrl);
            Pattern bidPtrn = Pattern.compile("bid\":[0-9]*[.][0-9]*");
            Matcher bidMtch = bidPtrn.matcher(bidData);
            bidMtch.find();
            double bidRate = Double.parseDouble((bidMtch.group(0)).substring(5));
            return bidAmt * bidRate;
        } catch (MalformedURLException e) {
            System.out.println(e);
            return -1;
        }

    }

    public static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
        sc.useLocale(Locale.US);
        Double bidAmt = sc.nextDouble();
        String bidCurr = sc.next("[A-Z]{3}");
        Double askAmt = 0.0;
        String askCurr = sc.next("[A-Z]{3}");

        boolean bidPLN = bidCurr.equals("PLN");
        boolean askPLN = askCurr.equals("PLN");

        if (bidCurr.equals(askCurr)) {
            askAmt = bidAmt;
        } else if (askPLN == false && bidPLN == true) { // kupno waluty za PLN
            askAmt = ask(bidAmt, askCurr);
        } else if (askPLN == true && bidPLN == false) { // sprzedaz waluty
            askAmt = bid(bidAmt, bidCurr);
        } else {
            // sprzedaz jednej waluty i kupno drugiej
            double tempBidAmt = bid(bidAmt, bidCurr);
            askAmt = ask(tempBidAmt, askCurr);

        }

        System.out.format(Locale.US, "%.2f " + askCurr, askAmt);

    }
}