# OpenHR

<p align="center">
Progressive, open-source leave management system for staff.
</p>
<p align="center">
  <a href="https://travis-ci.com/Bartosz-D3V/OpenHR">
    <img src="https://travis-ci.com/Bartosz-D3V/OpenHR.svg?token=tqZyPRhzSnop7iN2Y7Ug&branch=master"/>
  </a>
</p>

## Primary objective

### Human resources management

To provide a web-based application that would allow to keep track of employees’ personal information and allow users to amend its information.

### Leave application management

Provide workflow for applying for a leave application. Leave application should automatically exclude bank holidays and weekends. It should be approved by a manager and HR team, if is applied by an employee, or only by HR team if is applied by a manager.

### Delegation application management

Allow delegations to other destinations for professional purposes. Delegation should be approved in the same way as a leave application, but once rejected, it should be amended by an employee and workflow should start again.
Delegation is understood as a situation when an employee is asked to move to destination other than the workplace for professional purposes.

## Sub-objectives

### Managing information

• Secure users’ passwords using one-way encryption

• Store information about employees’ supervisor

• Allow HR Team to amend users’ data

• Provide validation – e.g. validating National Insurance Number (NIN) pattern, UK postcode pattern etc

• Provide validation for elements like National Insurance Number or email address that must be unique

### Leave application & allowance

• Allow users to apply for different types of leave – maternity, paternity, annual and, sick leaves

• Automatically detect bank holidays

• Client and server-side validation to avoid booking a holiday that is longer than annual allowance and to avoid submitting incorrect date

range or date range that has already been applied for

• Allow do download calendar file (ICS) as a reminder

### Delegation application & management

• Provide list of countries that the user can select from

• Provide client and server-side validation to avoid submitting incorrect data

### Modern web development standards

• Design and application that will drive excellent quality, in terms of visual fidelity, user interface (UI) and ease of use user experience (UX)

• Use Material Design for UI, UX and accessibility reasons

• Use RESTful architecture using non-blocking asynchronous calls to provide smooth and efficient UX

• Implement Progressive Web Application checklist that will result in a number of benefits for users: offline mode, mobile application and splash screens

• Implement Responsive Web Design standards so application will look good on every screen resolution and every device

### Auto-rescheduling used leave

User with appropriate role (e.g. HR Team Member or a Manager), should be able to set up the date when the leave allowance of all employees should be reset. This operation should be done automatically every year at a selected date. In addition, it should be possible to select maximum days to carry forward to the next year.

## Technology stack

### Backend

- Java 8
- Spring Boot
- Spring Security
- Alfresco Activiti
- JUnit + Mockito

### Frontend

- Angular
- Typescript
- Angular Material
- Jasmine + Karma

## Install

```bash
yarn // or...
npm install
```

## Run

```bash
yarn start // <-- Start front-end application
mvn spring-boot:run  // <-- Server application
```

## Test

```bash
yarn test // <-- Start front-end application
mvn test  // <-- Server application
```
