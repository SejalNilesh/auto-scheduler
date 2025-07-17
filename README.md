# 🧠 Auto Scheduling Engine (DSA-Based Exam Scheduler)

A DSA-powered exam timetable scheduler built in Java.  
This engine automatically generates conflict-free exam schedules based on:
- Student overlap
- Room availability
- Room capacity
- Teacher assignment
- Custom constraints like "max 2 exams per student per slot"

---

## 📌 Features

| Feature                          | Algorithm Used                |
|----------------------------------|-------------------------------|
| Conflict detection               | Graph construction            |
| Exam slot assignment             | Backtracking + Constraint satisfaction |
| Room assignment                  | Dynamic room allocation inside backtracking |
| Constraint: max 2 exams/slot     | Student slot map + validation |
| Input/Output from CSV            | CSV parsing + exporting       |

---

