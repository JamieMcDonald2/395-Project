# w24goldwasser
# Team Goldwasser
## Team Members

Jameel Bsata (Jamster187)

Benjamin Morley (UpscalePilot)

Grant Baartman (grantbaartman)

Jamie McDonald (JamieMcDonald2)

# Project Name TBD

## Release build "Solaris" v3.4-alpha

### See Release Notes at Bottom

## Getting Started

<a href="https://github.com/macewanCS/w24goldwasser.git"><img src="https://static.vecteezy.com/system/resources/previews/017/119/660/original/github-logo-git-hub-icon-with-text-on-white-and-black-background-free-vector.jpg" width="100" style="background: white; margin-right: 40px;"></a> <a href="https://www.figma.com/file/sjYPI6UptLTQ1tIf73pxSn/Team-Goldwasser?type=design&node-id=0%3A1&mode=design&t=Jnmr05dUMOu4uRa7-1"><img src="https://brandlogos.net/wp-content/uploads/2022/05/figma-logo_brandlogos.net_6n1pb.png" width="50"></a>

## Prerequisites

Android Studio, Figma, Relay, Jetpack Compose

## Installing

Step-by-step instructions on how to install the project.
TBD

## Usage

Instructions on how to use the project.
TBD

## Contributing

Push to fork, wait pull request approval.

Make sure to mark your tasks in the taskboard, and update version numbers of pages, components, etc.
as required!

## Known Issues

1.7 - transitions not working quite right for home/back buttons in top bar

2.2 - Selected state for Employee icon on navbar is lost when moving to add employee screen

2.3 - Sometimes generate random test employee data button crashes app (won't matter after testing - low priority)

2.4 - transitions from employee list are wrong - sometimes! known issue with android studio

2.6 - text field logic is incomplete on most pages (easy to fix)

2.7 - error checking missing on many fields/buttons - missing on nav bar icons/home icon

2.8 - pressed state activated on weekly calendar (a single day) when app starts - should not be - only sometimes

Other Problems:

    - Pulling from repo causes minor syntax errors sometimes - unknown cause (android studio issue - not issue with app)
    - Unused functions and errors that can be ignored for now

## License

https://github.com/macewanCS/w24goldwasser/blob/main/LICENSE

## Release Notes

    3.4
    - Started availability overlay on edit employee screen

    3.2
    - Added textfield logic for remaining screens that didn't have it
    - rewrote logic for textfields on edit employee screen so that deleted fields are maintained when scrolling
    - fixed issue with settings text field/user name (wasn't saving unless you clicked outside the box)
    3.21
    - fixed bug with back button logic (showed dialog unnecessarily on the employee main page)

    3.1
    - Continued and completed visual aspect of schedule screens

    3.0
    - New Release Build 'Solaris'
    - Bug Testing - found issues (listed in known issues)
    - Removed 'ID' field from Edit Employee Screen
    - readded 'edit availability' button

    2.99
    - fixed logic on edit employee page to allow more then one field to be edited each edit session (was only updating last field edited in 2.9)
    - added verification to back button for edit employee (untested on other page fields but might work)
    - updates to edit employee viewmodel and database to allow for proper verification going forward
    - removed unnecessary / outdated logic from many items

    2.9
    - added more edit employee logic, confirmations and error checking
    - added more database logic to help with verifications
    - large debugging related to viewmodel and database information sharing

    - bug remains on edit employee page, see known issues 2.9

    2.8
    - Added more functionality to the scheduling part of application

    2.71
    - bug fix for add employee

    2.7
    - availability button/logic on edit employee screen

    - added alot of logging that will need to be removed later

    - refactored edit employee logic to be more efficient
    - adjusted top bar text field to be more efficient
    - bug fixing - add employee errors because of many updates to navigation

    2.6
    - Finished Edit employee screen
    - new top bar text field can be manipulated from any screen/component
    - Major DB changes (Employee ID)
    
    2.5
    - Starting functionality to schedule employee screen

    2.41
    - new inactive colors for employee list items / inactive employees

    2.4
    - New logic for passing data to text fields on employee screen
    - New settings field for user name
    - User name displays in top bar  
    - New logic for retained database information in textfields based on settings field (for use anywhere)
        - deployed this new logic to the employee info screen
    - performance improvments for bug fixing

    2.3
    - Set up employee information page to recieve and Employee ID as an argument
    - Set up nav controller to handle argument for employee info screen
    - Set up viewModel function to use the new querry for single employee retreival
    - Changed employee list item to pass the ID of the employee that is clicked

    2.2
    - fixed top bar icons - final
    - added new query for single employee data retrival

    2.19
    - Started an employee information screen
    - Created the navagation that takes the user to an employee information screen if they click the employee
    on the employee screen
    2.191
    - minor fixes to UI:
        - Adjusted overline logic on employee list item
        - adjusted size of nav bar (again)- UI adjustments 

    2.18
    - Added navigation to the Schedule screen so now when selecting any date it will navigate
    to the schedule employee screen (not implemented yet). 
    - Fixed spacings and paddings for top bar and bottom bar. 
    - Created the Schedule Employee Screen

    2.17
    - Added missing ")" to the database creation line
    - Removed some padding around search bar on employee screen so more employees are visable
    - Replaced position field with phone number field
    - Removed edit icon from employee list on employee screen
    - Changed the overline on the employee list to show whether or not the employee is open or closed trained

    2.16
    - finished UI for add employee screen
    - fixed database - variable types

    2.15
    - UI calendar for schedule screen

    2.14
    - Database updates

    2.13
    - added new fields for database
    - added UI components to add employee screen

    2.12
    - added database seed for testing

    2.11
    - imported name field component for Add employee/Edit employee screens

    2.10
    - adjusted search bar size
    - added weekly calendar to home screen
    - finished FAB button

    2.01:
    - new FAB logic for employee screen (WIP still)

    2.00:
    - New build, new employee page features:
        - scrollable list with list items
        - FAB for adding employees on employee page (WIP as of this release)

    1.05:
    - New icons states for the rest of the nav bar
    1.051:
    - modifiers for icons to align them better

    1.04:
    - New icon states for nav bar (employee icon only for now)  

    1.03:
    - Added new database structure
        - lacking way to delete data as of this build - will delete employees from list on
          employee main screen eventually
        - basic UI for add employee screen our UI not added yet
    






















