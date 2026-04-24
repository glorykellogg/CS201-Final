This is a Java CLI application that allows users to store and manage books. 
It supports printed books and audiobooks, and includes functionality for adding books, searching/sorting, viewing statistics, displaying recently added books, and saving/loading data.

How to Run:
Requires Java JDK 8+
Compile: javac *.java
Run: java BookApp


Design Explanation
	•	BookApp: handles user interaction (CLI)
	•	Catalog: manages book collection and operations
	•	Book: abstract base class
	•	PrintedBook, AudioBook: subclasses
	•	BookInterface: defines shared behaviors
	•	BookType: enum for book categories

Video Presentation:
https://youtu.be/SmtDYNtU_NI
