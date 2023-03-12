# CurrencyConverter

## General info
The project involves using the NBP (National Bank of Poland) [API](http://api.nbp.pl/) to create a currency conversion tool. The application has a simple graphical user interface (GUI) created using the javax.swing library. The tool will allow users to input a currency and amount, and then convert it to another currency based on current exchange rates provided by the NBP.
![Zrzut ekranu 2023-03-12 o 20 20 39](https://user-images.githubusercontent.com/121491288/224567817-434b7074-067b-4de4-8191-95313d6455ac.jpg)

	
## Requirements

- Java 12 or higher
- Internet connection to access the NBP API
	

## How to run the application

1. Clone or download the repository to your local machine
2. Open the project in your Java IDE of choice
3. Build and run the `GUI.java` file

## How to use the application

1. Select the currency you want to convert from in the combobox menu under "You send" or "They receive" label
2. Enter the amount you want to convert in the text field 
3. Select the currency you want to convert to in the combobox menu under "You send" or "They receive" label (depending on what option has been chosen in step 1.)
4. Press "Enter" to convert

## Libraries Used

- `javax.swing`, `java.awt` - for creating the GUI
- `import java.net`,`import java.io`, `import java.util.regex` - for parsing JSON data from the NBP API

## Authors

- Karolina Źróbek - karolina.m.zrobek@gmail.com

