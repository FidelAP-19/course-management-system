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

✅ **Completed - First Vertical Slice:**
- FacultyRepository.java (CRUD operations for Faculty collection)
- CourseRepository.java (CRUD with compound key search)
- FacultyService.addCoursesToFaculty() (Business logic with information flow pattern)
    * Returns List<Course> of actually added courses
    * Silently skips duplicates, lets Controller handle feedback
- FacultyMenuController.java - COMPLETE!
    - Constructor with dependency injection
    - displayFacultyList() - Shows all faculty
    - displayFacultyDetails() - Shows single faculty member
    - selectFacultyById() - Gets and validates faculty ID from user
    - displayCourseList() - Shows all courses
    - selectCourse() - Gets and validates course selection from user
    - addCoursesToFaculty() - Complete orchestration method
        * Pre-flight validation (empty repos)
        * Context display (current courses before selection)
        * Collection of user input via helpers
        * Service call with exception handling
        * Detailed feedback based on results (all added / some added / none added)
        * Updated faculty display on success
- Tested end-to-end with all scenarios (success, partial, duplicates, errors)
- Committed with comprehensive message

📋 **Next Up:**
- Refactor Student operations (addCoursesToStudent) using same pattern
- Identify common patterns that could be extracted
- Consider base Controller class or helper utilities
- Continue refactoring more operations to solidify understanding

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
    - **NEW:** Can return information about results (information flow pattern)
    - Example: `List<Course> added = facultyService.addCoursesToFaculty(5, courseList)`

3. **Controller (UI/API)** - "Interface" layer
    - Handles user interaction
    - Delegates to Service
    - Displays results/errors
    - Validates INPUT FORMAT (not business rules)
    - **Makes display decisions** based on Service results
    - Console version becomes REST API later
    - Example: Menu that calls Service methods and interprets results

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
- **Information Flow Pattern:** Service returns data about what happened; Controller decides how to display it

### Controller Design Patterns:

- **Helper methods** encapsulate single responsibilities
- **Main methods** orchestrate by calling helpers
- **Input validation** (format) happens in Controller
- **Business validation** (meaning) happens in Service
- **Exception handling:** Service throws → Controller catches → Display to user
- **Loop until valid:** Keep asking user until they provide valid input
- **Pre-flight checks:** Validate prerequisites (non-empty data) before starting operation
- **Context display:** Show relevant information before asking for input
- **Result-based feedback:** Use Service return values to provide detailed messages
- **Conditional display:** Only show updates when changes were actually made

### Validation Boundaries:

**Controller validates:**
- Is input the right type? (number vs string)
- Is index within array bounds?
- Is string empty when it shouldn't be?
- Are prerequisites met? (data exists before starting operation)

**Controller does NOT validate:**
- Does this ID exist in the database? (Service checks)
- Is this operation allowed by business rules? (Service checks)
- Would this create a duplicate? (Service checks, but Controller displays result)

### Service Return Patterns:

**When to return void:**
- Simple create/update/delete with no ambiguity
- Operation either succeeds completely or throws exception

**When to return data:**
- Operation might partially succeed (e.g., some items added, some skipped)
- Controller needs details to provide informative feedback
- Result summary needed (e.g., "3 added, 2 duplicates skipped")

## Current Questions/Blockers

**Architectural Questions:**
- When should I create interfaces vs concrete classes?
- Should I have one big Controller or multiple smaller Controllers?
- How much should I refactor before moving to Spring Boot?
- When should Service return void vs return data?
- As I refactor more operations, what patterns will emerge?

**Testing Questions:**
- How do I test Controller methods that use Scanner?
- How do I test Service when it depends on Repositories?
- Do I need to learn mocking frameworks?

**Design Questions:**
- Should I create custom exception classes or keep using IllegalArgumentException?
- How do I handle "cancel" functionality in menu operations?
- Will Faculty and Student Controllers have enough in common to warrant a base class?

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
- **NEW:** Information flow that respects separation of concerns
- **NEW:** Service provides information, Controller makes display decisions
- **NEW:** Thoughtful UX with context, feedback, and confirmation

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
# "feat: implement layered architecture for adding courses to faculty"
```

## Session Transition Protocol

When starting a new chat:
1. Mention this is a continuation of the mentorship project
2. Reference LEARNING_LOG.md for context on what's been completed
3. State what I'm working on next
4. Ask specific questions about blockers
5. Remind Claude of my learning preferences (Socratic method, analogies, challenge assumptions)

## Recent Accomplishments (for context when transitioning)

**Latest Milestone:** Completed first vertical slice - addCoursesToFaculty
- Full three-layer architecture working end-to-end
- Information flow pattern implemented (Service returns List<Course>)
- Detailed user feedback for all scenarios
- Tested with success, partial success, and failure cases
- Code committed with comprehensive message

**Key Insights from This Milestone:**
- Vertical slice means ALL THREE layers working together
- Service can return information for Controller to interpret
- Duplicates are not errors - they're expected behavior to handle gracefully
- Pre-flight validation improves UX by failing early with clear messages
- Context display (showing current state) helps users make informed decisions

**Ready for Next:** Apply same pattern to Student operations to reinforce learning