# w24goldwasser
# Team Goldwasser
## Team Members

Jameel Bsata (Jamster187)

Benjamin Morley (UpscalePilot)

Grant Baartman (grantbaartman)

Jamie McDonald (JamieMcDonald2)

# Project Name TBD

## Release build "Aurora" v2.18-alpha

### See Release Notes at Bottom

## Getting Started

!https://github.com/macewanCS/w24goldwasser.git

!https://www.figma.com/file/sjYPI6UptLTQ1tIf73pxSn/Team-Goldwasser?type=design&node-id=0%3A1&mode=design&t=DPM6oQKMJKvnJGry-1

## Prerequisites

Android Studio, Figma, Relay, Jetpack Compose

## Installing

Step-by-step instructions on how to install the project.
TBD

## Usage

Instructions on how to use the project.
TBD

## Contributing

Push to fork, wait pull request approval
Make sure to mark your tasks in the taskboard, and update version numbers of pages, components, etc.
as required!

## Known Issues

1.7 - transitions not working quite right for home/back buttons in top bar

2.1 - no way to delete employee data once added yet on frontend (query exists) (only because
      edit employee screen not implemented)

2.2 - Selected state for Employee icon on navbar is lost when moving to add employee screen

## License

https://github.com/macewanCS/w24goldwasser/blob/main/LICENSE

## Release Notes

    1.03:
    - Added new database structure
        - lacking way to delete data as of this build - will delete employees from list on
          employee main screen eventually
        - basic UI for add employee screen our UI not added yet

    1.04:
    - New icon states for nav bar (employee icon only for now)
    
    1.05:
    - New icons states for the rest of the nav bar
    1.051:
    - modifiers for icons to align them better

    2.00:
    - New build, new employee page features:
        - scrollable list with list items
        - FAB for adding employees on employee page (WIP as of this release)

    2.01:
    - new FAB logic for employee screen (WIP still)

    2.10
    - adjusted search bar size
    - added weekly calendar to home screen
    - finished FAB button

    2.11
    - imported name field component for Add employee/Edit employee screens

    2.12
    - added database seed for testing

    2.13
    - added new fields for database
    - added UI components to add employee screen

    2.14
    - No update from contributor (database updates)

    2.15
    - UI calendar for schedule screen

    2.16
    - finished UI for add employee screen
    - fixed database - variable types

    2.17
    - Added missing ")" to the database creation line
    - Removed some padding around search bar on employee screen so more employees are visable
    - Replaced position field with phone number field
    - Removed edit icon from employee list on employee screen
    - Changed the overline on the employee list to show whether or not the employee is open or closed trained

    2.18
    - Added navigation to the Schedule screen so now when selecting any date it will navigate
    to the schedule employee screen (not implemented yet). 
    - Fixed spacings and paddings for top bar and bottom bar. 
    - Created the Schedule Employee Screen
