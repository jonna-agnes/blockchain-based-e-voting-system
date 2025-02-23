document
  .getElementById("passwordForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();
    const password = document.getElementById("password").value;
    fetch("/api/results", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ password }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Unauthorized: Incorrect password");
        }
        return response.json();
      })
      .then((results) => {
        let resultsHtml = "<h2>Voting Results</h2><ul>";
        for (const [party, votes] of Object.entries(results)) {
          resultsHtml += `<li>${party}: ${votes} votes</li>`;
        }
        resultsHtml += "</ul>";
        document.getElementById("resultsContainer").innerHTML = resultsHtml;
      })
      .catch((error) => {
        document.getElementById("resultsContainer").innerHTML =
          "An error occurred: " + error.message;
      });
  });
