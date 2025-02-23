# e-voting-frontend Documentation

## Overview
The E-Voting Frontend is a web application designed to provide a user-friendly interface for the Blockchain E-Voting System. It allows voters to cast their votes, view results, and manage their sessions securely.

## Project Structure
The project is organized as follows:

```
e-voting-frontend
├── public
│   ├── index.html        # Main entry point for the application
│   ├── vote.html        # Voting page for casting votes
│   ├── viewresults.html  # Page for viewing voting results
│   └── signout.html      # Sign out confirmation page
├── src
│   ├── css
│   │   └── styles.css    # CSS styles for the application
│   ├── js
│   │   ├── main.js       # Main JavaScript file
│   │   ├── vote.js       # JavaScript for the voting page
│   │   └── viewresults.js # JavaScript for the results page
├── package.json           # npm configuration file
└── README.md              # Project documentation
```

## Setup Instructions
1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd e-voting-frontend
   ```

2. **Install Dependencies**
   Make sure you have Node.js and npm installed. Run the following command to install the required packages:
   ```bash
   npm install
   ```

3. **Run the Application**
   You can use a local server to run the application. For example, you can use `live-server` or any other static server:
   ```bash
   npx live-server public
   ```

## Usage Guidelines
- Navigate to the homepage to access the voting and results pages.
- Use the voting page to enter your voter ID and select a party to cast your vote.
- Access the results page by entering the correct password to view the voting results.
- Sign out to end your session and return to the homepage.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License.