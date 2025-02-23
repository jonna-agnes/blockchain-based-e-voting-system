
# Blockchain E-Voting System

## Project Overview

The Blockchain E-Voting System is a secure and transparent voting platform that leverages blockchain technology to ensure the integrity and immutability of votes. This system allows voters to cast their votes, view the results, and ensures that each voter can only vote once.

## Features

- **Secure Voting**: Votes are stored on a blockchain, ensuring they cannot be tampered with.
- **One Vote Per Voter**: Each voter can only cast one vote, preventing duplicate voting.
- **Real-Time Results**: Voting results are updated in real-time and can be viewed after entering a password.
- **User-Friendly Interface**: Simple and intuitive web interface for casting votes and viewing results.

## Project Structure

```
/c:/e-voting
├── e-voting-frontend
│   ├── public
│   │   ├── css
│   │   │   └── styles.css
│   │   ├── js
│   │   │   ├── main.js
│   │   │   └── viewresults.js
│   │   ├── index.html
│   │   ├── vote.html
│   │   └── viewresults.html
│   ├── server.js
├── README.md
```

## Installation

1. **Clone the repository**:

   ```sh
   git clone https://github.com/your-username/e-voting.git
   cd e-voting
   ```

2. **Navigate to the frontend directory**:

   ```sh
   cd e-voting-frontend
   ```

3. **Install dependencies**:
   ```sh
   npm install
   ```

## Running the Project

1. **Start the server**:

   ```sh
   node server.js
   ```

2. **Access the application**:
   Open your web browser and navigate to `http://127.0.0.1:8080`.

## Usage

### Casting a Vote

1. Navigate to the "Cast Vote" page.
2. Enter your Voter ID and select your preferred party.
3. Click "Cast Vote" to submit your vote.
4. If you have already voted, you will receive a message indicating that only one vote is allowed.

### Viewing Results

1. Navigate to the "View Results" page.
2. Enter the password (`resultsxyz`) to view the voting results.
3. The results will display the number of votes each party has received.

## Blockchain Implementation

The blockchain implementation ensures the security and immutability of votes. Each vote is stored as a block in the blockchain, and each block contains the following information:

- **Index**: The position of the block in the chain.
- **Timestamp**: The time when the block was created.
- **Data**: The vote data, including the voter ID and the selected party.
- **Previous Hash**: The hash of the previous block in the chain.
- **Hash**: The hash of the current block, calculated using the block's index, timestamp, data, and previous hash.

## Deployment

To deploy the application, follow these steps:

1. **Set up a server**: Ensure you have a server with Node.js installed.
2. **Clone the repository**: Clone the project repository to your server.
3. **Install dependencies**: Navigate to the project directory and run `npm install`.
4. **Start the server**: Run `node server.js` to start the application.
5. **Access the application**: Open your web browser and navigate to your server's IP address and port (e.g., `http://your-server-ip:8080`).

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

