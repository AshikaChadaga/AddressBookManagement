package com.bridgelabz.addressbooksystemjdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bridgelabz.addressbooksystemjdbc.AddressBook.IOService;

public interface AddressBookDirectoryIF {
	
	public void addAddressBook(String bookNameToAdd);

	public void operationDirectory();

	public void displayDirectoryContents();

	public void editAddressBook(String addressBookToEdit);

	public void searchByCity(String cityName, String personName);

	public void searchByState(String stateName, String personName);

	public void displayPeopleByRegion(String regionName, HashMap<String, ArrayList<ContactPerson>> listToDisplay);

	public void countPeopleByRegion(String regionName, HashMap<String, ArrayList<ContactPerson>> listToDisplay);
	
	public void readDataFromJson();
	
	public List<ContactPerson> readContactDetails(IOService ioService);
	
	public Map<Integer,String> readAddressDetails(IOService ioService);
	
	public List<ContactPerson> getEmployeeDetailsBasedOnCity(IOService ioService, String city);
	
	public List<ContactPerson> getEmployeeDetailsBasedOnState(IOService ioService, String state);
	
	public List<Integer> getCountOfEmployeesBasedOnCity(IOService ioService);
	
	public List<Integer> getCountOfEmployeesBasedOnState(IOService ioService);
	
	public List<ContactPerson> getContactsBasedOnStartDateUsingPreparedStatement(IOService ioService, String startDate, String endDate);
	
	public void updateContactLastName(String firstName, String lastName);
	
	public boolean checkContactDetailsInSyncWithDB(String firstName);
	
	public void addContactToUpdatedDatabase(int id, String firstName, String lastName, long phoneNumber, String email, int addressId, String dateAdded, int addressBookId);
	
	public void addContacts(List<ContactPerson> contactsListToBeAdded);
}
