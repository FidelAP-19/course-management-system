# Mentorship Guidelines

## My Learning Goals
- Transform school project into portfolio-quality application
- Learn modern Java development (Spring Boot, testing, design patterns)
- Build full-stack application (React + REST API)
- Develop professional software engineering habits

## Mentor's Role (When Using Claude)
- Guide through questions, not answers
- Ask me to explain my reasoning
- Point to resources for self-learning
- Review with specific, constructive feedback
- Challenge assumptions
- Help me understand WHY, not just HOW

## My Commitment
- Update LEARNING_LOG.md after each significant session
- Make atomic git commits with descriptive messages
- Don't move forward without understanding
- Ask "why" when I don't understand
- Reflect on trade-offs before making decisions
- Admit when I'm confused rather than pretending to understand

## Current Phase: Week 1 - Foundation & Code Quality
**Focus:** Refactoring existing code to use layered architecture (Repository → Service → Controller)

**Why this matters:**
- Makes code reusable (same Service/Repository work for console OR web)
- Prepares for Spring Boot (which uses these exact same layers)
- Teaches separation of concerns (fundamental to all professional development)

## Current Progress

✅ **Completed:**
- FacultyRepository.java (CRUD operations for Faculty collection)
- CourseRepository.java (CRUD with compound key search)
- FacultyService.java (Business logic: addCoursesToFaculty method)
- FacultyMenuController.java (Partial - helper methods complete)
    - Constructor with dependency injection
    - displayFacultyList() - Shows all faculty
    - selectFacultyById() - Gets and validates faculty ID from user
    - displayCourseList() - Shows all courses
    - selectCourse() - Gets and validates course selection from user

📄 **In Progress:**
- Writing addCoursesToFaculty() main method in Controller
- This will complete the first vertical slice!

📋 **Next Up:**
- Test complete vertical slice in Driver.java
- Refactor Student operations using same pattern
- Extract common patterns (maybe a base Controller class?)
- Start thinking about unit testing

## Key Concepts I've Learned

### The Three Layers:
1. **Repository (Data Access)** - "Dumb" layer
    - Stores/retrieves data only
    - No business logic
    - Returns null when not found
    - Returns defensive copies of collections
    - Example: `facultyRepository.findById(5)`

2. **Service (Business Logic)** - "Smart" layer
    - Enforces business rules
    - Validates data
    - Coordinates between repositories
    - Throws exceptions for invalid operations
    - Example: `facultyService.addCoursesToFaculty(5, courseList)`

3. **Controller (UI/API)** - "Interface" layer
    - Handles user interaction
    - Delegates to Service
    - Displays results/errors
    - Validates INPUT FORMAT (not business rules)
    - Console version becomes REST API later
    - Example: Menu that calls Service methods

### Design Principles I'm Learning:

- **Separation of Concerns:** Each class has ONE job
- **Dependency Injection:** Pass dependencies through constructor, store as fields
- **Constructor Injection Pattern:** Don't create dependencies internally; receive them
- **Defensive Copying:** Return copies of collections to protect internal state
- **Fail Fast:** Validate early and throw meaningful exceptions
- **Don't Repeat Yourself (DRY):** Extract repeated code into helper methods
- **Single Responsibility:** Each method does one thing well
- **Return Data, Not Void:** Helper methods should return useful data for reuse
- **Method Chaining:** Calling methods on the results of other methods

### Controller Design Patterns:

- **Helper methods** encapsulate single responsibilities
- **Main methods** orchestrate by calling helpers
- **Input validation** (format) happens in Controller
- **Business validation** (meaning) happens in Service
- **Exception handling:** Service throws → Controller catches → Display to user
- **Loop until valid:** Keep asking user until they provide valid input

### Validation Boundaries:

**Controller validates:**
- Is input the right type? (number vs string)
- Is index within array bounds?
- Is string empty when it shouldn't be?

**Controller does NOT validate:**
- Does this ID exist in the database?
- Is this operation allowed by business rules?
- Would this create a duplicate?

## Current Questions/Blockers

**Architectural Questions:**
- When should I create interfaces vs concrete classes?
- Should I have one big Controller or multiple smaller Controllers?
- How much should I refactor before moving to Spring Boot?

**Testing Questions:**
- How do I test Controller methods that use Scanner?
- How do I test Service when it depends on Repositories?
- Do I need to learn mocking frameworks?

**Design Questions:**
- Should displayFacultyList() check for empty list, or should caller check?
- Should I create custom exception classes or keep using IllegalArgumentException?
- How do I handle "cancel" functionality in menu operations?

## What "Portfolio-Worthy" Means (Refined Understanding)

- Clean separation of concerns (not tangled code)
- Code that can be reused in different contexts (console → web)
- Demonstrates understanding of architecture, not just syntax
- Can explain design decisions and trade-offs
- Professional structure that scales
- Proper dependency management (constructor injection)
- Helper methods that are composable and reusable
- Clear responsibility boundaries between layers
- Comprehensive input validation and error handling
- User-friendly error messages

## Git Workflow
```bash
# After completing a logical unit of work:
git add [specific files]
git commit -m "feat: descriptive message about WHAT and WHY"

# Examples of good commit messages:
# "feat: add FacultyRepository with CRUD operations"
# "refactor: extract validation logic to FacultyService"
# "docs: update learning log with constructor injection insights"
# "feat: add FacultyMenuController helper methods with full validation"
```

## Session Transition Protocol

When starting a new chat:
1. Mention this is a continuation of the mentorship project
2. Reference LEARNING_LOG.md for context on what's been completed
3. State what I'm working on next
4. Ask specific questions about blockers
5. Remind Claude of my learning preferences (Socratic method, analogies, challenge assumptions)