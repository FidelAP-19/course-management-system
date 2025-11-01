# Learning Log

## Week 1: Foundation & Layered Architecture [October 28 - November 3, 2025]

### What I'm Working On
- **COMPLETED:** Understanding layered architecture (Repository → Service → Controller)
- **COMPLETED:** Created first Repository classes (FacultyRepository, CourseRepository)
- **COMPLETED:** Created first Service class (FacultyService with addCoursesToFaculty method)
- **IN PROGRESS:** Refactoring Driver.java to use the new layered structure
- **NEXT:** Create FacultyMenuController to separate UI concerns from business logic

### What I Learned

#### Big Conceptual Breakthroughs:
1. **Separation of Concerns** - Why mixing UI, business logic, and data access in one class makes code impossible to reuse in different contexts (console vs web)

2. **The Three Layers:**
    - **Repository (Data Access):** "Dumb" layer - only stores/retrieves data, no business logic. Like a librarian who just finds/stores books.
    - **Service (Business Logic):** "Smart" layer - enforces rules, validates, coordinates between repositories. Like a research assistant who analyzes and validates.
    - **Controller (UI/API):** Handles user interaction (console menus OR HTTP requests), delegates everything to Service.

3. **Why This Matters for Spring Boot:**
    - Service and Repository layers are **identical** whether you have console UI or web UI
    - Only the Controller changes when switching from console to REST API
    - This is why refactoring BEFORE Spring Boot is critical!

4. **Key Design Principles:**
    - Repository returns `null` when not found; Service decides if that's an error
    - Service throws exceptions; Controller decides how to display them
    - Always return defensive copies of collections (`new ArrayList<>(list)`) to protect internal data
    - Use identifiers (IDs, unique keys) to reference existing objects, not full object graphs

#### Technical Skills:
- Implementing CRUD operations (Create, Read, Update, Delete) in Repository layer
- Constructor injection for dependencies (Repository needs lists, Service needs Repositories)
- Difference between `findById()` and `findAll()` patterns
- Why Course needs compound key (dept + number) since it has no single ID field

### Challenges I Hit

1. **Thinking in layers was hard at first** - Kept wanting to put validation in Repository or data access in Controller
    - **Solution:** Used the "librarian vs research assistant" analogy to remember who does what

2. **Confused about where duplicate checking belongs** - Should Faculty class prevent duplicates? Or Service?
    - **Learning:** Business rules belong in Service; data structure rules belong in domain classes
    - **Decision:** Skipped duplicate check for now; will add as refinement later

3. **Didn't understand why Service needs BOTH FacultyRepository AND CourseRepository**
    - **Insight:** Service orchestrates across multiple data sources! That's its job.

4. **Almost made Repository methods too complex** - Wanted `addCourseToFaculty()` in Repository
    - **Learning:** Repository should ONLY do CRUD on its own collection. Anything involving multiple entities or business rules = Service job.

### Questions for My Mentor (Claude)
- ✅ ~~What should be my first focus area in Week 1?~~ ANSWERED: Layered architecture before frameworks
- ✅ ~~How do I identify what makes code "portfolio-worthy"?~~ ANSWERED: Clean separation of concerns, testable design
- When should I create interfaces for my Repositories/Services? (Is that overkill for now?)
- How do I test Service layer when it depends on Repositories? (Do I need to learn mocking?)
- Should I add a StudentService next, or finish refactoring all Faculty operations first?

### Decisions I Made and Why

1. **Started with Repository layer, not Controller**
    - Why: Foundation first. Can't build Service without Repository, can't build Controller without Service.
    - Alternative considered: Start with Controller to keep UI working
    - Chose foundation approach because it's more systematic

2. **Used `List<Course>` parameter instead of `List<Integer>` in Service**
    - Why: Simpler for console app; course objects already exist in Driver
    - Trade-off: Will need to refactor for REST API (which should pass IDs, not full objects)
    - Okay with this because I'm learning the pattern first, optimization later

3. **Skipped duplicate checking in `addCoursesToFaculty`**
    - Why: Keeps first implementation simple and testable
    - Plan: Add as enhancement after basic flow works end-to-end

4. **Created CourseRepository even though we're focusing on Faculty**
    - Why: FacultyService NEEDS CourseRepository to validate courses exist
    - Learning: Services often need multiple Repositories - this is normal!

### Code Created This Session
```
✅ FacultyRepository.java - Complete CRUD operations for Faculty collection
✅ CourseRepository.java - CRUD with compound key search (dept + number)
✅ FacultyService.java - Business logic for adding courses to faculty
```

### Next Session Goals
1. Create FacultyMenuController to extract menu logic from Driver
2. Test the complete flow: Controller → Service → Repository
3. Refactor at least one more Driver method using the layered pattern
4. Update Driver.java to use repositories instead of direct ArrayList access
5. Commit working code with descriptive message

### Reflection: What Makes Me Proud
- I understood WHY we're refactoring before learning Spring Boot (would have rushed to frameworks before)
- I can explain the three layers and give examples of what belongs where
- My Repository classes are clean - no business logic leaked in
- I asked good questions about design trade-offs (duplicate checking, where to validate)

### Reflection: What I'm Still Unsure About
- When the application grows, will I need FacultyService, CourseService, RegistrationService? How do they interact?
- How much validation is "enough" before moving on?
- Is my Service method too long? Should I extract helper methods?