# w24goldwasser
# Team Goldwasser
## Team Members

Jameel Bsata (Jamster187)

Benjamin Morley (UpscalePilot)

Grant Baartman (grantbaartman)

Jamie McDonald (JamieMcDonald2)

# Project Name TBD

## Release build "Aurora" v2.191-alpha

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

Push to fork, wait pull request approval
Make sure to mark your tasks in the taskboard, and update version numbers of pages, components, etc.
as required!

## Known Issues

1.7 - transitions not working quite right for home/back buttons in top bar

2.2 - Selected state for Employee icon on navbar is lost when moving to add employee screen

2.3 - Sometimes generate random test employee data button crashes app (won't matter after testing - low priority)

Other Problems:
    - Pulling from repo causes minor syntax errors sometimes - unknown cause (android studio issue - not issue with app)

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

    2.19
    - Started an employee information screen
    - Created the navagation that takes the user to an employee information screen if they click the employee
    on the employee screen
    2.191
    - minor fixes to UI:
        - Adjusted overline logic on employee list item
        - adjusted size of nav bar (again)- UI adjustments 
