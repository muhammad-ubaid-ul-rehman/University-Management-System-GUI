# University Management System - Dependency Installation Guide

## Status Update
✅ Successfully Downloaded: 2/3 Dependencies
⚠️  Partially Failed: 1/3 (requires manual download)

---

## Downloaded Dependencies ✅

The following files have been downloaded to `lib/` folder:

1. **mysql-connector-j-9.0.0.jar** (2.47 MB)
   - MySQL JDBC Driver
   - Used in: Conn.java
   - Status: ✅ Downloaded

2. **jcalendar-1.4.jar** (0.16 MB)
   - JCalendar Library (provides JDateChooser component)
   - Used in: AddStudent.java, updateStudent.java
   - Status: ✅ Downloaded

---

## Missing Dependency ⚠️

3. **rs2xml.jar** (or proteanit-dbutils)
   - Database Result Set to JTable Model Converter
   - Used in: FacultyDetails.java, StudentDetails.java, ExaminationDetails.java, FreeStructure.java, TeacherLeaveDetails.java, StudentLeaveDetails.java
   - Status: ⚠️  Manual download required

### To Download rs2xml.jar Manually:

**Option 1: Direct Download**
1. Download from: https://sourceforge.net/projects/finalangelsanddemons/files/
2. Look for: `rs2xml.jar`
3. Place in: `lib/` folder

**Option 2: Alternative Library (proteanit library)**
1. Download from: https://sourceforge.net/projects/proteanit/files/
2. Look for: `proteanit.jar` or `proteanit-full.jar`
3. Place in: `lib/` folder

**Option 3: Build from Source**
If the above don't work, use the built-in approach (add dependency in project settings)

---

## Compiler & Runtime Instructions

### After All Dependencies are Downloaded:

#### 1. COMPILE the Project:
```powershell
javac -cp "lib/*" -d bin src/university/management/system/*.java
```

#### 2. RUN the Application:
```powershell
java -cp "bin;lib/*" university.management.system.Main
```

---

## Project Structure Required:
```
university management system/
├── lib/
│   ├── mysql-connector-j-9.0.0.jar      ✅
│   ├── jcalendar-1.4.jar                ✅
│   └── rs2xml.jar                       ⚠️  (manual)
├── bin/                                 (created after compile)
├── src/
│   └── university/
│       └── management/
│           └── system/
│               ├── Main.java
│               ├── Login.java
│               ├── Conn.java
│               └── ... (other files)
└── download_dependencies.ps1
```

---

## Dependencies Summary

| Library | Version | JAR File | Status | Size | Purpose |
|---------|---------|----------|--------|------|---------|
| MySQL Connector | 9.0.0 | mysql-connector-j-9.0.0.jar | ✅ | 2.47 MB | Database Driver |
| JCalendar | 1.4 | jcalendar-1.4.jar | ✅ | 0.16 MB | Date Picker |
| rs2xml/Proteanit | Latest | rs2xml.jar | ⚠️  | ~0.5 MB | ResultSet to Table |

---

## Troubleshooting

**Error: Class not found: com.mysql.cj.jdbc.Driver**
→ mysql-connector-j-9.0.0.jar not in classpath

**Error: Class not found: com.toedter.calendar.JDateChooser**
→ jcalendar-1.4.jar not in classpath

**Error: Class not found: net.proteanit.sql.DbUtils**
→ rs2xml.jar or proteanit library not found

---

## Database Requirements

Before running the application, ensure you have:
- **MySQL Server** installed and running on localhost:3306
- **Database**: UniversityManagementSystem
- **User**: root
- **Password**: MYSQL369

(You can modify these in Conn.java if different)

---

Last Updated: 2026-04-07
