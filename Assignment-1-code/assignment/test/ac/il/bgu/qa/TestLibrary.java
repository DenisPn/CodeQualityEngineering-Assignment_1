package ac.il.bgu.qa;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ac.il.bgu.qa.errors.BookAlreadyBorrowedException;
import ac.il.bgu.qa.errors.BookNotFoundException;
import ac.il.bgu.qa.errors.UserNotRegisteredException;
import ac.il.bgu.qa.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    private final String ISBN= "978-0-545-01022-1";
    private final String userID ="123456789123";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    /* ADD BOOK TESTS */
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
    @ParameterizedTest
    @ValueSource(strings = {"11-12","11-1A2","9832-9485398475"})
    public void givenInvalidISBN_whenAddBook_ThrowException(String invalidISBN) {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn(invalidISBN);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    /*
    // REPLACED WITH PARAMETERIZED TEST ABOVE
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
    // REPLACED WITH PARAMETERIZED TEST ABOVE
    */
    @Test
    public void givenEmptyTitle_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn(ISBN);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
        when(mockBook.getTitle()).thenReturn(null);

        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockBook,times(1)).getTitle();
        verify(mockBook,times(1)).getISBN();
        assertEquals(exception.getMessage(), "Invalid title.");

    }
    @ParameterizedTest
    @ValueSource(strings = {"", "-Bob,", "Lewis Carroll-",
            "Lewis''Carroll", "Lewis%Carroll", "Lewis--Carroll"})
    public void givenInvalidAuthorNames_whenAddBook_ThrowException(String author) {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn(ISBN);
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn(author);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
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
/*
// REPLACED WITH PARAMETERIZED TEST ABOVE
    @Test
    public void givenEmptyAuthorName_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn(ISBN);
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
    public void givenFirstNonLetterAuthor_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn(ISBN);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
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
  //  REPLACED WITH PARAMETERIZED TEST  ABOVE
*/

    @Test
    public void givenTrueIsBorrowed_whenAddBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockBook.getISBN()).thenReturn(ISBN);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis Carroll");
        when(mockBook.isBorrowed()).thenReturn(false);
        when(mockDatabaseService.getBookByISBN(anyString())).thenReturn(mockBook);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
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
        when(mockBook.getISBN()).thenReturn(ISBN);
        when(mockBook.getTitle()).thenReturn("Alice In Wonderland");
        when(mockBook.getAuthor()).thenReturn("Lewis Carroll");
        when(mockBook.isBorrowed()).thenReturn(false);
        when(mockDatabaseService.getBookByISBN(anyString())).thenReturn(null);
        // 3. Action
        assertDoesNotThrow(() -> library.addBook(mockBook));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        verify(mockBook,times(1)).isBorrowed();
        verify(mockBook,times(1)).getAuthor();
        verify(mockBook,times(2)).getTitle();
        verify(mockBook,times(3)).getISBN();
        verify(mockDatabaseService,times(1)).addBook(ISBN,mockBook);
    }
    /* BORROW BOOK TESTS */
    @Test
    public void givenNullISBN_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook(null, userID));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    @ParameterizedTest
    @ValueSource(strings = {"11-12","11-1A2","9832-9485398475"})
    public void givenInvalidISBN_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook("999-1-589-99922-1", userID));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    /*
    // REPLACE IN TEST ABOVE
    @Test
    public void givenTooShortISBN_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook("11-23", userID));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    @Test
    public void givenNonDigitISBN_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook("12-A32", userID));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }

    @Test
    public void givenInvalidISBN_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook("999-1-589-99922-1", userID));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid ISBN.");
    }
    */
    @Test
    public void givenFailedDBFetch_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(null);
        // 3. Action
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> library.borrowBook(ISBN, userID));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        assertEquals(exception.getMessage(), "Book not found!");
    }
    @Test
    public void givenNullUserID_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(mockBook);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook(ISBN,null));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        assertEquals(exception.getMessage(), "Invalid user Id.");
    }
    @Test
    public void givenTooShortUserID_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(mockBook);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook(ISBN,"11"));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        assertEquals(exception.getMessage(), "Invalid user Id.");
    }
    @Test
    public void givenTooLongUserID_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(mockBook);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook(ISBN,"12345678912345654"));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        assertEquals(exception.getMessage(), "Invalid user Id.");
    }
    @Test
    public void givenNonDigitUserID_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(mockBook);
        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> library.borrowBook(ISBN,"I_LOVE_PANCAKES"));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        assertEquals(exception.getMessage(), "Invalid user Id.");
    }
    @Test
    public void givenFailedUserFetch_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(mockBook);
        when(mockDatabaseService.getUserById(userID)).thenReturn(null);
        // 3. Action
        UserNotRegisteredException exception = assertThrows(UserNotRegisteredException.class, () -> library.borrowBook(ISBN,userID));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        verify(mockDatabaseService,times(1)).getUserById(userID);
        assertEquals(exception.getMessage(), "User not found!");
    }
    @Test
    public void givenBookIsBorrowed_whenBorrowBook_ThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(mockBook);
        when(mockDatabaseService.getUserById(userID)).thenReturn(mockUser);
        when(mockBook.isBorrowed()).thenReturn(true);
        // 3. Action
        BookAlreadyBorrowedException exception = assertThrows(BookAlreadyBorrowedException.class, () -> library.borrowBook(ISBN,userID));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        verify(mockDatabaseService,times(1)).getUserById(userID);
        verify(mockBook,times(1)).isBorrowed();
        assertEquals(exception.getMessage(), "Book is already borrowed!");
    }
    @Test
    public void givenValidCall_whenBorrowBook_DoesntThrowException() {
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(ISBN)).thenReturn(mockBook);
        when(mockDatabaseService.getUserById(userID)).thenReturn(mockUser);
        when(mockBook.isBorrowed()).thenReturn(false);
        // 3. Action
        assertDoesNotThrow(() -> library.borrowBook(ISBN,userID));
        // 4. Assertion
        verify(mockDatabaseService,times(1)).getBookByISBN(ISBN);
        verify(mockDatabaseService,times(1)).getUserById(userID);
        verify(mockBook,times(1)).isBorrowed();
        verify(mockBook,times(1)).borrow();
        verify(mockDatabaseService,times(1)).borrowBook(ISBN,userID);
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

    @Test
    public void givenInvalidISBN_whenGetBookByISBN_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.getBookByISBN("1234","123456789012"));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid ISBN.");

    }

    @Test
    public void givenNullUserId_whenGetBookByISBN_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.getBookByISBN(ISBN,null));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid user Id.");

    }

    @Test
    public void givenInvalidUserId_whenGetBookByISBN_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->library.getBookByISBN(ISBN,"1234"));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Invalid user Id.");

    }

    @Test
    public void givenBookIsNotInDB_whenGetBookByISBN_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(anyString())).thenReturn(null);
        // 3. Action
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, ()->library.getBookByISBN(ISBN,"012345678912"));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Book not found!");

    }

    @Test
    public void givenBookIsAlreadyBorrowed_whenGetBookByISBN_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(anyString())).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(true);
        // 3. Action
        BookAlreadyBorrowedException exception = assertThrows(BookAlreadyBorrowedException.class, ()->library.getBookByISBN(ISBN,"012345678912"));
        // 4. Assertion
        assertEquals(exception.getMessage(), "Book was already borrowed!");

    }

    @Test
    public void givenBookIsValid_whenGetBookByISBN_thenDontThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing
        when(mockDatabaseService.getBookByISBN(anyString())).thenReturn(mockBook);
        when(mockBook.isBorrowed()).thenReturn(false);
        // 3. Action
        assertDoesNotThrow(()->library.getBookByISBN(ISBN,"012345678912"));
        // 4. Assertion


    }

    @Test
    public void givenInvalidISBN_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

    @Test
    public void givenInvalidUserId_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

    @Test
    public void givenBookIsNotInDataBase_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

    @Test
    public void givenUserDoesNotExist_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

    @Test
    public void givenNoReviewsForTheBook_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

    @Test
    public void givenReviewServiceIsUnavailable_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

    @Test
    public void givenNotificationServiceDoesNotWork_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

    @Test
    public void givenDataIsValid_whenNotifyUserWithBookReviews_thenThrowException(){
        // 1. Arrange
        Library library = new Library(mockDatabaseService, mockReviewService);
        // 2. Stubbing

        // 3. Action

        // 4. Assertion

    }

}
