package com.bridgelabz.addressbooksystemjdbc;

import java.io.IOException;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.bridgelabz.addressbooksystemjdbc.AddressBook.IOService;


public class AddressBookDirectory implements AddressBookDirectoryIF{
	
	public AddressBook addressBook;
	List<ContactPerson> contactsList = new ArrayList<ContactPerson>();
	Scanner scannerObject = new Scanner(System.in);
	Map<String,AddressBook> addressBookDirectory = new HashMap<String,AddressBook>();
	private AddressBookDBService addressBookDbService;
	

	public AddressBookDirectory(){
		addressBookDbService =  AddressBookDBService.getInstance();
	}
	
	public AddressBookDirectory(List<ContactPerson> contactList) {
		this();
		this.contactsList = contactList;
	}
	
	@Override
	public void operationDirectory() {

		boolean moreChanges = true;
		do {

			System.out.println("\nChoose the operation on the Directory you want to perform");
			System.out.println(
					"1.Add an Address Book\n2.Edit Existing Address Book\n3.Search Person By Region\n4.View People By Region\n5.Count People By Region\n6.Display Address book Directory\n7.Read From Json\n8.Exit Address book System");

			switch (scannerObject.nextInt()) {
			
			case 1:
				System.out.println("Enter the name of the Address Book you want to add");
				String bookNameToAdd = scannerObject.next();
				addAddressBook(bookNameToAdd);
				break;
				
			case 2:
				System.out.println("Enter the Name of the Address Book which you want to edit:");
				String addressBookToEdit = scannerObject.next();
				editAddressBook(addressBookToEdit);
				break;
				
			case 3:
				System.out.println("Enter \n1.Search By City\n2.Search By State");
				int searChoice = scannerObject.nextInt();
				if(searChoice==1){
					System.out.println("Enter the name of the City where the Person resides : ");
					String cityName = scannerObject.next();
					System.out.println("Enter the name of the Person : ");
					String personName = scannerObject.next();
					searchByCity(cityName, personName);
				}
				else {
					System.out.println("Enter the name of the State where the Person resides : ");
					String stateName = scannerObject.next();
					System.out.println("Enter the name of the Person : ");
					String personName = scannerObject.next();
					searchByState(stateName, personName);
				}
				break;
				
			case 4:
				System.out.println("Enter \n1.Display By City\n2.Display By State");
				int displayChoice = scannerObject.nextInt();
				System.out.println("Enter the name of the region :");
				String regionName = scannerObject.next();
				
				if(displayChoice==1)
					displayPeopleByRegion(regionName, AddressBook.personByCity);
				else 
					displayPeopleByRegion(regionName, AddressBook.personByState);
				break;
				
			case 5:
				System.out.println("Enter \n1.Display By City\n2.Display By State");
				int countChoice = scannerObject.nextInt();
				System.out.println("Enter the name of the region :");
				String region = scannerObject.next();
				
				if(countChoice==1)
					countPeopleByRegion(region, AddressBook.personByCity);
				else 
					countPeopleByRegion(region, AddressBook.personByState);
				break;
				
			case 6:
				displayDirectoryContents();
				break;
				
			case 7:
				readDataFromJson();
				break;
				
			case 8:
				moreChanges = false;
				System.out.println("Exiting Address Book Directory !");
			}

		} while (moreChanges);
	}
	
	@Override
	public void addAddressBook(String bookNameToAdd) {
		
		if(addressBookDirectory.containsKey(bookNameToAdd)) {
			System.out.println("Book Name Already Exists");
			return;
		}
		AddressBook addressBook = new AddressBook();
		addressBook.setAddressBookName(bookNameToAdd);
		addressBookDirectory.put(bookNameToAdd, addressBook);
	}
	
	@Override
	public void editAddressBook(String addressBookToEdit) {

		if(addressBookDirectory.containsKey(addressBookToEdit)) {
			addressBook = addressBookDirectory.get(addressBookToEdit);
			addressBook.operation();
		}
		else {
			System.out.println("Book Does Not Exist");
		}
	}
	
	@Override
	public void searchByCity(String cityName, String personName) {
		
		for(AddressBook addressBook : addressBookDirectory.values()) {
			ArrayList<ContactPerson> contactList = addressBook.getContact();
			contactList.stream()
				.filter(person -> person.getFirstName().equals(personName) && person.address.getCity().equals(cityName))
				.forEach(person -> System.out.println(person));	
		}		
	}
	
	@Override
	public void searchByState(String stateName, String personName) {
		
		for(AddressBook addressBook : addressBookDirectory.values()) {
			ArrayList<ContactPerson> contactList = ((AddressBook) addressBook).getContact();
			contactList.stream()
				.filter(person -> person.getFirstName().equals(personName) && person.address.getState().equals(stateName))
				.forEach(person -> System.out.println(person));
		}
	}
	
	@Override
	public void displayPeopleByRegion(String regionName, HashMap<String, ArrayList<ContactPerson>> listToDisplay) {
		
		listToDisplay.values().stream()
			.map(region -> region.stream()
				.filter(person -> person.address.getState().equals(regionName) || person.address.getCity().equals(regionName)))
				.forEach(person -> person.forEach(personDetails -> System.out.println(personDetails)));
	}
	
	@Override
	public void countPeopleByRegion(String regionName, HashMap<String, ArrayList<ContactPerson>> listToDisplay) {
		
		long countPeople = listToDisplay.values().stream()
				.map(region -> region.stream()
					.filter(person -> person.address.getState().equals(regionName) || person.address.getCity().equals(regionName)))
					.count();
					
		System.out.println("Number of People residing in " + regionName+" are: "+countPeople+"\n");
	}
	
