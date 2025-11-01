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

🔄 **In Progress:**
- Refactoring Driver.java to use the new layers
- Creating FacultyMenuController to separate UI from business logic

📋 **Next Up:**
- Complete one vertical slice (Repository → Service → Controller)
- Test end-to-end with existing Driver
- Apply same pattern to Student operations
- Extract common patterns into reusable code

## Key Concepts I've Learned
### The Three Layers:
1. **Repository (Data Access)** - "Dumb" layer
    - Stores/retrieves data only
    - No business logic
    - Returns null when not found
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
    - Console version becomes REST API later
    - Example: Menu that calls Service methods

### Design Principles I'm Learning:
- **Separation of Concerns:** Each class has ONE job
- **Dependency Injection:** Pass dependencies through constructor
- **Defensive Copying:** Return copies of collections to protect internal state
- **Fail Fast:** Validate early and throw meaningful exceptions
- **Don't Repeat Yourself (DRY):** Extract repeated code into methods

## Current Questions/Blockers
- When to create interfaces vs concrete classes?
- How to test Service layer that depends on Repositories?
- Should I create all Services first, or one complete vertical slice at a time?
- How much refactoring before moving to Spring Boot?

## What "Portfolio-Worthy" Means (Learned This Session)
- Clean separation of concerns (not tangled code)
- Code that can be reused in different contexts (console → web)
- Demonstrates understanding of architecture, not just syntax
- Can explain design decisions and trade-offs
- Professional structure that scales

## Git Workflow
```bash
# After completing a logical unit of work:
git add [specific files]
git commit -m "feat: descriptive message about WHAT and WHY"

# Examples of good commit messages:
# "feat: add FacultyRepository with CRUD operations"
# "refactor: extract validation logic to FacultyService"
# "docs: update learning log with layered architecture insights"
```

## Session Transition Protocol
When starting a new chat:
1. Mention this is a continuation of the mentorship project
2. Reference LEARNING_LOG.md for context on what's been completed
3. State what I'm working on next
4. Ask specific questions about blockers