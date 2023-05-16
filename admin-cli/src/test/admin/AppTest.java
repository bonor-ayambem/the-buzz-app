// package edu.lehigh.cse216.alb323.admin;

// import junit.framework.Test;
// import junit.framework.TestCase;
// import junit.framework.TestSuite;

// /**
// * Unit test for simple App.
// */
// public class AppTest extends TestCase {

// Database db = Database.getDatabase(
// "postgres://eehbofgeeadmjf:753fdc0ca74cecb3502d61746fe358ead0c31c0dd7f7d474f7d3ff7593b01ebc@ec2-3-219-111-26.compute-1.amazonaws.com:5432/dco1kb5belobn0");

// /**
// * Create the test case
// *
// * @param testName name of the test case
// */
// public AppTest(String testName) {
// super(testName);
// }

// /**
// * @return the suite of tests being tested
// */
// public static Test suite() {
// return new TestSuite(AppTest.class);
// }

// /**
// * Test to ensure that a row cannot be inserted without a subject and a
// message
// *
// * public void testInsertRow() { db.createTableMessages(); String subject =
// "";
// * String message = ""; String auth = ""; int inserted =
// * db.insertRowMessage(subject, message, auth);
// *
// * assertFalse(inserted == 1); db.dropTableMessages(); }
// */

// /**
// * Test to ensure that the same row cannot be deleted twice
// */
// public void testTableOps() {

// System.out.println("Creating tables");
// db.createTableMessages();
// db.createTableLikes();
// db.createTableUser();
// db.createTableComment();

// // System.out.println("Creating content in tables");
// // db.insertRowMessage("Test Title","Test Message","Shayne");
// // db.insertRowMessage("Test Title 2","Electric boogaloo","Shayne");
// // db.insertRowMessage("Test Title 3D","Straight to dvd","Shayne");
// // db.insertRowComment(0,"First Message","Shayne");
// // db.insertRowComment(1,"First Message","Shayne");
// // db.insertRowComment(1,"Second Message","Shayne");
// // db.insertRowComment(1,"Third Message","Shayne");
// // db.insertRowComment(2,"First Message","Shayne");
// // db.insertRowUser("Mr. Test","McTesterson","Test@Test.com");
// // db.insertRowUser("shayne","conner","smc323@lehigh.edu");

// // System.out.println("fetching single row on table");
// // db.selectOneMessage(1);
// // db.selectOneComment(1,1);
// // db.selectOneUser("smc323@lehigh.edu");

// // System.out.println("fetching all rows on table");

// // db.selectAllMessages();
// // db.selectAllComments(1);
// // db.selectAllUsers();
// }

// }
