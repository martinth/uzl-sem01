package de.uniluebeck.itm.vs1112.uebung4.employeedb;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class must not be modified.
 */
public class EmployeeDBEntry {

	private final long employeeId;

	private String name;

	private String phoneNumber;

	private String emailAddress;

	public EmployeeDBEntry(final long employeeId, final String name) {

		checkNotNull(employeeId);
		checkNotNull(name);

		this.employeeId = employeeId;
		this.name = name;
	}

	public EmployeeDBEntry(final EmployeeDBEntry template) {

		checkNotNull(template);

		this.employeeId = template.employeeId;
		this.name = template.name;
		this.phoneNumber = template.phoneNumber;
		this.emailAddress = template.emailAddress;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setName(final String name) {
		checkNotNull(name);
		this.name = name;
	}

	public void setPhoneNumber(@Nullable final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@SuppressWarnings("unused")
	public String getEmailAddress() {
		return emailAddress;
	}

	@SuppressWarnings("unused")
	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final EmployeeDBEntry that = (EmployeeDBEntry) o;

		return employeeId == that.employeeId;

	}

	@Override
	public int hashCode() {
		return (int) (employeeId ^ (employeeId >>> 32));
	}
}
