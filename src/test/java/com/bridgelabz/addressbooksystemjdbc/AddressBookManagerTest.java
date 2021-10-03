package com.bridgelabz.addressbooksystemjdbc;

import java.util.ArrayList; 
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import com.bridgelabz.addressbooksystemjdbc.InputOutputService.IOService;

public class AddressBookManagerTest {
	
	@Test
	public void givenAddressBookInDB_WhenRetrieved_ShouldMatchCountOfAddressBooks(){
		
		AddressBookManager addressBookDirectory = new AddressBookManager();
		Map<Integer, String> addressbookList = addressBookDirectory.readAddressDetails(IOService.DB_IO);
		Assert.assertEquals(2, addressbookList.size());
	}
	
	@Test
	public void givenContactInDB_WhenRetrieved_ShouldMatchContactCount(){
		
		AddressBookManager addressBookDirectory = new AddressBookManager();
		List<ContactPerson> contactdetailsList = addressBookDirectory.readContactDetails(IOService.DB_IO);
		Assert.assertEquals(7, contactdetailsList.size());
	}
	

	@Test
	public void givenCity_WhenMatches_ShouldReturnEmployeeDetails() {
		
		AddressBookManager addressBookDirectory = new AddressBookManager();
		String city = "Bangalore";
		List<ContactPerson> contactList = addressBookDirectory.getEmployeeDetailsBasedOnCity(IOService.DB_IO, city);
		Assert.assertEquals(2, contactList.size());
	}
	
	@Test
	public void givenState_WhenMatches_ShouldReturnEmployeeDetails() {
		
		String state = "New York";
		AddressBookManager addressBookDirectory = new AddressBookManager();
		List<ContactPerson> contactList = addressBookDirectory.getEmployeeDetailsBasedOnState(IOService.DB_IO, state);
		Assert.assertEquals(2, contactList.size());
	}
	
	@Test
	public void givenAddressBookInDB_ShouldReturnCountOfBasedOnCity() {
		
		AddressBookManager addressBookDirectory = new AddressBookManager();
		List<Integer> expectedCountBasedOnGender = new ArrayList<Integer>();
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
		
		AddressBookManager addressBookDirectory = new AddressBookManager();
		List<Integer> expectedCountBasedOnGender = new ArrayList<Integer>();
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
		
		AddressBookManager addressBookDirectory = new AddressBookManager();
		addressBookDirectory.readContactDetails(IOService.DB_IO);
		addressBookDirectory.updateContactLastName("Rosa", "Ramirez");
		
		boolean result = addressBookDirectory.checkContactDetailsInSyncWithDB("Rosa");
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void givenStartDateRange_WhenMatchesUsingPreparedStatement_ShouldReturnEmployeeDetails() {
		
		String startDate = "2019-01-01";
		String endDate = "2021-01-01";
		AddressBookManager addressBookDirectory = new AddressBookManager();
		List<ContactPerson> contactData = addressBookDirectory.getContactsBasedOnStartDateUsingPreparedStatement(IOService.DB_IO, startDate, endDate);
		Assert.assertEquals(3, contactData.size());
	}
	
	@Test
	public void givenNewContact_WhenAdded_ShouldSyncWithUpdatedDB() {
		
		String dateAdded = "2017-02-12";
		AddressBookManager addressBookDirectory = new AddressBookManager();
		addressBookDirectory.readContactDetails(IOService.DB_IO);
		ContactPerson newContact = new ContactPerson(13, "Sukanya", "Satish", Long.parseUnsignedLong("8475633857"), "suki@gmail.com", 1, dateAdded, 1);
		addressBookDirectory.addContactToUpdatedDatabase(newContact);
		addressBookDirectory.readContactDetails(IOService.DB_IO);

		boolean result = addressBookDirectory.checkContactDetailsInSyncWithDB("Sukanya");
		Assert.assertTrue(result);
		
	}
	
	@Test
    public void given3Contacts_WhenAddedToDatabase_ShouldMatchContactEntries() {
		
		AddressBookManager addressBookDirectory = new AddressBookManager();
		addressBookDirectory.readContactDetails(IOService.DB_IO);
        List<ContactPerson> contactToBeAdded = new ArrayList<ContactPerson>();
        contactToBeAdded.add(new ContactPerson(10, "Rick", "Sanchez", Long.parseUnsignedLong("9321546787"), "rick@gmail.com", 4, "2019-01-13", 2));
        contactToBeAdded.add(new ContactPerson(11, "Morty", "Sanchez", Long.parseUnsignedLong("6453847569"), "morty@gmail.com", 5, "2015-03-16", 1));
        contactToBeAdded.add(new ContactPerson(12, "Phil", "Dunphy", Long.parseUnsignedLong("9483775646"), "phil@gmail.com", 2, "2016-09-24", 2));
       
        addressBookDirectory.addContacts(contactToBeAdded);
        List<ContactPerson> contactsInDatabase = addressBookDirectory.readContactDetails(IOService.DB_IO);
        Assert.assertEquals(12, contactsInDatabase.size());
    }
}
