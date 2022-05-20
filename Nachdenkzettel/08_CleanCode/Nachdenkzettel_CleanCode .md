# Nachdenkzettel: Clean Code

# Aufgabe 1

Interface or Composition

# Aufgabe 2

Indexer are used in for-loops.

When we have an two dimensional Array and want to loop through every entry whe have to use a nested for-loop (so a for-loop in an other for-loop).

The index names should be on point with there task.

A good example would look like: (Non-confused-indexer)

for(int playlist = 0; playlist < mediaCenter.length(); playlist++){
    for(int song = 0; song < mediaCenter.length(); song++){
        value = mediaCenter[playlist][song]
    }
}

A bad example would look like: (confused-indexer)

for(int a = 0; a < mediaCenter.length(); a++){
    for(int giraffe = 0; giraffe < mediaCenter.length(); giraffe++){
        value = mediaCenter[a][giraffe]
    }
}

# Aufgabe 3

The Object “Adress” should be initialized by the Consturctor.
Values which belong together like “city and zipcode” or “street and number” should be initialized together.
(And variable names should be written small)

public class Address {

    private String city;
    private String zipcode;
    private String streetname;
    private String number;

    setCity(String c, String z) {
        city = c;
        zipcode = z;
    }

    setStreet(String s, String n) {
        streetname = s
        number = n
    }

}

# Aufgabe 4

public class Person {
    public Wallet wallet = new Wallet();
}

public class Wallet{
    private int balance = 0;
    public addMoney(int money) {
        balance += money;
    

    public int getBalance() {
        return balance;
    }
}