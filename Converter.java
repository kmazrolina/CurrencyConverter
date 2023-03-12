import java.util.regex.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;


class Converter {
    
    ArrayList<String> currencies;
   
    Converter(){
        getCurrenciesUtil();

    }

    public void getCurrencies(char AorB){
        try{
            
            
            URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/"+ AorB + "/");
            String data = getData(url);
            data = data + getData(url);
            Pattern codePtrn = Pattern.compile("code\":\"[A-Z]{3}");
            Matcher codeMtch = codePtrn.matcher(data);
            codeMtch.find();
            
            while (codeMtch.find()) {
                currencies.add(codeMtch.group().substring(7));
                
            }
            
        } catch (MalformedURLException e) {
        }
    }
    public void getCurrenciesUtil(){
        currencies = new ArrayList<>();
        getCurrencies('A');
        getCurrencies('B');
            
        
    }
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
    public double ask(double bidAmt, String askCurr) {
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
    public double bid(double bidAmt, String bidCurr) {
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

    public double convertUtil(
            double bidAmt,
            String bidCurr,
            String askCurr) {
        double askAmt;
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

        return askAmt;
    }


    public static void main(String args[]) {
        
    }
}