
# IT Helpdesk Ticket System (Java)

A Java command-line application for managing IT support tickets.

## Features

- Create support tickets
- Assign ticket priorities (Low, Medium, High)
- Automatically generate unique ticket IDs
- View all tickets
- Search tickets by ID
- Update ticket status (Open, In Progress, Closed)
- Delete tickets
- Validate user input
- Automatically save and load tickets using CSV

## Technologies

- Java 17+
- Object-Oriented Programming (OOP)
- ArrayList
- Java File I/O
- CSV
- IntelliJ IDEA
- Git and GitHub

## How to Run

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Open `src/Main.java`.
4. Run the `main()` method.
5. Use the console menu to manage tickets.

## Example

```text
=== IT Helpdesk Ticket System ===
1. Create ticket
2. List tickets
3. Find ticket
4. Update ticket status
5. Delete ticket
0. Exit

Choose: 1
Problem description: Internet not working
Priority (Low/Medium/High): High
Tickets saved successfully.
Ticket created: Ticket #1 | Internet not working | Priority: High | Status: Open
```

## Data Storage

Tickets are saved automatically in `tickets.csv` and loaded when the application starts.

The CSV file is excluded from Git using `.gitignore`.

## Future Improvements

- Automated tests with JUnit
- Search tickets by priority or status
- Assign tickets to support technicians
- Add creation timestamps

## Learning Objectives

This project demonstrates Java programming, object-oriented design, CRUD operations, collections, input validation, and file persistence.
