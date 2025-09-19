# Bongo User Guide

---

## ✨ Features

* Add tasks (`todo`, `deadline`, `event`)
* Mark and unmark tasks as done
* List all current tasks
* Delete tasks
* Find tasks by keyword
* Exit the application

---

## 📜 Command Overview

Each command starts with a keyword followed by the task details (if needed).

### 1. `todo`

Adds a basic task with a description.

```
todo <task description>
```

**Example:**

```
todo Feed the cat
```

---

### 2. `deadline`

Adds a task that must be done by a certain time.

```
deadline <task description> /by <due date>
```

**Example:**

```
deadline Submit assignment /by Sunday
```

---

### 3. `event`

Adds an event that occurs within a specific time range.

```
event <event description> /from <start time> /to <end time>
```

**Example:**

```
event Team meeting /from 2pm /to 4pm
```

---

### 4. `list`

Displays all current tasks.

```
list
```

---

### 5. `mark`

Marks a task as done.

```
mark <task number>
```

**Example:**

```
mark 2
```

---

### 6. `unmark`

Unmarks a task (marks it as not done).

```
unmark <task number>
```

**Example:**

```
unmark 2
```

---

### 7. `delete`

Deletes a task.

```
delete <task number>
```

**Example:**

```
delete 3
```

---

### 8. `find`

Searches for tasks containing a keyword.

```
find <keyword>
```

**Example:**

```
find meeting
```

---

### 9. `bye`

Exits the application and saves your task list.

```
bye
```

---

📅 Supported Date & Time Formats

When using commands like deadline and event, Bongo can understand a variety of date and time formats. Below are the accepted inputs:
---

## 📅 Supported Date & Time Formats

When using commands like `deadline` and `event`, Bongo can understand a variety of **date** and **time** formats. Below are the accepted inputs:

### ✅ Date Formats

You can specify dates in many flexible ways:

| Input Example           | Description            | Interpreted As                   |
|-------------------------|------------------------| -------------------------------- |
| `Mon`, `Monday`         | Day of week            | Next or same Monday              |
| `Sep`, `September`      | Month name             | 1st of that month (next or same) |
| `12 Sep`, `12 September`| Day Month name         | Current year                     |
| `12/9`                  | Day/Month              | Current year                     |
| `12/9/25`               | Day/Month/2-digit year | Interpreted as 12 Sep 2025       |
| `12/9/2025`             | Day/Month/4-digit year | 12 Sep 2025                      |
| `2025`                  | Year only              | 1 Jan 2025                       |

> 🔎 Tip: If only the day of week or month is given, Bongo assumes the next upcoming occurrence.

---

### ⏰ Time Formats

Times can be entered in either **12-hour** or **24-hour** style:

| Input Example       | Description                    | Interpreted As |
| ------------------- | ------------------------------ | -------------- |
| `1230am`            | 12-hour, compact               | 00:30          |
| `12am`, `12:30am`   | 12-hour with/without minutes   | 00:00 or 00:30 |
| `0030`, `0`, `0:30` | 24-hour format                 | 00:30          |
| `14:00`, `2pm`      | Afternoon in 24h or 12h format | 14:00          |

> ⏱ If no time is provided, Bongo assumes **00:00 (midnight)** by default.

---

### ❗ Invalid Inputs

If the date or time you gave is invalid, for example 32/1, bongo will let you know.

---

## 🧠 Notes

* Commands are case-insensitive.
* Invalid or incomplete commands will return a snarky error message.
* All tasks are saved automatically to the file ./tasks.bongo when you say `bye`.

---