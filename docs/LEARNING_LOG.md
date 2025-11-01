# Learning Log

## Week 1 Day 1: Foundation & Layered Architecture [October 28 - November 3, 2025]

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

# Learning Log

## Week 1 Day 2: Foundation & Layered Architecture [October 28 - November 3, 2025]

### What I'm Working On
- **COMPLETED:** Understanding layered architecture (Repository → Service → Controller)
- **COMPLETED:** Crea ted Repository classes (FacultyRepository, CourseRepository)
- **COMPLETED:** Created Service class (FacultyService with addCoursesToFaculty method)
- **COMPLETED:** Created FacultyMenuController with constructor injection and helper methods
- **IN PROGRESS:** Writing the main addCoursesToFaculty() method in Controller
- **NEXT:** Test complete vertical slice in Driver, then refactor more operations

### What I Learned

#### Big Conceptual Breakthroughs:

1. **Constructor Injection Pattern** - Instead of creating dependencies inside a class or passing them to every method, you pass them ONCE through the constructor and store them as fields. This makes code cleaner and more testable.
```java
   // ✅ Good: Constructor injection
   public FacultyMenuController(Scanner scanner, FacultyService service, ...) {
       this.scanner = scanner;
       this.service = service;
   }
   
   // ❌ Bad: Creating dependencies internally
   public FacultyMenuController() {
       this.scanner = new Scanner(System.in);  // Hard to test!
   }
```

2. **Helper Methods That Return Data** - Instead of methods that just print and return void, create methods that DO something and RETURN the result. The caller decides what to do with it.
    - `selectFacultyById()` returns an `int` (the ID)
    - `selectCourse()` returns a `Course` object
    - This makes methods **reusable** and **composable**

3. **Input Validation vs Business Validation** - Critical distinction!
    - **Controller validates FORMAT**: "Did user type a number? Is the index in range?"
    - **Service validates MEANING**: "Does this faculty exist? Can they teach this course?"
    - Controller checks the container; Service checks the content

4. **Exception Flow (Service → Controller):**
    - Service throws exception when business rule violated
    - **Control immediately jumps** to catch block (like a fire alarm!)
    - Controller catches and displays user-friendly message
    - Service doesn't "return" exceptions - it THROWS and stops executing

5. **Method Chaining:**
```java
   courseRepository.findAll().get(index)
   // Step 1: findAll() returns List<Course>
   // Step 2: .get(index) called on that List
   // Result: Course at that index
```
You can call methods on the result of other methods!

#### Technical Skills:

- Implementing constructor injection for dependency management
- Writing helper methods that encapsulate single responsibilities
- Proper exception handling with try-catch blocks
- Index-based vs ID-based selection (understanding the trade-offs)
- Using `while(true)` loops with validation until user enters valid input
- Clearing Scanner buffer after InputMismatchException
- Method signatures: what to pass as parameters vs what to return

### Challenges I Hit

1. **Almost created new Scanner in helper method** - Didn't realize I should use `this.scanner` from constructor
    - **Solution:** Constructor injection means dependencies are stored as fields - use them!

2. **Initialized fields AND used constructor injection** - Had `private FacultyRepository repo = new FacultyRepository();` AND constructor parameter
    - **Learning:** With constructor injection, ONLY declare fields, don't initialize them. Let constructor assign them.

3. **Index variable reset in loop** - Put `int index = 0` INSIDE the for loop, so it reset to 0 every iteration
    - **Solution:** Declare loop counters OUTSIDE the loop they're counting

4. **Redundant validation** - Was checking both index range AND if course exists, but if you get it from the list it's guaranteed to exist
    - **Learning:** Validate the index BEFORE calling `.get()` to prevent IndexOutOfBoundsException

5. **Confused about what to return from helpers** - Initially thought helpers should just print
    - **Insight:** Helpers should RETURN useful data. Let the caller decide whether to print, store, or process it.

### Questions for My Mentor (Claude)

- ✅ ~~What should be my first focus area in Week 1?~~ ANSWERED: Layered architecture before frameworks
- ✅ ~~How do I identify what makes code "portfolio-worthy"?~~ ANSWERED: Clean separation of concerns, testable design
- ✅ ~~Should I create Controller for one operation or all operations?~~ ANSWERED: One vertical slice first
- ✅ ~~How does constructor injection work?~~ ANSWERED: Pass dependencies once, store as fields
- ✅ ~~What's method chaining?~~ ANSWERED: Calling methods on results of other methods
- Should I create interfaces for my Repositories/Services now, or wait until I have multiple implementations?
- How do I test Service layer when it depends on Repositories? (Do I need to learn mocking?)
- When I switch to Spring Boot, will my Controller methods look the same or completely different?

### Decisions I Made and Why

1. **Constructor injection over parameter passing**
    - Why: Don't want to pass 4 parameters to every method
    - Trade-off: Slightly more setup code, but much cleaner method signatures
    - This is the professional standard

2. **Helper methods that return data vs void**
    - Why: Makes methods composable and reusable
    - Example: `selectFacultyById()` returns ID so main method can use it
    - Alternative considered: Helper prints and returns void (less flexible)

3. **Index-based course selection vs dept+number input**
    - Why: Easier for user (see options), simpler validation
    - Trade-off: Less flexible than allowing user to type dept+number
    - Matches how faculty selection works (consistency)

4. **Loop until valid input vs return -1 on error**
    - Why: Matches current Driver behavior, better UX
    - Trade-off: Can't easily "cancel" the operation
    - Plan: Add ability to cancel later if needed

5. **Show IDs directly instead of array indices**
    - Why: IDs are what Service expects, easier to maintain
    - Will work better when we switch to web UI (REST APIs use IDs)

### Code Created This Session
```
✅ FacultyMenuController.java (partial)
   - Constructor with dependency injection (Scanner, FacultyService, FacultyRepository, CourseRepository)
   - displayFacultyList() - Shows all faculty with IDs, names, departments, courses
   - selectFacultyById() - Gets ID from user with validation, returns int
   - displayCourseList() - Shows all courses with indices
   - selectCourse() - Gets course selection from user with validation, returns Course object
```

### Next Session Goals

1. Write `addCoursesToFaculty()` main method using all the helpers
2. Wire up the Controller in Driver.java
3. Test the complete flow end-to-end: User input → Controller → Service → Repository → Success/Error
4. Debug any issues that arise
5. Commit working code with comprehensive commit message
6. Refactor one more operation (maybe addCoursesToStudent) to reinforce the pattern

### Reflection: What Makes Me Proud

- I understand constructor injection and can explain WHY it's better than alternatives
- I can distinguish between input validation (Controller) and business validation (Service)
- My helper methods are clean, single-purpose, and reusable
- I asked good questions about method chaining and understood the explanation
- I caught that we should transition before hitting context limits (good awareness!)
- My code has proper exception handling and user-friendly error messages

### Reflection: What I'm Still Unsure About

- When do I need interfaces vs concrete classes? (Right now everything is concrete)
- How will testing work? Can I test Controller without actually typing input?
- When I have 10 different menu operations, will I have 10 methods in one Controller? Or separate Controllers?
- The Service throws IllegalArgumentException - should I create custom exceptions instead?
- Should displayFacultyList() check for empty list, or should the caller check?