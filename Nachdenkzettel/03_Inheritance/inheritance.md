# Nachdenkzettel Inheritance

# Aufgabe 1

No, it is not necessary.

# Aufgabe 2

The object x of class X can’t access the `newMethodInB()` method. That’s because there’s no such method in class X (also no abstract method). The method only exists in B.

# Aufgabe 3

The method in class X gets overwritten by the method in class B.

# Aufgabe 4

It makes more sense the other way around: The rectangle should extend the square because the square only needs one value, while the rectangle needs 2 values.

# Aufgabe 5

Look at task 4.

# Aufgabe 6

It’s called “Liskovsches Substitutionsprinzip”. When the class “Filename” extends the class “String”, Filename needs to have at least as much functionality as the parent class.
