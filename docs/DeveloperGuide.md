---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete_meeting 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete_meeting 1")` API call.

![Interactions Inside the Logic Component for the `delete_meeting 1` Command](images/DeleteMeetingSequenceDiagram.png)

<div markdown="span" class="alert alert-info"> 

:information_source: **Note:** The lifeline for `DeleteMeetingCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the data of contacts and meetings.
* exposes unmodifiable `ObservableList<Person>` and `ObservableList<Meeting>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">

:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique `Tag`, instead of each `Person` needing their own `Tag` object.<br>
![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the address book data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add meeting command

#### Implementation

The add meeting mechanism is facilitated by `AddMeetingCommand`. It extends `Command`.

* `AddMeetingCommand#execute()` —  Add a new meeting in the model if it is valid and not a duplicate.

This operation is exposed in the `Model` interface as `Model#addMeeting()`.

The following sequence diagram shows how the add meeting operation works:

![AddMeetingSequenceDiagram](images/AddMeetingSequenceDiagram.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The lifeline for `AddMeetingCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

#### Design consideration:

##### Aspect: How add meeting executes

* Consistent workflow with other commands

_{more aspects and alternatives to be added}_

### Edit meeting command

#### Implementation

The edit meeting mechanism is facilitated by `EditMeetingCommand`. It extends `Command`.

* `EditMeetingCommand#execute()` —  Edit a new meeting in the model if it is valid and not a duplicate.

This operation is exposed in the `Model` interface as `Model#setMeeting()`, `Model#getFilteredMeetingList()` and `Model#getFilteredMeetingList()`.

The following sequence diagram shows how the edit meeting operation works:

![EditMeetingSequenceDiagram](images/EditMeetingSequenceDiagram.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The lifeline for `EditMeetingCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

#### Design consideration:

##### Aspect: How edit meeting executes

* Consistent workflow with other commands

_{more aspects and alternatives to be added}_

### Delete meeting command

#### Implementation

The delete meeting mechanism is facilitated by `DeleteMeetingCommand`. It extends `Command`.

-   `DeleteMeetingCommand#execute()` —  Deletes the meeting (and possibly its recurrences) specified by an index.

The flow of a usual delete meeting execution cycle has been illustrated above as an example in [**logic component**](#logic-component):

#### Design consideration:

##### Aspect: which list to delete from?

*   `DeleteMeetingCommand` is implemented in a way so that it deletes the meeting specified by an index from the _last shown list_. This enables combinatorial commands which seem more intuitive. For instance, `delete_meeting 1` following a `FindMeetingCommand` deletes the first meeting from the search results, whereas the same command following a `ListMeetingCommand` deletes the first meeting from the whole meeting list.

### Find meeting command

#### Implementation

The find meeting mechanism is facilitated by `FindMeetingCommand`. It extends `Command`.

-   `FindMeetingCommand#execute()` —  Finds meeting where the data of meeting matches given keywords.

Given below is the high-level class diagram based on `FindMeetingCommand` and its direct dependencies.

![FindMeetingClassDiagram](images/FindMeetingClassDiagram.png)

The given sequence diagram illustrates the flow of a usual find meeting execution cycle:

![FindMeetingSequenceDiagram](images/FindMeetingSequenceDiagram.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The lifeline for `FindMeetingCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

#### Design consideration:

##### Aspect: Keyword matching Title or Data?

*   Initially, mimicing the functionality of `FindContactCommand`, the find meeting only matched the keywords to the title. However, it made more sense to match other attributes like participant names, location and time since it would be easy to pinpoint which meetings take place where through a single find command.
*   Hence, the predicate matching logic was tweaked in order to accomodate other attributes to make the feature more robust.

##### \[Proposed\] DateTime matching using different formats

*   Currently, DateTime finding is carried out using string matching. It would be more natural to match through DateTime comparison. This would make sure that different date formats like "November" and "Nov" both match the meeting.

### List meeting command

#### Implementation

The list meeting mechanism is facilitated by `ListMeetingCommand`. It extends `Command`.

-   `ListMeetingCommand#execute()` —  Lists out all the meetings stored in the address book.

#### Design consideration:

##### Aspect: why not use find?

*   Adding a syntax like `find_meeting` with empty keyword makes the list operation less intuitive. As `list_meeting` is a frequently used functionality, we decide to have a separate command.

### Clear meeting command

#### Implementation

The clear meeting mechanism is facilitated by `ClearMeetingCommand`. It extends `Command`.

-   `ClearMeetingCommand#execute()` —  Deletes all the meetings stored in the address book.

#### Design consideration:

##### Aspect: why not use delete?

*   Adding a syntax like `delete_meeting all` command makes it hard to parse `DeleteMeetingCommand`, and `clear_meeting` itself is not very often used. 

### Modelling Meetings 

#### Implementation

The Meetings class and meeting details classes are adapted from the code for Persons and person details.

The following is the Class Diagram for the meetings feature.

![MeetingClassDiag](images/MeetingClassDiag.png)

The Meetings class and meeting details classes are adapted from the code for Persons and person details. The Meeting class contains two methods that are not present in the Person class:

* `addParticipant(Person person)` — Adds person as a participant of the meeting.
* `delParticipant(Index index)` — Deletes the participant at index from the meeting's list of participants.

The following sequence diagram shows how the delete participant operation works:

![DelPartSequenceDiagram](images/DelPartSequenceDiagram.png)

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The lifeline for `DeleteParticipantCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `addParticipant` command does the opposite with a similar sequence — it calls `Meeting#addParticipant(person)`.

The following activity diagram summarizes what happens when a user executes a delete participant command:

![ParticipantActivityDiagram](images/ParticipantActivityDiagram.png)

#### Design consideration:

##### Aspect: How add & delete participants executes

* Consistent workflow with other commands

_{more aspects and alternatives to be added}_

### System Timer

#### Implementation

A system timer is implemented to automatically update Ui (implemented) and send reminders (proposed) as time passes by if the app is running in the background (no user interaction). The timer is handled by `Scheduler` and `ScheduledTask`. The `Scheduler` keeps track of the next upcoming meeting, if any, and uses a `Timer` to start `ScheduledTask`. When the time comes, the `Timer` executes `ScheduledTask` where `Scheduler` and `Ui` are updated. The dependencies are shown in the diagram below.

![SchedulerClassDiagram](images/SchedulerClassDiagram.png)

#### Design consideration:

##### Aspect: How to ensure the timer is valid

* The `Scheduler` is updated at the start of application and after every user input. If the `ScheduledTask` is not repaced, which happens most of the time, the overhead is relatively low.

_{more aspects and alternatives to be added}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

* **Target user profile**:
    * Potential Users (who prefer CLI/typing):
        * Coders
        * Authors/Bloggers/Journalists
        * **Personal Secretaries**
    * Potential Users (who need address book):
        * Business managers
        * **Personal Secretaries**
        * HR admins
        * Salespersons
    
    * Common in both: 
        * **Executive Personal Secretary**
    * Job Focus: 
        * Arrange conference calls and meetings
        * Manage clients
        * Send email correspondence
        * Make travel arrangements
    
* **Value proposition**: 
    * Minimise the workload
    * Easier to manage
    * Automate monotonous and repetitive tasks
    * Decrease human errors
    * Reduce typos and spelling mistakes
    * Optimise meeting timings and location
    * Reminders for important tasks/events



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `*`  |    meeting planner | update existing meetings | make sure the details are up to date.| 
| `*`  |    meeting planner | delete specified meetings if they are cancelled.| |
| `*`  |    meeting planner | view all upcoming meetings in a specific order.| |
| `*`  |    meeting planner | add a meeting to the schedule.| |
| `*`  |    frequent user | search for a certain contact I am looking for| |
| `*`  |    frequent user | view a list of all my contacts at any time.| |
| `*`  |    frequent user | create new contact in my contact list.| |
| `*`  |    first-time user | enter my and my employer's details| |
| `*`  |    meeting planner | search for meetings with some criteria.| |
| `*`  |    frequent meeting planner | receive reminders from the app that to remind my employer for an upcoming meeting | make sure my employer can be on time for their meetings.| 
| `*`  |    frequent meeting planner | attach a location and time of the meeting as additional information| |
| `*`  |     expert user | delete some unwanted contacts in my contact list.| |
| `*`  |    frequent user | update each of my contacts whenever there is a change in their particulars/ details.||
| `* *`  |    frequent meeting planner | plan a route based on meeting locations and times.| |
| `* *`  |    frequent user | receive reminders for upcoming meetings |  prepare for the meeting. | 
| `* *`  |    frequent meeting planner | assimilate a map in the app to keep track of all frequently visited locations  |  plan the travel routine wisely that takes time taken to travel from one place to another place into consideration.| 
| `* *`  |    frequent meeting planner | send emails directly from the app by choosing the necessary recipients from the contact list.| |
| `* *`  |    relatively new user | input slightly variated input that the app can understand and interpret  |  learn while doing.| 
| `* *`  |    first time user | learn how to use the app  |  actually use the app to solve the tasks that I have.| 
| `* *`  |    long-time user | automatically archive expired meetings | I am not distracted by old meetings.| 
| `* *`  |    user ready to start using the app | clear all current data |  get rid of data I added when experimenting with the app.| 
| `* *`  |    meeting planner | give priority to certain meetings | make sure these important meetings will take place under the best possible circumstances.| 
| `* *`  |    frequent user | use built-in shortcuts  |  accelerate my workflow.| 
| `* *`  |    frequent meeting planner | import and export the existing calendar  |  save time on entering this information manually.| 
| `* *`  |    relatively new user | be prompted to change my invalid input  |  get it correctly from then on.|  
| `* * *`  |    first time user | find the list of all features that the app has | know what specific task can I complete by using this app.| 
| `* * *`  |    potential user exploring the app | see the app populated with sample data,  |  easily see how the app will look like when it is in use.|
| `* * *`  |    regular user | sync/export contacts/calendar| save time on data migration. |



### Use cases

(For all use cases below, the **System** is the `Recretary` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a person**  

**MSS**  

1. User requests to add a new contact with the relevant details.
2. System indicates that the addition is successful.

Use case ends.

**Extensions**:

* 1a. System detects an error in the data.
  * 1a1. System requests for the correct data.
  * 1a2. User enters new data.
  
  Steps 1a1-1a2 are repeated until the data entered are correct.
  
  Use case resumes from step 2.

**Use case: UC02 - Add a meeting and its participants**  

**MSS**

1. User requests to add a new meeting with the location, title and date.
2. System requests for the participant lists.
3. User enters the participant's name (must be one of the contacts).
4. System indicates that the addition is successful.
5. User repeats step 3 until all participants are added.

Use case ends.

**Extensions**:

* 1a. System detects an error in the data.
  * 1a1. System requests for the correct data.
  * 1a2. User enters new data.
  
  Steps 1a1-1a2 are repeated until the data entered are correct.
  
  Use case resumes from step 2.
  
* 3a. Contact is not in System.
  * 3a1. System requests for the correct contact.
  * 3a2. User enters new contact name.
  
  Steps 3a1-3a2 are repeated until the data entered are correct.
  
  Use case resumes from step 4.

**Use case: UC03 - List all contacts or all meetings**  

**MSS**

1. User requests to list all contacts/ meetings
2. System shows the full list of contacts/ meetings

Use case ends.

**Extensions**:

* 2a. The requested list is empty.
  
  Use case ends.

**Use case: UC04 - Edit a contact**  

**MSS**

1. User requests to <ins> list all contacts (UC03)</ins>.
2. User requests to edit a contact with its index and new details.
3. System indicates that the update is successful.

Use case ends.

**Extensions**:

* 2a. The list is empty.

  Use case ends.

* 2b. User enters a negative integer as index.
  * 2b1. System indicates the error and requests for a non-negative index as index.
  * 2b2. User enters the correct index and new details.
  
  Steps 2b1-2b2 are repeated until the data entered are correct.  
  Use case resumes from step 3.
  
* 2c. User did not enter new details.
  * 2c1. System indicates the error and requests for the correct details.
  * 2c2. User enters the specific index and correct details.
  
  Steps 2c1-2c2 are repeated until the data entered are correct.  
  Use case resumes from step 3.

**Use case: UC05 - Edit a meeting**  

**MSS**

1. User requests to <ins> list all meetings (UC03)</ins>.
2. User requests to edit a meeting with its index and new details.
3. System indicates that the update is successful.

Use case ends.

**Extensions**:

* 2a. The list is empty.

  Use case ends.

* 2b. User enters a negative integer as index.
  * 2b1. System indicates the error and requests for a non-negative index as index.
  * 2b2. User enters the correct index and new details.
  
  Steps 2b1-2b2 are repeated until the data entered are correct.  
  Use case resumes from step 3.
  
* 2c. User did not enter new details.
  * 2c1. System indicates the error and requests for the correct details.
  * 2c2. User enters the specific index and correct details.
  
  Steps 2c1-2c2 are repeated until the data entered are correct.  
  Use case resumes from step 3.
 
* 2d. User requests to edit participant list.
  * 2d1. System shows current list of participants. 
  * 2d2. User enters the index of the participant he/she wants to remove.
  * 2d3. System shows the updated list of participants.
  
  Steps 2d1-2d2 are repeated until the user finishes editing.  
  Use case resumes from step 3.

**Use case: UC06 - Find a contact or a meeting**  

**MSS**

1. User requests to search for a contact/meeting with a keyword.
2. System shows the list of contacts/ meetings with matching keywords.

Use case ends.

**Extensions**:

* 1a. No contact/ meeting matched the keyword method.
  * 1a1. System shows a message indicating no matching records were found.
  Use case ends.
  
**Use case: UC07 - Delete a contact or a meeting**  

**MSS**

1. User requests to <ins> list all contacts/ meetings (UC03)</ins>.
2. User requests to remove a contact/ meeting from the list with its index.
3. System shows a success message 

Use case ends.

**Extensions**:

* 2a. The list is empty.

  Use case ends.

* 2b. User enters a negative integer as index.
  * 2a1. System indicates the error and requests for a non-negative index as index.
  * 2a2. User enters the correct index.
  
  Steps 2b1-2b2 are repeated until the data entered are correct.  
  Use case resumes from step 3.

**Use case: UC08 - Delete all contacts or meetings**  

**MSS**

1.  User requests to delete all contacts/ meetings.
2.  System indicates that the deletion is successful.

    Use case ends.
    
**Extensions**: 

* 1a. No contact/ meeting has been added.

  Use case ends.

### Non-Functional Requirements

1.  Should work on any mainstream OS as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should be portable. The executable must be one-click run.
5.  The data must be saved onto the hard disk and must be transferrable. In case of unexpected shutdown, the data must be preserved. The data should be human readable.
6.  Each new update should be backwords-compatable with the data from the previous versions so that it will be easy for users to port over.
7.  The app must speeden the workflow of the secretary and not be of hinderance.  

### Glossary

* **API**: Application Programming Interface
* **UML**: Unified Modeling Language
* **CLI**: Command Line Interface
* **GUI**: Graphic User Interface
* **MSS**: Main Success Scenario (aka Main Flow of Events)
* **Java FX**: Standard GUI library for Java SE
* **Mainstream OS**: Windows, Linux, Unix, OS-X


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">

:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