	@Override
	public void displayDirectoryContents() {

		System.out.println("----- Contents of the Address Book Directory-----");
		for (String eachBookName : addressBookDirectory.keySet()) {
			
				System.out.println(eachBookName);
		}
		System.out.println("-----------------------------------------");
	}
	
	@Override
	public void readDataFromJson() {
		
		System.out.println("{");
		for(AddressBook addressBook : addressBookDirectory.values()) {
			System.out.println(addressBook.getAddressBookName()+": [\n");
			try {
				addressBook.readDataFromJson();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("]\n");
			
		}
		System.out.println("}");
		
	}
	
	private ContactPerson getContactData(String firstName) {
		
		return this.contactsList.stream()
				.filter(EmployeePayrollDataItem -> EmployeePayrollDataItem.getFirstName().equals(firstName))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<ContactPerson> readContactDetails(IOService ioService) {
		
		if(ioService.equals(IOService.DB_IO))
			this.contactsList = addressBookDbService.readContactDetails();
		return this.contactsList;	
	}
	
	@Override
	public Map<Integer,String> readAddressDetails(IOService ioService) {
		
		Map<Integer,String> contactsList = new HashMap<Integer,String>();
		if(ioService.equals(IOService.DB_IO))
			contactsList = addressBookDbService.readAddressDetails();
		return contactsList;
	}

	@Override
	public List<ContactPerson> getEmployeeDetailsBasedOnCity(IOService ioService, String city) {
		
		List<ContactPerson> contactsList = new ArrayList<ContactPerson>();
		if(ioService.equals(IOService.DB_IO))
			contactsList = addressBookDbService.getContactDetailsBasedOnCityUsingStatement(city);
		return contactsList;
	}

	@Override
	public List<ContactPerson> getEmployeeDetailsBasedOnState(IOService ioService, String state) {
		
		List<ContactPerson> contactsList = new ArrayList<ContactPerson>();
		if(ioService.equals(IOService.DB_IO))
			contactsList = addressBookDbService.getContactDetailsBasedOnStateUsingStatement(state);
		return contactsList;
	}

	@Override
	public List<Integer> getCountOfEmployeesBasedOnCity(IOService ioService) {
		
		List<Integer> countBasedOnCity = new ArrayList<Integer>();
		if(ioService.equals(IOService.DB_IO))
			countBasedOnCity = addressBookDbService.getCountOfEmployeesBasedOnCityUsingStatement();
		return countBasedOnCity;
	}
	
	@Override
	public List<Integer> getCountOfEmployeesBasedOnState(IOService ioService) {
		
		List<Integer> countBasedOnState = new ArrayList<Integer>();
		if(ioService.equals(IOService.DB_IO))
			countBasedOnState = addressBookDbService.getCountOfEmployeesBasedOnStateUsingStatement();
		return countBasedOnState;
	}
	
	@Override
	public List<ContactPerson> getContactsBasedOnStartDateUsingPreparedStatement(IOService ioService, String startDate, String endDate) {
		
		if(ioService.equals(IOService.DB_IO))
			this.contactsList = addressBookDbService.getContactsBasedOnStartDateUsingPreparedStatement(startDate, endDate);
		return this.contactsList;
	}
	
	@Override
	public void updateContactLastName(String firstName, String lastName) {
		
		int result = addressBookDbService.updateContactData(firstName, lastName);
		if(result == 0) 
			return;
		
		ContactPerson contactDetailsData = this.getContactData(firstName);
		if(contactDetailsData != null)
			contactDetailsData.setLastName(lastName);		
	}
	
	@Override
	public boolean checkContactDetailsInSyncWithDB(String firstName) {
		
		List<ContactPerson> contactDetailsList = addressBookDbService.getContactDataUsingName(firstName);
		return contactDetailsList.get(0).equals(getContactData(firstName));
	}

	@Override
	public void addContactToUpdatedDatabase(int id, String firstName, String lastName, long phoneNumber, String email, int addressId, String dateAdded, int addressBookId) {
		
		contactsList.add(addressBookDbService.addNewContactToContacts(id, firstName, lastName, phoneNumber, email, addressId, dateAdded, addressBookId));
	}
	
	@Override
	public void addContacts(List<ContactPerson> contactsListToBeAdded) {
	        Map<Integer,Boolean> additionStatus = new HashMap<Integer, Boolean>();
	        contactsListToBeAdded.forEach(contact -> {
	            Runnable task = () -> {
	                additionStatus.put(contact.hashCode(), false);
	                System.out.println("Contact being added:(threads) "+Thread.currentThread().getName());
	                this.addContactToUpdatedDatabase(contact.getContactid(),contact.getFirstName(),contact.getLastName(),contact.getPhoneNumber(),contact.getEmail(),contact.getAddress().getAddressId(),contact.getDateAdded(), contact.getAddressBookId());
	                additionStatus.put(contact.hashCode(), true);
	                System.out.println("Contact added: (threads)"+Thread.currentThread().getName());
	            };
	            Thread thread = new Thread(task,contact.getFirstName());
	            thread.start();
	        });
	        while(additionStatus.containsValue(false)) {
	            try {
	                Thread.sleep(10);
	            }catch(InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        System.out.println(contactsListToBeAdded);
	    }
	
}
