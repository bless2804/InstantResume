
## Overview
---
This project started as a small experiment within a larger project to make resume writing easier for students.  
The app generates three short, professional bullet points for each job title without needing hardcoded descriptions.  
You can now try it online here: [https://instant-resume.onrender.com/](https://instant-resume.onrender.com/)

It can also run locally, but an OpenAI API key is required for resume generation.

## Features

- Clean and minimal web interface
- Generates 3 resume bullets per job title
- Save and load resumes by email 
- One-click PDF download 
- OpenAI integration for smarter text generation

---

## Setup

### Requirements
- Java 17 or newer
- Maven installed 
- **Recommended:** IntelliJ IDEA (includes Maven support) 



### Run the project

#### Option 1 — Using IntelliJ IDEA 
1. Open **IntelliJ IDEA**.
2. Click **“Open Project”** and select the folder containing this project (`InstantResume`).
3. Wait a few seconds for IntelliJ to automatically download all dependencies.
4. Open the file: src/main/java/com/example/InstantResume_WORKING/ResumeBuilderApplication.java
5. Click the green **Run** button at the top, or press **Shift + F10**.
6. Once the app starts successfully, open your browser and go to:

#### Option 2 — Using the Command Line
If you prefer running the app directly with Maven:

```bash
# Clone the project
git clone https://github.com/<your-username>/InstantResume.git
cd InstantResume

# Build the project
mvn clean package

# Run it
mvn spring-boot:run
```

## OpenAI API Setup 

### Step 1: Get an API Key
1. Go to [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys)  
2. Log in or create a free OpenAI account.  
3. Click **“Create new secret key”** and copy it.

---

###  Step 2: Create a `.env` File (This step is already done for you!)
Inside the **root folder** of your project (same level as `pom.xml`), create a new file named: .env

Add this line inside it:

OPENAI_API_KEY=sk-your-openai-key-here

Now you can run the project.  
Once running, visit:  [http://localhost:8080/](http://localhost:8080/)

---
## After Setup

When the app is running:

Fill out your info on the left panel (email, name, program, university, work experience).

Click Generate → preview appears on the right.

Use Save, Load, or Download PDF to see your resume.

