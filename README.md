<div align="center">

# 🏋️ Group Exercise Booking System
### Furzefield Leisure Centre

A Java-based console application that enables members of a leisure centre to browse exercise classes, manage bookings, record attendance, submit reviews, and generate management reports.

Developed as part of the **7COM1025 – Programming for Software Engineers** module at the **University of Hertfordshire**.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![JUnit](https://img.shields.io/badge/JUnit-5-green?style=for-the-badge)
![OOP](https://img.shields.io/badge/OOP-Java-blue?style=for-the-badge)
![Git](https://img.shields.io/badge/Git-Version_Control-red?style=for-the-badge&logo=git)
![GitHub](https://img.shields.io/badge/GitHub-Repository-black?style=for-the-badge&logo=github)

</div>

---

# 📖 Overview

The **Group Exercise Booking System** is a Java console application that simulates the booking operations of a leisure centre.

The application allows members to:

- Browse available exercise classes
- Book lessons
- Change existing bookings
- Cancel bookings
- Record attendance
- Leave reviews and ratings
- View personal bookings

The system also provides reporting features for administrators, including:

- Monthly lesson reports
- Champion Exercise reports
- Average lesson ratings
- Attendance statistics

The project demonstrates the application of **Object-Oriented Programming principles**, **software design**, **collections**, **enumerations**, and **unit testing**.

---

# ✨ Features

## 👥 Member Management

- View personal bookings
- Manage booking history

---

## 📅 Lesson Timetable

Browse lessons by:

- Day
- Exercise Type

Each lesson displays:

- Lesson ID
- Exercise Type
- Day
- Month
- Session Time
- Price
- Remaining Spaces

---

## 📝 Booking Management

Members can:

- Book exercise lessons
- Change existing bookings
- Cancel bookings

The system automatically updates available spaces.

---

## ✅ Attendance Recording

Members who attend a lesson can be marked as attended.

Attendance data contributes to monthly reports and statistics.

---

## ⭐ Reviews & Ratings

Members can submit:

- Rating (1–5)
- Written review

Each booking can only be reviewed once.

Average lesson ratings are automatically calculated.

---

## 📊 Reports

The application generates:

- Monthly Lesson Report
- Champion Exercise Report

Reports include:

- Attendance totals
- Average ratings
- Exercise popularity

---

# 🖥️ Application Screenshots

## Main Menu

![Main Menu](images/main-menu.png)

---

## Browse Timetable

![Timetable](images/timetable.png)

---

## Book a Lesson

![Booking](images/book-lesson.png)

---

## Change Booking

![Change Booking](images/change-booking.png)

---

## Cancel Booking

![Cancel Booking](images/cancel-booking.png)

---

## Record Attendance

![Attendance](images/attendance.png)

---

## Submit Review

![Review](images/review.png)

---

## Monthly Lesson Report

![Monthly Report](images/monthly-report.png)

---

# 🏗️ System Architecture

```
                 Main
                  │
                  ▼
          BookingSystem
          │     │      │
          │     │      │
          ▼     ▼      ▼
      Member  Lesson  Booking
                       │
                       ▼
                    Review

          ▲
          │
 ReportGenerator

Enums

ExerciseType
BookingDay
SessionTime
BookingStatus
```

---

# 📚 UML Class Diagram

> *(Insert generated UML diagram here)*

```
BookingSystem
│
├── Member
├── Lesson
├── Booking
│      └── Review
│
└── ReportGenerator

Enums:
ExerciseType
BookingDay
SessionTime
BookingStatus
```

---

# 📂 Project Structure

```
Group-Exercise-Booking-System-for-Furzefield-Leisure-Centre
│
├── src
│   ├── Booking.java
│   ├── BookingDay.java
│   ├── BookingStatus.java
│   ├── BookingSystem.java
│   ├── DataSeeder.java
│   ├── ExerciseType.java
│   ├── Lesson.java
│   ├── Main.java
│   ├── Member.java
│   ├── ReportGenerator.java
│   ├── Review.java
│   └── SessionTime.java
│
├── test
│   └── BookingSystemTest.java
│
├── images
│
├── BookingSystem.jar
├── run.bat
└── README.md
```

---

# ⚙️ Technologies Used

| Technology | Purpose |
|------------|----------|
| Java | Core application |
| Object-Oriented Programming | Software design |
| Java Collections | Data management |
| JUnit | Unit testing |
| Git | Version control |
| GitHub | Repository hosting |
| NetBeans | Development IDE |

---

# 🚀 Getting Started

## Clone the repository

```bash
git clone https://github.com/MarshalFrank/Group-Exercise-Booking-System-for-Furzefield-Leisure-Centre.git
```

---

## Open in NetBeans

Open the project folder inside NetBeans.

---

## Run the application

Run:

```
Main.java
```

or

Double-click

```
run.bat
```

or execute

```
java -jar BookingSystem.jar
```

---

# 🧪 Testing

The project includes JUnit tests covering:

- Booking creation
- Booking changes
- Booking cancellation
- Attendance recording
- Review submission
- Report generation

---

# 💡 Object-Oriented Principles Demonstrated

✔ Encapsulation

✔ Composition

✔ Enumerations

✔ Separation of Concerns

✔ Single Responsibility Principle

✔ Collection Management

✔ Data Seeding

✔ Modular Design

---

# 📈 Future Improvements

Potential enhancements include:

- JavaFX graphical user interface
- Database integration (MySQL/PostgreSQL)
- User authentication
- Online member accounts
- Email notifications
- REST API
- Persistent storage
- Admin dashboard
- Search and filtering
- Instructor management

---

# 🎯 Learning Outcomes

This project demonstrates practical experience with:

- Java Programming
- Object-Oriented Design
- Software Engineering Principles
- Data Modelling
- Unit Testing
- Git Version Control
- Console Application Development
- Report Generation
- UML Design

---

# 👨‍💻 Author

**Franklin Dike**

MSc Software Engineering

University of Hertfordshire

GitHub:

https://github.com/MarshalFrank

---

# 📄 License

This project was developed for educational purposes as part of coursework for the University of Hertfordshire.

---

<div align="center">

### ⭐ If you found this project interesting, consider giving it a star!

</div>