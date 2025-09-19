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

## 🧠 Notes

* Commands are case-insensitive.
* Invalid or incomplete commands will return a snarky error message.
* All tasks are saved automatically to the file ./tasks.bongo when you say `bye`.

---