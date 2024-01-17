package ac.il.bgu.qa;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ac.il.bgu.qa.services.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.internal.matchers.Null;


public class TestLibrary {

    @Mock
    User mockUser;
    @Mock
    Book mockBook;
    @Mock
    DatabaseService mockDatabaseService;
    @Mock
    ReviewService mockReviewService;

    @Mock
    NotificationService mockNotificationService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);


    }
    /*  When testing an exception being thrown, seems to me it's impossible to separate
     *   Action/Assertion without using try/catch (which feels wrong to do) */

    @Test
    public void givenNullBook_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid book.");
    }
    @Test
    public void givenNullISBN_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn(null);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    @Test
    public void givenTooShortISBN_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("11-12");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    @Test
    public void givenNonDigitISBN_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("11-1A2");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    @Test
    public void givenInvalidISBN_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("11-12");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    @Test
    public void givenEmptyTitle_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getAuthor()).thenReturn("");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid title.");
    }

    @Test
    public void givenNullTitle_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn(null);

        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid title.");

    }
    @Test
    public void givenEmptyAuthorName_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid author.");

    }

    @Test
    public void givenNullAuthorName_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn(null);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid author.");
    }
    @Test
    public void givenFirstNonLetterAuthor_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("-Bob");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid author.");
    }

    @Test
    public void givenLastNonLetterAuthor_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis Carroll-");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid author.");
    }

    @Test
    public void givenDoubleApoAuthor_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis''Carroll");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid author.");
    }

    @Test
    public void givenDoubleHyphenAuthor_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis--Carroll");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid author.");
    }

    @Test
    public void givenIllegalCharInAuthor_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis%Carroll");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid author.");
    }

    @Test
    public void givenTrueIsBorrowed_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis Carroll");
        when(mockBook.isBorrowed()).thenReturn(true);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).isBorrowed();
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Book with invalid borrowed state.");
    }
    @Test
    public void givenBookInDB_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis Carroll");
        when(mockBook.isBorrowed()).thenReturn(false);
        when(mockDatabaseService.getBookByISBN(anyString())).thenReturn(mockBook);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN("978-0-545-01022-1");
        verify(mockBook,times(1)).isBorrowed();
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(2)).getISBN();
        assertEquals(exception.getMessage(), "Book already exists.");
    }
    @Test
    public void givenValidBook_whenAddBook_DoesNotThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn("978-0-545-01022-1");
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis Carroll");
        when(mockBook.isBorrowed()).thenReturn(false);
        when(mockDatabaseService.getBookByISBN(anyString())).thenReturn(null);
        // 3. Action
        assertDoesNotThrow(() -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN("978-0-545-01022-1");
        verify(mockBook,times(1)).isBorrowed();
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(3)).getISBN();
    }

//-------------------------------------------------------------------------------------

    @Test
    public void givenUserIsNull_whenRegisterUser_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.registerUser(null));
        // 4. Assertion
        assertEquals(exception.getMessage(),"Invalid user.");
    }

    @Test
    public void givenUserHasNoId_whenRegisterUser_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockUser.getId()).thenReturn(null);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.registerUser(mockUser));
        // 4. Assertion
        verify(mockUser,times(1)).getId();
        assertEquals(exception.getMessage(), "Invalid user Id.");
    }

    @Test
    public void givenUserHasInvalidId_whenRegisterUser_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockUser.getId()).thenReturn("4654645");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.registerUser(mockUser));
        // 4. Assertion
        verify(mockUser,times(2)).getId();
        assertEquals(exception.getMessage(), "Invalid user Id.");
    }

    @Test
    public void givenUserHasNoName_whenRegisterUser_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockUser.getName()).thenReturn(null);
        when(mockUser.getId()).thenReturn("123456789012");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.registerUser(mockUser));
        // 4. Assertion
        verify(mockUser,times(2)).getId();
        verify(mockUser,times(1)).getName();
        assertEquals(exception.getMessage(), "Invalid user name.");
    }

    @Test
    public void givenUserHasEmptyName_whenRegisterUser_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockUser.getName()).thenReturn("");
        when(mockUser.getId()).thenReturn("123456789012");
        when(mockUser.getNotificationService()).thenReturn(mockNotificationService);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.registerUser(mockUser));
        // 4. Assertion
        verify(mockUser,times(2)).getId();
        verify(mockUser,times(2)).getName();
        assertEquals(exception.getMessage(), "Invalid user name.");
    }

    @Test
    public void givenUserHasNoNotificationService_whenRegisterUser_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockUser.getNotificationService()).thenReturn(null);
        when(mockUser.getId()).thenReturn("123456789012");
        when(mockUser.getName()).thenReturn("someName");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.registerUser(mockUser));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid notification service.");
        verify(mockUser,times(2)).getId();
        verify(mockUser,times(2)).getName();

    }

    @Test
    public void givenUserIsAlreadyInDB_whenRegisterUser_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockUser.getId()).thenReturn("123456789012");
        when(mockDatabaseService.getUserById("123456789012")).thenReturn(mockUser);
        when(mockUser.getNotificationService()).thenReturn(mockNotificationService);
        when(mockUser.getName()).thenReturn("someName");
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.registerUser(mockUser));
        // 4. Assertion
        assertEquals(exception.getMessage(), "User already exists.");
        verify(mockUser,times(3)).getId();
        verify(mockUser,times(2)).getName();

    }

    @Test
    public void givenUserIsValid_whenRegisterUser_thenDontThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockUser.getId()).thenReturn("123456789012");
        when(mockUser.getNotificationService()).thenReturn(mockNotificationService);
        when(mockUser.getName()).thenReturn("someName");
        // 3. Action
        assertDoesNotThrow(()->library.registerUser(mockUser));
        // 4. Assertion
        verify(mockUser,times(4)).getId();
        verify(mockUser,times(2)).getName();

    }
}
