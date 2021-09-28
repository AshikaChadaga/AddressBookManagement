package com.bridgelabz.addressbooksystemjdbc;
 
import java.time.LocalDate;
import java.util.ArrayList;  
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bridgelabz.addressbooksystemjdbc.AddressBook.IOService;

public class AddressBookTest {
	

	static AddressBook addressBook = new AddressBook();
	
	@BeforeClass
	public static void createAddressBookObject() {
		addressBook.setAddressBookName("book1");
	}
	
	@Test
	public void givenDetails_ShouldAddToContactList() {
		
		ContactPerson person = new ContactPerson();
		
		
		String firstName = "Ashika";
		String lastName = "Satish";
		String email = "ashika@gmail.com";
		long phoneNumber = 938476387;
		String city = "Bangalore";
		String state = "Karnataka";
		long zipCode = 560043;
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(email);
		person.setPhoneNumber(phoneNumber);
		person.address.setCity(city);
		person.address.setState(state);
		person.address.setZip(zipCode);
		
		addressBook.addContact(firstName, person);
		
		firstName = "Sagarika";
		lastName = "Satish";
		email = "sagarika@gmail.com";
		phoneNumber = 847648253;
		city = "Mangalore";
		state = "Karnataka";
		zipCode = 560043;
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(email);
		person.setPhoneNumber(phoneNumber);
		person.address.setCity(city);
		person.address.setState(state);
		person.address.setZip(zipCode);
		
		addressBook.addContact(firstName, person);
		
		int listSize = addressBook.contactList.size();
		Assert.assertEquals(2, listSize);
		
	}
	
	@Test
	public void given2Contacts_WhenWrittenToFile_ShouldMatchEntries()
	{
		AddressBookFileIO addressFileIO = new AddressBookFileIO();
		addressFileIO.writeToAddressBookFile("book1.txt", addressBook.contactList);
		addressFileIO.printData("book1.txt");
		long entries = addressFileIO.countEntries("book1.txt");
		Assert.assertEquals(2, entries);
		
	}
	
	@Test
	public void givenFile_WhenRead_ShouldReturnNumberOfEntries() {
		
		AddressBookFileIO addressFileIO = new AddressBookFileIO();
		List<String> entries = addressFileIO.readDataFromFile("book1.txt");
		long countEntries = entries.size();
		Assert.assertEquals(2, countEntries);
	}
	
	@Test
	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchCountOfAddressBooks(){
		
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		Map<Integer, String> addressbookList = addressBookDirectory.readAddressDetails(IOService.DB_IO);
		Assert.assertEquals(2, addressbookList.size());
	}
	
	@Test
	public void givenContactInDB_WhenRetrieved_ShouldMatchContactCount(){
		
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		List<ContactPerson> contactdetailsList = addressBookDirectory.readContactDetails(IOService.DB_IO);
		Assert.assertEquals(7, contactdetailsList.size());
	}
	

	@Test
	public void givenCity_WhenMatches_ShouldReturnEmployeeDetails() {
		
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		String city = "Bangalore";
		List<ContactPerson> contactList = addressBookDirectory.getEmployeeDetailsBasedOnCity(IOService.DB_IO, city);
		Assert.assertEquals(2, contactList.size());
	}
	
	@Test
	public void givenState_WhenMatches_ShouldReturnEmployeeDetails() {
		
		String state = "New York";
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		List<ContactPerson> contactList = addressBookDirectory.getEmployeeDetailsBasedOnState(IOService.DB_IO, state);
		Assert.assertEquals(2, contactList.size());
	}
	
	@Test
	public void givenAddressBookInDB_ShouldReturnCountOfBasedOnCity() {
		
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		List<Integer> expectedCountBasedOnGender = new ArrayList();
		expectedCountBasedOnGender.add(1);
		expectedCountBasedOnGender.add(2);
		expectedCountBasedOnGender.add(1);
		expectedCountBasedOnGender.add(1);
		expectedCountBasedOnGender.add(1);
		expectedCountBasedOnGender.add(1);
		List<Integer> maximumSalaryBasedOnGender = addressBookDirectory.getCountOfEmployeesBasedOnCity(IOService.DB_IO);
		if(maximumSalaryBasedOnGender.size() == 6) {
			Assert.assertEquals(expectedCountBasedOnGender, maximumSalaryBasedOnGender);
		}
	}
	
	@Test
	public void givenAddressBookInDB_ShouldReturnCountOfBasedOnState() {
		
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		List<Integer> expectedCountBasedOnGender = new ArrayList();
		expectedCountBasedOnGender.add(1);
		expectedCountBasedOnGender.add(4);
		expectedCountBasedOnGender.add(2);
		List<Integer> maximumSalaryBasedOnGender = addressBookDirectory.getCountOfEmployeesBasedOnState(IOService.DB_IO);
		if(maximumSalaryBasedOnGender.size() == 3) {
			Assert.assertEquals(expectedCountBasedOnGender, maximumSalaryBasedOnGender);
		}
	}
	
	@Test 
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {
		
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		List<ContactPerson> contactData = addressBookDirectory.readContactDetails(IOService.DB_IO);
		addressBookDirectory.updateContactLastName("Rosa", "Ramirez");
		
		boolean result = addressBookDirectory.checkContactDetailsInSyncWithDB("Rosa");
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void givenStartDateRange_WhenMatchesUsingPreparedStatement_ShouldReturnEmployeeDetails() {
		
		String startDate = "2013-01-01";
		String endDate = "2021-01-01";
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		List<ContactPerson> contactData = addressBookDirectory.getContactsBasedOnStartDateUsingPreparedStatement(IOService.DB_IO, startDate, endDate);
		Assert.assertEquals(5, contactData.size());
	}
	
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithUpdatedDB() {
		
		String dateAdded = "2017-02-12";
		AddressBookDirectory addressBookDirectory = new AddressBookDirectory();
		addressBookDirectory.readContactDetails(IOService.DB_IO);
		addressBookDirectory.addContactToUpdatedDatabse(8, "Amy", "Gonzales", 123456789, "amy@gmail.com", 6, "Kodagu", "Karnataka", 345567, dateAdded);

		boolean result = addressBookDirectory.checkContactDetailsInSyncWithDB("Amy");
		Assert.assertTrue(result);
		
	}
	
}
